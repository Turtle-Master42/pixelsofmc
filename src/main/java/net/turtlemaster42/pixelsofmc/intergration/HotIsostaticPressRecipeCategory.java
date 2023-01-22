package net.turtlemaster42.pixelsofmc.intergration;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class HotIsostaticPressRecipeCategory implements IRecipeCategory<HotIsostaticPressRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "pressing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/hot_isotopic_press_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public HotIsostaticPressRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 25, 5, 110, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get()));
    }

    @Override
    public RecipeType<HotIsostaticPressRecipe> getRecipeType() {
        return new RecipeType<>(UID, HotIsostaticPressRecipe.class);
    }

    @Override
    @Deprecated
    public Class<? extends HotIsostaticPressRecipe> getRecipeClass() {
        return HotIsostaticPressRecipe.class;
    }

    @Override
    @Deprecated
       public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("block.pixelsofmc.hot_isotopic_press");
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
    public void draw(HotIsostaticPressRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        drawHeat(recipe, poseStack, 0);
    }

    protected void drawHeat(HotIsostaticPressRecipe recipe, PoseStack poseStack, int y) {
        float heat = recipe.getHeat();
        String heatString = String.valueOf(heat);
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(heatString);
        fontRenderer.draw(poseStack, heatString, background.getWidth() - stringWidth, y, 0xFF808080);
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull HotIsostaticPressRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 4).addIngredients(recipe.getInputAsI());
        //mold
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 29).addIngredients(recipe.getMoldAsI());
        //burn
        //builder.addSlot(RecipeIngredientRole.INPUT, 51, 9).addItemStack();
        //output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 29).addItemStack(recipe.getResultItem());
    }
}
