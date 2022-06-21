package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMcMod;
import net.turtlemaster42.pixelsofmc.datagen.PixelsOfMcModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PixelsOfMcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PixelsOfMcModDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new PixelsOfMcModItemModelProvider(generator, existingFileHelper));
    }
}
