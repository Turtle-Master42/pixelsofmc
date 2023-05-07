package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IFusionControllerBlock {
    BlockState[][][] getMultiblockStructure();
    int getHeight();
    int getWidth();
    int getLength();
    BlockPos fusionStarPos();
    int fusionStarLevel();
}
