package net.turtlemaster42.pixelsofmc.block.entity;

import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import org.jetbrains.annotations.NotNull;

public interface IEnergyHandlingBlockEntity {
    @NotNull PixelEnergyStorage createEnergyStorage();
    void setEnergyLevel(int energyLevel);
    IEnergyStorage getEnergyStorage();
}
