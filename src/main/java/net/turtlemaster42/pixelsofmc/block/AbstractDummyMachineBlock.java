package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDummyMachineBlock extends Block implements EntityBlock {
    public AbstractDummyMachineBlock(Properties pProp) {
        super(pProp);
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
            if (blockEntity != null) {
                blockEntity.getTileData().putInt("mainX", pos.getX());
                blockEntity.getTileData().putInt("mainY", pos.getY());
                blockEntity.getTileData().putInt("mainZ", pos.getZ());
                world.sendBlockUpdated(pos, blockstate, blockstate, 3);
            }
        }
    }

    @Nullable
    @Deprecated
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult hit) {
        super.use(pState, pLevel, pPos, pPlayer, hand, hit);

        if (!pLevel.isClientSide()) {

            BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
            BlockState mainState = pLevel.getBlockState(mainPos);

            if (mainPos.equals(pPos)) {
                PixelsOfMc.LOGGER.warn("This Machine Block does not have a connected block!");
                pLevel.destroyBlock(pPos, false);
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
        BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
        BlockState mainState = pLevel.getBlockState(mainPos);

        if (mainState.getBlock() == Blocks.AIR || mainState.getBlock() == Blocks.VOID_AIR || mainState.getBlock() == Blocks.CAVE_AIR) {
            pLevel.destroyBlock(pPos, false);
        }
    }

}
