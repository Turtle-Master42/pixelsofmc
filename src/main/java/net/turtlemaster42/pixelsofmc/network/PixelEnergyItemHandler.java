package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PixelEnergyItemHandler extends PixelEnergyStorage {

    @NotNull
    protected ItemStack stack;


    public PixelEnergyItemHandler(@NotNull ItemStack stack, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.stack = stack;
        this.energy = stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("Energy") ? stack.getTag().getInt("Energy") : 0;
    }

    @NotNull
    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int rc = super.receiveEnergy(maxReceive, simulate);
        if (rc > 0 && !simulate) {
            stack.getOrCreateTag().putInt("Energy", this.energy);
            onEnergyChanged();
        }
        return rc;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int rc = super.extractEnergy(maxExtract, simulate);
        if (rc > 0 && !simulate) {
            stack.getOrCreateTag().putInt("Energy", this.energy);
            onEnergyChanged();
            return rc;
        }
        return rc;
    }

    @Override
    public int setEnergy(int energy) {
        this.energy = energy;
        stack.getOrCreateTag().putInt("Energy", this.energy);
        return energy;
    }

    @Override
    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        stack.getOrCreateTag().putInt("Energy", this.energy);
        onEnergyChanged();
    }

    @Override
    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        stack.getOrCreateTag().putInt("Energy", this.energy);
        onEnergyChanged();
    }
}
