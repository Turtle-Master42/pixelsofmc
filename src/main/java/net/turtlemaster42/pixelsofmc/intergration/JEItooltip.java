package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.network.chat.Component;

public class JEItooltip implements IRecipeSlotRichTooltipCallback {
    final String add;
    public JEItooltip(int display) {this.add = String.valueOf(display);}
    public JEItooltip(float display) {this.add = String.valueOf(display);}
    public JEItooltip(boolean display) {this.add = String.valueOf(display);}
    public JEItooltip(long display) {this.add = String.valueOf(display);}
    public JEItooltip(String display) {this.add = String.valueOf(display);}

    @Override
    public void onRichTooltip(IRecipeSlotView iRecipeSlotView, ITooltipBuilder iTooltipBuilder) {
        iTooltipBuilder.add(Component.literal(add));
    }
}
