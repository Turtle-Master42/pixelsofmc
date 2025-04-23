package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IHexaFluidHandlingTile {
    void setHexaFluid(FluidStack fluid);
    FluidStack getHexaFluid();
    FluidTank getHexaFluidTank();
}
