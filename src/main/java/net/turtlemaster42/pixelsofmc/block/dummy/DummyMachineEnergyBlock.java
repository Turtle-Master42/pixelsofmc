package net.turtlemaster42.pixelsofmc.block.dummy;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.DummyMachineEnergyBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DummyMachineEnergyBlock extends AbstractDummyMachineBlock {
    public DummyMachineEnergyBlock() {
        super(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).dynamicShape().strength(2f, 3600000f).noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DummyMachineEnergyBlockTile(pos, state);
    }

    @Deprecated
    public boolean triggerEvent(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isClientSide()) {
            ItemStack itemstack = Minecraft.getInstance().player.getMainHandItem();
            if (itemstack.is(POMitems.DEBUGIUM_INGOT.get())) {
                pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK_MARKER, Blocks.LIME_STAINED_GLASS.defaultBlockState()), (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide())
            return createTickerHelper(pBlockEntityType, POMtiles.MACHINE_ENERGY_BLOCK.get(), DummyMachineEnergyBlockTile::serverTick);
        return null;
    }
}
