//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;
import java.util.Optional;

public class InfiniteEnergyArea extends InfoArea {
    private final InfinitePixelEnergyStorage energy;

    public InfiniteEnergyArea(int xMin, int yMin, InfinitePixelEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public InfiniteEnergyArea(int xMin, int yMin, InfinitePixelEnergyStorage energy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<Component> getTooltip() {
        if (!Screen.hasShiftDown()) {
            String[] capacityStrings = Util.compactMetricNumber(energy.getInfiniteEnergy().toString());
            String[] energyStrings = Util.compactMetricNumber(energy.getInfiniteCapacity().toString());
            return List.of(
                    Component.literal("§6"+energyStrings[0]+"§r§7 " + energyStrings[1] + "FE"),
                    Component.literal("§e"+capacityStrings[0]+"§r§7 " + capacityStrings[1] + "FE"));
        }
        return List.of(Component.literal("§6"+Util.formatNumber(energy.getInfiniteEnergy())+"§r§7 FE"), Component.literal("§e"+Util.formatNumber(energy.getInfiniteCapacity())+"§r§7 FE"));
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltip(), Optional.empty(), mouseX, mouseY);
    }
    @Override
    public void draw(GuiGraphics graphics) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy.getEnergyStored()/(float)energy.getMaxEnergyStored()));
        graphics.fillGradient(
                area.getX(), area.getY()+(height - stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}
