package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.turtlemaster42.pixelsofmc.block.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.block.tile.FusionFluidPortTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FusionFluidPortBlock extends AbstractPort {
    public FusionFluidPortBlock(Properties pProperties) {
        super(pProperties);
    }


    public @NotNull InteractionResult use(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack handItem = pPlayer.getItemInHand(pHand);
        if (handItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
        handItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            if (pLevel.getBlockEntity(pPos) instanceof AbstractMultiBlockTile tile) {
                BlockEntity mainEntity = pLevel.getBlockEntity(tile.getMainPos());
                if (mainEntity instanceof IFluidHandlingTile fluidHandler && mainEntity instanceof IDuoFluidHandlingTile duoFluidHandler) {
                    if (!pState.getValue(MODE).equals(1) && handler.fill(duoFluidHandler.getDuoFluid(), IFluidHandler.FluidAction.SIMULATE) > 0) { //handler.getFluidInTank(0).isEmpty() &&
                        int fluidAmount = handler.fill(duoFluidHandler.getDuoFluid(), IFluidHandler.FluidAction.EXECUTE);
                        duoFluidHandler.getDuoFluidTank().drain(fluidAmount, IFluidHandler.FluidAction.EXECUTE);
                        pPlayer.setItemInHand(pHand, handler.getContainer());
                    } else if (!pState.getValue(MODE).equals(2)) {
                        int drainAmount = fluidHandler.getFluidTank().getSpace();
                        FluidStack fluidStack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                        if (fluidHandler.getFluidTank().isFluidValid(fluidStack)) {
                            if (fluidHandler.getFluid().isFluidEqual(fluidStack) || fluidHandler.getFluid().isEmpty()) {
                                fluidStack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                                fluidHandler.getFluidTank().fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                pPlayer.setItemInHand(pHand, handler.getContainer());
                            }
                        }
                    }
                }
            }
        });
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new FusionFluidPortTile(pPos, pState);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(AbstractPort.PUSHING)) {
            return createTickerHelper(pBlockEntityType, POMtiles.FUSION_FLUID_PORT.get(),
                    pLevel.isClientSide ? FusionFluidPortTile::clientTick : FusionFluidPortTile::serverTick);
        }
        return null;
    }
}
