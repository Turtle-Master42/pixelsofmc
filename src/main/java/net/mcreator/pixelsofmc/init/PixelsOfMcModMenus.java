
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pixelsofmc.init;

import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;

import net.mcreator.pixelsofmc.world.inventory.MolucularSplitterGuiMenu;
import net.mcreator.pixelsofmc.world.inventory.MolucularDeformerGuiMenu;

import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PixelsOfMcModMenus {
	private static final List<MenuType<?>> REGISTRY = new ArrayList<>();
	public static final MenuType<MolucularDeformerGuiMenu> MOLUCULAR_DEFORMER_GUI = register("molucular_deformer_gui",
			(id, inv, extraData) -> new MolucularDeformerGuiMenu(id, inv, extraData));
	public static final MenuType<MolucularSplitterGuiMenu> MOLUCULAR_SPLITTER_GUI = register("molucular_splitter_gui",
			(id, inv, extraData) -> new MolucularSplitterGuiMenu(id, inv, extraData));

	private static <T extends AbstractContainerMenu> MenuType<T> register(String registryname, IContainerFactory<T> containerFactory) {
		MenuType<T> menuType = new MenuType<T>(containerFactory);
		menuType.setRegistryName(registryname);
		REGISTRY.add(menuType);
		return menuType;
	}

	@SubscribeEvent
	public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
		event.getRegistry().registerAll(REGISTRY.toArray(new MenuType[0]));
	}
}
