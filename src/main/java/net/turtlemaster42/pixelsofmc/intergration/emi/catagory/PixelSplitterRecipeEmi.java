package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelAssemblerRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.ArrayList;
import java.util.List;

public class PixelSplitterRecipeEmi extends BaseEmiRecipe<PixelSplitterRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/pixel_splitter_gui.png"), 0, 0, 184, 84);

    public PixelSplitterRecipeEmi(PixelSplitterRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return parseInput(recipe.getInput());
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<ItemStack> output = new ArrayList<>();
        for (CountedIngredient recipeInput : recipe.getOutputs()) {
            ItemStack pixel = recipeInput.asItemStack();
            PixelItem.createForPixel(
                    pixel,
                    recipe.getColor(0).getRGB(),
                    recipe.getColor(1).getRGB(),
                    recipe.getColor(2).getRGB(),
                    recipe.getStructure()
            );
            pixel.setCount(recipeInput.count());
            output.add(pixel);
        }
        return parseStackOutput(output);
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //input
        widgets.addSlot(recipe.getInput(), 35, 41);

        //saw
        widgets.addSlot(Ingredient.of(POMtags.Items.CIRCLE_SAW), 80, 18).catalyst(true);

        //output
        int color1 = recipe.getColor(0).getRGB();
        int color2 = recipe.getColor(1).getRGB();
        int color3 = recipe.getColor(2).getRGB();
        ItemStack pixel = recipe.getResultItems(0);
        PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
        widgets.addSlot(pixel, 116, 32).recipeContext(this);
        if (recipe.getOutputs().size() > 1) {
            pixel = recipe.getResultItems(1);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            widgets.addSlot(pixel, 116, 50).recipeContext(this);
        }
        if (recipe.getOutputs().size() > 2) {
            pixel = recipe.getResultItems(2);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            widgets.addSlot(pixel, 134, 41).recipeContext(this);
        }
    }
}
