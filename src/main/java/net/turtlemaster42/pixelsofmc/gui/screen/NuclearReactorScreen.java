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
import net.turtlemaster42.pixelsofmc.gui.menu.NuclearReactorMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.widget.GreenSwitchButton;
import net.turtlemaster42.pixelsofmc.gui.widget.RedSwitchButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class NuclearReactorScreen extends AbstractPOMscreen<NuclearReactorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/nuclear_reactor_gui.png");

    private EnergyArea energyArea;
    private NameArea nameArea;
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private RedSwitchButton redSwitch1;
    private RedSwitchButton redSwitch2;
    private RedSwitchButton redSwitch3;
    private GreenSwitchButton greenSwitch1;
    private GreenSwitchButton greenSwitch2;
    private GreenSwitchButton greenSwitch3;
    private GreenSwitchButton greenSwitch4;

    public NuclearReactorScreen(NuclearReactorMenu guiMenu, Inventory playerInventory, Component title) {
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
        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);

        if (greenSwitch1.isOn() && greenSwitch2.isOn() && (mouseX >= x + 71 && mouseY >= y + 24 && mouseX < x + 76 && mouseY < y + 33)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(Component.literal("§a+50%")), Optional.empty(), mouseX - x, mouseY - y);
        }
        if (greenSwitch1.isOn() && greenSwitch3.isOn() && (mouseX >= x + 54 && mouseY >= y + 41 && mouseX < x + 63 && mouseY < y + 46)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(Component.literal("§a+50%")), Optional.empty(), mouseX - x, mouseY - y);
        }
        if (greenSwitch3.isOn() && greenSwitch4.isOn() && (mouseX >= x + 71 && mouseY >= y + 54 && mouseX < x + 76 && mouseY < y + 63)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(Component.literal("§a+50%")), Optional.empty(), mouseX - x, mouseY - y);
        }
        if (greenSwitch2.isOn() && greenSwitch4.isOn() && (mouseX >= x + 84 && mouseY >= y + 41 && mouseX < x + 93 && mouseY < y + 46)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(Component.literal("§a+50%")), Optional.empty(), mouseX - x, mouseY - y);
        }
        if (mouseX >= x + 69 && mouseY >= y + 39 && mouseX < x + 78 && mouseY < y + 48) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(efficiencyBonusTooltip(menu.getEfficiencyBonus())), Optional.empty(), mouseX - x, mouseY - y);
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

        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 209, 44 - menu.getScaledEnergy(), 10, 44);

        if (greenSwitch1.isOn() && greenSwitch2.isOn()) {
            guiGraphics.blit(TEXTURE, x + 71, y + 28, 3, 168, 6, 2);
        }
        if (greenSwitch1.isOn() && greenSwitch3.isOn()) {
            guiGraphics.blit(TEXTURE, x + 58, y + 41, 0, 168, 2, 6);
        }
        if (greenSwitch3.isOn() && greenSwitch4.isOn()) {
            guiGraphics.blit(TEXTURE, x + 71, y + 58, 3, 168, 6, 2);
        }
        if (greenSwitch2.isOn() && greenSwitch4.isOn()) {
            guiGraphics.blit(TEXTURE, x + 88, y + 41, 0, 168, 2, 6);
        }

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

        energyArea = new EnergyArea(x + 9, y + 22,
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
        this.redSwitch1 = new RedSwitchButton(x + 127, y + 6, Component.literal("§6???"), (pButton) -> {
            redSwitch1.cycleOn();
            menu.setSwitch(redSwitch1.isOn(), 0);
        });
        this.redSwitch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.redSwitch1);

        this.redSwitch2 = new RedSwitchButton(x + 136, y + 6, Component.literal("§bToggle Liquid Cooling"), (pButton) -> {
            redSwitch2.cycleOn();
            menu.setSwitch(redSwitch2.isOn(), 1);
        });
        this.redSwitch2.setOn(menu.getSwitch(1));
        this.addRenderableWidget(this.redSwitch2);

        this.redSwitch3 = new RedSwitchButton(x + 145, y + 6, Component.literal("§d???"), (pButton) -> {
            redSwitch3.cycleOn();
            menu.setSwitch(redSwitch3.isOn(), 2);
        });
        this.redSwitch3.setOn(menu.getSwitch(2));
        this.addRenderableWidget(this.redSwitch3);

        this.greenSwitch1 = new GreenSwitchButton(x + 38, y + 22, Component.literal("§dLock/Unlock"), (pButton) -> {
            if (!menu.blockEntity.getItemStackHandler().getStackInSlot(0).isEmpty()) {
                greenSwitch1.cycleOn();
                menu.setSwitch(greenSwitch1.isOn(), 3);
            }
        });
        this.greenSwitch1.setOn(menu.getSwitch(3));
        this.addRenderableWidget(this.greenSwitch1);

        this.greenSwitch2 = new GreenSwitchButton(x + 103, y + 22, Component.literal("§dLock/Unlock"), (pButton) -> {
            if (!menu.blockEntity.getItemStackHandler().getStackInSlot(1).isEmpty()) {
                greenSwitch2.cycleOn();
                menu.setSwitch(greenSwitch2.isOn(), 4);
            }
        });
        this.greenSwitch2.setOn(menu.getSwitch(4));
        this.addRenderableWidget(this.greenSwitch2);

        this.greenSwitch3 = new GreenSwitchButton(x + 38, y + 52, Component.literal("§dLock/Unlock"), (pButton) -> {
            if (!menu.blockEntity.getItemStackHandler().getStackInSlot(2).isEmpty()) {
                greenSwitch3.cycleOn();
                menu.setSwitch(greenSwitch3.isOn(), 5);
            }
        });
        this.greenSwitch3.setOn(menu.getSwitch(5));
        this.addRenderableWidget(this.greenSwitch3);

        this.greenSwitch4 = new GreenSwitchButton(x + 103, y + 52, Component.literal("§dLock/Unlock"), (pButton) -> {
            if (!menu.blockEntity.getItemStackHandler().getStackInSlot(3).isEmpty()) {
                greenSwitch4.cycleOn();
                menu.setSwitch(greenSwitch4.isOn(), 6);
            }
        });
        this.greenSwitch4.setOn(menu.getSwitch(6));
        this.addRenderableWidget(this.greenSwitch4);
    }

    private Component efficiencyBonusTooltip(float bonus) {
        int efficiencyBonus = (int) ((bonus - 1f) * 100);
        PixelsOfMc.LOGGER.info("{}, {}", efficiencyBonus, bonus);
        if (efficiencyBonus == 50)
            return Component.literal("§6+" + efficiencyBonus + "%");
        else if (efficiencyBonus == 100) {
            return Component.literal("§2+" + efficiencyBonus + "%");
        }
        else if (efficiencyBonus == 200) {
            return Component.literal("§a+" + efficiencyBonus + "%");
        }
        return Component.literal("§c+" + efficiencyBonus + "%");
    }
}
