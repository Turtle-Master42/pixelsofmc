package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.turtlemaster42.pixelsofmc.recipe.machines.*;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class POMrecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> DEF_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, PixelsOfMc.MOD_ID);
	
	public static final RegistryObject<RecipeSerializer<PixelSplitterRecipe>> PIXEL_SPLITTER_SERIALIZER =
			SERIALIZERS.register("pixel_splitting", () -> PixelSplitterRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<BallMillRecipe>> BALL_MILLING_SERIALIZER =
			SERIALIZERS.register("ball_milling", () -> BallMillRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<GrinderRecipe>> GRINDING_SERIALIZER =
			SERIALIZERS.register("grinding", () -> GrinderRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<HotIsostaticPressRecipe>> PRESSING_SERIALIZER =
			SERIALIZERS.register("pressing", () -> HotIsostaticPressRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<ChemicalSeperatorRecipe>> CHEMICAL_SEPERATOR_SERIALIZER =
			SERIALIZERS.register("chemical_separating", () -> ChemicalSeperatorRecipe.Serializer.INSTANCE);

	//public static final RegistryObject<SimpleCraftingRecipeSerializer<PixelDecompactingRecipe>> PIXEL_DECOMPACTING = DEF_REG.register("pixel_decompacting", () -> new SimpleCraftingRecipeSerializer<>(PixelDecompactingRecipe::new));
	//public static final RegistryObject<SimpleCraftingRecipeSerializer<PixelCompactingRecipe>> PIXEL_COMPACTING = DEF_REG.register("elytra_wing_glow_recipe", () -> new SimpleCraftingRecipeSerializer<>(PixelCompactingRecipe::new));


	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
		DEF_REG.register(bus);
	}
}
