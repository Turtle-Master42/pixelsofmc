package net.turtlemaster42.pixelsofmc.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class NameArea extends InfoArea {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/widgets/widgets.png");
    private final Component name;

    public NameArea(Component name, int x,  int y) {
        super(new Rect2i(x, y, Minecraft.getInstance().font.width(name.getString()), 15));
        this.name = name;
    }

    @Override
    protected void fillTooltipOverArea(GuiGraphics guiGraphics, int mouseX, int mouseY) {
//        guiGraphics.renderTooltip(Minecraft.getInstance().font, name, mouseX, mouseY);
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        int width = Mth.floor((float) area.getWidth() / 5);
        guiGraphics.blit(TEXTURE, area.getX(), area.getY(), 18, 16, 5, 15);
        for (int i = 1; i <= width + 1; i++) {
            guiGraphics.blit(TEXTURE, area.getX() + (i * 5), area.getY(), 23, 16, 5, 15);
        }
        guiGraphics.blit(TEXTURE, area.getX() + ((width + 2) * 5), area.getY(), 28, 16, 5, 15);
        guiGraphics.drawString(Minecraft.getInstance().font, name, area.getX() + 5, area.getY() + 4, 4210752, false);
    }
}
