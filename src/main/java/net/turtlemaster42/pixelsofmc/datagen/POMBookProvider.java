package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMentities;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;

public class POMBookProvider extends BookProvider<POMBookBuilder> {
    public POMBookProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, PixelsOfMc.MOD_ID, POMBookBuilder::new, existingFileHelper);
    }

    @Override
    void generatePages() {
        for(Element e : Element.validValues()) {
            BookBuilder<POMBookBuilder> book1 = page("book_1", e.elementName(), "element.pixelsofmc." + e.elementName());

            book1.pageParent("elements.json")
                    .text("empty.txt")
                    .item(e.item()).pos(15, 27).scale(2.5f).end();

            for (int i = 0; i < e.getIsotopes().getAmount(); i++) {
                book1.item(e.isotope64(i)).pos(215, 18 + 24*i).scale(1.5f).end();
            }
        }




        page("book_1", "test", "Testing if this works")
                .pageParent("root.json")
                .text("empty.txt")
                .pageButton("test.json", "Crushing").page(0).item(POMitems.QUARTZ_DUST.get()).pos(50, 25).end()
                .image(Util.resourceLocation("textures/block/advanced_casing_2.png")).pos(50, 35).end()
                .entity(POMentities.RIVER_SHELL.get()).end()
                .item(POMitems.BLACK_DIAMOND.get()).pos(50, 200).scale(0.5f).end();
        page("big_book", "anotherone", "Still TESTING");
    }
}
