package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.block.tile.ItemInputFusionTile;
import org.jetbrains.annotations.Nullable;

public class ItemInputFusionBlock extends AbstractFusionCasing {
    public ItemInputFusionBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemInputFusionTile(pPos, pState);
    }
}
