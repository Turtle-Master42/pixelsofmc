package net.turtlemaster42.pixelsofmc;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;

import static net.turtlemaster42.pixelsofmc.PixelsOfMc.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void openBookGUI(ItemStack itemStack) {
    }

    public void openBookGUI(ItemStack itemStack, String page) {
    }

    public void clientInit() {
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, PixelSplitterRecipe.Type.ID, PixelSplitterRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, BallMillRecipe.Type.ID, BallMillRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, GrinderRecipe.Type.ID, GrinderRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, HotIsostaticPressRecipe.Type.ID, HotIsostaticPressRecipe.Type.INSTANCE);
    }
}
