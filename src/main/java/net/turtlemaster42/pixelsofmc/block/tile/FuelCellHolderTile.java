package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.block.FuelCellHolderBlock;
import net.turtlemaster42.pixelsofmc.block.NuclearReactorBlock;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
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
    public void onInvalidation() {
        if (level.isClientSide() || !this.isLocked()) {return;}
        
        FuelCellHolderBlock.steamParticles((ServerLevel) level,  level.getBlockState(worldPosition), worldPosition, isLocked(), hasFuelCell());
        this.locked = false;
        level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FuelCellHolderBlock.CELL_TYPE, 0), 2);
        BlockPos mainPos = getMainPos();
        if (level.getBlockEntity(mainPos) instanceof NuclearReactorTile reactorTile) {
            Direction mainDirection = level.getBlockState(mainPos).getValue(NuclearReactorBlock.FACING);
            if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 0, 1, 0, mainPos).equals(worldPosition)) {
                reactorTile.handleFuelCellHolder(itemHandler, 0, false);
            } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, -1, 0, 0, mainPos).equals(worldPosition)) {
                reactorTile.handleFuelCellHolder(itemHandler, 1, false);
            } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 0, -1, 0, mainPos).equals(worldPosition)) {
                reactorTile.handleFuelCellHolder(itemHandler, 2, false);
            } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 1, 0, 0, mainPos).equals(worldPosition)) {
                reactorTile.handleFuelCellHolder(itemHandler, 3, false);
            }
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    public void cycleLocking() {
        this.locked = !this.locked;
    }

    public ItemStack getFuelCell() {
        return this.itemHandler.getStackInSlot(0);
    }

    public boolean hasFuelCell() {return !this.itemHandler.getStackInSlot(0).isEmpty();}

    public boolean isLocked() {return locked;}
}
