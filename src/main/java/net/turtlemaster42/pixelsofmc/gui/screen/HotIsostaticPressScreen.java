package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressGuiMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyInfoArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class HotIsostaticPressScreen extends AbstractPOMscreen<HotIsostaticPressGuiMenu> {
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
        this.addRenderableWidget(new ImageButton((width - imageWidth) / 2 + 8, (height - imageHeight) / 2 + 69, 6, 6, pressed?0:6, 34, 6, BUTTON,256, 256,
                (onPress) -> pressed=!pressed,
                Component.literal("§eHOVERING")));
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderTooltip(pPoseStack, pMouseX, pMouseY, x, y);
        renderTooltip2(pPoseStack);

        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 55, 27, 62, 31, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));
        renderArea(pPoseStack, pMouseX, pMouseY, x, y, 70, 37, 106, 48, new GuiTooltips().getProgressArea(menu.getProgress(), menu.getMaxProgress()));

        this.font.draw(pPoseStack, "H.I.P", 5, 4, 4210752);
        if (!Screen.hasControlDown()) {
            renderEnergyArea(pPoseStack, pMouseX, pMouseY, x, y);
            if (menu.isHeating()) {
                int offsetY = 0;
                if (hoveredSlot != null && hoveredSlot.hasItem())
                    offsetY = -15;
                renderArea(pPoseStack, pMouseX, pMouseY, 0, offsetY, x, y, 50, 56, 67, 75, new GuiTooltips().getTimeArea(menu.getTime() + menu.getSoulTime()));
            }
            renderArea(pPoseStack, pMouseX, pMouseY, x, y, 37, 56, 49, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
            renderArea(pPoseStack, pMouseX, pMouseY, x, y, 50, 77, 67, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
            renderArea(pPoseStack, pMouseX, pMouseY, x, y, 68, 56, 77, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
        }
    }

    private void renderEnergyArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 11, 22, 9, 44)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
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
            renderTooltip(pPoseStack, getAllAreas(menu.getProgress(), menu.getMaxProgress(), menu.getHeat(), menu.getTime() + menu.getSoulTime(), menu.getEnergy(), menu.getMaxEnergy()),
                    Optional.empty(), 167, 83);
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
            blit(pPoseStack, x + 70, y + 37, 0, 168, menu.getScaledProgressOne(), 12);
            blit(pPoseStack, x + 55, y + 27, 38, 168, 9, menu.getScaledProgressTwo());
        }
        blit(pPoseStack, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);//energy

        blit(pPoseStack, x + 36, y + 82 - menu.getScaledSoulHeat(), 195, 69-menu.getScaledSoulHeat(), 45, menu.getScaledSoulHeat());//soul heat
        blit(pPoseStack, x + 36, y + 55, 195, 14, 45, menu.getScaledHeat());//heat
        if(menu.isHeating())
            blit(pPoseStack, x + 51, y + 75-menu.getScaledBurnTime(), 240, 29-menu.getScaledBurnTime(), 16, menu.getScaledBurnTime());//time
        if (pressed)
            blit(pPoseStack, x + 6, y + 75, 252, 10, 4, 4);//button on
        else
            blit(pPoseStack, x + 6, y + 75, 248, 10, 4, 4);//button off
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) + 11,
                ((height - imageHeight) / 2) + 22, menu.blockEntity.energyStorage, 10, 44);
    }

    public List<Component> getAllAreas(int progress, int maxProgress, int heat, int time, int energy, int maxEnergy) {
        String l1 = "";
        String l2 = "";
        int multi = 1;
        if (maxEnergy >= 1000000) {l1="K"; l2="M";}
        else if (maxEnergy >= 1000000000) {l1="M"; l2="G"; multi=1000;}

        if (Screen.hasShiftDown())
            return List.of(
                    Component.literal("§c"+(heat+273)+" K"),
                    Component.literal("§9"+(time/20)+" s"),
                    Component.literal("§6"+energy+"§r§7 FE"),
                    Component.literal("§e"+maxEnergy+"§r§7 FE"),
                    Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%"),
                    Component.literal("§9"+((maxProgress/20)-(progress/20))+" s")
            );
        else return List.of(
                Component.literal("§c"+(heat)+" °C"),
                Component.literal("§9"+(time/20)+" s"),
                Component.literal(("§6"+energy/1000*multi) + "." + ((energy/10*multi) - ((energy/1000*multi)*100)+"§r§7 "+l1+"FE")),
                Component.literal(("§e"+ (float) (maxEnergy / 10000*multi) /100) + "§r§7 "+l2+"FE"),
                Component.literal("§7"+(int)(100f/(float)maxProgress*(float)progress)+"%")
        );
    }
}