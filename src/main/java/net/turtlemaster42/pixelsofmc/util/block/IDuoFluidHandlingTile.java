package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IDuoFluidHandlingTile {
    void setDuoFluid(FluidStack fluid);
    FluidStack getDuoFluid();
    FluidTank getDuoFluidTank();
}
