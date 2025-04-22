package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface IQuadFluidHandlingTile {
    void setQuadFluid(FluidStack fluid);
    FluidStack getQuadFluid();
}
