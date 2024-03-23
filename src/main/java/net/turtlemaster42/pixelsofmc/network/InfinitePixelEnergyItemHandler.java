package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class InfinitePixelEnergyItemHandler extends InfinitePixelEnergyStorage {

    @NotNull
    protected ItemStack stack;


    public InfinitePixelEnergyItemHandler(@NotNull ItemStack stack, InfiniteNumber capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer);
        this.stack = stack;
        this.energy = stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("Energy") ? stack.getTag().getInt("Energy") : 0;
        this.infiniteEnergy = stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("InfiniteEnergy") ? new InfiniteNumber().fromString(stack.getTag().getString("InfiniteEnergy")) : new InfiniteNumber().fromInt(0);
    }


    @NotNull
    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    protected void updateEnergy() {
        super.updateEnergy();
        stack.getOrCreateTag().putInt("Energy", this.energy);
        stack.getOrCreateTag().putString("InfiniteEnergy", this.infiniteEnergy.toString());
    }
}
