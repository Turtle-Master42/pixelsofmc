package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class BallMillRecipeCategory implements IRecipeCategory<BallMillRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "ball_milling");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/ball_mill_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public BallMillRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 185, 84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.BALL_MILL.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends BallMillRecipe> getRecipeClass() {
        return BallMillRecipe.class;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TextComponent("Ball Mill");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull BallMillRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //first Input
        builder.addSlot(RecipeIngredientRole.INPUT, 31, 14).addIngredients(recipe.getInputs().get(0).ingredient());
        //second input, only of it exists
        if (recipe.getInputs().size() >= 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 31, 36).addIngredients(recipe.getInputs().get(1).ingredient());
        }
        //third input, only of it exists
        if (recipe.getInputs().size() >= 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 31, 58).addIngredients(recipe.getInputs().get(2).ingredient());
        }
        //grinding ball input
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 36).addIngredients(Ingredient.of(POMitems.TITANIUM_CIRCLE_SAW.get()));

        //output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 36).addItemStack(recipe.getResultItem());
    }
}
