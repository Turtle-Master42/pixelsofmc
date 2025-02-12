package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.BlockPos;

public interface IFusionControllerBlock extends IMultiControllerBlock {
    BlockPos fusionStarPos();
    int fusionStarLevel();
}
