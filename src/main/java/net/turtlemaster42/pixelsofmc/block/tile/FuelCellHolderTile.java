package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.IInventoryHandlingTile;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class FuelCellHolderTile extends AbstractMultiBlockTile implements IInventoryHandlingTile {

    private boolean locked = false;

    public FuelCellHolderTile(BlockPos pPos, BlockState pBlockState) {
        super(POMtiles.FUEL_CELL_HOLDER.get(), pPos, pBlockState);
    }

    private final ItemStackHandler itemHandler = new PixelItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
        }

//        @Override
//        @Nonnull
//        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//            ItemStack insertSim;
//            insertSim = ItemStack.EMPTY;
//            if (level.getBlockEntity(worldPosition) instanceof FuelCellHolderTile fuelTile && fuelTile.isMainPosValid()) {
//                BlockEntity tile = level.getBlockEntity(fuelTile.getMainPos());
//                if (tile instanceof NuclearReactorTile reactorTile) {
//                    PixelItemStackHandler ItemHandlerFrom = (PixelItemStackHandler) reactorTile.getItemStackHandler();
//                    if (ItemHandlerFrom == null || slot > ItemHandlerFrom.getSlots() - 1 || !ItemHandlerFrom.isItemValid(slot, stack)) {
//                        PixelsOfMc.LOGGER.info("{}, {}, {}", ItemHandlerFrom == null, slot > ItemHandlerFrom.getSlots() - 1, !ItemHandlerFrom.isItemValid(slot, stack));
//                        return stack;
//                        }
//                    itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
//                    insertSim = ItemHandlerFrom.insertItem(slot, stack, true);
//                    if (insertSim == stack)
//                        return stack;
//                    if (!simulate) {
//                        ItemStack newStack = stack;
//                        newStack.setCount(stack.getCount() - insertSim.getCount());
//                        ItemHandlerFrom.insertItem(slot, newStack, false);//nbt still not working
//                        itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
//                    }
//                }
//            }
//            return insertSim;
//        }
//        @Override
//        @Nonnull
//        public ItemStack extractItem(int slot,int amount, boolean simulate) {
//            if (level.getBlockEntity(worldPosition) instanceof FuelCellHolderTile fuelTile && fuelTile.isMainPosValid()) {
//                BlockEntity tile = level.getBlockEntity(fuelTile.getMainPos());
//                if (tile instanceof NuclearReactorTile reactorTile) {
//                    PixelItemStackHandler ItemHandlerFrom = (PixelItemStackHandler) reactorTile.getItemStackHandler();
//                    if (ItemHandlerFrom == null || !ItemHandlerFrom.isValidOutput(slot))
//                        return ItemStack.EMPTY;
//                    itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
//                    ItemHandlerFrom.extractItem(slot, amount, simulate);
//                    return super.extractItem(slot, amount, simulate);
//                }
//            }
//            return ItemStack.EMPTY;
//        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putBoolean("locked", locked);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        locked = nbt.getBoolean("locked");
    }


    @Override
    public void setHandler(ItemStackHandler handler) {
        itemHandler.setStackInSlot(0, handler.getStackInSlot(0));
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    public void cycleLocking() {
        this.locked = !this.locked;
    }

    public boolean isLocked() {
        return locked;
    }
}
