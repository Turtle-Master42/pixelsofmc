package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public interface IHexaFluidMenu {
    void setHexaFluid(FluidStack fluidStack);
    FluidStack getHexaFluid();
    BlockEntity getBlockEntity();
}
