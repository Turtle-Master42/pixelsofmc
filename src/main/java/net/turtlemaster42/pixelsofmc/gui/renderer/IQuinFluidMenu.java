package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public interface IQuinFluidMenu {
    void setQuinFluid(FluidStack fluidStack);
    FluidStack getQuinFluid();
    BlockEntity getBlockEntity();
}
