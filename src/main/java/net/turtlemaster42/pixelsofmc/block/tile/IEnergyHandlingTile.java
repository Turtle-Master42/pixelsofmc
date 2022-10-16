package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import org.jetbrains.annotations.NotNull;

public interface IEnergyHandlingTile {
    @NotNull PixelEnergyStorage createEnergyStorage();
    void setEnergyLevel(int energyLevel);
    IEnergyStorage getEnergyStorage();
}
