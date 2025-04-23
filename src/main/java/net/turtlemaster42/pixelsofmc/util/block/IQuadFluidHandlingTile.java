package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IQuadFluidHandlingTile {
    void setQuadFluid(FluidStack fluid);
    FluidStack getQuadFluid();
    FluidTank getQuadFluidTank();
}
