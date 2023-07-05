package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Element;

import java.util.List;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMtabs {
	public static CreativeModeTab PIXELS_OF_MINECRAFT_TAB;
	public static CreativeModeTab ATOM_TAB;

	private static final List<RegistryObject<Item>> DONT_INCLUDE = List.of(POMitems.PIXEL, POMitems.PIXEL_PILE, POMitems.PLACE_HOLDER);
	private static final List<RegistryObject<Item>> OP_ONLY = List.of(POMitems.DEBUGIUM_INGOT, POMitems.TEST_ITEM);

	@SubscribeEvent
	public static void register(CreativeModeTabEvent.Register event) {
		PIXELS_OF_MINECRAFT_TAB = event.registerCreativeModeTab(new ResourceLocation(PixelsOfMc.MOD_ID, "pixels_of_minecraft_tab"),
				builder -> builder
						.icon(() -> new ItemStack(POMitems.NETHERITE_PLATING.get()))
						.title(Component.translatable("itemGroup.pixels_of_minecraft_tab"))
						.displayItems(
								(pEnabledFeatures, pOutput, pDisplayOperatorCreativeTab) -> {
									POMitems.BLOCK_ITEMS.getEntries().forEach(itemRegistryObject -> {
										if (!DONT_INCLUDE.contains(itemRegistryObject)) {
											if (!OP_ONLY.contains(itemRegistryObject))
												pOutput.accept(itemRegistryObject.get());
											else if (pDisplayOperatorCreativeTab)
												pOutput.accept(itemRegistryObject.get());
										}
									});
									POMitems.ITEMS.getEntries().forEach(itemRegistryObject -> {
										if (!DONT_INCLUDE.contains(itemRegistryObject) && !POMitems.Metals.ATOMX512.containsValue(itemRegistryObject) && !POMitems.Metals.ATOMX64.containsValue(itemRegistryObject)) {
											if (!OP_ONLY.contains(itemRegistryObject))
												pOutput.accept(itemRegistryObject.get());
											else if (pDisplayOperatorCreativeTab)
												pOutput.accept(itemRegistryObject.get());
										}
									});
								})
						.build());

		ATOM_TAB = event.registerCreativeModeTab(new ResourceLocation(PixelsOfMc.MOD_ID, "atom_tab"),
				builder -> builder.icon(() -> new ItemStack(Element.CALIFORNIUM.atom64()))
						.title(Component.translatable("itemGroup.atom_tab"))
						.displayItems(
								(pEnabledFeatures, pOutput, pDisplayOperatorCreativeTab) -> {
									for (Element m: Element.values()) {
										if (m.equals(Element.DEBUGIUM)) continue;
										pOutput.accept(m.atom64());
										pOutput.accept(m.atom512());
										pOutput.accept(m.pixel());
										pOutput.accept(m.pixelPile());
										if (!m.isVanilla())
											pOutput.accept(m.item());
									}
								})
						.withSearchBar()
						.build());
	}
}
