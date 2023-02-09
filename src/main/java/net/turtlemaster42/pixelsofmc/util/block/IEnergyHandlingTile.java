package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyHandlingTile {
    void setEnergyLevel(int energyLevel);
    IEnergyStorage getEnergyStorage();
}
