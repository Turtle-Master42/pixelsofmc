package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelAssemblerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PixelAssemblerRecipeCategory extends BaseCategory<PixelAssemblerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "pixel_assembling");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/pixel_assembler_gui.png");

    public PixelAssemblerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 25, 25, 120, 57);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.PIXEL_ASSEMBLER.get()));
    }

    @Override
    public @NotNull RecipeType<PixelAssemblerRecipe> getRecipeType() {
        return new RecipeType<>(UID, PixelAssemblerRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.pixel_assembler");
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull PixelAssemblerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //inputs
        List<CountedIngredient> recipeInputs = recipe.getInputs();
        int color1 = recipe.getColor(0).getRGB();
        int color2 = recipe.getColor(1).getRGB();
        int color3 = recipe.getColor(2).getRGB();
        List<ItemStack> input = new ArrayList<>();

        for (CountedIngredient recipeInput : recipeInputs) {
            ItemStack pixel = recipeInput.asItemStack();
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            if (recipeInput.count() > 64) {
                pixel.setCount(64);
                input.add(pixel);
                pixel.setCount(recipeInput.count() - 64);
                if (pixel.getCount() > 64) {
                    pixel.setCount(64);
                    input.add(pixel);
                    pixel.setCount(recipeInput.count() - 64);
                    input.add(pixel);
                } else {
                    input.add(pixel);
                }
            } else {
                pixel.setCount(recipeInput.count());
                input.add(pixel);
            }
        }

        addInputSlot(builder, 4, 13, input.get(0));
        if (input.size() > 1)
            addInputSlot(builder, 22, 4, input.get(1));
        if (input.size() > 2)
            addInputSlot(builder, 22, 22, input.get(2));

        //output
        addOutputSlot(builder, 103, 13, recipe.getBaseOutput());

    }
}
