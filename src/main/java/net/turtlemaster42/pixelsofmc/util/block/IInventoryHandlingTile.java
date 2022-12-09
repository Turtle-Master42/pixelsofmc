package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraftforge.items.ItemStackHandler;

public interface IInventoryHandlingTile {
    void setHandler(ItemStackHandler handler);
    ItemStackHandler getItemStackHandler();
}
