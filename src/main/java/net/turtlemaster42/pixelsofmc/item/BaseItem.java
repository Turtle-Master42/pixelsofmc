package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMtabs;

import javax.annotation.Nullable;
import java.util.List;

public class BaseItem extends Item {
    public BaseItem(Properties pProperties) {
        super(pProperties);
    }
    public BaseItem() {
        this(new Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB));
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown())
            if (Screen.hasControlDown()) {
                pTooltipComponents.add(new TranslatableComponent("tooltip.pixelsofmc." + pStack.getItem() + ".advanced"));
            }else{
                pTooltipComponents.add(new TranslatableComponent("tooltip.pixelsofmc." + pStack.getItem()));
            }
    }
}
