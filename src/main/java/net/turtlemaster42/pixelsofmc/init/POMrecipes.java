package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.item.crafting.RecipeSerializer;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.recipe.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipe;

public class POMrecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PixelsOfMc.MOD_ID);
	
	public static final RegistryObject<RecipeSerializer<PixelSplitterRecipe>> PIXEL_SPLITTER_SERIALIZER =
			SERIALIZERS.register("pixel_splitting", () -> PixelSplitterRecipe.Serializer.INSTANCE);

	public static final RegistryObject<RecipeSerializer<BallMillRecipe>> BALL_MILLING_SERIALIZER =
			SERIALIZERS.register("ball_milling", () -> BallMillRecipe.Serializer.INSTANCE);
	
	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
		
	}
	
	

}
