package net.turtlemaster42.pixelsofmc.network;

import net.minecraftforge.energy.EnergyStorage;

public class PixelEnergyStorage extends EnergyStorage {

    public PixelEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, 0);
    }
    public PixelEnergyStorage(int capacity, int maxTransfer, int maxExtract) {
        super(capacity, maxTransfer, maxExtract);
    }

    // Override this to (for example) call setChanged() on your block entity
    protected void onEnergyChanged() {
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int rc = super.receiveEnergy(maxReceive, simulate);
        if (rc > 0 && !simulate) {
            onEnergyChanged();
        }
        return rc;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int rc = super.extractEnergy(maxExtract, simulate);
        if (rc > 0 && !simulate) {
            onEnergyChanged();
            return rc;
        }
        return rc;
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }
}
