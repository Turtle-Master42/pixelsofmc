package net.turtlemaster42.pixelsofmc.intergration.emi;

import dev.emi.emi.api.EmiExclusionArea;
import dev.emi.emi.api.widget.Bounds;
import net.minecraft.client.gui.screens.Screen;
import net.turtlemaster42.pixelsofmc.gui.screen.AbstractPOMscreen;

import java.util.function.Consumer;

public class ExclusionArea implements EmiExclusionArea<Screen> {

    @Override
    public void addExclusionArea(Screen screen, Consumer<Bounds> consumer) {
        if (screen instanceof AbstractPOMscreen<?> gui) {
            int screenX = gui.getGuiLeft();
            int screenY = gui.getGuiTop();
            int screenWidth = gui.getExtraWidth();
            int screenHeight = gui.getExtraHeight();
            consumer.accept(new Bounds(screenX, screenY, screenWidth, screenHeight));
        }
    }
}

