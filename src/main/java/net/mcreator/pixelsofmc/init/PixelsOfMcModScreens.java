
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pixelsofmc.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.pixelsofmc.client.gui.MolucularSplitterGuiScreen;
import net.mcreator.pixelsofmc.client.gui.MolucularDeformerGuiScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PixelsOfMcModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(PixelsOfMcModMenus.MOLUCULAR_DEFORMER_GUI, MolucularDeformerGuiScreen::new);
			MenuScreens.register(PixelsOfMcModMenus.MOLUCULAR_SPLITTER_GUI, MolucularSplitterGuiScreen::new);
		});
	}
}
