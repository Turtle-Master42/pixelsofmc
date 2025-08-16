package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.DecayRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;

public class DecayRecipeEmi extends BaseEmiRecipe<DecayRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/decay.png"), 0, 0, 69, 30);

    public DecayRecipeEmi(DecayRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseInput(Ingredient.of(recipe.getFuelCell()));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseOutput(recipe.getBaseOutput());
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(new ItemStack(recipe.getFuelCell()), 7, 7).recipeContext(this);
        //output
        widgets.addSlot(recipe.getBaseOutput(), 46, 7).recipeContext(this);
    }
}
