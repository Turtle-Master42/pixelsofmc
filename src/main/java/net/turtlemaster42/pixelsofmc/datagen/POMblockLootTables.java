package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.world.item.Items;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

public class POMblockLootTables extends BlockLoot {

    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F };

    @Override
    protected void addTables() {
        this.dropSelf(POMblocks.TITANIUM_BLOCK.get());
        this.dropSelf(POMblocks.RAW_TITANIUM_BLOCK.get());
        this.dropSelf(POMblocks.TITANIUM_DIBORIDE_BLOCK.get());
        this.dropSelf(POMblocks.PIXEL_SPLITTER.get());
        this.dropSelf(POMblocks.BALL_MILL.get());
        this.dropSelf(POMblocks.GRINDER.get());
        this.dropSelf(POMblocks.SDS_CONTROLLER.get());

        this.dropSelf(POMblocks.SIMPLE_CASING_1.get());
        this.dropSelf(POMblocks.ADVANCED_CASING_1.get());
        this.dropSelf(POMblocks.PERFECTED_CASING_1.get());
        this.dropSelf(POMblocks.STRONG_CASING.get());
        this.dropSelf(POMblocks.REINFORCED_CASING.get());



        this.dropOther(POMblocks.MACHINE_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.MACHINE_ENERGY_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.MACHINE_ITEM_BLOCK.get(), Items.AIR);
        this.dropOther(POMblocks.FUSION_CORE.get(), Items.AIR);


        this.add(POMblocks.TITANIUM_ORE.get(), (block) -> {
            return createOreDrop(block, POMitems.RAW_TITANIUM.get());
        });
        this.add(POMblocks.DEEPSLATE_TITANIUM_ORE.get(), (block) -> {
            return createOreDrop(block, POMitems.RAW_TITANIUM.get());
        });
        this.add(POMblocks.ENDSTONE_TITANIUM_ORE.get(), (block) -> {
            return createOreDrop(block, POMitems.RAW_TITANIUM.get());
        });

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return POMblocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}