package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.turtlemaster42.pixelsofmc.recipe.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    public POMrecipeProvider(PackOutput output) {
        super(output);
    }

    private final HashMap<String, Integer> PATH_COUNT = new HashMap<>();

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> fConsumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.SUGAR_CANE)
                .requires(Items.HONEYCOMB)
                .requires(Items.WARPED_ROOTS, 2)
                .requires(Items.KELP)
                .requires(Items.WARPED_FUNGUS)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Tags.Items.EGGS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.FIRE_PROOF_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CRIMSON_FUNGUS)
                .requires(Items.WEEPING_VINES)
                .requires(Items.CRIMSON_ROOTS)
                .requires(Items.NETHER_WART, 2)
                .requires(Items.MAGMA_CREAM, 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.REPELLING_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CHORUS_FLOWER)
                .requires(Items.CHORUS_FRUIT, 2)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(Tags.Items.ENDER_PEARLS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DENSE_CARBON_CUBE.get())
                .define('C', Element.CARBON.item())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.CARBONARO_CLUMP.get())
                .define('C', POMitems.DENSE_CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.BLACK_DIAMOND.get())
                .define('C', POMitems.CARBONARO_CLUMP.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND)
                .define('C', POMitems.BLACK_DIAMOND.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(Items.DIAMOND.toString()));
//        ShapedRecipeBuilder.shaped(POMitems.VIOLET_DIAMOND.get())
//                .define('C', Items.DIAMOND)
//                .pattern("CC")
//                .pattern("CC")
//                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
//                .save(fConsumer);
//        ShapedRecipeBuilder.shaped(POMitems.RED_DIAMOND.get())
//                .define('C', POMitems.VIOLET_DIAMOND.get())
//                .pattern("CC")
//                .pattern("CC")
//                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
//                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, POMitems.SCREWDRIVER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern("B")
                .pattern("B")
                .pattern("A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, POMitems.WIRECUTTER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern(" B ")
                .pattern("A A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, POMitems.CLEANING_CLOTH.get())
                .requires(ItemTags.WOOL_CARPETS)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.TITANIUM_PLATING.get())
                .requires(Element.TITANIUM.itemTag())
                .requires(Element.TITANIUM.itemTag())
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.NETHERITE_PLATING.get())
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.NETHERITE_INGOT)
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_IMBUED_SILVER_WIRE.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.SILVER_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.RED_TUNGSTEN_WIRE.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.TUNGSTEN_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DRAGON_EYE.get())
                .define('B', Items.ENDER_EYE)
                .define('C', Items.DRAGON_BREATH)
                .define('D', Tags.Items.NETHER_STARS)
                .pattern(" C ")
                .pattern("BDB")
                .pattern(" C ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.MICRO_CHIP.get())
                .define('A', POMitems.NETHERITE_NUGGET.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', Items.IRON_NUGGET)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DDD")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENDER_SENSOR.get())
                .define('A', Element.TITANIUM.nuggetTag())
                .define('B', Element.COPPER.nuggetTag())
                .define('C', Items.ENDER_EYE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DRAGON_SENSOR.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', Element.SILVER.nugget())
                .define('C', POMitems.DRAGON_EYE.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.MOVING_PARTS.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .define('D', Items.DRIED_KELP)
                .define('E', Tags.Items.INGOTS_IRON)
                .pattern("ACA")
                .pattern("ADA")
                .pattern("BEB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SPEED_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.TITANIUM.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENERGY_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.GOLD.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.HEAT_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.COBALT.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.GRINDER.get())
                .define('A', POMitems.MOVING_PARTS.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', Element.TITANIUM.block())
                .define('D', Items.NETHERITE_INGOT)
                .define('E', POMblocks.SIMPLE_CASING_1.get())
                .define('F', POMblocks.ADVANCED_CASING_1.get())
                .pattern("ADA")
                .pattern("BEB")
                .pattern("CFC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.BALL_MILL.get())
                .define('A', POMitems.MOVING_PARTS.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', Element.TITANIUM.block())
                .define('D', Element.TITANIUM.itemTag())
                .define('E', POMblocks.SIMPLE_CASING_1.get())
                .define('F', POMblocks.ADVANCED_CASING_1.get())
                .pattern("DBD")
                .pattern("EEA")
                .pattern("CFC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.HOT_ISOSTATIC_PRESS.get())
                .define('A', Items.PISTON)
                .define('B', Items.REDSTONE)
                .define('C', POMitems.TITANIUM_PLATING.get())
                .define('D', POMblocks.SIMPLE_CASING_1.get())
                .define('E', POMblocks.PERFECTED_CASING_1.get())
                .define('F', Items.BLAST_FURNACE)
                .pattern("CDC")
                .pattern("ADA")
                .pattern("BEF")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);




        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ADVANCED_LASER.get())
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
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', Items.GOLD_NUGGET)
                .pattern("EDE")
                .pattern("AAA")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', Element.TITANIUM.nugget())
                .define('F', POMitems.REDSTONE_COUNTER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.PERFECTED_CIRCUIT_BOARD_1.get())
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
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.ADVANCED_CASING_1.get())
                .define('A', POMblocks.SIMPLE_CASING_1.get())
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.REDSTONE)
                .define('D', Items.GOLD_INGOT)
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.PERFECTED_CASING_1.get())
                .define('A', POMblocks.ADVANCED_CASING_1.get())
                .define('B', POMitems.TITANIUM_PLATING.get())
                .define('C', Items.DIAMOND_BLOCK)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);


        //grinding
        Grinder(toI(Items.FLINT), fConsumer, toCHI(Element.CALCIUM.dustTag(), 1, 0.7f));
        Grinder(toI(Items.NETHERITE_SCRAP), fConsumer, toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 1f));
        Grinder(toI(Items.ANCIENT_DEBRIS), fConsumer, toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 1f), toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 0.5f));
        Grinder(toI(Items.TUFF), fConsumer, toCHI(Element.SULFUR.dustTag(), 1, 0.30f), toCHI(Element.LEAD.dustTag(), 1, 0.05f), toCHI(POMitems.MINERAL_GRIT.get(), 1, 0.2f), toCHI(Element.CARBON.dustTag(), 1, 0.2f));
        Grinder(toI(Items.NETHERRACK), fConsumer, toCHI(POMitems.MERCURY_SULFIDE_DUST.get(), 2, 0.9f), toCHI(POMitems.MERCURY_SULFIDE_DUST.get(), 1, 0.2f), toCHI(Element.GOLD.dustTag(), 1, 0.2f), toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.COBALT.dustTag(), 1, 0.05f));
        Grinder(toI(Items.END_STONE), fConsumer, toCHI(Element.POTASSIUM.dustTag(), 2, 0.60f), toCHI(Element.TITANIUM.dustTag(), 1, 0.75f), toCHI(Element.SILICON.dustTag(), 1, 0.65f));
        Grinder(toI(Items.BLACKSTONE), fConsumer, toCHI(Element.BORON.dustTag(), 1, 0.8f), toCHI(Element.NICKEL.dustTag(), 1, 0.1f), toCHI(Element.PLATINUM.dustTag(), 1, 0.01f), toCHI(Element.COBALT.dustTag(), 1, 0.08f));
        Grinder(toI(Items.ANDESITE), fConsumer, toCHI(Element.ALUMINIUM.dustTag(), 1, 0.2f));
        Grinder(toI(Items.BASALT), fConsumer, toCHI(Element.IRON.dustTag(), 2, 0.7f), toCHI(Element.GOLD.dustTag(), 1, 0.1f));
        Grinder(toI(Items.DIORITE), fConsumer, toCHI(Element.SODIUM.dustTag(), 1, 0.3f));
        Grinder(toI(Items.GRANITE), fConsumer, toCHI(Element.TUNGSTEN.dustTag(), 1, 0.015f), toCHI(Element.MOLYBDENUM.dustTag(), 1, 0.015f));
        Grinder(toI(POMblocks.ACANTHITE.get()), fConsumer, toCHI(POMitems.ACANTHITE_DUST.get(), 1, 1f), toCHI(POMitems.ACANTHITE_DUST.get(), 1, 0.2f), toCHI(Element.SILICON.dustTag(), 1, 0.4f), toCHI(Element.SILVER.dustTag(), 1, 0.05f));

        Grinder(toI(Items.RAW_IRON), fConsumer, toCHI(Element.IRON.dust(), 1, 1f), toCHI(Element.IRON.dust(), 1, 0.5f));
        Grinder(toI(Items.RAW_COPPER), fConsumer, toCHI(Element.COPPER.dust(), 1, 1f), toCHI(Element.COPPER.dust(), 1, 0.5f), toCHI(Element.SULFUR.dust(), 1, 0.1f));
        Grinder(toI(Items.RAW_GOLD), fConsumer, toCHI(Element.GOLD.dust(), 1, 1f), toCHI(Element.GOLD.dust(), 1, 0.5f));
        Grinder(toI(POMitems.RAW_TITANIUM.get()), fConsumer, toCHI(Element.TITANIUM.dust(), 1, 1f), toCHI(Element.TITANIUM.dust(), 1, 0.5f), toCHI(Element.IRON.dust(), 1, 0.5f));

        Grinder(toI(Tags.Items.ORES_IRON), fConsumer, toCHI(Element.IRON.dust(), 3, 1f), toCHI(Element.IRON.dust(), 2, 0.5f));
        Grinder(toI(Tags.Items.ORES_COPPER), fConsumer, toCHI(Element.COPPER.dust(), 10, 1f), toCHI(Element.COPPER.dust(), 4, 0.5f), toCHI(Element.COPPER.dust(), 2, 0.25f), toCHI(Element.SULFUR.dust(), 3, 0.5f), toCHI(Element.SULFUR.dust(), 1, 0.25f));
        Grinder(toI(Tags.Items.ORES_GOLD), fConsumer, toCHI(Element.GOLD.dust(), 3, 1f), toCHI(Element.GOLD.dust(), 2, 0.5f));
        Grinder(toI(POMblocks.ENDSTONE_TITANIUM_ORE.get()), fConsumer, toCHI(Element.TITANIUM.dust(), 3, 1f), toCHI(Element.TITANIUM.dust(), 1, 0.5f), toCHI(Element.IRON.dust(), 2, 0.5f));


        Grinder(toI(POMitems.ALUMINIUM_SCRAP.get()), fConsumer, toCHI(Element.ALUMINIUM.dust(), 1, 1f));
        Grinder(toI(Items.OBSIDIAN), fConsumer, toCHI(POMitems.OBSIDIAN_DUST.get(), 2, 1f), toCHI(POMitems.OBSIDIAN_DUST.get(), 1, 0.25f));
        Grinder(toI(Items.CRYING_OBSIDIAN), fConsumer, toCHI(POMitems.OBSIDIAN_DUST.get(), 1, 1f), toCHI(POMitems.CRYING_OBSIDIAN_DUST.get(), 1, 0.7f));

        Grinder(toI(Items.STONE), fConsumer, toCHI(Element.SILICON.dustTag(), 2, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f) ,toCHI(Element.SILICON.dustTag(), 1, 0.55f) ,toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f), toCHI(Element.SILICON.dustTag(), 1, 0.55f));
        Grinder(toI(Items.GOLD_BLOCK), fConsumer, toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f), toCHI(Element.GOLD.dustTag(), 1, 1f));


        Grinder(toI(Items.BONE), fConsumer, toCHI(Items.BONE_MEAL, 4, 1), toCHI(Items.BONE_MEAL, 2, 0.33f));
        Grinder(toI(Items.BLAZE_ROD), fConsumer, toCHI(Items.BLAZE_POWDER, 3, 1), toCHI(Items.BLAZE_POWDER, 1, 0.33f), toCHI(Items.BLAZE_POWDER, 1, 0.33f));
        Grinder(toI(ItemTags.WOOL), fConsumer, toCHI(Items.STRING, 2, 1), toCHI(Items.STRING, 1, 0.33f));
        Grinder(toI(ItemTags.WOOL_CARPETS), fConsumer, toCHI(Items.STRING, 2, 1), toCHI(Items.STRING, 1, 0.33f));
        Grinder(toI(Items.SMALL_AMETHYST_BUD), fConsumer, toCHI(Items.AMETHYST_SHARD, 1, 0.1f));
        Grinder(toI(Items.MEDIUM_AMETHYST_BUD), fConsumer, toCHI(Items.AMETHYST_SHARD, 1, 0.25f));
        Grinder(toI(Items.LARGE_AMETHYST_BUD), fConsumer, toCHI(Items.AMETHYST_SHARD, 1, 0.70f));
        Grinder(toI(Items.AMETHYST_CLUSTER), fConsumer, toCHI(Items.AMETHYST_SHARD, 6, 1), toCHI(Items.AMETHYST_SHARD, 2, 0.33f), toCHI(Items.AMETHYST_SHARD, 1, 0.2f));
        Grinder(toI(Items.AMETHYST_BLOCK), fConsumer, toCHI(Items.AMETHYST_SHARD, 3, 1f), toCHI(Items.AMETHYST_SHARD, 1, 0.33f));
        Grinder(toI(Items.MUSIC_DISC_5), fConsumer, toCHI(Items.DISC_FRAGMENT_5, 7, 1f), toCHI(Items.DISC_FRAGMENT_5, 1, 0.33f), toCHI(Items.DISC_FRAGMENT_5, 1, 0.33f));
        Grinder(toI(Items.COBWEB), fConsumer, toCHI(Items.STRING, 2, 1f), toCHI(Items.STRING, 1, 0.33f), toCHI(Items.STRING, 1, 0.33f));
        Grinder(toI(Items.SANDSTONE), fConsumer, toCHI(Items.SAND, 2, 1f), toCHI(Items.SAND, 1, 0.33f), toCHI(Items.SAND, 1, 0.1f));
        Grinder(toI(Items.SMOOTH_SANDSTONE), fConsumer, toCHI(Items.SAND, 2, 1f), toCHI(Items.SAND, 1, 0.33f), toCHI(Items.SAND, 1, 0.1f));
        Grinder(toI(Items.RED_SANDSTONE), fConsumer, toCHI(Items.RED_SAND, 2, 1f), toCHI(Items.RED_SAND, 1, 0.33f), toCHI(Items.RED_SAND, 1, 0.1f));
        Grinder(toI(Items.SMOOTH_RED_SANDSTONE), fConsumer, toCHI(Items.RED_SAND, 2, 1f), toCHI(Items.RED_SAND, 1, 0.33f), toCHI(Items.RED_SAND, 1, 0.1f));
        Grinder(toI(Items.MELON), fConsumer, toCHI(Items.MELON_SEEDS, 4, 1f), toCHI(Items.MELON_SEEDS, 2, 0.33f), toCHI(Items.MELON_SLICE, 1, 0.33f), toCHI(Items.MELON_SLICE, 1, 0.1f));
        Grinder(toI(Items.PUMPKIN), fConsumer, toCHI(Items.PUMPKIN_SEEDS, 2, 1f), toCHI(Items.PUMPKIN_SEEDS, 1, 0.33f));
        Grinder(toI(Items.HONEYCOMB_BLOCK), fConsumer, toCHI(Items.HONEYCOMB, 2, 1f), toCHI(Items.HONEYCOMB, 1, 0.33f));
        Grinder(toI(Items.PRISMARINE), fConsumer, toCHI(Items.PRISMARINE_SHARD, 2, 1f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f));
        Grinder(toI(Items.PRISMARINE_BRICKS), fConsumer, toCHI(Items.PRISMARINE_SHARD, 4, 1f), toCHI(Items.PRISMARINE_SHARD, 2, 0.33f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f));
        Grinder(toI(Items.DARK_PRISMARINE), fConsumer, toCHI(Items.PRISMARINE_SHARD, 3, 1f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f));
        Grinder(toI(Items.SEA_LANTERN), fConsumer, toCHI(Items.PRISMARINE_CRYSTALS, 2, 1f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f), toCHI(Items.PRISMARINE_SHARD, 1, 0.33f), toCHI(Items.PRISMARINE_CRYSTALS, 1, 0.33f), toCHI(Items.PRISMARINE_CRYSTALS, 1, 0.33f));


        //milling
        BallMill(toI(POMitems.MERCURY_SULFIDE_DUST.get()), 1, toI(POMitems.TITANIUM_DIBORIDE_DUST.get()), 1, toI(POMitems.TITANIUM_OXIDE_DUST.get()), 1, POMitems.ANCIENT_DEBRIS_DUST.get(), 3, POMtags.Items.BALL_5, fConsumer);
        BallMill(toI(Element.GOLD.dustTag()), 2, toI(POMitems.ANCIENT_DEBRIS_DUST.get()), 2, POMitems.NETHERITE_DUST.get(), 1, POMtags.Items.BALL_4, fConsumer);
        BallMill(toI(Element.BORON.dustTag()), 2, toI(Element.TITANIUM.dustTag()), 1, POMitems.TITANIUM_DIBORIDE_DUST.get(), 2, POMtags.Items.BALL_4, fConsumer);

        BallMill(toI(Items.DANDELION), 1, Items.YELLOW_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.POPPY), 1, Items.RED_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BLUE_ORCHID), 1, Items.LIGHT_BLUE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.ALLIUM), 1, Items.MAGENTA_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.AZURE_BLUET), 1, Items.LIGHT_GRAY_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.RED_TULIP), 1, Items.RED_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.ORANGE_TULIP), 1, Items.ORANGE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WHITE_TULIP), 1, Items.WHITE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.PINK_TULIP), 1, Items.PINK_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.OXEYE_DAISY), 1, Items.LIGHT_GRAY_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.CORNFLOWER), 1, Items.BLUE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.LILY_OF_THE_VALLEY), 1, Items.WHITE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WITHER_ROSE), 1, Items.BLACK_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.SPORE_BLOSSOM), 1, Items.PINK_DYE, 5, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.SUNFLOWER), 1, Items.YELLOW_DYE, 4, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.PEONY), 1, Items.PINK_DYE, 4, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.ROSE_BUSH), 1, Items.RED_DYE, 4, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.LILAC), 1, Items.MAGENTA_DYE, 4, POMtags.Items.BALL_2, fConsumer);

        BallMill(toI(Items.SUGAR_CANE), 1, Items.SUGAR, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BLAZE_ROD), 1, Items.BLAZE_POWDER, 3, POMtags.Items.BALL_3, fConsumer);
        BallMill(toI(Items.BONE), 1, Items.BONE_MEAL, 4, POMtags.Items.BALL_3, fConsumer);
        BallMill(toI(Items.STONE), 1, Items.COBBLESTONE, 1, POMtags.Items.BALL_3, fConsumer);
        BallMill(toI(Items.DEEPSLATE), 1, Items.COBBLED_DEEPSLATE, 1, POMtags.Items.BALL_3, fConsumer);


        BallMill(toI(Items.BONE_MEAL), 1, Items.WHITE_DYE, 3, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WHITE_DYE), 2, toI(Items.BLACK_DYE), 1, Items.LIGHT_GRAY_DYE, 3, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WHITE_DYE), 1, toI(Items.GRAY_DYE), 1, Items.LIGHT_GRAY_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WHITE_DYE), 1, toI(Items.BLACK_DYE), 1, Items.GRAY_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.INK_SAC), 1, Items.BLACK_DYE, 3, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.COCOA_BEANS), 1, Items.BROWN_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BEETROOT), 1, Items.RED_DYE, 3, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.YELLOW_DYE), 1, toI(Items.RED_DYE), 1, Items.ORANGE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.GREEN_DYE), 1, toI(Items.WHITE_DYE), 1, Items.LIME_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.GREEN_DYE), 1, toI(Items.BLUE_DYE), 1, Items.CYAN_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.WHITE_DYE), 1, toI(Items.BLUE_DYE), 1, Items.LIGHT_BLUE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.LAPIS_LAZULI), 1, Items.BLUE_DYE, 4, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BLUE_DYE), 1, toI(Items.RED_DYE), 1, Items.PURPLE_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BLUE_DYE), 1, toI(Items.RED_DYE), 2, toI(Items.WHITE_DYE), 1, Items.MAGENTA_DYE, 4, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.BLUE_DYE), 1, toI(Items.RED_DYE), 1, toI(Items.PINK_DYE), 1, Items.MAGENTA_DYE, 3, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.PURPLE_DYE), 1, toI(Items.PINK_DYE), 1, Items.MAGENTA_DYE, 2, POMtags.Items.BALL_2, fConsumer);
        BallMill(toI(Items.RED_DYE), 1, toI(Items.WHITE_DYE), 1, Items.PINK_DYE, 2, POMtags.Items.BALL_2, fConsumer);


        //pixel splitter
        PixelSplitting(Items.GOLD_NUGGET, POMitems.PIXEL.get(), 6, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);
        PixelSplitting(Items.GOLD_INGOT, POMitems.PIXEL.get(), 56, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);
        PixelSplitting(Items.GOLD_BLOCK, POMitems.PIXEL_PILE.get(), 64, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);

        PixelSplitting(Items.IRON_NUGGET, POMitems.PIXEL.get(), 6, toAInt(216, 147, 94), toAInt(216, 147, 94), toAInt(216, 147, 94), fConsumer);
        PixelSplitting(Items.IRON_INGOT, POMitems.PIXEL.get(), 56, toAInt(216, 147, 94), toAInt(216, 147, 94), toAInt(216, 147, 94), fConsumer);
        PixelSplitting(Items.IRON_BLOCK, POMitems.PIXEL_PILE.get(), 64, toAInt(216, 147, 94), toAInt(216, 147, 94), toAInt(216, 147, 94), fConsumer);



        //pressing
        Pressing(POMitems.BIO_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(POMitems.FIRE_PROOF_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(POMitems.REPELLING_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.REPELLING_RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(Element.TITANIUM.item(), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_BALL.get(), 1, 1941, 3560, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.BALL_CAST.get(), POMitems.NETHERITE_BALL.get(), 1, 2500, 4000, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), 1, 3000, 5000, fConsumer);

        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.INGOT_CAST.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), 1, 3000, 5000, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), 1, 3000, 5000, fConsumer);
        Pressing(Element.TITANIUM.item(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_PLATING.get(), 1, 1941, 3560, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.PLATE_CAST.get(), POMitems.NETHERITE_PLATING.get(), 1, 2500, 4000, fConsumer);
        Pressing(POMitems.OBSIDIAN_DUST.get(), 2, POMitems.PLATE_CAST.get(), POMitems.OBSIDIAN_PLATING.get(), 1, 2200, 3400, fConsumer);
        Pressing(POMitems.CRYING_OBSIDIAN_DUST.get(), 2, POMitems.PLATE_CAST.get(), POMitems.CRYING_OBSIDIAN_PLATING.get(), 1, 2500, 5000, fConsumer);

        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(Tags.Items.INGOTS), toI(POMitems.INGOT_CAST.get()), 1, 250, 700, fConsumer);
        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(POMtags.Items.MILLING_BALL), toI(POMitems.BALL_CAST.get()), 1, 250, 700, fConsumer);
        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(POMitems.TITANIUM_PLATING.get()), toI(POMitems.PLATE_CAST.get()), 1, 250, 700, fConsumer);

        Pressing(toI(Items.DIAMOND), 4, toI(POMitems.BALL_CAST.get()), toI(POMitems.VIOLET_DIAMOND.get()), 1, 3500, 5000, fConsumer);
        Pressing(toI(POMitems.VIOLET_DIAMOND.get()), 4, toI(POMitems.BALL_CAST.get()), toI(POMitems.RED_DIAMOND.get()), 1, 5000, 5000, fConsumer);

        Pressing(toI(Element.GOLD.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.GOLD_INGOT), 1, 1337, 3243, fConsumer);
        Pressing(toI(Element.IRON.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.IRON_INGOT), 1, 1811, 3134, fConsumer);
        Pressing(toI(Element.COPPER.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.COPPER_INGOT), 1, 1358, 2835, fConsumer);
        Pressing(toI(POMitems.NETHERITE_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.NETHERITE_INGOT), 1, 2500, 4000, fConsumer);

        //chemical separating

        ChemicalSeperator(fConsumer, toCI(POMitems.ACANTHITE_DUST.get(), 2), toF(Fluids.WATER, 200), toF(POMfluids.SULFURIC_ACID_SOURCE.get(), 50), toCHI(Element.SILVER.dustTag(), 1, 1), toCHI(Element.SILVER.dustTag(), 1, 0.5f));
        ChemicalSeperator(fConsumer, toCI(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1), FluidStack.EMPTY, FluidStack.EMPTY, toCHI(Element.TITANIUM.dustTag(), 1, 1), toCHI(Element.BORON.dustTag(), 2, 1));

        ChemicalSeperator(fConsumer, toCI(Element.TITANIUM.dustTag(), 1), toF(Fluids.WATER, 150), toF(POMfluids.HYDROGEN_SOURCE.get(), 100), toCHI(POMitems.TITANIUM_OXIDE_DUST.get(), 1, 1));
        ChemicalSeperator(fConsumer, toCI(POMitems.MERCURY_SULFIDE_DUST.get(), 1), FluidStack.EMPTY, toF(POMfluids.HYDROGEN_SOURCE.get(), 100), toCHI(Element.SULFUR.dustTag(), 1, 1));

        //ez crafting
        SimpleSurroundRecipe(Element.TITANIUM.nugget(), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.VIOLET_DIAMOND.get(), POMitems.VIOLET_DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(Element.COPPER.nugget(), Items.STICK, POMitems.COPPER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.SILVER.nugget(), Items.STICK, POMitems.SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TUNGSTEN.nugget(), Items.STICK, POMitems.TUNGSTEN_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TITANIUM.item(), Items.NETHERITE_BLOCK, POMblocks.SIMPLE_CASING_1.get(), fConsumer);

        SimpleFullCrossRecipe(POMitems.OBSIDIAN_PLATING.get(), POMitems.NETHERITE_PLATING.get(), Items.NETHERITE_BLOCK, POMblocks.STRONG_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRONG_CASING.get(), POMblocks.REINFORCED_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, Items.NETHER_STAR, POMitems.POWER_ORB.get(), fConsumer);
        SimpleFullCrossRecipe(Element.TITANIUM.nugget(), Items.GOLD_INGOT, Items.REDSTONE_BLOCK, POMitems.REDSTONE_COUNTER.get(), fConsumer);
        SimpleFullCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_PLATING.get(), POMitems.TITANIUM_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get(), fConsumer);

        SimpleCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_COMPOUND.get(), POMitems.RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, POMitems.NETHERITE_BALL.get(), fConsumer);
        SimpleCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), fConsumer);

        //compacting
        //SimpleMetalCompactingRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMblocks.TITANIUM_BLOCK.get(), fConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), fConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, fConsumer);
        SimpleCompactingRecipe(Element.COPPER.nugget(), Items.COPPER_INGOT, fConsumer);
        SimpleCompactingRecipe(POMitems.ALUMINIUM_SCRAP.get(), POMblocks.ALUMINIUM_SCRAP_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.COPPER_WIRE.get(), POMblocks.COPPER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SILVER_WIRE.get(), POMblocks.SILVER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.TUNGSTEN_WIRE.get(), POMblocks.TUNGSTEN_SPOOL.get(), fConsumer);


        //Furnace
        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), Element.TITANIUM.item(), 0.5f, 200 , fConsumer, "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), Element.TITANIUM.item(), 0.5f, 200 , fConsumer, "_from_ore");
        SimpleSmeltingRecipe(POMitems.RUSTED_PLATING.get(), Items.NETHERITE_SCRAP, 0.5f, 200, fConsumer, "");
        SimpleSmeltingRecipe(Element.ALUMINIUM.dust(), POMitems.ALUMINIUM_SCRAP.get(), 0.8f, 200 , fConsumer, "");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 200 , fConsumer, "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 200 , fConsumer, "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 200 , fConsumer, "");

        //Smithing
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.STICK), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, POMitems.HAMMER.get())
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(fConsumer, toRL(getItemName(POMitems.HAMMER.get()) + "_smithing"));

        //auto
        for(Element m : Element.values()) {
            if (m.equals(Element.DEBUGIUM)) continue;
            SimpleAtomCompacting(m.atom64(), m.atom512(), fConsumer);

            if (m.isMetal() && m.shouldAddDust())
                Pressing(toCI(m.dustTag(), 1), toI(POMitems.INGOT_CAST.get()), toCI(m.itemTag(), 1), m.getInfo().getMeltingPoint(), m.getInfo().getEvaporatingPoint(), fConsumer);
            if (m.isMetal() && m.shouldAddDust() && m!=Element.ALUMINIUM)
                SimpleSmeltingRecipe(m.dustTag(), m.item(), 1f, 200, fConsumer, "_from_dust");
            if (m.shouldAddDust() && m!=Element.ALUMINIUM && !m.isVanilla())
                Grinder(toI(m.itemTag()), fConsumer, toCHI(m.dustTag(), 1, 1));
            if (m.shouldAddNugget() && !m.equals(Element.COPPER))
                SimpleCompactingRecipe(toI(m.nugget()), m.item(), fConsumer);
            if (m.shouldAddBlock())
                SimpleCompactingRecipe(toI(m.item()), m.block(), fConsumer);
        }

    }

    private void SimpleFurnaceRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleFurnaceRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleFurnaceRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
    }

    //Immersive Engineering
    private void SimpleSmeltingRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleSmeltingRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, extra);
    }
    private void SimpleSmeltingRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
        SimpleCookingRecipeBuilder.blasting(input, RecipeCategory.MISC, output, xp, smeltingTime/2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("smelting/"+output.asItem()+extra+"_from_blasting"));
    }

    private static void SmithingRecipe(Item pIngredientItem, Item pResultItem, RecipeCategory pCategory, Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pCategory, pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, getItemName(pResultItem) + "_smithing");
    }

    private void SimpleCompactingRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        SimpleCompactingRecipe(Ingredient.of(input),output,consumer);
    }
    private void SimpleCompactingRecipe(TagKey<Item> input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        SimpleCompactingRecipe(Ingredient.of(input),output,consumer);

    }
    private void SimpleCompactingRecipe(Ingredient input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('T', input)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/" + input.getItems()[0].getItem() + "_to_" + output.asItem() + "_compacting" ));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, input.getItems()[0].getItem(), 9)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot.getItems()[0].getItem(), 1)
                .define('T', nugget)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+nugget.getItems()[0].getItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block, 1)
                .define('T', ingot)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+block.asItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget.getItems()[0].getItem(), 9)
                .requires(ingot)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+nugget.getItems()[0].getItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot.getItems()[0].getItem(), 9)
                .requires(block)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/"+block.asItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));

    }
    private void SimpleSurroundRecipe(ItemLike around, ItemLike middle, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', around)
                .define('B', middle)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', corners)
                .define('B', sides)
                .define('C', middle)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void SimpleCrossRecipe(ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', sides)
                .define('B', middle)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void SimpleAtomCompacting(ItemLike atomx64, ItemLike atomx512, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, atomx512, 1)
                .requires(atomx64, 8)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/atoms/"+atomx64.asItem()+"_to_"+atomx512.asItem()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, atomx64, 8)
                .requires(atomx512)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/atoms/"+atomx512.asItem()+"_to_"+atomx64.asItem()));
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


    private void PixelSplitting(ItemLike ingredient, ItemLike output, int count, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), CountedIngredient.of(count, output), r,g,b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void Pressing(ItemLike ingredient, int inCount, ItemLike mold, ItemLike output, int outCount, int heat, int maxHeat, Consumer<FinishedRecipe> consumer) {
        new HotIsostaticPressRecipeBuilder(CountedIngredient.of(inCount, ingredient), CountedIngredient.of(1, mold), CountedIngredient.of(outCount, output), heat, maxHeat)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void Pressing(Ingredient ingredient, int inCount, Ingredient mold, Ingredient output, int outCount, int heat, int maxHeat, Consumer<FinishedRecipe> consumer) {
        new HotIsostaticPressRecipeBuilder(CountedIngredient.of(inCount, ingredient), CountedIngredient.of(1, mold), CountedIngredient.of(outCount, output), heat, maxHeat)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void Pressing(CountedIngredient ingredient, Ingredient mold, CountedIngredient output, int heat, int maxHeat, Consumer<FinishedRecipe> consumer) {
        new HotIsostaticPressRecipeBuilder(ingredient, CountedIngredient.of(1, mold), output, heat, maxHeat)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void Grinder(Ingredient input, ItemLike output, int outputCount, int outputChance, Consumer<FinishedRecipe> consumer) {
        new GrinderRecipeBuilder(input, List.of(ChanceIngredient.of(outputCount, outputChance, output)))
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void Grinder(Ingredient input, Consumer<FinishedRecipe> consumer, ChanceIngredient... output) {
        List<ChanceIngredient> outputList = new java.util.ArrayList<>();
        Collections.addAll(outputList, output);
        new GrinderRecipeBuilder(input, outputList)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void ChemicalSeperator(Consumer<FinishedRecipe> consumer, CountedIngredient input, FluidStack inputFluid, FluidStack outputFluid, ChanceIngredient... output) {
        List<ChanceIngredient> outputList = new java.util.ArrayList<>();
        Collections.addAll(outputList, output);
        new ChemicalSeperatorRecipeBuilder(input, inputFluid, outputFluid, outputList)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }


    //toIngredient
    private Ingredient toI(ItemLike input) {
        return Ingredient.of(input);
    }
    private Ingredient toI(TagKey<Item> input) {
        return Ingredient.of(input);
    }
    //toCountIngredient
    private CountedIngredient toCI(ItemLike input, int count) {
        return CountedIngredient.of(count, input);
    }
    private CountedIngredient toCI(TagKey<Item> input, int count) {
        return CountedIngredient.of(count, input);
    }
    //toChanceIngredient
    private ChanceIngredient toCHI(ItemLike input, int count, float chance) {
        return ChanceIngredient.of(count, chance, input);
    }
    private ChanceIngredient toCHI(TagKey<Item> input, int count, float chance) {
        return ChanceIngredient.of(count, chance, input);
    }

    private FluidStack toF(Fluid fluid, int amount) {return new FluidStack(fluid, amount);}
    private int[] toAInt(int ...num) {
        return num;
    }

    //Immersive Engineering
    private ResourceLocation toRL(String string) {
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