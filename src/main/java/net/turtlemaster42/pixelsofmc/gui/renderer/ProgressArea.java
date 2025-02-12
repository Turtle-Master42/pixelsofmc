package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ProgressArea extends InfoArea {

    private final int progress;
    private final int maxProgress;
    private final Rect2i[] areas;

    public ProgressArea(int progress, int maxProgress, Rect2i... area)  {
        super(area[0]);
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.areas = area;
    }

    public List<Component> getTooltips() {
        if (Screen.hasShiftDown())
            return List.of(
                    Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"),
                    Component.literal("§9"+((maxProgress/20)-(progress/20))+" s")
            );
        else return List.of(Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"));
    }

    @Override
    public void fillTooltip(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY)
    {
        boolean inArea = false;
        for (Rect2i rect2i : areas) {
            if (rect2i.contains(mouseX, mouseY)) {
                inArea = true;
                break;
            }
        }

        if(inArea)
            fillTooltipOverArea(guiGraphics, mouseX - x, mouseY - y);
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, getTooltips(), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        for (Rect2i area : areas) {
            guiGraphics.fill(area.getX(), area.getY(), area.getX() + area.getWidth(), area.getY() + area.getHeight(), new Color(200, 0, 0).getRGB());
        }
    }
}
