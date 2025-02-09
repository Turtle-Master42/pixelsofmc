package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.NuclearReactorTile;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.MultiBlockStructures;
import net.turtlemaster42.pixelsofmc.util.block.VoxelShapeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class NuclearReactorBlock extends AbstractMultiControllerBlock {
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);


    public NuclearReactorBlock(Properties properties) {
        super(properties);
    }

    /* FACING */

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
