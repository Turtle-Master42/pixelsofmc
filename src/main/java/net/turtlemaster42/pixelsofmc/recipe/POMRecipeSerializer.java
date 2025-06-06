package net.turtlemaster42.pixelsofmc.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public interface POMRecipeSerializer<R extends Recipe<?>> extends RecipeSerializer<R> {

    RecipeSerializer<?> setRegistryName();

    @Nullable
    ResourceLocation getRegistryName();

    default Class<RecipeSerializer<?>> getRegistryType() {
        return castClass(RecipeSerializer.class);
    }

    @SuppressWarnings("unchecked") // Need this wrapper, because generics
    private static <G> Class<G> castClass(Class<?> cls) {
        return (Class<G>)cls;
    }
}
