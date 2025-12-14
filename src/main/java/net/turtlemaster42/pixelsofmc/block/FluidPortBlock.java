package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.tile.FluidPortTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IMultiFluidHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class FluidPortBlock extends AbstractPort {
    public FluidPortBlock(Properties pProperties) {
        super(pProperties);
    }


    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack handItem = pPlayer.getItemInHand(pHand);

        if (pHand.equals(InteractionHand.MAIN_HAND) && handItem.isEmpty()) {
            if (pLevel.getBlockEntity(pPos) instanceof FluidPortTile portTile) {
                if (!portTile.isMainPosValid() && !portTile.getCurrentTank().equals("null")) {
                    portTile.setCurrentTank("null");
                }

                if (pPlayer.isCrouching()) {
                    if (pLevel.isClientSide()) {
                        pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block.fluid_port." + portTile.getCurrentTank()), true);
                        Vector3f colorVec = Util.formatCodeVecColor(Component.translatable("message.pixelsofmc.block.fluid_port." + portTile.getCurrentTank() + ".color").getString());
                        ParticleUtils.spawnParticlesOnBlockFace(pLevel, pHit.getBlockPos(), new DustParticleOptions(colorVec, 0.75f), UniformInt.of(9, 12), pHit.getDirection(), () -> Vec3.ZERO, 0.55D);
                    }
                    return InteractionResult.SUCCESS;
                }

                BlockEntity mainTile = pLevel.getBlockEntity(portTile.getMainPos());
                if (mainTile instanceof IMultiFluidHandlingTile portLogicTile) {
                    String[] tankNames = portLogicTile.getFluidTankNames();
                    for (int i = 0; i < tankNames.length; i++) {
                        if (tankNames[i].equals(portTile.getCurrentTank()) || portTile.getCurrentTank().equals("null")) {
                            int index = i + 1;
                            if (index == tankNames.length) {
                                index = 0;
                            }
                            portTile.setCurrentTank(tankNames[index]);
                            portTile.fluidTank.setFluid(portLogicTile.getFluidTank(tankNames[index]).getFluidInTank(0));
                            if (pLevel.isClientSide()) {
                                pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block.fluid_port." + tankNames[index]), true);
                                Vector3f colorVec = Util.formatCodeVecColor(Component.translatable("message.pixelsofmc.block.fluid_port." + tankNames[index] + ".color").getString());
                                ParticleUtils.spawnParticlesOnBlockFace(pLevel, pHit.getBlockPos(), new DustParticleOptions(colorVec, 0.75f), UniformInt.of(9, 12), pHit.getDirection(), () -> Vec3.ZERO, 0.55D);
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

            }
        }

        if (handItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            handItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fluidItem -> {
                if (pLevel.getBlockEntity(pPos) instanceof FluidPortTile tile) {
                    BlockEntity mainTile = pLevel.getBlockEntity(tile.getMainPos());

                    if (mainTile instanceof IMultiFluidHandlingTile portLogicTile) { // MULTIPLE TANKS
                        FluidTank fluidTank = portLogicTile.getFluidTank(tile.getCurrentTank());
                        if (fluidTank == null) {return;}
                        if (!pState.getValue(MODE).equals(1) && fluidItem.fill(fluidTank.getFluid(), IFluidHandler.FluidAction.SIMULATE) > 0) { //not insert only && item excepts fluid
                            fluidTank.drain(fluidItem.fill(fluidTank.getFluid(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            pPlayer.setItemInHand(pHand, fluidItem.getContainer());
                        } else if (!pState.getValue(MODE).equals(2)) { // not extract only
                            int drainAmount = fluidTank.getSpace();
                            FluidStack fluidStack = fluidItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                            if (fluidTank.isFluidValid(fluidStack) && (fluidTank.getFluid().isFluidEqual(fluidStack) || fluidTank.getFluid().isEmpty())) {
                                fluidStack = fluidItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                                fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                pPlayer.setItemInHand(pHand, fluidItem.getContainer());
                            }
                        }
                    } else if (mainTile instanceof IFluidHandlingTile fluidHandler) { // ONE TANK
                        if (!pState.getValue(MODE).equals(1) && fluidItem.fill(fluidHandler.getFluid(), IFluidHandler.FluidAction.SIMULATE) > 0) { //not insert only && item excepts fluid
                            fluidHandler.getFluidTank().drain(fluidItem.fill(fluidHandler.getFluid(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            pPlayer.setItemInHand(pHand, fluidItem.getContainer());
                        } else if (!pState.getValue(MODE).equals(2)) { // not extract only
                            int drainAmount = fluidHandler.getFluidTank().getSpace();
                            FluidStack fluidStack = fluidItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                            if (fluidHandler.getFluidTank().isFluidValid(fluidStack) && (fluidHandler.getFluid().isFluidEqual(fluidStack) || fluidHandler.getFluid().isEmpty())) {
                                fluidStack = fluidItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                                fluidHandler.getFluidTank().fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                pPlayer.setItemInHand(pHand, fluidItem.getContainer());
                            }
                        }
                    }
                }
            });
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean hasComparatorOutput(BlockState pState) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof FluidPortTile portTile) {
            BlockEntity mainTile = pLevel.getBlockEntity(portTile.getMainPos());
            if (mainTile == null || !portTile.isMainPosValid()) {return 0;}

            // multi tank
            if (mainTile instanceof IMultiFluidHandlingTile portLogicTile && !portTile.getCurrentTank().equals("null")) {
                int fluid = portLogicTile.getFluidTank(portTile.getCurrentTank()).getFluidAmount();
                int maxFluid = portLogicTile.getFluidTank(portTile.getCurrentTank()).getCapacity();
                if (fluid == 0) {return 0;} // empty
                if (fluid == maxFluid) {return 15;} // full
                return 1 + Math.round((((float) fluid) / ((float) maxFluid)) * 13f); // partial
            // single tank
            } else if (mainTile instanceof IFluidHandlingTile fluidHandler) { // ONE TANK
                int fluid = fluidHandler.getFluidTank().getFluidAmount();
                int maxFluid = fluidHandler.getFluidTank().getCapacity();
                if (fluid == 0) {return 0;} // empty
                if (fluid == maxFluid) {return 15;} // full
                return 1 + Math.round((((float) fluid) / ((float) maxFluid)) * 13f); // partial
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new FluidPortTile(pPos, pState);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(PUSHING)) {
            return createTickerHelper(pBlockEntityType, POMtiles.FLUID_PORT.get(),
                    pLevel.isClientSide ? FluidPortTile::clientTick : FluidPortTile::serverTick);
        }
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, POMtiles.FLUID_PORT.get(), FluidPortTile::idleTick);
    }
}
