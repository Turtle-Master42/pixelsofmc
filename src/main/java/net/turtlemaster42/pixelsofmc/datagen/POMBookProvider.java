package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.util.Util;

public class POMBookProvider extends BookProvider<POMBookBuilder> {
    public POMBookProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, POMBookBuilder::new, existingFileHelper);
    }

    @Override
    void generatePages() {
        page("book_1", "test", "Testing if this works")
                .pageParent("root.json")
                .text("empty.txt")
                .pageButton("test.json", "Crushing").page(0).item(POMitems.QUARTZ_DUST.get()).pos(50, 25).end()
                .image(Util.resourceLocation("textures/block/advanced_casing_2.png")).pos(50, 35).end()
                .entity(POMentities.RIVER_SHELL.get());
        page("big_book", "anotherone", "Still TESTING");
    }
}
