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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;

import javax.annotation.Nullable;
import java.util.Arrays;

public class SDSFusionControllerBlock extends BaseEntityBlock {
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
        return createTickerHelper(pBlockEntityType, POMtiles.SDS_CONTROLLER.get(),
                pLevel.isClientSide ? SDSFusionControllerTile::clientTick : SDSFusionControllerTile::serverTick);
    }

    // --- Multi Block --- //

    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you
    private static final BlockState[][][] MULTIBLOCK_STRUCTURE = {
            {
                    {null, POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), null},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {null, POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), null}
            },
            {
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()}
            },
            {
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), null, Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()}
            },
            {
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_GLASS.get().defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()}
            },
            {
                    {null, POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), null},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_GLASS.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState()},
                    {null, POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), POMblocks.REINFORCED_CASING.get().defaultBlockState(), null}
            }
    };

    public int getHeight() {return 5;}
    public int getWidth() {return 5;}
    public int getLength() {return 5;}

    public void validateMultiBlock(Level level, BlockPos controllerPos) {

        if (level.isClientSide()) return;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = controllerState.getValue(FACING);
        int correctBlocks =0;
        int totalBlocks = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;

                    BlockPos rotatedOffsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);

                    if (level.getBlockState(rotatedOffsetPos) == MULTIBLOCK_STRUCTURE[y][z][x]) {
                        correctBlocks++;
                    } else if (MULTIBLOCK_STRUCTURE[y][z][x].is(POMblocks.REINFORCED_GLASS.get())) {
                        if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractFusionCasing) {
                            correctBlocks++;
                        }
                    }
                    if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractMultiBlock multiBlock) {
                        if (level.getBlockEntity(rotatedOffsetPos) instanceof AbstractMultiBlockTile multiBlockTile)
                            multiBlockTile.setMainPos(controllerPos);
                    }
                }

            }

        }
        if (correctBlocks == totalBlocks) {
            if (controllerState.getValue(ACTIVE) != 3)
                    level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
            level.setBlock(rotatedOffsetBlock(direction, fusionStarPos(), controllerPos), POMblocks.STAR.get().defaultBlockState().setValue(StarBlock.STAR_STAGE, fusionStarLevel()), 2);
        }  else {
            level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 1), 2);
            level.destroyBlock(rotatedOffsetBlock(direction, fusionStarPos(), controllerPos), false);
        }
        PixelsOfMc.LOGGER.info("{} out of {}", correctBlocks, totalBlocks);
    }

    public void forcePlaceMultiBlock(Level level, BlockPos controllerPos) {
        Direction direction = level.getBlockState(controllerPos).getValue(FACING);
        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;

                    level.setBlock(rotatedOffsetBlock(direction, x, y, z, controllerPos), MULTIBLOCK_STRUCTURE[y][z][x], 2);
                }
            }
        }
    }

    public BlockPos fusionStarPos() {return new BlockPos(2, 2, 2);}
    public int fusionStarLevel() {return 1;}

    public BlockPos rotatedOffsetBlock (Direction direction, int x, int y, int z, BlockPos startPos) {
        return offsetMultiBlock(BigMachineBlockUtil.rotateBlockPosOnDirection(direction, x, y, z, startPos), direction);
    }

    public BlockPos rotatedOffsetBlock (Direction direction, BlockPos offset, BlockPos startPos) {
        return rotatedOffsetBlock(direction, offset.getX(), offset.getY(), offset.getZ(), startPos);
    }

    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, -2, 1, 1, pos);
    }
    public BlockPos offsetMultiBlock(int x, int y, int z, Direction direction) {
        return offsetMultiBlock(new BlockPos(x,y,z), direction);
    }
}
