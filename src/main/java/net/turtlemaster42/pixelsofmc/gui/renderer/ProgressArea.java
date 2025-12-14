package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgressArea extends InfoArea {

    private int progress;
    private int maxProgress;
    private final Rect2i[] areas;

    public ProgressArea(int progress, int maxProgress, Rect2i... area)  {
        super(area[0]);
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.areas = area;
    }

    public List<Component> getTooltips() {
        List<Component> tooltip = new ArrayList<>();

        tooltip.add(Component.literal("§7"+ (int)((100f / (float)maxProgress * (float)progress)) +"%"));
        if (Screen.hasShiftDown()) {
            float seconds = (maxProgress - progress) / 20f;
            if (seconds < 1) {
                tooltip.add(Component.literal("§9" + (Math.round(seconds * 10f) / 10f) + " s"));
            } else {
                tooltip.add(Component.literal("§9" + (int)seconds + " s"));
            }
        }
        return tooltip;
    }

    public void fillTooltip(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, int progress, int maxProgress) {
        if (progress <= 0) return;
        this.progress = progress;
        this.maxProgress = maxProgress;
        for (Rect2i rect2i : areas) {
            if (rect2i.contains(mouseX, mouseY)) {
                fillTooltipOverArea(guiGraphics, mouseX - x, mouseY - y);
                break;
            }
        }
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltips(), Optional.empty(), mouseX, mouseY);
    }

    //Debug drawer
    @Override
    public void draw(GuiGraphics guiGraphics) {
        for (Rect2i area : areas) {
            guiGraphics.fill(area.getX(), area.getY(), area.getX() + area.getWidth(), area.getY() + area.getHeight(), new Color(200, 0, 0).getRGB());
        }
    }
}
