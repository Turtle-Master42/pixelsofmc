package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FuelCellHolderBlock extends AbstractMultiBlock {
    public static final IntegerProperty CELL_TYPE = IntegerProperty.create("cell_type", 0, 2);
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
}
