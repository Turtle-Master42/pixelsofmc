package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelAssemblerRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.ArrayList;
import java.util.List;

public class PixelAssemblerRecipeEmi extends BaseEmiRecipe<PixelAssemblerRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/pixel_assembler_gui.png"), 25, 25, 120, 57);

    public PixelAssemblerRecipeEmi(PixelAssemblerRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<ItemStack> input = new ArrayList<>();
        for (CountedIngredient recipeInput : recipe.getInputs()) {
            ItemStack pixel = recipeInput.asItemStack();
            PixelItem.createForPixel(
                    pixel,
                    recipe.getColor(0).getRGB(),
                    recipe.getColor(1).getRGB(),
                    recipe.getColor(2).getRGB(),
                    recipe.getStructure()
            );
            pixel.setCount(recipeInput.count());
            input.add(pixel);
        }

        return parseStackInput(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseOutput(recipe.getBaseOutput());
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //inputs
        List<CountedIngredient> recipeInputs = recipe.getInputs();
        int color1 = recipe.getColor(0).getRGB();
        int color2 = recipe.getColor(1).getRGB();
        int color3 = recipe.getColor(2).getRGB();
        List<ItemStack> input = new ArrayList<>();

        for (CountedIngredient recipeInput : recipeInputs) {
            ItemStack pixel = recipeInput.asItemStack();
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            pixel.setCount(recipeInput.count());
            input.add(pixel);
        }

        widgets.addSlot(input.get(0), 4, 13);
        if (input.size() > 1)
            widgets.addSlot(input.get(1), 22, 4);
        if (input.size() > 2)
            widgets.addSlot(input.get(2), 22, 22);

        //output
        widgets.addSlot(recipe.getBaseOutput(), 103, 13).recipeContext(this);
    }
}
