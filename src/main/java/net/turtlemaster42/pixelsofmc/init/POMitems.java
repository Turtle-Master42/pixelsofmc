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
	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Test(new Item.Properties().stacksTo(1).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).rarity(Rarity.UNCOMMON)));

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

	public static final RegistryObject<Item> TITANIUM_CIRCLE_SAW = ITEMS.register("titanium_circle_saw",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).durability(256)));

	public static final RegistryObject<Item> TITANIUM_DIBORIDE_CIRCLE_SAW = ITEMS.register("titanium_diboride_circle_saw",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).durability(2048)));

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

	public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> URANIUM_DUST = ITEMS.register("uranium_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));


	//elements
	public static final RegistryObject<Item> BORON_CUBE = ITEMS.register("boron_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> CARBON_CUBE = ITEMS.register("carbon_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> DENSE_CARBON_CUBE = ITEMS.register("dense_carbon_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> SILICON_CUBE = ITEMS.register("silicon_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BERYLLIUM_INGOT = ITEMS.register("beryllium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> CALCIUM_INGOT = ITEMS.register("calcium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> CHROMIUM_INGOT = ITEMS.register("chromium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> GALLIUM_INGOT = ITEMS.register("gallium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> LITHIUM_INGOT = ITEMS.register("lithium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MANGANESE_INGOT = ITEMS.register("manganese_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MAGNESIUM_INGOT = ITEMS.register("magnesium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POTASSIUM_INGOT = ITEMS.register("potassium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SCANDIUM_INGOT = ITEMS.register("scandium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SODIUM_INGOT = ITEMS.register("sodium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VANADIUM_INGOT = ITEMS.register("vanadium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> RUBIDIUM_INGOT = ITEMS.register("rubidium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> STRONTIUM_INGOT = ITEMS.register("strontium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> YTTRIUM_INGOT = ITEMS.register("yttrium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> ZIRCONIUM_INGOT = ITEMS.register("zirconium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> NIOBIUM_INGOT = ITEMS.register("niobium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> MOLYBDENUM_INGOT = ITEMS.register("molybdenum_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  TECHNETIUM_INGOT = ITEMS.register("technetium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  RUTHENIUM_INGOT = ITEMS.register("ruthenium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  RHODIUM_INGOT = ITEMS.register("rhodium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  PALLADIUM_INGOT = ITEMS.register("palladium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  SILVER_INGOT = ITEMS.register("silver_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  CADMIUM_INGOT = ITEMS.register("cadmium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  INDIUM_INGOT = ITEMS.register("indium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  TIN_INGOT = ITEMS.register("tin_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item>  ANTIMONY_INGOT = ITEMS.register("antimony_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	//test
	public static final RegistryObject<Item> BIT_PIXEL = ITEMS.register("bit_pixel",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}
