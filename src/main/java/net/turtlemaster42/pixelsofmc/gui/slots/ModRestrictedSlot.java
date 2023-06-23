package net.turtlemaster42.pixelsofmc.gui.slots;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModRestrictedSlot extends SlotItemHandler {
    protected final Supplier<Item>[] possibleItems;

    @SafeVarargs
    public ModRestrictedSlot(IItemHandler itemHandler, int index, int x, int y, Supplier<Item>... possibleItems) {
        super(itemHandler, index, x, y);
        this.possibleItems = possibleItems;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return Arrays.stream(possibleItems).anyMatch(supplier -> supplier.get() == stack.getItem());
    }
}