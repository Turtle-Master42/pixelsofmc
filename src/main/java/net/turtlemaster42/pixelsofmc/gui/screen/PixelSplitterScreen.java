package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class PixelSplitterScreen extends AbstractPOMscreen<PixelSplitterMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/pixel_splitter_gui.png");
    private EnergyArea energyArea;
    private ProgressArea progressArea;
    private NameArea nameArea;

    public PixelSplitterScreen(PixelSplitterMenu guiMenu, Inventory playerInventory, Component title) {
        super(guiMenu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        assignAreas();
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        progressArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY, menu.getProgress(), menu.getMaxProgress());
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 53, y + 44, 0, 168, menu.getScaledProgressOne(), 11);
            guiGraphics.blit(TEXTURE, x + 85, y + 37, 73, 168, 7, menu.getScaledProgressTwo());
        }

        nameArea.draw(guiGraphics);
        energyArea.draw(guiGraphics);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        energyArea = new EnergyArea(x + 9, y + 22, menu.blockEntity.getEnergyStorage(), EnergyArea.EnergyType.OVERCHARGED);
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 53, y + 44, 61, 10),
                new Rect2i(x + 84, y + 36, 8, 9)
        );
    }
}
