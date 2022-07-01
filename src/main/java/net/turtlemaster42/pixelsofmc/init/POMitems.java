package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.item.*;

public class POMitems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);

	public static final RegistryObject<Item> BOOK_1 = ITEMS.register("book_1", () -> new book1(new Item.Properties().stacksTo(1).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> JAR_OF_PIXELS = ITEMS.register("jar_of_pixels",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_LASER = ITEMS.register("advanced_laser",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_PLATING = ITEMS.register("titanium_plating",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> HEAT_RESISTANT_PLATING = ITEMS.register("heat_resistant_plating",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> ANCIENT_HEAT_PLATE = ITEMS.register("ancient_heat_plate",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	public static final RegistryObject<Item> REFINED_HEAT_RESISTANT_PLATING = ITEMS.register("refined_heat_resistant_plating",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	public static final RegistryObject<Item> BIO_COMPOUND = ITEMS.register("bio_compound",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BIO_PLASTIC_SHEET = ITEMS.register("bio_plastic_sheet",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = ITEMS.register("fire_proof_compound",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = ITEMS.register("fire_proof_plastic",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> REPELLING_COMPOUND = ITEMS.register("repelling_compound",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REPELLING_PLASTIC = ITEMS.register("repelling_plastic",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD = ITEMS.register("simple_circuit_board",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NORMAL_CIRCUIT_BOARD = ITEMS.register("normal_circuit_board",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD = ITEMS.register("advanced_circuit_board",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MICRO_CHIP = ITEMS.register("micro_chip",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_ORB = ITEMS.register("power_orb",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> CIRCLE_SAW = ITEMS.register("circle_saw",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).durability(256)));

	public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DIBORIDE_INGOT = ITEMS.register("titanium_diboride_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> EMPTY_JAR = ITEMS.register("empty_jar",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_IRON = ITEMS.register("jar_of_pixels_iron",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_PLANTS = ITEMS.register("jar_of_pixels_plants",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_COPPER = ITEMS.register("jar_of_pixels_copper",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_DIAMOND = ITEMS.register("jar_of_pixels_diamond",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> JAR_OF_PIXELS_EMERALD = ITEMS.register("jar_of_pixels_emerald",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_DUST = ITEMS.register("steel_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DUST = ITEMS.register("titanium_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> TITANIUM_DIBORIDE_DUST = ITEMS.register("titanium_diboride_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE = ITEMS.register("energy_upgrade",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));


	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}
