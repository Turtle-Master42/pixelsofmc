package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

public class HotLiquidBlock extends LiquidBlock {
    public HotLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        pEntity.setRemainingFireTicks(20);
        pEntity.hurt(pEntity.damageSources().onFire(), 1.0F);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
