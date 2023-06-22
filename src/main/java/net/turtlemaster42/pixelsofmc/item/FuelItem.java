package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class FuelItem extends Item {
    private final int time;
    public FuelItem(int time, Properties properties) {
        super(properties);
        this.time = time;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return time;
    }
}
