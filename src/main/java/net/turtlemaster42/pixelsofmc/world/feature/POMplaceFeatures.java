package net.turtlemaster42.pixelsofmc.world.feature;


import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class POMplaceFeatures {
    public static final Holder<PlacedFeature> END_TITANIUM_ORE_PLACED = PlacementUtils.register("titanium_ore_placed",
            POMfeatures.END_TITANIUM_ORE, OrePlacement.commonOrePlacement(2, // VeinsPerChunk
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(50))));
}
