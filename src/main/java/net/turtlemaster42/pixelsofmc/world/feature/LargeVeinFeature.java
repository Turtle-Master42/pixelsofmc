package net.turtlemaster42.pixelsofmc.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.world.feature.configurations.LargeVeinConfiguration;

import java.util.function.Predicate;

public class LargeVeinFeature extends Feature<LargeVeinConfiguration> {
    private static final float VEININESS_THRESHOLD = 0.4F;
    private static final int EDGE_ROUNDOFF_BEGIN = 20;
    private static final double MAX_EDGE_ROUNDOFF = 0.2D;
    private static final float VEIN_SOLIDNESS = 0.9F;
    private static final float MIN_RICHNESS = 0.2F;
    private static final float MAX_RICHNESS = 0.5F;
    private static final float MAX_RICHNESS_THRESHOLD = 0.7F;
    private static final float CHANCE_OF_RAW_ORE_BLOCK = 0.05F;

    public LargeVeinFeature(Codec<LargeVeinConfiguration> config) {super(config);}
//    boolean ? yes : no

    public boolean place(FeaturePlaceContext<LargeVeinConfiguration> context) {

        LargeVeinConfiguration config = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        int minGenOffset = config.minGenOffset;
        int maxGenOffset = config.maxGenOffset;
        Predicate<BlockState> predicate = (blockState) -> {
            return blockState.is(config.vein_block_settings.can_replace);
        };

        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(worldgenlevel.getSeed()));
        // vein placement, effects how big veins are
        NormalNoise oreVeinA = NormalNoise.create(worldgenrandom, -5, 1.0D);
        NormalNoise oreVeinB = NormalNoise.create(worldgenrandom, -5, 1.0D);
        // effects how the veins are rounded and how dense veins are
        NormalNoise oreVeininess = NormalNoise.create(worldgenrandom, -6, 1.0D);

        for (BlockPos blockPosInFeature : BlockPos.betweenClosed(blockpos.offset(minGenOffset, minGenOffset * 2, minGenOffset), blockpos.offset(maxGenOffset, maxGenOffset * 3, maxGenOffset))) {
            double VEIN_TOGGLE = blockPosInFeature.getY() > config.vein_block_settings.minY && blockPosInFeature.getY() < config.vein_block_settings.maxY ? Math.abs(oreVeininess.getValue(blockPosInFeature.getX(), blockPosInFeature.getY(), blockPosInFeature.getZ()) * 1.5f * config.noiseMultiplier) : 0;
            double VEIN_RIDGED = -0.23 + Math.max(
                    Math.abs(blockPosInFeature.getY() > config.vein_block_settings.minY - 5 && blockPosInFeature.getY() < config.vein_block_settings.maxY + 5 ? oreVeinA.getValue(blockPosInFeature.getX(), blockPosInFeature.getY(), blockPosInFeature.getZ()) * 4 * config.noiseMultiplier : 0),
                    Math.abs(blockPosInFeature.getY() > config.vein_block_settings.minY - 5 && blockPosInFeature.getY() < config.vein_block_settings.maxY + 5 ? oreVeinB.getValue(blockPosInFeature.getX(), blockPosInFeature.getY(), blockPosInFeature.getZ()) * 4 * config.noiseMultiplier : 0)
            );

            BlockState blockstate;
            int blockY = blockPosInFeature.getY();
            int dMax = config.vein_block_settings.maxY - blockY;
            int dMin = blockY - config.vein_block_settings.minY;
            int l = Math.min(dMax, dMin);
            double edgeRoundOff = Mth.clampedMap(l, 0, config.edge_roundoff_begin, -config.max_edge_roundoff, 0);
            if (VEIN_TOGGLE + edgeRoundOff < config.veininess_threshold || randomsource.nextFloat() > config.vein_solidness || VEIN_RIDGED >= 0) {
                continue;
            } else {
                double richness = Mth.clampedMap(VEIN_TOGGLE, config.veininess_threshold, config.max_richness_threshold, config.min_richness, config.max_richness);
                if (randomsource.nextFloat() < richness) {
                    blockstate = randomsource.nextFloat() < config.raw_ore_block_chance ? config.vein_block_settings.raw_ore_block.getState(randomsource, blockPosInFeature) : config.vein_block_settings.ore.getState(randomsource, blockPosInFeature);
                } else {
                    blockstate = config.vein_block_settings.filler.getState(randomsource, blockPosInFeature);
                }
                if (worldgenlevel.getBlockState(blockPosInFeature).is(config.vein_block_settings.can_replace)){
                    this.safeSetBlock(worldgenlevel, blockPosInFeature, blockstate, predicate);
                }
            }
        }
        return true;
    }

    protected enum VeinType {
        TITANIUM(POMblocks.ENDSTONE_TITANIUM_ORE.get().defaultBlockState(), POMblocks.RAW_TITANIUM_BLOCK.get().defaultBlockState(), Blocks.MAGENTA_STAINED_GLASS.defaultBlockState(), 0, 70);

        final BlockState ore;
        final BlockState rawOreBlock;
        final BlockState filler;
        final int minY;
        final int maxY;

        VeinType(BlockState pOre, BlockState pRawOreBlock, BlockState pFiller, int pMinY, int pMaxY) {
            this.ore = pOre;
            this.rawOreBlock = pRawOreBlock;
            this.filler = pFiller;
            this.minY = pMinY;
            this.maxY = pMaxY;
        }
    }
}
