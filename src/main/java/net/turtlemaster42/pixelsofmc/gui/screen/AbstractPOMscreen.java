package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;

import java.util.List;
import java.util.Optional;

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
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
    }

    protected void renderArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        renderArea(pPoseStack, pMouseX, pMouseY, 0 ,0, x, y, fromX, fromY, toX, toY, tooltip);
    }

    protected void renderArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int offsetX, int offsetY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, fromX, fromY, toX - fromX, toY-fromY)) {
            renderTooltip(pPoseStack, tooltip,
                    Optional.empty(), pMouseX - x + offsetX, pMouseY - y + offsetY);
        }
    }

    protected boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
