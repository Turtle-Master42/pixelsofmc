//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.widget.SwitchButton;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.awt.*;
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
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/widgets/widgets.png");
    private final IEnergyStorage energy;
    private final EnergyArea.EnergyType type;

    public EnergyArea(int x, int y, IEnergyStorage energy)  {
        this(x, y, energy, 10, 44, EnergyType.NORMAL);
    }

    public EnergyArea(int x, int y, IEnergyStorage energy, EnergyArea.EnergyType type)  {
        this(x, y, energy, 10, 44, type);
    }

    public EnergyArea(int x, int y, IEnergyStorage energy, int width, int height, EnergyArea.EnergyType type)  {
        super(new Rect2i(x, y, width, height));
        this.energy = energy;
        this.type = type;
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
    public void draw(GuiGraphics guiGraphics) {
        int scaledEnergy = this.energy.getMaxEnergyStored() != 0 && this.energy.getEnergyStored() != 0 ? (this.energy.getEnergyStored() * area.getHeight() / this.energy.getMaxEnergyStored()) : 0;
        guiGraphics.blit(TEXTURE, area.getX(), area.getY() + area.getHeight() - scaledEnergy, type.getX(), area.getHeight() - scaledEnergy, area.getWidth(), area.getHeight());
    }

    public enum EnergyType {
        NORMAL(100),
        OVERCHARGED(122),
        SUPERCHARGED(144),
        INFINITE(166);

        private final int x;

        EnergyType(int pX) {
            this.x = pX;
        }

        public int getX() {
            return this.x;
        }

    }
}
