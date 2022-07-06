package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterGuiMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyInfoArea;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PixelSplitterGuiScreen extends AbstractContainerScreen<PixelSplitterGuiMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/pixel_splitter_gui.png");

    private EnergyInfoArea EnergyInfoArea;

    public PixelSplitterGuiScreen(PixelSplitterGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 8, 19, 16, 50)) {
            renderTooltip(pPoseStack, EnergyInfoArea.getTooltips(),
                    Optional.empty(),pMouseX - x, pMouseY - y);
        }
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
        blit(pPoseStack, x + 11, y + 22, 185, 0, 10, menu.getScaledEnergy());

    }


    private void assignEnergyInfoArea() {
        EnergyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) +  156, ((height - imageHeight) / 2) + 13, menu.blockEntity.energyStorage);
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
