package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelBombarderMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.ProgressArea;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PixelBombarderScreen extends AbstractPOMscreen<PixelBombarderMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/pixel_bombarder_gui.png");
    private EnergyArea energyArea;
    private NameArea nameArea;
    private ProgressArea progressArea;

    public PixelBombarderScreen(PixelBombarderMenu guiMenu, Inventory playerInventory, Component title) {
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
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight + 2);


        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 119, y + 33, 0, 168, 11, menu.getScaledProgress());
        }

        nameArea.draw(guiGraphics);
        energyArea.draw(guiGraphics);
    }

    private double laserTime = 0;

    protected void renderLaser(@NotNull GuiGraphics guiGraphics, float partialTicks) {
        laserTime += partialTicks * 1.5f;
        if (laserTime > 16) {laserTime = 0;}
        int laserSpeed = (int) Math.round(laserTime);
        int sourceSize = menu.getSourceSize();
        int laserSize = menu.getLaserSize();
        int laserType = menu.getLaserType();
        Color sourceColor = new Color(menu.getSourceColor());
        Color laserColor = new Color(menu.getLaserColor());

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(sourceColor.getRed()/255f, sourceColor.getGreen()/255f, sourceColor.getBlue()/255f, 1f);

        //size
        int source_size = 9 * (sourceSize - 1);
        int laser_size = 9 * (laserSize - 1);

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.enableBlend();
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;

        // short lazer
        if (sourceSize > 0) {
            guiGraphics.blit(TEXTURE, x + 79, y + 41, 0, 194 + source_size, 1, 8);
            guiGraphics.blit(TEXTURE, x + 84, y + 41, 0, 194 + source_size, 1, 8);
            guiGraphics.blit(TEXTURE, x + 80, y + 41, 18 - laserSpeed, 194 + source_size, 4, 8);
        }
        RenderSystem.disableBlend();

        RenderSystem.setShaderColor(laserColor.getRed()/255f, laserColor.getGreen()/255f, laserColor.getBlue()/255f, 1f);
        RenderSystem.enableBlend();
        // long lazer
        if (laserSize > 0 && laserColor.getRGB() != -16777216) {
            guiGraphics.blit(TEXTURE, x + 103, y + 41, 0, 194 + laser_size, 1, 8);
            guiGraphics.blit(TEXTURE, x + 120, y + 41, 0, 194 + laser_size, 1, 8);
            guiGraphics.blit(TEXTURE, x + 104, y + 41, (laserType * 35) + 18 - laserSpeed, 194 + laser_size, 16, 8);
        }
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderLaser(guiGraphics, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        energyArea = new EnergyArea(x + 9, y + 22, menu.blockEntity.getEnergyStorage(), EnergyArea.EnergyType.OVERCHARGED);
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 120, y + 33, 8, 22)
        );
    }
}

