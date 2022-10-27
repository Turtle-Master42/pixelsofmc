package net.turtlemaster42.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.turtlemaster42.pixelsofmc.block.tile.*;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class POMtiles {
	public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PixelsOfMc.MOD_ID);

	public static final RegistryObject<BlockEntityType<?>> MACHINE_BLOCK = register("machine_block", POMblocks.MACHINE_BLOCK,
			DummyMachineBlockTile::new);

	public static final RegistryObject<BlockEntityType<DummyMachineEnergyBlockTile>> MACHINE_ENERGY_BLOCK =
			TILES.register("machine_energy_block", () -> BlockEntityType.Builder.of(DummyMachineEnergyBlockTile::new, POMblocks.MACHINE_ENERGY_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<DummyMachineItemBlockTile>> MACHINE_ITEM_BLOCK =
			TILES.register("machine_item_block", () -> BlockEntityType.Builder.of(DummyMachineItemBlockTile::new, POMblocks.MACHINE_ITEM_BLOCK.get()).build(null));


	public static final RegistryObject<BlockEntityType<PixelSplitterTile>> PIXEL_SPLITTER =
			TILES.register("pixel_splitter", () -> BlockEntityType.Builder.of(PixelSplitterTile::new, POMblocks.PIXEL_SPLITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BallMillTile>> BALL_MILL =
			TILES.register("ball_mill", () -> BlockEntityType.Builder.of(BallMillTile::new, POMblocks.BALL_MILL.get()).build(null));

	public static final RegistryObject<BlockEntityType<GrinderTile>> GRINDER =
			TILES.register("grinder", () -> BlockEntityType.Builder.of(GrinderTile::new, POMblocks.GRINDER.get()).build(null));

	public static final RegistryObject<BlockEntityType<SDSFusionControllerTile>> SDS_CONTROLLER =
			TILES.register("sds_controller", () -> BlockEntityType.Builder.of(SDSFusionControllerTile::new, POMblocks.SDS_CONTROLLER.get()).build(null));



	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return TILES.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
	public static void register(IEventBus bus) {
		TILES.register(bus);
	}
}
