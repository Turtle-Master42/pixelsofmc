package net.turtlemaster42.pixelsofmc.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;
import net.turtlemaster42.pixelsofmc.item.Pixel;

public class PixelDecompactingRecipe extends CustomRecipe {
    private final int[] inColor = new int[3];
    public PixelDecompactingRecipe(ResourceLocation pId) {
        super(pId);
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
    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        ItemStack out = POMitems.PIXEL.get().getDefaultInstance();

        Pixel.setColor(out, inColor[0], 0);
        Pixel.setColor(out, inColor[1], 1);
        Pixel.setColor(out, inColor[2], 2);
        out.setCount(8);

        return out;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return POMrecipes.PIXEL_DECOMPACTING.get();
    }
}
