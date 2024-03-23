package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelAssemblerGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelAssemblerRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;


public class PixelAssemblerTile extends AbstractMachineTile<PixelAssemblerTile> {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedUpgrade = 0;
    private int energyUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 1024000;
    private static final int energyConsumption = 256;
    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    private final FluidTank fluidTank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    public void setFluid(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public PixelAssemblerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.PIXEL_ASSEMBLER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> PixelAssemblerTile.this.progress;
                    case 1 -> PixelAssemblerTile.this.maxProgress;
                    case 2 -> PixelAssemblerTile.this.speedUpgrade;
                    case 3 -> PixelAssemblerTile.this.capacity;
                    case 4 -> PixelAssemblerTile.this.maxReceive;
                    case 5 -> PixelAssemblerTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PixelAssemblerTile.this.progress = value;
                    case 1 -> PixelAssemblerTile.this.maxProgress = value;
                    case 2 -> PixelAssemblerTile.this.speedUpgrade = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot <= 2) return true;
        else if (slot == 4) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        else if (slot == 5) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot == 6) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        return false;
    }

    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 3;
    }

    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==5)
            speedUpgradeCheck();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.pixel_assembler");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        return new PixelAssemblerGuiMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag = fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
        fluidTank.readFromNBT(nbt);
    }

            //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, PixelAssemblerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, PixelAssemblerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, PixelAssemblerTile pBlockEntity) {
        transferFluidToItem(pBlockEntity, fluidTank, 4);
        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(4).getCount();
            pBlockEntity.energyUpgradeCheck();
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(speedAmount != 0 ? speedAmount * energyConsumption - pBlockEntity.energyUpgrade * speedAmount : energyConsumption);
            if(pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
                   craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(PixelAssemblerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelAssemblerRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelAssemblerRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
//                && canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
//                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static boolean hasPower(PixelAssemblerTile entity) {
        return entity.energyStorage.getEnergyStored() >= (energyConsumption - entity.energyUpgrade);
    }

    private static void craftItem(PixelAssemblerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelAssemblerRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelAssemblerRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            List<CountedIngredient> recipeItems = match.get().getInputs();


            entity.removeMultiInput(recipeItems, 0, 2);

            entity.addOutput(match.get().getResultItem(), 3);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private static boolean canInsertItemIntoSlot(ItemStack stackInSlot, ItemStack newStack, PixelAssemblerRecipe pixelSplitterRecipe) {
        return false;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void speedUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(5).getItem() == POMitems.SPEED_UPGRADE.get()) {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(5).getCount();
        } else {
            this.speedUpgrade = 0;
        }
    }
    private void energyUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(6).getItem() == POMitems.ENERGY_UPGRADE.get()) {
            this.energyUpgrade = energyConsumption / 10 * this.itemHandler.getStackInSlot(6).getCount();
        } else {
            this.energyUpgrade = 0;
        }
    }

    public int getProgress() {return progress;}
    public int getMaxProgress() {return maxProgress;}

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(3).getMaxStackSize() >= inventory.getItem(3).getCount() + count;
    }

    public ItemStack customInsertItemStack(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        ItemStack existing = this.itemHandler.getStackInSlot(slot);
        int limit =  Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize());
        boolean limitReached = existing.getCount() + stack.getCount() > limit;

        if (!limitReached && !simulate) {
            stack.setCount(existing.getCount() + stack.getCount());
            itemHandler.setStackInSlot(slot, stack);
        }
        if (limitReached) {

            if (!simulate) {
                stack.setCount(stack.getMaxStackSize());
                itemHandler.setStackInSlot(slot, stack);
            }
            stack.setCount(existing.getCount()+stack.getCount()-stack.getMaxStackSize());
            return stack;
        }
        return ItemStack.EMPTY;
    }

    private void transferFluidToItem(PixelAssemblerTile pBlockEntity, FluidTank tank, int slot) {
        pBlockEntity.itemHandler.getStackInSlot(slot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int fillAmount = Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.SIMULATE), 1000);

            FluidStack stack = new FluidStack(tank.getFluid(), fillAmount);
            if(handler.isFluidValid(0, stack)) {
                stack = new FluidStack(tank.getFluid(), Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE), 1000));
                drainTankWithFluid(pBlockEntity, tank, stack, handler.getContainer(), slot);
            }
        });
    }

    private void drainTankWithFluid(PixelAssemblerTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item, int slot) {
        fluidTank.drain(stack, IFluidHandler.FluidAction.EXECUTE);

        pBlockEntity.itemHandler.extractItem(slot, 1, false);
        pBlockEntity.itemHandler.insertItem(slot, item, false);
    }


    //---ENERGY---//

    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy " + energyStorage.getEnergyStored() + " is higher than max " + energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at " + this.getBlockPos() + " was outside limits, energy reverted to 0");
        }
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }
    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

}
