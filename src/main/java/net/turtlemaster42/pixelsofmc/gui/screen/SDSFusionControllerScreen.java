package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.SDSFusionControllerMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import net.turtlemaster42.pixelsofmc.gui.widget.*;
import net.turtlemaster42.pixelsofmc.util.Constants;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SDSFusionControllerScreen extends AbstractPOMscreen<SDSFusionControllerMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/sds_controller_gui.png");
    private InfiniteEnergyArea infiniteEnergyArea;
    private NameArea nameArea;
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private SwitchButton switch1;
    private SwitchButton switch2;
    private SwitchButton switch3;

    public SDSFusionControllerScreen(SDSFusionControllerMenu guiMenu, Inventory playerInventory, Component title) {
        super(guiMenu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        assignAreas();
        assignButtons();
    }

    @Override
    public int getExtraWidth() {
        return 210;
    }

    @Override
    public int getExtraHeight() {
        return 90;
    }


    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea1.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea2.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        infiniteEnergyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);

        if (menu.getReason() == 1) {
            renderReactorStrengthErrorArea(guiGraphics, mouseX, mouseY, x, y);
        } else if (menu.getReason() == 2) {
            renderFusionPowerErrorArea(guiGraphics, mouseX, mouseY, x, y);
        }
        renderFusionPowerArea(guiGraphics, mouseX, mouseY, x, y);
    }

    private void renderReactorStrengthErrorArea(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 56, 34, 36, 32)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip.pixelsofmc.gui.info.crafting.title"),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.reactor_strength.1"),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.reactor_strength.2"),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.reactor_strength.3", "§e" + menu.getFirstElement().getElement().elementName(), "§e" + menu.getSecondElement().getElement().elementName())), mouseX - x, mouseY - y);
        }
    }

    private void renderFusionPowerErrorArea(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 56, 34, 36, 32)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip.pixelsofmc.gui.info.crafting.title"),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion_power.1"),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion_power.2", "§d" + Util.formatNumber(Math.round((Math.PI * Math.abs((menu.getFirstElement().getProtonCount() * menu.getSecondElement().getProtonCount()) / (5.636 * Math.pow(10, 12) * (Math.pow(menu.getFirstElement().getBitMass(), 0.333) + Math.pow(menu.getSecondElement().getBitMass(), 0.333))))) / (4 * 1.3806 * Math.pow(10, -23)) / Constants.fusionTemperatureDivider))),
                    Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion_power.3", "§e" + menu.getFirstElement().getElement().elementName(), "§e" + menu.getSecondElement().getElement().elementName())), mouseX - x, mouseY - y);
        }
    }

    private void renderFusionPowerArea(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 80, 6, 36, 6)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip.pixelsofmc.gui.info.fusion_power", "§6"+Util.formatNumber(menu.blockEntity.getFusionPower()), "§6"+Util.formatNumber(menu.blockEntity.getMaxFusionPower()))), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 33, imageHeight);

        nameArea.draw(guiGraphics);
        fluidArea1.draw(guiGraphics);
        fluidArea2.draw(guiGraphics);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 56, y + 34, 0, 168, menu.getScaledProgress(), 33);
        }
        if (menu.getReason() > 0) {
            guiGraphics.blit(TEXTURE, x + 56, y + 34, 38, 168, 36, 32);
        }
        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 209, 44 - menu.getScaledEnergy(), 10, 44);
        guiGraphics.blit(TEXTURE, x + 80, y + 6, 209 + 2 * Math.round((float) Math.max(0, menu.getScaledFusionPower() - 37) / 2), 88, Math.min(36, menu.getScaledFusionPower()), 6);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        infiniteEnergyArea = new InfiniteEnergyArea(x + 9, y + 22,
                menu.blockEntity.getEnergyStorage(), 10, 44);
        fluidArea1 = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 169, y + 6, 15, 67));
        fluidArea2 = new FluidArea(menu.blockEntity.getDuoFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 188, y + 6, 15, 67));
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
    }


    private void assignButtons() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.switch1 = new SwitchButton(x + 127, y + 6, Component.literal("§6Toggle Generate Plasma"), (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(switch1.isOn(), 0);
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);

        this.switch2 = new SwitchButton(x + 136, y + 6, Component.literal("§bToggle Liquid Cooling"), (pButton) -> {
            switch2.cycleOn();
            menu.setSwitch(switch2.isOn(), 1);
        });
        this.switch2.setOn(menu.getSwitch(1));
        this.addRenderableWidget(this.switch2);

        this.switch3 = new SwitchButton(x + 145, y + 6, Component.literal("§dToggle Overcharging"), (pButton) -> {
            switch3.cycleOn();
            menu.setSwitch(switch3.isOn(), 2);
        });
        this.switch3.setOn(menu.getSwitch(2));
        this.addRenderableWidget(this.switch3);
    }
}
