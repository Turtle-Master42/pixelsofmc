package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
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
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.IInventoryHandlingTile;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FuelCellHolderTile extends AbstractMultiBlockTile implements IInventoryHandlingTile {

    private boolean locked = false;

    public FuelCellHolderTile(BlockPos pPos, BlockState pBlockState) {
        super(POMtiles.FUEL_CELL_HOLDER.get(), pPos, pBlockState);
    }

    private final ItemStackHandler itemHandler = new PixelItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
            setChanged();
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
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
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
