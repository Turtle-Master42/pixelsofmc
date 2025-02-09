package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractFusionControllerTile;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.IFusionControllerBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFusionControllerBlock extends AbstractMultiControllerBlock implements IFusionControllerBlock {
    protected AbstractFusionControllerBlock(Properties pProperties) {
        super(pProperties);
    }

    // --- Multi Block --- //
    public void validateMultiBlock(Level level, BlockPos controllerPos) {

        if (level.isClientSide()) return;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = controllerState.getValue(FACING);
        int correctBlocks =0;
        int totalBlocks = 0;
        int heatSinkCount = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;

                    BlockPos rotatedOffsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
                    BlockState rotatedBlockState = level.getBlockState(rotatedOffsetPos);

                    if (rotatedBlockState.getBlock() == MULTIBLOCK_STRUCTURE[y][z][x].getBlock()) {
                        if (rotatedBlockState.hasProperty(AbstractFusionCasing.PLATING)) {
                            BlockState changedState = rotatedBlockState.setValue(AbstractFusionCasing.PLATING, 0);
                            if (changedState == MULTIBLOCK_STRUCTURE[y][z][x]) {
                                correctBlocks++;
                            }
                        } else if (rotatedBlockState == MULTIBLOCK_STRUCTURE[y][z][x]) {
                            correctBlocks++;
                        }
                    } else if (MULTIBLOCK_STRUCTURE[y][z][x].is(POMblocks.REINFORCED_GLASS.get())) {
                        if (rotatedBlockState.getBlock() instanceof AbstractFusionCasing) {
                            correctBlocks++;
                        }
                    }
                    if (rotatedBlockState.getBlock() instanceof AbstractMultiBlock) {
                        if (level.getBlockEntity(rotatedOffsetPos) instanceof AbstractMultiBlockTile multiBlockTile)
                            multiBlockTile.setMainPos(controllerPos);
                    }
                    if (rotatedBlockState.getBlock().equals(POMblocks.HEAT_SINK.get())) {
                        heatSinkCount++;
                    }
                }

            }

        }
        PixelsOfMc.LOGGER.info("{} out of {}", correctBlocks, totalBlocks);
        PixelsOfMc.LOGGER.info("Heat Sinks: {}", heatSinkCount);
        if (correctBlocks == totalBlocks) {
            if (controllerState.getValue(ACTIVE) != 3)
                level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
            level.setBlock(rotatedOffsetBlock(direction, fusionStarPos(), controllerPos), POMblocks.STAR.get().defaultBlockState().setValue(StarBlock.STAR_STAGE, fusionStarLevel()), 2);

            if (level.getBlockEntity(controllerPos) instanceof SDSFusionControllerTile fusionControllerTile) {
                fusionControllerTile.setHeatSinkAmount(heatSinkCount);
            }

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

    public BlockPos fusionStarPos() {return new BlockPos(0, 0, 0);}
    public int fusionStarLevel() {return 1;}
}
