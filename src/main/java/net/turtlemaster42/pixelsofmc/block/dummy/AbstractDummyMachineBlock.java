package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDummyMachineBlock extends BaseEntityBlock implements EntityBlock {

    public AbstractDummyMachineBlock(Properties pProp) {
        super(pProp);
    }

    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
        return true;
    }

    @Deprecated
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    @Deprecated
    public float getShadeBrightness(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return true;
    }

    @Deprecated
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Deprecated
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(Blocks.AIR));
    }

    @Nullable
    public static BlockPos getMainBlockPos(BlockGetter world, BlockPos thisPos) {
        AbstractDummyMachineBlockTile te = BigMachineBlockUtil.getTileEntity(AbstractDummyMachineBlockTile.class, world, thisPos);
        if (te != null && !thisPos.equals(te.getMainPos())) {
            return te.getMainPos();
        }
        return null;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState pState, HitResult pTarget, BlockGetter pWorld, BlockPos pPos, Player pPlayer) {
        //gets the main block and returns its getCloneItemStack
        BlockPos mainPos = getMainBlockPos(pWorld, pPos);
        if (mainPos != null && mainPos.equals(pPos))
            return ItemStack.EMPTY;
        else return pWorld.getBlockState(mainPos).getCloneItemStack(pTarget, pWorld, mainPos, pPlayer);
    }

    @Deprecated
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        super.use(pState, pLevel, pPos, pPlayer, hand, hit);
        //when clicked on a dummy block it will go and click on the main block as well
        if (!pLevel.isClientSide()) {
            PixelsOfMc.LOGGER.info("serverside: {}", getMainBlockPos(pLevel, pPos));
            BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
            BlockState mainState = pLevel.getBlockState(mainPos);

            if (mainPos.equals(pPos)) {
                PixelsOfMc.LOGGER.warn("This Machine Block does not have a connected block!");
                pLevel.destroyBlock(pPos, false);
                return InteractionResult.FAIL;
            } else {
                return mainState.getBlock().use(mainState, pLevel, mainPos, pPlayer, hand, hit);
            }
        } else {
            PixelsOfMc.LOGGER.info("clientside: {}", getMainBlockPos(pLevel, pPos));
            return InteractionResult.SUCCESS;
        }
    }

    @Deprecated
    public void neighborChanged(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        //when a dummy block receives a blockupdate it will check if the main block still exists,
        // if not, it will destroy itself and thereby update the surrounding dummy blocks
        BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
        BlockState mainState = pLevel.getBlockState(mainPos);

        if (mainPos == pPos || mainState.getBlock() == Blocks.AIR || mainState.getBlock() == Blocks.VOID_AIR || mainState.getBlock() == Blocks.CAVE_AIR) {
            pLevel.removeBlock(pPos, false);
            pLevel.removeBlockEntity(pPos);
            ((ServerLevel)pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ANVIL.defaultBlockState()), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 20, 0.3f, 0.3f, 0.3f, 0.05d);
        }
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        //when a dummy block is destroyed it will remove the main block from the world and drop
        // the main block as item on its location
        pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.METAL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        if (!pLevel.isClientSide()) {
            BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
            BlockState mainState = pLevel.getBlockState(mainPos);
            if (!pPlayer.isCreative()) {
                popResource(pLevel, pPos, pLevel.getBlockState(mainPos).getBlock().getCloneItemStack(pLevel, mainPos, mainState));
            }
            pLevel.destroyBlock(mainPos, false);
            ((ServerLevel)pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ANVIL.defaultBlockState()), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, 20, 0.3f, 0.3f, 0.3f, 0.05d);
        }
    }


    //pLevel.destroyBlockProgress(1, getMainPos(pLevel, pPos), 5);

    // --- MEKANISM --- //

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, BlockStateBase::getShape);
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, BlockStateBase::getCollisionShape);
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, BlockStateBase::getVisualShape);
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getOcclusionShape(level, p));
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getBlockSupportShape(level, p));
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getInteractionShape(level, p));
    }

    //Context should only be null if there is none, and it isn't used in the shape proxy
    private VoxelShape proxyShape(BlockGetter world, BlockPos pos, @Nullable CollisionContext context, ShapeProxy proxy) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return Shapes.empty();
        }
        BlockState mainState;
        try {
            mainState = world.getBlockState(mainPos);
        } catch (ArrayIndexOutOfBoundsException e) {
            if (world instanceof RenderChunkRegion region) {
                mainState = region.getBlockState(mainPos);
            } else {
                PixelsOfMc.LOGGER.error("Error getting bounding block shape, for position {}, with main position {}. World of type {}", pos, mainPos,
                        world.getClass().getName());
                return Shapes.empty();
            }
        }
        VoxelShape shape = proxy.getShape(mainState, world, mainPos, context);
        BlockPos offset = pos.subtract(mainPos);
        //TODO: Can we somehow cache the withOffset? It potentially would have to then be moved into the Tile, but that is probably fine
        return shape.move(-offset.getX(), -offset.getY(), -offset.getZ());
    }

    private interface ShapeProxy {
        VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);
    }
}
