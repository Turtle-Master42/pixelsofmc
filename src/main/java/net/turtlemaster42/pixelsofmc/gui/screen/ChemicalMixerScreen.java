package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalMixerMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.ProgressArea;
import net.turtlemaster42.pixelsofmc.gui.widget.SpriteCycleButton;
import net.turtlemaster42.pixelsofmc.gui.widget.SwitchButton;
import net.turtlemaster42.pixelsofmc.gui.widget.TemperatureScale;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class ChemicalMixerScreen extends AbstractPOMscreen<ChemicalMixerMenu> {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/chemical_mixer_gui.png");
    private EnergyArea energyArea;
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private FluidArea fluidArea3;
    private FluidArea fluidArea4;
    private FluidArea fluidArea5;
    private FluidArea fluidArea6;
    private NameArea nameArea;
    private ProgressArea progressArea;
    private SwitchButton switch1;
    private SwitchButton switch2;
    private SwitchButton switch3;
    private TemperatureScale temperatureScale;
    private SpriteCycleButton spriteCycleButton;

    public ChemicalMixerScreen(ChemicalMixerMenu guiMenu, Inventory inventory, Component title) {
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
        fluidArea5.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        fluidArea6.fillTooltip(guiGraphics, x, y, mouseX, mouseY);

        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        progressArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY, menu.getProgress(), menu.getMaxProgress());
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 84, y + 26, 0, 168, menu.getScaledProgress(), 10);
        }
        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);

        fluidArea1.draw(guiGraphics);
        fluidArea2.draw(guiGraphics);
        fluidArea3.draw(guiGraphics);
        fluidArea4.draw(guiGraphics);
        fluidArea5.draw(guiGraphics);
        fluidArea6.draw(guiGraphics);
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

        energyArea = new EnergyArea(x + 11, y + 22,
                menu.blockEntity.getEnergyStorage(), 10, 44);

        fluidArea1 = new FluidArea(menu.blockEntity.getFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 50, y + 6, 27, 14));
        fluidArea2 = new FluidArea(menu.blockEntity.getDuoFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 50, y + 24, 27, 14));
        fluidArea3 = new FluidArea(menu.blockEntity.getTriFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.input"),
                new Rect2i(x + 50, y + 42, 27, 14));
        fluidArea4 = new FluidArea(menu.blockEntity.getQuadFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 107, y + 6, 27, 14));
        fluidArea5 = new FluidArea(menu.blockEntity.getQuinFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 107, y + 24, 27, 14));
        fluidArea6 = new FluidArea(menu.blockEntity.getHexaFluidTank(), Component.translatable("tooltip.pixelsofmc.fluid.output"),
                new Rect2i(x + 107, y + 42, 27, 14));

        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 84, y + 26, 16, 10)
        );
    }

    private void assignButtons() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.switch1 = new SwitchButton(x + 45, y + 64, SwitchButton.Color.WHITE, Component.literal("Allow Airflow"), (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(switch1.isOn(), 0);
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);

        this.switch2 = new SwitchButton(x + 54, y + 64, Component.literal("§cDump Liquids"), (pButton) -> {
            switch2.cycleOn();
            menu.setSwitch(switch2.isOn(), 1);
        });
        this.switch2.setOn(menu.getSwitch(1));
        this.addRenderableWidget(this.switch2);

        this.switch3 = new SwitchButton(x + 63, y + 64, Component.literal("§a???"), (pButton) -> {
            switch3.cycleOn();
            menu.setSwitch(switch3.isOn(), 2);
        });
        this.switch3.setOn(menu.getSwitch(2));
        this.addRenderableWidget(this.switch3);

        this.temperatureScale = new TemperatureScale(x + 113, y + 64, (temperatureScale) -> {
            menu.setState(temperatureScale.getState());
            this.spriteCycleButton.setState(temperatureScale.getState());
        }, Component.literal("Temperature"));
        this.temperatureScale.setState(menu.getState());
        this.addRenderableWidget(this.temperatureScale);

        this.spriteCycleButton = new SpriteCycleButton(x + 121, y + 71, 10, 10, 5, TEXTURE, 0, 231, (cycleButton) -> {
            menu.setState(cycleButton.getState());
            this.temperatureScale.setState(cycleButton.getState());
            }, Component.literal(""));
        this.spriteCycleButton.setState(menu.getState());
        this.addRenderableWidget(this.spriteCycleButton);
    }
}

