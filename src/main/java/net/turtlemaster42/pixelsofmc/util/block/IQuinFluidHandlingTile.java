package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface IQuinFluidHandlingTile {
    void setQuinFluid(FluidStack fluid);
    FluidStack getQuinFluid();
}
