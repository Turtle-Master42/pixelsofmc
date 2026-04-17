package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.turtlemaster42.pixelsofmc.init.POMdamage;
import net.turtlemaster42.pixelsofmc.init.POMparticles;
import net.turtlemaster42.pixelsofmc.particle.options.FluidBubbleParticleOptions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AcidLiquidBlock extends POMLiquidBlock {
    private final float damage;

    public AcidLiquidBlock(float damage, FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
        this.damage = damage;
    }

    public AcidLiquidBlock(float damage, FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties, effects);
        this.damage = damage;
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextDouble() < 0.333d && pLevel.getBlockState(pPos.above()).is(Blocks.AIR)) {
            pLevel.addParticle(new FluidBubbleParticleOptions(POMparticles.FLUID_BUBBLE_POP.get(), this.getFluid().defaultFluidState()),
                    pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + ((8 - pState.getValue(LiquidBlock.LEVEL)) * 0.125f),
                    pPos.getZ() + pRandom.nextDouble(),
                    0f, 0f, 0f);
        }
        if (pRandom.nextDouble() < 0.2d && pLevel.getFluidState(pPos).is(getFluid())) {
            pLevel.addParticle(new FluidBubbleParticleOptions(POMparticles.FLUID_BUBBLE.get(), this.getFluid().defaultFluidState()),
                    pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + pRandom.nextDouble(),
                    pPos.getZ() + pRandom.nextDouble(),
                    0f, 0f, 0f);
        }
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        pEntity.hurt(POMdamage.acid(pLevel), damage);
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }
}
