package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.item.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class POMitems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> NUGGETS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> DUSTS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> BUCKETS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> ELEMENTS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> ATOMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> STORAGE_BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final RegistryObject<Item> BOOK_1 = BLOCK_ITEMS.register("book_1", () -> new book1(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> RIVER_SHELL = ITEMS.register("river_shell", () -> new RiverShellItem(new Item.Properties().stacksTo(1)));
//	public static final RegistryObject<Item> NEW_RIVER_SHELL = ITEMS.register("new_river_shell", () -> new RiverShellItem(new Item.Properties().stacksTo(1)));

	//upgrades
	public static final RegistryObject<Item> TITANIUM_UPGRADE_TEMPLATE = ITEMS.register("titanium_upgrade_template", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_UPGRADE_TEMPLATE = ITEMS.register("titanium_diboride_upgrade_template", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SPEED_UPGRADE_1 = ITEMS.register("speed_upgrade_1", () -> new Item(new Item.Properties().stacksTo(4)));
	public static final RegistryObject<Item> ENERGY_UPGRADE_1 = ITEMS.register("energy_upgrade_1", () -> new Item(new Item.Properties().stacksTo(4)));
	public static final RegistryObject<Item> HEAT_UPGRADE_1 = ITEMS.register("heat_upgrade_1", () -> new Item(new Item.Properties().stacksTo(4)));
	public static final RegistryObject<Item> SPEED_UPGRADE_2 = ITEMS.register("speed_upgrade_2", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE_2 = ITEMS.register("energy_upgrade_2", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistryObject<Item> HEAT_UPGRADE_2 = ITEMS.register("heat_upgrade_2", () -> new Item(new Item.Properties().stacksTo(8)));


	//milling balls
	public static final RegistryObject<Item> RUBBER_BALL = ITEMS.register("rubber_ball", () -> new Item(new Item.Properties().durability(100)));
	public static final RegistryObject<Item> FIRE_PROOF_RUBBER_BALL = ITEMS.register("fire_proof_rubber_ball", () -> new Item(new Item.Properties().durability(120).fireResistant()));
	public static final RegistryObject<Item> REPELLING_RUBBER_BALL = ITEMS.register("repelling_rubber_ball", () -> new Item(new Item.Properties().durability(80)));
	public static final RegistryObject<Item> NETHERITE_BALL = ITEMS.register("netherite_ball", () -> new Item(new Item.Properties().durability(300).fireResistant()));
	public static final RegistryObject<Item> TITANIUM_BALL = ITEMS.register("titanium_ball", () -> new Item(new Item.Properties().durability(250)));
	public static final RegistryObject<Item> TITANIUM_GOLD_BALL = ITEMS.register("titanium_gold_ball", () -> new Item(new Item.Properties().durability(400)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_BALL = ITEMS.register("titanium_diboride_ball", () -> new Item(new Item.Properties().durability(500).fireResistant()));

	public static final RegistryObject<Item> TITANIUM_CIRCLE_SAW = ITEMS.register("titanium_circle_saw", () -> new SawItem(new Item.Properties().durability(256)));
	public static final RegistryObject<Item> TITANIUM_GOLD_CIRCLE_SAW = ITEMS.register("titanium_gold_circle_saw", () -> new SawItem(new Item.Properties().durability(1280)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_CIRCLE_SAW = ITEMS.register("titanium_diboride_circle_saw", () -> new SawItem(new Item.Properties().durability(2048).fireResistant()));
	public static final RegistryObject<Item> INGOT_CAST = ITEMS.register("ingot_cast", () -> new SawItem(new Item.Properties()));
	public static final RegistryObject<Item> BALL_CAST = ITEMS.register("ball_cast", () -> new SawItem(new Item.Properties()));
	public static final RegistryObject<Item> PLATE_CAST = ITEMS.register("plate_cast", () -> new SawItem(new Item.Properties()));

	//tools
	public static final RegistryObject<Item> CLEANING_CLOTH = ITEMS.register("cleaning_cloth", () -> new ToolItem(new Item.Properties().durability(80)));
	public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new ToolItem(new Item.Properties().durability(240)));
	public static final RegistryObject<Item> WIRECUTTER = ITEMS.register("wirecutter", () -> new ToolItem(new Item.Properties().durability(240)));
	public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new ToolItem(new Item.Properties().durability(380).fireResistant()));
	public static final RegistryObject<Item> CLEANING_SPONGE = ITEMS.register("cleaning_sponge", () -> new SpongeItem(new Item.Properties().stacksTo(1)));


	//compounds
	public static final RegistryObject<Item> BIO_COMPOUND = ITEMS.register("bio_compound", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = ITEMS.register("fire_proof_compound", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> REPELLING_COMPOUND = ITEMS.register("repelling_compound", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BIO_PLASTIC = ITEMS.register("bio_plastic", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = ITEMS.register("fire_proof_plastic", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> REPELLING_PLASTIC = ITEMS.register("repelling_plastic", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SOUL_COAL = ITEMS.register("soul_coal", () -> new FuelItem(2400, new Item.Properties()));
	public static final RegistryObject<Item> PYROLYTIC_CARBON = ITEMS.register("pyrolytic_carbon", () -> new FuelItem(2000, new Item.Properties()));
	public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_TUNGSTEN_AMALGAMATION = ITEMS.register("royal_tungsten_amalgamation", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ALUMINIUM_SCRAP = ITEMS.register("aluminium_scrap", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_GOLD_INGOT = ITEMS.register("titanium_gold_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_INGOT = ITEMS.register("titanium_diboride_ingot", () -> new Item(new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> SUPERCONDUCTIVE_INGOT = ITEMS.register("superconductive_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_SILVER_INGOT = ITEMS.register("red_silver_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_TUNGSTEN_INGOT = ITEMS.register("royal_tungsten_ingot", () -> new Item(new Item.Properties().fireResistant()));

	//nuggets
	public static final RegistryObject<Item> TITANIUM_GOLD_NUGGET = NUGGETS.register("titanium_gold_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_NUGGET = NUGGETS.register("titanium_diboride_nugget", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> NETHERITE_NUGGET = NUGGETS.register("netherite_nugget", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_NUGGET = NUGGETS.register("superconductive_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_SILVER_NUGGET = NUGGETS.register("red_silver_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_TUNGSTEN_NUGGET = NUGGETS.register("royal_tungsten_nugget", () -> new Item(new Item.Properties().fireResistant()));

	//crafting ingredients
	public static final RegistryObject<Item> TITANIUM_PLATING = ITEMS.register("titanium_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RUSTED_PLATING = ITEMS.register("rusted_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> NETHERITE_PLATING = ITEMS.register("netherite_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> TITANIUM_GOLD_PLATING = ITEMS.register("titanium_gold_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_PLATING = ITEMS.register("titanium_diboride_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> OBSIDIAN_PLATING = ITEMS.register("obsidian_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_PLATING = ITEMS.register("crying_obsidian_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_PLATING = ITEMS.register("lead_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TUNGSTEN_PLATING = ITEMS.register("tungsten_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> PYROLYTIC_CARBON_SHEET = ITEMS.register("pyrolytic_carbon_sheet", () -> new FuelItem(8000, new Item.Properties()));
	public static final RegistryObject<Item> SILICON_SHEET = ITEMS.register("silicon_sheet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DENSE_CARBON_CUBE = ITEMS.register("dense_carbon_cube", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CARBONARO_CLUMP = ITEMS.register("carbonado_clump", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BLACK_DIAMOND = ITEMS.register("black_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PERFECT_DIAMOND = ITEMS.register("perfect_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_DIAMOND = ITEMS.register("violet_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_DIAMOND = ITEMS.register("red_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MANA_AMALGAMATION = ITEMS.register("mana_amalgamation", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MANA_SPHERE = ITEMS.register("mana_sphere", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRIMSON_MANA_SPHERE = ITEMS.register("crimson_mana_sphere", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GLEAMING_MANA_SPHERE = ITEMS.register("gleaming_mana_sphere", () -> new Item(new Item.Properties()));


	//circuit parts
	public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_WIRE = ITEMS.register("silver_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TUNGSTEN_WIRE = ITEMS.register("tungsten_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_LAYERED_COPPER_WIRE = ITEMS.register("redstone_layered_copper_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_SILVER_WIRE = ITEMS.register("red_silver_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_TUNGSTEN_WIRE = ITEMS.register("royal_tungsten_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_WIRE = ITEMS.register("superconductive_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_COUNTER = ITEMS.register("redstone_counter", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_TIMER = ITEMS.register("redstone_timer", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_DETECTOR = ITEMS.register("redstone_detector", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DRAGON_EYE = ITEMS.register("dragon_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VOID_EYE = ITEMS.register("void_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ENDER_SENSOR = ITEMS.register("ender_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DRAGON_SENSOR = ITEMS.register("dragon_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VOID_SENSOR = ITEMS.register("void_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_LENS = ITEMS.register("diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_DIAMOND_LENS = ITEMS.register("violet_diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_DIAMOND_LENS = ITEMS.register("red_diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_CORE = ITEMS.register("redstone_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRUDE_POWER_CORE = ITEMS.register("crude_power_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> POWER_ORB = ITEMS.register("power_orb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> OVERCHARGED_POWER_ORB = ITEMS.register("overcharged_power_orb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUPERCHARGED_POWER_ORB = ITEMS.register("supercharged_power_orb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> YELLOWCAKE_URANIUM = ITEMS.register("yellowcake_uranium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> URANIUM_FUEL_PELLET = ITEMS.register("uranium_fuel_pellet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> URANIUM_FUEL_CORE = ITEMS.register("uranium_fuel_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PLUTONIUM_FUEL_PELLET = ITEMS.register("plutonium_fuel_pellet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PLUTONIUM_FUEL_CORE = ITEMS.register("plutonium_fuel_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MICRO_CHIP = ITEMS.register("micro_chip", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> IMPROVED_MICRO_CHIP = ITEMS.register("improved_micro_chip", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CARBON_HEAT_SINK = ITEMS.register("carbon_heat_sink", () -> new Item(new Item.Properties()));


	//circuit boards
	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD_1 = ITEMS.register("simple_circuit_board_1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD_1 = ITEMS.register("advanced_circuit_board_1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PERFECTED_CIRCUIT_BOARD_1 = ITEMS.register("perfected_circuit_board_1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD_2 = ITEMS.register("simple_circuit_board_2", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD_2 = ITEMS.register("advanced_circuit_board_2", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PERFECTED_CIRCUIT_BOARD_2 = ITEMS.register("perfected_circuit_board_2", () -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> EMPTY_CELL = ITEMS.register("empty_cell", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell", () -> new PowerCellItem(new Item.Properties(), 8_000_000, new Color(0, 205 ,255).getRGB(), ChatFormatting.AQUA));
	public static final RegistryObject<Item> OVERCHARGED_POWER_CELL = ITEMS.register("overcharged_power_cell", () -> new PowerCellItem(new Item.Properties().rarity(Rarity.UNCOMMON), 64_000_000, new Color(205, 100 ,255).getRGB(), ChatFormatting.LIGHT_PURPLE));
	public static final RegistryObject<Item> SUPERCHARGED_POWER_CELL = ITEMS.register("supercharged_power_cell", () -> new PowerCellItem(new Item.Properties().rarity(Rarity.RARE), 512_000_000, 16733525, ChatFormatting.RED));
	public static final RegistryObject<Item> EMPTY_FUEL_CELL = ITEMS.register("empty_fuel_cell", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> DEPLETED_URANIUM_FUEL_CELL = ITEMS.register("depleted_uranium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), 20, "textures/item/depleted_uranium_fuel_model.png"));
	public static final RegistryObject<Item> URANIUM_FUEL_CELL = ITEMS.register("uranium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), DEPLETED_URANIUM_FUEL_CELL.get(), 500, new Color(0, 160, 0).getRGB(), "textures/item/uranium_fuel_model.png")); //total 8_000_000 FE
	public static final RegistryObject<Item> DEPLETED_ENRICHED_URANIUM_FUEL_CELL = ITEMS.register("depleted_enriched_uranium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), 80, "textures/item/depleted_enriched_uranium_fuel_model.png"));
	public static final RegistryObject<Item> ENRICHED_URANIUM_FUEL_CELL = ITEMS.register("enriched_uranium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), DEPLETED_ENRICHED_URANIUM_FUEL_CELL.get(), 2000, new Color(0, 255, 0).getRGB(), "textures/item/enriched_uranium_fuel_model.png")); //total 32_000_000 FE
	public static final RegistryObject<Item> DEPLETED_PLUTONIUM_FUEL_CELL = ITEMS.register("depleted_plutonium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), 320, "textures/item/depleted_plutonium_fuel_model.png"));
	public static final RegistryObject<Item> PLUTONIUM_FUEL_CELL = ITEMS.register("plutonium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), DEPLETED_PLUTONIUM_FUEL_CELL.get(), 8000, new Color(0, 200, 175).getRGB(), "textures/item/plutonium_fuel_model.png")); //total 128_000_000 FE
	public static final RegistryObject<Item> DEPLETED_ENRICHED_PLUTONIUM_FUEL_CELL = ITEMS.register("depleted_enriched_plutonium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), 1280, "textures/item/depleted_enriched_plutonium_fuel_model.png"));
	public static final RegistryObject<Item> ENRICHED_PLUTONIUM_FUEL_CELL = ITEMS.register("enriched_plutonium_fuel_cell", () -> new FuelCellItem(new Item.Properties(), DEPLETED_ENRICHED_PLUTONIUM_FUEL_CELL.get(), 32000, new Color(0, 255, 225).getRGB(), "textures/item/enriched_plutonium_fuel_model.png")); //total 512_000_000 FE
	public static final RegistryObject<Item> ADVANCED_LASER = ITEMS.register("advanced_laser", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FUSION_LINING = ITEMS.register("fusion_lining", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FUSION_PLATING = ITEMS.register("fusion_plating", () -> new Item(new Item.Properties()));

	//dusts
	public static final RegistryObject<Item> COAL_DUST = DUSTS.register("coal_dust", () -> new FuelItem(1200, new Item.Properties()));
	public static final RegistryObject<Item> QUARTZ_DUST = DUSTS.register("quartz_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_GOLD_DUST = DUSTS.register("titanium_gold_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_DUST = DUSTS.register("titanium_diboride_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> ANCIENT_DEBRIS_DUST = DUSTS.register("ancient_debris_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> MERCURY_SULFIDE_DUST = DUSTS.register("mercury_sulfide_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ACANTHITE_DUST = DUSTS.register("acanthite_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> OBSIDIAN_DUST = DUSTS.register("obsidian_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_DUST = DUSTS.register("crying_obsidian_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MINERAL_GRIT = DUSTS.register("mineral_grit", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_DUST = DUSTS.register("netherite_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> TITANIUM_OXIDE_DUST = DUSTS.register("titanium_oxide_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_DUST = DUSTS.register("superconductive_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_SILVER_DUST = DUSTS.register("red_silver_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_TUNGSTEN_DUST = DUSTS.register("royal_tungsten_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> REFINED_REDSTONE = DUSTS.register("refined_redstone", () -> new Item(new Item.Properties()));

	//fluids
	public static final RegistryObject<Item> TITANIUM_BUCKET = BUCKETS.register("titanium_bucket", () -> new BigBucket(new Item.Properties().stacksTo(1), 1000));
	public static final RegistryObject<Item> REINFORCED_BUCKET = BUCKETS.register("reinforced_bucket", () -> new BigBucket(new Item.Properties().stacksTo(1), 4000));

	public static final RegistryObject<Item> MERCURY_BUCKET = BUCKETS.register("mercury_bucket", () -> new BucketItem(POMfluids.MERCURY, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_LEAD_BUCKET = BUCKETS.register("liquid_lead_bucket", () -> new BucketItem(POMfluids.LEAD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> SULFURIC_ACID_BUCKET = BUCKETS.register("sulfuric_acid_bucket", () -> new BucketItem(POMfluids.SULFURIC_ACID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> NITRIC_ACID_BUCKET = BUCKETS.register("nitric_acid_bucket", () -> new BucketItem(POMfluids.NITRIC_ACID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> PUREX_SOLUTION_BUCKET = BUCKETS.register("purex_solution_bucket", () -> new BucketItem(POMfluids.PUREX_SOLUTION, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> RED_OIL_BUCKET = BUCKETS.register("red_oil_bucket", () -> new BucketItem(POMfluids.RED_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> NUCLEAR_WASTE_BUCKET = BUCKETS.register("nuclear_waste_bucket", () -> new BucketItem(POMfluids.NUCLEAR_WASTE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> NUCLEAR_WASTE_SOLUTION_BUCKET = BUCKETS.register("nuclear_waste_solution_bucket", () -> new BucketItem(POMfluids.NUCLEAR_WASTE_SOLUTION, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> URANIUM_SOLUTION_BUCKET = BUCKETS.register("uranium_solution_bucket", () -> new BucketItem(POMfluids.URANIUM_SOLUTION, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> PLUTONIUM_SOLUTION_BUCKET = BUCKETS.register("plutonium_solution_bucket", () -> new BucketItem(POMfluids.PLUTONIUM_SOLUTION, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

	public static final RegistryObject<Item> LIQUID_HYDROGEN_BUCKET = BUCKETS.register("liquid_hydrogen_bucket", () -> new BucketItem(POMfluids.HYDROGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_NITROGEN_BUCKET = BUCKETS.register("liquid_nitrogen_bucket", () -> new BucketItem(POMfluids.NITROGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_OXYGEN_BUCKET = BUCKETS.register("liquid_oxygen_bucket", () -> new BucketItem(POMfluids.OXYGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_CHLORINE_BUCKET = BUCKETS.register("liquid_chlorine_bucket", () -> new BucketItem(POMfluids.CHLORINE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_BROMINE_BUCKET = BUCKETS.register("liquid_bromine_bucket", () -> new BucketItem(POMfluids.BROMINE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	//gas
	public static final RegistryObject<Item> STEAM_BUCKET = BUCKETS.register("steam_bucket", () -> new GasBucketItem(POMfluids.STEAM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> BLAZING_STEAM_BUCKET = BUCKETS.register("blazing_steam_bucket", () -> new GasBucketItem(POMfluids.BLAZING_STEAM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> MERCURY_GAS_BUCKET = BUCKETS.register("mercury_gas_bucket", () -> new GasBucketItem(POMfluids.MERCURY_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LEAD_GAS_BUCKET = BUCKETS.register("lead_gas_bucket", () -> new GasBucketItem(POMfluids.LEAD_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> HYDROGEN_GAS_BUCKET = BUCKETS.register("hydrogen_gas_bucket", () -> new GasBucketItem(POMfluids.HYDROGEN_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> NITROGEN_GAS_BUCKET = BUCKETS.register("nitrogen_gas_bucket", () -> new GasBucketItem(POMfluids.NITROGEN_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> OXYGEN_GAS_BUCKET = BUCKETS.register("oxygen_gas_bucket", () -> new GasBucketItem(POMfluids.OXYGEN_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> CHLORINE_GAS_BUCKET = BUCKETS.register("chlorine_gas_bucket", () -> new GasBucketItem(POMfluids.CHLORINE_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> BROMINE_GAS_BUCKET = BUCKETS.register("bromine_gas_bucket", () -> new GasBucketItem(POMfluids.BROMINE_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> AMMONIA_GAS_BUCKET = BUCKETS.register("ammonia_gas_bucket", () -> new GasBucketItem(POMfluids.AMMONIA_GAS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


	public static final class Elements {
		public static final Map<Element, RegistryObject<Item>> ATOMX512 = new EnumMap<>(Element.class);
		public static final Map<Element, RegistryObject<Item>> ATOMX64 = new EnumMap<>(Element.class);
		public static final Map<String, RegistryObject<Item>> ISOTOPEX512 = new HashMap<>();
		public static final Map<String, RegistryObject<Item>> ISOTOPEX64 = new HashMap<>();
		public static final Map<Element, RegistryObject<Item>> ELEMENTS = new EnumMap<>(Element.class);
		public static final Map<Element, RegistryObject<Item>> NUGGETS = new EnumMap<>(Element.class);
		public static final Map<Element, RegistryObject<Item>> DUSTS = new EnumMap<>(Element.class);
		public static final Map<Element, RegistryObject<Item>> BLOCKS = new EnumMap<>(Element.class);

		private static void init() {
			for(Element m : Element.values()) {
				if (m.equals(Element.DEBUGIUM)) continue;
				String elementName = m.elementName();
				String type = m.typeName();
				ItemRegObject<Item> atomx512;
				ItemRegObject<Item> atomx64;
				ItemRegObject<Item> nugget = null;
				ItemRegObject<Item> element = null;
				ItemRegObject<Item> dust = null;
				ItemRegObject<Item> block = null;

				Item.Properties properties = new Item.Properties();
				if (m.isFireResistant())
					properties = new Item.Properties().fireResistant();
				final Item.Properties finalProperties = properties;

				if (m.shouldAddBlock()) block = registerStorageBlockItem(elementName+"_block", () -> new ElementBlockItem(m, m.block(), finalProperties));
				if (!m.isVanilla()) element = registerElement(elementName+"_"+type, () -> new ElementItem(m, finalProperties));
				if (m.shouldAddNugget()) nugget = registerNugget(elementName+"_nugget", () -> new ElementItem(m, finalProperties));
				if (m.shouldAddDust()) dust = registerDust(elementName+"_dust", () -> new ElementItem(m, finalProperties));
				atomx64 = registerAtom(elementName+"_atom_64", () -> new AtomItem(m, finalProperties));
				atomx512 = registerAtom(elementName+"_atom_512", () -> new AtomItem(m, finalProperties));
				ATOMX64.put(m, atomx64.regObject);
				ATOMX512.put(m, atomx512.regObject);
				for (int i = 0; i < m.getIsotopes().getNeutrons().length; i++) {
					int extraNeutrons = m.getIsotopes().getNeutrons()[i] - m.getNeutrons();
					ItemRegObject<Item> isotopex64 = registerAtom(elementName+"_"+(m.getIsotopes().getNeutrons()[i] + m.getElement())+"_atom_64", () -> new AtomItem(m, extraNeutrons, finalProperties));
					ItemRegObject<Item> isotopex512 = registerAtom(elementName+"_"+(m.getIsotopes().getNeutrons()[i] + m.getElement())+"_atom_512", () -> new AtomItem(m, extraNeutrons, finalProperties));
					ISOTOPEX64.put(m + "_" + (m.getIsotopes().getNeutrons()[i] + m.getElement()), isotopex64.regObject);
					ISOTOPEX512.put(m + "_" + (m.getIsotopes().getNeutrons()[i] + m.getElement()), isotopex512.regObject);
				}

				if (dust != null)
					DUSTS.put(m, dust.regObject);
				if (nugget != null)
					NUGGETS.put(m, nugget.regObject);
				if (element != null)
					ELEMENTS.put(m, element.regObject);
				if (block != null)
					BLOCKS.put(m, block.regObject);
			}
		}
	}

	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Test(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> PLACE_HOLDER = ITEMS.register("place_holder", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> DEBUGIUM_INGOT = ITEMS.register("debugium_ingot", () -> new Debugium(Element.DEBUGIUM, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> PIXEL = ITEMS.register("pixel", () -> new PixelItem(new Item.Properties(), 4096));
	public static final RegistryObject<Item> PIXEL_PILE = ITEMS.register("pixel_pile", () -> new PixelItem(new Item.Properties(), 32768));
	public static final RegistryObject<Item> INFINITE_POWER_CELL = ITEMS.register("infinite_power_cell", () -> new InfinitePowerCellItem(new Item.Properties().rarity(Rarity.EPIC), new InfiniteNumber().fromString("1000000000000000000000000000000000000"), new Color(255, 170, 0).getRGB(), ChatFormatting.GOLD));

	public static void register(IEventBus bus) {
		BLOCK_ITEMS.register(bus);
		STORAGE_BLOCK_ITEMS.register(bus);
		ITEMS.register(bus);
		NUGGETS.register(bus);
		DUSTS.register(bus);
		BUCKETS.register(bus);
		ELEMENTS.register(bus);
		ATOMS.register(bus);
		Elements.init();
	}


	//Immersive Engineering
	private static <T extends Item> ItemRegObject<T> register(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(ITEMS.register(name, make));
	}

	private static <T extends Item> ItemRegObject<T> registerElement(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(ELEMENTS.register(name, make));
	}

	private static <T extends Item> ItemRegObject<T> registerNugget(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(NUGGETS.register(name, make));
	}

	private static <T extends Item> ItemRegObject<T> registerDust(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(DUSTS.register(name, make));
	}

	private static <T extends Item> ItemRegObject<T> registerStorageBlockItem(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(STORAGE_BLOCK_ITEMS.register(name, make));
	}


	private static <T extends Item> ItemRegObject<T> registerAtom(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(ATOMS.register(name, make));
	}

	public static class ItemRegObject<T extends Item> implements Supplier<T>, ItemLike {
		private final RegistryObject<T> regObject;
		private ItemRegObject(RegistryObject<T> regObject)
		{
			this.regObject = regObject;
		}
		@Override
		@Nonnull
		public T get()
		{
			return regObject.get();
		}
		@Nonnull
		@Override
		public Item asItem()
		{
			return regObject.get();
		}
		public ResourceLocation getId()
		{
			return regObject.getId();
		}
	}
}
