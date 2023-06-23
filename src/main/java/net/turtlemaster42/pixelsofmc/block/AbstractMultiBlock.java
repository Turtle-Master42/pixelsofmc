package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractMultiBlock extends BaseEntityBlock implements EntityBlock {
    public AbstractMultiBlock(Properties pProperties) {
        super(pProperties);
    }

    @Deprecated
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }
    @Deprecated
    public float getShadeBrightness(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1.0F;
    }

    @Deprecated
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @javax.annotation.Nullable
    public static BlockPos getMainBlockPos(BlockGetter world, BlockPos thisPos) {
        AbstractMultiBlockTile te = BigMachineBlockUtil.getTileEntity(AbstractMultiBlockTile.class, world, thisPos);
        if (te != null && !thisPos.equals(te.getMainPos())) {
            return te.getMainPos();
        }
        return null;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if (!pLevel.isClientSide()) {
            AbstractMultiBlockTile tile = BigMachineBlockUtil.getTileEntity(AbstractMultiBlockTile.class, pLevel, pPos);
            BlockPos mainPos = tile.getMainPos();
            if (pLevel.getBlockState(mainPos).getBlock() instanceof AbstractFusionControllerBlock controller) {
                controller.validateMultiBlock(pLevel, mainPos);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new AbstractMultiBlockTile(pPos, pState);
    }
}
