package net.turtlemaster42.pixelsofmc.unused;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class FireTrapBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public FireTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /* FACING */

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getClickedFace()).setValue(POWERED, false);
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
        pBuilder.add(FACING, POWERED);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (pState.getValue(POWERED)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY() + 0.5D;
            double d2 = (double)pPos.getZ() + 0.5D;

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();

            double dX = direction$axis == Direction.Axis.X ? (double)direction.getStepX() : 0;
            double dY = direction$axis == Direction.Axis.Y ? (double)direction.getStepY() : 0;
            double dZ = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() : 0;

            if (pRand.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
            //Player player = pLevel.getNearestPlayer(pPos.getX() + dX, pPos.getY() + dY, pPos.getZ() + dZ, 1, false);

            //player.hurt(DamageSource.IN_FIRE, 5);
            //player.setSecondsOnFire(5);



            for(int l = 0; l < 8; ++l) {

                double d3 = pRand.nextDouble() * 0.6D - 0.2D;
                double d4 = pRand.nextDouble() * 0.6D - 0.2D;
                double d5 = pRand.nextDouble() * 0.6D - 0.2D;


                double d6 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d3;
                double d7 = direction$axis == Direction.Axis.Y ? (double)direction.getStepY() * 0.52D : d4;
                double d8 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d5;

                double d9 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.05D : 0;
                double d10 = direction$axis == Direction.Axis.Y ? (double)direction.getStepY() * 0.05D : 0;
                double d11 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.05D : 0;


                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + d3,
                        d1 + d4,
                        d2 + d5,
                        d9, d10, d11);
                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + d4 + dX,
                        d1 + d3 + dY,
                        d2 + d5 + dZ,
                        d9, d10, d11);
                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + d3 + (dX * 2),
                        d1 + d5 + (dY * 2),
                        d2 + d4 + (dZ * 2),
                        d9, d10, d11);
                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + d3 + (dX * 3),
                        d1 + d4 + (dY * 3),
                        d2 + d5 + (dZ * 3),
                        d9, d10, d11);
                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + (d3 * 1.5) + (dX * 4),
                        d1 + (d4 * 1.5) + (dY * 4),
                        d2 + (d5 * 1.5) + (dZ * 4),
                        d9, d10, d11);

                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + (d3 * 0.5),
                        d1 + (d4 * 0.5),
                        d2 + (d5 * 0.5),
                        d9 + (dX * 0.125), d10 + (dY * 0.125), d11 + (dZ * 0.125));
                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + (d3 * 1.2) + dX,
                        d1 + (d4 * 1.2) + dY,
                        d2 + (d5 * 1.2) + dZ,
                        d9 + (dX * 0.125), d10 + (dY * 0.125), d11 + (dZ * 0.125));


                pLevel.addParticle(ParticleTypes.FLAME,
                        d0 + (d3 * 1.5) + (dX * 4),
                        d1 + (d4 * 1.5) + (dY * 4),
                        d2 + (d5 * 1.5) + (dZ * 4),
                        d9 + (dX * 0.025), d10 + (dY * 0.025), d11 + (dZ * 0.025));

            }
        }
    }


    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {

    }

}
