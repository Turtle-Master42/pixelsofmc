package net.turtlemaster42.pixelsofmc.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

import java.util.List;

public class POMfeatures {
    public static final List<OreConfiguration.TargetBlockState> END_TITANIUM_ORES = List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), POMblocks.ENDSTONE_TITANIUM_ORE.get().defaultBlockState()));

//    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> END_TITANIUM_ORE = FeatureUtils.register("end_titanium_ore",
//            Feature.ORE, new OreConfiguration(END_TITANIUM_ORES, 20));

}
