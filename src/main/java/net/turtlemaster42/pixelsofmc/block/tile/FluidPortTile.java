package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;

public class FluidPortTile extends AbstractFluidPortTile implements IFluidHandlingTile, IDuoFluidHandlingTile {

    public FluidPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.FLUID_PORT.get(), pWorldPosition, pBlockState);
    }

}
