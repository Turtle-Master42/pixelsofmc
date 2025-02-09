package net.turtlemaster42.pixelsofmc.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;
import net.turtlemaster42.pixelsofmc.util.Util;

public class ColoredBlockParticle extends TextureSheetParticle {
    private final BlockPos pos;
    private final float uo;
    private final float vo;

    public ColoredBlockParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, BlockState pState) {
        this(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pState, Util.blockPos(pX, pY, pZ));
    }

    public ColoredBlockParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, BlockState pState, BlockPos pPos) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.pos = pPos;
        this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(pState));
        int light = pState.getLightEmission() / 8;
        this.gravity = 1.0F;
        this.rCol = 0.75F + light;
        this.gCol = 0.75F + light;
        this.bCol = 0.75F + light;
        if (pState.getBlock() instanceof LiquidBlock liquid) {
            int j = IClientFluidTypeExtensions.of(liquid.getFluid().getSource()).getTintColor(new FluidStack(liquid.getFluid().getSource(), 1));
            this.setAlpha((float)(j >> 24 & 255) / 255.0F);
            this.rCol *= (float)(j >> 16 & 255) / 255.0F;
            this.gCol *= (float)(j >> 8 & 255) / 255.0F;
            this.bCol *= (float)(j & 255) / 255.0F;
            if (liquid.getFluid().getSource().getFluidType().isLighterThanAir()) {
                this.gravity = -0.4f;
            }

        } else if (!pState.is(Blocks.GRASS_BLOCK)) {
            int i = Minecraft.getInstance().getBlockColors().getColor(pState, pLevel, pPos, 0);
            this.rCol *= (float)(i >> 16 & 255) / 255.0F;
            this.gCol *= (float)(i >> 8 & 255) / 255.0F;
            this.bCol *= (float)(i & 255) / 255.0F;
        }

        this.quadSize /= 2.0F;
        this.uo = this.random.nextFloat() * 3.0F;
        this.vo = this.random.nextFloat() * 3.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    protected float getU0() {
        return this.sprite.getU((this.uo + 1.0F) / 4.0F * 16.0F);
    }

    protected float getU1() {
        return this.sprite.getU(this.uo / 4.0F * 16.0F);
    }

    protected float getV0() {
        return this.sprite.getV(this.vo / 4.0F * 16.0F);
    }

    protected float getV1() {
        return this.sprite.getV((this.vo + 1.0F) / 4.0F * 16.0F);
    }


    public int getLightColor(float pPartialTick) {
        int i = super.getLightColor(pPartialTick);
        return i == 0 && this.level.hasChunkAt(this.pos) ? LevelRenderer.getLightColor(this.level, this.pos) : i;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<ColoredBlockParticleOptions> {
        public Particle createParticle(ColoredBlockParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            BlockState blockState = pType.getState();
            return !blockState.isAir() && !blockState.is(Blocks.MOVING_PISTON) ? (new ColoredBlockParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, blockState)).updateSprite(blockState, pType.getPos()) : null;
        }
    }

    public Particle updateSprite(BlockState state, BlockPos pos) { //FORGE: we cannot assume that the x y z of the particles match the block pos of the block.
        if (pos != null) // There are cases where we are not able to obtain the correct source pos, and need to fallback to the non-model data version
            this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(state, level, pos));
        return this;
    }
}
