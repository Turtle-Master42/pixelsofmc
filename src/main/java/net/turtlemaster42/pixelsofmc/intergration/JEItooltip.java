package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JEItooltip implements IRecipeSlotTooltipCallback {
    final String add;
    public JEItooltip(int display) {this.add = String.valueOf(display);}
    public JEItooltip(float display) {this.add = String.valueOf(display);}
    public JEItooltip(boolean display) {this.add = String.valueOf(display);}
    public JEItooltip(long display) {this.add = String.valueOf(display);}
    public JEItooltip(String display) {this.add = String.valueOf(display);}

    @Override
    public void onTooltip(@NotNull IRecipeSlotView recipeSlotView, List<Component> tooltip) {
        tooltip.add(Component.literal(add));
    }
}
