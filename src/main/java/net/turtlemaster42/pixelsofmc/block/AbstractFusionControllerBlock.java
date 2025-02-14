package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.block.IFusionControllerBlock;

import java.util.Map;

public abstract class AbstractFusionControllerBlock extends AbstractMultiControllerBlock implements IFusionControllerBlock {
    protected AbstractFusionControllerBlock(Properties pProperties) {
        super(pProperties);
    }

    // --- Multi Block --- //

    public Map<Block, Integer> validateMultiBlock(Level level, BlockPos controllerPos) {
        Map<Block, Integer> blocks = super.validateMultiBlock(level, controllerPos);
        if (blocks.isEmpty()) {
            return blocks;
        }

        if (blocks.containsKey(POMblocks.HEAT_SINK.get()) && level.getBlockEntity(controllerPos) instanceof SDSFusionControllerTile fusionControllerTile) {
            fusionControllerTile.setHeatSinkAmount(blocks.get(POMblocks.HEAT_SINK.get()));
        }
        level.setBlock(rotatedOffsetBlock(level.getBlockState(controllerPos).getValue(FACING), fusionStarPos(), controllerPos), POMblocks.STAR.get().defaultBlockState().setValue(StarBlock.STAR_STAGE, fusionStarLevel()), 2);

        return blocks;
    }

    @Override
    public void invalidateMultiBlock(Level level, BlockPos controllerPos) {
        super.invalidateMultiBlock(level, controllerPos);
        level.destroyBlock(rotatedOffsetBlock(level.getBlockState(controllerPos).getValue(FACING), fusionStarPos(), controllerPos), false);
    }

    public BlockPos fusionStarPos() {return new BlockPos(0, 0, 0);}
    public int fusionStarLevel() {return 1;}
}
