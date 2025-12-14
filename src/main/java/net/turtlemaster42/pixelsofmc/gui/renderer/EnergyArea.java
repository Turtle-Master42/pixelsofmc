//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;
import java.util.Optional;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public class EnergyArea extends InfoArea {
    private final IEnergyStorage energy;

    public EnergyArea(int xMin, int yMin, IEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public EnergyArea(int xMin, int yMin, IEnergyStorage energy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        if (!Screen.hasShiftDown()) {
            String[] capacityStrings = Util.compactMetricNumber(energy.getMaxEnergyStored());
            String[] energyStrings = Util.compactMetricNumber(energy.getEnergyStored());
            return List.of(
                    Component.literal("§6"+energyStrings[0]+"§r§7 " + energyStrings[1] + "FE"),
                    Component.literal("§e"+capacityStrings[0]+"§r§7 " + capacityStrings[1] + "FE"));
        }
        return List.of(Component.literal("§6"+Util.formatNumber(energy.getEnergyStored())+"§r§7 FE"), Component.literal("§e"+Util.formatNumber(energy.getMaxEnergyStored())+"§r§7 FE"));
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltips(), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void draw(GuiGraphics graphics) {}
}
