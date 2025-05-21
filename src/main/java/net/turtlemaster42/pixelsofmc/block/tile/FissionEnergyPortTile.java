package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;

public class FissionEnergyPortTile extends AbstractEnergyPortTile implements IEnergyHandlingTile {


    public FissionEnergyPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.ENERGY_PORT.get(), pWorldPosition, pBlockState);
    }

}
