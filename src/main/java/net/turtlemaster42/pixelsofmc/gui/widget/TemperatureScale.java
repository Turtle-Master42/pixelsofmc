package net.turtlemaster42.pixelsofmc.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Util;

public class TemperatureScale extends AbstractButton {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/widgets/widgets.png");
    private int state;
    private OnPress onPress;


    public TemperatureScale(int pX, int pY, OnPress onPress, Component pMessage) {
        super(pX, pY, 26, 6, pMessage);
        this.onPress = onPress;
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        int x = this.getX();
        if (pMouseX - x > 20) {
            setState(4);
        } else if (pMouseX - x > 15) {
            setState(3);
        } else if (pMouseX - x > 10) {
            setState(2);
        } else if (pMouseX - x > 5) {
            setState(1);
        } else {
           setState(0);
        }
        this.onPress();
    }

    public void setState(int state) {
        this.state = Math.min(Math.max(state, 0), 4);
    }

    public int getState() {
        return state;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int currentState = getState();
        if (this.isHovered()) {
            currentState += 5;
        }
        Scale scale = Scale.values()[currentState];

        pGuiGraphics.blit(TEXTURE, this.getX(), this.getY(), scale.getX(), scale.getY(), 26, 6);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}


    private enum Scale {
        FREEZE(0, 114),
        COLD(0, 120),
        OFF(0, 126),
        WARM(0, 132),
        HOT(0, 138),
        FREEZE_HOVERED(26, 114),
        COLD_HOVERED(26, 120),
        OFF_HOVERED(26, 126),
        WARM_HOVERED(26, 132),
        HOT_HOVERED(26, 138);

        private final int x;
        private final int y;

        Scale(int pX, int pY) {
            this.x = pX;
            this.y = pY;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(TemperatureScale var1);
    }
}
