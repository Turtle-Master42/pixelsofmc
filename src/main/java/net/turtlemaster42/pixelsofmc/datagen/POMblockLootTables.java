package net.turtlemaster42.pixelsofmc.datagen;

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

        this.add(POMblocks.TITANIUM_ORE.get(),
                (block) -> createOreDrop(POMblocks.TITANIUM_ORE.get(), POMitems.RAW_TITANIUM.get()));
        this.add(POMblocks.DEEPSLATE_TITANIUM_ORE.get(),
                (block) -> createOreDrop(POMblocks.DEEPSLATE_TITANIUM_ORE.get(), POMitems.RAW_TITANIUM.get()));
        this.add(POMblocks.ENDSTONE_TITANIUM_ORE.get(),
                (block) -> createOreDrop(POMblocks.ENDSTONE_TITANIUM_ORE.get(), POMitems.RAW_TITANIUM.get()));
/*
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.CUCUMBER_PLANT.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CucumberPlantBlock.AGE, 5));

        this.add(ModBlocks.CUCUMBER_PLANT.get(), createCropDrops(ModBlocks.CUCUMBER_PLANT.get(), ModItems.CUCUMBER.get(),
                ModItems.CUCUMBER_SEEDS.get(), lootitemcondition$builder));
 */
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return POMblocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}