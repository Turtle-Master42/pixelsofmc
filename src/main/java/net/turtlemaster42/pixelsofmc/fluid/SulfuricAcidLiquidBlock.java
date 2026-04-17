package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SulfuricAcidLiquidBlock extends AcidLiquidBlock {

    public SulfuricAcidLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(2, pFluid, pProperties);
    }

    public SulfuricAcidLiquidBlock(FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(2, pFluid, pProperties, effects);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity) {
            if (livingEntity.isEyeInFluidType(POMFluidType.SULFURIC_ACID_FLUID_TYPE.get())) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600));
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
