package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ReinforcedGlass extends AbstractFusionCasing {
    public ReinforcedGlass(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return true;
    }

//    @Override
//    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
//        if (getMainBlockPos(level, pos) != null)
//            return 15;
//        return 0;
//    }
}
