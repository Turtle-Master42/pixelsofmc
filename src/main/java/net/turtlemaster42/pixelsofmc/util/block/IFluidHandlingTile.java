package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IFluidHandlingTile {
    void setFluid(FluidStack fluid);
    FluidStack getFluid();
    FluidTank getFluidTank();
}
