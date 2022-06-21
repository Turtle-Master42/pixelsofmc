package net.turtlemaster42.pixelsofmc.gui;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.init.PixelsOfMcModItems;

public class ModSpeedUpgradeSlot extends SlotItemHandler {
    public ModSpeedUpgradeSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        if (stack.is(PixelsOfMcModItems.SPEED_UPGRADE.get())) {
            return true;
        } else {
            return false;
        }
    }
}