package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.item.PowerCellItem;
import net.turtlemaster42.pixelsofmc.util.Element;

import java.util.List;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMtabs {

	private static final List<RegistryObject<Item>> DONT_INCLUDE = List.of(POMitems.PIXEL, POMitems.PIXEL_PILE, POMitems.PLACE_HOLDER);
	private static final List<RegistryObject<Item>> OP_ONLY = List.of(POMitems.DEBUGIUM_INGOT, POMitems.TEST_ITEM, POMitems.INFINITE_POWER_CELL);

	public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(
			Registries.CREATIVE_MODE_TAB, PixelsOfMc.MOD_ID
	);


	private static final RegistryObject<CreativeModeTab> PIXELS_OF_MINECRAFT_TAB = REGISTER.register(
			"main_tab",
			() -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
					.icon(() -> POMitems.NETHERITE_PLATING.get().getDefaultInstance())
					.title(Component.literal("Pixels of Minecraft"))
					.displayItems(POMtabs::pixelsOfMcTab)
					.build()
	);

	private static final RegistryObject<CreativeModeTab> ELEMENTS_TAB = REGISTER.register(
			"elements_tab",
			() -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
					.icon(() -> Element.CALIFORNIUM.atom64().asItem().getDefaultInstance())
					.title(Component.literal("Elements"))
					.withSearchBar()
					.displayItems(POMtabs::elementsTab)
					.build()
	);

	@SubscribeEvent
	public static void fillVanillaTab(BuildCreativeModeTabContentsEvent ev)
	{
		if (ev.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
			ev.accept(POMitems.DEBUGIUM_INGOT.get());
			ev.accept(POMitems.INFINITE_POWER_CELL.get());
			ev.accept(POMblocks.STAR.get());
			ev.accept(POMitems.TEST_ITEM.get());
		}
	}

	private static void pixelsOfMcTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
	{
		for(final RegistryObject<Item> itemRef : POMitems.BLOCK_ITEMS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
		for(final RegistryObject<Item> itemRef : POMitems.STORAGE_BLOCK_ITEMS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
		for(final RegistryObject<Item> itemRef : POMitems.ITEMS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
			if (item instanceof PowerCellItem powerCell) {
				ItemStack powerStack = new ItemStack(powerCell);
				IEnergyStorage energy =  powerStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
				energy.receiveEnergy(energy.getMaxEnergyStored(), false);
				out.accept(powerStack);
			}
		}
		for(final RegistryObject<Item> itemRef : POMitems.NUGGETS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
		for(final RegistryObject<Item> itemRef : POMitems.DUSTS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
		for(final RegistryObject<Item> itemRef : POMitems.BUCKETS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
	}

	private static void elementsTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
	{
		for(final RegistryObject<Item> itemRef : POMitems.ELEMENTS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
		for (Element m: Element.values()) {
			if (m.equals(Element.DEBUGIUM)) continue;
			out.accept(m.pixel());
			out.accept(m.pixelPile());
		}
		for(final RegistryObject<Item> itemRef : POMitems.ATOMS.getEntries()) {
			final Item item = itemRef.get();
			if(DONT_INCLUDE.contains(itemRef) || OP_ONLY.contains(itemRef))
				continue;
			out.accept(item);
		}
	}
}
