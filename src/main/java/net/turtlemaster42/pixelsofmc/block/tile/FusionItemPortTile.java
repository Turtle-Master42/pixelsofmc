package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;

public class FusionItemPortTile extends AbstractItemPortTile {

    public FusionItemPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.FUSION_ITEM_PORT.get(), pWorldPosition, pBlockState);
    }

}
