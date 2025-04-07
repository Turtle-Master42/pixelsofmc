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
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.NuclearReactorMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.widget.BigRedSwitchButton;
import net.turtlemaster42.pixelsofmc.gui.widget.RedSwitchButton;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
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
    private BigRedSwitchButton bigRedSwitch;
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
            guiGraphics.renderTooltip(Minecraft.getInstance().font, List.of(efficiencyBonusTooltip(menu.getEfficiencyBonus()), energyPerTickTooltip(menu.blockEntity.getItemStackHandler(), menu.getEfficiencyBonus())), Optional.empty(), mouseX - x, mouseY - y);
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

        this.bigRedSwitch = new BigRedSwitchButton(x + 42, y + 30, Component.literal("§dLock/Unlock"), (pButton) -> {
            bigRedSwitch.cycleOn();
            menu.setSwitch(bigRedSwitch.isOn(), 3);
            fuelCellUp = !menu.blockEntity.getItemStackHandler().getStackInSlot(0).isEmpty();
            fuelCellDown = !menu.blockEntity.getItemStackHandler().getStackInSlot(1).isEmpty();
            fuelCellRight = !menu.blockEntity.getItemStackHandler().getStackInSlot(2).isEmpty();
            fuelCellLeft = !menu.blockEntity.getItemStackHandler().getStackInSlot(3).isEmpty();
        });
        this.bigRedSwitch.setOn(menu.getSwitch(3));
        this.addRenderableWidget(this.bigRedSwitch);
    }

    private Component efficiencyBonusTooltip(float bonus) {
        int efficiencyBonus = (int) ((bonus - 1f) * 100);
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
                heating += fuelCell.getEnergyPerTick(stackHandler.getStackInSlot(i));
            }
        }

        //TODO: make this nicer
        int total = 0;
        if (fuelCellUp)
            total += 1;
        if (fuelCellDown)
            total += 1;
        if (fuelCellRight)
            total += 1;
        if (fuelCellLeft)
            total += 1;

        if (total > 0)
            heating = heating + Math.round(((double) heating / ((double) total)) * (bonus - 1));

        return Component.literal(heating + " FE/t").withStyle(ChatFormatting.GOLD);
    }
}
