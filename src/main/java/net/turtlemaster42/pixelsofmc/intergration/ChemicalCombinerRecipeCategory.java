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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.renderer.FluidTankRenderer;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.MouseUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ChemicalCombinerRecipeCategory implements IRecipeCategory<ChemicalCombinerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "chemical_combining");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/chemical_combiner_gui.png");
    public final static ResourceLocation CHANCE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer renderer;

    public ChemicalCombinerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 35, 3, 122, 74);
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
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ChemicalCombinerRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        renderer.render(stack, 79, 9, recipe.getFluidInput());
        renderer.render(stack, 79, 24, recipe.getResultFluid());
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(ChemicalCombinerRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 79 && mouseX <= 105 && mouseY >= 9 && mouseY <= 20) {
            return renderer.getTooltip(recipe.getFluidInput(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.input"));
        }
        if (mouseX >= 79 && mouseX <= 105 && mouseY >= 24 && mouseY <= 35) {
            return renderer.getTooltip(recipe.getResultFluid(), TooltipFlag.Default.NORMAL, Component.translatable("tooltip.pixelsofmc.fluid.output"));
        }

        return new ArrayList<>();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalCombinerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            int x = 1 + (9 * p);
            int y = 21 + (18 * p);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(Ingredient.of(recipe.getInput(p)));
        }
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount());
        //outputs
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 56).addIngredients(Ingredient.of(recipe.getResultItem()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getResultFluid().getFluid(), recipe.getResultFluid().getAmount());
    }
}
