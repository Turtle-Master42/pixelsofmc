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
import net.turtlemaster42.pixelsofmc.block.tile.HotIsostaticPressTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;

import javax.annotation.Nullable;

public class HotIsostaticPressBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BlockStateProperties.LIT;


    public HotIsostaticPressBlock(Properties properties) {
        super(properties);
    }


    private static final VoxelShape SHAPE =  VoxelShapeUtils.combine(
            box(-16, 0, -16, 16, 4, 16), //base
            box(-16, 4, 6, 16, 16, 16), //furnace
            box(-16, 4, -6, 16, 48, 6), //frame
            box(-10, 12, -10, 10, 44, 10), //mold
            box(-12, 16, -12, 12, 20, 12), //ring 1
            box(-12, 26, -12, 12, 30, 12), //ring 2
            box(-12, 36, -12, 12, 40, 12), //ring 3
            box(6, 4, -14, 14, 24, -6) //vent
    );

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((Direction)pState.getValue(FACING)) {
            case EAST:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.COUNTERCLOCKWISE_90);
            case SOUTH:
                return SHAPE;
            case WEST:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_90);
            default:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_180);
        }
    }


    /* FACING */

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 2 &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 0, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 0, 0, 1) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 0, 1) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 0, 1, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 1, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 0, 1, 1) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 1, 1) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 0, 2, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 2, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 0, 2, 1) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 2, 1)
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
            if(entity instanceof HotIsostaticPressTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (HotIsostaticPressTile)entity, pPos);
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
            if (blockEntity instanceof HotIsostaticPressTile) {
                ((HotIsostaticPressTile) blockEntity).drops();
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
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 0, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 0, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 1, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 1, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 1, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 2, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 2, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,0, 2, 1, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 2, 1, MACHINE_BLOCK, pPos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HotIsostaticPressTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, POMtiles.HOT_ISOSTATIC_PRESS.get(),
                pLevel.isClientSide ? HotIsostaticPressTile::clientTick : HotIsostaticPressTile::serverTick);
    }
}