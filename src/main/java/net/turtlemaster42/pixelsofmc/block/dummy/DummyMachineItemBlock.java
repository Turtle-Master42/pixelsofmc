package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineItemBlockTile;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;

public class DummyMachineItemBlock extends AbstractDummyMachineBlock {

    public DummyMachineItemBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).strength(2f, 3600000f).noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Deprecated
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
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
}
