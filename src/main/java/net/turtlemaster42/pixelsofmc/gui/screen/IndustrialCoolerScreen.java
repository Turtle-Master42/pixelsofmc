package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.IndustrialCoolerMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.widget.BigSwitchButton;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class IndustrialCoolerScreen extends AbstractPOMscreen<IndustrialCoolerMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/industrial_cooler.png");
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private FluidArea fluidArea3;
    private FluidArea fluidArea4;
    private NameArea nameArea;
    private BigSwitchButton switch1;

    public IndustrialCoolerScreen(IndustrialCoolerMenu guiMenu, Inventory inventory, Component title) {
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
        fluidArea3.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea4.fillTooltip(guiGraphics, x, y, mouseX, mouseY);

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

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 59, y + 14, 0, 150, 54, 40);
        }
        fluidArea1.draw(guiGraphics);
        fluidArea2.draw(guiGraphics);
        fluidArea3.draw(guiGraphics);
        fluidArea4.draw(guiGraphics);
        nameArea.draw(guiGraphics);
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

        fluidArea1 = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 37, y + 6, 15, 53));
        fluidArea2 = new FluidArea(menu.blockEntity.getDuoFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.coolant_input"),
                new Rect2i(x + 75, y + 6, 15, 53));
        fluidArea3 = new FluidArea(menu.blockEntity.getTriFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.coolant_output"),
                new Rect2i(x + 119, y + 12, 27, 15));
        fluidArea4 = new FluidArea(menu.blockEntity.getQuadFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 119, y + 41, 27, 15));

        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
    }

    private void assignButtons() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.switch1 = new BigSwitchButton(x + 17, y + 19, BigSwitchButton.Color.RED, Component.translatable("tooltip.pixelsofmc.button.on_off"), (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(switch1.isOn(), 0);
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);
    }
}

