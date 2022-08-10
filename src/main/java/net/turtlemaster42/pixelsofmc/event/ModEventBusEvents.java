package net.turtlemaster42.pixelsofmc.event;

import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, PixelSplitterRecipe.Type.ID, PixelSplitterRecipe.Type.INSTANCE);
    }
}
