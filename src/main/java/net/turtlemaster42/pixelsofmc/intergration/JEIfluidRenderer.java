package net.turtlemaster42.pixelsofmc.intergration;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JEIfluidRenderer implements mezz.jei.api.ingredients.IIngredientRenderer<FluidStack> {

    private final FluidTankRenderer renderer;

    public JEIfluidRenderer(long capacity, boolean showCapacity, int width, int height) {
        this.renderer = new FluidTankRenderer(capacity, showCapacity, width, height);
    }

    @Override
    public int getHeight() {
        return renderer.getHeight();
    }

    @Override
    public int getWidth() {
        return renderer.getWidth();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @Nullable FluidStack ingredient, int xPosition, int yPosition) {
        this.renderer.render(guiGraphics, xPosition, yPosition, ingredient);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull FluidStack ingredient) {

    }

    @Override
    public @NotNull List<Component> getTooltip(@NotNull FluidStack ingredient, @NotNull TooltipFlag tooltipFlag) {
        return renderer.getTooltip(ingredient);
    }
}
