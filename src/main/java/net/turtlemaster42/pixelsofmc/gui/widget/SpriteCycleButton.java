package net.turtlemaster42.pixelsofmc.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class SpriteCycleButton extends AbstractButton {
    private int state = 0;
    private int maxState;
    private ResourceLocation TEXTURE;
    private int u = 0;
    private int v = 0;
    private OnPress onPress;

    public SpriteCycleButton(int pX, int pY, int pWidth, int pHeight, int cycles, ResourceLocation texture, int u, int v, OnPress onPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.maxState = cycles;
        this.TEXTURE = texture;
        this.u = u;
        this.v = v;
        this.onPress = onPress;
    }

    public SpriteCycleButton(int pX, int pY, int pWidth, int pHeight, int cycles, ResourceLocation texture, OnPress onPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.maxState = cycles;
        this.TEXTURE = texture;
        this.onPress = onPress;
    }

    public SpriteCycleButton(int pX, int pY, int pWidth, int pHeight, int cycles, OnPress onPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.maxState = cycles;
        this.TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        if (Screen.hasShiftDown()) {
            state -= 1;
            if (state < 0) {
                state = maxState - 1;
            }
        } else {
            state += 1;
            if (state >= maxState) {
                state = 0;
            }
        }
        this.onPress.onPress(this);
    }

    public void setState(int state) {
        this.state = Math.min(Math.max(state, 0), maxState);
    }

    public int getState() {
        return state;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        pGuiGraphics.blit(TEXTURE, this.getX(), this.getY(), this.u + (width * state), this.v, this.width, this.height);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(SpriteCycleButton var1);
    }
}
