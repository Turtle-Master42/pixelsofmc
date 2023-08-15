package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ItemInputFusionTile extends AbstractMultiBlockTile{

    public ItemInputFusionTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(27) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
        }
        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            BlockPos posTarget = getMainPos();
            if (posTarget.equals(worldPosition)) {
                return stack;
            }
            ItemStack insertSim = stack;
            BlockEntity tile = level.getBlockEntity(posTarget);
            if (tile != null) {
                IItemHandler ItemHandlerFrom = tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP.getOpposite()).orElse(null);
                if (ItemHandlerFrom != null) {
                    if (slot > ItemHandlerFrom.getSlots()-1 || !ItemHandlerFrom.isItemValid(slot, stack))
                        return stack;
                    itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
                    insertSim = ItemHandlerFrom.insertItem(slot, stack, true);
                    if (insertSim == stack)
                        return stack;
                    if (!simulate) {
                        ItemStack newStack = stack;
                        newStack.setCount(stack.getCount() - insertSim.getCount());
                        ItemHandlerFrom.insertItem(slot, newStack, false);//nbt still not working

                        itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
                    }
                }
            }
            return insertSim;
        }
        @Override
        @Nonnull
        public ItemStack extractItem(int slot,int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
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
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
    }
}
