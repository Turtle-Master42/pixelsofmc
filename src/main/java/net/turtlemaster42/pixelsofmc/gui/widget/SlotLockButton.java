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

public class SlotLockButton extends Button {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");
    private boolean locked;
    private final int slotX;
    private final int slotY;

    public SlotLockButton(int pX, int pY, int pSlotX, int pSlotY, Button.OnPress pOnPress) {
        super(pX, pY, 6, 6, Component.literal(""), pOnPress, DEFAULT_NARRATION);
        this.slotX = pSlotX;
        this.slotY = pSlotY;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean pLocked) {
        this.locked = pLocked;
    }

    public void cycleLocked() {
        this.locked = !this.locked;
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        SlotLockButton.Icon slotlockbutton$icon;
        if (this.isHoveredOrFocused()) {
            slotlockbutton$icon = this.locked ? SlotLockButton.Icon.LOCKED_HOVER : SlotLockButton.Icon.UNLOCKED_HOVER;
        } else {
            slotlockbutton$icon = this.locked ? SlotLockButton.Icon.LOCKED : SlotLockButton.Icon.UNLOCKED;
        }

        this.blit(pPoseStack, this.getX(), this.getY(), slotlockbutton$icon.getX(), slotlockbutton$icon.getY(), this.width, this.height);
        if (this.isLocked()) {
            this.blit(pPoseStack, slotX, slotY, 48, 0, 16, 16);
        }
    }

    @OnlyIn(Dist.CLIENT)
    enum Icon {
        LOCKED(6, 58),
        LOCKED_HOVER(6, 64),
        UNLOCKED(0, 58),
        UNLOCKED_HOVER(0, 64);

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
