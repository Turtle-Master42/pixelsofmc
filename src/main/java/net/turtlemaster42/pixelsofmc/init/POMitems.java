package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.item.*;
//POMtabs.PIXELS_OF_MINECRAFT_TAB
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
	public static final RegistryObject<Item> DRAGON_EYE = ITEMS.register("dragon_eye",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VOID_EYE = ITEMS.register("void_eye",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REDSTONE_COUNTER = ITEMS.register("redstone_counter",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DIAMOND_LENS = ITEMS.register("diamond_lens",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VIOLET_DIAMOND_LENS = ITEMS.register("violet_diamond_lens",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RED_DIAMOND_LENS = ITEMS.register("red_diamond_lens",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ENDER_SENSOR = ITEMS.register("ender_sensor",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DRAGON_SENSOR = ITEMS.register("dragon_sensor",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VOID_SENSOR = ITEMS.register("void_sensor",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REDSTONE_LAYERED_WIRE = ITEMS.register("redstone_layered_wire",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SILVER_WIRE = ITEMS.register("silver_wire",
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
	public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
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
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_NUGGET = ITEMS.register("titanium_diboride_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE = ITEMS.register("energy_upgrade",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));

	public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> URANIUM_DUST = ITEMS.register("uranium_dust",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
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
	public static final RegistryObject<Item> SULFUR_CUBE = ITEMS.register("sulfur_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> PHOSPHORUS_CUBE = ITEMS.register("phosphorus_cube",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> HYDROGEN_CANISTER = ITEMS.register("hydrogen_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> HELIUM_CANISTER = ITEMS.register("helium_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> OXYGEN_CANISTER = ITEMS.register("oxygen_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RADON_CANISTER = ITEMS.register("radon_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> XENON_CANISTER = ITEMS.register("xenon_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ARGON_CANISTER = ITEMS.register("argon_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NITROGEN_CANISTER = ITEMS.register("nitrogen_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NEON_CANISTER = ITEMS.register("neon_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> KRYPTON_CANISTER = ITEMS.register("krypton_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> CHLORINE_CANISTER = ITEMS.register("chlorine_canister",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BROMINE_CANISTER = ITEMS.register("bromine_canister",
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

	public static final RegistryObject<Item>  CAESIUM_INGOT = ITEMS.register("caesium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  BARIUM_INGOT = ITEMS.register("barium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  LANTHANUM_INGOT = ITEMS.register("lanthanum_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  CERIUM_INGOT = ITEMS.register("cerium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  PRASEODYMIUM_INGOT = ITEMS.register("praseodymium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  NEODYMIUM_INGOT = ITEMS.register("neodymium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  PROMETHIUM_INGOT = ITEMS.register("promethium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  SAMARIUM_INGOT = ITEMS.register("samarium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  EUROPIUM_INGOT = ITEMS.register("europium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  GADOLINIUM_INGOT = ITEMS.register("gadolinium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  TERBIUM_INGOT = ITEMS.register("terbium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  DYSPROSIUM_INGOT = ITEMS.register("dysprosium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  HOLMIUM_INGOT = ITEMS.register("holmium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  ERBIUM_INGOT = ITEMS.register("erbium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  THULIUM_INGOT = ITEMS.register("thulium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  YTTERBIUM_INGOT = ITEMS.register("ytterbium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  LUTETIUM_INGOT = ITEMS.register("lutetium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  HAFNIUM_INGOT = ITEMS.register("hafnium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  TANTALUM_INGOT = ITEMS.register("tantalum_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  RHENIUM_INGOT = ITEMS.register("rhenium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  OSMIUM_INGOT = ITEMS.register("osmium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  IRIDIUM_INGOT = ITEMS.register("iridium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  PLATINUM_INGOT = ITEMS.register("platinum_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  MERCURY_INGOT = ITEMS.register("mercury_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  THALLIUM_INGOT = ITEMS.register("thallium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  LEAD_INGOT = ITEMS.register("lead_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  BISMUTH_INGOT = ITEMS.register("bismuth_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  POLONIUM_INGOT = ITEMS.register("polonium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  FRANCIUM_INGOT = ITEMS.register("francium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  RADIUM_INGOT = ITEMS.register("radium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  ACTINIUM_INGOT = ITEMS.register("actinium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  THORIUM_INGOT = ITEMS.register("thorium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  PROTACTINIUM_INGOT = ITEMS.register("protactinium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  NEPTUNIUM_INGOT = ITEMS.register("neptunium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  AMERICIUM_INGOT = ITEMS.register("americium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  CURIUM_INGOT = ITEMS.register("curium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  BERKELIUM_INGOT = ITEMS.register("berkelium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  CALIFORNIUM_INGOT = ITEMS.register("californium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item>  EINSTEINIUM_INGOT = ITEMS.register("einsteinium_ingot",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));


	//test
	public static final RegistryObject<Item> BIT_PIXEL = ITEMS.register("bit_pixel",
			() -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}
