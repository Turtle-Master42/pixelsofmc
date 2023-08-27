package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import net.turtlemaster42.pixelsofmc.gui.screen.AbstractPOMscreen;

import java.util.ArrayList;
import java.util.List;

public class GuiSizeExtension implements IGuiContainerHandler<AbstractPOMscreen<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(AbstractPOMscreen<?> containerScreen) {
        int screenX = containerScreen.getGuiLeft();
        int screenY = containerScreen.getGuiTop();
        int screenWidth = containerScreen.getExtraWidth();
        int screenHeight = containerScreen.getExtraHeight();
        List<Rect2i> extraAreas = new ArrayList<>();
        extraAreas.add(new Rect2i(screenX, screenY, screenWidth, screenHeight));
        return extraAreas;
    }
}
