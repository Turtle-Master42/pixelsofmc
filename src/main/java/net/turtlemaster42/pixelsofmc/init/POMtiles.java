package net.turtlemaster42.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.block.tile.*;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.*;

public class POMtiles {
	public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PixelsOfMc.MOD_ID);

	public static final RegistryObject<BlockEntityType<DummyMachineBlockTile>> MACHINE_BLOCK =
			TILES.register("machine_block", () -> BlockEntityType.Builder.of(DummyMachineBlockTile::new, POMblocks.MACHINE_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<DummyMachineEnergyBlockTile>> MACHINE_ENERGY_BLOCK =
			TILES.register("machine_energy_block", () -> BlockEntityType.Builder.of(DummyMachineEnergyBlockTile::new, POMblocks.MACHINE_ENERGY_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<DummyMachineItemBlockTile>> MACHINE_ITEM_BLOCK =
			TILES.register("machine_item_block", () -> BlockEntityType.Builder.of(DummyMachineItemBlockTile::new, POMblocks.MACHINE_ITEM_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<MultiBlockTile>> MULTIBLOCK =
			TILES.register("multiblock", () -> BlockEntityType.Builder.of(MultiBlockTile::new, POMblocks.REINFORCED_GLASS.get(), POMblocks.REINFORCED_THING.get(), POMblocks.REINFORCED_CASING.get(), POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get(), POMblocks.FUSION_CASING.get(), POMblocks.FUSION_CORNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<FusionEnergyPortTile>> FUSION_ENERGY_PORT =
			TILES.register("fusion_energy_port", () -> BlockEntityType.Builder.of(FusionEnergyPortTile::new, POMblocks.FUSION_ENERGY_PORT.get()).build(null));
	public static final RegistryObject<BlockEntityType<FusionItemPortTile>> FUSION_ITEM_PORT =
			TILES.register("fusion_item_port", () -> BlockEntityType.Builder.of(FusionItemPortTile::new, POMblocks.FUSION_ITEM_PORT.get()).build(null));



	public static final RegistryObject<BlockEntityType<StarTile>> STAR =
			TILES.register("star", () -> BlockEntityType.Builder.of(StarTile::new, POMblocks.STAR.get()).build(null));


	public static final RegistryObject<BlockEntityType<PixelSplitterTile>> PIXEL_SPLITTER =
			TILES.register("pixel_splitter", () -> BlockEntityType.Builder.of(PixelSplitterTile::new, POMblocks.PIXEL_SPLITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<PixelAssemblerTile>> PIXEL_ASSEMBLER =
			TILES.register("pixel_assembler", () -> BlockEntityType.Builder.of(PixelAssemblerTile::new, POMblocks.PIXEL_ASSEMBLER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BallMillTile>> BALL_MILL =
			TILES.register("ball_mill", () -> BlockEntityType.Builder.of(BallMillTile::new, POMblocks.BALL_MILL.get()).build(null));
	public static final RegistryObject<BlockEntityType<GrinderTile>> GRINDER =
			TILES.register("grinder", () -> BlockEntityType.Builder.of(GrinderTile::new, POMblocks.GRINDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<HotIsostaticPressTile>> HOT_ISOSTATIC_PRESS =
			TILES.register("hot_isostatic_press", () -> BlockEntityType.Builder.of(HotIsostaticPressTile::new, POMblocks.HOT_ISOSTATIC_PRESS.get()).build(null));
	public static final RegistryObject<BlockEntityType<ChemicalSeparatorTile>> CHEMICAL_SEPARATOR =
			TILES.register("chemical_separator", () -> BlockEntityType.Builder.of(ChemicalSeparatorTile::new, POMblocks.CHEMICAL_SEPARATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<ChemicalCombinerTile>> CHEMICAL_COMBINER =
			TILES.register("chemical_combiner", () -> BlockEntityType.Builder.of(ChemicalCombinerTile::new, POMblocks.CHEMICAL_COMBINER.get()).build(null));



	public static final RegistryObject<BlockEntityType<SDSFusionControllerTile>> SDS_CONTROLLER =
			TILES.register("sds_controller", () -> BlockEntityType.Builder.of(SDSFusionControllerTile::new, POMblocks.SDS_CONTROLLER.get(), POMblocks.MDS_CONTROLLER.get(), POMblocks.MNS_CONTROLLER.get(), POMblocks.BH_CONTROLLER.get()).build(null));



	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return TILES.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
	public static void register(IEventBus bus) {
		TILES.register(bus);
	}
}
