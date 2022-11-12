package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;

import javax.annotation.Nullable;

public class POMitemTagProvider extends ItemTagsProvider {

    public POMitemTagProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, new POMblockTagProvider(pGenerator, PixelsOfMc.MOD_ID, existingFileHelper),
                PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        tag(POMtags.Items.CIRCLE_SAW).add(POMitems.TITANIUM_CIRCLE_SAW.get()).add(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
        tag(POMtags.Items.MILLING_BALL)
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
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_3)
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_4)
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_5)
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());


        tag(Tags.Items.DUSTS)
                .addTag(POMtags.Items.DUST_COAL)
                .addTag(POMtags.Items.DUST_TITANIUM);
        tag(Tags.Items.INGOTS)
                .addTag(POMtags.Items.INGOT_TITANIUM)
                .add(POMitems.TITANIUM_DIBORIDE_INGOT.get());
        tag(Tags.Items.NUGGETS)
                .addTag(POMtags.Items.NUGGET_COPPER)
                .addTag(POMtags.Items.NUGGET_SILVER)
                .addTag(POMtags.Items.NUGGET_STEEL)
                .addTag(POMtags.Items.NUGGET_TITANIUM)
                .addTag(POMtags.Items.NUGGET_NETHERITE);

        tag(Tags.Items.NETHER_STARS).add(POMitems.POWER_ORB.get());
        tag(Tags.Items.SLIMEBALLS).add(POMitems.BIO_COMPOUND.get()).add(POMitems.FIRE_PROOF_COMPOUND.get()).add(POMitems.REPELLING_COMPOUND.get());


        tag(POMtags.Items.INGOT_TITANIUM).add(POMitems.TITANIUM_INGOT.get());

        tag(POMtags.Items.NUGGET_COPPER).add(POMitems.COPPER_NUGGET.get());
        tag(POMtags.Items.NUGGET_SILVER).add(POMitems.SILVER_NUGGET.get());
        tag(POMtags.Items.NUGGET_STEEL).add(POMitems.STEEL_NUGGET.get());
        tag(POMtags.Items.NUGGET_TITANIUM).add(POMitems.TITANIUM_NUGGET.get());
        tag(POMtags.Items.NUGGET_NETHERITE).add(POMitems.NETHERITE_NUGGET.get());

        tag(POMtags.Items.DUST_COAL).add(POMitems.COAL_DUST.get());
        tag(POMtags.Items.DUST_TITANIUM).add(POMitems.TITANIUM_DUST.get());
    }

}