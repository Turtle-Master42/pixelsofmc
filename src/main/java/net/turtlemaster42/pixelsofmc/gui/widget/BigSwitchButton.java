package net.turtlemaster42.pixelsofmc.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class BigSwitchButton extends Button {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/widgets/buttons.png");
    private boolean on;
    private final BigSwitchButton.Color color;

    public BigSwitchButton(int pX, int pY, BigSwitchButton.Color color, OnPress pOnPress) {
        super(pX, pY, 12, 26, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        this.color = color;
    }

    public BigSwitchButton(int pX, int pY, BigSwitchButton.Color color, Component tooltip, OnPress pOnPress) {
        super(pX, pY, 12, 26, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        setTooltip(Tooltip.create(tooltip));
        this.color = color;
    }

    public BigSwitchButton(int pX, int pY, OnPress pOnPress) {
        super(pX, pY, 12, 26, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        this.color = Color.RED;
    }

    public BigSwitchButton(int pX, int pY, Component tooltip, OnPress pOnPress) {
        super(pX, pY, 12, 26, Component.literal(""), pOnPress, DEFAULT_NARRATION);
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

    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        BigSwitchButton.Icon icon;
        if (this.isHoveredOrFocused()) {
            icon = this.on ? Icon.ON_HOVER : Icon.OFF_HOVER;
        } else {
            icon = this.on ? BigSwitchButton.Icon.ON : BigSwitchButton.Icon.OFF;
        }
        pGuiGraphics.blit(TEXTURE, this.getX(), this.getY(), color.getX(), icon.getY(), this.width, this.height);
    }

    @OnlyIn(Dist.CLIENT)
    enum Icon {
        ON(29),
        ON_HOVER(55),
        OFF(81),
        OFF_HOVER(107);

        private final int y;

        Icon(int pY) {
            this.y = pY;
        }

        public int getY() {
            return this.y;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public enum Color {
        RED(0),
        ORANGE(13),
        YELLOW(26),
        LIME(39),
        GREEN(52),
        CYAN(65),
        LIGHT_BLUE(78),
        BLUE(91),
        PURPLE(104),
        MAGENTA(117),
        PINK(130),
        WHITE(143),
        LIGHT_GRAY(156),
        GRAY(169),
        BLACK(182),
        BROWN(195),
        METAL(207);

        private final int x;

        Color(int pX) {
            this.x = pX;
        }

        public int getX() {
            return this.x;
        }
    }
}
