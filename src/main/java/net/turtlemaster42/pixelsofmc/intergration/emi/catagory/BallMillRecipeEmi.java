package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.List;

public class BallMillRecipeEmi extends BaseEmiRecipe<BallMillRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/ball_mill.png"), 0, 0, 128, 75);

    public BallMillRecipeEmi(BallMillRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (CountedIngredient ingredient : recipe.getInputs()) {
            list.add(EmiIngredient.of(ingredient.ingredient(), ingredient.count()));
        }
        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(recipe.getOutput().getItems()[0]));
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            widgets.addSlot(recipe.getInputs().get(p), 7, 7 + (22 * p));
        }
        //ball
        widgets.addSlot(recipe.getBall(), 56, 29);
        //output
        widgets.addSlot(recipe.getOutput(), 105, 29).recipeContext(this);
    }
}
