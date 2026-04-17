package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.intergration.emi.LaserWidget;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelBombarderRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.awt.*;
import java.util.List;

public class PixelBombarderRecipeEmi extends BaseEmiRecipe<PixelBombarderRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/pixel_bombarder.png"), 0, 0, 37, 73);

    public PixelBombarderRecipeEmi(PixelBombarderRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseInput(recipe.getInput());
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseOutput(recipe.getOutput());
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getInput(), 13, 7).recipeContext(this);
        //output
        widgets.addSlot(recipe.getOutput(), 13, 50).recipeContext(this);

        widgets.add(new LaserWidget(0, 33, recipe.getLaserType(), 1, new Color(recipe.getLaserColor())));
    }
}
