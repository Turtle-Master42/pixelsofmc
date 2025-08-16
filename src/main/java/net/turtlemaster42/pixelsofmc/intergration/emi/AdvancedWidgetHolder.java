package net.turtlemaster42.pixelsofmc.intergration.emi;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TankWidget;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

public class AdvancedWidgetHolder implements WidgetHolder {
    public final static ResourceLocation CHANCE = Util.resourceLocation("textures/gui/widgets/widgets.png");

    public final int width;
    public final int height;
    public final WidgetHolder widgetHolder;

    public AdvancedWidgetHolder(WidgetHolder widgetHolder) {
        this.width = widgetHolder.getWidth();
        this.height = widgetHolder.getHeight();
        this.widgetHolder = widgetHolder;
    }

    public SlotWidget addSlot(ItemStack stack, int x, int y) {
        return widgetHolder.add(new SlotWidget(EmiStack.of(stack), x - 1, y - 1));
    }

    public SlotWidget addSlot(Ingredient ingredient, int x, int y) {
        return widgetHolder.add(new SlotWidget(EmiIngredient.of(ingredient), x - 1, y - 1));
    }

    public SlotWidget addSlot(CountedIngredient ingredient, int x, int y) {
        return widgetHolder.add(new SlotWidget(EmiIngredient.of(ingredient.ingredient(), ingredient.count()), x - 1, y - 1));
    }

    public SlotWidget addSlot(ChanceIngredient ingredient, int x, int y) {
        if (ingredient.isEmpty())
            return null;
        if (ingredient.chance() >= 1f)
            return widgetHolder.add(new SlotWidget(EmiIngredient.of(ingredient.ingredient(), ingredient.count()), x - 1, y - 1));

        if (ingredient.chance() < 1f) {
            widgetHolder.addText(Component.literal("§6"+Math.round(ingredient.chance() * 100) + "%"), x - 3, y, 0, true);
        }

        return widgetHolder
                .add(new SlotWidget(EmiIngredient.of(ingredient.ingredient(), ingredient.count()), x, y))
                .customBackground(CHANCE, ingredient.chance() < 0.5f ? 16 : 0, 0, 16, 16);
    }

    public TankWidget addTank(FluidStack stack, int x, int y, int width, int height) {
        return (TankWidget) widgetHolder
                .add(new TankWidget(EmiStack.of(stack.getFluid(), stack.getAmount()), x - 1, y - 1, width + 2, height + 2, (int)(stack.getAmount() * 1.2f)).drawBack(false)).drawBack(false);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public WidgetHolder getWidgetHolder() {
        return widgetHolder;
    }

    @Override
    public <T extends Widget> T add(T t) {
        return null;
    }
}
