package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
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
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ChemicalCombinerRecipeCategory implements IRecipeCategory<ChemicalCombinerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_combining");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/chemical_combiner.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ChemicalCombinerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 106, 69);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_COMBINER.get()));
    }

    @Override
    public @NotNull RecipeType<ChemicalCombinerRecipe> getRecipeType() {
        return new RecipeType<>(UID, ChemicalCombinerRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.chemical_combiner");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalCombinerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            int x = 7 + (2 * p);
            int y = 7 + (20 * p);
            builder.addInputSlot(x, y).addIngredients(Ingredient.of(recipe.getInput(p)));
        }
        if (!recipe.getFluidInput().isEmpty())
            builder.addInputSlot(72, 9)
                    .addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount())
                    .setFluidRenderer(16000, false, 25, 11);

        if (!recipe.getOutput().isEmpty())
            builder.addOutputSlot(83, 46).addIngredients(recipe.getOutput().asIngredient());

        if (!recipe.getResultFluid().isEmpty())
            builder.addOutputSlot(72, 24)
                    .addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount())
                    .setFluidRenderer(16000, false, 25, 11);

    }
}
