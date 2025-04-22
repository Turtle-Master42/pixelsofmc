package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface ITriFluidHandlingTile {
    void setTriFluid(FluidStack fluid);
    FluidStack getTriFluid();
}
