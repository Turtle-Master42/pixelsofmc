package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.List;

public class ExplosiveLiquidBlock extends POMLiquidBlock {

    private final float radius;

    public ExplosiveLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
        this.radius = 3f;
    }
    public ExplosiveLiquidBlock(FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties, effects);
        this.radius = 3f;
    }

    public ExplosiveLiquidBlock(float radius, FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
        this.radius = radius;
    }

    public ExplosiveLiquidBlock(float radius, FlowingFluid pFluid, Properties pProperties, List<MobEffectInstance> effects) {
        super(pFluid, pProperties, effects);
        this.radius = radius;
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (!level.isClientSide) {
            if (state.getValue(LiquidBlock.LEVEL) == 0) {
                level.explode(null, pos.getX(), pos.getY(), pos.getZ(), radius, Level.ExplosionInteraction.TNT);
            } else {
                level.explode(null, pos.getX(), pos.getY(), pos.getZ(), radius / 2, Level.ExplosionInteraction.TNT);
            }
        }
    }
}
