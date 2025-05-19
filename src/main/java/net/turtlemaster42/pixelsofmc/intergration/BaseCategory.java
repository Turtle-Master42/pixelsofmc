package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.Nullable;

public class BaseCategory<T> implements IRecipeCategory<T> {
    public final static ResourceLocation CHANCE = Util.resourceLocation("textures/gui/widgets/widgets.png");

    protected final IDrawable chanceOverlay;
    protected final IDrawable smallChanceOverlay;
    protected IDrawable background;
    protected IDrawable icon;

    public BaseCategory(IGuiHelper helper) {
        this.chanceOverlay = helper.drawableBuilder(CHANCE, 0, 0, 16 ,16).build();
        this.smallChanceOverlay = helper.drawableBuilder(CHANCE, 16, 0, 16 ,16).build();
        this.background = null;
        this.icon = null;
    }

    public BaseCategory() {
        this.chanceOverlay = null;
        this.smallChanceOverlay = null;
        this.background = null;
        this.icon = null;
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
    public RecipeType<T> getRecipeType() {
        return null;
    }

    @Override
    public Component getTitle() {
        return null;
    }

    public Font getFont() {
        return Minecraft.getInstance().font;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
    }

    public void addInputSlot(IRecipeLayoutBuilder builder, int x, int y, ItemStack stack) {
        if (!stack.isEmpty()) {
            builder.addInputSlot(x, y).addIngredients(Ingredient.of(stack));
        }
    }

    public void addInputSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        if (!ingredient.isEmpty()) {
            builder.addInputSlot(x, y).addIngredients(ingredient);
        }
    }

    public void addOutputSlot(IRecipeLayoutBuilder builder, int x, int y, ItemStack stack) {
        if (!stack.isEmpty()) {
            builder.addOutputSlot(x, y).addIngredients(Ingredient.of(stack));
        }
    }

    public void addOutputSlot(IRecipeLayoutBuilder builder, int x, int y, ChanceIngredient ingredient) {
        float chance = ingredient.chance();
        if (!ingredient.isEmpty() && chance > 0f) {
            if (chance < 1f) {
                builder.addOutputSlot(x, y).addIngredients(ingredient.asIngredient()).setBackground(chance < 0.5f ? smallChanceOverlay : chanceOverlay, 0, 0).addRichTooltipCallback(new JEItooltip("§6"+Math.round(chance*100)+"%"));
            } else {
                builder.addOutputSlot(x, y).addIngredients(ingredient.asOverflowIngredient());
            }
        }
    }

    public void addFluidInput(IRecipeLayoutBuilder builder, int x, int y, FluidStack fluidStack, int capacity, int width, int height) {
        if (!fluidStack.isEmpty()) {
            builder.addInputSlot(x, y)
                    .addFluidStack(fluidStack.getFluid(), fluidStack.getAmount())
                    .setCustomRenderer(ForgeTypes.FLUID_STACK, new JEIfluidRenderer(capacity, false, width, height));
        }
    }

    public void addFluidOutput(IRecipeLayoutBuilder builder, int x, int y, FluidStack fluidStack, int capacity, int width, int height) {
        if (!fluidStack.isEmpty()) {
            builder.addOutputSlot(x, y)
                    .addFluidStack(fluidStack.getFluid(), fluidStack.getAmount())
                    .setCustomRenderer(ForgeTypes.FLUID_STACK, new JEIfluidRenderer(capacity, false, width, height));
        }
    }
}
