package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PixelItem extends Item {
    private final int pixelAmount;
    public PixelItem(Properties properties, int pixelAmount) {
        super(properties);
        this.pixelAmount = pixelAmount;
    }

    public int getColor(ItemStack itemStack, int index) {
        CompoundTag compoundtag = itemStack.getTagElement("display");

        if (compoundtag != null && compoundtag.contains("color"+index, 99)) return compoundtag.getInt("color"+index);
        else if (index == 0) return 16777215;
        else if (index == 1) return 11842740;
        else return 6579300;
    }

    public static void setColor(ItemStack itemStack, int color, int index) {
        itemStack.getOrCreateTagElement("display").putInt("color"+index, color);
    }

    public static ItemStack createForPixel(ItemStack itemStack, int color1, int color2, int color3, String tooltip) {
        if (itemStack.getItem() instanceof PixelItem) {
            itemStack.getOrCreateTagElement("display").putInt("color0", color1);
            itemStack.getOrCreateTagElement("display").putInt("color1", color2);
            itemStack.getOrCreateTagElement("display").putInt("color2", color3);
            itemStack.getOrCreateTagElement("structure").putString("text", tooltip);
        }
        return itemStack;
    }

    public static ItemStack createForPixel(ItemStack itemStack, int color1, int color2, int color3, String tooltip, String[] extra) {
        for (int i = 0; i < extra.length; i++)
            itemStack.getOrCreateTagElement("extra").putString("text_"+i, extra[i]);
        return createForPixel(itemStack, color1, color2, color3, tooltip);
    }

    public static void setTooltip(ItemStack itemStack, String tooltip) {
        itemStack.getOrCreateTagElement("structure").putString("text", tooltip);
    }

    public static void setExtraTooltip(ItemStack itemStack, String[] tooltip) {
        for (int i = 0; i < tooltip.length; i++)
            itemStack.getOrCreateTagElement("extra").putString("text_"+i, tooltip[i]);
    }

    public String getTooltip(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTagElement("structure");
        if (tag != null)
            return tag.getString("text");
        return "";
    }

    public String getStructure(ItemStack itemStack) {
        return getTooltip(itemStack);
    }

    public String[] getExtraTooltip(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTagElement("extra");
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
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(itemStack, level, tooltipComponents, isAdvanced);

        if (!getTooltip(itemStack).isEmpty()) {
            tooltipComponents.add(Component.translatable(pixelAmount + "x").withStyle(ChatFormatting.RED));
            tooltipComponents.add(Component.translatable(getTooltip(itemStack)).withStyle(ChatFormatting.GRAY));
        }
    }
}
