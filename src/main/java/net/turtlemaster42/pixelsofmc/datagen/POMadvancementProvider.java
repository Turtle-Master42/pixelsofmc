package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class POMadvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {

    private Consumer<Advancement> consumer = null;
    private ExistingFileHelper existingFileHelper = null;

    @Override
    public void generate(HolderLookup.@NotNull Provider lookupProvider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        this.consumer = consumer;
        this.existingFileHelper = existingFileHelper;

        Advancement root = createBase(POMitems.NETHERITE_PLATING.get(), "root", Util.resourceLocation("textures/block/titanium_plating_block.png")).save(consumer, id("root"), existingFileHelper);
        Advancement titanium = itemTask(Element.TITANIUM.item(), "titanium", root);
        Advancement ball_mill = itemTask(POMblocks.BALL_MILL.get(), "ball_mill", titanium);
        Advancement grinder = itemTask(POMblocks.GRINDER.get(), "grinder", ball_mill);
        Advancement crying_dust = itemTask(POMitems.CRYING_OBSIDIAN_DUST.get(), "crying_dust", grinder);
        Advancement lead = itemTask(Element.LEAD.dust(), "lead", grinder);
        Advancement silver = itemTask(Element.SILVER.dust(), "silver", grinder);
//        Advancement full_cast = itemGoalTask("full_cast", crying_dust, POMitems.INGOT_CAST.get(), POMitems.BALL_CAST.get(), POMitems.INGOT_CAST.get()); doesn't require all in the inventory
        Advancement titanium_gold = itemTask(POMitems.TITANIUM_GOLD_INGOT.get(), "titanium_gold", grinder);
        Advancement hot_isostatic_press = itemTask(POMblocks.HOT_ISOSTATIC_PRESS.get(), "hot_isostatic_press", titanium_gold);
        Advancement titanium_diboride = itemTask(POMitems.TITANIUM_DIBORIDE_INGOT.get(), "titanium_diboride", hot_isostatic_press);
        Advancement chemical_seperator = itemTask(POMblocks.CHEMICAL_SEPARATOR.get(), "chemical_seperator", titanium_diboride);
        Advancement chemical_combiner = itemTask(POMblocks.CHEMICAL_COMBINER.get(), "chemical_combiner", titanium_diboride);
        Advancement chemical_mixer = itemTask(POMblocks.CHEMICAL_MIXER.get(), "chemical_mixer", titanium_diboride);
        Advancement royal_tungsten = itemTask(POMitems.ROYAL_TUNGSTEN_INGOT.get(), "royal_tungsten", chemical_combiner);
        Advancement yellowcake_uranium = itemTask(POMitems.YELLOWCAKE_URANIUM.get(), "yellowcake_uranium", chemical_seperator);
        Advancement mercury = itemTask(POMitems.MERCURY_BUCKET.get(), "mercury", chemical_seperator);
        Advancement uranium_fuel_cell = itemTask(POMitems.URANIUM_FUEL_CELL.get(), "uranium_fuel_cell", yellowcake_uranium);
        Advancement nuclear_waste_bucket = itemTask(POMitems.NUCLEAR_WASTE_BUCKET.get(), "nuclear_waste_bucket", uranium_fuel_cell);
        Advancement enriched_uranium_fuel_cell = itemTask(POMitems.ENRICHED_URANIUM_FUEL_CELL.get(), "enriched_uranium_fuel_cell", uranium_fuel_cell);
        Advancement purex_solution = itemTask(POMitems.PUREX_SOLUTION_BUCKET.get(), "purex_solution", nuclear_waste_bucket);
        Advancement plutonium_fuel_cell = itemTask(POMitems.PLUTONIUM_FUEL_CELL.get(), "plutonium_fuel_cell", purex_solution);
        Advancement enriched_plutonium_hexafluoride_dust = itemTask(POMitems.ENRICHED_PLUTONIUM_HEXAFLUORIDE_DUST.get(), "enriched_plutonium_hexafluoride_dust", purex_solution);
        Advancement enriched_plutonium_fuel_cell = itemTask(POMitems.ENRICHED_PLUTONIUM_FUEL_CELL.get(), "enriched_plutonium_fuel_cell", enriched_plutonium_hexafluoride_dust);


        Advancement bio_compound = itemTask(POMitems.BIO_COMPOUND.get(), "bio_compound", root);
        Advancement power_orb = itemTask(POMitems.POWER_ORB.get(), "power_orb", bio_compound);
        Advancement power_cell = itemTask(POMitems.POWER_CELL.get(), "power_cell", power_orb);
        Advancement overcharged = itemTask(POMitems.OVERCHARGED_POWER_ORB.get(), "overcharged", power_cell);
        Advancement supercharged = itemTask(POMitems.SUPERCHARGED_POWER_ORB.get(), "supercharged", overcharged);
        Advancement fire_proof_compound = itemTask(POMitems.FIRE_PROOF_COMPOUND.get(), "fire_proof_compound", bio_compound);
        Advancement repelling_compound = itemTask(POMitems.REPELLING_COMPOUND.get(), "repelling_compound", fire_proof_compound);


    }

    private Advancement.Builder create() {
        return Advancement.Builder.advancement();
    }

    private Advancement.Builder create(ItemLike item, String id, FrameType frame, boolean hidden) {
        return Advancement.Builder.advancement()
                .display(item, Component.translatable(String.format("advancements.pixelsofmc.%s.title", id)), Component.translatable(String.format("advancements.pixelsofmc.%s.description", id)), null, frame, true, true, hidden)
                .requirements(RequirementsStrategy.OR);
    }

    private Advancement itemTask(ItemLike item, String id, Advancement parent) {
        return createTask(item, id).parent(parent)
                .addCriterion(id, InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(consumer, id(id), existingFileHelper);
    }

    private Advancement itemListTask(String id, Advancement parent, ItemLike... items) {
        return createTask(items[0], id).parent(parent)
                .addCriterion(id, InventoryChangeTrigger.TriggerInstance.hasItems(items))
                .save(consumer, id(id), existingFileHelper);
    }

    private Advancement itemGoalTask(String id, Advancement parent, ItemLike... items) {
        return createGoal(items[0], id).parent(parent)
                .addCriterion(id, InventoryChangeTrigger.TriggerInstance.hasItems(items))
                .save(consumer, id(id), existingFileHelper);
    }

    private Advancement.Builder createTask(ItemLike item, String id) {
        return create(item, id, FrameType.TASK, false);
    }

    private Advancement.Builder createHiddenTask(ItemLike item, String id) {
        return create(item, id, FrameType.TASK, true);
    }

    private Advancement.Builder createGoal(ItemLike item, String id) {
        return create(item, id, FrameType.GOAL, false);
    }

    private Advancement.Builder createHiddenGoal(ItemLike item, String id) {
        return create(item, id, FrameType.GOAL, true);
    }

    private Advancement.Builder createChallenge(ItemLike item, String id) {
        return create(item, id, FrameType.CHALLENGE, false);
    }

    private Advancement.Builder createHiddenChallenge(ItemLike item, String id) {
        return create(item, id, FrameType.GOAL, true);
    }


    private Advancement.Builder createBase(ItemLike item, String id, ResourceLocation background) {
        PixelsOfMc.LOGGER.info("base id:{}", id(id));
        return Advancement.Builder.advancement()
                .display(item, Component.translatable(String.format("advancements.pixelsofmc.%s.title", id)), Component.translatable(String.format("advancements.pixelsofmc.%s.description", id)), background, FrameType.TASK, false, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("minecraft:inventory_changed", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY));
    }

    private ResourceLocation id(String id) {
        return Util.resourceLocation(id);
    }
}
