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
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ChemicalMixerRecipeCategory implements IRecipeCategory<ChemicalMixerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_mixing");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/chemical_mixer_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable freezeIcon;
    private final IDrawable coldIcon;
    private final IDrawable normalIcon;
    private final IDrawable warmIcon;
    private final IDrawable hotIcon;
    private final FluidTankRenderer renderer;

    public ChemicalMixerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 48, 4, 88, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_MIXER.get()));
        this.freezeIcon = helper.drawableBuilder(TEXTURE, 0, 231, 10 ,10).build();
        this.coldIcon = helper.drawableBuilder(TEXTURE, 10, 231, 10 ,10).build();
        this.normalIcon = helper.drawableBuilder(TEXTURE, 20, 231, 10 ,10).build();
        this.warmIcon = helper.drawableBuilder(TEXTURE, 30, 231, 10 ,10).build();
        this.hotIcon = helper.drawableBuilder(TEXTURE, 40, 231, 10 ,10).build();
        this.renderer = new FluidTankRenderer(16000, true, 27, 14);
    }

    @Override
    public @NotNull RecipeType<ChemicalMixerRecipe> getRecipeType() {
        return new RecipeType<>(UID, ChemicalMixerRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.chemical_mixer");
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
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ChemicalMixerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        renderer.render(guiGraphics, 2, 2, recipe.getInputFluid(0));
        renderer.render(guiGraphics, 2, 20, recipe.getInputFluid(1));
        renderer.render(guiGraphics, 2, 38, recipe.getInputFluid(2));
        renderer.render(guiGraphics, 59, 2, recipe.getResultFluid(0));
        renderer.render(guiGraphics, 59, 20, recipe.getResultFluid(1));
        renderer.render(guiGraphics, 59, 38, recipe.getResultFluid(2));

        switch (recipe.getTemperatureState()) {
            case 0 -> freezeIcon.draw(guiGraphics, 39, 40);
            case 1 -> coldIcon.draw(guiGraphics, 39, 40);
            case 3 -> warmIcon.draw(guiGraphics, 39, 40);
            case 4 -> hotIcon.draw(guiGraphics, 39, 40);
            default -> normalIcon.draw(guiGraphics, 39, 40);
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, ChemicalMixerRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 2 && mouseX <= 2 + 27 && mouseY >= 2 && mouseY <= 2 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getInputFluid(0), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input")));
        }
        if (mouseX >= 2 && mouseX <= 2 + 27 && mouseY >= 20 && mouseY <= 20 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getInputFluid(1), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input")));
        }
        if (mouseX >= 2 && mouseX <= 2 + 27 && mouseY >= 38 && mouseY <= 38 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getInputFluid(2), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input")));
        }

        if (mouseX >= 59 && mouseX <= 59 + 27 && mouseY >= 2 && mouseY <= 2 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getResultFluid(0), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output")));
        }
        if (mouseX >= 59 && mouseX <= 59 + 27 && mouseY >= 20 && mouseY <= 20 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getResultFluid(1), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output")));
        }
        if (mouseX >= 59 && mouseX <= 59 + 27 && mouseY >= 38 && mouseY <= 38 + 14) {
            tooltip.addAll(renderer.getTooltip(recipe.getResultFluid(2), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output")));
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalMixerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getInputFluid(0).getFluid(), recipe.getInputFluid(0).getAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getInputFluid(1).getFluid(), recipe.getInputFluid(1).getAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getInputFluid(2).getFluid(), recipe.getInputFluid(2).getAmount());
        //outputs
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid(0).getFluid(), recipe.getResultFluid(0).getAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid(1).getFluid(), recipe.getResultFluid(1).getAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid(2).getFluid(), recipe.getResultFluid(2).getAmount());
    }
}
