package net.turtlemaster42.pixelsofmc.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class GreenSwitchButton extends Button {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");
    private boolean on;


    public GreenSwitchButton(int pX, int pY, OnPress pOnPress) {
        super(pX, pY, 7, 14, Component.literal(""), pOnPress, DEFAULT_NARRATION);
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean pOn) {
        this.on = pOn;
    }

    public void cycleOn() {
        this.on = !this.on;
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        GreenSwitchButton.Icon switchButton$icon;
        if (this.isHoveredOrFocused()) {
            switchButton$icon = this.on ? Icon.ON_HOVER : Icon.OFF_HOVER;
        } else {
            switchButton$icon = this.on ? GreenSwitchButton.Icon.ON : GreenSwitchButton.Icon.OFF;
        }

        this.blit(pPoseStack, this.getX(), this.getY(), switchButton$icon.getX(), switchButton$icon.getY(), this.width, this.height);
    }

    @OnlyIn(Dist.CLIENT)
    enum Icon {
        ON(14, 70),
        ON_HOVER(14, 84),
        OFF(21, 70),
        OFF_HOVER(21, 84);

        private final int x;
        private final int y;

        Icon(int pX, int pY) {
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
}