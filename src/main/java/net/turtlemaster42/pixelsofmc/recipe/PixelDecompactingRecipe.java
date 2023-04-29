package net.turtlemaster42.pixelsofmc.recipe;

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

public class PixelDecompactingRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<?> SERIALIZER = new SimpleCraftingRecipeSerializer<>(PixelDecompactingRecipe::new);
    private final int[] inColor = new int[3];
    private String text = "";

    public PixelDecompactingRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }


    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Boolean[] match = new Boolean[9];
        Boolean[] air = new Boolean[9];
        boolean hasInput = false;

        for (int i = 0; i < 9; i++) {
            match[i] = false;
            air[i] = false;
            if (pContainer.getItem(i).getItem() == POMitems.PIXEL_PILE.get() && !hasInput) {
                inColor[0] = getColor(pContainer.getItem(i), 0);
                inColor[1] = getColor(pContainer.getItem(i), 1);
                inColor[2] = getColor(pContainer.getItem(i), 2);
                text = getTooltip(pContainer.getItem(i));
                match[i] = true;
                hasInput = true;
            } else if (pContainer.getItem(i).isEmpty()) {air[i] = true;}
        }

        for (int i = 0; i < 9; i++) {
            if (!match[i] && !air[i]) return false;
        }

        return hasInput;
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
        ItemStack out = POMitems.PIXEL.get().getDefaultInstance();

        Pixel.setColor(out, inColor[0], 0);
        Pixel.setColor(out, inColor[1], 1);
        Pixel.setColor(out, inColor[2], 2);
        Pixel.setTooltip(out, text);
        out.setCount(8);

        return out;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
