package net.turtlemaster42.pixelsofmc.intergration.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

public class IngredientDrawable implements IDrawable {
    private final int count;
    private final float chance;


    public IngredientDrawable(ItemStack stack) {
        this.count = stack.getCount();
        this.chance = 1f;
    }

    public IngredientDrawable(Ingredient ingredient) {
        this.count = 1;
        this.chance = 1f;
    }

    public IngredientDrawable(CountedIngredient ingredient) {
        this.count = ingredient.count();
        this.chance = 1f;
    }

    public IngredientDrawable(ChanceIngredient ingredient) {
        this.count = ingredient.count();
        this.chance = ingredient.chance();
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        Font font = Minecraft.getInstance().font;
        if (count > 1 && chance == 1f) {
            int stringWidth = font.width(String.valueOf(count));
            guiGraphics.drawString(font, Component.literal("§f"+count), xOffset + getWidth() - stringWidth + 1, yOffset + 9, 0);
        } else if (count == 1 && chance < 1f) {
            int stringWidth = font.width(Math.round(chance * 100) + "%");
            guiGraphics.drawString(font, Component.literal("§6"+Math.round(chance * 100) + "%"), xOffset + getWidth() - stringWidth + 3, yOffset + 10, 0);
        }
    }
}
