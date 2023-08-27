package net.turtlemaster42.pixelsofmc.init;

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

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class POMitems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final RegistryObject<Item> BOOK_1 = BLOCK_ITEMS.register("book_1", () -> new book1(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

	//upgrades
	public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE = ITEMS.register("energy_upgrade", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistryObject<Item> HEAT_UPGRADE = ITEMS.register("heat_upgrade", () -> new Item(new Item.Properties().stacksTo(8)));

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
	public static final RegistryObject<Item> CLEANING_CLOTH = ITEMS.register("cleaning_cloth", () -> new ToolItem(new Item.Properties().durability(64)));
	public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new ToolItem(new Item.Properties().durability(128)));
	public static final RegistryObject<Item> WIRECUTTER = ITEMS.register("wirecutter", () -> new ToolItem(new Item.Properties().durability(128)));
	public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new ToolItem(new Item.Properties().durability(256).fireResistant()));
	public static final RegistryObject<Item> CLEANING_SPONGE = ITEMS.register("cleaning_sponge", () -> new SpongeItem(new Item.Properties().stacksTo(1)));


	//compounds
	public static final RegistryObject<Item> BIO_COMPOUND = ITEMS.register("bio_compound", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = ITEMS.register("fire_proof_compound", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> REPELLING_COMPOUND = ITEMS.register("repelling_compound", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BIO_PLASTIC = ITEMS.register("bio_plastic", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = ITEMS.register("fire_proof_plastic", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> REPELLING_PLASTIC = ITEMS.register("repelling_plastic", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SOUL_COAL = ITEMS.register("soul_coal", () -> new FuelItem(2400, new Item.Properties()));
	public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ALUMINIUM_SCRAP = ITEMS.register("aluminium_scrap", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_GOLD_INGOT = ITEMS.register("titanium_gold_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_INGOT = ITEMS.register("titanium_diboride_ingot", () -> new Item(new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> SUPERCONDUCTIVE_INGOT = ITEMS.register("superconductive_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_TUNGSTEN_INGOT = ITEMS.register("red_tungsten_ingot", () -> new Item(new Item.Properties().fireResistant()));

	//nuggets
	public static final RegistryObject<Item> TITANIUM_GOLD_NUGGET = ITEMS.register("titanium_gold_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_NUGGET = ITEMS.register("titanium_diboride_nugget", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_NUGGET = ITEMS.register("superconductive_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_TUNGSTEN_NUGGET = ITEMS.register("red_tungsten_nugget", () -> new Item(new Item.Properties().fireResistant()));

	//crafting ingredients
	public static final RegistryObject<Item> TITANIUM_PLATING = ITEMS.register("titanium_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RUSTED_PLATING = ITEMS.register("rusted_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> NETHERITE_PLATING = ITEMS.register("netherite_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> TITANIUM_GOLD_PLATING = ITEMS.register("titanium_gold_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_PLATING = ITEMS.register("titanium_diboride_plating", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> OBSIDIAN_PLATING = ITEMS.register("obsidian_plating", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_PLATING = ITEMS.register("crying_obsidian_plating", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> DENSE_CARBON_CUBE = ITEMS.register("dense_carbon_cube", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CARBONARO_CLUMP = ITEMS.register("carbonado_clump", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BLACK_DIAMOND = ITEMS.register("black_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_DIAMOND = ITEMS.register("violet_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_DIAMOND = ITEMS.register("red_diamond", () -> new Item(new Item.Properties()));
	
	

	//circuit parts
	public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_WIRE = ITEMS.register("silver_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TUNGSTEN_WIRE = ITEMS.register("tungsten_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_LAYERED_COPPER_WIRE = ITEMS.register("redstone_layered_copper_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_IMBUED_SILVER_WIRE = ITEMS.register("redstone_imbued_silver_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_TUNGSTEN_WIRE = ITEMS.register("red_tungsten_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_WIRE = ITEMS.register("superconductive_wire", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_COUNTER = ITEMS.register("redstone_counter", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REDSTONE_TIMER = ITEMS.register("redstone_timer", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RESONANCE_DETECTOR = ITEMS.register("resonance_detector", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DRAGON_EYE = ITEMS.register("dragon_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VOID_EYE = ITEMS.register("void_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ENDER_SENSOR = ITEMS.register("ender_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DRAGON_SENSOR = ITEMS.register("dragon_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VOID_SENSOR = ITEMS.register("void_sensor", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_LENS = ITEMS.register("diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_DIAMOND_LENS = ITEMS.register("violet_diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_DIAMOND_LENS = ITEMS.register("red_diamond_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> POWER_ORB = ITEMS.register("power_orb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MICRO_CHIP = ITEMS.register("micro_chip", () -> new Item(new Item.Properties()));

	//circuit boards
	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD_1 = ITEMS.register("simple_circuit_board_1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD_1 = ITEMS.register("advanced_circuit_board_1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PERFECTED_CIRCUIT_BOARD_1 = ITEMS.register("perfected_circuit_board_1", () -> new Item(new Item.Properties()));

	//machine parts
	public static final RegistryObject<Item> MOVING_PARTS = ITEMS.register("moving_parts", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADVANCED_LASER = ITEMS.register("advanced_laser", () -> new Item(new Item.Properties()));

	//dusts
	public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", () -> new FuelItem(1200, new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_GOLD_DUST = ITEMS.register("titanium_gold_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_DUST = ITEMS.register("titanium_diboride_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> ANCIENT_DEBRIS_DUST = ITEMS.register("ancient_debris_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> MERCURY_SULFIDE_DUST = ITEMS.register("mercury_sulfide_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ACANTHITE_DUST = ITEMS.register("acanthite_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> OBSIDIAN_DUST = ITEMS.register("obsidian_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_DUST = ITEMS.register("crying_obsidian_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MINERAL_GRIT = ITEMS.register("mineral_grit", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_DUST = ITEMS.register("netherite_dust", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> TITANIUM_OXIDE_DUST = ITEMS.register("titanium_oxide_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUPERCONDUCTIVE_DUST = ITEMS.register("superconductive_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RED_TUNGSTEN_DUST = ITEMS.register("red_tungsten_dust", () -> new Item(new Item.Properties().fireResistant()));

	//fluids
	public static final RegistryObject<Item> MERCURY_BUCKET = ITEMS.register("mercury_bucket", () -> new BucketItem(POMfluids.MERCURY_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> SULFURIC_ACID_BUCKET = ITEMS.register("sulfuric_acid_bucket", () -> new BucketItem(POMfluids.SULFURIC_ACID_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

	public static final RegistryObject<Item> LIQUID_HYDROGEN_BUCKET = ITEMS.register("liquid_hydrogen_bucket", () -> new BucketItem(POMfluids.HYDROGEN_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_NITROGEN_BUCKET = ITEMS.register("liquid_nitrogen_bucket", () -> new BucketItem(POMfluids.NITROGEN_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_OXYGEN_BUCKET = ITEMS.register("liquid_oxygen_bucket", () -> new BucketItem(POMfluids.OXYGEN_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_CHLORINE_BUCKET = ITEMS.register("liquid_chlorine_bucket", () -> new BucketItem(POMfluids.CHLORINE_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> LIQUID_BROMINE_BUCKET = ITEMS.register("liquid_bromine_bucket", () -> new BucketItem(POMfluids.BROMINE_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	//gas
	public static final RegistryObject<Item> HYDROGEN_GAS_BUCKET = ITEMS.register("hydrogen_gas_bucket", () -> new GasBucketItem(POMfluids.HYDROGEN_GAS_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> NITROGEN_GAS_BUCKET = ITEMS.register("nitrogen_gas_bucket", () -> new GasBucketItem(POMfluids.NITROGEN_GAS_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> OXYGEN_GAS_BUCKET = ITEMS.register("oxygen_gas_bucket", () -> new GasBucketItem(POMfluids.OXYGEN_GAS_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> CHLORINE_GAS_BUCKET = ITEMS.register("chlorine_gas_bucket", () -> new GasBucketItem(POMfluids.CHLORINE_GAS_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> BROMINE_GAS_BUCKET = ITEMS.register("bromine_gas_bucket", () -> new GasBucketItem(POMfluids.BROMINE_GAS_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


	public static final class Metals {
		public static final Map<Element, RegistryObject<Item>> ATOMX512 = new EnumMap<>(Element.class);
		public static final Map<Element, RegistryObject<Item>> ATOMX64 = new EnumMap<>(Element.class);
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

				if (m.shouldAddBlock()) block = register(elementName+"_block", () -> new ElementBlockItem(m, m.block(), finalProperties));
				if (!m.isVanilla()) element = register(elementName+"_"+type, () -> new ElementItem(m, finalProperties));
				if (m.shouldAddNugget()) nugget = register(elementName+"_nugget", () -> new ElementItem(m, finalProperties));
				if (m.shouldAddDust()) dust = register(elementName+"_dust", () -> new ElementItem(m, finalProperties));
				atomx512 = register(elementName+"_atom_512", () -> new AtomItem(m, finalProperties));
				atomx64 = register(elementName+"_atom_64", () -> new AtomItem(m, finalProperties));

				ATOMX64.put(m, atomx64.regObject);
				ATOMX512.put(m, atomx512.regObject);
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

	public static void register(IEventBus bus) {
		BLOCK_ITEMS.register(bus);
		ITEMS.register(bus);
		Metals.init();
	}


	//Immersive Engineering
	private static <T extends Item> ItemRegObject<T> register(String name, Supplier<? extends T> make) {
		return new ItemRegObject<>(ITEMS.register(name, make));
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
