package net.turtlemaster42.pixelsofmc.gui.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModMaxStacksizeSlot extends SlotItemHandler {
    private final int maxSize;
    public ModMaxStacksizeSlot(IItemHandler itemHandler, int index, int x, int y, int maxSize) {
        super(itemHandler, index, x, y);
        this.maxSize = maxSize;
    }
    @Override
    public int getMaxStackSize() {
        return maxSize;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return maxSize;
    }
}