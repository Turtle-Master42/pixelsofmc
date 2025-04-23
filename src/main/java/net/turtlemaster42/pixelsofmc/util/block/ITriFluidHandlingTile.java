package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface ITriFluidHandlingTile {
    void setTriFluid(FluidStack fluid);
    FluidStack getTriFluid();
    FluidTank getTriFluidTank();
}
