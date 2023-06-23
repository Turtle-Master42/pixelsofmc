package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class POMblockLootTables extends BlockLootSubProvider {
    protected POMblockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F };

    @Override
    protected void generate() {

        for(Element m : Element.values()) {
            if (m.shouldAddBlock())
                this.dropSelf(POMblocks.Metals.BLOCKS.get(m).get());
        }

        this.dropSelf(POMblocks.RAW_TITANIUM_BLOCK.get());
        this.dropSelf(POMblocks.TITANIUM_DIBORIDE_BLOCK.get());
        this.dropSelf(POMblocks.ALUMINIUM_SCRAP_BLOCK.get());
        this.dropSelf(POMblocks.ACANTHITE.get());
        this.dropSelf(POMblocks.HOT_ISOSTATIC_PRESS.get());
        this.dropSelf(POMblocks.PIXEL_SPLITTER.get());
        this.dropSelf(POMblocks.BALL_MILL.get());
        this.dropSelf(POMblocks.GRINDER.get());
        this.dropSelf(POMblocks.CHEMICAL_SEPARATOR.get());
        this.dropSelf(POMblocks.CHEMICAL_COMBINER.get());
        this.dropSelf(POMblocks.SDS_CONTROLLER.get());
        this.dropSelf(POMblocks.MDS_CONTROLLER.get());
        this.dropSelf(POMblocks.MNS_CONTROLLER.get());
        this.dropSelf(POMblocks.BH_CONTROLLER.get());

        this.dropSelf(POMblocks.SIMPLE_CASING_1.get());
        this.dropSelf(POMblocks.ADVANCED_CASING_1.get());
        this.dropSelf(POMblocks.PERFECTED_CASING_1.get());
        this.dropSelf(POMblocks.STRONG_CASING.get());
        this.dropSelf(POMblocks.REINFORCED_CASING.get());
        this.dropSelf(POMblocks.COPPER_SPOOL.get());
        this.dropSelf(POMblocks.SILVER_SPOOL.get());
        this.dropSelf(POMblocks.TUNGSTEN_SPOOL.get());

        this.dropSelf(POMblocks.REINFORCED_GLASS.get());
        this.dropSelf(POMblocks.REINFORCED_THING.get());

        this.dropSelf(POMblocks.TITANIUM_PLATING_BLOCK.get());
        this.dropSelf(POMblocks.TITANIUM_PLATING_SLAB.get());
        this.dropSelf(POMblocks.TITANIUM_PLATING_STAIRS.get());
        this.dropSelf(POMblocks.NETHERITE_PLATING_BLOCK.get());
        this.dropSelf(POMblocks.NETHERITE_PLATING_SLAB.get());
        this.dropSelf(POMblocks.NETHERITE_PLATING_STAIRS.get());
        this.dropSelf(POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get());
        this.dropSelf(POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get());
        this.dropSelf(POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get());

        this.dropOther(POMblocks.MERCURY_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.SULFURIC_ACID_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.HYDROGEN_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.NITROGEN_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.OXYGEN_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.CHLORINE_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.BROMINE_BLOCK.get(), Items.AIR);

        this.dropOther(POMblocks.MACHINE_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.MACHINE_ENERGY_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.MACHINE_ITEM_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.STAR.get(), Items.AIR);

        this.add(POMblocks.TITANIUM_ORE.get(), (block) -> createOreDrop(block, POMitems.RAW_TITANIUM.get()));
        this.add(POMblocks.DEEPSLATE_TITANIUM_ORE.get(), (block) -> createOreDrop(block, POMitems.RAW_TITANIUM.get()));
        this.add(POMblocks.ENDSTONE_TITANIUM_ORE.get(), (block) -> createOreDrop(block, POMitems.RAW_TITANIUM.get()));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return POMblocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}