package net.turtlemaster42.pixelsofmc.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class CrossParticle extends TextureSheetParticle {
    static final RandomSource RANDOM = RandomSource.create();
    private final SpriteSet sprites;

    CrossParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.quadSize *= 0.75F;
        this.hasPhysics = false;
        this.setSpriteFromAge(pSprites);
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float pPartialTick) {
        float t = ((float) this.age + pPartialTick) / (float) this.lifetime;
        t = Mth.clamp(t, 0.0F, 1.0F);
        int lightColor = super.getLightColor(pPartialTick);
        int j = lightColor & 255;
        int k = lightColor >> 16 & 255;
        j += (int) (t * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class RedCrossProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public RedCrossProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CrossParticle particle = new CrossParticle(pLevel, pX, pY, pZ, 0.5d - RANDOM.nextDouble(), pYSpeed, 0.5d - RANDOM.nextDouble(), this.sprite);
            particle.setColor(0.9f, 0.05f, 0.18f);

            particle.yd *= 0.08d;
            if (pXSpeed == 0 && pZSpeed == 0) {
                particle.xd *= 0.08f;
                particle.zd *= 0.08f;
            }

            particle.setLifetime((int) (30d / (pLevel.random.nextDouble() * 0.6d + 0.4d)));
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class GreenCrossProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public GreenCrossProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            CrossParticle particle = new CrossParticle(pLevel, pX, pY, pZ, 0.5d - RANDOM.nextDouble(), pYSpeed, 0.5d - RANDOM.nextDouble(), this.sprite);
            particle.setColor(0.25f, 0.95f, 0.55f);

            particle.yd *= 0.08d;
            if (pXSpeed == 0 && pZSpeed == 0) {
                particle.xd *= 0.08f;
                particle.zd *= 0.08f;
            }

            particle.setLifetime((int) (6d / (pLevel.random.nextDouble() * 0.8d + 0.2d)));
            return particle;
        }
    }
}