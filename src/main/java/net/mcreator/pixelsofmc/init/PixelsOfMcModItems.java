
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pixelsofmc.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

import net.mcreator.pixelsofmc.item.TitaniumNuggetItem;
import net.mcreator.pixelsofmc.item.TitaniumItem;
import net.mcreator.pixelsofmc.item.SimpleCircuitBoardItem;
import net.mcreator.pixelsofmc.item.RepellingPlasticItem;
import net.mcreator.pixelsofmc.item.RepellingCompoundItem;
import net.mcreator.pixelsofmc.item.PowerOrbItem;
import net.mcreator.pixelsofmc.item.PowerCellItem;
import net.mcreator.pixelsofmc.item.NormalCircuitBoardItem;
import net.mcreator.pixelsofmc.item.NetheriteNuggetItem;
import net.mcreator.pixelsofmc.item.MicroChipItem;
import net.mcreator.pixelsofmc.item.JarofPixelsItem;
import net.mcreator.pixelsofmc.item.JarOfPixelsIronItem;
import net.mcreator.pixelsofmc.item.JarOfPixelsEmeraldItem;
import net.mcreator.pixelsofmc.item.JarOfPixelsDiamondItem;
import net.mcreator.pixelsofmc.item.JarOfPixelsCopperItem;
import net.mcreator.pixelsofmc.item.JarOfPixelPlantsItem;
import net.mcreator.pixelsofmc.item.HeatResistantPlatingItem;
import net.mcreator.pixelsofmc.item.FireProofPlasticItem;
import net.mcreator.pixelsofmc.item.FireProofCompoundItem;
import net.mcreator.pixelsofmc.item.EmptyJarItem;
import net.mcreator.pixelsofmc.item.CoppernuggetItem;
import net.mcreator.pixelsofmc.item.CopperWireItem;
import net.mcreator.pixelsofmc.item.CircleSawItem;
import net.mcreator.pixelsofmc.item.BioPlasticSheetItem;
import net.mcreator.pixelsofmc.item.BioCompoundItem;
import net.mcreator.pixelsofmc.item.AncientHeatPlateItem;
import net.mcreator.pixelsofmc.item.AdvancedLaserItem;
import net.mcreator.pixelsofmc.item.AdvancedCircuitBoardItem;
import net.mcreator.pixelsofmc.PixelsOfMcMod;

public class PixelsOfMcModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PixelsOfMcMod.MODID);

	public static final RegistryObject<Item> PIXEL_SPLITTER = block(PixelsOfMcModBlocks.PIXEL_SPLITTER,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);

	public static final RegistryObject<Item> JAROF_PIXELS = REGISTRY.register("jarof_pixels", () -> new JarofPixelsItem());
	public static final RegistryObject<Item> MOLUCULAR_DEFORMER = block(PixelsOfMcModBlocks.MOLUCULAR_DEFORMER,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> MOLUCULAR_SPLITTER = block(PixelsOfMcModBlocks.MOLUCULAR_SPLITTER,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> LARGE_POWER_CELL = block(PixelsOfMcModBlocks.LARGE_POWER_CELL,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> HEAT_RESTISTANT_CASING = block(PixelsOfMcModBlocks.HEAT_RESTISTANT_CASING,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> DEEPSLATE_TITANIUM_ORE = block(PixelsOfMcModBlocks.DEEPSLATE_TITANIUM_ORE,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> TITANIUM_ORE = block(PixelsOfMcModBlocks.TITANIUM_ORE, PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> TITANIUM_BLOCK = block(PixelsOfMcModBlocks.TITANIUM_BLOCK,
			PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> ADVANCED_LASER = REGISTRY.register("advanced_laser", () -> new AdvancedLaserItem());
	public static final RegistryObject<Item> HEAT_RESISTANT_PLATING = REGISTRY.register("heat_resistant_plating",
			() -> new HeatResistantPlatingItem());
	public static final RegistryObject<Item> ANCIENT_HEAT_PLATE = REGISTRY.register("ancient_heat_plate", () -> new AncientHeatPlateItem());
	public static final RegistryObject<Item> BIO_COMPOUND = REGISTRY.register("bio_compound", () -> new BioCompoundItem());
	public static final RegistryObject<Item> BIO_PLASTIC_SHEET = REGISTRY.register("bio_plastic_sheet", () -> new BioPlasticSheetItem());
	public static final RegistryObject<Item> SIMPLE_CIRCUIT_BOARD = REGISTRY.register("simple_circuit_board", () -> new SimpleCircuitBoardItem());
	public static final RegistryObject<Item> NORMAL_CIRCUIT_BOARD = REGISTRY.register("normal_circuit_board", () -> new NormalCircuitBoardItem());
	public static final RegistryObject<Item> ADVANCED_CIRCUIT_BOARD = REGISTRY.register("advanced_circuit_board",
			() -> new AdvancedCircuitBoardItem());
	public static final RegistryObject<Item> POWER_CELL = REGISTRY.register("power_cell", () -> new PowerCellItem());
	public static final RegistryObject<Item> POWER_ORB = REGISTRY.register("power_orb", () -> new PowerOrbItem());
	public static final RegistryObject<Item> CIRCLE_SAW = REGISTRY.register("circle_saw", () -> new CircleSawItem());
	public static final RegistryObject<Item> TITANIUM_INGOT = REGISTRY.register("titanium_ingot", () -> new TitaniumItem());
	public static final RegistryObject<Item> TITANIUM_NUGGET = REGISTRY.register("titanium_nugget", () -> new TitaniumNuggetItem());
	public static final RegistryObject<Item> EMPTY_JAR = REGISTRY.register("empty_jar", () -> new EmptyJarItem());
	public static final RegistryObject<Item> JAR_OF_PIXELS_IRON = REGISTRY.register("jar_of_pixels_iron", () -> new JarOfPixelsIronItem());
	public static final RegistryObject<Item> JAR_OF_PIXELS_PLANTS = REGISTRY.register("jar_of_pixels_plants", () -> new JarOfPixelPlantsItem());
	public static final RegistryObject<Item> JAR_OF_PIXELS_COPPER = REGISTRY.register("jar_of_pixels_copper", () -> new JarOfPixelsCopperItem());
	public static final RegistryObject<Item> JAR_OF_PIXELS_DIAMOND = REGISTRY.register("jar_of_pixels_diamond", () -> new JarOfPixelsDiamondItem());
	public static final RegistryObject<Item> JAR_OF_PIXELS_EMERALD = REGISTRY.register("jar_of_pixels_emerald", () -> new JarOfPixelsEmeraldItem());
	public static final RegistryObject<Item> MACHINE_BLOCK = block(PixelsOfMcModBlocks.MACHINE_BLOCK, PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> BALL_MILL = block(PixelsOfMcModBlocks.BALL_MILL, PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> TEST = block(PixelsOfMcModBlocks.TEST, PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB);
	public static final RegistryObject<Item> FIRE_PROOF_COMPOUND = REGISTRY.register("fire_proof_compound", () -> new FireProofCompoundItem());
	public static final RegistryObject<Item> FIRE_PROOF_PLASTIC = REGISTRY.register("fire_proof_plastic", () -> new FireProofPlasticItem());
	public static final RegistryObject<Item> REPELLING_COMPOUND = REGISTRY.register("repelling_compound", () -> new RepellingCompoundItem());
	public static final RegistryObject<Item> REPELLING_PLASTIC = REGISTRY.register("repelling_plastic", () -> new RepellingPlasticItem());
	public static final RegistryObject<Item> COPPERNUGGET = REGISTRY.register("coppernugget", () -> new CoppernuggetItem());
	public static final RegistryObject<Item> NETHERITE_NUGGET = REGISTRY.register("netherite_nugget", () -> new NetheriteNuggetItem());
	public static final RegistryObject<Item> MICRO_CHIP = REGISTRY.register("micro_chip", () -> new MicroChipItem());
	public static final RegistryObject<Item> COPPER_WIRE = REGISTRY.register("copper_wire", () -> new CopperWireItem());

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
