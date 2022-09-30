package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.*;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;


import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.entity.MachineEnergyBlockBlockEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;

    public class MachineEnergyBlock extends Block implements EntityBlock {
        public MachineEnergyBlock() {
            super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).dynamicShape().strength(2f, 3600000f).noOcclusion()
                    .isRedstoneConductor((bs, br, bp) -> false));
        }

        public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
            return true;
        }

        @Deprecated
        public RenderShape getRenderShape(BlockState pState) {
            return RenderShape.INVISIBLE;
        }
        @Deprecated
        public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
            return 1.0F;
        }

        public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
            return true;
        }

        public ItemStack getCloneItemStack(BlockState pState, HitResult pTarget, BlockGetter pLevel, BlockPos pPos, Player pPlayer) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

            int mainX = blockEntity.getTileData().getInt("mainX");
            int mainY = blockEntity.getTileData().getInt("mainY");
            int mainZ = blockEntity.getTileData().getInt("mainZ");


            BlockPos mainPos = new BlockPos(mainX, mainY, mainZ);
            if (mainPos == null) {
                return ItemStack.EMPTY;
            }
            if (blockEntity == null) {
                return ItemStack.EMPTY;
            }
            PixelsOfMc.LOGGER.info(mainX);
            PixelsOfMc.LOGGER.info(mainY);
            PixelsOfMc.LOGGER.info(mainZ);
            PixelsOfMc.LOGGER.info(blockEntity);
            PixelsOfMc.LOGGER.info(mainPos);

            BlockState mainState = pLevel.getBlockState(mainPos);
            return mainState.getBlock().getCloneItemStack(mainState, pTarget, pLevel, mainPos, pPlayer);
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
                    pLevel.destroyBlock(pPos, false);
                    return InteractionResult.FAIL;
                } else {
                    pLevel.destroyBlockProgress(1, getMainPos(pLevel, pPos), 5);
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
            return new MachineEnergyBlockBlockEntity(pos, state);
        }

        @Deprecated
        public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
            super.triggerEvent(state, world, pos, eventID, eventParam);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
        }

        public BlockPos getMainPos(Level pLevel, BlockPos pPos) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

            assert blockEntity != null;
            int mainX = blockEntity.getTileData().getInt("mainX");
            int mainY = blockEntity.getTileData().getInt("mainY");
            int mainZ = blockEntity.getTileData().getInt("mainZ");

            return new BlockPos(mainX, mainY, mainZ);
        }

        @Override
        @Deprecated
        public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
            PixelsOfMc.LOGGER.info(pState.getDestroyProgress(pPlayer, pLevel, pPos));
        }
    }
