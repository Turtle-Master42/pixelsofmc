package net.turtlemaster42.pixelsofmc.world.gen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.turtlemaster42.pixelsofmc.world.feature.POMplaceFeatures;

import java.util.List;

public class TitaniumOreGen {
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        if(event.getCategory() == Biome.BiomeCategory.THEEND) {
            base.add(POMplaceFeatures.END_TITANIUM_ORE_PLACED);
        }
    }
}
