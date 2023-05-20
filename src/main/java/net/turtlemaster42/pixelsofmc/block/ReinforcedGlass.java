package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedGlass extends AbstractFusionCasing {
    public ReinforcedGlass(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

//    @Override
//    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
//        if (getMainBlockPos(level, pos) != null)
//            return 15;
//        return 0;
//    }
}
