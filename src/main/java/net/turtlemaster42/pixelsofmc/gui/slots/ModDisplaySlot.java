package net.turtlemaster42.pixelsofmc.gui.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModDisplaySlot extends SlotItemHandler {
    public ModDisplaySlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean allowModification(Player pPlayer) {
        return false;
    }
}