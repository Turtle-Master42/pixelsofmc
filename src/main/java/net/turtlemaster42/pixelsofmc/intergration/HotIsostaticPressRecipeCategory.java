package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class HotIsostaticPressRecipeCategory implements IRecipeCategory<HotIsostaticPressRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "pressing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/hot_isostatic_press.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable flame_small;
    private final IDrawable flame;
    private final IDrawable flame_full;
    private final IDrawable soul_flame_small;
    private final IDrawable soul_flame;
    private final IDrawable soul_flame_full;

    public HotIsostaticPressRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 103, 85);

        this.flame_small = helper.createDrawable(TEXTURE, 0, 87, 45, 20);
        this.flame = helper.createDrawable(TEXTURE, 0, 87, 45, 10);
        this.flame_full = helper.createDrawable(TEXTURE, 0, 144, 45, 1);
        this.soul_flame_small = helper.createDrawable(TEXTURE, 0, 136, 45, 8);
        this.soul_flame = helper.createDrawable(TEXTURE, 0, 126, 45, 18);
        this.soul_flame_full = helper.createDrawable(TEXTURE, 0, 116, 45, 28);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get()));
    }

    @Override
    public @NotNull RecipeType<HotIsostaticPressRecipe> getRecipeType() {
        return new RecipeType<>(UID, HotIsostaticPressRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.hot_isostatic_press");
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
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void draw(@NotNull HotIsostaticPressRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        drawHeatString(recipe, guiGraphics);

        if (recipe.getHeat() > 4500) {
            this.soul_flame_full.draw(guiGraphics, 6, 53);
        } else if (recipe.getHeat() > 3500) {
            this.soul_flame.draw(guiGraphics, 6, 63);
        } else if (recipe.getHeat() > 2500) {
            this.soul_flame_small.draw(guiGraphics, 6, 73);
        } else if (recipe.getHeat() > 2000) {
            this.flame_full.draw(guiGraphics, 6, 53);
        } else if (recipe.getHeat() > 1000) {
            this.flame.draw(guiGraphics, 6, 53);
        } else if (recipe.getHeat() > 0)  {
            this.flame_small.draw(guiGraphics, 6, 53);
        }
    }

    protected void drawHeatString(HotIsostaticPressRecipe recipe, GuiGraphics guiGraphics) {
        int heat = recipe.getHeat();
        String heatString = String.valueOf(heat);
        int maxHeat = recipe.getMaxHeat();
        String maxHeatString = String.valueOf(maxHeat);
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int stringWidth = font.width(heatString);
        int stringWidth2 = font.width(maxHeatString);
        guiGraphics.drawString(font, heatString, background.getWidth() - stringWidth, 0, (heat > 2500 ? 0xFF4CD8FF : 0xFFF98900));
        guiGraphics.drawString(font, maxHeatString, background.getWidth() - stringWidth2, 10, (maxHeat > 2500 ? 0xFF4CD8FF : 0xFFF98900));
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull HotIsostaticPressRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 7).addIngredients(Ingredient.of(recipe.getInput()));
        //mold
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 31).addIngredients(recipe.getMoldAsI());
        //burn
        //builder.addSlot(RecipeIngredientRole.INPUT, 51, 9).addItemStack();
        //output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 32).addIngredients(Ingredient.of(recipe.getBaseOutput()));
    }
}
