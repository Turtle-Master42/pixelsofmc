package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
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
import net.turtlemaster42.pixelsofmc.block.ChemicalSeparatorBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalSeparatorGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.random;

public class ChemicalSeparatorTile extends AbstractMachineTile<ChemicalSeparatorTile> implements IDuoFluidHandlingTile {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedUpgrade = 0;
    private final int capacity = 512000;
    private final int maxReceive = 512000;
    private static final int energyConsumption = 128;

    public final PixelEnergyStorage energyStorage = createEnergyStorage();
    private final FluidTank fluidTank = new FluidTank(16000) {
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

    private final FluidTank duoFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.fluid, worldPosition));
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

    public void setDuoFluid(FluidStack stack) {
        this.duoFluidTank.setFluid(stack);
    }

    public FluidStack getDuoFluid() {
        return this.duoFluidTank.getFluid();
    }

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
                setChanged();
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                onEnergyChanged();
                setChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();


    public ChemicalSeparatorTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.CHEMICAL_SEPARATOR.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChemicalSeparatorTile.this.progress;
                    case 1 -> ChemicalSeparatorTile.this.maxProgress;
                    case 2 -> ChemicalSeparatorTile.this.speedUpgrade;
                    case 3 -> ChemicalSeparatorTile.this.capacity;
                    case 4 -> ChemicalSeparatorTile.this.maxReceive;
                    case 5 -> ChemicalSeparatorTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ChemicalSeparatorTile.this.progress = value;
                    case 1 -> ChemicalSeparatorTile.this.maxProgress = value;
                    case 2 -> ChemicalSeparatorTile.this.speedUpgrade = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot==0) return true;
        else if (slot==4) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==5) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        else if (slot==6) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent() && !stack.is(Items.BUCKET);
        else if (slot==7) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        else if (slot==8) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        return false;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot > 0 && slot < 4;
    }
    @Override
    protected int itemHandlerSize() {return 9;}
    protected void contentsChanged(int slot) {
        if (slot==4)
            speedUpgradeCheck();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.chemical_separator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        return new ChemicalSeparatorGuiMenu(pContainerId, pInventory, this, this.data);
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
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            Direction localDir = this.getBlockState().getValue(ChemicalSeparatorBlock.FACING);
            return switch (localDir) {
                case EAST -> {
                    if (side == Direction.SOUTH)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.NORTH)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                case SOUTH -> {
                    if (side == Direction.WEST)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.EAST)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                case WEST -> {
                    if (side == Direction.NORTH)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.SOUTH)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                default -> {
                    if (side == Direction.EAST)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.WEST)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
            };
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
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
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
    }


    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalSeparatorTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalSeparatorTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, ChemicalSeparatorTile pBlockEntity) {
        transferFluidToItem(pBlockEntity, fluidTank, 7);
        transferFluidToItem(pBlockEntity, duoFluidTank, 8);
        if (hasFluidItemInScourceSlot(pBlockEntity)) {
            transferFluidToTank(pBlockEntity);
        }
        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(4).getCount();
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));

            if (pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void transferFluidToTank(ChemicalSeparatorTile pBlockEntity) {
        pBlockEntity.itemHandler.getStackInSlot(6).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(pBlockEntity.fluidTank.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pBlockEntity.fluidTank.isFluidValid(stack)) {
                if (pBlockEntity.fluidTank.getFluid().isFluidEqual(stack) || pBlockEntity.fluidTank.getFluid().isEmpty()) {
                    stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                    fillTankWithFluid(pBlockEntity, fluidTank, stack, handler.getContainer());
                }
            }
        });
    }

    private void transferFluidToItem(ChemicalSeparatorTile pBlockEntity, FluidTank tank, int slot) {
        pBlockEntity.itemHandler.getStackInSlot(slot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int fillAmount = Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.SIMULATE), 1000);

            FluidStack stack = new FluidStack(tank.getFluid(), fillAmount);
            if(handler.isFluidValid(0, stack)) {
                stack = new FluidStack(tank.getFluid(), Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE), 1000));
                drainTankWithFluid(pBlockEntity, tank, stack, handler.getContainer(), slot);
            }
        });
    }

    private void fillTankWithFluid(ChemicalSeparatorTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item) {
        fluidTank.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        PixelsOfMc.LOGGER.info("item: {}", item.toString());

        pBlockEntity.itemHandler.extractItem(6, 1, false);
        pBlockEntity.itemHandler.setStackInSlot(6, item);
    }

    private void drainTankWithFluid(ChemicalSeparatorTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item, int slot) {
        fluidTank.drain(stack, IFluidHandler.FluidAction.EXECUTE);

        pBlockEntity.itemHandler.extractItem(slot, 1, false);
        pBlockEntity.itemHandler.insertItem(slot, item, false);
    }

    private boolean hasFluidItemInScourceSlot(ChemicalSeparatorTile pBlockEntity) {
        return pBlockEntity.itemHandler.getStackInSlot(6).getCount() > 0;
    }

    private static boolean hasRecipe(ChemicalSeparatorTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ChemicalSeparatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(ChemicalSeparatorRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertIntoOutputSlot(entity, match.get())
                && canExtractInputFluid(entity, match.get().getFluidInput())
                && canInsertOutputFluid(entity, match.get().getResultFluid());
    }

    private static boolean canInsertOutputFluid(ChemicalSeparatorTile entity, FluidStack resultFluid) {
        return resultFluid.equals(entity.duoFluidTank.getFluid()) && resultFluid.getAmount() < entity.duoFluidTank.getSpace() || entity.duoFluidTank.isEmpty() || resultFluid.isEmpty();
    }

    private static boolean canExtractInputFluid(ChemicalSeparatorTile entity, FluidStack fluidInput) {
        return fluidInput.equals(entity.fluidTank.getFluid()) && fluidInput.getAmount() < entity.fluidTank.getFluidAmount() || fluidInput.isEmpty();
    }

    private static boolean hasPower(ChemicalSeparatorTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(4).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
    }


    private static void craftItem(ChemicalSeparatorTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ChemicalSeparatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(ChemicalSeparatorRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent() && !level.isClientSide) {
            List<ChanceIngredient> outputs = match.get().getOutputs();
            boolean[] matched = new boolean[outputs.size()];
            // Iterate over the outputs -out-
            for (int out = 0;out < outputs.size(); out++) {
                ItemStack beginStack =  match.get().getResultItems(out);
                ItemStack newStack;
                newStack = beginStack;
                // Iterate over the slots -slot-
                for (int slot = 1; slot < 4; slot++) {
                    // if already matched continue output cycle
                    if (matched[out])
                        continue;
                    //if it can insert it will, otherwise continue slot cycle
                    if (canInsertItemIntoSlot(entity.itemHandler.getStackInSlot(slot), newStack)) {
                        //inserts items and sets newStack to that what could not be inserted
                        boolean chance = match.get().OutputChance(out) > random();
                        if (chance && newStack==beginStack)
                            newStack = entity.insertItemStack(slot, newStack, false);
                        else newStack=ItemStack.EMPTY;
                        // if newStack is empty match = true
                        if (newStack.isEmpty()) {
                            matched[out]=true;
                        }
                    }
                }
            }
            entity.fluidTank.drain(match.get().getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            entity.duoFluidTank.fill(match.get().getResultFluid(), IFluidHandler.FluidAction.EXECUTE);
            entity.itemHandler.extractItem(0, match.get().getInputCount(), false);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}

    private void speedUpgradeCheck() {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(4).getCount();
    }

    private int energyUpgrade() {
        int amount = this.itemHandler.getStackInSlot(5).getCount();
        return energyConsumption / 10 * amount;
    }

    private int speedUpgrade() {
        int amount = this.itemHandler.getStackInSlot(4).getCount();
        return maxProgress / 10 * amount;
    }


    private static boolean canInsertItemIntoSlot(ItemStack stack, ItemStack item) {
        return item.getItem()==stack.getItem() && stack.getCount() + item.getCount() <= stack.getMaxStackSize() || stack.isEmpty();
    }

    private static boolean canInsertIntoOutputSlot (ChemicalSeparatorTile entity, ChemicalSeparatorRecipe match) {
        boolean[] matched = new boolean[match.getOutputs().size()];
        boolean[] matchNeeded = new boolean[match.getOutputs().size()];
        ItemStack[] newStackInSlot = new ItemStack[5];
        ItemStack newStack;

        // Makes sure that newStackInSlot[] is not null
        for (int i = 1; i < 4; i++)
            newStackInSlot[i] = ItemStack.EMPTY;

        // Iterate over the inputs -q-
        for (int q = 0; q < match.getOutputs().size(); q++) {
            matchNeeded[q] = true;
            newStack = match.getResultItems(q);
            // Iterate over the slots -p-
            for (int p = 1; p < 4; p++) {
                if (matched[q])
                    continue;
                ItemStack[] stackInSlot = new ItemStack[5];
                stackInSlot[p] = entity.itemHandler.getStackInSlot(p);
                if (stackInSlot[p].isEmpty())
                    stackInSlot[p] = newStackInSlot[p];
                if (canInsertItemIntoSlot(stackInSlot[p], newStack)) {
                    newStackInSlot[p] = newStack;
                    newStack = entity.insertItemStack(p, newStack, true);
                    if (newStack.isEmpty()) {
                        matched[q] = true;
                    }
                }
            }
        }

        for (int i = 0; i < match.getOutputs().size(); i++) {
            if (matched[i]!=matchNeeded[i])
                return false;
        }
        return true;
    }

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}",energyStorage.getEnergyStored() ,energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }
    @Override
    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }
}


