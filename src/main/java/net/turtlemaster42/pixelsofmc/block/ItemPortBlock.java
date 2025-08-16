package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.EnergyPortTile;
import net.turtlemaster42.pixelsofmc.block.tile.FluidPortTile;
import net.turtlemaster42.pixelsofmc.block.tile.ItemPortTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInfiniteEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInventoryHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlock extends AbstractPort {
    public ItemPortBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ItemPortTile(pPos, pState);
    }

    @Override
    public boolean hasComparatorOutput(BlockState pState) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof ItemPortTile portTile) {
            BlockEntity mainTile = pLevel.getBlockEntity(portTile.getMainPos());
            if (mainTile == null || !portTile.isMainPosValid()) {return 0;}
            else if (mainTile instanceof IInventoryHandlingTile inventory) {
                int slots = inventory.getItemStackHandler().getSlots();
                float partial = 0f;
                // iterate over slots
                for (int i = 0; i < slots; i++) {
                    ItemStack stack = inventory.getItemStackHandler().getStackInSlot(i);
                    // add percentage per slot
                    partial += ((float) stack.getCount()) / ((float) stack.getMaxStackSize());
                }
                if (partial == 0f) {return 0;}
                return 1 + Math.round(partial / slots * 14f); // partial
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(AbstractPort.PUSHING)) {
            return createTickerHelper(pBlockEntityType, POMtiles.ITEM_PORT.get(),
                    pLevel.isClientSide ? ItemPortTile::clientTick : ItemPortTile::serverTick);
        }
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, POMtiles.ITEM_PORT.get(), ItemPortTile::idleTick);
    }
}
