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
    private final FluidTankRenderer renderer;

    public ChemicalSeraratorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 107, 86);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_SEPARATOR.get()));
        this.renderer = new FluidTankRenderer(16000, true, 25, 11);
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
    public void draw(ChemicalSeparatorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        renderer.render(guiGraphics, 9, 9, recipe.getFluidInput());
        renderer.render(guiGraphics, 9, 24, recipe.getResultFluid());
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, ChemicalSeparatorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 9 && mouseX <= 34 && mouseY >= 9 && mouseY <= 20) {
            tooltip.addAll(renderer.getTooltip(recipe.getFluidInput(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input")));
        }
        if (mouseX >= 9 && mouseX <= 34 && mouseY >= 24 && mouseY <= 35) {
            tooltip.addAll(renderer.getTooltip(recipe.getResultFluid(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output")));
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalSeparatorRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 45).addIngredients(Ingredient.of(recipe.getInput().asItemStack()));
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount());
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 84 - (2 * p);
            int y = 27 + (18 * p);
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addIngredients(Ingredient.of(recipe.getResultItems(p)));
        }
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount());
    }
}
