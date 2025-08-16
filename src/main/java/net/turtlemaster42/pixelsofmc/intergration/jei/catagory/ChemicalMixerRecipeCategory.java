package net.turtlemaster42.pixelsofmc.intergration.jei.catagory;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ChemicalMixerRecipeCategory extends BaseCategory<ChemicalMixerRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation( "chemical_mixing");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/chemical_mixer.png");

    private final IDrawable freezeIcon;
    private final IDrawable coldIcon;
    private final IDrawable normalIcon;
    private final IDrawable warmIcon;
    private final IDrawable hotIcon;

    public ChemicalMixerRecipeCategory(IGuiHelper helper) {
        super("chemical_mixing", helper);
        this.background = helper.createDrawable(TEXTURE, 0, 0, 102, 68);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.CHEMICAL_MIXER.get()));
        this.freezeIcon = helper.drawableBuilder(TEXTURE, 0, 118, 10 ,10).build();
        this.coldIcon = helper.drawableBuilder(TEXTURE, 10, 118, 10 ,10).build();
        this.normalIcon = helper.drawableBuilder(TEXTURE, 20, 118, 10 ,10).build();
        this.warmIcon = helper.drawableBuilder(TEXTURE, 30, 118, 10 ,10).build();
        this.hotIcon = helper.drawableBuilder(TEXTURE, 40, 118, 10 ,10).build();
    }

    @Override
    public @NotNull RecipeType<ChemicalMixerRecipe> getRecipeType() {
        return new RecipeType<>(UID, ChemicalMixerRecipe.class);
    }

    @Override
    public void draw(ChemicalMixerRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        switch (recipe.getTemperatureState()) {
            case 0 -> freezeIcon.draw(guiGraphics, 46, 47);
            case 1 -> coldIcon.draw(guiGraphics, 46, 47);
            case 3 -> warmIcon.draw(guiGraphics, 46, 47);
            case 4 -> hotIcon.draw(guiGraphics, 46, 47);
            default -> normalIcon.draw(guiGraphics, 46, 47);
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ChemicalMixerRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        addFluidOutput(builder, 9, 9, recipe.getInputFluid(0), 16000, 27, 14);
        addFluidOutput(builder, 9, 27, recipe.getInputFluid(1), 16000, 27, 14);
        addFluidOutput(builder, 9, 45, recipe.getInputFluid(2), 16000, 27, 14);

        //outputs
        addFluidOutput(builder, 66, 9, recipe.getResultFluid(0), 16000, 27, 14);
        addFluidOutput(builder, 66, 27, recipe.getResultFluid(1), 16000, 27, 14);
        addFluidOutput(builder, 66, 45, recipe.getResultFluid(2), 16000, 27, 14);
    }
}
