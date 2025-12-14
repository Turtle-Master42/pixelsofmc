package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class FuelBlockItem extends BlockItem {
    private final int time;
    public FuelBlockItem(Block block, Properties properties, int time) {
        super(block, properties);
        this.time = time;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return time;
    }
}
