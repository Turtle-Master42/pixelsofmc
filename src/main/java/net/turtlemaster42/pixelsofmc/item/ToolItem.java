package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.turtlemaster42.pixelsofmc.init.POMitems;

import java.util.Random;

public class ToolItem extends Item {
    public ToolItem(Properties pProperties) {
        super(pProperties);
    }

    //immersive engineering
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack container = itemStack.copy();
        if(container.hurt(1, RandomSource.create(), null))
            return ItemStack.EMPTY;
        else
            return container;
    }
    //immersive engineering
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (stack.is(POMitems.CLEANING_CLOTH.get()))
            return toolAction.equals(ToolActions.AXE_WAX_OFF);
        return false;
    }
}
