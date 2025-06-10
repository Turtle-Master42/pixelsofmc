package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.intergration.jei.IngredientDrawable;
import net.turtlemaster42.pixelsofmc.intergration.jei.JEItooltip;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.ArrayList;
import java.util.List;

public class GrinderRecipeEmi extends BaseEmiRecipe<GrinderRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/grinder.png"), 0, 0, 120, 84);

    public GrinderRecipeEmi(GrinderRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(recipe.getInput()));
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        for (ChanceIngredient output : recipe.getOutputs()) {
            list.add(EmiStack.of(output.asItemStack()));
        }

        return list;
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getInput(), 7, 34);

        //output
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            float chance = recipe.getOutputChance(p);
            String display = "§6"+Math.round(chance*100)+"%";
            int x = 75 + 18*(p/4);
            int y = 7 + (18*p) - (72*(p/4));

            if(p > 3) display = display + "\n§cThis item may not appear if the 4 official slots are full";

            widgets.addSlot(recipe.getOutputs().get(p), x, y).recipeContext(this).appendTooltip(Component.literal(display));
        }
    }
}
