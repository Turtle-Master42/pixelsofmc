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
        super(Util.resourceLocation("textures/gui/jei/laser_source.png"), x, y, 18, 6, 0, 34, 18, 6, 256, 256);
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
        if (laserTime > 8) {laserTime = 0;}
        int laserSpeed = Math.round(laserTime);

        //size
        int vThin = 0;
        if (this.size < 2)
            vThin = 7;

        EmiDrawContext context = EmiDrawContext.wrap(draw);
        context.setColor(this.color.getRed()/255f, this.color.getGreen()/255f, this.color.getBlue()/255f, 1f);
        context.drawTexture(this.texture, this.x, this.y+1, 1, 4, (float)this.u, (float)this.v + vThin + 1, 1, 4, this.textureWidth, this.textureHeight);
        context.drawTexture(this.texture, this.x+1, this.y, 16, 6, (float)this.u + (27*this.type)  + 10 - laserSpeed, (float)this.v + vThin, 16, 6, this.textureWidth, this.textureHeight);
        context.drawTexture(this.texture, this.x+17, this.y+1, 1, 4, (float)this.u, (float)this.v + vThin + 1, 1, 4, this.textureWidth, this.textureHeight);
        context.resetColor();
    }
}
