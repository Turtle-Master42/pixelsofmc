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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.MultiBlockStructures;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;

import javax.annotation.Nullable;

public class SDSFusionControllerBlock extends AbstractFusionControllerBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);


    public SDSFusionControllerBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =  VoxelShapeUtils.combine(
            box(-16, 0, 2, 32, 11, 16), //base
            box(-16, 10, 4, 32, 13, 16), //first slope
            box(-16, 12, 8, 32, 15, 16), //second slope
            box(-16, 14, 12, 32, 17, 16) //third slope
    );

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((Direction)pState.getValue(FACING)) {
            case EAST:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_90);
            case SOUTH:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.CLOCKWISE_180);
            case WEST:
                return VoxelShapeUtils.rotate(SHAPE, Rotation.COUNTERCLOCKWISE_90);
            default:
                return SHAPE;
        }
    }


    /* FACING */

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
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (BigMachineBlockUtil.BigMachinePlacement(pContext, 1, 0, 0) &&
                BigMachineBlockUtil.BigMachinePlacement(pContext, -1, 0, 0)
        ) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, 1);
        } else {
            return null;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SDSFusionControllerTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (SDSFusionControllerTile)entity, pPos);
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
            if (blockEntity instanceof SDSFusionControllerTile) {
                ((SDSFusionControllerTile) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Deprecated
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState oldState, boolean moving) {
        super.onPlace(pState, pLevel, pPos, oldState, moving);
        if (!pLevel.isClientSide()) {
            BlockState MACHINE_BLOCK = POMblocks.MACHINE_BLOCK.get().defaultBlockState();

            //this should always be the same, the only difference should be the name of the DirectionProperty (in this case FACING. This does need to be a DirectionProperty!!!)
            Direction direction = pState.getValue(FACING);

            //these are the location based on the default (NORTH) direction, they get turned automatically
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,1, 0, 0, MACHINE_BLOCK, pPos);
            BigMachineBlockUtil.setMachineBlock(pLevel, direction,-1, 0, 0, MACHINE_BLOCK, pPos);
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SDSFusionControllerTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(ACTIVE).equals(1)) return null;
        return createTickerHelper(pBlockEntityType, POMtiles.SDS_CONTROLLER.get(),
                pLevel.isClientSide ? SDSFusionControllerTile::clientTick : SDSFusionControllerTile::serverTick);
    }

    // --- Multi Block --- //
    @Override
    public BlockState[][][] getMultiblockStructure() {
        return MultiBlockStructures.SDS_STRUCTURE;
    }
    @Override
    public int getHeight() {return 5;}
    @Override
    public int getWidth() {return 5;}
    @Override
    public int getLength() {return 5;}
    @Override
    public BlockPos fusionStarPos() {return new BlockPos(2, 2, 2);}
    @Override
    public int fusionStarLevel() {return 1;}
    @Override
    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, -2, 0, 1, pos);
    }
}
