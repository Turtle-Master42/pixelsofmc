package net.turtlemaster42.pixelsofmc.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.EnergyArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.GuiTooltips;
import net.turtlemaster42.pixelsofmc.gui.renderer.NameArea;
import net.turtlemaster42.pixelsofmc.gui.renderer.ProgressArea;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotIsostaticPressScreen extends AbstractPOMscreen<HotIsostaticPressMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/hot_isostatic_press_gui.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");
    private EnergyArea energyArea;
    private NameArea nameArea;
    private ProgressArea progressArea;
    private Boolean pressed = false;

    public HotIsostaticPressScreen(HotIsostaticPressMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignAreas();
        this.addRenderableWidget(new ImageButton((width - imageWidth) / 2 + 8, (height - imageHeight) / 2 + 69, 6, 6, pressed?0:6, 34, 6, BUTTON,256, 256,
                (onPress) -> pressed=!pressed,
                Component.literal("§eHOVERING")));
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderTooltip(guiGraphics, mouseX, mouseY, x, y);
        renderTooltip2(guiGraphics);

        nameArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        energyArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);
        progressArea.fillTooltip(guiGraphics, x, y, mouseX, mouseY);

//        if (!Screen.hasControlDown()) {
        if (menu.isHeating()) {
            int offsetY = 0;
            if (hoveredSlot != null && hoveredSlot.hasItem())
                offsetY = -15;
            renderArea(guiGraphics, mouseX, mouseY, 0, offsetY, x, y, 50, 56, 67, 75, new GuiTooltips().getTimeArea(menu.getTime() + menu.getSoulTime()));
        }
        renderArea(guiGraphics, mouseX, mouseY, x, y, 37, 56, 49, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
        renderArea(guiGraphics, mouseX, mouseY, x, y, 50, 77, 67, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
        renderArea(guiGraphics, mouseX, mouseY, x, y, 68, 56, 77, 81, new GuiTooltips().getHeatArea(menu.getHeat(), menu.getRequiredHeat(), menu.getRequiredMaxHeat()));
//        }
    }

    private void renderTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(Screen.hasControlDown()) {
            pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderTooltip2(GuiGraphics pGuiGraphics) {
        if(pressed) {
            pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, getAllAreas(menu.getProgress(), menu.getMaxProgress(), menu.getHeat(), menu.getTime() + menu.getSoulTime(), menu.getEnergy(), menu.getMaxEnergy()), 167, 83);
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2 ;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth + 9, imageHeight + 2);

        nameArea.draw(guiGraphics);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 69, y + 38, 0, 168, menu.getScaledProgressOne(), 11);
            guiGraphics.blit(TEXTURE, x + 55, y + 27, 39, 168, 9, menu.getScaledProgressTwo());
        }
        guiGraphics.blit(TEXTURE, x + 9, y + 66 - menu.getScaledEnergy(), 185, 44-menu.getScaledEnergy(), 10, 44);//energy

        guiGraphics.blit(TEXTURE, x + 36, y + 82 - menu.getScaledSoulHeat(), 195, 69-menu.getScaledSoulHeat(), 45, menu.getScaledSoulHeat());//soul heat
        guiGraphics.blit(TEXTURE, x + 36, y + 55, 195, 14, 45, menu.getScaledHeat());//heat
        if(menu.isHeating())
            guiGraphics.blit(TEXTURE, x + 51, y + 75-menu.getScaledBurnTime(), 240, 29-menu.getScaledBurnTime(), 16, menu.getScaledBurnTime());//time
        if (pressed)
            guiGraphics.blit(TEXTURE, x + 6, y + 75, 252, 10, 4, 4);//button on
        else
            guiGraphics.blit(TEXTURE, x + 6, y + 75, 248, 10, 4, 4);//button off
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, mouseX, mouseY, delta);
        renderTooltip(pGuiGraphics, mouseX, mouseY);
    }

    private void assignAreas() {
        int x = ((width - imageWidth) / 2);
        int y = ((height - imageHeight) / 2);

        energyArea = new EnergyArea(x + 11, y + 22,
                menu.blockEntity.getEnergyStorage(), 10, 44);
        nameArea = new NameArea(menu.blockEntity.getDisplayName(), x, y - 16);
        progressArea = new ProgressArea(menu.getProgress(), menu.getMaxProgress(),
                new Rect2i(x + 54, y + 27, 10, 5),
                new Rect2i(x + 69, y + 39, 39, 10)
        );
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