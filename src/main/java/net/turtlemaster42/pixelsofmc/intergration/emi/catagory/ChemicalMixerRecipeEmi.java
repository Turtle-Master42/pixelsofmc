package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;

public class ChemicalMixerRecipeEmi extends BaseEmiRecipe<ChemicalMixerRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/chemical_mixer.png"), 0, 0, 102, 68);
    public static final ResourceLocation WIDGET = TEXTURE.texture;

    public ChemicalMixerRecipeEmi(ChemicalMixerRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseFluidInput(recipe.getFluidInputs());
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseFluidOutput(recipe.getResultFluids());
    }

    protected void draw(WidgetHolder widgets) {
        switch (recipe.getTemperatureState()) {
            case 0 -> widgets.addTexture(WIDGET, 46, 47, 10, 10, 0, 118);
            case 1 -> widgets.addTexture(WIDGET, 46, 47, 10, 10, 10, 118);
            case 3 -> widgets.addTexture(WIDGET, 46, 47, 10, 10, 20, 118);
            case 4 -> widgets.addTexture(WIDGET, 46, 47, 10, 10, 30, 118);
            default -> widgets.addTexture(WIDGET, 46, 47, 10, 10, 40, 118);
        }
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //fluid input
        widgets.addTank(recipe.getInputFluid(0), 9, 9, 27, 14);
        widgets.addTank(recipe.getInputFluid(1), 9, 27, 27, 14);
        widgets.addTank(recipe.getInputFluid(2), 9, 45, 27, 14);
        //fluid output
        widgets.addTank(recipe.getResultFluid(0), 66, 9, 27, 14).recipeContext(this);
        widgets.addTank(recipe.getResultFluid(1), 66, 27, 27, 14).recipeContext(this);
        widgets.addTank(recipe.getResultFluid(2), 66, 45, 27, 14).recipeContext(this);
    }
}
