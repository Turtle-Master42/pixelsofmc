package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.block.tile.EnergyInputFusionTile;
import net.turtlemaster42.pixelsofmc.block.tile.ItemOutputFusionTile;
import org.jetbrains.annotations.Nullable;

public class ItemOutputFusionBlock extends AbstractFusionCasing {
    public ItemOutputFusionBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemOutputFusionTile(pPos, pState);
    }
}
