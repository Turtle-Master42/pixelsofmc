package net.turtlemaster42.pixelsofmc.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.world.gen.TitaniumOreGen;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        TitaniumOreGen.generateOres(event);
    }
}
