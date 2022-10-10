package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    public POMrecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapelessRecipeBuilder.shapeless(POMitems.RAW_TITANIUM.get(), 9)
                .requires(POMblocks.RAW_TITANIUM_BLOCK.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.RAW_TITANIUM_BLOCK.get())
                .define('E', POMitems.RAW_TITANIUM.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_INGOT.get(), 9)
                .requires(POMblocks.TITANIUM_BLOCK.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .group("titanium_ingot")
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.TITANIUM_BLOCK.get())
                .define('T', POMitems.TITANIUM_INGOT.get())
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_NUGGET.get(), 9)
                .requires(POMitems.TITANIUM_INGOT.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.TITANIUM_INGOT.get())
                .define('T', POMitems.TITANIUM_NUGGET.get())
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .group("titanium_ingot")
                .save(pFinishedRecipeConsumer, POMitems.TITANIUM_INGOT.getId() + "_from_nugget");

        ShapedRecipeBuilder.shaped(POMitems.DENSE_CARBON_CUBE.get())
                .define('C', POMitems.CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.NETHERITE_NUGGET.get(), 9)
                .requires(Items.NETHERITE_INGOT)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(Items.NETHERITE_INGOT)
                .define('N', POMitems.NETHERITE_NUGGET.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.STEEL_NUGGET.get(), 9)
                .requires(POMitems.STEEL_INGOT.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.STEEL_INGOT.get())
                .define('T', POMitems.STEEL_NUGGET.get())
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer, POMitems.STEEL_INGOT.getId() + "_from_nugget");

        ShapelessRecipeBuilder.shapeless(POMitems.SILVER_NUGGET.get(), 9)
                .requires(POMitems.SILVER_INGOT.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.SILVER_INGOT.get())
                .define('N', POMitems.SILVER_NUGGET.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), 9)
                .requires(POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('T', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer, POMitems.TITANIUM_DIBORIDE_INGOT.getId() + "_from_nugget");



        ShapedRecipeBuilder.shaped(POMitems.HAMMER.get())
                .define('A', Items.NETHERITE_INGOT)
                .define('B', Items.STICK)
                .pattern("A")
                .pattern("B")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.SCREWDRIVER.get())
                .define('A', POMitems.BIO_PLASTIC_SHEET.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern("B")
                .pattern("B")
                .pattern("A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.WIRECUTTER.get())
                .define('A', POMitems.BIO_PLASTIC_SHEET.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern(" B ")
                .pattern("A A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.CLEANING_CLOTH.get())
                .requires(ItemTags.CARPETS)
                .requires(Items.STRING)
                .requires(Items.STRING)
                .requires(Items.BLUE_DYE)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMitems.TITANIUM_CIRCLE_SAW.get())
                .define('A', POMitems.TITANIUM_NUGGET.get())
                .define('B', POMitems.TITANIUM_INGOT.get())
                .define('C', POMitems.TITANIUM_PLATING.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get())
                .define('A', POMitems.TITANIUM_NUGGET.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('C', POMitems.REFINED_HEAT_RESISTANT_PLATING.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_PLATING.get())
                .requires(POMitems.TITANIUM_INGOT.get())
                .requires(POMitems.TITANIUM_INGOT.get())
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_GEAR.get())
                .requires(POMitems.TITANIUM_PLATING.get())
                .requires(POMitems.TITANIUM_PLATING.get())
                .requires(POMitems.HAMMER.get())
                .requires(POMitems.WIRECUTTER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.MOVING_PARTS.get())
                .requires(POMitems.TITANIUM_PLATING.get())
                .requires(POMitems.TITANIUM_GEAR.get())
                .requires(POMitems.TITANIUM_GEAR.get())
                .requires(POMitems.COPPER_WIRE.get())
                .requires(POMitems.COPPER_WIRE.get())
                .requires(Items.REDSTONE)
                .requires(Items.IRON_INGOT)
                .requires(POMitems.SCREWDRIVER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMitems.COPPER_WIRE.get(), 2)
                .define('A', POMitems.COPPER_NUGGET.get())
                .define('B', Items.STICK)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.SILVER_WIRE.get(), 2)
                .define('A', POMitems.SILVER_NUGGET.get())
                .define('B', Items.STICK)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.REDSTONE_COUNTER.get())
                .define('A', POMitems.TITANIUM_NUGGET.get())
                .define('B', Items.GOLD_NUGGET)
                .define('C', Items.REDSTONE_BLOCK)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ENDER_SENSOR.get())
                .define('A', POMitems.TITANIUM_NUGGET.get())
                .define('B', POMitems.COPPER_NUGGET.get())
                .define('C', Items.ENDER_EYE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DRAGON_SENSOR.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
            .define('B', POMitems.SILVER_NUGGET.get())
                .define('C', POMitems.DRAGON_EYE.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DIAMOND_LENS.get())
                .define('A', POMitems.TITANIUM_NUGGET.get())
                .define('B', Items.DIAMOND)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.VIOLET_DIAMOND_LENS.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', Items.DIAMOND)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.REDSTONE_LAYERED_WIRE.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DRAGON_EYE.get())
                .define('B', Items.ENDER_EYE)
                .define('C', Items.DRAGON_BREATH)
                .define('D', Items.NETHER_STAR)
                .pattern(" C ")
                .pattern("BDB")
                .pattern(" C ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.POWER_ORB.get())
                .define('A', POMitems.BIO_COMPOUND.get())
                .define('B', Items.REDSTONE)
                .define('C', Items.NETHER_STAR)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.MICRO_CHIP.get())
                .define('A', POMitems.NETHERITE_NUGGET.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', Items.IRON_NUGGET)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DDD")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC_SHEET.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', Items.GOLD_NUGGET)
                .pattern("EDE")
                .pattern("AAA")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC_SHEET.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.REDSTONE_LAYERED_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', POMitems.TITANIUM_NUGGET.get())
                .define('F', POMitems.REDSTONE_COUNTER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.PERFECTED_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC_SHEET.get())
                .define('B', POMitems.DIAMOND_LENS.get())
                .define('C', POMitems.REDSTONE_LAYERED_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', POMitems.COPPER_WIRE.get())
                .define('F', POMitems.ENDER_SENSOR.get())
                .define('G', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMblocks.SIMPLE_CASING_1.get())
                .define('A', Items.NETHERITE_INGOT)
                .define('B', POMitems.TITANIUM_INGOT.get())
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.ADVANCED_CASING_1.get())
                .define('A', POMblocks.SIMPLE_CASING_1.get())
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.REDSTONE)
                .define('D', Items.GOLD_INGOT)
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.PERFECTED_CASING_1.get())
                .define('A', POMblocks.ADVANCED_CASING_1.get())
                .define('B', POMitems.TITANIUM_PLATING.get())
                .define('C', Items.DIAMOND)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .define('E', POMitems.BIO_PLASTIC_SHEET.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BEB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);






        //furnace


    }
}