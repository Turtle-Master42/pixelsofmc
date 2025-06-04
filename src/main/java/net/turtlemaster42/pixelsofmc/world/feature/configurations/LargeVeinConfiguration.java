package net.turtlemaster42.pixelsofmc.world.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LargeVeinConfiguration implements FeatureConfiguration {
    public static final Codec<Double> CHANCE_RANGE = Codec.doubleRange(0.0D, 1.0D);
    public static final Codec<net.turtlemaster42.pixelsofmc.world.feature.configurations.LargeVeinConfiguration> CODEC = RecordCodecBuilder.create(
            (configurationInstance) -> configurationInstance.group(LargeVeinSettings.CODEC.fieldOf("blocks")
                    .forGetter((configuration) -> configuration.vein_block_settings), Codec.FLOAT.fieldOf("veininess_threshold").orElse(0.4f)
                    .forGetter((configuration) -> configuration.veininess_threshold), Codec.INT.fieldOf("edge_roundoff_begin").orElse(20)
                    .forGetter((configuration) -> configuration.edge_roundoff_begin), Codec.DOUBLE.fieldOf("max_edge_roundoff").orElse(0.2d)
                    .forGetter((configuration) -> configuration.max_edge_roundoff), Codec.FLOAT.fieldOf("vein_solidness").orElse(0.9f)
                    .forGetter((configuration) -> configuration.vein_solidness), Codec.FLOAT.fieldOf("min_richness").orElse(0.2f)
                    .forGetter((configuration) -> configuration.min_richness), Codec.FLOAT.fieldOf("max_richness").orElse(0.5f)
                    .forGetter((configuration) -> configuration.max_richness), Codec.FLOAT.fieldOf("max_richness_threshold").orElse(0.7f)
                    .forGetter((configuration) -> configuration.max_richness_threshold), Codec.FLOAT.fieldOf("raw_ore_block_chance").orElse(0.05f)
                    .forGetter((configuration) -> configuration.raw_ore_block_chance), Codec.INT.fieldOf("min_gen_offset").orElse(-16)
                    .forGetter((configuration) -> configuration.minGenOffset), Codec.INT.fieldOf("max_gen_offset").orElse(16)
                    .forGetter((configuration) -> configuration.maxGenOffset), CHANCE_RANGE.fieldOf("noise_multiplier").orElse(1D)
                    .forGetter((configuration) -> configuration.noiseMultiplier))
                    .apply(configurationInstance, LargeVeinConfiguration::new));
    public final LargeVeinSettings vein_block_settings;
    public final float veininess_threshold ;
    public final int edge_roundoff_begin;
    public final double max_edge_roundoff;
    public final float vein_solidness;
    public final float min_richness;
    public final float max_richness;
    public final float max_richness_threshold;
    public final float raw_ore_block_chance;
    public final int minGenOffset;
    public final int maxGenOffset;
    public final double noiseMultiplier;

    public LargeVeinConfiguration(LargeVeinSettings veinBlockSettings, float veininessThreshold, int edgeRoundoffBegin, double maxEdgeRoundoff, float veinSolidness, float minRichness, float maxRichness, float maxRichnessThreshold, float rawOreBlockChance, int minGenOffset, int maxGenOffset, double noiseMultiplier) {
        this.vein_block_settings = veinBlockSettings;
        this.veininess_threshold = veininessThreshold;
        this.edge_roundoff_begin = edgeRoundoffBegin;
        this.max_edge_roundoff = maxEdgeRoundoff;
        this.vein_solidness = veinSolidness;
        this.min_richness = minRichness;
        this.max_richness = maxRichness;
        this.max_richness_threshold = maxRichnessThreshold;
        this.raw_ore_block_chance = rawOreBlockChance;
        this.minGenOffset = minGenOffset;
        this.maxGenOffset = maxGenOffset;
        this.noiseMultiplier = noiseMultiplier;
    }
}
