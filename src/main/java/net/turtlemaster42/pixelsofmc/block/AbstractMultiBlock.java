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
import org.jetbrains.annotations.Nullable;

public class AbstractMultiBlock extends BaseEntityBlock implements EntityBlock {
    public AbstractMultiBlock(Properties pProperties) {
        super(pProperties);
    }

    @Deprecated
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    @Deprecated
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Deprecated
    public PushReaction getPistonPushReaction(BlockState state) {
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
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
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AbstractMultiBlockTile(pPos, pState);
    }
}
