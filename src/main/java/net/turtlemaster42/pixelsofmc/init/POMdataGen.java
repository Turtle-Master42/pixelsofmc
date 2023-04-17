package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.datagen.*;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMdataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        PixelsOfMc.LOGGER.info("Generating!!!");

        generator.addProvider(true, new POMblockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new POMitemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new POMitemTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, POMlootTableProvider.create(packOutput));
        generator.addProvider(true, new POMrecipeProvider(packOutput));
    }
}
