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
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ChemicalCombinerRecipeCategory implements IRecipeCategory<ChemicalCombinerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_combining");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/chemical_combiner_gui.png");
    public final static ResourceLocation CHANCE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer renderer;

    public ChemicalCombinerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 39, 4, 111, 72);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_COMBINER.get()));
        this.renderer = new FluidTankRenderer(16000, true, 25, 11);
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
    public void draw(ChemicalCombinerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        renderer.render(guiGraphics, 69, 4, recipe.getFluidInput());
        renderer.render(guiGraphics, 69, 19, recipe.getResultFluid());
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, ChemicalCombinerRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 69 && mouseX <= 94 && mouseY >= 4 && mouseY <= 15) {
            tooltip.addAll(renderer.getTooltip(recipe.getFluidInput(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input")));
        }
        if (mouseX >= 69 && mouseX <= 94 && mouseY >= 19 && mouseY <= 30) {
            tooltip.addAll(renderer.getTooltip(recipe.getResultFluid(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output")));
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalCombinerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            int x = 3 + (2 * p);
            int y = 12 + (20 * p);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(Ingredient.of(recipe.getInput(p)));
        }
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount());
        //outputs
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 51).addIngredients(Ingredient.of(recipe.getOutput().asItemStack()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount());
    }
}
