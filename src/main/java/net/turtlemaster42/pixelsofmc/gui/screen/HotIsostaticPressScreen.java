package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.ProgressArea;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotIsostaticPressScreen extends AbstractPOMscreen<HotIsostaticPressMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/hot_isostatic_press_gui.png");
    private EnergyArea energyArea;
    private NameArea nameArea;
    private ProgressArea progressArea;

    public HotIsostaticPressScreen(HotIsostaticPressMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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

        renderTooltip(guiGraphics, mouseX, mouseY, x, y);

        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        progressArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY, menu.getProgress(), menu.getMaxProgress());

        if (menu.isHeating()) {
            int offsetY = 0;
            if (hoveredSlot != null && hoveredSlot.hasItem())
                offsetY = -15;
            renderArea(guiGraphics, mouseX, mouseY, 0, offsetY, x, y, 50, 56, 67, 75, new GuiTooltips().getTimeArea( (menu.getTime() + menu.getSoulTime()) / (menu.blockEntity.getItemStackHandler().getStackInSlot(6).getCount() + 1)));
        }
        renderArea(guiGraphics, mouseX, mouseY, x, y, 37, 56, 49, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
        renderArea(guiGraphics, mouseX, mouseY, x, y, 50, 77, 67, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
        renderArea(guiGraphics, mouseX, mouseY, x, y, 68, 56, 77, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
    }

    private void renderTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(Screen.hasControlDown()) {
            pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(), pMouseX - x, pMouseY - y);
        }
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
            guiGraphics.blit(TEXTURE, x + 69, y + 38, 0, 168, menu.getScaledProgressOne(), 11);
            guiGraphics.blit(TEXTURE, x + 55, y + 27, 39, 168, 9, menu.getScaledProgressTwo());
        }

        guiGraphics.blit(TEXTURE, x + 36, y + 82 - menu.getScaledSoulHeat(), 195, 69-menu.getScaledSoulHeat(), 45, menu.getScaledSoulHeat()); //soul heat
        guiGraphics.blit(TEXTURE, x + 36, y + 55, 195, 14, 45, menu.getScaledHeat());//heat
        if(menu.isHeating())
            guiGraphics.blit(TEXTURE, x + 51, y + 75 - menu.getScaledBurnTime(), 240, 29-menu.getScaledBurnTime(), 16, menu.getScaledBurnTime()); //time

        nameArea.draw(guiGraphics);
        energyArea.draw(guiGraphics);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        energyArea = new EnergyArea(x + 9, y + 22, menu.blockEntity.getEnergyStorage());
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 54, y + 27, 10, 5),
                new Rect2i(x + 69, y + 39, 39, 10)
        );
    }
}