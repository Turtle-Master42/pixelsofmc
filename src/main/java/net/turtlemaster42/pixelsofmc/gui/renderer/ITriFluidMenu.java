package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public interface ITriFluidMenu {
    void setTriFluid(FluidStack fluidStack);
    FluidStack getTriFluid();
    BlockEntity getBlockEntity();
}
