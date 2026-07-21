package net.turtlemaster42.pixelsofmc.tile.dummy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;

import javax.annotation.Nonnull;

public class DummyMachineFluidBlockTile extends AbstractDummyMachineBlockTile {

    public DummyMachineFluidBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.EXTENDER_FLUID_BLOCK.get(), pWorldPosition, pBlockState);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            BlockPos posTarget = BigMachineBlockUtil.getMainPos(level, worldPosition);
            BlockEntity tile = level.getBlockEntity(posTarget);

            if (tile != null) {
                return tile.getCapability(cap, side);
            }
        }
        return super.getCapability(cap, side);
    }
}

