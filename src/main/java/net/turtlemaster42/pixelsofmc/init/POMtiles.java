package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineEnergyBlockTile;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineItemBlockTile;
import net.turtlemaster42.pixelsofmc.block.tile.*;

public class POMtiles {
	public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PixelsOfMc.MOD_ID);

	public static final RegistryObject<BlockEntityType<DummyMachineBlockTile>> EXTENDER_BLOCK =
			TILES.register("extender_block", () -> BlockEntityType.Builder.of(DummyMachineBlockTile::new, POMblocks.EXTENDER_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<DummyMachineEnergyBlockTile>> EXTENDER_ENERGY_BLOCK =
			TILES.register("extender_energy_block", () -> BlockEntityType.Builder.of(DummyMachineEnergyBlockTile::new, POMblocks.EXTENDER_ENERGY_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<DummyMachineItemBlockTile>> EXTENDER_ITEM_BLOCK =
			TILES.register("extender_item_block", () -> BlockEntityType.Builder.of(DummyMachineItemBlockTile::new, POMblocks.EXTENDER_ITEM_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<MultiBlockTile>> MULTIBLOCK =
			TILES.register("multiblock", () -> BlockEntityType.Builder.of(MultiBlockTile::new, POMblocks.REINFORCED_GLASS.get(), POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get(), POMblocks.FUSION_CASING.get(), POMblocks.FUSION_CORNER.get(), POMblocks.MACHINE_CASING.get(), POMblocks.ARMORED_MACHINE_CASING.get(), POMblocks.FISSION_CASING.get(), POMblocks.MACHINE_COIL.get()).build(null));
	public static final RegistryObject<BlockEntityType<EnergyPortTile>> ENERGY_PORT =
			TILES.register("energy_port", () -> BlockEntityType.Builder.of(EnergyPortTile::new, POMblocks.ENERGY_PORT.get(), POMblocks.FISSION_ENERGY_PORT.get(), POMblocks.FUSION_ENERGY_PORT.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemPortTile>> ITEM_PORT =
			TILES.register("item_port", () -> BlockEntityType.Builder.of(ItemPortTile::new, POMblocks.ITEM_PORT.get(), POMblocks.FUSION_ITEM_PORT.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidPortTile>> FLUID_PORT =
			TILES.register("fluid_port", () -> BlockEntityType.Builder.of(FluidPortTile::new, POMblocks.FLUID_PORT.get(), POMblocks.FISSION_FLUID_PORT.get(), POMblocks.FUSION_FLUID_PORT.get()).build(null));
	public static final RegistryObject<BlockEntityType<PlasmaPortTile>> PLASMA_PORT =
			TILES.register("plasma_port", () -> BlockEntityType.Builder.of(PlasmaPortTile::new, POMblocks.FUSION_PLASMA_PORT.get()).build(null));
	public static final RegistryObject<BlockEntityType<FuelCellHolderTile>> FUEL_CELL_HOLDER =
			TILES.register("fuel_cell_holder", () -> BlockEntityType.Builder.of(FuelCellHolderTile::new, POMblocks.FUEL_CELL_HOLDER.get()).build(null));



	public static final RegistryObject<BlockEntityType<StarTile>> STAR =
			TILES.register("star", () -> BlockEntityType.Builder.of(StarTile::new, POMblocks.STAR.get()).build(null));

	//machines
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
	public static final RegistryObject<BlockEntityType<ChemicalMixerTile>> CHEMICAL_MIXER =
			TILES.register("chemical_mixer", () -> BlockEntityType.Builder.of(ChemicalMixerTile::new, POMblocks.CHEMICAL_MIXER.get()).build(null));

	//multiblocks
	public static final RegistryObject<BlockEntityType<IndustrialCoolerTile>> INDUSTRIAL_COOLER =
			TILES.register("industrial_cooler", () -> BlockEntityType.Builder.of(IndustrialCoolerTile::new, POMblocks.INDUSTRIAL_COOLER.get()).build(null));

	//reactors
	public static final RegistryObject<BlockEntityType<NuclearReactorTile>> NUCLEAR_REACTOR =
			TILES.register("nuclear_reactor", () -> BlockEntityType.Builder.of(NuclearReactorTile::new, POMblocks.NUCLEAR_REACTOR.get()).build(null));
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
