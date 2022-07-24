package net.turtlemaster42.pixelsofmc.init;

import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.datagen.POMblockModelProvider;
import net.turtlemaster42.pixelsofmc.datagen.POMchestLootTables;
import net.turtlemaster42.pixelsofmc.datagen.POMitemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMdataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new POMitemModelProvider(generator, existingFileHelper));
        generator.addProvider(new POMblockModelProvider(generator, existingFileHelper));
        generator.addProvider(new POMrecipeProvider(generator));
    }
}
