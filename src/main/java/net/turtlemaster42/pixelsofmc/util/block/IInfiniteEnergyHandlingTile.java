package net.turtlemaster42.pixelsofmc.util.block;

import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;

public interface IInfiniteEnergyHandlingTile {
    void setEnergyLevel(InfiniteNumber energyLevel);
    InfinitePixelEnergyStorage getEnergyStorage();
}
