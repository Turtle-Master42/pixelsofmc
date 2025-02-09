package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;

public class PixelFluidItemHandlerSimple extends FluidHandlerItemStack {
    public PixelFluidItemHandlerSimple(@NotNull ItemStack container, int capacity)
    {
        super(container, capacity);
    }

    @Override
    public int fill(@NotNull FluidStack resource, FluidAction action) {
        if (resource.getAmount() >= 1000)
            return super.fill(new FluidStack(resource.getFluid(), 1000), action);
        else
            return 0;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (maxDrain >= 1000)
            return super.drain(1000, action);
        else
            return FluidStack.EMPTY;
    }
}
