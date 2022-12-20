package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public class JEItooltip implements IRecipeSlotTooltipCallback {
    String add;
    public JEItooltip(int display) {this.add = String.valueOf(display);}
    public JEItooltip(float display) {this.add = String.valueOf(display);}
    public JEItooltip(boolean display) {this.add = String.valueOf(display);}
    public JEItooltip(long display) {this.add = String.valueOf(display);}
    public JEItooltip(String display) {this.add = String.valueOf(display);}

    @Override
    public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
        tooltip.add(new TextComponent(add));
    }
}
