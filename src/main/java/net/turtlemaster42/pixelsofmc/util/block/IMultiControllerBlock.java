package net.turtlemaster42.pixelsofmc.util.block;

public interface IMultiControllerBlock {
    GhostBlockState[][][] getMultiblockStructure();
    int getHeight();
    int getWidth();
    int getLength();
}
