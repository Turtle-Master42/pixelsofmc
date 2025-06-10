package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.init.POMparticles;

public class SparkelingBlock extends Block {
    private final int amount;
    private final float chance;

    public SparkelingBlock(int amount, float chance, Properties pProperties) {
        super(pProperties);
        this.amount = amount;
        this.chance = chance;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        for (Direction dir : Direction.values()) {
            if (RandomSource.create().nextFloat() <= chance)
                ParticleUtils.spawnParticlesOnBlockFace(pLevel, pPos, POMparticles.SPARKLE.get(), UniformInt.of(Math.max(amount, 0), amount + 2), dir, () -> new Vec3(0, 0, 0), 0.55D);
        }
    }
}
