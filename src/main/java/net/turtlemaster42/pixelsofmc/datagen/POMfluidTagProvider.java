package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.Tags;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMtags;

import java.util.concurrent.CompletableFuture;

public class POMfluidTagProvider extends FluidTagsProvider {
    public POMfluidTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider) {
        super(pOutput, pProvider, PixelsOfMc.MOD_ID, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        PixelsOfMc.LOGGER.info("Generating Fluid Tags");
        this.tag(FluidTags.WATER).add(POMfluids.DIRTY_WATER.get(), POMfluids.DIRTY_WATER_FLOWING.get());

        this.tag(POMtags.Fluids.SULFURIC_ACID).add(POMfluids.SULFURIC_ACID.get(), POMfluids.SULFURIC_ACID_FLOWING.get());
        this.tag(POMtags.Fluids.NITRIC_ACID).add(POMfluids.NITRIC_ACID.get(), POMfluids.NITRIC_ACID_FLOWING.get());
        this.tag(POMtags.Fluids.STEAM).add(POMfluids.STEAM.get(), POMfluids.STEAM_FLOWING.get());
        this.tag(POMtags.Fluids.MERCURY).add(POMfluids.MERCURY.get(), POMfluids.MERCURY_FLOWING.get());
        this.tag(POMtags.Fluids.LEAD).add(POMfluids.LEAD.get(), POMfluids.LEAD_FLOWING.get());
        this.tag(POMtags.Fluids.AMMONIA).add(POMfluids.AMMONIA_GAS.get(), POMfluids.AMMONIA_GAS_FLOWING.get());


        this.tag(POMtags.Fluids.HYDROGEN).add(
                POMfluids.HYDROGEN.get(),
                POMfluids.HYDROGEN_FLOWING.get(),
                POMfluids.HYDROGEN_GAS.get(),
                POMfluids.HYDROGEN_GAS_FLOWING.get()
        );
        this.tag(POMtags.Fluids.NITROGEN).add(
                POMfluids.NITROGEN.get(),
                POMfluids.NITROGEN_FLOWING.get(),
                POMfluids.NITROGEN_GAS.get(),
                POMfluids.NITROGEN_GAS_FLOWING.get()
        );
        this.tag(POMtags.Fluids.OXYGEN).add(
                POMfluids.OXYGEN.get(),
                POMfluids.OXYGEN_FLOWING.get(),
                POMfluids.OXYGEN_GAS.get(),
                POMfluids.OXYGEN_GAS_FLOWING.get()
        );
        this.tag(POMtags.Fluids.CHLORINE).add(
                POMfluids.CHLORINE.get(),
                POMfluids.CHLORINE_FLOWING.get(),
                POMfluids.CHLORINE_GAS.get(),
                POMfluids.CHLORINE_GAS_FLOWING.get()
        );
        this.tag(POMtags.Fluids.BROMINE).add(
                POMfluids.BROMINE.get(),
                POMfluids.BROMINE_FLOWING.get(),
                POMfluids.BROMINE_GAS.get(),
                POMfluids.BROMINE_GAS_FLOWING.get()
        );


        this.tag(POMtags.Fluids.NO_INFINITE_DRAINING).add(
                POMfluids.MERCURY.get(),
                POMfluids.MERCURY_FLOWING.get(),
                POMfluids.LEAD.get(),
                POMfluids.LEAD_FLOWING.get(),
                POMfluids.SULFURIC_ACID.get(),
                POMfluids.SULFURIC_ACID_FLOWING.get(),
                POMfluids.NITRIC_ACID.get(),
                POMfluids.NITRIC_ACID_FLOWING.get(),
                POMfluids.PUREX_SOLUTION.get(),
                POMfluids.PUREX_SOLUTION_FLOWING.get(),
                POMfluids.NUCLEAR_WASTE.get(),
                POMfluids.NUCLEAR_WASTE_FLOWING.get(),
                POMfluids.NUCLEAR_WASTE_SOLUTION.get(),
                POMfluids.NUCLEAR_WASTE_SOLUTION_FLOWING.get(),
                POMfluids.URANIUM_SOLUTION.get(),
                POMfluids.URANIUM_SOLUTION_FLOWING.get(),
                POMfluids.PLUTONIUM_SOLUTION.get(),
                POMfluids.PLUTONIUM_SOLUTION_FLOWING.get(),
                POMfluids.HYDROGEN.get(),
                POMfluids.HYDROGEN_FLOWING.get(),
                POMfluids.NITROGEN.get(),
                POMfluids.NITROGEN_FLOWING.get(),
                POMfluids.OXYGEN.get(),
                POMfluids.OXYGEN_FLOWING.get(),
                POMfluids.CHLORINE.get(),
                POMfluids.CHLORINE_FLOWING.get(),
                POMfluids.BROMINE.get(),
                POMfluids.BROMINE_FLOWING.get()
        );
    }
}
