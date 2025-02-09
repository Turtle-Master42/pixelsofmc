package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class BallMillRecipeCategory implements IRecipeCategory<BallMillRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "ball_milling");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/ball_mill_gui.png");
    public final static ResourceLocation CHANCE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");


    private final IDrawable background;
    private final IDrawable chanceOverlay;
    private final IDrawable smallChanceOverlay;
    private final IDrawable slot;
    private final IDrawable icon;

    public BallMillRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 28, 11, 120, 66);
        this.chanceOverlay = helper.drawableBuilder(CHANCE, 0, 0, 16 ,16).build();
        this.smallChanceOverlay = helper.drawableBuilder(CHANCE, 16, 0, 16 ,16).build();
        this.slot = helper.drawableBuilder(CHANCE, 32, 0, 16 ,16).build();
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

    public Font getFont() {
        return Minecraft.getInstance().font;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull BallMillRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        for (int p = 0; p < recipe.getInputs().size(); p++ ) {
            builder.addSlot(RecipeIngredientRole.INPUT, 3, (14*(p+1)+8*p)-11).addIngredients(Ingredient.of(recipe.getInput(p)));
        }
        //grinding ball input
        builder.addSlot(RecipeIngredientRole.INPUT, 52, 25).addIngredients(recipe.getBall().get(0));
        //output
        if (recipe.getOutputChance() < 1f) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 25).addIngredients(recipe.getOutput().asIngredient()).setBackground(recipe.getOutputChance() < 0.5f ? smallChanceOverlay : chanceOverlay, 0, 0).addRichTooltipCallback(new JEItooltip("§6"+Math.round(recipe.getOutputChance()*100)+"%"));
        } else if (recipe.getOutputChance() > 1f) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 25).addIngredients(recipe.getOutput().asOverflowIngredient());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 43).addIngredients(recipe.getOutput().asIngredient()).setBackground(recipe.getOutputChance() < 0.5f ? smallChanceOverlay : chanceOverlay, 0, 0).addRichTooltipCallback(new JEItooltip("§6"+Math.round((recipe.getOutputChance() - Mth.floor(recipe.getOutputChance()))*100)+"%"));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 25).addIngredients(recipe.getOutput().asIngredient());
        }
    }
}
