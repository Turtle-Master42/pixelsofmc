package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelAssemblerMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import org.jetbrains.annotations.NotNull;

public class PixelAssemblerScreen extends AbstractPOMscreen<PixelAssemblerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/pixel_assembler_gui.png");
    private EnergyArea energyArea;
    private NameArea nameArea;
    private ProgressArea progressArea;
    private FluidArea fluidArea;

    public PixelAssemblerScreen(PixelAssemblerMenu guiMenu, Inventory playerInventory, Component title) {
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
        fluidArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        progressArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
    }


    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        nameArea.draw(guiGraphics);
        fluidArea.draw(guiGraphics);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 65, y + 41, 0, 168, menu.getScaledProgressOne(), 11);
        }
        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        energyArea = new EnergyArea(x + 11, y + 22,
                menu.blockEntity.getEnergyStorage(), 10, 44);
        fluidArea = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 128, y + 63, 16, 14));
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 65, y + 41, 60, 10)
        );
    }
}
