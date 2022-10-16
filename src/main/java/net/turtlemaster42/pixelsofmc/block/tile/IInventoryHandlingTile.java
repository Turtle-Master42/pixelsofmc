package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraftforge.items.ItemStackHandler;

public interface IInventoryHandlingTile {
    void setHandler(ItemStackHandler handler);
    ItemStackHandler getItemStackHandler();
}
