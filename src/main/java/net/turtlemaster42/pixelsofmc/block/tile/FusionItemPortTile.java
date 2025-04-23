package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.block.AbstractFusionPort;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;

public class FusionItemPortTile extends AbstractMultiBlockTile {

    private int itemCooldown = 0;

    public FusionItemPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.FUSION_ITEM_PORT.get(), pWorldPosition, pBlockState);
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
            ItemStack insertSim = stack;
            if (!level.getBlockState(worldPosition).getValue(AbstractFusionPort.MODE).equals(2)) {

                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition)) {
                    return stack;
                }

                BlockEntity tile = level.getBlockEntity(posTarget);
                if (tile != null) {
                    IItemHandler ItemHandlerFrom = tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP.getOpposite()).orElse(null);
                    if (ItemHandlerFrom != null) {
                        if (slot > ItemHandlerFrom.getSlots() - 1 || !ItemHandlerFrom.isItemValid(slot, stack))
                            return stack;
                        itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
                        insertSim = ItemHandlerFrom.insertItem(slot, stack, true);
                        if (insertSim == stack)
                            return stack;
                        if (!simulate) {
                            stack.setCount(stack.getCount() - insertSim.getCount());
                            ItemHandlerFrom.insertItem(slot, stack, false);//nbt still not working

                            itemHandler.setStackInSlot(slot, ItemHandlerFrom.getStackInSlot(slot));
                        }
                    }
                }
            }
            return insertSim;
        }
        @Override
        @Nonnull
        public ItemStack extractItem(int slot,int amount, boolean simulate) {
            if (!level.getBlockState(worldPosition).getValue(AbstractFusionPort.MODE).equals(1)) {
                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition)) {
                    return ItemStack.EMPTY;
                }
                BlockEntity tile = level.getBlockEntity(posTarget);
                if (tile != null) {
                    PixelItemStackHandler itemHandlerFrom = (PixelItemStackHandler) tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP.getOpposite()).orElse(null);
                    if (itemHandlerFrom != null) {
                        if (slot >= itemHandlerFrom.getSlots())
                            return ItemStack.EMPTY;
                        if (!itemHandlerFrom.isValidOutput(slot))
                            return ItemStack.EMPTY;
                        if (tile instanceof AbstractMachineTile<?> machineTile)
                            if (machineTile.itemHandlerSize() < slot)
                                return ItemStack.EMPTY;
                        itemHandler.setStackInSlot(slot, itemHandlerFrom.getStackInSlot(slot));
                        itemHandlerFrom.extractItem(slot, amount, simulate);
                        return super.extractItem(slot, amount, simulate);
                    }
                }
            }
            return ItemStack.EMPTY;
        }
    };

    public boolean isEmpty(ItemStackHandler itemHandler) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

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

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FusionItemPortTile e) {
        if (blockState.getValue(AbstractFusionPort.PUSHING) && e.isMainPosValid()) {
            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(AbstractFusionPort.PUSH_DIRECTION), 0, 0, 1, blockPos);
            BlockState facingState = level.getBlockState(facingPos);
            e.itemCooldown++;
            if (e.itemCooldown >= 4) {
                e.itemCooldown = 0;
                if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                    if (level.getBlockEntity(e.getMainPos()) instanceof AbstractMachineTile<?> tile) {
                        for (int i = 0; i < tile.itemHandler.getSlots(); i++) {
                            if (tile.isSlotValidOutput(i) && !tile.itemHandler.getStackInSlot(i).isEmpty()) {
                                ItemStack outStack = tile.itemHandler.extractItem(i, 1, false);
                                if (outStack.isEmpty())
                                    return;

                                Vector3f centerVec = new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                                Vector3f posVec = rotatedVecPos(blockState.getValue(AbstractFusionPort.PUSH_DIRECTION), centerVec, 0, 0, 0.8f);
                                Vector3f launchVec = rotatedVecPos(blockState.getValue(AbstractFusionPort.PUSH_DIRECTION), new Vector3f(0), 0, 0, 0.2f);
                                ItemEntity itementity = new ItemEntity(level, posVec.x, posVec.y, posVec.z, outStack);
                                itementity.setDeltaMovement(launchVec.x, launchVec.y, launchVec.z);
                                level.addFreshEntity(itementity);
                                break;
                            }
                        }
                    }
                } else {
                    BlockEntity facingTile = level.getBlockEntity(facingPos);
                    if (facingTile != null) {
                        IItemHandler itemStackHandler = facingTile.getCapability(ForgeCapabilities.ITEM_HANDLER, blockState.getValue(AbstractFusionPort.PUSH_DIRECTION).getOpposite()).orElse(null);
                        if (itemStackHandler != null && level.getBlockEntity(e.getMainPos()) instanceof AbstractMachineTile<?> tile) {
                            for (int inSlot = 0; inSlot < tile.itemHandler.getSlots(); inSlot++) {
                                if (!tile.isSlotValidOutput(inSlot)) {
                                    continue;
                                }
                                for (int outSlot = 0; outSlot < itemStackHandler.getSlots(); outSlot++) {
                                    ItemStack outStack = tile.itemHandler.extractItem(inSlot, 1, true);
                                    if (!outStack.isEmpty()) {
                                        ItemStack remainStack = itemStackHandler.insertItem(outSlot, outStack, false);
                                        if (remainStack != outStack) {
                                            tile.itemHandler.extractItem(inSlot, 1, false);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, FusionItemPortTile e) {
    }
}
