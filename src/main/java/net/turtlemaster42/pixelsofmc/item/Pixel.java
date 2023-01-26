package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Pixel extends Item implements DyeableLeatherItem {
    public Pixel(Item.Properties properties) {super(properties);}

    public int getColor(ItemStack stack) {
        return getColor(stack, 0);
    }

    public int getColor(ItemStack stack, int index) {
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color"+index, 99) ? compoundtag.getInt("color"+index) : 0;
    }
}
