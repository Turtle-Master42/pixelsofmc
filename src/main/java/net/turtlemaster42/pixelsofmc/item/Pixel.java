package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Pixel extends Item implements DyeableLeatherItem {
    public Pixel(Item.Properties properties) {super(properties);}

    public int getColor(ItemStack stack) {
        return getColor(stack, 0);
    }


    public int getColor(ItemStack stack, int index) {
        CompoundTag compoundtag = stack.getTagElement("display");

        if (compoundtag != null && compoundtag.contains("color"+index, 99)) return compoundtag.getInt("color"+index);
        else if (index == 0) return 16777215;
        else if (index == 1) return 11842740;
        else return 6579300;
    }

    public static void setColor(ItemStack pStack, int pColor, int index) {
        pStack.getOrCreateTagElement("display").putInt("color"+index, pColor);
    }

    public static ItemStack dyeArmor(ItemStack pStack, List<DyeItem> pDyes) {
        return pStack;
    }
}
