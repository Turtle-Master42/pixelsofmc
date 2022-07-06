
package net.turtlemaster42.pixelsofmc.block;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.procedures.MachineBlockBlockDestroyedByPlayerProcedure;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.block.entity.MachineBlockBlockEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;

public class MachineBlockBlock extends Block
		implements

			EntityBlock {
	public MachineBlockBlock() {
		super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).strength(-1, 3600000).noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false));
	}

	public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
		return true;
	}

	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(Blocks.AIR);
	}

	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(Blocks.AIR));
	}

	public static BlockPos getMainBlockPos(BlockGetter world, BlockPos thisPos) {
		return thisPos;
	}
	@Deprecated
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		if (!world.isClientSide()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity != null)
				blockEntity.getTileData().putDouble("mainX", pos.getX());
				blockEntity.getTileData().putDouble("mainY", pos.getY());
				blockEntity.getTileData().putDouble("mainZ", pos.getZ());
				world.sendBlockUpdated(pos, blockstate, blockstate, 3);
		}
	}

	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, player, willHarvest, fluid);
		MachineBlockBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), player);
		PixelsOfMc.LOGGER.info(retval);
		PixelsOfMc.LOGGER.info(blockstate.getBlock());
		return retval;
	}

	public void attack(BlockState blockstate, Level world, BlockPos pos, Player player) {
		super.attack(blockstate, world, pos, player);
		MachineBlockBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), player);
	}
	@Nullable
	@Deprecated
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		super.use(blockstate, world, pos, player, hand, hit);
		BlockPos mainPos = pos.above();
		if (mainPos == null) {
			return InteractionResult.FAIL;
		}
		BlockState mainState = world.getBlockState(mainPos);
		return mainState.getBlock().use(mainState, world, mainPos, player, hand, hit);
	}

	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MachineBlockBlockEntity(pos, state);
	}

	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderLayer() {
		ItemBlockRenderTypes.setRenderLayer(POMblocks.MACHINE_BLOCK.get(), renderType -> renderType == RenderType.translucent());
	}

}
