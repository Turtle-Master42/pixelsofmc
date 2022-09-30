package net.turtlemaster42.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.turtlemaster42.pixelsofmc.block.entity.BallMillBlockEntity;
import net.turtlemaster42.pixelsofmc.block.entity.MachineBlockBlockEntity;
import net.turtlemaster42.pixelsofmc.block.entity.MachineEnergyBlockBlockEntity;
import net.turtlemaster42.pixelsofmc.block.entity.PixelSplitterBlockEntity;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class POMblockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PixelsOfMc.MOD_ID);

	public static final RegistryObject<BlockEntityType<?>> MACHINE_BLOCK = register("machine_block", POMblocks.MACHINE_BLOCK,
			MachineBlockBlockEntity::new);

	public static final RegistryObject<BlockEntityType<MachineEnergyBlockBlockEntity>> MACHINE_ENERGY_BLOCK =
			BLOCK_ENTITIES.register("machine_energy_block.json", () -> BlockEntityType.Builder.of(MachineEnergyBlockBlockEntity::new, POMblocks.MACHINE_ENERGY_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<PixelSplitterBlockEntity>> PIXEL_SPLITTER =
			BLOCK_ENTITIES.register("pixel_splitter", () -> BlockEntityType.Builder.of(PixelSplitterBlockEntity::new, POMblocks.PIXEL_SPLITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BallMillBlockEntity>> BALL_MILL =
			BLOCK_ENTITIES.register("ball_mill", () -> BlockEntityType.Builder.of(BallMillBlockEntity::new, POMblocks.BALL_MILL.get()).build(null));

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return BLOCK_ENTITIES.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
	public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}
}
