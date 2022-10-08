package net.turtlemaster42.pixelsofmc.recipe;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.Recipe;

public abstract class BaseRecipe implements Recipe<SimpleContainer> {

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

}
