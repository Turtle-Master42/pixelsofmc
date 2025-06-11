package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ChemicalCombinerRecipeEmi extends BaseEmiRecipe<ChemicalCombinerRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/chemical_combiner.png"), 0, 0, 119, 69);

    public ChemicalCombinerRecipeEmi(ChemicalCombinerRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = parseCountedInput(recipe.getInputs());
        if (!recipe.getFluidInput().isEmpty()) {
            list.add(parseStack(recipe.getFluidInput()));
        }
        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        if (!recipe.getOutput().isEmpty())
            list.add(parseStack(recipe.getOutput()));
        if (!recipe.getResultFluid().isEmpty()) {
            list.add(parseStack(recipe.getResultFluid()));
        }
        return list;
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        // input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            int x = 7 + (2 * p);
            int y = 7 + (20 * p);
            widgets.addSlot(recipe.getInputs().get(p), x, y);
        }
        //fluid input
        widgets.addTank(recipe.getFluidInput(), 47, 8, 25, 11);

        //output
        if (!recipe.getOutput().isEmpty())
            widgets.addSlot(recipe.getOutput(), 83, 46).recipeContext(this);
        //fluid output
        widgets.addTank(recipe.getResultFluid(), 86, 8, 25, 11).recipeContext(this);

    }
}
