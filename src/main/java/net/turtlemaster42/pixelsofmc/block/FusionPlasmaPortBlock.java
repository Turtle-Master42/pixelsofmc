package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.tile.PlasmaPortTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FusionPlasmaPortBlock extends AbstractPort {
    public FusionPlasmaPortBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new PlasmaPortTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pState.getValue(AbstractPort.PUSHING)) {
            return createTickerHelper(pBlockEntityType, POMtiles.PLASMA_PORT.get(),
                    pLevel.isClientSide ? PlasmaPortTile::clientTick : PlasmaPortTile::serverTick);
        }
        return null;
    }
}
