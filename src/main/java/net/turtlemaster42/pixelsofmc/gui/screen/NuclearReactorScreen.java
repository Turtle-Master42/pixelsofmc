package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.NuclearReactorMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.IUpdatableWidgets;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.widget.BigSwitchButton;
import net.turtlemaster42.pixelsofmc.gui.widget.SpriteCycleButton;
import net.turtlemaster42.pixelsofmc.gui.widget.SwitchButton;
import net.turtlemaster42.pixelsofmc.gui.widget.TemperatureScale;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class NuclearReactorScreen extends AbstractPOMscreen<NuclearReactorMenu> implements IUpdatableWidgets {
    private static final ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/nuclear_reactor_gui.png");

    private EnergyArea energyArea;
    private NameArea nameArea;
    private FluidArea fluidArea1;
    private FluidArea fluidArea2;
    private SwitchButton switch1;
    private SwitchButton switch2;
    private SwitchButton switch3;
    private BigSwitchButton bigSwitch;
    private boolean fuelCellUp = false;
    private boolean fuelCellDown = false;
    private boolean fuelCellRight = false;
    private boolean fuelCellLeft = false;

    public NuclearReactorScreen(NuclearReactorMenu guiMenu, Inventory playerInventory, Component title) {
        super(guiMenu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        assignAreas();
        assignButtons();
        fuelCellUp = !menu.blockEntity.getItemStackHandler().getStackInSlot(0).isEmpty();
        fuelCellDown = !menu.blockEntity.getItemStackHandler().getStackInSlot(2).isEmpty();
        fuelCellRight = !menu.blockEntity.getItemStackHandler().getStackInSlot(1).isEmpty();
        fuelCellLeft = !menu.blockEntity.getItemStackHandler().getStackInSlot(3).isEmpty();
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

        if (mouseX >= x + 80 && mouseY >= y + 35 && mouseX < x + 95 && mouseY < y + 50) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(
                    efficiencyBonusTooltip(menu.getEfficiencyBonus()),
                    energyPerTickTooltip(menu.blockEntity.getItemStackHandler(), menu.getEfficiencyBonus()),
                    Component.literal(String.valueOf(menu.data.get(2)))
            ), Optional.empty(), mouseX - x, mouseY - y);
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

        if (fuelCellUp && fuelCellLeft) {
            guiGraphics.blit(TEXTURE, x + 70, y + 25, 0, 168, 5, 5);
            guiGraphics.blit(TEXTURE, x + 81, y + 36, 0, 173, 3, 3);
        }
        if (fuelCellRight && fuelCellUp) {
            guiGraphics.blit(TEXTURE, x + 101, y + 25, 5, 168, 5, 5);
            guiGraphics.blit(TEXTURE, x + 92, y + 36, 3, 173, 3, 3);
        }
        if (fuelCellDown && fuelCellRight) {
            guiGraphics.blit(TEXTURE, x + 101, y + 56, 0, 168, 5, 5);
            guiGraphics.blit(TEXTURE, x + 92, y + 47, 0, 173, 3, 3);
        }
        if (fuelCellDown && fuelCellLeft) {
            guiGraphics.blit(TEXTURE, x + 70, y + 56, 5, 168, 5, 5);
            guiGraphics.blit(TEXTURE, x + 81, y + 47, 3, 173, 3, 3);
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
        this.switch1 = new SwitchButton(x + 127, y + 6, Component.literal("§6???"), (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(0, switch1.isOn());
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);

        this.switch2 = new SwitchButton(x + 136, y + 6, Component.literal("§bToggle Liquid Cooling"), (pButton) -> {
            switch2.cycleOn();
            menu.setSwitch(1, switch2.isOn());
        });
        this.switch2.setOn(menu.getSwitch(1));
        this.addRenderableWidget(this.switch2);

        this.switch3 = new SwitchButton(x + 145, y + 6, Component.literal("§d???"), (pButton) -> {
            switch3.cycleOn();
            menu.setSwitch(2, switch3.isOn());
        });
        this.switch3.setOn(menu.getSwitch(2));
        this.addRenderableWidget(this.switch3);

        this.bigSwitch = new BigSwitchButton(x + 42, y + 30, Component.literal("§dLock/Unlock"), (pButton) -> {
            if (menu.data.get(2) < 20_000_000) {
                bigSwitch.cycleOn();
                menu.setSwitch(3, bigSwitch.isOn());
            }
            fuelCellUp = !menu.blockEntity.getItemStackHandler().getStackInSlot(0).isEmpty();
            fuelCellDown = !menu.blockEntity.getItemStackHandler().getStackInSlot(2).isEmpty();
            fuelCellRight = !menu.blockEntity.getItemStackHandler().getStackInSlot(1).isEmpty();
            fuelCellLeft = !menu.blockEntity.getItemStackHandler().getStackInSlot(3).isEmpty();
        });
        this.bigSwitch.setOn(menu.getSwitch(3));
        this.addRenderableWidget(this.bigSwitch);
    }

    private Component efficiencyBonusTooltip(float bonus) {
        int efficiencyBonus = (int) ((bonus - 1f) * 400);
        if (efficiencyBonus == 50)
            return Component.literal("§6+" + efficiencyBonus + "%");
        else if (efficiencyBonus == 100) {
            return Component.literal("§2+" + efficiencyBonus + "%");
        }
        else if (efficiencyBonus == 200) {
            return Component.literal("§a+" + efficiencyBonus + "%");
        }
        return Component.literal("§c§l+" + efficiencyBonus + "%");
    }

    private Component energyPerTickTooltip(ItemStackHandler stackHandler, float bonus) {
        long heating = 0;
        for (int i = 0; i < 4; i++) {
            if (stackHandler.getStackInSlot(i).getItem() instanceof FuelCellItem fuelCell) {
                heating += fuelCell.getEnergyPerTick();
            }
        }
        return Component.literal(((long) (heating * bonus)) + " FE/t").withStyle(ChatFormatting.GOLD);
    }

    public void forceUpdateWidgets() {
        this.switch1.setOn(menu.getSwitch(0));
        this.switch2.setOn(menu.getSwitch(1));
        this.switch3.setOn(menu.getSwitch(2));
        this.bigSwitch.setOn(menu.getSwitch(3));
    }

    @Override
    public BlockEntity getBlockEntity() {
        return menu.blockEntity;
    }
}
