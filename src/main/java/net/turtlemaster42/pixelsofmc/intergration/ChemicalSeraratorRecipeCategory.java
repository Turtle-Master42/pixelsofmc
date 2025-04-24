package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ChemicalSeraratorRecipeCategory implements IRecipeCategory<ChemicalSeparatorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_separating");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/chemical_seperator.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ChemicalSeraratorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 107, 86);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_SEPARATOR.get()));
    }

    @Override
    public @NotNull RecipeType<ChemicalSeparatorRecipe> getRecipeType() {
        return new RecipeType<>(UID, ChemicalSeparatorRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.chemical_separator");
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalSeparatorRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addInputSlot(7, 45).addIngredients(Ingredient.of(recipe.getInput().asItemStack()));
        builder.addInputSlot(9, 9)
                .addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount())
                .setFluidRenderer(16000, false, 25, 11);
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 84 - (2 * p);
            int y = 27 + (18 * p);
            builder.addOutputSlot(x, y).addIngredients(Ingredient.of(recipe.getResultItems(p)));
        }
        builder.addOutputSlot(9, 24)
                .addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount())
                .setFluidRenderer(16000, false, 25, 11);

    }
}
