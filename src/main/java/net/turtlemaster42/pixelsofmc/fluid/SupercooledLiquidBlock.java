package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class SupercooledLiquidBlock extends LiquidBlock {

    public SupercooledLiquidBlock(FlowingFluid pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextDouble() < 0.1d)
           pLevel.addParticle(ParticleTypes.SNOWFLAKE, pPos.getX() + pRandom.nextDouble(), pPos.getY() + pRandom.nextDouble(), pPos.getZ() + pRandom.nextDouble(), 0f, 0f, 0f);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        pEntity.extinguishFire();
        pEntity.setTicksFrozen((int) (pEntity.getTicksFrozen() * 1.3f));
        pEntity.setIsInPowderSnow(true);
        if (pEntity.isFullyFrozen())
            pEntity.hurt(new DamageSource("pixelsofmc.supercooled_liquid").bypassArmor().setNoAggro(), 4);
    }

    @Deprecated // FORGE: Use FluidInteractionRegistry#canInteract instead
    private boolean shouldSpreadLiquid(Level pLevel, BlockPos pPos, BlockState pState) {

            for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockpos = pPos.relative(direction.getOpposite());
                if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                    Block block = pLevel.getFluidState(blockpos).isSource() ? Blocks.PACKED_ICE : Blocks.ICE;
                    pLevel.setBlockAndUpdate(blockpos, block.defaultBlockState());
                    return false;
                }
            }
        return true;
    }
}
