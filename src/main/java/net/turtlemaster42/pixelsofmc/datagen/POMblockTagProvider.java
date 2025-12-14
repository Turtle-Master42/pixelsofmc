package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class POMblockTagProvider extends BlockTagsProvider {

    public POMblockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PixelsOfMc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        PixelsOfMc.LOGGER.info("Generating Block Tags");
        tag(Tags.Blocks.GLASS).add(POMblocks.REINFORCED_GLASS.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                POMblocks.SIMPLE_CASING_1.get(),
                POMblocks.ADVANCED_CASING_1.get(),
                POMblocks.PERFECTED_CASING_1.get(),
                POMblocks.SIMPLE_CASING_2.get(),
                POMblocks.ADVANCED_CASING_2.get(),
                POMblocks.PERFECTED_CASING_2.get(),
                POMblocks.STRONG_CASING.get(),
                POMblocks.STRENGTHENED_CASING.get(),
                POMblocks.REINFORCED_CASING.get(),

                POMblocks.TITANIUM_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_PLATING_SLAB.get(),
                POMblocks.NETHERITE_PLATING_BLOCK.get(),
                POMblocks.NETHERITE_PLATING_STAIRS.get(),
                POMblocks.NETHERITE_PLATING_SLAB.get(),
                POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(),
                POMblocks.LEAD_PLATING_BLOCK.get(),
                POMblocks.LEAD_PLATING_STAIRS.get(),
                POMblocks.LEAD_PLATING_SLAB.get(),
                POMblocks.TUNGSTEN_PLATING_BLOCK.get(),
                POMblocks.TUNGSTEN_PLATING_STAIRS.get(),
                POMblocks.TUNGSTEN_PLATING_SLAB.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(),

                POMblocks.TITANIUM_ORE.get(),
                POMblocks.DEEPSLATE_TITANIUM_ORE.get(),
                POMblocks.ENDSTONE_TITANIUM_ORE.get(),
                POMblocks.ACANTHITE.get(),
                POMblocks.ACANTHITE_ORE.get(),
                POMblocks.LESSER_ACANTHITE_ORE.get(),
                POMblocks.ACANTHITE_SPIKE.get(),

                POMblocks.GRINDER.get(),
                POMblocks.BALL_MILL.get(),
                POMblocks.HOT_ISOSTATIC_PRESS.get(),
                POMblocks.CHEMICAL_SEPARATOR.get(),
                POMblocks.CHEMICAL_COMBINER.get(),
                POMblocks.PIXEL_SPLITTER.get(),
                POMblocks.PIXEL_ASSEMBLER.get(),
                POMblocks.SDS_CONTROLLER.get(),
                POMblocks.MDS_CONTROLLER.get(),
                POMblocks.MNS_CONTROLLER.get(),
                POMblocks.BH_CONTROLLER.get(),
                POMblocks.NUCLEAR_REACTOR.get(),
                POMblocks.CHEMICAL_MIXER.get(),
                POMblocks.INDUSTRIAL_HEAT_EXCHANGER.get(),

                POMblocks.REINFORCED_GLASS.get(),
                POMblocks.MACHINE_CASING.get(),
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.FISSION_CASING.get(),
                POMblocks.FUEL_CELL_HOLDER.get(),
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_CORNER.get(),
                POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get(),
                POMblocks.HEAT_SINK.get(),
                POMblocks.FUSION_ENERGY_PORT.get(),
                POMblocks.FUSION_ITEM_PORT.get(),
                POMblocks.FUSION_FLUID_PORT.get(),
                POMblocks.FUSION_PLASMA_PORT.get(),
                POMblocks.FISSION_ENERGY_PORT.get(),
                POMblocks.FISSION_FLUID_PORT.get(),
                POMblocks.FLUID_PORT.get(),
                POMblocks.ITEM_PORT.get(),
                POMblocks.ENERGY_PORT.get(),
                POMblocks.MACHINE_CASING_STAIRS.get(),
                POMblocks.MACHINE_CASING_SLAB.get(),
                POMblocks.FISSION_CASING_STAIRS.get(),
                POMblocks.FISSION_CASING_SLAB.get(),
                POMblocks.ARMORED_MACHINE_CASING_STAIRS.get(),
                POMblocks.ARMORED_MACHINE_CASING_SLAB.get(),
                POMblocks.MACHINE_COIL.get(),

                POMblocks.RAW_TITANIUM_BLOCK.get(),
                POMblocks.TITANIUM_DIBORIDE_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_BLOCK.get(),
                POMblocks.BLACK_DIAMOND_BLOCK.get(),
                POMblocks.PERFECT_DIAMOND_BLOCK.get(),
                POMblocks.VIOLET_DIAMOND_BLOCK.get(),
                POMblocks.RED_DIAMOND_BLOCK.get(),

                POMblocks.EXTENDER_BLOCK.get(),
                POMblocks.EXTENDER_ENERGY_BLOCK.get(),
                POMblocks.EXTENDER_ITEM_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                POMblocks.RAW_TITANIUM_BLOCK.get(),
                POMblocks.EXTENDER_BLOCK.get(),
                POMblocks.EXTENDER_ENERGY_BLOCK.get(),
                POMblocks.EXTENDER_ITEM_BLOCK.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(),
                POMblocks.TUNGSTEN_PLATING_BLOCK.get(),
                POMblocks.TUNGSTEN_PLATING_STAIRS.get(),
                POMblocks.TUNGSTEN_PLATING_SLAB.get(),
                POMblocks.NETHERITE_PLATING_BLOCK.get(),
                POMblocks.NETHERITE_PLATING_STAIRS.get(),
                POMblocks.NETHERITE_PLATING_SLAB.get(),
                POMblocks.MACHINE_CASING_STAIRS.get(),
                POMblocks.MACHINE_CASING_SLAB.get(),
                POMblocks.FISSION_CASING_STAIRS.get(),
                POMblocks.FISSION_CASING_SLAB.get(),
                POMblocks.ARMORED_MACHINE_CASING_STAIRS.get(),
                POMblocks.ARMORED_MACHINE_CASING_SLAB.get(),
                POMblocks.BLACK_DIAMOND_BLOCK.get(),
                POMblocks.PERFECT_DIAMOND_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                POMblocks.TITANIUM_DIBORIDE_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_BLOCK.get(),
                POMblocks.VIOLET_DIAMOND_BLOCK.get(),
                POMblocks.RED_DIAMOND_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                POMblocks.TITANIUM_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_PLATING_SLAB.get(),
                POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(),
                POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(),
                POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(),
                POMblocks.LEAD_PLATING_BLOCK.get(),
                POMblocks.LEAD_PLATING_STAIRS.get(),
                POMblocks.LEAD_PLATING_SLAB.get(),
                POMblocks.PYROLYTIC_CARBON_BLOCK.get()
        );

        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(
                POMblocks.TITANIUM_DIBORIDE_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_BLOCK.get(),
                POMblocks.BLACK_DIAMOND_BLOCK.get(),
                POMblocks.PERFECT_DIAMOND_BLOCK.get(),
                POMblocks.VIOLET_DIAMOND_BLOCK.get(),
                POMblocks.RED_DIAMOND_BLOCK.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND).add(
                POMblocks.PERFECT_DIAMOND_BLOCK.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
                POMblocks.RAW_TITANIUM_BLOCK.get(),
                POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get(),
                POMblocks.TITANIUM_GOLD_BLOCK.get(),
                POMblocks.PYROLYTIC_CARBON_BLOCK.get(),
                POMblocks.BLACK_DIAMOND_BLOCK.get(),
                POMblocks.PERFECT_DIAMOND_BLOCK.get(),
                POMblocks.VIOLET_DIAMOND_BLOCK.get(),
                POMblocks.RED_DIAMOND_BLOCK.get()
        );


        this.tag(POMtags.Blocks.ORES_TITANIUM).add(
                POMblocks.TITANIUM_ORE.get(),
                POMblocks.DEEPSLATE_TITANIUM_ORE.get(),
                POMblocks.ENDSTONE_TITANIUM_ORE.get()
        );

        this.tag(POMtags.Blocks.EXTENDER_BLOCK).add(
                POMblocks.EXTENDER_BLOCK.get(),
                POMblocks.EXTENDER_ITEM_BLOCK.get(),
                POMblocks.EXTENDER_ENERGY_BLOCK.get()
        );

        this.tag(POMtags.Blocks.MACHINE_CASINGS).add(
                POMblocks.MACHINE_CASING.get(),
                POMblocks.ITEM_PORT.get(),
                POMblocks.ENERGY_PORT.get(),
                POMblocks.FLUID_PORT.get(),
                POMblocks.FISSION_CASING.get(),
                POMblocks.FISSION_FLUID_PORT.get(),
                POMblocks.FISSION_ENERGY_PORT.get(),
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_FLUID_PORT.get(),
                POMblocks.FUSION_ENERGY_PORT.get(),
                POMblocks.FUSION_PLASMA_PORT.get(),
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_CORNER.get(),
                POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get(),
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.MACHINE_COIL.get(),
                POMblocks.REINFORCED_GLASS.get()
        );

        this.tag(POMtags.Blocks.FISSION_CASINGS).add(
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_FLUID_PORT.get(),
                POMblocks.FUSION_ENERGY_PORT.get(),
                POMblocks.FISSION_CASING.get(),
                POMblocks.FISSION_FLUID_PORT.get(),
                POMblocks.FISSION_ENERGY_PORT.get(),
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.REINFORCED_GLASS.get()
        );

        this.tag(POMtags.Blocks.FUSION_CASINGS).add(
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_FLUID_PORT.get(),
                POMblocks.FUSION_ENERGY_PORT.get(),
                POMblocks.FUSION_PLASMA_PORT.get(),
                POMblocks.FUSION_ITEM_PORT.get(),
                POMblocks.FUSION_CASING.get(),
                POMblocks.FUSION_CORNER.get(),
                POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get(),
                POMblocks.REINFORCED_GLASS.get(),
                POMblocks.ARMORED_MACHINE_CASING.get()
        );

        this.tag(POMtags.Blocks.CASINGS_DECOR).add(
                POMblocks.MACHINE_CASING.get(),
                POMblocks.MACHINE_CASING_SLAB.get(),
                POMblocks.MACHINE_CASING_STAIRS.get(),
                POMblocks.FISSION_CASING.get(),
                POMblocks.FISSION_CASING_SLAB.get(),
                POMblocks.FISSION_CASING_STAIRS.get(),
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.ARMORED_MACHINE_CASING_SLAB.get(),
                POMblocks.ARMORED_MACHINE_CASING_STAIRS.get()
        );

        this.tag(POMtags.Blocks.FISSION_DECOR).add(
                POMblocks.FISSION_CASING.get(),
                POMblocks.FISSION_CASING_SLAB.get(),
                POMblocks.FISSION_CASING_STAIRS.get(),
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.ARMORED_MACHINE_CASING_SLAB.get(),
                POMblocks.ARMORED_MACHINE_CASING_STAIRS.get()
        );

        this.tag(POMtags.Blocks.FUSION_DECOR).add(
                POMblocks.ARMORED_MACHINE_CASING.get(),
                POMblocks.ARMORED_MACHINE_CASING_SLAB.get(),
                POMblocks.ARMORED_MACHINE_CASING_STAIRS.get()
        );

        //auto gen
        for(Element e : Element.validValues()) {
            if (e.shouldAddBlock()) {
                this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(e.block());
                this.tag(BlockTags.NEEDS_IRON_TOOL).add(e.block());
                this.tag(BlockTags.BEACON_BASE_BLOCKS).add(e.block());
                this.tag(Tags.Blocks.STORAGE_BLOCKS).add(e.block());
            }
        }
    }
}
