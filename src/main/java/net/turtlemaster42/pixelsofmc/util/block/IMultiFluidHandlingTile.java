package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IMultiFluidHandlingTile {
    FluidTank[] getFluidTanks();
    FluidTank getFluidTank(String name);
    String[] getFluidTankNames();
}
