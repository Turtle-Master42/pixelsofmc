package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class POMitemTagProvider extends ItemTagsProvider {

    public POMitemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, tagLookupCompletableFuture, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        for(Element e : Element.validValues()) {
            POMtags.ElementTags tags = POMtags.getTagsFor(e);

            tag(POMtags.Items.ATOM).add(e.atom64().asItem());
            tag(POMtags.Items.ATOM).add(e.atom512().asItem());
            tag(POMtags.Items.ATOM64).add(e.atom64().asItem());
            tag(POMtags.Items.ATOM512).add(e.atom512().asItem());

            if(e.getElement() >= 3 && e.getElement() <= 7) {
                tag(POMtags.Items.SDS).add(e.atom64().asItem());
                tag(POMtags.Items.SDS).add(e.atom512().asItem());
            }
            if(e.getElement() >= 38 && e.getElement() <= 41) {
                tag(POMtags.Items.SDS).add(e.atom64().asItem());
                tag(POMtags.Items.SDS).add(e.atom512().asItem());
            }
            if(e.getElement() >= 8 && e.getElement() <= 37) {
                tag(POMtags.Items.MDS).add(e.atom64().asItem());
                tag(POMtags.Items.MDS).add(e.atom512().asItem());
            }
            if(e.getElement() >= 42 && e.getElement() <= 94) {
                tag(POMtags.Items.MNS).add(e.atom64().asItem());
                tag(POMtags.Items.MNS).add(e.atom512().asItem());
            }

            for (int i = 0; i < e.getIsotopes().getNeutrons().length; i++) {
                tag(POMtags.Items.ATOM).add(e.isotope64(i).asItem());
                tag(POMtags.Items.ATOM).add(e.isotope512(i).asItem());
                tag(POMtags.Items.ATOM64).add(e.isotope64(i).asItem());
                tag(POMtags.Items.ATOM512).add(e.isotope512(i).asItem());

                if (e.getElement() >= 3 && e.getElement() <= 7) {
                    tag(POMtags.Items.SDS).add(e.isotope64(i).asItem());
                    tag(POMtags.Items.SDS).add(e.isotope512(i).asItem());
                }
                if (e.getElement() >= 38 && e.getElement() <= 41) {
                    tag(POMtags.Items.SDS).add(e.isotope64(i).asItem());
                    tag(POMtags.Items.SDS).add(e.isotope512(i).asItem());
                }
                if (e.getElement() >= 8 && e.getElement() <= 37) {
                    tag(POMtags.Items.MDS).add(e.isotope64(i).asItem());
                    tag(POMtags.Items.MDS).add(e.isotope512(i).asItem());
                }
                if (e.getElement() >= 42 && e.getElement() <= 94) {
                    tag(POMtags.Items.MNS).add(e.isotope64(i).asItem());
                    tag(POMtags.Items.MNS).add(e.isotope512(i).asItem());
                }
            }

            if(e.shouldAddDust()) {
                tag(tags.dust).add(e.dust().asItem());
                tag(Tags.Items.DUSTS).addTag(tags.dust);
            }
            if (e.isMetal()) {
                tag(tags.metal).add(e.item().asItem());
                tag(Tags.Items.INGOTS).addTag(tags.metal);
            }
            if (e.shouldAddNugget()) {
                tag(tags.nugget).add(e.nugget().asItem());
                tag(Tags.Items.NUGGETS).addTag(tags.nugget);
            }
            if (!e.isMetal() && !e.isVanilla()) {
                tag(tags.other1).add(e.item().asItem());
                tag(tags.other2).add(e.item().asItem());
            }
            if (e.shouldAddBlock()) {
                tag(tags.block).add(e.block().asItem());
                tag(Tags.Items.STORAGE_BLOCKS).add(e.block().asItem());
            }
        }

        tag(Tags.Items.SLIMEBALLS).add(POMitems.BIO_COMPOUND.get()).add(POMitems.FIRE_PROOF_COMPOUND.get()).add(POMitems.REPELLING_COMPOUND.get());
        tag(Tags.Items.GLASS).add(POMblocks.REINFORCED_GLASS.get().asItem());
        tag(Tags.Items.GEMS)
                .add(POMitems.PERFECT_DIAMOND.get(),
                        POMitems.VIOLET_DIAMOND.get(),
                        POMitems.RED_DIAMOND.get(),
                        POMitems.BLACK_DIAMOND.get(),
                        POMitems.BROWN_DIAMOND.get(),
                        POMitems.ORANGE_DIAMOND.get(),
                        POMitems.YELLOW_DIAMOND.get(),
                        POMitems.LIME_DIAMOND.get(),
                        POMitems.GREEN_DIAMOND.get(),
                        POMitems.BLUE_DIAMOND.get(),
                        POMitems.PINK_DIAMOND.get()
                );

        tag(Tags.Items.GEMS_DIAMOND).add(POMitems.PERFECT_DIAMOND.get());
        tag(Tags.Items.ORES)
                .add(POMblocks.ACANTHITE_ORE.get().asItem())
                .add(POMblocks.LESSER_ACANTHITE_ORE.get().asItem())
                .add(POMblocks.ENDSTONE_TITANIUM_ORE.get().asItem())
                .add(POMblocks.DEEPSLATE_TITANIUM_ORE.get().asItem())
                .add(POMblocks.TITANIUM_ORE.get().asItem());
        tag(Tags.Items.STORAGE_BLOCKS)
                .add(POMblocks.ACANTHITE.get().asItem())
                .add(POMblocks.TITANIUM_DIBORIDE_BLOCK.get().asItem())
                .add(POMblocks.TITANIUM_GOLD_BLOCK.get().asItem())
                .add(POMblocks.RAW_TITANIUM_BLOCK.get().asItem())
                .add(POMblocks.BLACK_DIAMOND_BLOCK.get().asItem())
                .add(POMblocks.PERFECT_DIAMOND_BLOCK.get().asItem())
                .add(POMblocks.VIOLET_DIAMOND_BLOCK.get().asItem())
                .add(POMblocks.RED_DIAMOND_BLOCK.get().asItem())
                .add(POMblocks.PYROLYTIC_CARBON_BLOCK.get().asItem());
        tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(POMblocks.DEEPSLATE_TITANIUM_ORE.get().asItem());
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(POMblocks.TITANIUM_ORE.get().asItem());
        tag(Tags.Items.RAW_MATERIALS).add(POMitems.RAW_TITANIUM.get());
        tag(Tags.Items.TOOLS)
                .add(POMitems.SCREWDRIVER.get())
                .add(POMitems.HAMMER.get())
                .add(POMitems.CLEANING_CLOTH.get())
                .add(POMitems.CLEANING_SPONGE.get())
                .add(POMitems.WIRECUTTER.get());


        tag(ItemTags.PIGLIN_LOVED)
                .add(POMitems.TITANIUM_GOLD_INGOT.get())
                .add(POMitems.TITANIUM_GOLD_DUST.get())
                .add(POMitems.TITANIUM_GOLD_PLATING.get())
                .add(POMitems.TITANIUM_GOLD_NUGGET.get())
                .add(POMitems.TITANIUM_GOLD_BALL.get())
                .add(POMitems.TITANIUM_GOLD_CIRCLE_SAW.get())
                .add(POMblocks.TITANIUM_GOLD_BLOCK.get().asItem())
                .add(POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get().asItem())
                .add(POMblocks.TITANIUM_GOLD_PLATING_SLAB.get().asItem())
                .add(POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get().asItem())
                .add(Element.GOLD.dust().asItem());

        tag(POMtags.Items.CIRCLE_SAW)
                .add(POMitems.TITANIUM_CIRCLE_SAW.get())
                .add(POMitems.TITANIUM_GOLD_CIRCLE_SAW.get())
                .add(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get());
        tag(POMtags.Items.MILLING_BALL)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_GOLD_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());

        tag(POMtags.Items.BALL_1)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get());
        tag(POMtags.Items.BALL_2)
                .add(POMitems.RUBBER_BALL.get())
                .add(POMitems.TITANIUM_GOLD_BALL.get())
                .add(POMitems.FIRE_PROOF_RUBBER_BALL.get())
                .add(POMitems.REPELLING_RUBBER_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_3)
                .add(POMitems.TITANIUM_BALL.get())
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_GOLD_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_4)
                .add(POMitems.NETHERITE_BALL.get())
                .add(POMitems.TITANIUM_GOLD_BALL.get())
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());
        tag(POMtags.Items.BALL_5)
                .add(POMitems.TITANIUM_DIBORIDE_BALL.get());

        tag(POMtags.Items.SOUL_FUELS)
                .add(POMitems.SOUL_COAL.get());

        tag(POMtags.Items.ORES_TITANIUM)
                .add(POMblocks.ENDSTONE_TITANIUM_ORE.get().asItem())
                .add(POMblocks.DEEPSLATE_TITANIUM_ORE.get().asItem())
                .add(POMblocks.TITANIUM_ORE.get().asItem());

        tag(POMtags.Items.FOCUS_LENS)
                .add(POMitems.VIOLET_DIAMOND.get())
                .add(POMitems.RED_DIAMOND.get())
                .add(POMitems.PERFECT_DIAMOND.get());
        tag(POMtags.Items.DARK_LENS)
                .add(POMitems.BLACK_DIAMOND.get())
                .add(POMitems.GREEN_DIAMOND.get())
                .addTag(Tags.Items.GLASS_RED)
                .addTag(Tags.Items.GLASS_ORANGE)
                .addTag(Tags.Items.GLASS_YELLOW)
                .addTag(Tags.Items.GLASS_LIME)
                .addTag(Tags.Items.GLASS_GREEN)
                .addTag(Tags.Items.GLASS_CYAN)
                .addTag(Tags.Items.GLASS_LIGHT_BLUE)
                .addTag(Tags.Items.GLASS_BLUE)
                .addTag(Tags.Items.GLASS_PURPLE)
                .addTag(Tags.Items.GLASS_MAGENTA)
                .addTag(Tags.Items.GLASS_PINK)
                .addTag(Tags.Items.GLASS_WHITE)
                .addTag(Tags.Items.GLASS_LIGHT_GRAY)
                .addTag(Tags.Items.GLASS_GRAY)
                .addTag(Tags.Items.GLASS_BLACK)
                .addTag(Tags.Items.GLASS_BROWN)
        ;
        tag(POMtags.Items.OPAQUE_LENS).add(Items.TINTED_GLASS);
        tag(POMtags.Items.RED_LENS)
                .addTag(Tags.Items.GLASS_RED)
                .add(POMitems.RED_DIAMOND.get());
        tag(POMtags.Items.ORANGE_LENS)
                .addTag(Tags.Items.GLASS_ORANGE)
                .addTag(Tags.Items.GLASS_BROWN)
                .add(POMitems.ORANGE_DIAMOND.get());
        tag(POMtags.Items.YELLOW_LENS)
                .addTag(Tags.Items.GLASS_YELLOW)
                .add(POMitems.YELLOW_DIAMOND.get());
        tag(POMtags.Items.LIME_LENS)
                .addTag(Tags.Items.GLASS_LIME)
                .addTag(Tags.Items.GLASS_GREEN)
                .add(POMitems.GREEN_DIAMOND.get())
                .add(POMitems.LIME_DIAMOND.get());
        tag(POMtags.Items.LIGHT_BLUE_LENS)
                .addTag(Tags.Items.GLASS_LIGHT_BLUE)
                .addTag(Tags.Items.GLASS_CYAN);
        tag(POMtags.Items.BLUE_LENS)
                .addTag(Tags.Items.GLASS_BLUE)
                .add(POMitems.BLUE_DIAMOND.get());
        tag(POMtags.Items.PURPLE_LENS)
                .addTag(Tags.Items.GLASS_PURPLE)
                .add(POMitems.VIOLET_DIAMOND.get());
        tag(POMtags.Items.MAGENTA_LENS)
                .addTag(Tags.Items.GLASS_PINK)
                .add(POMitems.PINK_DIAMOND.get())
                .addTag(Tags.Items.GLASS_MAGENTA);
        tag(POMtags.Items.WHITE_LENS)
                .addTag(Tags.Items.GLASS)
                .addTag(Tags.Items.GLASS_WHITE)
                .addTag(Tags.Items.GLASS_LIGHT_GRAY);

        //dusts
        tag(Tags.Items.DUSTS)
                .addTag(POMtags.Items.DUST_ANCIENT_DEBRIS)
                .addTag(POMtags.Items.DUST_COAL)
                .add(POMitems.TITANIUM_GOLD_DUST.get())
                .add(POMitems.ACANTHITE_DUST.get())
                .add(POMitems.CRYING_OBSIDIAN_DUST.get())
                .add(POMitems.MERCURY_SULFIDE_DUST.get())
                .add(POMitems.OBSIDIAN_DUST.get())
                .add(POMitems.RED_SILVER_DUST.get())
                .add(POMitems.ROYAL_TUNGSTEN_DUST.get())
                .add(POMitems.SUPERCONDUCTIVE_DUST.get())
                .add(POMitems.TITANIUM_DIBORIDE_DUST.get())
                .add(POMitems.TITANIUM_OXIDE_DUST.get())
                .add(POMitems.TITANIUM_GOLD_DUST.get())
                .add(POMitems.REFINED_REDSTONE.get())
                .add(POMitems.QUARTZ_DUST.get())
                .addTag(POMtags.Items.DUST_NETHERITE);

        tag(POMtags.Items.DUST_NETHERITE).add(POMitems.NETHERITE_DUST.get());
        tag(POMtags.Items.DUST_ANCIENT_DEBRIS).add(POMitems.ANCIENT_DEBRIS_DUST.get());
        tag(POMtags.Items.DUST_COAL).add(POMitems.COAL_DUST.get());
        tag(POMtags.Items.DUST_QUARTZ).add(POMitems.QUARTZ_DUST.get());

        //ingots
        tag(Tags.Items.INGOTS)
                .add(POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .add(POMitems.TITANIUM_GOLD_INGOT.get())
                .add(POMitems.SUPERCONDUCTIVE_INGOT.get())
                .add(POMitems.ROYAL_TUNGSTEN_INGOT.get())
                .add(POMitems.RED_SILVER_INGOT.get());

        //nuggets
        tag(Tags.Items.NUGGETS)
                .add(POMitems.NETHERITE_NUGGET.get())
                .add(POMitems.SUPERCONDUCTIVE_NUGGET.get())
                .add(POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .add(POMitems.ROYAL_TUNGSTEN_NUGGET.get())
                .add(POMitems.TITANIUM_GOLD_NUGGET.get())
                .add(POMitems.RED_SILVER_NUGGET.get());

        tag(POMtags.Items.NUGGET_NETHERITE).add(POMitems.NETHERITE_NUGGET.get());

    }
}