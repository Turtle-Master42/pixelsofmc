package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.List;

public class POMLiquidBlock extends LiquidBlock {
    private final List<MobEffectInstance> effects;

    public POMLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
        this.effects = List.of();
    }

    public POMLiquidBlock(FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties);
        this.effects = effects;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        for (MobEffectInstance instance : effects) {
            if (pEntity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(instance.getEffect(), instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
