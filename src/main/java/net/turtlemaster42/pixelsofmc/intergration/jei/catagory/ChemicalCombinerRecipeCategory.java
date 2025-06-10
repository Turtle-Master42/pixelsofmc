package net.turtlemaster42.pixelsofmc.intergration.jei.catagory;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ChemicalCombinerRecipeCategory extends BaseCategory<ChemicalCombinerRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("chemical_combining");
    public final static ResourceLocation TEXTURE = Util.resourceLocation( "textures/gui/jei/chemical_combiner.png");

    public ChemicalCombinerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 119, 69);
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalCombinerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            int x = 7 + (2 * p);
            int y = 7 + (20 * p);
            addInputSlot(builder, x, y, recipe.getInputs().get(p));
        }
        addFluidInput(builder, 47, 8, recipe.getFluidInput(), 16000, 25, 11);

        //output
        addOutputSlot(builder, 83, 46, recipe.getOutput());
        addFluidOutput(builder, 86, 8, recipe.getResultFluid(), 16000, 25, 11);
    }
}
