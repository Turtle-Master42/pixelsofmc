package net.turtlemaster42.pixelsofmc.block.dummy.tile;

import net.minecraft.nbt.NbtUtils;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.network.PacketUpdateTile;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;
import org.jetbrains.annotations.NotNull;

public class DummyMachineBlockTile extends AbstractDummyMachineBlockTile {
	protected final ContainerData data;

	public DummyMachineBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
		super(POMtiles.MACHINE_BLOCK.get(), pWorldPosition, pBlockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return 0;
			}
			@Override
			public void set(int pIndex, int pValue) {
			}
			@Override
			public int getCount() {
				return 0;
			}
		};
	}
}
