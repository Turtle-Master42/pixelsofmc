package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ChemicalSeperatorRecipeEmi extends BaseEmiRecipe<ChemicalSeparatorRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/chemical_seperator.png"), 0, 0, 107, 86);

    public ChemicalSeperatorRecipeEmi(ChemicalSeparatorRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = new ArrayList<>();
        if (!recipe.getInput().isEmpty())
            list.add(parseStack(recipe.getInput()));
        if (!recipe.getFluidInput().isEmpty()) {
            list.add(parseStack(recipe.getFluidInput()));
        }
        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = parseChanceOutput(recipe.getOutputs());
        if (!recipe.getResultFluid().isEmpty()) {
            list.add(parseStack(recipe.getResultFluid()));
        }
        return list;
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        // input
        if (!recipe.getInput().isEmpty())
            widgets.addSlot(recipe.getInput(), 7, 45);
        //fluid input
        widgets.addTank(recipe.getFluidInput(), 9, 9, 25, 11);

        // output
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 84 - (2 * p);
            int y = 27 + (18 * p);
            widgets.addSlot(recipe.getOutputs().get(p), x, y).recipeContext(this);
        }
        //fluid output
        widgets.addTank(recipe.getResultFluid(), 48, 9, 25, 11).recipeContext(this);

    }
}
