package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.SDSFusionControllerGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.gui.renderer.InfiniteEnergyInfoArea;
import net.turtlemaster42.pixelsofmc.gui.widget.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class SDSFusionControllerGuiScreen extends AbstractPOMscreen<SDSFusionControllerGuiMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/sps_controller_gui.png");

    private InfiniteEnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private EditBox editBox;
    private RedSwitchButton switch1;
    private RedSwitchButton switch2;
    private RedSwitchButton switch3;
    private SlotLockButton slotLock0;
    private SlotLockButton slotLock1;
    private SlotLockButton slotLock2;
    private SlotLockButton slotLock3;
    private SlotLockButton slotLock4;
    private SlotLockButton slotLock5;
    private SlotLockButton slotLock6;
    private SlotLockButton slotLock7;
    private SlotLockButton slotLock8;

    public SDSFusionControllerGuiScreen(SDSFusionControllerGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.editBox.tick();
    }

    @Override
    protected void init() {
        super.init();
        assignEditBox();
        assignEnergyInfoArea();
        assignFluidRenderer();
        assignSlotLocks();
        assignSwitchButtons();
    }

    @Override
    public int getExtraWidth() {
        return 209;
    }

    @Override
    public int getExtraHeight() {
        return 90;
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        this.editBox.setEditable(true);
        if (pKeyCode == 256 || pKeyCode == this.minecraft.options.keyInventory.getKey().getValue()) {
            if (this.editBox.getValue().isEmpty())
                this.editBox.setValue("0");
            this.menu.setSlotLimit(Integer.parseInt(this.editBox.getValue()));
            this.minecraft.player.closeContainer();
        }
        if (pKeyCode == 257) {
            if (this.editBox.getValue().isEmpty())
                this.editBox.setValue("0");
            this.menu.setSlotLimit(Integer.parseInt(this.editBox.getValue()));
            this.editBox.setFocus(false);
            return false;
        }
        if ((pKeyCode >= 48 && pKeyCode <= 57) || (pKeyCode >= 259 && pKeyCode <= 329)) {
            PixelsOfMc.LOGGER.info("KeyCode: {}", pKeyCode);
            return this.editBox.keyPressed(pKeyCode, pScanCode, pModifiers) || this.editBox.canConsumeInput() || (super.keyPressed(pKeyCode, pScanCode, pModifiers));
        }
        this.editBox.setEditable(false);
        return this.editBox.canConsumeInput() || super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.font.draw(pPoseStack, Component.translatable("tooltip.pixelsofmc.gui.sds_fusion"), 5, 4, 4210752);
        if (menu.getReason() == 1) {
            renderErrorArea(pPoseStack, pMouseX, pMouseY, x, y);
        }
        renderEnergyArea(pPoseStack, pMouseX, pMouseY, x, y);
        renderFluidArea(pPoseStack, pMouseX, pMouseY, x, y, Component.translatable("tooltip.pixelsofmc.fluid.input"));
        renderDuoFluidArea(pPoseStack, pMouseX, pMouseY, x, y, Component.translatable("tooltip.pixelsofmc.fluid.output"));
        renderMaxStackArea(pPoseStack, pMouseX, pMouseY, x, y);
        renderLockSlotArea(pPoseStack, pMouseX, pMouseY, x, y);
        renderFusionPowerArea(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 9, 22, 9, 44)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderFluidArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, Component extra) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 169, 6, renderer.getWidth(), renderer.getHeight())) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL, extra),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderDuoFluidArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, Component extra) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 188, 6, renderer.getWidth(), renderer.getHeight())) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getDuoFluid(), TooltipFlag.Default.NORMAL, extra),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderErrorArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 90, 36, 46, 14)) {
            renderTooltip(pPoseStack,
                    List.of(Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion.1"),
                            Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion.2"),
                            Component.translatable("tooltip.pixelsofmc.gui.info.crafting.fusion.3", menu.getElement(), Element.values()[menu.getElement()-1].elementName())),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderFusionPowerArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 168, -9, 36, 6)) {
            renderTooltip(pPoseStack,
                    List.of(Component.translatable("tooltip.pixelsofmc.gui.info.fusion_power", "§6"+menu.blockEntity.getFusionPower(), "§6"+menu.blockEntity.getMaxFusionPower())),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderMaxStackArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 95, 57, 21, 10)) {
            renderTooltip(pPoseStack,
                    List.of(Component.translatable("tooltip.pixelsofmc.gui.info.max_stack_size")),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderLockSlotArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 92, 10, 16, 16)) {
            renderTooltip(pPoseStack,
                    List.of(Component.translatable("tooltip.pixelsofmc.gui.info.lock_slots")),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }


    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y - 15, 0, 0, imageWidth + 33, imageHeight + 17);

        if(menu.isCrafting()) {
            blit(pPoseStack, x + 89, y + 37, 0, 183, menu.getScaledProgress(), 13);
        }
        if (menu.getReason() > 0) {
            blit(pPoseStack, x + 90, y + 37, 0, 196, 46, 12);
        }
        blit(pPoseStack, x + 9, y + 66 - menu.getScaledEnergy(), 209, 44 - menu.getScaledEnergy(), 10, 44);
        blit(pPoseStack, x + 168, y - 9, 209, 88, menu.getScaledFusionPower(), 6);

        this.editBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        this.slotLock0.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock1.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock2.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock3.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock4.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock5.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock6.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock7.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.slotLock8.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        renderer.render(pPoseStack, x + 169, y + 6, menu.getFluid());
        renderer.render(pPoseStack, x + 188, y + 6, menu.getDuoFluid());
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, pPartialTick);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new InfiniteEnergyInfoArea(x + 9,
                y + 22, menu.blockEntity.getEnergyStorage(), 10, 44);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(50000, true, 15, 67);
    }
    private void assignEditBox() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.editBox = new EditBox(this.font, x + 95, y + 57, 21, 10, Component.literal(""));
        this.editBox.setCanLoseFocus(true);
        this.editBox.setTextColor(new Color(0, 230, 100).getRGB());
        this.editBox.setTextColorUneditable(new Color(0, 230, 100).getRGB());
        this.editBox.setBordered(true);
        this.editBox.setMaxLength(2);
        this.editBox.setValue(String.valueOf(menu.getSlotLimit()));
        this.addWidget(this.editBox);
        this.editBox.setEditable(true);
    }

    private void assignSlotLocks() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.slotLock0 = new SlotLockButton(x + 92, y + 10, x + 35, y + 18, (pButton) -> {
            slotLock0.cycleLocked();
            menu.setSlotLock(slotLock0.isLocked(), 0);
        });
        this.slotLock0.setLocked(menu.getSlotLock(0));
        this.addRenderableWidget(this.slotLock0);

        this.slotLock1 = new SlotLockButton(x + 97, y + 10, x + 53, y + 18, (pButton) -> {
            slotLock1.cycleLocked();
            menu.setSlotLock(slotLock1.isLocked(), 1);
        });
        this.slotLock1.setLocked(menu.getSlotLock(1));
        this.addRenderableWidget(this.slotLock1);

        this.slotLock2 = new SlotLockButton(x + 102, y + 10, x + 71, y + 18, (pButton) -> {
            slotLock2.cycleLocked();
            menu.setSlotLock(slotLock2.isLocked(), 2);
        });
        this.slotLock2.setLocked(menu.getSlotLock(2));
        this.addRenderableWidget(this.slotLock2);

        this.slotLock3 = new SlotLockButton(x + 92, y + 15, x + 35, y + 36, (pButton) -> {
            slotLock3.cycleLocked();
            menu.setSlotLock(slotLock3.isLocked(), 3);
        });
        this.slotLock3.setLocked(menu.getSlotLock(3));
        this.addRenderableWidget(this.slotLock3);

        this.slotLock4 = new SlotLockButton(x + 97, y + 15, x + 53, y + 36, (pButton) -> {
            slotLock4.cycleLocked();
            menu.setSlotLock(slotLock4.isLocked(), 4);
        });
        this.slotLock4.setLocked(menu.getSlotLock(4));
        this.addRenderableWidget(this.slotLock4);

        this.slotLock5 = new SlotLockButton(x + 102, y + 15, x + 71, y + 36, (pButton) -> {
            slotLock5.cycleLocked();
            menu.setSlotLock(slotLock5.isLocked(), 5);
        });
        this.slotLock5.setLocked(menu.getSlotLock(5));
        this.addRenderableWidget(this.slotLock5);

        this.slotLock6 = new SlotLockButton(x + 92, y + 20, x + 35, y + 54, (pButton) -> {
            slotLock6.cycleLocked();
            menu.setSlotLock(slotLock6.isLocked(), 6);
        });
        this.slotLock6.setLocked(menu.getSlotLock(6));
        this.addRenderableWidget(this.slotLock6);

        this.slotLock7 = new SlotLockButton(x + 97, y + 20, x + 53, y + 54, (pButton) -> {
            slotLock7.cycleLocked();
            menu.setSlotLock(slotLock7.isLocked(), 7);
        });
        this.slotLock7.setLocked(menu.getSlotLock(7));
        this.addRenderableWidget(this.slotLock7);

        this.slotLock8 = new SlotLockButton(x + 102, y + 20, x + 71, y + 54, (pButton) -> {
            slotLock8.cycleLocked();
            menu.setSlotLock(slotLock8.isLocked(), 8);
        });
        this.slotLock8.setLocked(menu.getSlotLock(8));
        this.addRenderableWidget(this.slotLock8);
    }

    private void assignSwitchButtons() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.switch1 = new RedSwitchButton(x + 127, y + 6, (pButton) -> {
            switch1.cycleOn();
            menu.setSwitch(switch1.isOn(), 0);
        });
        this.switch1.setOn(menu.getSwitch(0));
        this.addRenderableWidget(this.switch1);

        this.switch2 = new RedSwitchButton(x + 136, y + 6, (pButton) -> {
            switch2.cycleOn();
            menu.setSwitch(switch2.isOn(), 1);
        });
        this.switch2.setOn(menu.getSwitch(1));
        this.addRenderableWidget(this.switch2);

        this.switch3 = new RedSwitchButton(x + 145, y + 6, (pButton) -> {
            switch3.cycleOn();
            menu.setSwitch(switch3.isOn(), 2);
        });
        this.switch3.setOn(menu.getSwitch(2));
        this.addRenderableWidget(this.switch3);
    }
}
