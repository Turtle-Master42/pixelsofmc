package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.HashMap;
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
                .requires(Items.SLIME_BALL)
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
                .requires(Items.ENDER_PEARL)
                .requires(Items.ENDER_PEARL)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);



        ShapedRecipeBuilder.shaped(POMitems.DENSE_CARBON_CUBE.get())
                .define('C', POMitems.CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);





        ShapedRecipeBuilder.shaped(POMitems.HAMMER.get())
                .define('A', Items.NETHERITE_INGOT)
                .define('B', Items.STICK)
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
                .define('C', POMitems.TITANIUM_DIBORIDE_PLATING.get())
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
                .define('E', POMitems.TITANIUM_NUGGET.get())
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
                .define('C', Items.DIAMOND)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .define('E', POMitems.BIO_PLASTIC.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BEB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        SimpleSurroundRecipe(POMitems.TITANIUM_NUGGET.get(), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), Items.DIAMOND, POMitems.VIOLET_DIAMOND_LENS.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(POMitems.COPPER_NUGGET.get(), Items.STICK, POMitems.COPPER_WIRE.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(POMitems.SILVER_NUGGET.get(), Items.STICK, POMitems.SILVER_WIRE.get(), pFinishedRecipeConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_INGOT.get(), Items.NETHERITE_INGOT, POMblocks.SIMPLE_CASING_1.get(), pFinishedRecipeConsumer);

        //compacting

        SimpleMetalCompactingRecipe(POMitems.TITANIUM_NUGGET.get(), POMitems.TITANIUM_INGOT.get(), POMblocks.TITANIUM_BLOCK.get(), pFinishedRecipeConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), pFinishedRecipeConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), pFinishedRecipeConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, pFinishedRecipeConsumer);
        SimpleCompactingRecipe(POMitems.COPPER_NUGGET.get(), Items.COPPER_INGOT, pFinishedRecipeConsumer);
        SimpleCompactingRecipe(POMitems.SILVER_NUGGET.get(), POMitems.SILVER_INGOT.get(), pFinishedRecipeConsumer);
        SimpleCompactingRecipe(POMitems.STEEL_NUGGET.get(), POMitems.STEEL_INGOT.get(), pFinishedRecipeConsumer);

        //Furnace

        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), POMitems.TITANIUM_INGOT.get(), 0.5f, 10 , pFinishedRecipeConsumer, "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), POMitems.TITANIUM_INGOT.get(), 0.5f, 10 , pFinishedRecipeConsumer, "_from_ore");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 10 , pFinishedRecipeConsumer, "");


    }

    private void SimpleFurnaceRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output+extra));
    }

    //Immersive Engineering
    private void SimpleSmeltingRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output+extra));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), output, xp, smeltingTime/2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output+extra+"_from_blasting"));
    }

    private void SimpleCompactingRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shaped(output)
                .define('T', input)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + input.asItem() + "_to_" + output.asItem() + "_compacting" ));
        ShapelessRecipeBuilder.shapeless(input, 9)
                .requires(output)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + output.asItem() + "_to_" + input.asItem() + "_compacting" ));

    }

    private void SimpleMetalCompactingRecipe(ItemLike nugget, ItemLike ingot, ItemLike block, Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shaped(ingot, 1)
                .define('T', nugget)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + nugget + "_to_" + ingot + "_compacting"));
        ShapedRecipeBuilder.shaped(block, 1)
                .define('T', ingot)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + ingot + "_to_" + block.asItem()  + "_compacting"));
        ShapelessRecipeBuilder.shapeless(nugget, 9)
                .requires(ingot)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + ingot + "_to_" + nugget + "_compacting"));
        ShapelessRecipeBuilder.shapeless(ingot, 9)
                .requires(block)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + block.asItem() + "_to_" + ingot + "_compacting"));

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