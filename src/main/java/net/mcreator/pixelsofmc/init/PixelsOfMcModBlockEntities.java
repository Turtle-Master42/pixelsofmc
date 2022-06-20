package net.mcreator.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.mcreator.pixelsofmc.block.entity.MachineBlockBlockEntity;
import net.mcreator.pixelsofmc.block.entity.PixelSplitterBlockEntity;
import net.mcreator.pixelsofmc.PixelsOfMcMod;

public class PixelsOfMcModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PixelsOfMcMod.MOD_ID);

	public static final RegistryObject<BlockEntityType<?>> MACHINE_BLOCK = register("machine_block", PixelsOfMcModBlocks.MACHINE_BLOCK,
			MachineBlockBlockEntity::new);

	public static final RegistryObject<BlockEntityType<PixelSplitterBlockEntity>> PIXEL_SPLITTER =
			REGISTRY.register("pixel_splitter", () -> BlockEntityType.Builder.of(PixelSplitterBlockEntity::new, PixelsOfMcModBlocks.PIXEL_SPLITTER.get()).build(null));

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}
}
