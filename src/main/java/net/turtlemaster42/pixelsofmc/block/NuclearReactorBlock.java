package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.NuclearReactorTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.MultiBlockStructures;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class NuclearReactorBlock extends AbstractMultiControllerBlock {
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);


    public NuclearReactorBlock(Properties properties) {
        super(properties);
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            this.checkIfExtend(pLevel, pPos, pState);
        }

    }

    private void checkIfExtend(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean flag = this.getNeighborSignal(pLevel, pPos);
        if (flag && pState.getValue(ACTIVE) == 2) {
            PixelsOfMc.LOGGER.info("{}, Received Signal", pLevel);
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            pLevel.setBlock(pPos.north(), pLevel.getBlockState(pPos.north()).cycle(FuelCellHolderBlock.CELL_TYPE), 2);
            pLevel.setBlock(pPos.east(), pLevel.getBlockState(pPos.east()).cycle(FuelCellHolderBlock.CELL_TYPE), 2);
            pLevel.setBlock(pPos.south(), pLevel.getBlockState(pPos.south()).cycle(FuelCellHolderBlock.CELL_TYPE), 2);
            pLevel.setBlock(pPos.west(), pLevel.getBlockState(pPos.west()).cycle(FuelCellHolderBlock.CELL_TYPE), 2);
        }
    }

    private boolean getNeighborSignal(SignalGetter pSignalGetter, BlockPos pPos) {
        for(Direction direction : Direction.values()) {
            if (pSignalGetter.hasSignal(pPos.relative(direction), direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof NuclearReactorTile) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (NuclearReactorTile)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new NuclearReactorTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(ACTIVE).equals(1)) return null;
        return createTickerHelper(pBlockEntityType, POMtiles.NUCLEAR_REACTOR.get(),
                pLevel.isClientSide ? NuclearReactorTile::clientTick : NuclearReactorTile::serverTick);
    }

    // --- Multi Block --- //
    @Override
    public BlockState[][][] getMultiblockStructure() {
        return MultiBlockStructures.NUCLEAR_REACTOR;
    }
    @Override
    public int getHeight() {return 5;}
    @Override
    public int getWidth() {return 5;}
    @Override
    public int getLength() {return 5;}
    @Override
    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, -2, -4, -2, pos);
    }
}
