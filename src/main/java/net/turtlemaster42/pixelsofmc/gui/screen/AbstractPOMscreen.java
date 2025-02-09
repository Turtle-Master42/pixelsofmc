package net.turtlemaster42.pixelsofmc.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AbstractPOMscreen<MENU extends AbstractContainerMenu> extends AbstractContainerScreen<MENU> {

    protected AbstractPOMscreen(MENU pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    public int getExtraWidth() {
        return 0;
    }

    public int getExtraHeight() {
        return 0;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    }

    protected void renderArea(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        renderArea(pGuiGraphics, pMouseX, pMouseY, 0 ,0, x, y, fromX, fromY, toX, toY, tooltip);
    }

    protected void renderArea(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int offsetX, int offsetY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, fromX, fromY, toX - fromX, toY-fromY)) {
            pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, tooltip, pMouseX - x + offsetX, pMouseY - y + offsetY);
        }
    }

    protected boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
