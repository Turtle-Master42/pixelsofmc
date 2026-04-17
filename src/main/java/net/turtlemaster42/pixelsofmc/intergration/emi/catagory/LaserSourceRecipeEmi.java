package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.intergration.emi.LaserWidget;
import net.turtlemaster42.pixelsofmc.recipe.machines.LaserSourceRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.awt.*;
import java.util.List;

public class LaserSourceRecipeEmi extends BaseEmiRecipe<LaserSourceRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/laser_source.png"), 0, 0, 52, 30);

    public LaserSourceRecipeEmi(LaserSourceRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseInput(recipe.getSource());
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getSource().asIngredient(), 18, 7).recipeContext(this);
        widgets.add(new LaserWidget(-1, 11, 0, recipe.getSizeRequirement(), new Color(recipe.getInputColor())));
        widgets.add(new LaserWidget(35, 11, recipe.getOutputType(), recipe.getSizeRequirement() + recipe.getSizeModifier(), new Color(recipe.getOutputColor())));
    }
}
