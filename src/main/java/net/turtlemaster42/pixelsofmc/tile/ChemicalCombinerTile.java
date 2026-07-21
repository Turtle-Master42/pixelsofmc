package net.turtlemaster42.pixelsofmc.tile;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.block.ChemicalCombinerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalCombinerMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ChemicalCombinerTile extends AbstractMachineTile<ChemicalCombinerTile> implements IDuoFluidHandlingTile {

    protected final ContainerData data;

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

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();


    public ChemicalCombinerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.CHEMICAL_COMBINER.get(), pWorldPosition, pBlockState, 512000, 128);
        defineMaxProgress(72);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChemicalCombinerTile.this.progress;
                    case 1 -> ChemicalCombinerTile.this.maxProgress;
                    case 2 -> ChemicalCombinerTile.this.requiredProgress;
                    case 3 -> ChemicalCombinerTile.this.capacity;
                    case 4 -> ChemicalCombinerTile.this.maxReceive;
                    case 5 -> ChemicalCombinerTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ChemicalCombinerTile.this.progress = value;
                    case 1 -> ChemicalCombinerTile.this.maxProgress = value;
                    case 2 -> ChemicalCombinerTile.this.requiredProgress = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (0 <= slot && slot < 3) return true;
        else if (slot==4) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==5) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        else if (slot==6) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        else if (slot==7) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        else if (slot==8) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        return false;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 3;
    }
    @Override
    protected int itemHandlerSize() {return 9;}
    protected void contentsChanged(int slot) {
        if (slot==4) speedUpgradeCheck(4);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.chemical_combiner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        return new ChemicalCombinerMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            Direction localDir = this.getBlockState().getValue(ChemicalCombinerBlock.FACING);
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
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
    }


    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalCombinerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalCombinerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, ChemicalCombinerTile pBlockEntity) {
        transferFluidToItem(pBlockEntity, fluidTank, 7);
        transferFluidToItem(pBlockEntity, duoFluidTank, 8);
        if (hasFluidItemInSourceSlot(pBlockEntity)) {
            transferFluidToTank(pBlockEntity);
        }
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;

            if (pBlockEntity.progress > pBlockEntity.requiredProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void transferFluidToTank(ChemicalCombinerTile pBlockEntity) {
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

    private void transferFluidToItem(ChemicalCombinerTile pBlockEntity, FluidTank tank, int slot) {
        pBlockEntity.itemHandler.getStackInSlot(slot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int fillAmount = Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.SIMULATE), 1000);

            FluidStack stack = new FluidStack(tank.getFluid(), fillAmount);
            if(handler.isFluidValid(0, stack)) {
                stack = new FluidStack(tank.getFluid(), Math.min(handler.fill(stack, IFluidHandler.FluidAction.EXECUTE), 1000));
                drainTankWithFluid(pBlockEntity, tank, stack, handler.getContainer(), slot);
            }
        });
    }

    private void fillTankWithFluid(ChemicalCombinerTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item) {
        fluidTank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pBlockEntity.itemHandler.extractItem(6, 1, false);
        pBlockEntity.itemHandler.setStackInSlot(6, item);
    }

    private void drainTankWithFluid(ChemicalCombinerTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item, int slot) {
        fluidTank.drain(stack, IFluidHandler.FluidAction.EXECUTE);

        pBlockEntity.itemHandler.extractItem(slot, 1, false);
        pBlockEntity.itemHandler.insertItem(slot, item, false);
    }

    private boolean hasFluidItemInSourceSlot(ChemicalCombinerTile pBlockEntity) {
        return pBlockEntity.itemHandler.getStackInSlot(6).getCount() > 0;
    }

    private static boolean hasRecipe(ChemicalCombinerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ChemicalCombinerRecipe> match = level.getRecipeManager()
                .getRecipeFor(ChemicalCombinerRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent()
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().asItemStack())
                && canInsertAmountIntoOutputSlot(inventory, match.get().getOutput().count())
                && canExtractInputFluid(entity, match.get().getFluidInput())
                && canInsertOutputFluid(entity, match.get().getResultFluid());
    }

    private static boolean canInsertOutputFluid(ChemicalCombinerTile entity, FluidStack resultFluid) {
        return resultFluid.equals(entity.duoFluidTank.getFluid()) && resultFluid.getAmount() < entity.duoFluidTank.getSpace() || entity.duoFluidTank.isEmpty() || resultFluid.isEmpty();
    }

    private static boolean canExtractInputFluid(ChemicalCombinerTile entity, FluidStack fluidInput) {
        return fluidInput.equals(entity.fluidTank.getFluid()) && fluidInput.getAmount() <= entity.fluidTank.getFluidAmount() || fluidInput.isEmpty();
    }

    private static void craftItem(ChemicalCombinerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ChemicalCombinerRecipe> match = level.getRecipeManager()
                .getRecipeFor(ChemicalCombinerRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            List<CountedIngredient> recipeItems = match.get().getInputs();

            entity.removeMultiInput(recipeItems, 0, 2);
            entity.fluidTank.drain(match.get().getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE);

            entity.addChanceOutput(match.get().getOutput(), 3);
            entity.duoFluidTank.fill(match.get().getResultFluid(), IFluidHandler.FluidAction.EXECUTE);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private static boolean canInsertItemIntoSlot(ItemStack stack, ItemStack item) {
        return item.getItem()==stack.getItem() && stack.getCount() + item.getCount() <= stack.getMaxStackSize() || stack.isEmpty();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(3).getMaxStackSize() >= inventory.getItem(3).getCount() + count;
    }

    public FluidTank getFluidTank() { return fluidTank; }

    public FluidTank getDuoFluidTank() { return duoFluidTank; }
}


