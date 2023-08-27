package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

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

}
