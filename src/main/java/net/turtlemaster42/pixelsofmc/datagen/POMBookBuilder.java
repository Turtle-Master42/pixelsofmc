package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class POMBookBuilder extends BookBuilder<POMBookBuilder> {
    public POMBookBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }
}
