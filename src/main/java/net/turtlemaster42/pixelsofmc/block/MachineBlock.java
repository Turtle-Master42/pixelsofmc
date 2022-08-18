
package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.block.entity.MachineBlockBlockEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class MachineBlock extends Block implements EntityBlock {
	public MachineBlock() {
		super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).strength(2f, 3600000f).noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false));
	}

	public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
		return true;
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	public ItemStack getCloneItemStack(BlockState pState, HitResult pTarget, BlockGetter pLevel, BlockPos pPos, Player pPlayer) {
		BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

		assert blockEntity != null;
		int mainX = blockEntity.getTileData().getInt("mainX");
		int mainY = blockEntity.getTileData().getInt("mainY");
		int mainZ = blockEntity.getTileData().getInt("mainZ");

		BlockPos mainPos = new BlockPos(mainX, mainY, mainZ);
		BlockState mainState = pLevel.getBlockState(mainPos);

		Level level = Objects.requireNonNull(pLevel.getBlockEntity(pPos)).getLevel();

		PixelsOfMc.LOGGER.info(level);
		PixelsOfMc.LOGGER.info(pPlayer.getLevel());
		PixelsOfMc.LOGGER.info(pLevel);
		PixelsOfMc.LOGGER.info(mainPos);
		PixelsOfMc.LOGGER.info(mainState);

		return new ItemStack(mainState.getBlock());
	}

	@Deprecated
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Deprecated
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(Blocks.AIR));
	}

	@Deprecated
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		if (!world.isClientSide()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity != null)
				blockEntity.getTileData().putInt("mainX", pos.getX());
				blockEntity.getTileData().putInt("mainY", pos.getY());
				blockEntity.getTileData().putInt("mainZ", pos.getZ());
				world.sendBlockUpdated(pos, blockstate, blockstate, 3);
		}
	}

	public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {

		pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.METAL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);

		if (!pLevel.isClientSide()) {
			BlockPos mainPos = getMainPos(pLevel, pPos);
			BlockState mainState = pLevel.getBlockState(mainPos);

			if (!pPlayer.isCreative()) {
				popResource(pLevel, pPos, pLevel.getBlockState(mainPos).getBlock().getCloneItemStack(pLevel, mainPos, mainState));
			}

			pLevel.destroyBlock(mainPos, false);
		}
	}

	@Nullable
	@Deprecated
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult hit) {
		super.use(pState, pLevel, pPos, pPlayer, hand, hit);

		if (!pLevel.isClientSide()) {

			BlockPos mainPos = getMainPos(pLevel, pPos);
			BlockState mainState = pLevel.getBlockState(mainPos);

			if (mainPos.equals(pPos)) {
				PixelsOfMc.LOGGER.warn("This Machine Block does not have a connected block!");
				return InteractionResult.FAIL;
			} else {

				return mainState.getBlock().use(mainState, pLevel, mainPos, pPlayer, hand, hit);
			}
		} else {
			return InteractionResult.SUCCESS;
		}
	}

	@Deprecated
	public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		BlockPos mainPos = getMainPos(pLevel, pPos);
		BlockState mainState = pLevel.getBlockState(mainPos);

		if (mainState.getBlock() == Blocks.AIR || mainState.getBlock() == Blocks.VOID_AIR || mainState.getBlock() == Blocks.CAVE_AIR) {
			pLevel.destroyBlock(pPos, false);
		}
	}

	@Deprecated
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MachineBlockBlockEntity(pos, state);
	}

	@Deprecated
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderLayer() {
		ItemBlockRenderTypes.setRenderLayer(POMblocks.MACHINE_BLOCK.get(), renderType -> renderType == RenderType.translucent());
	}

	public BlockPos getMainPos(Level pLevel, BlockPos pPos) {
		BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

		assert blockEntity != null;
		int mainX = blockEntity.getTileData().getInt("mainX");
		int mainY = blockEntity.getTileData().getInt("mainY");
		int mainZ = blockEntity.getTileData().getInt("mainZ");

		return new BlockPos(mainX, mainY, mainZ);
	}
}
