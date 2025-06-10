package net.turtlemaster42.pixelsofmc.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class Sparkle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected Sparkle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet) {
        super(level, xCoord, yCoord, zCoord, 0, 0, 0);

        this.friction = 0.8F;
        this.lifetime = 6;
        this.setSpriteFromAge(spriteSet);
        this.sprites = spriteSet;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(sprites);
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float pPartialTick) {
        return 200;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new Sparkle(level, x, y, z, this.sprites);
        }
    }
}
