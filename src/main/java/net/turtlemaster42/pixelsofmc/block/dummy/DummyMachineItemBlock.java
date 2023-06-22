package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineItemBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMtiles;

import javax.annotation.Nullable;

public class DummyMachineItemBlock extends AbstractDummyMachineBlock {

    public DummyMachineItemBlock() {
        super(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).strength(2f, 3600000f).noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DummyMachineItemBlockTile(pos, state);
    }

    @Deprecated
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide())
            return createTickerHelper(pBlockEntityType, POMtiles.MACHINE_ITEM_BLOCK.get(), DummyMachineItemBlockTile::serverTick);
        return null;
    }
}
