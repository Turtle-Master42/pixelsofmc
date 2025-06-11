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
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;

public class HotIsostaticPressRecipeEmi extends BaseEmiRecipe<HotIsostaticPressRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/hot_isostatic_press.png"), 0, 0, 103, 85);
    public static final ResourceLocation FLAME = TEXTURE.texture;


    public HotIsostaticPressRecipeEmi(HotIsostaticPressRecipe recipe, EmiRecipeCategory category) {
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

    protected void draw(WidgetHolder widgets) {
        String heatString = String.valueOf(recipe.getHeat());
        String maxHeatString = String.valueOf(recipe.getMaxHeat());
        Font font =  Minecraft.getInstance().font;
        widgets.addText(Component.literal(heatString), getDisplayWidth() - font.width(heatString), 0, (recipe.getHeat() > 2500 ? 0xFF4CD8FF : 0xFFF98900), true);
        widgets.addText(Component.literal(maxHeatString), getDisplayWidth() - font.width(maxHeatString), 10, (recipe.getMaxHeat() > 2500 ? 0xFF4CD8FF : 0xFFF98900), true);

        if (recipe.getHeat() > 4500) {
            widgets.addTexture(FLAME, 6, 53, 45, 28, 0, 116);
        } else if (recipe.getHeat() > 3500) {
            widgets.addTexture(FLAME, 6, 63, 45, 18, 0, 126);
        } else if (recipe.getHeat() > 2500) {
            widgets.addTexture(FLAME, 6, 73, 45, 8, 0, 136);
        } else if (recipe.getHeat() > 2000) {
            widgets.addTexture(FLAME, 6, 53, 45, 0, 0, 87);
        } else if (recipe.getHeat() > 1000) {
            widgets.addTexture(FLAME, 6, 53, 45, 10, 0, 87);
        } else if (recipe.getHeat() > 10)  {
            widgets.addTexture(FLAME, 6, 53, 45, 20, 0, 87);
        } else {
            widgets.addTexture(FLAME, 6, 53, 45, 28, 0, 87);
        }
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getInput(), 21, 7);
        //mold
        widgets.addSlot(recipe.getMold(), 21, 32).catalyst(true);
        //output
        widgets.addSlot(recipe.getOutput(), 80, 32).recipeContext(this);
    }
}
