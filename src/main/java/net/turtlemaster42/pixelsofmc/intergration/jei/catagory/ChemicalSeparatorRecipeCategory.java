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
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ChemicalSeparatorRecipeCategory extends BaseCategory<ChemicalSeparatorRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("chemical_separating");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/chemical_seperator.png");

    public ChemicalSeparatorRecipeCategory(IGuiHelper helper) {
        super(helper);
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalSeparatorRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        addInputSlot(builder, 7, 45, recipe.getInput());
        addFluidInput(builder, 9, 9, recipe.getFluidInput(), 16000, 25, 11);
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            int x = 84 - (2 * p);
            int y = 27 + (18 * p);
            addOutputSlot(builder, x, y, recipe.getOutputs().get(p));
        }
        addFluidOutput(builder, 48, 9, recipe.getResultFluid(), 16000, 25, 11);
    }
}
