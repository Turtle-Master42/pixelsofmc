package net.turtlemaster42.pixelsofmc.network;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;

public class InfinitePixelEnergyStorage extends PixelEnergyStorage {

    protected InfiniteNumber infiniteEnergy;
    protected final InfiniteNumber infiniteCapacity;

    public InfinitePixelEnergyStorage(InfiniteNumber capacity, int maxTransfer) {
        super(capacity.toInt(), maxTransfer);
        this.infiniteCapacity = capacity;
        this.infiniteEnergy = new InfiniteNumber().fromInt(0);
    }

    public InfinitePixelEnergyStorage(InfiniteNumber capacity, int maxTransfer, int maxExtract) {
        super(capacity.toInt(), maxTransfer, maxExtract);
        this.infiniteCapacity = capacity;
        this.infiniteEnergy = new InfiniteNumber().fromInt(0);
    }

    protected void updateEnergy() {
        InfiniteNumber tempInfEnergy = infiniteEnergy.copy();
        tempInfEnergy.subtract(infiniteCapacity.copy());
        this.energy = Math.max(Integer.MAX_VALUE + Math.min(tempInfEnergy.toInt(), 0), 0);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

//        updateEnergy();
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if (energyReceived > 0 && !simulate) {
            infiniteEnergy.add(energyReceived);
            updateEnergy();
            onEnergyChanged();
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

//        updateEnergy();
        int energyExtracted = Math.min(infiniteEnergy.toInt(), Math.min(this.maxExtract, maxExtract));

        if (energyExtracted > 0 && !simulate) {
            infiniteEnergy.subtract(energyExtracted);
            updateEnergy();
            onEnergyChanged();
        }
        return energyExtracted;
    }

    public InfiniteNumber setInfiniteEnergy(int energy) {
        this.infiniteEnergy = new InfiniteNumber().fromInt(energy);
        updateEnergy();
        return infiniteEnergy;
    }

    public InfiniteNumber setInfiniteEnergy(long energy) {
        this.infiniteEnergy = new InfiniteNumber().fromLong(energy);
        updateEnergy();
        return infiniteEnergy;
    }

    public InfiniteNumber setInfiniteEnergy(InfiniteNumber energy) {
        this.infiniteEnergy = energy.copy();
        updateEnergy();
        return infiniteEnergy;
    }

    public void addEnergy(int energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isBiggerThen(infiniteCapacity)) {
            this.infiniteEnergy = infiniteCapacity.copy();
        }
        updateEnergy();
        onEnergyChanged();
    }

    public void addEnergy(long energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isBiggerThen(infiniteCapacity)) {
            this.infiniteEnergy = infiniteCapacity.copy();
        }
        updateEnergy();
        onEnergyChanged();
    }

    public void addEnergy(InfiniteNumber energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isBiggerThen(infiniteCapacity)) {
            this.infiniteEnergy = infiniteCapacity.copy();
        }
        updateEnergy();
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isNegative()) {
            this.infiniteEnergy = new InfiniteNumber().fromInt(0);
        }
        updateEnergy();
        onEnergyChanged();
    }

    public void consumeEnergy(long energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isNegative()) {
            this.infiniteEnergy = new InfiniteNumber().fromInt(0);
        }
        updateEnergy();
        onEnergyChanged();
    }

    public void consumeEnergy(InfiniteNumber energy) {
        this.infiniteEnergy.add(energy);
        if (this.infiniteEnergy.isNegative()) {
            this.infiniteEnergy = new InfiniteNumber().fromInt(0);
        }
        updateEnergy();
        onEnergyChanged();
    }

    public InfiniteNumber getInfiniteEnergy() {
        return infiniteEnergy;
    }

    public InfiniteNumber getInfiniteCapacity() {
        return infiniteCapacity;
    }
}

