package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotLiquidBlock extends POMLiquidBlock {
    public HotLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    public HotLiquidBlock(FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties, effects);
    }


    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        pEntity.setRemainingFireTicks(20);
        pEntity.hurt(pEntity.damageSources().onFire(), 1.0F);
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }
}
