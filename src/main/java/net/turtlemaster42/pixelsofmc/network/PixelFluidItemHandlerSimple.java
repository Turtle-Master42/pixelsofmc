package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.jetbrains.annotations.NotNull;

public class PixelFluidItemHandlerSimple extends FluidHandlerItemStack {
    public PixelFluidItemHandlerSimple(@NotNull ItemStack container, int capacity)
    {
        super(container, capacity);
    }

    @Override
    public int fill(@NotNull FluidStack resource, FluidAction action) {
        return super.fill(new FluidStack(resource.getFluid(), (int) Math.floor(resource.getAmount()/1000d) * 1000), action);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return super.drain((int) Math.floor(maxDrain/1000d) * 1000, action);
    }
}
