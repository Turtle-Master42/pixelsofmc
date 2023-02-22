package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public interface IDuoFluidMenu {
    void setDuoFluid(FluidStack fluidStack);
    FluidStack getDuoFluid();
    BlockEntity getBlockEntity();
}
