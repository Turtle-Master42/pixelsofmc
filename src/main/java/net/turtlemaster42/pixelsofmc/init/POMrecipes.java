package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.item.crafting.RecipeSerializer;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.recipe.machines.*;

public class POMrecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PixelsOfMc.MOD_ID);
	
	public static final RegistryObject<RecipeSerializer<PixelSplitterRecipe>> PIXEL_SPLITTER_SERIALIZER =
			SERIALIZERS.register("pixel_splitting", () -> PixelSplitterRecipe.Serializer.INSTANCE);

	public static final RegistryObject<RecipeSerializer<BallMillRecipe>> BALL_MILLING_SERIALIZER =
			SERIALIZERS.register("ball_milling", () -> BallMillRecipe.Serializer.INSTANCE);

	public static final RegistryObject<RecipeSerializer<GrinderRecipe>> GRINDING_SERIALIZER =
			SERIALIZERS.register("grinding", () -> GrinderRecipe.Serializer.INSTANCE);

	public static final RegistryObject<RecipeSerializer<HotIsostaticPressRecipe>> PRESSING_SERIALIZER =
			SERIALIZERS.register("pressing", () -> HotIsostaticPressRecipe.Serializer.INSTANCE);


	public static final RegistryObject<DamageToolRecipeSerializer> DAMAGE_TOOL_SERIALIZER =
			SERIALIZERS.register("damage_tool", DamageToolRecipeSerializer::new);
	
	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
	}
}
