package net.turtlemaster42.pixelsofmc.intergration;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChemicalSeraratorRecipeCategory implements IRecipeCategory<ChemicalSeparatorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_separating");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/chemical_seperator_gui.png");
    public final static ResourceLocation CHANCE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer renderer;

    public ChemicalSeraratorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 23, 3, 118, 79);
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
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ChemicalSeparatorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        renderer.render(stack, 35, 5, recipe.getFluidInput());
        renderer.render(stack, 35, 20, recipe.getResultFluid());
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(ChemicalSeparatorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 35 && mouseX <= 61 && mouseY >= 5 && mouseY <= 16) {
            return renderer.getTooltip(recipe.getFluidInput(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input"));
        }
        if (mouseX >= 35 && mouseX <= 61 && mouseY >= 20 && mouseY <= 31) {
            return renderer.getTooltip(recipe.getResultFluid(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output"));
        }

        return new ArrayList<>();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalSeparatorRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 44).addIngredients(recipe.getInput());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount());
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 101 - (4 * p);
            int y = 26 + (18 * p);
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addIngredients(Ingredient.of(recipe.getResultItems(p)));
        }
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount());
    }
}
