package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.BallMillGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyInfoArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class BallMillGuiScreen extends AbstractContainerScreen<BallMillGuiMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/ball_mill_gui.png");
    private EnergyInfoArea energyInfoArea;

    public BallMillGuiScreen(BallMillGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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

        this.font.draw(pPoseStack, menu.blockEntity.getDisplayName(), 5, 5, 4210752); //+
        renderEnergyArea(pPoseStack, pMouseX, pMouseY, x, y);
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 49, 19, 58, 68, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 59, 41, 68, 46, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 69, 25, 74, 62, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 75, 25, 100, 30, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 75, 57, 100, 62, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 101, 25, 106, 62, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 107, 39, 124, 48, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
    }

    private void renderEnergyArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 11, 22, 9, 44)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, fromX, fromY, toX - fromX, toY-fromY)) {
            renderTooltip(pPoseStack, tooltip,
                    Optional.empty(), pMouseX - x, pMouseY - y);
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
            blit(pPoseStack, x + 47, y + 19, 0, 168, menu.getScaledProgressOne(), 51);
        }
        blit(pPoseStack, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) + 11,
                ((height - imageHeight) / 2) + 22, menu.blockEntity.energyStorage, 10, 44);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}

