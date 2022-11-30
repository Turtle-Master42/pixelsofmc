package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.recipe.BallMillRecipeBuilder;
import net.turtlemaster42.pixelsofmc.recipe.DamageToolRecipe;
import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipeBuilder;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final HashMap<String, Integer> PATH_COUNT = new HashMap<>();
    public POMrecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapelessRecipeBuilder.shapeless(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.SUGAR_CANE)
                .requires(Items.HONEYCOMB)
                .requires(Items.WARPED_ROOTS)
                .requires(Items.WARPED_ROOTS)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Tags.Items.EGGS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.FIRE_PROOF_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get())
                .requires(POMitems.BIO_COMPOUND.get())
                .requires(Items.CRIMSON_FUNGUS)
                .requires(Items.WEEPING_VINES)
                .requires(Items.CRIMSON_ROOTS)
                .requires(Items.NETHER_WART)
                .requires(Items.NETHER_WART)
                .requires(Items.MAGMA_CREAM)
                .requires(Items.MAGMA_CREAM)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.REPELLING_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get())
                .requires(POMitems.BIO_COMPOUND.get())
                .requires(Items.CHORUS_FLOWER)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(Tags.Items.ENDER_PEARLS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);



        ShapedRecipeBuilder.shaped(POMitems.DENSE_CARBON_CUBE.get())
                .define('C', elementItem(Element.CARBON))
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);





        ShapedRecipeBuilder.shaped(POMitems.HAMMER.get())
                .define('A', Items.NETHERITE_INGOT)
                .define('B', Tags.Items.RODS_WOODEN)
                .pattern("A")
                .pattern("B")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.SCREWDRIVER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern("B")
                .pattern("B")
                .pattern("A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.WIRECUTTER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern(" B ")
                .pattern("A A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.CLEANING_CLOTH.get())
                .requires(ItemTags.CARPETS)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);



        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_PLATING.get())
                .requires(ingotTag(Element.TITANIUM))
                .requires(ingotTag(Element.TITANIUM))
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.NETHERITE_PLATING.get())
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.NETHERITE_INGOT)
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



        ShapedRecipeBuilder.shaped(POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
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
                .define('D', Tags.Items.NETHER_STARS)
                .pattern(" C ")
                .pattern("BDB")
                .pattern(" C ")
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
        ShapedRecipeBuilder.shaped(POMitems.POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ENDER_SENSOR.get())
                .define('A', nuggetItem(Element.TITANIUM))
                .define('B', nuggetTag(Element.COPPER))
                .define('C', Items.ENDER_EYE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DRAGON_SENSOR.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', nuggetItem(Element.SILVER))
                .define('C', POMitems.DRAGON_EYE.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.MOVING_PARTS.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .define('D', POMitems.TITANIUM_GEAR.get())
                .define('E', Tags.Items.INGOTS_IRON)
                .pattern("ACA")
                .pattern("ADA")
                .pattern("BEB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMitems.SPEED_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', nuggetTag(Element.COPPER))
                .define('C', dustTag(Element.GOLD))
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ENERGY_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', nuggetTag(Element.COPPER))
                .define('C', dustTag(Element.ALUMINIUM))
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(POMblocks.GRINDER.get())
                .define('A', POMitems.MOVING_PARTS.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMblocks.TITANIUM_BLOCK.get())
                .define('D', Items.NETHERITE_INGOT)
                .define('E', POMblocks.SIMPLE_CASING_1.get())
                .define('F', POMblocks.ADVANCED_CASING_1.get())
                .pattern("ADA")
                .pattern("BEB")
                .pattern("CFC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.BALL_MILL.get())
                .define('A', POMitems.MOVING_PARTS.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMblocks.TITANIUM_BLOCK.get())
                .define('D', ingotTag(Element.TITANIUM))
                .define('E', POMblocks.SIMPLE_CASING_1.get())
                .define('F', POMblocks.ADVANCED_CASING_1.get())
                .pattern("DBD")
                .pattern("EEA")
                .pattern("CFC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);




        ShapedRecipeBuilder.shaped(POMitems.ADVANCED_LASER.get())
                .define('A', POMblocks.STRONG_CASING.get())
                .define('B', POMitems.POWER_CELL.get())
                .define('C', Items.COPPER_INGOT)
                .define('D', Items.BEACON)
                .define('E', POMitems.NETHERITE_PLATING.get())
                .define('F', POMitems.POWER_ORB.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("EFE")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC.get())
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
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', nuggetItem(Element.TITANIUM))
                .define('F', POMitems.REDSTONE_COUNTER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(POMitems.PERFECTED_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.DIAMOND_LENS.get())
                .define('C', POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', POMitems.COPPER_WIRE.get())
                .define('F', POMitems.ENDER_SENSOR.get())
                .define('G', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
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
                .define('C', Items.DIAMOND_BLOCK)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);


        //milling
        BallMill(toIngredient(POMitems.MERCURY_SULFIDE_DUST.get()), 1, toIngredient(POMitems.TITANIUM_DIBORIDE_DUST.get()), 1, toIngredient(POMitems.TITANIUM_OXIDE_DUST.get()), 3, POMitems.ANCIENT_DEBRIS_DUST.get(), 3, POMtags.Items.BALL_5, pFinishedRecipeConsumer);
        BallMill(toIngredient(dustTag(Element.GOLD)), 2, toIngredient(POMitems.ANCIENT_DEBRIS_DUST.get()), 2, POMitems.NETHERITE_DUST.get(), 1, POMtags.Items.BALL_4, pFinishedRecipeConsumer);
        BallMill(toIngredient(dustTag(Element.BORON)), 2, toIngredient(dustTag(Element.TITANIUM)), 1, POMitems.TITANIUM_DIBORIDE_DUST.get(), 2, POMtags.Items.BALL_4, pFinishedRecipeConsumer);

        //pixel splitter
        PixelSplitting(Items.GOLD_INGOT, POMitems.PIXEL.get(), 8, 253, 245, 95, pFinishedRecipeConsumer);


        //ez crafting
        SimpleSurroundRecipe(nuggetItem(Element.TITANIUM), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), Items.DIAMOND, POMitems.VIOLET_DIAMOND_LENS.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(nuggetItem(Element.COPPER), Items.STICK, POMitems.COPPER_WIRE.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(nuggetItem(Element.SILVER), Items.STICK, POMitems.SILVER_WIRE.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(elementItem(Element.TITANIUM), Items.NETHERITE_BLOCK, POMblocks.SIMPLE_CASING_1.get(), pFinishedRecipeConsumer);

        SimpleFullCrossRecipe(Items.OBSIDIAN, POMitems.NETHERITE_PLATING.get(), Items.NETHERITE_BLOCK, POMblocks.STRONG_CASING.get(), pFinishedRecipeConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRONG_CASING.get(), POMblocks.REINFORCED_CASING.get(), pFinishedRecipeConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, Items.NETHER_STAR, POMitems.POWER_ORB.get(), pFinishedRecipeConsumer);
        SimpleFullCrossRecipe(nuggetItem(Element.TITANIUM), Items.GOLD_INGOT, Items.REDSTONE_BLOCK, POMitems.REDSTONE_COUNTER.get(), pFinishedRecipeConsumer);
        SimpleFullCrossRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMitems.TITANIUM_PLATING.get(), POMitems.TITANIUM_CIRCLE_SAW.get(), pFinishedRecipeConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get(), pFinishedRecipeConsumer);

        SimpleCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_COMPOUND.get(), POMitems.RUBBER_BALL.get(), pFinishedRecipeConsumer);
        SimpleCrossRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), pFinishedRecipeConsumer);
        SimpleCrossRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_RUBBER_BALL.get(), pFinishedRecipeConsumer);
        SimpleCrossRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, POMitems.NETHERITE_BALL.get(), pFinishedRecipeConsumer);
        SimpleCrossRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMitems.TITANIUM_BALL.get(), pFinishedRecipeConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), pFinishedRecipeConsumer);

        //compacting

        SimpleMetalCompactingRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMblocks.TITANIUM_BLOCK.get(), pFinishedRecipeConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), pFinishedRecipeConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), pFinishedRecipeConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, pFinishedRecipeConsumer);
        SimpleCompactingRecipe(nuggetItem(Element.COPPER), Items.COPPER_INGOT, pFinishedRecipeConsumer);
        SimpleCompactingRecipe(nuggetItem(Element.SILVER), elementItem(Element.SILVER), pFinishedRecipeConsumer);

        //Furnace

        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), elementItem(Element.TITANIUM), 0.5f, 10 , pFinishedRecipeConsumer, "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), elementItem(Element.TITANIUM), 0.5f, 10 , pFinishedRecipeConsumer, "_from_ore");
        SimpleSmeltingRecipe(POMitems.RUSTED_PLATING.get(), Items.NETHERITE_INGOT, 0.5f, 10, pFinishedRecipeConsumer, "");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");
    }

    private void SimpleFurnaceRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleFurnaceRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleFurnaceRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output+extra));
    }

    //Immersive Engineering
    private void SimpleSmeltingRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleSmeltingRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleSmeltingRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
        SimpleCookingRecipeBuilder.blasting(input, output, xp, smeltingTime/2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output.asItem()+extra+"_from_blasting"));
    }


    private void SimpleCompactingRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        SimpleCompactingRecipe(Ingredient.of(input),output,consumer);
    }
    private void SimpleCompactingRecipe(TagKey<Item> input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        SimpleCompactingRecipe(Ingredient.of(input),output,consumer);

    }
    private void SimpleCompactingRecipe(Ingredient input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(output)
                .define('T', input)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + input.getItems()[0].getItem() + "_to_" + output.asItem() + "_compacting" ));
        ShapelessRecipeBuilder.shapeless(input.getItems()[0].getItem(), 9)
                .requires(output)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + output.asItem() + "_to_" + input.getItems()[0].getItem() + "_compacting" ));

    }

    private void SimpleMetalCompactingRecipe(ItemLike nugget, ItemLike ingot, ItemLike block, Consumer<FinishedRecipe> consumer) {
        SimpleMetalCompactingRecipe(Ingredient.of(nugget), Ingredient.of(ingot), block, consumer);
    }
    private void SimpleMetalCompactingRecipe(TagKey<Item> nugget, TagKey<Item> ingot, ItemLike block, Consumer<FinishedRecipe> consumer) {
        SimpleMetalCompactingRecipe(Ingredient.of(nugget), Ingredient.of(ingot), block, consumer);
    }
    private void SimpleMetalCompactingRecipe(Ingredient nugget, Ingredient ingot, ItemLike block, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ingot.getItems()[0].getItem(), 1)
                .define('T', nugget)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+nugget.getItems()[0].getItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));
        ShapedRecipeBuilder.shaped(block, 1)
                .define('T', ingot)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+block.asItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(nugget.getItems()[0].getItem(), 9)
                .requires(ingot)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+nugget.getItems()[0].getItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(ingot.getItems()[0].getItem(), 9)
                .requires(block)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+block.asItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));

    }

    private void SimpleSurroundRecipe(ItemLike around, ItemLike middle, ItemLike output, Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shaped(output)
                .define('A', around)
                .define('B', middle)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shaped(output)
                .define('A', corners)
                .define('B', sides)
                .define('C', middle)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void SimpleCrossRecipe(ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shaped(output)
                .define('A', sides)
                .define('B', middle)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void BallMill(Ingredient input1, int count1, Ingredient input2, int count2, ItemLike output, int outputCount, TagKey<Item> ball, Consumer<FinishedRecipe> consumer) {
        BallMill(input1, count1, input2, count2, Ingredient.EMPTY, 0, output, outputCount, ball, consumer);
    }
    private void BallMill(Ingredient input1, int count1, ItemLike output, int outputCount, TagKey<Item> ball, Consumer<FinishedRecipe> consumer) {
        BallMill(input1, count1, Ingredient.EMPTY, 0, Ingredient.EMPTY, 0, output, outputCount, ball, consumer);
    }
    private void BallMill(Ingredient input1, int count1, Ingredient input2, int count2, Ingredient input3, int count3, ItemLike output, int outputCount, TagKey<Item> ball, Consumer<FinishedRecipe> consumer) {
        if (input2.isEmpty() && input3.isEmpty())
            new BallMillRecipeBuilder(List.of(CountedIngredient.of(count1, input1)), output, outputCount, Ingredient.of(ball))
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
        else if (input3.isEmpty())
            new BallMillRecipeBuilder(List.of(CountedIngredient.of(count1, input1), CountedIngredient.of(count2, input2)), output, outputCount, Ingredient.of(ball))
                    .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                    .save(consumer);
        else
            new BallMillRecipeBuilder(List.of(CountedIngredient.of(count1, input1), CountedIngredient.of(count2, input2), CountedIngredient.of(count3, input3)), output, outputCount, Ingredient.of(ball))
                    .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                    .save(consumer);
    }




    private void PixelSplitting(ItemLike ingredient, ItemLike output, int count, int r, int g, int b, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), CountedIngredient.of(count, output), r,g,b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private TagKey<Item> ingotTag(Element element) {
        return POMtags.getTagsFor(element).metal;
    }
    private TagKey<Item> dustTag(Element element) {
        return POMtags.getTagsFor(element).dust;
    }
    private TagKey<Item> nuggetTag(Element element) {
        return POMtags.getTagsFor(element).nugget;
    }
    private TagKey<Item> elementTag(Element element) {
        return POMtags.getTagsFor(element).other;
    }

    private ItemLike elementItem(Element element) {
        return POMitems.Metals.ELEMENTS.get(element);
    }
    private ItemLike dustItem(Element element) {
        return POMitems.Metals.DUSTS.get(element);
    }
    private ItemLike nuggetItem(Element element) {
        return POMitems.Metals.NUGGETS.get(element);
    }

    private Ingredient toIngredient(ItemLike input) {
        return Ingredient.of(input);
    }
    private Ingredient toIngredient(TagKey<Item> input) {
        return Ingredient.of(input);
    }



    //Immersive Engineering
    private ResourceLocation toRL(String string)
    {
        if(!string.contains("/"))
            string = "crafting/"+string;
        if(PATH_COUNT.containsKey(string))
        {
            int count = PATH_COUNT.get(string)+1;
            PATH_COUNT.put(string, count);
            return new ResourceLocation(PixelsOfMc.MOD_ID, string+count);
        }
        PATH_COUNT.put(string, 1);
        return new ResourceLocation(PixelsOfMc.MOD_ID, string);
    }
}