package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.block.tile.EnergyPortTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInfiniteEnergyHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyPortBlock extends AbstractPort {
    public EnergyPortBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new EnergyPortTile(pPos, pState);
    }

    @Override
    public boolean hasComparatorOutput(BlockState pState) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof EnergyPortTile portTile) {
            BlockEntity mainTile = pLevel.getBlockEntity(portTile.getMainPos());
            if (mainTile == null || !portTile.isMainPosValid()) {return 0;}

            // Infinite Energy
            if (mainTile instanceof IInfiniteEnergyHandlingTile infiniteStorage) {
                InfiniteNumber energy = infiniteStorage.getEnergyStorage().getInfiniteEnergy();
                InfiniteNumber maxEnergy = infiniteStorage.getEnergyStorage().getInfiniteCapacity();
                if (energy.equals(0)) {return 0;} // empty
                if (energy.equals(maxEnergy)) {return 15;} // full
                return 1 + Math.round(energy.getCrudePercentage(maxEnergy, energy) * 0.13f); // partial
            }
            // Normal Energy
            else if (mainTile instanceof IEnergyHandlingTile energyStorage) {
                int energy = energyStorage.getEnergyStorage().getEnergyStored();
                int maxEnergy = energyStorage.getEnergyStorage().getMaxEnergyStored();
                if (energy == 0) {return 0;} // empty
                if (energy == maxEnergy) {return 15;} // full
                return 1 + Math.round((((float) energy) / ((float) maxEnergy)) * 13f); // partial
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(PUSHING)) {
            return createTickerHelper(pBlockEntityType, POMtiles.ENERGY_PORT.get(),
                    pLevel.isClientSide ? EnergyPortTile::clientTick : EnergyPortTile::serverTick);
        }
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, POMtiles.ENERGY_PORT.get(), EnergyPortTile::idleTick);
    }
}
