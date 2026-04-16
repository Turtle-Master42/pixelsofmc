package net.turtlemaster42.pixelsofmc.intergration.emi;

import dev.emi.emi.api.widget.TextureWidget;
import dev.emi.emi.runtime.EmiDrawContext;
import net.minecraft.client.gui.GuiGraphics;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.awt.*;

public class LaserWidget extends TextureWidget {
    private float laserTime = 0;
    private final int size;
    private final int type;
    private final Color color;

    public LaserWidget(int x, int y, int size, int type, Color color) {
        super(Util.resourceLocation("textures/gui/jei/laser_source.png"), x, y, 26, 8, 0, 34, 18, 8, 256, 256);
        this.size = size;
        this.type = type;
        this.color = color;
    }

    public LaserWidget(int x, int y, Color color) {
        this(x, y, 2, 0, color);
    }

    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float partialTicks) {
        laserTime += partialTicks * 1.5f;
        if (laserTime > 16) {laserTime = 0;}
        int laserSpeed = Math.round(laserTime);

        //size
        int size = (this.size-1) * 9;

        EmiDrawContext context = EmiDrawContext.wrap(draw);
        context.setColor(this.color.getRed()/255f, this.color.getGreen()/255f, this.color.getBlue()/255f, 1f);
        context.drawTexture(this.texture, this.x, this.y, 1, 8, (float)this.u, (float)this.v + size, 1, 8, this.textureWidth, this.textureHeight);
        context.drawTexture(this.texture, this.x+1, this.y, 16, 8, (float)this.u + (35 * this.type)  + 18 - laserSpeed, (float)this.v + size, 16, 8, this.textureWidth, this.textureHeight);
        context.drawTexture(this.texture, this.x+17, this.y, 1, 8, (float)this.u, (float)this.v + size, 1, 8, this.textureWidth, this.textureHeight);
        context.resetColor();
    }
}
