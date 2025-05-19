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
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PixelSplitterRecipeCategory extends BaseCategory<PixelSplitterRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation( "pixel_splitting");
    public final static ResourceLocation TEXTURE = Util.resourceLocation( "textures/gui/pixel_splitter_gui.png");

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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull PixelSplitterRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        addInputSlot(builder, 35, 41, recipe.getInput().ingredient());

        //grinding circle input
        addInputSlot(builder, 80, 18, Ingredient.of(POMtags.Items.CIRCLE_SAW));

        //output
        int color1 = recipe.getColor(0).getRGB();
        int color2 = recipe.getColor(1).getRGB();
        int color3 = recipe.getColor(2).getRGB();

        ItemStack pixel = recipe.getResultItems(0);
        PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
        addOutputSlot(builder, 116, 32, pixel);
        if (recipe.getOutputs().size() > 1) {
            pixel = recipe.getResultItems(1);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            addOutputSlot(builder, 116, 50, pixel);
        }
        if (recipe.getOutputs().size() > 2) {
            pixel = recipe.getResultItems(2);
            PixelItem.createForPixel(pixel, color1, color2, color3, recipe.getStructure());
            addOutputSlot(builder, 134, 41, pixel);
        }
    }
}
