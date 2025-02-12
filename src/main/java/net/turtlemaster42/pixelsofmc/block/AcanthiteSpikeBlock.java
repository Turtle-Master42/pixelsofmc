package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

import javax.annotation.Nullable;

public class AcanthiteSpikeBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty SPIKE_TYPE = IntegerProperty.create("spike_type", 0, 2);
    //0 normal, 1 long, 2 double

    public AcanthiteSpikeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        double offset = 3;
        if (pState.getValue(SPIKE_TYPE) != 0) {
            double size = 16;
            return switch (direction) {
                case NORTH -> Block.box(offset, offset, (16 - size), (16 - offset), (16 - offset), 16);
                case SOUTH -> Block.box(offset, offset, 0, (16 - offset), (16 - offset), size);
                case EAST -> Block.box(0, offset, offset, size, (16 - offset), (16 - offset));
                case WEST -> Block.box(16 - size, offset, offset, 16, (16 - offset), (16 - offset));
                case DOWN -> Block.box(offset, (16 - size), offset, (16 - offset), 16, (16 - offset));
                default -> Block.box(offset, 0.0D, offset, (16 - offset), size, (16 - offset));
            };
        } else {
            double size = 8;
            return switch (direction) {
                case NORTH -> Block.box(offset, offset, (16 - size), (16 - offset), (16 - offset), 16);
                case SOUTH -> Block.box(offset, offset, 0, (16 - offset), (16 - offset), size);
                case EAST -> Block.box(0, offset, offset, size, (16 - offset), (16 - offset));
                case WEST -> Block.box(16 - size, offset, offset, 16, (16 - offset), (16 - offset));
                case DOWN -> Block.box(offset, (16 - size), offset, (16 - offset), 16, (16 - offset));
                default -> Block.box(offset, 0.0D, offset, (16 - offset), size, (16 - offset));
            };
        }
    }
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction.getOpposite());
        return pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, direction);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getFluidState().isEmpty();
    }

    @Override
    public boolean isPossibleToRespawnInThis(BlockState pState) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return pType == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(pState, pLevel, pPos, pType);
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pDirection == pState.getValue(FACING)) {
            if (pNeighborState.getBlock() == POMblocks.ACANTHITE_SPIKE.get() && pNeighborState.getValue(FACING) == pState.getValue(FACING).getOpposite()) {
                return pState.setValue(SPIKE_TYPE, 1);
            }
            if (!pNeighborState.isFaceSturdy(pLevel, pNeighborPos, pDirection.getOpposite()) && !pNeighborState.getBlock().equals(POMblocks.ACANTHITE_SPIKE.get())) {
                if (!pLevel.isClientSide())
                    ((ServerLevel)pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, POMblocks.ACANTHITE_SPIKE.get().defaultBlockState()), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 20, 0.3f, 0.3f, 0.3f, 0.05d);
                pLevel.gameEvent(GameEvent.BLOCK_DESTROY, pPos, GameEvent.Context.of(null, pState));
                return pState.setValue(SPIKE_TYPE, 0);
            }
        }
        if (pDirection == pState.getValue(FACING).getOpposite()) {
            if (!pState.canSurvive(pLevel, pPos)) {
                if (pState.getValue(SPIKE_TYPE) == 2) {
                    if (!pLevel.isClientSide())
                        ((ServerLevel)pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, POMblocks.ACANTHITE_SPIKE.get().defaultBlockState()), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 20, 0.3f, 0.3f, 0.3f, 0.05d);
                    pLevel.gameEvent(GameEvent.BLOCK_DESTROY, pPos, GameEvent.Context.of(null, pState));
                    return pState.setValue(SPIKE_TYPE, 0).setValue(FACING, pDirection);
                }

                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();

        BlockState returnState = this.defaultBlockState().setValue(SPIKE_TYPE, 0).setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER).setValue(FACING, pContext.getClickedFace());

        BlockPos facingPos = blockpos.relative(pContext.getClickedFace(), 1);
        BlockState facingState = pContext.getLevel().getBlockState(facingPos);

        if (facingState.getBlock().equals(POMblocks.ACANTHITE_SPIKE.get()) && facingState.getValue(FACING) == pContext.getClickedFace().getOpposite()) {//
            returnState = returnState.setValue(SPIKE_TYPE, 1);
        } else if (facingState.isFaceSturdy(levelaccessor, facingPos, pContext.getClickedFace().getOpposite())) {
            returnState = returnState.setValue(SPIKE_TYPE, 2);
        }
        return returnState;
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED, FACING, SPIKE_TYPE);
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pState.getValue(FACING) == Direction.UP && pState.getValue(SPIKE_TYPE) == 0) {
            pEntity.causeFallDamage(pFallDistance + 2.0F, 3.0F, pLevel.damageSources().stalagmite());
        } else {
            if (pFallDistance > 4) {
                pEntity.causeFallDamage(pFallDistance, 0.5f, pLevel.damageSources().stalagmite());
            } else {
                super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
            }
        }
        if (pFallDistance > 4) {
            pLevel.destroyBlock(pPos, true, pEntity);
        }
    }
}
