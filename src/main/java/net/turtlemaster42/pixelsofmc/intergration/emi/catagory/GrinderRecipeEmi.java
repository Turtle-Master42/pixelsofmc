package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;

public class GrinderRecipeEmi extends BaseEmiRecipe<GrinderRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/grinder.png"), 0, 0, 120, 84);

    public GrinderRecipeEmi(GrinderRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseInput(recipe.getInput());
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseChanceOutput(recipe.getOutputs());
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getInput(), 7, 34);

        //output
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 75 + 18*(p/4);
            int y = 7 + (18*p) - (72*(p/4));

            if(p > 3) {
                widgets.addSlot(recipe.getOutputs().get(p), x, y).recipeContext(this).appendTooltip(Component.literal("§cThis item may not appear if the 4 official slots are full"));
            } else {
                widgets.addSlot(recipe.getOutputs().get(p), x, y).recipeContext(this);

            }
        }
    }
}
