package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.PixelCompactingRecipe;
import net.turtlemaster42.pixelsofmc.recipe.PixelDecompactingRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.*;

public class POMrecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PixelsOfMc.MOD_ID);

	public static final RegistryObject<RecipeSerializer<PixelSplitterRecipe>> PIXEL_SPLITTER_SERIALIZER =
			SERIALIZERS.register("pixel_splitting", () -> PixelSplitterRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<BallMillRecipe>> BALL_MILLING_SERIALIZER =
			SERIALIZERS.register("ball_milling", () -> BallMillRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<GrinderRecipe>> GRINDING_SERIALIZER =
			SERIALIZERS.register("grinding", () -> GrinderRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<HotIsostaticPressRecipe>> PRESSING_SERIALIZER =
			SERIALIZERS.register("pressing", () -> HotIsostaticPressRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<ChemicalSeparatorRecipe>> CHEMICAL_SEPARATOR_SERIALIZER =
			SERIALIZERS.register("chemical_separating", () -> ChemicalSeparatorRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<ChemicalCombinerRecipe>> CHEMICAL_COMBINER_SERIALIZER =
			SERIALIZERS.register("chemical_combining", () -> ChemicalCombinerRecipe.Serializer.INSTANCE);
	public static final RegistryObject<RecipeSerializer<FusionRecipe>> FUSION_SERIALIZER =
			SERIALIZERS.register("fusing", () -> FusionRecipe.Serializer.INSTANCE);

	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
		ForgeRegistries.RECIPE_SERIALIZERS.register(PixelsOfMc.MOD_ID + ":pixel_decompacting", PixelDecompactingRecipe.SERIALIZER);
		ForgeRegistries.RECIPE_SERIALIZERS.register(PixelsOfMc.MOD_ID + ":pixel_compacting", PixelCompactingRecipe.SERIALIZER);
	}
}
