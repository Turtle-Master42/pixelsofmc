package net.turtlemaster42.pixelsofmc.recipe;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;
import net.turtlemaster42.pixelsofmc.item.Pixel;

public class PixelCompactingRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<?> SERIALIZER = new SimpleCraftingRecipeSerializer<>(PixelCompactingRecipe::new);
    private final int[] inColor = new int[3];
    public String text = "";

    public PixelCompactingRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }


    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Boolean[] match = new Boolean[9];
        Boolean[] air = new Boolean[9];
        boolean inColored = false;
        boolean hasAir = false;

        for (int i = 0; i < 9; i++) {
            match[i] = false;
            air[i] = false;
            if (pContainer.getItem(i).getItem() == POMitems.PIXEL.get()) {
                int[] color = new int[3];
                String text;
                color[0] = getColor(pContainer.getItem(i), 0);
                color[1] = getColor(pContainer.getItem(i), 1);
                color[2] = getColor(pContainer.getItem(i), 2);
                text = getTooltip(pContainer.getItem(i));
                if (!inColored) {
                    inColored = true;
                    inColor[0] = color[0];
                    inColor[1] = color[1];
                    inColor[2] = color[2];
                    this.text = text;
                }

                if (color[0] == inColor[0] && color[1] == inColor[1] && color[2] == inColor[2] && text == this.text) match[i] = true;
            } else if (pContainer.getItem(i).isEmpty() && !hasAir) {
                air[i] = true;
                hasAir = true;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (!match[i] && !air[i]) return false;
        }

        return hasAir;
    }

    public int getColor(ItemStack stack, int index) {
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color"+index, 99) ? compoundtag.getInt("color"+index) : 0;
    }
    public String getTooltip(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("structure");
        if (tag != null)
            return tag.getString("text");
        return "";
    }
    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        ItemStack out = POMitems.PIXEL_PILE.get().getDefaultInstance();

        Pixel.setColor(out, inColor[0], 0);
        Pixel.setColor(out, inColor[1], 1);
        Pixel.setColor(out, inColor[2], 2);
        Pixel.setTooltip(out, text);

        return out;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
