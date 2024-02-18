package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalCombinerGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyInfoArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChemicalCombinerScreen extends AbstractPOMscreen<ChemicalCombinerGuiMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/chemical_combiner_gui.png");
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;

    public ChemicalCombinerScreen(ChemicalCombinerGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.font.draw(pPoseStack, menu.blockEntity.getDisplayName(), 6, 5, 4210752);
        renderEnergyArea(pPoseStack, pMouseX, pMouseY, x, y);
        renderFluidArea(pPoseStack, pMouseX, pMouseY, x, y, Component.translatable("tooltip.pixelsofmc.fluid.input"));
        renderDuoFluidArea(pPoseStack, pMouseX, pMouseY, x, y, Component.translatable("tooltip.pixelsofmc.fluid.output"));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 54, 27, 82, 40, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 62, 41, 82, 58, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 72, 59, 114, 71, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
    }

    private void renderFluidArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, Component extra) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 114, 12, renderer.getWidth(), renderer.getHeight())) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL, extra),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderDuoFluidArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, Component extra) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 114, 27, renderer.getWidth(), renderer.getHeight())) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getDuoFluid(), TooltipFlag.Default.NORMAL, extra),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 11, 22, 9, 44)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;
        this.blit(pPoseStack, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        if(menu.isCrafting()) {
            blit(pPoseStack, x + 54, y + 29, 0, 168, menu.getScaledProgress(), 41);
        }
        blit(pPoseStack, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);
        renderer.render(pPoseStack, x + 114, y + 12, menu.getFluid());
        renderer.render(pPoseStack, x + 114, y + 27, menu.getDuoFluid());
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) + 11,
                ((height - imageHeight) / 2) + 22, menu.blockEntity.getEnergyStorage(), 10, 44);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(16000, true, 25, 11);
    }
}

