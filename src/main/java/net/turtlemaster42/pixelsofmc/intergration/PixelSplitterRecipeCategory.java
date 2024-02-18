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
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PixelSplitterRecipeCategory implements IRecipeCategory<PixelSplitterRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "pixel_splitting");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/pixel_splitter_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public PixelSplitterRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 184, 84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.PIXEL_SPLITTER.get()));
    }

    @Override
    public @NotNull RecipeType<PixelSplitterRecipe> getRecipeType() {
        return new RecipeType<>(UID, PixelSplitterRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.pixel_splitter");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull PixelSplitterRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 41).addIngredients(recipe.getInput().ingredient());

        //grinding ball input
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 18).addIngredients(Ingredient.of(POMtags.Items.CIRCLE_SAW));
        //output

        int color1 = recipe.getColor(0).getRGB();
        int color2 = recipe.getColor(1).getRGB();
        int color3 = recipe.getColor(2).getRGB();

        ItemStack pixel = recipe.getResultItems(0);
        PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 32).addIngredients(Ingredient.of(pixel));
        if (recipe.getOutputs().size() > 1) {
            pixel = recipe.getResultItems(1);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 50).addIngredients(Ingredient.of(pixel));
        }
        if (recipe.getOutputs().size() > 2) {
            pixel = recipe.getResultItems(2);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 41).addIngredients(Ingredient.of(pixel));
        }
    }
}
