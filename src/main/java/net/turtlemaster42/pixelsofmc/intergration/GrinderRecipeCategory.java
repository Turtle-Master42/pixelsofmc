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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class GrinderRecipeCategory implements IRecipeCategory<GrinderRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "grinding");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/grinder_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public GrinderRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 185, 84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.GRINDER.get()));
    }

    @Override
    public RecipeType<GrinderRecipe> getRecipeType() {
        return new RecipeType<>(UID, GrinderRecipe.class);
    }

    @Override
    @Deprecated
    public Class<? extends GrinderRecipe> getRecipeClass() {
        return GrinderRecipe.class;
    }

    @Override
    @Deprecated
       public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("block.pixelsofmc.grinder");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull GrinderRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
            builder.addSlot(RecipeIngredientRole.INPUT, 43, 36).addIngredients(recipe.getInput());
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            builder.addSlot(RecipeIngredientRole.INPUT, 111, 9*(p*2+1)).addItemStack(recipe.getResultItems(p));
        }
    }
}
