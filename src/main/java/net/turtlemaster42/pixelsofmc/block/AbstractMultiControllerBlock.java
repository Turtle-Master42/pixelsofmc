package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMultiControllerBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);
    protected AbstractMultiControllerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ACTIVE);
    }

    @Override
    @javax.annotation.Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, 1);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide())
            invalidateMultiBlock(pLevel, pPos);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return null;
    }

    // --- Multi Block --- //

    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you
    protected final BlockState[][][] MULTIBLOCK_STRUCTURE = getMultiblockStructure();

    public BlockState[][][] getMultiblockStructure() {
        return new BlockState[][][]{{{}}};
    }
    public int getHeight() {return 0;}
    public int getWidth() {return 0;}
    public int getLength() {return 0;}

//    public void oldValidateMultiBlock(Level level, BlockPos controllerPos) {
//        if (level.isClientSide()) return;
//
//        BlockState controllerState = level.getBlockState(controllerPos);
//        Direction direction = controllerState.getValue(FACING);
//        int correctBlocks =0;
//        int totalBlocks = 0;
//
//        for (int y = 0; y < getHeight(); y++) {
//            for (int z = 0; z < getLength(); z++) {
//                for (int x = 0; x < getWidth(); x++) {
//                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
//                    totalBlocks++;
//
//                    BlockPos blockPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
//                    BlockState blockState = level.getBlockState(blockPos);
//
//                    if (blockState.getBlock() == MULTIBLOCK_STRUCTURE[y][z][x].getBlock()) {
//                        if (blockState.hasProperty(AbstractFusionCasing.PLATING)) {
//                            BlockState changedState = blockState.setValue(AbstractFusionCasing.PLATING, 0);
//                            if (changedState == MULTIBLOCK_STRUCTURE[y][z][x]) {
//                                correctBlocks++;
//                            }
//                        } else if (blockState == MULTIBLOCK_STRUCTURE[y][z][x]) {
//                            correctBlocks++;
//                        }
//                    } else if (MULTIBLOCK_STRUCTURE[y][z][x].is(POMblocks.REINFORCED_GLASS.get())) {
//                        if (blockState.getBlock() instanceof AbstractFusionCasing) {
//                            correctBlocks++;
//                        }
//                    }
//                    if (blockState.getBlock() instanceof AbstractMultiBlock) {
//                        if (level.getBlockEntity(blockPos) instanceof AbstractMultiBlockTile multiBlockTile)
//                            multiBlockTile.setMainPos(controllerPos);
//                    }
//                }
//
//            }
//
//        }
//        PixelsOfMc.LOGGER.info("{} out of {}", correctBlocks, totalBlocks);
//        if (correctBlocks == totalBlocks) {
//            if (controllerState.getValue(ACTIVE) != 3)
//                level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
//        }  else {
//            invalidateMultiBlock(level, controllerPos);
//        }
//    }

    public void validateMultiBlock(Level level, BlockPos controllerPos) {
        if (level.isClientSide()) return;
        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = controllerState.getValue(FACING);
        int correctBlocks =0;
        int totalBlocks = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    BlockState multiBlockState = MULTIBLOCK_STRUCTURE[y][z][x];
                    if (multiBlockState == null) continue;
                    totalBlocks++;

                    BlockPos blockPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
                    BlockState blockState = level.getBlockState(blockPos);
                    Block block = blockState.getBlock();

                    if (multiBlockState.is(POMblocks.REINFORCED_GLASS.get()) && blockState.getBlock() instanceof AbstractMultiBlock) {
                        correctBlocks++;
                        continue;
                    }

                    if (multiBlockState.hasProperty(BlockStateProperties.FACING) && blockState.hasProperty(BlockStateProperties.FACING)) {
                        if (blockState.getValue(BlockStateProperties.FACING) == multiBlockState.getValue(BlockStateProperties.FACING)) {
                            correctBlocks++;
                        }
                    } else if (multiBlockState.hasProperty(BlockStateProperties.AXIS) && blockState.hasProperty(BlockStateProperties.AXIS)) {
                        if (blockState.getValue(BlockStateProperties.AXIS) == multiBlockState.getValue(BlockStateProperties.AXIS)) {
                            correctBlocks++;
                        }
                    } else if (multiBlockState.is(block)) {
                        correctBlocks++;
                    }

                    if (blockState.getBlock() instanceof AbstractMultiBlock) {
                        if (level.getBlockEntity(blockPos) instanceof AbstractMultiBlockTile multiBlockTile)
                            multiBlockTile.setMainPos(controllerPos);
                    }
                }

            }

        }
        PixelsOfMc.LOGGER.info("{} out of {}", correctBlocks, totalBlocks);
        if (correctBlocks == totalBlocks) {
            if (controllerState.getValue(ACTIVE) != 3)
                level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
        }  else {
            invalidateMultiBlock(level, controllerPos);
        }
    }

    public void invalidateMultiBlock(Level level, BlockPos controllerPos) {
        if (level.isClientSide()) return;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = controllerState.getValue(FACING);
        int totalBlocks = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;

                    BlockPos rotatedOffsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);

                    if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractMultiBlock) {
                        if (level.getBlockEntity(rotatedOffsetPos) instanceof AbstractMultiBlockTile multiBlockTile) {
                            multiBlockTile.setMainPos(rotatedOffsetPos);
                            totalBlocks++;
                        }
                    }
                }

            }

        }
        level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 1), 2);
        PixelsOfMc.LOGGER.info("invalidated {} blocks", totalBlocks);
    }

    public void forcePlaceMultiBlock(Level level, BlockPos controllerPos) {
        if (level.isClientSide()) return;
        Direction direction = level.getBlockState(controllerPos).getValue(FACING);
        int totalBlocks = 0;
        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;
                    level.setBlock(rotatedOffsetBlock(direction, x, y, z, controllerPos), MULTIBLOCK_STRUCTURE[y][z][x], 2);
                }
            }
        }
        PixelsOfMc.LOGGER.info("placed {} blocks", totalBlocks);
    }

    public BlockPos rotatedOffsetBlock (Direction direction, int x, int y, int z, BlockPos startPos) {
        return offsetMultiBlock(BigMachineBlockUtil.rotateBlockPosOnDirection(direction, x, y, z, startPos), direction);
    }

    public BlockPos rotatedOffsetBlock (Direction direction, BlockPos offset, BlockPos startPos) {
        return rotatedOffsetBlock(direction, offset.getX(), offset.getY(), offset.getZ(), startPos);
    }

    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, 0, 0, 0, pos);
    }
    public BlockPos offsetMultiBlock(int x, int y, int z, Direction direction) {
        return offsetMultiBlock(new BlockPos(x,y,z), direction);
    }
}
