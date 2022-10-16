package net.turtlemaster42.pixelsofmc;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.turtlemaster42.pixelsofmc.recipe.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipe;

import static net.turtlemaster42.pixelsofmc.PixelsOfMc.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void openBookGUI(ItemStack itemStack) {
    }

    public void openBookGUI(ItemStack itemStack, String page) {
    }
    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, PixelSplitterRecipe.Type.ID, PixelSplitterRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, BallMillRecipe.Type.ID, BallMillRecipe.Type.INSTANCE);
    }
}
