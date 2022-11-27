package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PixelItemStackHandler extends ItemStackHandler implements ICapabilityProvider
{
    public PixelItemStackHandler()
    {
        this(1);
    }
    public PixelItemStackHandler(int size)
    {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public boolean isValidOutput(int slot) {
        return true;
    }



    //Credits ImmersiveEngineering
    private final LazyOptional<IItemHandler> thisOpt = constantOptional(this);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, thisOpt);
    }

    public static <T> LazyOptional<T> constantOptional(T val)
    {
        LazyOptional<T> result = LazyOptional.of(() -> Objects.requireNonNull(val));
        // Resolve directly: There is currently a bug in the LO resolve code that can cause problems in multithreaded
        // contexts ("resolved" is set to a reference to null during the resolution on one thread, any other thread
        // trying to access the value will get null)
        result.resolve();
        return result;
    }
}
