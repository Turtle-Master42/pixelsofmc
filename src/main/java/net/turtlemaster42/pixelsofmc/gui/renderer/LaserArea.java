package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class LaserArea extends InfoArea {
    protected int type;
    protected int size;
    protected int color;


    public LaserArea(int x,  int y, int width, int height, int type, int size, int color) {
        super(new Rect2i(x, y, width, height));
        this.type = type;
        this.size = size;
        this.color = color;
    }

    public List<Component> getTooltips() {
        if (color == 0) {
            return List.of();
        }

        if (Screen.hasShiftDown()) {
            return List.of(
                    Component.literal(getBeam()).setStyle(Style.EMPTY.withColor(color)),
                    Component.translatable("tooltip.pixelsofmc.gui.info.beam.color").append(Component.literal(Util.RGBToHex(color)).setStyle(Style.EMPTY.withColor(color))),
                    Component.translatable("tooltip.pixelsofmc.gui.info.beam.size", "§b" + size),
                    Component.translatable("tooltip.pixelsofmc.gui.info.beam.type", "§d" + getType().getString())
            );
        } else {
            return List.of(Component.literal(getBeam()).setStyle(Style.EMPTY.withColor(color)));
        }
    }

    public void fillTooltip(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, int type, int size, int color) {
        this.type = type;
        this.size = size;
        this.color = color;
        if(area.contains(mouseX, mouseY)) {
            fillTooltipOverArea(guiGraphics, mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltips(), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void draw(GuiGraphics graphics) {}

    public String getBeam() {
        String translatableString = "tooltip.pixelsofmc.gui.info.beam." + type + "." + Util.RGBToHex(color).replace("#", "");
        MutableComponent beam = Component.translatable(translatableString);
        if (!beam.getString().equals(translatableString)) {
            return beam.getString();
        }
        return getType().getString();
    }

    public Component getType() {
        return switch (type) {
            case 1 -> Component.translatable("tooltip.pixelsofmc.gui.info.laser_type.particles");
            case 2 -> Component.translatable("tooltip.pixelsofmc.gui.info.laser_type.broad_spectrum");
            default -> Component.translatable("tooltip.pixelsofmc.gui.info.laser_type.visible_light");
        };
    }
}
