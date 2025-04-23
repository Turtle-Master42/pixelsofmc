package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IQuinFluidHandlingTile {
    void setQuinFluid(FluidStack fluid);
    FluidStack getQuinFluid();
    FluidTank getQuinFluidTank();
}
