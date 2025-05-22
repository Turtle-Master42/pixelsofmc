package net.turtlemaster42.pixelsofmc.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.particle.options.FluidBubbleParticleOptions;

public class FluidBubblePopParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public FluidBubblePopParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet sprite, Fluid fluid) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.sprites = sprite;
        this.lifetime = 5;
        this.gravity = -0.008F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        int color = IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1));
        this.rCol *= (float) (color >> 16 & 255) / 255.0F;
        this.gCol *= (float) (color >> 8 & 255) / 255.0F;
        this.bCol *= (float) (color & 255) / 255.0F;
        this.setSpriteFromAge(sprite);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(sprites);
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<FluidBubbleParticleOptions> {

        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(FluidBubbleParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new FluidBubblePopParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites, pType.getFluid());
        }
    }
}
