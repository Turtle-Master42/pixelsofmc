package net.turtlemaster42.pixelsofmc.world.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LargeVeinSettings {
    public final BlockStateProvider ore;
    public final BlockStateProvider raw_ore_block;
    public final BlockStateProvider filler;
    public final int minY;
    public final int maxY;
    public final TagKey<Block> can_replace;
    public static final Codec<LargeVeinSettings> CODEC = RecordCodecBuilder.create((settingsInstance) -> settingsInstance.group(BlockStateProvider.CODEC.fieldOf("ore").forGetter((settings) -> settings.ore), BlockStateProvider.CODEC.fieldOf("raw_ore_block").forGetter((settings) -> settings.raw_ore_block), BlockStateProvider.CODEC.fieldOf("filler").forGetter((settings) -> settings.filler), Codec.INT.fieldOf("min_y").orElse(0).forGetter((settings) -> settings.minY), Codec.INT.fieldOf("max_y").orElse(70).forGetter((settings) -> settings.maxY), TagKey.hashedCodec(Registries.BLOCK).fieldOf("can_replace").forGetter((settings) -> settings.can_replace)).apply(settingsInstance, LargeVeinSettings::new));

    public LargeVeinSettings(BlockStateProvider ore, BlockStateProvider rawOreBlock, BlockStateProvider filler, int minY, int maxY, TagKey<Block> canReplace) {
        this.ore = ore;
        this.raw_ore_block = rawOreBlock;
        this.filler = filler;
        this.minY = minY;
        this.maxY = maxY;
        this.can_replace = canReplace;
    }
}
