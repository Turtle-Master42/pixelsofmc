package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyInfoArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;

import java.util.List;
import java.util.Optional;

public class HotIsostaticPressScreen extends AbstractContainerScreen<HotIsostaticPressGuiMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/hot_isotopic_press_gui.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");
    private EnergyInfoArea energyInfoArea;
    private Boolean pressed = false;

    public HotIsostaticPressScreen(HotIsostaticPressGuiMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        this.addRenderableWidget(new ImageButton((width - imageWidth) / 2 + 8, (height - imageHeight) / 2 + 69, 6, 6, pressed?0:6, 34, 6, BUTTON,256, 256, (onPress) -> {
            pressed=!pressed;
        }, new TextComponent("§eHOVERING")));
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderTooltip(pPoseStack, pMouseX, pMouseY, x, y);
        renderTooltip2(pPoseStack);

        this.font.draw(pPoseStack, "H.I.P", 5, 4, 4210752);
        if (!Screen.hasControlDown()) {
            renderEnergyArea(pPoseStack, pMouseX, pMouseY, x, y);
            if (menu.isHeating())
                renderArea(pPoseStack, pMouseX, pMouseY, x, y, 51, 55, 65, 69, new GuiTooltips().getTimeArea(menu.getTime()));
            renderArea(pPoseStack, pMouseX, pMouseY, x, y, 28, 75, 68, 79, new GuiTooltips().getHeatArea(menu.getHeat()));
        }
    }

    private void renderEnergyArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 11, 22, 9, 44)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int fromX, int fromY, int toX, int toY, List<Component> tooltip) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, fromX, fromY, toX - fromX, toY-fromY)) {
            renderTooltip(pPoseStack, tooltip,
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(Screen.hasControlDown()) {
            renderTooltip(pPoseStack,
                    List.of(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderTooltip2(PoseStack pPoseStack) {
        if(pressed) {
            renderTooltip(pPoseStack, getAllAreas(5, 14, menu.getHeat(), menu.getTime(), menu.getEnergy(), menu.getMaxEnergy()),
                    Optional.empty(), 167, 65);
        }
    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth + 9, imageHeight + 2);
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 70, y + 37, 0, 168, menu.getScaledProgressOne(), 12);
            blit(pPoseStack, x + 55, y + 27, 38, 168, 8, menu.getScaledProgressTwo());
        }
        blit(pPoseStack, x + 11, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);//energy
        blit(pPoseStack, x + 28, y + 75, 209, 0, menu.getScaledHeat(), 4);//heat
        if(menu.isHeating())
            blit(pPoseStack, x + 51, y + 57 + 13 - menu.getScaledBurnTime(), 195, 14- menu.getScaledBurnTime(), 14, menu.getScaledBurnTime()+1);//time
        if (!pressed)
            blit(pPoseStack, x + 8, y + 69, 250, 10, 6, 6);//button
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) + 11,
                ((height - imageHeight) / 2) + 22, menu.blockEntity.energyStorage, 10, 44);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public List<Component> getAllAreas(int progress, int maxProgress, int heat, int time, int energy, int maxEnergy) {
        String l1 = "";
        String l2 = "";
        int multi = 1;
        if (maxEnergy >= 1000000) {l1="K"; l2="M";}
        else if (maxEnergy >= 1000000000) {l1="M"; l2="G"; multi=1000;}

        if (Screen.hasShiftDown())
            return List.of(
                    new TextComponent("§c"+(heat+273)+" K"),
                    new TextComponent("§9"+(time/20)+" s"),
                    new TextComponent("§6"+energy+"§r§7 FE"),
                    new TextComponent("§e"+maxEnergy+"§r§7 FE"),
                    new TextComponent("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"),
                    new TextComponent("§9"+((maxProgress/20)-(progress/20))+" s")
            );
        else return List.of(
                new TextComponent("§c"+(heat)+" °C"),
                new TextComponent("§9"+(time/20)+" s"),
                new TextComponent(("§6"+energy/1000*multi) + "." + ((energy/10*multi) - ((energy/1000*multi)*100)+"§r§7 "+l1+"FE")),
                new TextComponent(("§e"+ (float) (maxEnergy / 10000*multi) /100) + "§r§7 "+l2+"FE"),
                new TextComponent("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%")
        );
    }


}

