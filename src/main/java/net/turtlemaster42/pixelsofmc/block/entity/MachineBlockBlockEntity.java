package net.turtlemaster42.pixelsofmc.block.entity;

import net.turtlemaster42.pixelsofmc.init.POMblockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MachineBlockBlockEntity extends BlockEntity {
	protected final ContainerData data;


	public BlockPos mainPos = null;

	public MachineBlockBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(POMblockEntities.MACHINE_BLOCK.get(), pWorldPosition, pBlockState);
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


	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void invalidateCaps()  {
		super.invalidateCaps();
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
	}
}
