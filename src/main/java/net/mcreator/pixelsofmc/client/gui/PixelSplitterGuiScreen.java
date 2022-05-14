package net.mcreator.pixelsofmc.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.pixelsofmc.PixelsOfMcMod;
import net.mcreator.pixelsofmc.world.inventory.PixelSplitterGuiMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PixelSplitterGuiScreen extends AbstractContainerScreen<PixelSplitterGuiMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMcMod.MOD_ID, "textures/pixel_splitter_gui.png");

    public PixelSplitterGuiScreen(PixelSplitterGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        if(menu.isCrafting()) {
            blit(pPoseStack, x + 54, y + 43, 0, 168, menu.getScaledProgress(), 13);
            blit(pPoseStack, x + 85, y + 37, 73, 168, 7, menu.getScaledProgress2());
        }

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
