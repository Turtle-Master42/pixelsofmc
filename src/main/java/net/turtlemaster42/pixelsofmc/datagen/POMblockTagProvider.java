package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class POMblockTagProvider extends BlockTagsProvider {

    public POMblockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(Tags.Blocks.GLASS).add(POMblocks.REINFORCED_GLASS.get());
    }
}
