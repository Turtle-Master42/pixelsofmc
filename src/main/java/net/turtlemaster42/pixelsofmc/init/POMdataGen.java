package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.turtlemaster42.pixelsofmc.datagen.*;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMdataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider( new POMrecipeProvider(generator));
            generator.addProvider( new POMlootTableProvider(generator));
            generator.addProvider( new POMblockModelProvider(generator, existingFileHelper));
            generator.addProvider( new POMitemModelProvider(generator, existingFileHelper));
    }
}
