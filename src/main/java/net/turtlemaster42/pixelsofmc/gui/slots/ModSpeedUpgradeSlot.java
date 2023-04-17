package net.turtlemaster42.pixelsofmc.gui.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;

public class ModSpeedUpgradeSlot extends SlotItemHandler {
    public ModSpeedUpgradeSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(POMtags.Items.SPEED_UPGRADE) || stack.is(POMitems.SPEED_UPGRADE.get());
    }
}