package net.turtlemaster42.pixelsofmc.recipe.machines;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecipe implements Recipe<SimpleContainer> {

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static boolean matchMultiInput(SimpleContainer container, List<CountedIngredient> recipeItems, int minSlots, int maxSlots) {
        List<Item> slotItems = new ArrayList<>();
        List<Integer> slotCounts = new ArrayList<>();

        // Iterate over the slots and makes a list of the total ingredients
        for (int slot = minSlots; slot <= maxSlots; slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty())
                continue;

            if (!slotItems.contains(stack.getItem())) {
                slotItems.add(stack.getItem());
                slotCounts.add(stack.getCount());
            } else {
                int index = slotItems.indexOf(stack.getItem());
                slotCounts.set(index, slotCounts.get(index) + stack.getCount());
            }
        }

        // if slotItems and recipeItems are not equal there are ingredients missing or to many
        if (slotItems.size() != recipeItems.size()) {
            return false;
        }

        // Iterates over the needed items
        for (CountedIngredient recipeItem : recipeItems) {
            Item item = recipeItem.asItem();
            // Checks if the item is present in the slots
            if (!slotItems.contains(item)) {
                return false;
            }
            int index = slotItems.indexOf(item);
            // Checks if there is enough items in the slots
            if (recipeItem.count() > slotCounts.get(index)) {
                return false;
            }
            // We win, the item is present and there is enough
        }
        return true;
    }
}
