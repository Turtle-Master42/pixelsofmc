package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.turtlemaster42.pixelsofmc.block.tile.BallMillTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;

import javax.annotation.Nullable;

public class BallMillBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BlockStateProperties.LIT;


    public BallMillBlock(Properties properties) {
        super(properties);
    }


    private static final VoxelShape SHAPE =  VoxelShapeUtils.combine(
            box(-16, 0, -16, 32, 4, 32), //base
            box(-6, 4, -6, 29, 30, 22), //barrel
            box(-10, 4, -8, -6, 32, 24), //barrel rim
            box(-16, 4, 16, -10, 16, 32), //electric intake
            box(-14, 4, 2, -10, 8, 14), //support 1
            box(-16, 8, 0, -10, 24, 16), //hinge 1
            box(29, 4, 2, 32, 8, 14), //support 2
            box(29, 8, 0, 32, 24, 16) //hinge 2
    );

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((Direction)pState.getValue(FACING)) {
            case EAST:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_180);
            case SOUTH:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.COUNTERCLOCKWISE_90);
            case WEST:
                return SHAPE;
            default:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_90);
        }
    }


    /* FACING */

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 &&
                level.getBlockState(blockpos.north()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.east()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.south()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.west()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.north().east()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.east().south()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.south().west()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.west().north()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.north().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.east().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.south().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.west().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.north().east().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.east().south().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.south().west().above()).canBeReplaced(pContext) &&
                level.getBlockState(blockpos.west().north().above()).canBeReplaced(pContext)

        ) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, false);
        } else {
            return null;
        }
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

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof BallMillTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (BallMillTile)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BallMillTile) {
                ((BallMillTile) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }



    @Deprecated
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState oldState, boolean moving) {
        super.onPlace(pState, pLevel, pPos, oldState, moving);
        if (!pLevel.isClientSide()) {
            BlockState MACHINE_BLOCK = POMblocks.MACHINE_BLOCK.get().defaultBlockState();
            BlockState MACHINE_ENERGY_BLOCK = POMblocks.MACHINE_ENERGY_BLOCK.get().defaultBlockState();
            BlockState MACHINE_ITEM_BLOCK = POMblocks.MACHINE_ITEM_BLOCK.get().defaultBlockState();

            //this should always be the same, the only difference should be the name of the DirectionProperty (in this case FACING. This does need to be a DirectionProperty!!!)
            Direction direction = pState.getValue(FACING);

            //these are the location based on the default (NORTH) direction, they get turned automatically
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 0, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 0, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 0, 1, MACHINE_ITEM_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 0, -1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 0, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 0, -1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 0, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 0, -1, MACHINE_ENERGY_BLOCK, pPos);

            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 1, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 1, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, 1, MACHINE_ITEM_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, -1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 1, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 1, -1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 1, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 1, -1, MACHINE_BLOCK, pPos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BallMillTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, POMtiles.BALL_MILL.get(),
                pLevel.isClientSide ? BallMillTile::clientTick : BallMillTile::serverTick);
    }
}