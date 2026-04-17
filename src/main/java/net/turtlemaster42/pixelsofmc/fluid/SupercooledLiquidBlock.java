package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.turtlemaster42.pixelsofmc.init.POMdamage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SupercooledLiquidBlock extends POMLiquidBlock {

    public SupercooledLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    public SupercooledLiquidBlock(FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties, effects);
    }


    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextDouble() < 0.4d && pLevel.getBlockState(pPos.above()).is(Blocks.AIR))
           pLevel.addParticle(ParticleTypes.SNOWFLAKE,
                   pPos.getX() + pRandom.nextDouble(),
                   pPos.getY() + ((8 - pState.getValue(LiquidBlock.LEVEL)) * 0.125f),
                   pPos.getZ() + pRandom.nextDouble(),
                   0f, 0f, 0f);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        pEntity.extinguishFire();
        pEntity.setTicksFrozen((int) (pEntity.getTicksFrozen() * 1.3f));
        pEntity.setIsInPowderSnow(true);
        if (pEntity.isFullyFrozen())
            pEntity.hurt(POMdamage.super_cooled(pLevel), 4);
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
