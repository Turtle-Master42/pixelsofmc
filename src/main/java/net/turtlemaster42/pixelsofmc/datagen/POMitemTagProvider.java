package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.util.Element;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class POMitemTagProvider extends ItemTagsProvider {

    public POMitemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, new POMblockTagProvider(pOutput, pLookupProvider, existingFileHelper),
                PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        for(Element element : Element.values()) {
            if (element.equals(Element.DEBUGIUM)) continue;
            POMtags.MetalTags tags = POMtags.getTagsFor(element);
            if(element.shouldAddDust()) {
                tag(tags.dust).add(POMitems.Metals.DUSTS.get(element).get());
                tag(Tags.Items.DUSTS).addTag(tags.dust);
            }
            if (element.isMetal()) {
                tag(tags.metal).add(POMitems.Metals.ELEMENTS.get(element).get());
                tag(Tags.Items.INGOTS).addTag(tags.metal);
            }
            if (element.shouldAddNugget()) {
                tag(tags.nugget).add(POMitems.Metals.NUGGETS.get(element).get());
                tag(Tags.Items.NUGGETS).addTag(tags.nugget);
            }
            if (!element.isMetal() && !element.isVanilla()) {
                tag(tags.other).add(POMitems.Metals.ELEMENTS.get(element).get());
            }
        }

        tag(Tags.Items.NETHER_STARS).add(POMitems.POWER_ORB.get());
        tag(Tags.Items.SLIMEBALLS).add(POMitems.BIO_COMPOUND.get()).add(POMitems.FIRE_PROOF_COMPOUND.get()).add(POMitems.REPELLING_COMPOUND.get());

        tag(POMtags.Items.CIRCLE_SAW).add(POMitems.TITANIUM_CIRCLE_SAW.get()).add(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
        tag(POMtags.Items.MILLING_BALL)
                .addTag(Tags.Items.STONE)
                .addTag(Tags.Items.OBSIDIAN)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());

        tag(POMtags.Items.BALL_1)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get());
        tag(POMtags.Items.BALL_2)
                .addTag(Tags.Items.STONE)
                .addTag(Tags.Items.OBSIDIAN)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_3)
                .addTag(Tags.Items.OBSIDIAN)
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_4)
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_5)
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());


        //dusts
        tag(Tags.Items.DUSTS)
                .addTag(POMtags.Items.DUST_ANCIENT_DEBRIS)
                .addTag(POMtags.Items.DUST_COAL)
                .addTag(POMtags.Items.DUST_NETHERITE);

        tag(POMtags.Items.DUST_NETHERITE).add(POMitems.NETHERITE_DUST.get());
        tag(POMtags.Items.DUST_ANCIENT_DEBRIS).add(POMitems.ANCIENT_DEBRIS_DUST.get());
        tag(POMtags.Items.DUST_COAL).add(POMitems.COAL_DUST.get());

        //ingots
        tag(Tags.Items.INGOTS)
                .add(POMitems.TITANIUM_DIBORIDE_INGOT.get());


        //nuggets
        tag(Tags.Items.NUGGETS)
                .addTag(POMtags.Items.NUGGET_NETHERITE);

        tag(POMtags.Items.NUGGET_NETHERITE).add(POMitems.NETHERITE_NUGGET.get());

    }
}