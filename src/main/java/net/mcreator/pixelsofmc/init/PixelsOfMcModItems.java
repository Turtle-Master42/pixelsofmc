package net.mcreator.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.mcreator.pixelsofmc.PixelsOfMcMod;

public class PixelsOfMcModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMcMod.MOD_ID);

	public static final RegistryObject<Item> JAR_OF_PIXELS = REGISTRY.register("jar_of_pixels",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_LASER = REGISTRY.register("advanced_laser",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_PLATING = REGISTRY.register("titanium_plating",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> HEAT_RESISTANT_PLATING = REGISTRY.register("heat_resistant_plating",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> ANCIENT_HEAT_PLATE = REGISTRY.register("ancient_heat_plate",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	public static final RegistryObject<Item> REFINED_HEAT_RESISTANT_PLATING = REGISTRY.register("refined_heat_resistant_plating",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	public static final RegistryObject<Item> BIO_COMPOUND = REGISTRY.register("bio_compound",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BIO_PLASTIC_SHEET = REGISTRY.register("bio_plastic_sheet",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = REGISTRY.register("fire_proof_compound",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = REGISTRY.register("fire_proof_plastic",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> REPELLING_COMPOUND = REGISTRY.register("repelling_compound",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REPELLING_PLASTIC = REGISTRY.register("repelling_plastic",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD = REGISTRY.register("simple_circuit_board",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NORMAL_CIRCUIT_BOARD = REGISTRY.register("normal_circuit_board",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD = REGISTRY.register("advanced_circuit_board",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MICRO_CHIP = REGISTRY.register("micro_chip",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_CELL = REGISTRY.register("power_cell",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_ORB = REGISTRY.register("power_orb",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> CIRCLE_SAW = REGISTRY.register("circle_saw",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).durability(256)));

	public static final RegistryObject<Item> RAW_TITANIUM = REGISTRY.register("raw_titanium",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_INGOT = REGISTRY.register("titanium_ingot",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_NUGGET = REGISTRY.register("titanium_nugget",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DIBORIDE_INGOT = REGISTRY.register("titanium_diboride_ingot",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> EMPTY_JAR = REGISTRY.register("empty_jar",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_IRON = REGISTRY.register("jar_of_pixels_iron",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_PLANTS = REGISTRY.register("jar_of_pixels_plants",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_COPPER = REGISTRY.register("jar_of_pixels_copper",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_DIAMOND = REGISTRY.register("jar_of_pixels_diamond",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_EMERALD = REGISTRY.register("jar_of_pixels_emerald",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COPPER_NUGGET = REGISTRY.register("copper_nugget",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NETHERITE_NUGGET = REGISTRY.register("netherite_nugget",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> COPPER_WIRE = REGISTRY.register("copper_wire",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> IRON_DUST = REGISTRY.register("iron_dust",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_DUST = REGISTRY.register("steel_dust",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COAL_DUST = REGISTRY.register("coal_dust",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DUST = REGISTRY.register("titanium_dust",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DIBORIDE_DUST = REGISTRY.register("titanium_diboride_dust",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_INGOT = REGISTRY.register("steel_ingot",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SPEED_UPGRADE = REGISTRY.register("speed_upgrade",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE = REGISTRY.register("energy_upgrade",
			() -> new Item(new Item.Properties().tab(PixelsOfMcModTabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));


	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}
}
