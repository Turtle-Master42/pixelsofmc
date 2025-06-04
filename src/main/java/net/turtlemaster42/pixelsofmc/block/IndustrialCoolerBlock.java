package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.turtlemaster42.pixelsofmc.block.tile.IndustrialCoolerTile;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.GhostBlockState;
import net.turtlemaster42.pixelsofmc.util.block.MultiBlockStructures;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IndustrialCoolerBlock extends AbstractMultiControllerBlock {
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);
    public static final DirectionProperty FACING = DirectionalBlock.FACING;


    public IndustrialCoolerBlock(Properties properties) {
        super(POMtags.Blocks.FUSION_CASINGS, properties);
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
    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite()).setValue(ACTIVE, 1);
    }

    @Override
    public Direction getControllerDirection(BlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide() && pState.getValue(ACTIVE) != 1) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof IndustrialCoolerTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (IndustrialCoolerTile)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new IndustrialCoolerTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, POMtiles.INDUSTRIAL_COOLER.get(),
                pLevel.isClientSide ? IndustrialCoolerTile::clientTick : IndustrialCoolerTile::serverTick);
    }

    // --- Multi Block --- //
    @Override
    public GhostBlockState[][][] getMultiblockStructure() {
        return MultiBlockStructures.INDUSTRIAL_COOLER;
    }
    @Override
    public int getHeight() {return 3;}
    @Override
    public int getWidth() {return 3;}
    @Override
    public int getLength() {return 5;}
    @Override
    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, -1, -2, 0, pos);
    }
}
