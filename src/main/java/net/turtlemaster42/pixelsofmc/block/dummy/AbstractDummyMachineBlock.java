package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
public abstract class AbstractDummyMachineBlock extends Block implements EntityBlock {

    public AbstractDummyMachineBlock(Properties pProp) {
        super(pProp);
    }

    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
        return true;
    }

    @Deprecated
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    @Deprecated
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Deprecated
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Deprecated
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
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
    public ItemStack getCloneItemStack(BlockState pState, HitResult pTarget, BlockGetter pWorld, BlockPos pPos, Player pPlayer)
    {
        PixelsOfMc.LOGGER.info("getCloneItemStack getTileEntity");
        AbstractDummyMachineBlockTile blockentity = BigMachineBlockUtil.getTileEntity(AbstractDummyMachineBlockTile.class, pWorld, pPos);


        PixelsOfMc.LOGGER.info("getCloneItemStack mainPos {}", getMainBlockPos(pWorld, pPos));

        PixelsOfMc.LOGGER.info("getCloneItemStack blockEntity {}", blockentity);
        AbstractDummyMachineBlockTile blockentity2 = BigMachineBlockUtil.getTileEntity(AbstractDummyMachineBlockTile.class, Minecraft.getInstance().level, pPos);
        PixelsOfMc.LOGGER.info("getCloneItemStack blockEntity2 {}", blockentity2);

        PixelsOfMc.LOGGER.info("getCloneItemStack blockEntity Tag {}", blockentity.saveWithFullMetadata().copy());
        PixelsOfMc.LOGGER.info("getCloneItemStack Tag BlockPos {}", blockentity.saveWithFullMetadata().get("mainPos"));

        return ItemStack.EMPTY;
    }

    @Nullable
    @Deprecated
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult hit) {
        super.use(pState, pLevel, pPos, pPlayer, hand, hit);

        if (!pLevel.isClientSide()) {

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
            return InteractionResult.SUCCESS;
        }
    }

    @Deprecated
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
        BlockState mainState = pLevel.getBlockState(mainPos);

        if (mainState.getBlock() == Blocks.AIR || mainState.getBlock() == Blocks.VOID_AIR || mainState.getBlock() == Blocks.CAVE_AIR) {
            pLevel.destroyBlock(pPos, false);
        }
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.METAL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        if (!pLevel.isClientSide()) {
            BlockPos mainPos = BigMachineBlockUtil.getMainPos(pLevel, pPos);
            BlockState mainState = pLevel.getBlockState(mainPos);
            if (!pPlayer.isCreative()) {
                popResource(pLevel, pPos, pLevel.getBlockState(mainPos).getBlock().getCloneItemStack(pLevel, mainPos, mainState));
            }
            pLevel.destroyBlock(mainPos, false);
        }
    }
}
