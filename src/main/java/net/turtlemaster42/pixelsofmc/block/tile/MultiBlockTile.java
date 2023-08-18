package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;

public class MultiBlockTile extends AbstractMultiBlockTile {
    public MultiBlockTile(BlockPos pPos, BlockState pBlockState) {
        super(POMtiles.MULTIBLOCK.get(), pPos, pBlockState);
    }
}
