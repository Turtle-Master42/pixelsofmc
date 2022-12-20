package net.turtlemaster42.pixelsofmc.gui.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.init.POMitems;

public class ModEnergyUpgradeSlot extends SlotItemHandler {
    public ModEnergyUpgradeSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(POMitems.ENERGY_UPGRADE.get());
    }
}