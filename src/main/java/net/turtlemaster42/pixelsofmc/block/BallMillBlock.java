package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class BallMillBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BlockStateProperties.LIT;


    public BallMillBlock(Properties properties) {
        super(properties);
    }


    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    /* FACING */

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, false);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ACTIVE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }



    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState oldState, boolean moving) {
        super.onPlace(pState, pLevel, pPos, oldState, moving);
        if (!pLevel.isClientSide()) {

            setMachineBlock(pLevel, new BlockPos(pPos.getX(),pPos.getY()+1,pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY(),pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY(),pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX(),pPos.getY(),pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX(),pPos.getY(),pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY(),pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY(),pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY(),pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY(),pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);

            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY()+1,pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY()+1,pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX(),pPos.getY()+1,pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX(),pPos.getY()+1,pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY()+1,pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()+1,pPos.getY()+1,pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY()+1,pPos.getZ()+1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
            setMachineBlock(pLevel, new BlockPos(pPos.getX()-1,pPos.getY()+1,pPos.getZ()-1), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), pPos);
        }
    }


    //recomended: setMachineBlock(Level, new BlockPos(pPos.getX() + 1, pPos.getY(), pPos.getZ()), POMblocks.MACHINE_BLOCK.get().defaultBlockState(), 2, pPos);
    public void setMachineBlock(Level pLevel, BlockPos pPos, BlockState pState, int flag,BlockPos mainPos) {
        pLevel.setBlock(pPos, pState, flag);
        setMainPos(pLevel, pPos, mainPos);
    }

    public void setMachineBlock(Level pLevel, BlockPos pPos, BlockState pState,BlockPos mainPos) {
        pLevel.setBlock(pPos, pState, 2);
        setMainPos(pLevel, pPos, mainPos);
    }

    public void setMainPos(Level pLevel, BlockPos pPos, BlockPos mainPos) {
        BlockEntity pBlockentity = pLevel.getBlockEntity(pPos);

        assert pBlockentity != null;
        pBlockentity.getTileData().putInt("mainX", mainPos.getX());
        pBlockentity.getTileData().putInt("mainY", mainPos.getY());
        pBlockentity.getTileData().putInt("mainZ", mainPos.getZ());
    }
}