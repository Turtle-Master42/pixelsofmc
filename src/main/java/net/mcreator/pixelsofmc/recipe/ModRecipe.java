package net.mcreator.pixelsofmc.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;

import net.mcreator.pixelsofmc.PixelsOfMcMod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipe {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PixelsOfMcMod.MOD_ID);
	
	public static final RegistryObject<RecipeSerializer<PixelSplitterRecipe>> PIXEL_SPLITTER_SERIALIZER =
			SERIALIZERS.register("pixel_splitting", () -> PixelSplitterRecipe.Serializer.INSTANCE);
	
	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
		
	}
	
	

}
