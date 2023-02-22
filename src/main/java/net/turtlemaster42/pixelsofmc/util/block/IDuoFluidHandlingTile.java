package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.fluids.FluidStack;

public interface IDuoFluidHandlingTile {
    void setDuoFluid(FluidStack fluid);
    FluidStack getDuoFluid();
}
