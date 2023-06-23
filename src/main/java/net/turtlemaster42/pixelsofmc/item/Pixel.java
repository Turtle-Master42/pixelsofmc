package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Pixel extends Item implements DyeableLeatherItem {

    public Pixel(Properties properties) {super(properties);}

    public int getColor(@NotNull ItemStack stack) {
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

    public static ItemStack createForPixel(ItemStack pStack, int color1, int color2, int color3, String tooltip) {
        pStack.getOrCreateTagElement("display").putInt("color0", color1);
        pStack.getOrCreateTagElement("display").putInt("color1", color2);
        pStack.getOrCreateTagElement("display").putInt("color2", color3);
        pStack.getOrCreateTagElement("structure").putString("text", tooltip);
        return pStack;
    }

    public static ItemStack createForPixel(ItemStack pStack, int color1, int color2, int color3, String tooltip, String[] extra) {
        for (int i = 0; i < extra.length; i++)
            pStack.getOrCreateTagElement("extra").putString("text_"+i, extra[i]);
        return createForPixel(pStack, color1, color2, color3, tooltip);
    }

    public static void setTooltip(ItemStack stack, String tooltip) {
        stack.getOrCreateTagElement("structure").putString("text", tooltip);
    }

    public static void setExtraTooltip(ItemStack stack, String[] tooltip) {
        for (int i = 0; i < tooltip.length; i++)
            stack.getOrCreateTagElement("extra").putString("text_"+i, tooltip[i]);
    }

    public String getTooltip(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("structure");
        if (tag != null)
            return tag.getString("text");
        return "";
    }

    public String[] getExtraTooltip(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("extra");
        if (tag != null) {
            int loop = 0;

            for (int i = 0; i < 100; i++) {
                String text = tag.getString("text_"+i);
                if (text.isEmpty()) {
                    loop = i;
                    break;
                }
            }
            String[] out = new String[loop];
            for (int j = 0; j < loop; j++) {
                out[j] = tag.getString("text_"+j);
            }
            return out;
        }
        return new String[]{""};
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (!getTooltip(pStack).isEmpty())
            pTooltipComponents.add(Component.translatable(getTooltip(pStack)).withStyle(ChatFormatting.GRAY));
//        if (!getExtraTooltip(pStack)[0].isEmpty() && Screen.hasShiftDown())
//            for (int i = 0; i < getExtraTooltip(pStack).length; i++) {
//                pTooltipComponents.add(Component.translatable(getExtraTooltip(pStack)[i]));
//            }
    }
}
