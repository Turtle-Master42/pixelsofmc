package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidHandlingTile {
    void setFluid(FluidStack fluid);
    FluidStack getFluid();
}
