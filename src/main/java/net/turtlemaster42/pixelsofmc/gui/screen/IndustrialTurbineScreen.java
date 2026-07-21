package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.IndustrialTurbineMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.widget.BigSwitchButton;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class IndustrialTurbineScreen extends AbstractPOMscreen<IndustrialTurbineMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/industrial_turbine.png");
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private NameArea nameArea;
    private EnergyArea energyArea;
    private BigSwitchButton switch1;

    public IndustrialTurbineScreen(IndustrialTurbineMenu guiMenu, Inventory inventory, Component title) {
        super(guiMenu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        assignAreas();
        assignButtons();
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        fluidArea1.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea2.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight - 16);

        if (menu.isActive()) {
            guiGraphics.blit(TEXTURE, x + 77, y + 19, 0, 150, 32, 27);
        }
        fluidArea1.draw(guiGraphics);
        fluidArea2.draw(guiGraphics);
        energyArea.draw(guiGraphics);
        nameArea.draw(guiGraphics);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        fluidArea1 = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 54, y + 6, 15, 53));
        fluidArea2 = new FluidArea(menu.blockEntity.getDuoFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 116, y + 6, 15, 53));
        energyArea = new EnergyArea(x + 9, y + 8, menu.blockEntity.getEnergyStorage());

        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
    }

    private void assignButtons() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.switch1 = new BigSwitchButton(x + 34, y + 19, BigSwitchButton.Color.RED, Component.translatable("tooltip.pixelsofmc.button.on_off"), (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(switch1.isOn(), 0);
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);
    }
}

