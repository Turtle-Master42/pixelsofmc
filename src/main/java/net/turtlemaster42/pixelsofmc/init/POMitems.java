package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.item.*;
import net.turtlemaster42.pixelsofmc.util.Element;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class POMitems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMc.MOD_ID);
	public static final RegistryObject<Item> BOOK_1 = ITEMS.register("book_1", () -> new book1(new Item.Properties().stacksTo(1).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).rarity(Rarity.UNCOMMON)));

	//compounds
	public static final RegistryObject<Item> BIO_COMPOUND = ITEMS.register("bio_compound", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BIO_PLASTIC = ITEMS.register("bio_plastic", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = ITEMS.register("fire_proof_compound", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = ITEMS.register("fire_proof_plastic", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> REPELLING_COMPOUND = ITEMS.register("repelling_compound", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REPELLING_PLASTIC = ITEMS.register("repelling_plastic", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	//items
	public static final RegistryObject<Item> CARBONARO_CLUMP = ITEMS.register("carbonado_clump", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BLACK_DIAMOND = ITEMS.register("black_diamond", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VIOLET_DIAMOND = ITEMS.register("violet_diamond", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RED_DIAMOND = ITEMS.register("red_diamond", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	
	

	//circuit parts
	public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SILVER_WIRE = ITEMS.register("silver_wire", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REDSTONE_LAYERED_COPPER_WIRE = ITEMS.register("redstone_layered_copper_wire", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_ORB = ITEMS.register("power_orb", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MICRO_CHIP = ITEMS.register("micro_chip", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> REDSTONE_COUNTER = ITEMS.register("redstone_counter", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DRAGON_EYE = ITEMS.register("dragon_eye", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VOID_EYE = ITEMS.register("void_eye", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ENDER_SENSOR = ITEMS.register("ender_sensor", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DRAGON_SENSOR = ITEMS.register("dragon_sensor", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VOID_SENSOR = ITEMS.register("void_sensor", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DIAMOND_LENS = ITEMS.register("diamond_lens", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> VIOLET_DIAMOND_LENS = ITEMS.register("violet_diamond_lens", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RED_DIAMOND_LENS = ITEMS.register("red_diamond_lens", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	//circuit boards
	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD_1 = ITEMS.register("simple_circuit_board_1", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD_1 = ITEMS.register("advanced_circuit_board_1", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> PERFECTED_CIRCUIT_BOARD_1 = ITEMS.register("perfected_circuit_board_1", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));


	public static final RegistryObject<Item> ALUMINIUM_SCRAP = ITEMS.register("aluminium_scrap", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_INGOT = ITEMS.register("titanium_diboride_ingot", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	//nuggets
	public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_NUGGET = ITEMS.register("titanium_diboride_nugget", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	//crafting ingredients
	public static final RegistryObject<Item> TITANIUM_PLATING = ITEMS.register("titanium_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> RUSTED_PLATING = ITEMS.register("rusted_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> NETHERITE_PLATING = ITEMS.register("netherite_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_PLATING = ITEMS.register("titanium_diboride_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> OBSIDIAN_PLATING = ITEMS.register("obsidian_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_PLATING = ITEMS.register("crying_obsidian_plating", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	//fluids
	public static final RegistryObject<Item> MERCURY_BUCKET = ITEMS.register("mercury_bucket", () -> new BucketItem(POMfluids.MERCURY_FLUID, new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(1)));


	//machine parts
	public static final RegistryObject<Item> MOVING_PARTS = ITEMS.register("moving_parts", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> POWER_CELL = ITEMS.register("power_cell", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ADVANCED_LASER = ITEMS.register("advanced_laser", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_CIRCLE_SAW = ITEMS.register("titanium_circle_saw", () -> new Saw(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).durability(256)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_CIRCLE_SAW = ITEMS.register("titanium_diboride_circle_saw", () -> new Saw(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).durability(2048).fireResistant()));
	public static final RegistryObject<Item> INGOT_CAST = ITEMS.register("ingot_cast", () -> new Saw(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> BALL_CAST = ITEMS.register("ball_cast", () -> new Saw(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> PLATE_CAST = ITEMS.register("plate_cast", () -> new Saw(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));


	//upgrades
	public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));
	public static final RegistryObject<Item> ENERGY_UPGRADE = ITEMS.register("energy_upgrade", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));
	public static final RegistryObject<Item> HEAT_UPGRADE = ITEMS.register("heat_upgrade", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(8)));

	//tools
	public static final RegistryObject<Item> CLEANING_CLOTH = ITEMS.register("cleaning_cloth", () -> new ToolItem(new Item.Properties().durability(64).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new ToolItem(new Item.Properties().durability(128).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> WIRECUTTER = ITEMS.register("wirecutter", () -> new ToolItem(new Item.Properties().durability(128).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new ToolItem(new Item.Properties().durability(256).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));

	//milling balls
	public static final RegistryObject<Item> RUBBER_BALL = ITEMS.register("rubber_ball", () -> new Item(new Item.Properties().durability(100).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> FIRE_PROOF_RUBBER_BALL = ITEMS.register("fire_proof_rubber_ball", () -> new Item(new Item.Properties().durability(120).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> REPELLING_RUBBER_BALL = ITEMS.register("repelling_rubber_ball", () -> new Item(new Item.Properties().durability(80).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NETHERITE_BALL = ITEMS.register("netherite_ball", () -> new Item(new Item.Properties().durability(300).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> TITANIUM_BALL = ITEMS.register("titanium_ball", () -> new Item(new Item.Properties().durability(250).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_BALL = ITEMS.register("titanium_diboride_ball", () -> new Item(new Item.Properties().durability(500).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));



	//dusts
	public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> TITANIUM_DIBORIDE_DUST = ITEMS.register("titanium_diboride_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> ANCIENT_DEBRIS_DUST = ITEMS.register("ancient_debris_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> MERCURY_SULFIDE_DUST = ITEMS.register("mercury_sulfide_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> ACANTHITE_DUST = ITEMS.register("acanthite_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> OBSIDIAN_DUST = ITEMS.register("obsidian_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> CRYING_OBSIDIAN_DUST = ITEMS.register("crying_obsidian_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> MINERAL_GRIT = ITEMS.register("mineral_grit", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> NETHERITE_DUST = ITEMS.register("netherite_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
	public static final RegistryObject<Item> TITANIUM_OXIDE_DUST = ITEMS.register("titanium_oxide_dust", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));


	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> DENSE_CARBON_CUBE = ITEMS.register("dense_carbon_cube", () -> new Item(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));



	public static final RegistryObject<Item> CLEANING_SPONGE = ITEMS.register("cleaning_sponge", () -> new SpongeItem(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).stacksTo(1)));


	public static final class Metals {
		public static final Map<Element, ItemRegObject<Item>> ATOMX512 = new EnumMap<>(Element.class);
		public static final Map<Element, ItemRegObject<Item>> ATOMX64 = new EnumMap<>(Element.class);
		public static final Map<Element, ItemRegObject<Item>> ELEMENTS = new EnumMap<>(Element.class);
		public static final Map<Element, ItemRegObject<Item>> NUGGETS = new EnumMap<>(Element.class);
		public static final Map<Element, ItemRegObject<Item>> DUSTS = new EnumMap<>(Element.class);

		private static void init() {
			for(Element m : Element.values()) {
				String elementName = m.tagName();
				String type = m.typeName();
				ItemRegObject<Item> atomx512;
				ItemRegObject<Item> atomx64;
				ItemRegObject<Item> nugget = null;
				ItemRegObject<Item> element = null;
				ItemRegObject<Item> dust = null;

				atomx64 = register(elementName+"_atom_64", AtomItem::new);
				atomx512 = register(elementName+"_atom_512", AtomItem::new);

				if (!m.isVanilla())
					if (m.isFireResistant())
						element = register(elementName+"_"+type, () -> new BaseItem(new BaseItem.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
					else element = register(elementName+"_"+type, BaseItem::new);
				if (m.shouldAddNugget())
					if (m.isFireResistant())
						nugget = register(elementName+"_nugget", () -> new BaseItem(new BaseItem.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
					else nugget = register(elementName+"_nugget", BaseItem::new);
				if (m.shouldAddDust())
					if (m.isFireResistant())
						dust = register(elementName+"_dust", () -> new BaseItem(new BaseItem.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).fireResistant()));
					else dust = register(elementName+"_dust", BaseItem::new);


				ATOMX512.put(m, atomx512);
				ATOMX64.put(m, atomx64);
				NUGGETS.put(m, nugget);
				ELEMENTS.put(m, element);
				DUSTS.put(m, dust);
			}
		}
	}



	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Test(new Item.Properties().stacksTo(1).tab(POMtabs.PIXELS_OF_MINECRAFT_TAB).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> PLACE_HOLDER = ITEMS.register("place_holder", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> DEBUGUIM_INGOT = ITEMS.register("debugium_ingot", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> PIXEL = ITEMS.register("pixel", () -> new Pixel(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));
	public static final RegistryObject<Item> PIXEL_PILE = ITEMS.register("pixel_pile", () -> new Pixel(new Item.Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB)));

	public static void register(IEventBus bus) {
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
