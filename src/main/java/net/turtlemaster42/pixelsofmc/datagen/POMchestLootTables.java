package net.turtlemaster42.pixelsofmc.datagen;


import net.minecraft.data.DataGenerator;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class POMchestLootTables extends ChestLoot {
    private static final ResourceLocation CUSTOM_CHEST_LOOT =
            new ResourceLocation(PixelsOfMc.MOD_ID, "chests/custom_chest_loot");

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124363_) {
    }
}
