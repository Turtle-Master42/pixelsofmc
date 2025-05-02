package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.FuelCellHolderTile;
import net.turtlemaster42.pixelsofmc.block.tile.NuclearReactorTile;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuelCellHolderBlock extends AbstractMultiBlock {
    public static final IntegerProperty CELL_TYPE = IntegerProperty.create("cell_type", 0, 1);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public FuelCellHolderBlock(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CELL_TYPE, FACING);
        super.createBlockStateDefinition(pBuilder);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(CELL_TYPE, 0)
                .setValue(FACING, pContext.getNearestLookingDirection().equals(Direction.UP) || pContext.getNearestLookingDirection().equals(Direction.DOWN) ? pContext.getNearestLookingDirection().getOpposite() : pContext.getNearestLookingDirection());
    }

    // received redstone signal
    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        if (pLevel.isClientSide || !pLevel.hasNeighborSignal(pPos)) {
            return;
        }
        pLevel.setBlock(pPos, pState.cycle(FuelCellHolderBlock.CELL_TYPE), 2);
        if (pLevel.getBlockEntity(pPos) instanceof FuelCellHolderTile fuelCellTile) {
            fuelCellTile.cycleLocking();
            BlockPos mainPos = fuelCellTile.getMainPos();
            if (pLevel.getBlockEntity(mainPos) instanceof NuclearReactorTile reactorTile) {
                Direction mainDirection = pLevel.getBlockState(mainPos).getValue(NuclearReactorBlock.FACING);
                if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 0, 1, 0, mainPos).equals(pPos)) { //TODO: Needs to be made more pretty
                    reactorTile.handleFuelCellHolder(fuelCellTile.getItemStackHandler(), 0, fuelCellTile.isLocked());
                } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, -1, 0, 0, mainPos).equals(pPos)) {
                    reactorTile.handleFuelCellHolder(fuelCellTile.getItemStackHandler(), 1, fuelCellTile.isLocked());
                } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 0, -1, 0, mainPos).equals(pPos)) {
                    reactorTile.handleFuelCellHolder(fuelCellTile.getItemStackHandler(), 2, fuelCellTile.isLocked());
                } else if (BigMachineBlockUtil.rotateBlockPosOnDirection(mainDirection, 1, 0, 0, mainPos).equals(pPos)) {
                    reactorTile.handleFuelCellHolder(fuelCellTile.getItemStackHandler(), 3, fuelCellTile.isLocked());
                }
            }
        }

    }


    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pHand.equals(InteractionHand.OFF_HAND))
            return InteractionResult.PASS;

        ItemStack handItem = pPlayer.getItemInHand(pHand);
        if (pLevel.getBlockEntity(pPos) instanceof FuelCellHolderTile fuelCellTile) {
            PixelItemStackHandler cellItemHandler = (PixelItemStackHandler) fuelCellTile.getItemStackHandler();
            if (fuelCellTile.isLocked() || !fuelCellTile.isMainPosValid())
                return InteractionResult.PASS;

            if (handItem.isEmpty()) {
                if (cellItemHandler.extractItem(0, 1, true).isEmpty()) {
                    return InteractionResult.PASS;
                }
                if (!pLevel.isClientSide()) {
                    pPlayer.setItemInHand(pHand, cellItemHandler.extractItem(0, 1, false));
                    return InteractionResult.SUCCESS;
                }
            } else {
                if (cellItemHandler.getStackInSlot(0).isEmpty()) {
                    if (!pLevel.isClientSide()) {
                        ItemStack newStack = handItem.copy();
                        newStack.setCount(1);
                        cellItemHandler.insertItem(0, newStack, false);
                        pPlayer.setItemInHand(pHand, new ItemStack(handItem.getItem(), handItem.getCount() - 1, handItem.getTag()));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new FuelCellHolderTile(pPos, pState);
    }
}
