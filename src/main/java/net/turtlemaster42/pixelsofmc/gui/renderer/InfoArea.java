//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public abstract class InfoArea {
    protected final Rect2i area;

    protected InfoArea(Rect2i area)
    {
        this.area = area;
    }


    public void fillTooltip(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY)
    {
        if(area.contains(mouseX, mouseY))
            fillTooltipOverArea(guiGraphics, mouseX - x, mouseY - y);
    }

    protected abstract void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY);

    public abstract void draw(GuiGraphics graphics);
}
