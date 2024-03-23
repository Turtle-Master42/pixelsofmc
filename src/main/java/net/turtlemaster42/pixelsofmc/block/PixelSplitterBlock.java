package net.turtlemaster42.pixelsofmc.block;


import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.block.tile.PixelSplitterTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PixelSplitterBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE =   VoxelShapeUtils.combine(
            box(0, 0, 0, 16, 14, 16), //base
            box(0, 22, 0, 16, 30, 16), //base top
            box(2, 30, 2, 14, 32, 14), //top
            box(1, 14, 2, 4, 22, 5), //pillar back left
            box(12, 14, 2, 15, 22, 5), //pillar back right
            box(1, 14, 11, 4, 22, 14), //pillar front left
            box(12, 14, 11, 15, 22, 14), //pillar front right
            box(2, 14, 5, 3, 22, 11), //wall left
            box(13, 14, 5, 14, 22, 11) //wall right
    );

    public PixelSplitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Deprecated
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case EAST -> VoxelShapeUtils.rotate(SHAPE, Rotation.COUNTERCLOCKWISE_90);
            case SOUTH -> SHAPE;
            case WEST -> VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_90);
            default -> VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_180);
        };
    }
	

    /* FACING */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 &&
                level.getBlockState(blockpos.above()).canBeReplaced(pContext)
        ) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, false);
        } else {
            return null;
        }
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ACTIVE);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }


    public void onPlace(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState oldState, boolean moving) {
        super.onPlace(pState, pLevel, pPos, oldState, moving);
        if (!pLevel.isClientSide()) {
            BlockState MACHINE_BLOCK = POMblocks.MACHINE_BLOCK.get().defaultBlockState();

            //this should always be the same, the only difference should be the name of the DirectionProperty (in this case FACING. This does need to be a DirectionProperty!!!)
            Direction direction = pState.getValue(FACING);

            //these are the location based on the default (NORTH) direction, they get turned automatically
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, 0, MACHINE_BLOCK, pPos);
        }
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PixelSplitterTile) {
                ((PixelSplitterTile) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof PixelSplitterTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (PixelSplitterTile)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new PixelSplitterTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, POMtiles.PIXEL_SPLITTER.get(),
                PixelSplitterTile::tick);
    }
}
