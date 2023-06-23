package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.IFusionControllerBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractFusionControllerBlock extends BaseEntityBlock implements IFusionControllerBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);
    protected AbstractFusionControllerBlock(Properties pProperties) {
        super(pProperties);
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
    private final BlockState[][][] MULTIBLOCK_STRUCTURE = getMultiblockStructure();

    public BlockState[][][] getMultiblockStructure() {
        return new BlockState[][][]{{{}}};
    }
    public int getHeight() {return 0;}
    public int getWidth() {return 0;}
    public int getLength() {return 0;}

    public void validateMultiBlock(Level level, BlockPos controllerPos) {

        if (level.isClientSide()) return;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = controllerState.getValue(FACING);
        int correctBlocks =0;
        int totalBlocks = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;

                    BlockPos rotatedOffsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);

                    if (level.getBlockState(rotatedOffsetPos) == MULTIBLOCK_STRUCTURE[y][z][x]) {
                        correctBlocks++;
                    } else if (MULTIBLOCK_STRUCTURE[y][z][x].is(POMblocks.REINFORCED_GLASS.get())) {
                        if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractFusionCasing) {
                            correctBlocks++;
                        }
                    }
                    if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractMultiBlock) {
                        if (level.getBlockEntity(rotatedOffsetPos) instanceof AbstractMultiBlockTile multiBlockTile)
                            multiBlockTile.setMainPos(controllerPos);
                    }
                }

            }

        }
        PixelsOfMc.LOGGER.info("{} out of {}", correctBlocks, totalBlocks);
        if (correctBlocks == totalBlocks) {
            if (controllerState.getValue(ACTIVE) != 3)
                level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
            level.setBlock(rotatedOffsetBlock(direction, fusionStarPos(), controllerPos), POMblocks.STAR.get().defaultBlockState().setValue(StarBlock.STAR_STAGE, fusionStarLevel()), 2);
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
        level.destroyBlock(rotatedOffsetBlock(direction, fusionStarPos(), controllerPos), false);
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

    public BlockPos fusionStarPos() {return new BlockPos(0, 0, 0);}
    public int fusionStarLevel() {return 1;}

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
