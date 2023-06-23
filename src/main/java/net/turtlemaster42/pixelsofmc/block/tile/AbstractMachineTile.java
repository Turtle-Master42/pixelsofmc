package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInventoryHandlingTile;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class AbstractMachineTile<Tile extends BlockEntity> extends BlockEntity implements MenuProvider, IInventoryHandlingTile, IEnergyHandlingTile, IFluidHandlingTile {

    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    protected final ItemStackHandler itemHandler = new PixelItemStackHandler(itemHandlerSize()) {
        @Override
        protected void onContentsChanged(int slot) {
            if (level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
            contentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return isInputValid(slot, stack);
        }

        @Override
        public boolean isValidOutput(int slot) {
            return isSlotValidOutput(slot);
        }
    };

    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }
    protected boolean isSlotValidOutput(int slot) {
        return true;
    }
    protected int itemHandlerSize() {return 1;}
    protected void contentsChanged(int slot) {}

    @Override
    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    protected void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);
        return compound;
    }

    // -- CRAFTING -- //

    public ItemStack insertItemStack(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        ItemStack existing = this.itemHandler.getStackInSlot(slot);
        int limit =  Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize());
        boolean limitReached = existing.getCount() + stack.getCount() > limit;

        if (!limitReached && !simulate)
            itemHandler.setStackInSlot(slot, new ItemStack(stack.getItem(), existing.getCount()+stack.getCount()));
        if (limitReached) {
            if (!simulate) {
                itemHandler.setStackInSlot(slot, new ItemStack(stack.getItem(), stack.getMaxStackSize()));
            }
            return new ItemStack(stack.getItem(), existing.getCount()+stack.getCount()-stack.getMaxStackSize());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setEnergyLevel(int energyLevel) {}

    @Override
    public PixelEnergyStorage getEnergyStorage() {
        return null;
    }

    @Override
    public void setFluid(FluidStack fluid) {}

    @Override
    public FluidStack getFluid() {
        return null;
    }
}
