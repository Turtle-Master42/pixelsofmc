package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class InfinitePixelEnergyItemProvider implements ICapabilityProvider {

    private ItemStack stack;
    private InfiniteNumber capacity;
    private int maxTransfer;
    private final LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new InfinitePixelEnergyItemHandler(stack, capacity, maxTransfer));

    public InfinitePixelEnergyItemProvider(ItemStack stack, InfiniteNumber capacity, int maxTransfer) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
