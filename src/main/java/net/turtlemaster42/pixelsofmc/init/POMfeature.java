package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.world.feature.CustomGeodeFeature;
import net.turtlemaster42.pixelsofmc.world.feature.LargeVeinFeature;
import net.turtlemaster42.pixelsofmc.world.feature.configurations.LargeVeinConfiguration;

public class POMfeature {

    public static DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, PixelsOfMc.MOD_ID);

	public static RegistryObject<CustomGeodeFeature> CUSTOM_GEODE =	FEATURES.register("custom_geode", () -> new CustomGeodeFeature(GeodeConfiguration.CODEC));
    public static RegistryObject<LargeVeinFeature> LARGE_VEIN =	FEATURES.register("large_vein", () -> new LargeVeinFeature(LargeVeinConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
