package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedGlass extends AbstractFusionCasing {
    public ReinforcedGlass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}
