package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.BlockPos;

public interface IMultiControllerBlock {
    GhostBlockState[][][] getMultiblockStructure();
    int getHeight();
    int getWidth();
    int getLength();
}
