package net.turtlemaster42.pixelsofmc.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.client.gui.components.Button;

public class SwitchButton extends Button {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/widgets/buttons.png");
    private boolean on;
    private SwitchButton.Color color;


    public SwitchButton(int pX, int pY, SwitchButton.Color color, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        super(pX, pY, 7, 14, net.minecraft.network.chat.Component.literal(""), pOnPress, DEFAULT_NARRATION);
        this.color = color;
    }

    public SwitchButton(int pX, int pY, SwitchButton.Color color, net.minecraft.network.chat.Component tooltip, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        super(pX, pY, 7, 14, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        setTooltip(Tooltip.create(tooltip));
        this.color = color;
    }

    public SwitchButton(int pX, int pY, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        super(pX, pY, 7, 14, net.minecraft.network.chat.Component.literal(""), pOnPress, DEFAULT_NARRATION);
        this.color = Color.RED;
    }

    public SwitchButton(int pX, int pY, net.minecraft.network.chat.Component tooltip, net.minecraft.client.gui.components.Button.OnPress pOnPress) {
        super(pX, pY, 7, 14, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        setTooltip(Tooltip.create(tooltip));
        this.color = Color.RED;
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

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        Icon icon;
        if (this.isHoveredOrFocused()) {
            icon = this.on ? Icon.ON_HOVER : Icon.OFF_HOVER;
        } else {
            icon = this.on ? Icon.ON : Icon.OFF;
        }
        pGuiGraphics.blit(TEXTURE, this.getX(), this.getY(), color.getX() + icon.getX(), icon.getY(), 7, 14);
    }

    @OnlyIn(Dist.CLIENT)
    private enum Icon {
        ON(0, 0),
        ON_HOVER(0, 14),
        OFF(7, 0),
        OFF_HOVER(7, 14);

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

    @OnlyIn(Dist.CLIENT)
    public enum Color {
        RED(0),
        ORANGE(15),
        YELLOW(30),
        LIME(45),
        GREEN(60),
        CYAN(75),
        LIGHT_BLUE(90),
        BLUE(105),
        PURPLE(120),
        MAGENTA(135),
        PINK(150),
        WHITE(165),
        LIGHT_GRAY(180),
        GRAY(195),
        BLACK(210),
        BROWN(225),
        METAL(240);

        private final int x;

        Color(int pX) {
            this.x = pX;
        }

        public int getX() {
            return this.x;
        }
    }
}
