package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

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

    public static void setTooltip(ItemStack stack, String tooltip) {
        stack.getOrCreateTagElement("structure").putString("text", tooltip);
    }
    public String getTooltip(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("structure");
        if (tag != null)
            return tag.getString("text");
        return "";
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (!getTooltip(pStack).isEmpty())
            pTooltipComponents.add(Component.literal(getTooltip(pStack)).withStyle(ChatFormatting.GRAY));
    }
}
