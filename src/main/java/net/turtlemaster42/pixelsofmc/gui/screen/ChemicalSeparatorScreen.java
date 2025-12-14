package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalSeparatorMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.ProgressArea;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class ChemicalSeparatorScreen extends AbstractPOMscreen<ChemicalSeparatorMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/chemical_seperator_gui.png");
    private EnergyArea energyArea;
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private NameArea nameArea;
    private ProgressArea progressArea;

    public ChemicalSeparatorScreen(ChemicalSeparatorMenu guiMenu, Inventory playerInventory, Component title) {
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
        fluidArea1.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea2.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
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

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 66, y + 34, 0, 168, menu.getScaledProgress(), 43);
        }
        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);
        fluidArea1.draw(guiGraphics);
        fluidArea2.draw(guiGraphics);
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
        fluidArea1 = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 58, y + 8, 25, 11));
        fluidArea2 = new FluidArea(menu.blockEntity.getDuoFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 58, y + 23, 25, 11));
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 66, y + 50, 55, 10),
                new Rect2i(x + 110, y + 34, 12, 16),
                new Rect2i(x + 110, y + 61, 9, 15)
        );
    }
}

