package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface IHexaFluidHandlingTile {
    void setHexaFluid(FluidStack fluid);
    FluidStack getHexaFluid();
}
