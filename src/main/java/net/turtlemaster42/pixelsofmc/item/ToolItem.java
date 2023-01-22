package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class ToolItem extends Item {
    public ToolItem(Properties pProperties) {
        super(pProperties);
    }

    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack container = itemStack.copy();
        if(container.hurt(1, new Random(), null))
            return ItemStack.EMPTY;
        else
            return container;
    }

    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}
