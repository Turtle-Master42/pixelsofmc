
package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.core.BlockPos;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.block.tile.BallMillTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;

import javax.annotation.Nullable;

public class DummyMachineBlock extends AbstractDummyMachineBlock {
	public DummyMachineBlock() {
		super(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).strength(2f, 3600000f).noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false));
	}

	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DummyMachineBlockTile(pos, state);
	}

	@Deprecated
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		if (!pLevel.isClientSide())
			return createTickerHelper(pBlockEntityType, POMtiles.MACHINE_BLOCK.get(), DummyMachineBlockTile::serverTick);
		return null;
	}
}
