package net.mcreator.pixelsofmc.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PixelsOfMcModVariables {
	public static File vanillarecipe = new File("");

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
}
