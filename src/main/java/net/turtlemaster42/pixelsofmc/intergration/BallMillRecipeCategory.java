package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class BallMillRecipeCategory extends BaseCategory<BallMillRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("ball_milling");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/ball_mill.png");

    public BallMillRecipeCategory(IGuiHelper helper) {
        super(helper);
        this.background = helper.createDrawable(TEXTURE, 0, 0, 128, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.BALL_MILL.get()));
    }

    @Override
    public @NotNull RecipeType<BallMillRecipe> getRecipeType() {
        return new RecipeType<>(UID, BallMillRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.ball_mill");
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull BallMillRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            addInputSlot(builder, 7, 7 + (22 * p), recipe.getInput(p));
        }
        //grinding ball input
        addInputSlot(builder, 56, 29, recipe.getBall().get(0));
        //output
        if (recipe.getOutputChance() > 1f) {
            addOutputSlot(builder, 105, 29, recipe.getOutput());
            builder.addOutputSlot(105, 47).addIngredients(recipe.getOutput().asIngredient()).setBackground(recipe.getOutputChance() < 0.5f ? smallChanceOverlay : chanceOverlay, 0, 0).addRichTooltipCallback(new JEItooltip("§6"+Math.round((recipe.getOutputChance() - Mth.floor(recipe.getOutputChance()))*100)+"%"));
        } else {
            addOutputSlot(builder, 105, 29, recipe.getOutput());
        }
    }
}
