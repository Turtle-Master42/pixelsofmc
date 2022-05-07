
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pixelsofmc.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.mcreator.pixelsofmc.block.entity.MolucularSplitterBlockEntity;
import net.mcreator.pixelsofmc.block.entity.MolucularDeformerBlockEntity;
import net.mcreator.pixelsofmc.block.entity.MachineBlockBlockEntity;
import net.mcreator.pixelsofmc.block.entity.LargePowerCellBlockEntity;
import net.mcreator.pixelsofmc.block.entity.PixelSplitterBlockEntity;
import net.mcreator.pixelsofmc.PixelsOfMcMod;

public class PixelsOfMcModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PixelsOfMcMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> MOLUCULAR_DEFORMER = register("molucular_deformer", PixelsOfMcModBlocks.MOLUCULAR_DEFORMER,
			MolucularDeformerBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> MOLUCULAR_SPLITTER = register("molucular_splitter", PixelsOfMcModBlocks.MOLUCULAR_SPLITTER,
			MolucularSplitterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> LARGE_POWER_CELL = register("large_power_cell", PixelsOfMcModBlocks.LARGE_POWER_CELL,
			LargePowerCellBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> MACHINE_BLOCK = register("machine_block", PixelsOfMcModBlocks.MACHINE_BLOCK,
			MachineBlockBlockEntity::new);

	public static final RegistryObject<BlockEntityType<?>> PIXEL_SPLITTER = register("pixel_splitter", PixelsOfMcModBlocks.PIXEL_SPLITTER,
			PixelSplitterBlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}
}
