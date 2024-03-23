package net.turtlemaster42.pixelsofmc.datagen;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
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
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    public POMrecipeProvider(PackOutput output) {
        super(output);
    }

    private final HashMap<String, Integer> PATH_COUNT = new HashMap<>();
    private final ItemPredicate HAS_TITANIUM = toItemP(Element.TITANIUM.itemTag());
    private final ItemPredicate HAS_TITANIUM_DIBORIDE = toItemP(POMitems.TITANIUM_DIBORIDE_INGOT.get());
    private final ItemPredicate HAS_TITANIUM_GOLD = toItemP(POMitems.TITANIUM_GOLD_INGOT.get());
    private final ItemPredicate HAS_NETHERITE = toItemP(Tags.Items.INGOTS_NETHERITE);
    private final ItemPredicate HAS_REDSTONE = toItemP(Tags.Items.DUSTS_REDSTONE);
    private final ItemPredicate HAS_FUSION_PLATING = toItemP(POMitems.FUSION_PLATING.get());

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> fConsumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.SUGAR_CANE)
                .requires(Items.HONEYCOMB)
                .requires(Items.WARPED_ROOTS, 2)
                .requires(Items.KELP)
                .requires(Items.WARPED_FUNGUS)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Tags.Items.EGGS)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Items.SUGAR_CANE, Items.HONEYCOMB, Items.WARPED_ROOTS, Items.KELP, Items.WARPED_FUNGUS),
                        toItemP(Tags.Items.SLIMEBALLS),
                        toItemP(Tags.Items.EGGS)))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.FIRE_PROOF_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CRIMSON_FUNGUS)
                .requires(Items.WEEPING_VINES)
                .requires(Items.CRIMSON_ROOTS)
                .requires(Items.NETHER_WART, 2)
                .requires(Items.MAGMA_CREAM, 2)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_COMPOUND.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.REPELLING_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CHORUS_FLOWER)
                .requires(Items.CHORUS_FRUIT, 2)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(Tags.Items.ENDER_PEARLS)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_COMPOUND.get())))
                .save(fConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.SOUL_COAL.get(), 2)
                .requires(Items.CHARCOAL)
                .requires(POMitems.COAL_DUST.get(), 2)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.SOUL_SAND, 2)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Items.SOUL_SAND), toItemP(Items.BLAZE_POWDER), toItemP(POMitems.COAL_DUST.get())))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DENSE_CARBON_CUBE.get())
                .define('C', Element.CARBON.item())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.item().asItem())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.CARBONARO_CLUMP.get())
                .define('C', POMitems.DENSE_CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.item().asItem())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.BLACK_DIAMOND.get())
                .define('C', POMitems.CARBONARO_CLUMP.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.item().asItem())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND)
                .define('C', POMitems.BLACK_DIAMOND.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BLACK_DIAMOND.get())))
                .save(fConsumer, toRL(Items.DIAMOND.toString()));


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, POMitems.SCREWDRIVER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern("B")
                .pattern("B")
                .pattern("A")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, POMitems.WIRECUTTER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern(" B ")
                .pattern("A A")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, POMitems.CLEANING_CLOTH.get())
                .requires(ItemTags.WOOL_CARPETS)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.STRING), toItemP(ItemTags.WOOL), toItemP(ItemTags.WOOL_CARPETS)))
                .save(fConsumer);



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.TITANIUM_PLATING.get())
                .requires(Element.TITANIUM.itemTag())
                .requires(Element.TITANIUM.itemTag())
                .requires(POMitems.HAMMER.get())
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.NETHERITE_PLATING.get())
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.NETHERITE_INGOT)
                .requires(POMitems.HAMMER.get())
                .unlockedBy("has_items", inventoryTrigger(HAS_NETHERITE))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.TITANIUM_GOLD_PLATING.get())
                .requires(POMitems.TITANIUM_GOLD_INGOT.get())
                .requires(POMitems.TITANIUM_GOLD_INGOT.get())
                .requires(POMitems.HAMMER.get())
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_GOLD))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.LEAD_PLATING.get())
                .requires(Element.LEAD.itemTag())
                .requires(Element.LEAD.itemTag())
                .requires(POMitems.HAMMER.get())
                .unlockedBy("has_items", inventoryTrigger(toItemP(Element.LEAD.itemTag())))
                .save(fConsumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.FUSION_LINING.get())
                .requires(POMitems.TUNGSTEN_PLATING.get())
                .requires(POMitems.PYROLYTIC_CARBON_SHEET.get())
                .requires(POMitems.PYROLYTIC_CARBON_SHEET.get())
                .unlockedBy("has_items",
                        inventoryTrigger(toItemP(POMitems.PYROLYTIC_CARBON_SHEET.get()), toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.FUSION_PLATING.get())
                .requires(POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .requires(POMitems.LEAD_PLATING.get())
                .requires(POMitems.FUSION_LINING.get())
                .unlockedBy("has_items",
                        inventoryTrigger(toItemP(POMitems.PYROLYTIC_CARBON_SHEET.get()), toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_REDSTONE, toItemP(POMitems.COPPER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DRAGON_EYE.get())
                .define('B', Items.ENDER_EYE)
                .define('C', Items.DRAGON_BREATH)
                .define('D', Tags.Items.NETHER_STARS)
                .pattern(" C ")
                .pattern("BDB")
                .pattern(" C ")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.NETHER_STARS), toItemP(Items.DRAGON_BREATH, Items.ENDER_EYE)))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.MICRO_CHIP.get())
                .define('A', POMtags.Items.NUGGET_NETHERITE)
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', Tags.Items.NUGGETS_IRON)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DDD")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_NETHERITE, toItemP(POMitems.FIRE_PROOF_COMPOUND.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.IMPROVED_MICRO_CHIP.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', POMtags.getTagsFor(Element.SILVER).nugget)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DDD")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_NETHERITE, toItemP(POMitems.FIRE_PROOF_COMPOUND.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.EMPTY_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .pattern("ABA")
                .pattern("A A")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.NETHERITE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.EMPTY_FUEL_CELL.get())
                .define('A', POMblocks.REINFORCED_GLASS.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .pattern("ABA")
                .pattern("A A")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_DIBORIDE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.NETHER_STARS), toItemP(POMitems.POWER_ORB.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.OVERCHARGED_POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.OVERCHARGED_POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.NETHER_STARS), toItemP(POMitems.POWER_ORB.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SUPERCHARGED_POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.SUPERCHARGED_POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.NETHER_STARS), toItemP(POMitems.POWER_ORB.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.POWER_CELL.get())
                .requires(POMitems.EMPTY_CELL.get())
                .requires(POMitems.POWER_ORB.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.POWER_ORB.get()), toItemP(POMitems.EMPTY_CELL.get())))
                .save(fConsumer, toRL(POMitems.POWER_CELL.get().toString() + "_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.OVERCHARGED_POWER_CELL.get())
                .requires(POMitems.EMPTY_CELL.get())
                .requires(POMitems.OVERCHARGED_POWER_ORB.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.OVERCHARGED_POWER_ORB.get()), toItemP(POMitems.EMPTY_CELL.get())))
                .save(fConsumer, toRL(POMitems.OVERCHARGED_POWER_CELL.get().toString() + "_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.SUPERCHARGED_POWER_CELL.get())
                .requires(POMitems.EMPTY_CELL.get())
                .requires(POMitems.SUPERCHARGED_POWER_ORB.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SUPERCHARGED_POWER_ORB.get()), toItemP(POMitems.EMPTY_CELL.get())))
                .save(fConsumer, toRL(POMitems.SUPERCHARGED_POWER_CELL.get().toString() + "_2"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_COUNTER.get())
                .define('A', Element.TITANIUM.nuggetTag())
                .define('B', Tags.Items.INGOTS_GOLD)
                .define('C', Items.COMPARATOR)
                .define('D', Items.DROPPER)
                .pattern("ABA")
                .pattern("CDD")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM, toItemP(Element.TITANIUM.nuggetTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_TIMER.get())
                .define('A', Items.STICKY_PISTON)
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('C', Items.COMPARATOR)
                .define('D', Items.HOPPER)
                .define('E', POMitems.TITANIUM_GOLD_PLATING.get())
                .define('F', Tags.Items.DUSTS_REDSTONE)
                .pattern("ABA")
                .pattern("CDC")
                .pattern("EFE")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.STORAGE_BLOCKS_REDSTONE), toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.RESONANCE_DETECTOR.get())
                .define('A', Tags.Items.DUSTS_REDSTONE)
                .define('B', Element.SILVER.itemTag())
                .define('C', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('D', Items.SCULK_SENSOR)
                .define('E', Items.COMPARATOR)
                .pattern("ABC")
                .pattern("BDB")
                .pattern("EBA")
                .unlockedBy("", inventoryTrigger(
                        toItemP(Items.SCULK_SENSOR)))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENDER_SENSOR.get())
                .define('A', Element.TITANIUM.nuggetTag())
                .define('B', Element.COPPER.nuggetTag())
                .define('C', Items.ENDER_EYE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DRAGON_SENSOR.get())
                .define('A', POMitems.TITANIUM_GOLD_NUGGET.get())
                .define('B', Element.SILVER.nugget())
                .define('C', POMitems.DRAGON_EYE.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.DRAGON_EYE.get())))
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
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get(), POMitems.COPPER_WIRE.get())))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SPEED_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.TITANIUM.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TITANIUM.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENERGY_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.GOLD.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.GOLD.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.HEAT_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.COBALT.dustTag())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.COBALT.dustTag())))
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
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
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
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.HOT_ISOSTATIC_PRESS.get())
                .define('A', Items.PISTON)
                .define('B', POMitems.PERFECTED_CIRCUIT_BOARD_1.get())
                .define('C', POMitems.TITANIUM_GOLD_PLATING.get())
                .define('D', POMblocks.STRENGTHENED_CASING.get())
                .define('E', POMblocks.PERFECTED_CASING_1.get())
                .define('F', Items.BLAST_FURNACE)
                .pattern("CDC")
                .pattern("ADA")
                .pattern("BEF")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.CHEMICAL_SEPARATOR.get())
                .define('A', POMitems.EMPTY_CELL.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('C', POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('D', POMblocks.PERFECTED_CASING_2.get())
                .define('E', POMitems.REDSTONE_IMBUED_SILVER_WIRE.get())
                .define('F', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .define('G', POMblocks.REINFORCED_CASING.get())
                .pattern("ABA")
                .pattern("CDE")
                .pattern("FGF")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.CHEMICAL_COMBINER.get())
                .define('A', POMitems.EMPTY_CELL.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('C', POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('D', POMblocks.PERFECTED_CASING_2.get())
                .define('E', POMitems.REDSTONE_IMBUED_SILVER_WIRE.get())
                .define('F', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .define('G', POMblocks.REINFORCED_CASING.get())
                .pattern("CBE")
                .pattern("ADA")
                .pattern("FGF")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
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
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', Items.GOLD_NUGGET)
                .pattern("EDE")
                .pattern("AAA")
                .pattern("BCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get(), POMitems.MICRO_CHIP.get(), POMitems.COPPER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('D', POMitems.MICRO_CHIP.get())
                .define('E', Element.TITANIUM.nugget())
                .define('F', POMitems.REDSTONE_COUNTER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_1.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get(), POMitems.MICRO_CHIP.get(), POMitems.COPPER_WIRE.get())))
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
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get(), POMitems.MICRO_CHIP.get(), POMitems.COPPER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('A', POMitems.FIRE_PROOF_PLASTIC.get())
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('C', POMitems.SILVER_WIRE.get())
                .define('D', POMitems.IMPROVED_MICRO_CHIP.get())
                .define('E', Tags.Items.INGOTS_GOLD)
                .pattern("EDE")
                .pattern("AAA")
                .pattern("BCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.IMPROVED_MICRO_CHIP.get(), POMitems.SILVER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .define('A', POMitems.FIRE_PROOF_PLASTIC.get())
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('C', POMitems.REDSTONE_IMBUED_SILVER_WIRE.get())
                .define('D', POMitems.IMPROVED_MICRO_CHIP.get())
                .define('E', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('F', POMitems.REDSTONE_TIMER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.IMPROVED_MICRO_CHIP.get(), POMitems.SILVER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('A', POMitems.FIRE_PROOF_PLASTIC.get())
                .define('B', POMitems.VIOLET_DIAMOND_LENS.get())
                .define('C', POMitems.REDSTONE_IMBUED_SILVER_WIRE.get())
                .define('D', POMitems.IMPROVED_MICRO_CHIP.get())
                .define('E', POMitems.SILVER_WIRE.get())
                .define('F', POMitems.DRAGON_SENSOR.get())
                .define('G', POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .pattern("EDE")
                .pattern("GAG")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.IMPROVED_MICRO_CHIP.get(), POMitems.SILVER_WIRE.get())))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.ADVANCED_CASING_1.get())
                .define('A', POMblocks.SIMPLE_CASING_1.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.INGOTS_GOLD)
                .define('E', POMitems.POWER_CELL.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BEB")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.PERFECTED_CASING_1.get())
                .define('A', POMblocks.ADVANCED_CASING_1.get())
                .define('B', POMitems.TITANIUM_PLATING.get())
                .define('C', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.ADVANCED_CASING_2.get())
                .define('A', POMblocks.SIMPLE_CASING_2.get())
                .define('B', POMtags.getTagsFor(Element.SILVER).metal)
                .define('C', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('D', Tags.Items.STORAGE_BLOCKS_GOLD)
                .define('E', POMitems.POWER_CELL.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BEB")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_GOLD))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.PERFECTED_CASING_2.get())
                .define('A', POMblocks.ADVANCED_CASING_2.get())
                .define('B', POMitems.TITANIUM_GOLD_PLATING.get())
                .define('C', POMblocks.VIOLET_DIAMOND_BLOCK.get().asItem())
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_GOLD))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.REINFORCED_GLASS.get(), 2)
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Items.TINTED_GLASS)
                .pattern("ABA")
                .pattern("B B")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.FUSION_ENERGY_PORT.get())
                .define('A', POMitems.FUSION_PLATING.get())
                .define('B', POMitems.TUNGSTEN_WIRE.get())
                .define('C', POMitems.REFINED_REDSTONE.get())
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(HAS_FUSION_PLATING))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.FUSION_CASING.get())
                .define('A', POMitems.FUSION_PLATING.get())
                .define('B', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('C', POMitems.REDSTONE_CORE.get())
                .define('D', POMblocks.TUNGSTEN_SPOOL.get())
                .pattern("ABA")
                .pattern("ADA")
                .pattern("ACA")
                .unlockedBy("has_items", inventoryTrigger(HAS_FUSION_PLATING))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.FUSION_CORNER.get())
                .define('A', POMitems.FUSION_PLATING.get())
                .define('B', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('C', POMitems.REDSTONE_CORE.get())
                .define('D', POMblocks.RED_TUNGSTEN_SPOOL.get())
                .define('E', POMitems.REFINED_REDSTONE.get())
                .pattern("ABA")
                .pattern("EDE")
                .pattern("ACA")
                .unlockedBy("has_items", inventoryTrigger(HAS_FUSION_PLATING))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.TITANIUM_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.NETHERITE_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.NETHERITE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.TITANIUM_GOLD_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_DIBORIDE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.LEAD_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.LEAD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_BLOCK.get(), 2)
                .define('A', POMitems.TUNGSTEN_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get(), 2)
                .define('A', POMitems.PYROLYTIC_CARBON_SHEET.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.PYROLYTIC_CARBON_SHEET.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.TITANIUM_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.NETHERITE_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.NETHERITE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_DIBORIDE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.LEAD_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.LEAD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_STAIRS.get(), 4)
                .define('A', POMblocks.TUNGSTEN_PLATING_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(), 4)
                .define('A', POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get())
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.PYROLYTIC_CARBON_SHEET.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.TITANIUM_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.NETHERITE_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.NETHERITE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_DIBORIDE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.LEAD_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.LEAD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_SLAB.get(), 6)
                .define('A', POMblocks.TUNGSTEN_PLATING_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(), 6)
                .define('A', POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get())
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.PYROLYTIC_CARBON_SHEET.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_TORCH, 6)
                .define('A', POMitems.SOUL_COAL.get())
                .define('B', Items.STICK)
                .pattern("A")
                .pattern("B")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SOUL_COAL.get())))
                .save(fConsumer, toRL(Items.SOUL_TORCH.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_CAMPFIRE, 1)
                .define('A', POMitems.SOUL_COAL.get())
                .define('B', Items.STICK)
                .define('C', ItemTags.LOGS_THAT_BURN)
                .pattern(" B ")
                .pattern("BAB")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SOUL_COAL.get())))
                .save(fConsumer, toRL(Items.SOUL_CAMPFIRE.toString()));



        //grinding
        Grinder(toI(Items.FLINT), fConsumer, toCHI(Element.CALCIUM.dustTag(), 1, 0.7f));
        Grinder(toI(Items.NETHERITE_SCRAP), fConsumer, toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 1f));
        Grinder(toI(POMitems.TITANIUM_GOLD_INGOT.get()), fConsumer, toCHI(POMitems.TITANIUM_GOLD_DUST.get(), 1, 1f));
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
        BallMill(toI(Element.TITANIUM.dustTag()), 3, toI(Element.GOLD.dustTag()), 1, POMitems.TITANIUM_GOLD_DUST.get(), 4, POMtags.Items.BALL_4, fConsumer);
        BallMill(toI(Element.TITANIUM.dustTag()), 1, toI(Element.NIOBIUM.dustTag()), 1, POMitems.SUPERCONDUCTIVE_DUST.get(), 1, POMtags.Items.BALL_4, fConsumer);
        BallMill(toI(Element.SILVER.dustTag()), 1, toI(Tags.Items.DUSTS_REDSTONE), 3, POMitems.REDSTONE_IMBUED_SILVER_DUST.get(), 2, POMtags.Items.BALL_3, fConsumer);


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
        autoPixelSplittingAndAssembling(Items.GOLD_BLOCK, List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.gold", Element.GOLD.hexToRGB(0), Element.GOLD.hexToRGB(1), Element.GOLD.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Items.GOLD_NUGGET, List.of(toCI(POMitems.PIXEL.get(), 9)), "element.pixelsofmc.gold", Element.GOLD.hexToRGB(0), Element.GOLD.hexToRGB(1), Element.GOLD.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Items.GOLD_INGOT, List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.gold", Element.GOLD.hexToRGB(0), Element.GOLD.hexToRGB(1), Element.GOLD.hexToRGB(2), fConsumer);

        autoPixelSplittingAndAssembling(Items.IRON_BLOCK, List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.iron", Element.IRON.hexToRGB(0), Element.IRON.hexToRGB(1), Element.IRON.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Items.IRON_NUGGET, List.of(toCI(POMitems.PIXEL_PILE.get(), 1), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.iron", Element.IRON.hexToRGB(0), Element.IRON.hexToRGB(1), Element.IRON.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Items.IRON_INGOT, List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.iron", Element.IRON.hexToRGB(0), Element.IRON.hexToRGB(1), Element.IRON.hexToRGB(2), fConsumer);

        autoPixelSplittingAndAssembling(Items.COPPER_BLOCK, List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.copper", Element.COPPER.hexToRGB(0), Element.COPPER.hexToRGB(1), Element.COPPER.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Element.COPPER.nugget(), List.of(toCI(POMitems.PIXEL.get(), 9)), "element.pixelsofmc.copper", Element.COPPER.hexToRGB(0), Element.COPPER.hexToRGB(1), Element.COPPER.hexToRGB(2), fConsumer);
        autoPixelSplittingAndAssembling(Items.COPPER_INGOT, List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc.copper", Element.COPPER.hexToRGB(0), Element.COPPER.hexToRGB(1), Element.COPPER.hexToRGB(2), fConsumer);

        PixelSplittingAndAssembling(POMblocks.TITANIUM_GOLD_BLOCK.get().asItem(), List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get(), 1)), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);
        PixelSplittingAndAssembling(POMitems.TITANIUM_GOLD_NUGGET.get(), List.of(toCI(POMitems.PIXEL.get(), 9)), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);
        PixelSplittingAndAssembling(POMitems.TITANIUM_GOLD_INGOT.get(), List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get(), 1)), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);

        //pixel assembler
        PixelAssembling(POMitems.VOID_EYE.get(), List.of(toCI(POMitems.DRAGON_EYE.get(), 9)), "", fConsumer);
        autoPixelAssembling(Items.AMETHYST_SHARD, List.of(toCI(POMitems.PIXEL.get(), 9), toCI(Items.LAPIS_LAZULI, 9)), "element.pixelsofmc.helium", Element.HELIUM.hexToRGB(0), Element.HELIUM.hexToRGB(1), Element.HELIUM.hexToRGB(2),  fConsumer);

        //pressing
        Pressing(POMitems.BIO_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(POMitems.FIRE_PROOF_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(POMitems.REPELLING_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.REPELLING_RUBBER_BALL.get(), 1, 0, 5000, fConsumer);
        Pressing(Element.TITANIUM.item(), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_BALL.get(), 1, 1941, 3560, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.BALL_CAST.get(), POMitems.NETHERITE_BALL.get(), 1, 2500, 4000, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), 1, 3000, 5000, fConsumer);

        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.INGOT_CAST.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), 1, 3000, 5000, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), 1, 3000, 5000, fConsumer);
        Pressing(POMitems.TITANIUM_GOLD_DUST.get(), 1, POMitems.INGOT_CAST.get(), POMitems.TITANIUM_GOLD_INGOT.get(), 1, 2300, 3500, fConsumer);
        Pressing(POMitems.TITANIUM_GOLD_DUST.get(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_GOLD_PLATING.get(), 1, 2300, 3500, fConsumer);
        Pressing(Element.TITANIUM.item(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_PLATING.get(), 1, 1941, 3560, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.PLATE_CAST.get(), POMitems.NETHERITE_PLATING.get(), 1, 2500, 4000, fConsumer);
        Pressing(POMitems.OBSIDIAN_DUST.get(), 2, POMitems.PLATE_CAST.get(), POMitems.OBSIDIAN_PLATING.get(), 1, 2200, 3400, fConsumer);
        Pressing(POMitems.CRYING_OBSIDIAN_DUST.get(), 2, POMitems.PLATE_CAST.get(), POMitems.CRYING_OBSIDIAN_PLATING.get(), 1, 2500, 5000, fConsumer);

        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(Tags.Items.INGOTS), toI(POMitems.INGOT_CAST.get()), 1, 2500, 5000, fConsumer);
        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(POMtags.Items.MILLING_BALL), toI(POMitems.BALL_CAST.get()), 1, 2500, 5000, fConsumer);
        Pressing(toI(POMitems.CRYING_OBSIDIAN_DUST.get()), 4, toI(POMitems.TITANIUM_PLATING.get()), toI(POMitems.PLATE_CAST.get()), 1, 2500, 5000, fConsumer);

        Pressing(toI(Items.DIAMOND), 4, toI(POMitems.BALL_CAST.get()), toI(POMitems.VIOLET_DIAMOND.get()), 1, 3500, 5000, fConsumer);
        Pressing(toI(POMitems.VIOLET_DIAMOND.get()), 4, toI(POMitems.BALL_CAST.get()), toI(POMitems.RED_DIAMOND.get()), 1, 5000, 5000, fConsumer);

        Pressing(toI(Element.GOLD.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.GOLD_INGOT), 1, 1337, 3243, fConsumer);
        Pressing(toI(Element.IRON.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.IRON_INGOT), 1, 1811, 3134, fConsumer);
        Pressing(toI(Element.COPPER.dustTag()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.COPPER_INGOT), 1, 1358, 2835, fConsumer);
        Pressing(toI(POMitems.NETHERITE_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.NETHERITE_INGOT), 1, 2500, 4000, fConsumer);
        Pressing(toI(POMitems.SUPERCONDUCTIVE_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(POMitems.SUPERCONDUCTIVE_INGOT.get()), 1, 2300, 3500, fConsumer);
        Pressing(toI(POMitems.REDSTONE_IMBUED_SILVER_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(POMitems.REDSTONE_IMBUED_SILVER_INGOT.get()), 1, 1500, 2500, fConsumer);
        Pressing(toI(POMitems.RED_TUNGSTEN_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(POMitems.RED_TUNGSTEN_INGOT.get()), 1, 2900, 4500, fConsumer);
        Pressing(toI(POMitems.PYROLYTIC_CARBON.get()), 4, toI(POMitems.PLATE_CAST.get()), toI(POMitems.PYROLYTIC_CARBON_SHEET.get()), 1, 4000, 5000, fConsumer);
        Pressing(toI(Element.LEAD.dustTag()), 1, toI(POMitems.PLATE_CAST.get()), toI(POMitems.LEAD_PLATING.get()), 1, 700, 2000, fConsumer);
        Pressing(toI(Element.TUNGSTEN.dustTag()), 1, toI(POMitems.PLATE_CAST.get()), toI(POMitems.TUNGSTEN_PLATING.get()), 1, 3700, 5000, fConsumer);


        //chemical separating

        ChemicalSeperator(fConsumer, toCI(POMitems.ACANTHITE_DUST.get(), 2), toF(Fluids.WATER, 200), toF(POMfluids.SULFURIC_ACID_SOURCE.get(), 50), toCHI(Element.SILVER.dustTag(), 1, 1), toCHI(Element.SILVER.dustTag(), 1, 0.5f));
        ChemicalSeperator(fConsumer, toCI(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1), FluidStack.EMPTY, FluidStack.EMPTY, toCHI(Element.TITANIUM.dustTag(), 1, 1), toCHI(Element.BORON.dustTag(), 2, 1));

        ChemicalSeperator(fConsumer, toCI(Element.TITANIUM.dustTag(), 1), toF(Fluids.WATER, 150), toF(POMfluids.HYDROGEN_GAS_SOURCE.get(), 100), toCHI(POMitems.TITANIUM_OXIDE_DUST.get(), 1, 1));
        ChemicalSeperator(fConsumer, toCI(POMitems.MERCURY_SULFIDE_DUST.get(), 1), FluidStack.EMPTY, toF(POMfluids.MERCURY_SOURCE.get(), 100), toCHI(Element.SULFUR.dustTag(), 1, 1));
        ChemicalSeperator(fConsumer, toCI(Items.REDSTONE, 8), toF(Fluids.WATER, 500), toF(POMfluids.HYDROGEN_GAS_SOURCE.get(), 500), toCHI(POMitems.YELLOWCAKE_URANIUM.get(), 1, 1), toCHI(POMitems.REFINED_REDSTONE.get(), 8, 1));

        //chemical combining

        ChemicalCombining(fConsumer, toCHI(POMitems.MERCURY_SULFIDE_DUST.get(), 1, 1), toF(POMfluids.MERCURY_SOURCE.get(), 100), FluidStack.EMPTY, toCI(Element.SULFUR.dustTag(), 1));
        ChemicalCombining(fConsumer, toCHI(POMitems.PYROLYTIC_CARBON.get(), 1, 1), toF(POMfluids.HYDROGEN_GAS_SOURCE.get(), 250), FluidStack.EMPTY, toCI(POMitems.COAL_DUST.get(), 1));
        ChemicalCombining(fConsumer, toCHI(POMitems.RED_TUNGSTEN_DUST.get(), 3, 1), FluidStack.EMPTY, FluidStack.EMPTY, toCI(Element.TUNGSTEN.dustTag(), 1), toCI(POMitems.REFINED_REDSTONE.get(), 8));

        //ez crafting
        SimpleSurroundRecipe(Element.TITANIUM.nugget(), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.VIOLET_DIAMOND.get(), POMitems.VIOLET_DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.RED_DIAMOND.get(), POMitems.RED_DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(Element.COPPER.nugget(), Items.STICK, POMitems.COPPER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.SILVER.nugget(), Items.STICK, POMitems.SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TUNGSTEN.nugget(), Items.STICK, POMitems.TUNGSTEN_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.REDSTONE_IMBUED_SILVER_NUGGET.get(), Items.STICK, POMitems.REDSTONE_IMBUED_SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.RED_TUNGSTEN_NUGGET.get(), Items.STICK, POMitems.RED_TUNGSTEN_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.SUPERCONDUCTIVE_NUGGET.get(), Items.STICK, POMitems.SUPERCONDUCTIVE_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TITANIUM.item(), Items.NETHERITE_BLOCK, POMblocks.SIMPLE_CASING_1.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_GOLD_INGOT.get(), POMblocks.SIMPLE_CASING_1.get(), POMblocks.SIMPLE_CASING_2.get(), fConsumer);

        SimpleFullCrossRecipe(Items.OBSIDIAN, POMitems.NETHERITE_PLATING.get(), Items.NETHERITE_BLOCK, POMblocks.STRONG_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_GOLD_INGOT.get(), POMitems.TITANIUM_GOLD_PLATING.get(), POMblocks.STRONG_CASING.get(), POMblocks.STRENGTHENED_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRENGTHENED_CASING.get(), POMblocks.REINFORCED_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, Items.NETHER_STAR, POMitems.POWER_ORB.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, POMitems.CRUDE_POWER_CORE.get(), POMitems.POWER_ORB.get(), fConsumer, "_crude");
        SimpleFullCrossRecipe(POMitems.TITANIUM_PLATING.get(), Items.REDSTONE, Items.REDSTONE_BLOCK, POMitems.REDSTONE_CORE.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_GOLD_PLATING.get(), Items.AMETHYST_SHARD, POMitems.REDSTONE_CORE.get(), POMitems.CRUDE_POWER_CORE.get(), fConsumer);
        SimpleFullCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_PLATING.get(), POMitems.TITANIUM_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMitems.TITANIUM_GOLD_PLATING.get(), POMitems.TITANIUM_GOLD_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.FUSION_PLATING.get(), Items.HOPPER, POMitems.ADVANCED_CIRCUIT_BOARD_2.get(), POMblocks.FUSION_ITEM_PORT.get(), fConsumer);

        SimpleCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_COMPOUND.get(), POMitems.RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, POMitems.NETHERITE_BALL.get(), fConsumer);
        SimpleCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMitems.TITANIUM_GOLD_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), fConsumer);

        //compacting
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMblocks.TITANIUM_GOLD_BLOCK.get(), fConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), fConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCONDUCTIVE_NUGGET.get(), POMitems.SUPERCONDUCTIVE_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.RED_TUNGSTEN_NUGGET.get(), POMitems.RED_TUNGSTEN_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(Element.COPPER.nugget(), Items.COPPER_INGOT, fConsumer);
        SimpleCompactingRecipe(POMitems.ALUMINIUM_SCRAP.get(), POMblocks.ALUMINIUM_SCRAP_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.COPPER_WIRE.get(), POMblocks.COPPER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SILVER_WIRE.get(), POMblocks.SILVER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.TUNGSTEN_WIRE.get(), POMblocks.TUNGSTEN_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.REDSTONE_LAYERED_COPPER_WIRE.get(), POMblocks.REDSTONE_LAYERED_COPPER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.REDSTONE_IMBUED_SILVER_WIRE.get(), POMblocks.REDSTONE_IMBUED_SILVER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.REDSTONE_IMBUED_SILVER_NUGGET.get(), POMitems.REDSTONE_IMBUED_SILVER_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.RED_TUNGSTEN_WIRE.get(), POMblocks.RED_TUNGSTEN_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCONDUCTIVE_WIRE.get(), POMblocks.SUPERCONDUCTIVE_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.POWER_CELL.get(), POMblocks.POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.OVERCHARGED_POWER_CELL.get(), POMblocks.OVERCHARGED_POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCHARGED_POWER_CELL.get(), POMblocks.SUPERCHARGED_POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.VIOLET_DIAMOND.get(), POMblocks.VIOLET_DIAMOND_BLOCK.get(), fConsumer);


        //Furnace
        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), Element.TITANIUM.item(), 0.5f, 200, fConsumer, toItemP(POMitems.RAW_TITANIUM.get()), "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), Element.TITANIUM.item(), 0.5f, 200 , fConsumer, toItemP(POMblocks.ENDSTONE_TITANIUM_ORE.get().asItem()), "_from_ore");
        SimpleSmeltingRecipe(POMitems.RUSTED_PLATING.get(), Items.NETHERITE_SCRAP, 0.5f, 200, fConsumer, toItemP(POMitems.RUSTED_PLATING.get()), "");
        SimpleSmeltingRecipe(Element.ALUMINIUM.dust(), POMitems.ALUMINIUM_SCRAP.get(), 0.8f, 200 , fConsumer, toItemP(Element.ALUMINIUM.dustTag()), "");

        SimpleSmeltingRecipe(POMitems.TITANIUM_GOLD_DUST.get(), POMitems.TITANIUM_GOLD_INGOT.get(), 0.5f, 200 , fConsumer, toItemP(POMitems.TITANIUM_GOLD_DUST.get()), "");
        SimpleSmeltingRecipe(POMitems.REDSTONE_IMBUED_SILVER_DUST.get(), POMitems.REDSTONE_IMBUED_SILVER_INGOT.get(), 0.5f, 200 , fConsumer, toItemP(POMitems.REDSTONE_IMBUED_SILVER_DUST.get()), "");
        SimpleSmeltingRecipe(POMitems.RED_TUNGSTEN_DUST.get(), POMitems.RED_TUNGSTEN_INGOT.get(), 0.5f, 200 , fConsumer, toItemP(POMitems.RED_TUNGSTEN_DUST.get()), "");
        SimpleSmeltingRecipe(POMitems.SUPERCONDUCTIVE_DUST.get(), POMitems.SUPERCONDUCTIVE_INGOT.get(), 0.5f, 200 , fConsumer, toItemP(POMitems.SUPERCONDUCTIVE_DUST.get()), "");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.BIO_COMPOUND.get()),  "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.FIRE_PROOF_COMPOUND.get()), "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.REPELLING_COMPOUND.get()), "");

        //Smithing
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.STICK), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, POMitems.HAMMER.get())
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(fConsumer, toRL(getItemName(POMitems.HAMMER.get()) + "_smithing"));

        //Stonecutter
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_PLATING_SLAB.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.NETHERITE_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.NETHERITE_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.NETHERITE_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.NETHERITE_PLATING_SLAB.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_GOLD_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_GOLD_PLATING_SLAB.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_DIBORIDE_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TITANIUM_DIBORIDE_PLATING_SLAB.get()))+"_cutting");

        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.LEAD_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.LEAD_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.LEAD_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.LEAD_PLATING_SLAB.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TUNGSTEN_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TUNGSTEN_PLATING_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.TUNGSTEN_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.TUNGSTEN_PLATING_SLAB.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get(), 1)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.PYROLYTIC_CARBON_SHEET_STAIRS.get()))+"_cutting");
        SingleItemRecipeBuilder.stonecutting(toI(POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get(), 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer, toRL(getItemName(POMblocks.PYROLYTIC_CARBON_SHEET_SLAB.get()))+"_cutting");

        //auto
        for(Element m : Element.values()) {
            if (m.equals(Element.DEBUGIUM)) continue;
            SimpleAtomCompacting(m.atom64(), m.atom512(), fConsumer);
            Fusing(m, fConsumer);

            if (!m.isVanilla()) {
                autoPixelSplittingAndAssembling(m.item(), List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc." + m.elementName(), m.hexToRGB(0), m.hexToRGB(1), m.hexToRGB(2), fConsumer);
            }
            if (m.isMetal() && m.shouldAddDust()) {
                Pressing(toCI(m.dustTag(), 1), toI(POMitems.INGOT_CAST.get()), toCI(m.itemTag(), 1), m.getInfo().getMeltingPoint(), m.getInfo().getEvaporatingPoint(), fConsumer);
            }
            if (m.isMetal() && m.shouldAddDust() && m!=Element.ALUMINIUM) {
                SimpleSmeltingRecipe(m.dustTag(), m.item(), 1f, 200, fConsumer, toItemP(m.dustTag()), "_from_dust");
            }
            if (m.shouldAddDust() && m!=Element.ALUMINIUM && !m.isVanilla()) {
                Grinder(toI(m.itemTag()), fConsumer, toCHI(m.dustTag(), 1, 1));
            }
            if (m.shouldAddNugget() && !m.isVanilla()) {
                SimpleCompactingRecipe(toI(m.nugget()), m.item(), fConsumer);
                autoPixelSplittingAndAssembling(m.nugget(),  List.of(toCI(POMitems.PIXEL_PILE.get(), 1), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc."+m.elementName(), m.hexToRGB(0), m.hexToRGB(1), m.hexToRGB(2), fConsumer);
            }
            if (m.shouldAddBlock() && !m.isVanilla()) {
                SimpleCompactingRecipe(toI(m.item()), m.block(), fConsumer);
                autoPixelSplittingAndAssembling(m.blockItem(), List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get(), 1)), "element.pixelsofmc."+m.elementName(), m.hexToRGB(0), m.hexToRGB(1), m.hexToRGB(2), fConsumer);
            }
        }
    }

    private void SimpleFurnaceRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, trigger, extra);
    }
    private void SimpleFurnaceRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleFurnaceRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, trigger, extra);
    }
    private void SimpleFurnaceRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(trigger))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
    }

    //Immersive Engineering
    private void SimpleSmeltingRecipe(TagKey<Item> input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, trigger, extra);
    }
    private void SimpleSmeltingRecipe(ItemLike input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, smeltingTime, consumer, trigger, extra);
    }
    private void SimpleSmeltingRecipe(Ingredient input, ItemLike output, float xp, int smeltingTime, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, output, xp, smeltingTime)
                .unlockedBy("", inventoryTrigger(trigger))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
        SimpleCookingRecipeBuilder.blasting(input, RecipeCategory.MISC, output, xp, smeltingTime/2)
                .unlockedBy("", inventoryTrigger(trigger))
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
                .unlockedBy("", inventoryTrigger(toItemP(input.getItems()[0].getItem())))
                .save(consumer, toRL("compacting/" + input.getItems()[0].getItem() + "_to_" + output.asItem() + "_compacting" ));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, input.getItems()[0].getItem(), 9)
                .requires(output)
                .unlockedBy("", inventoryTrigger(toItemP(input.getItems()[0].getItem())))
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
                .unlockedBy("", inventoryTrigger(toItemP(ingot.getItems()[0].getItem())))
                .save(consumer, toRL("compacting/"+nugget.getItems()[0].getItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block, 1)
                .define('T', ingot)
                .pattern("TTT")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("", inventoryTrigger(toItemP(ingot.getItems()[0].getItem())))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+block.asItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget.getItems()[0].getItem(), 9)
                .requires(ingot)
                .unlockedBy("", inventoryTrigger(toItemP(ingot.getItems()[0].getItem())))
                .save(consumer, toRL("compacting/"+ingot.getItems()[0].getItem()+"_to_"+nugget.getItems()[0].getItem()+"_compacting"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot.getItems()[0].getItem(), 9)
                .requires(block)
                .unlockedBy("", inventoryTrigger(toItemP(ingot.getItems()[0].getItem())))
                .save(consumer, toRL("compacting/"+block.asItem()+"_to_"+ingot.getItems()[0].getItem()+"_compacting"));

    }
    private void SimpleSurroundRecipe(ItemLike around, ItemLike middle, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', around)
                .define('B', middle)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(toItemP(around.asItem()), toItemP(middle.asItem())))
                .save(consumer);
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer) {
        SimpleFullCrossRecipe(corners, sides, middle, output, consumer, "");
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer, String extra) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', corners)
                .define('B', sides)
                .define('C', middle)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(toItemP(middle.asItem()), toItemP(corners.asItem()), toItemP(sides.asItem())))
                .save(consumer, toRL(output.asItem() + extra));
    }
    private void SimpleCrossRecipe(ItemLike sides, ItemLike middle, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', sides)
                .define('B', middle)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("", inventoryTrigger(toItemP(middle.asItem()), toItemP(sides.asItem())))
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


    private void PixelSplitting(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), output, structure, r,g,b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void autoPixelSplitting(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), output, structure, new int[]{color1[0], color2[0], color3[0]},new int[]{color1[1], color2[1], color3[1]},new int[]{color1[2], color2[2], color3[2]})
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void PixelAssembling(ItemLike output, List<CountedIngredient> inputs, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        new PixelAssemblerRecipeBuilder(inputs, toCI(output, 1), structure, r, g, b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void PixelAssembling(ItemLike output, List<CountedIngredient> inputs, String structure, Consumer<FinishedRecipe> consumer) {
        new PixelAssemblerRecipeBuilder(inputs, toCI(output, 1), structure, new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0})
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void autoPixelAssembling(ItemLike output, List<CountedIngredient> inputs, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        new PixelAssemblerRecipeBuilder(inputs, toCI(output, 1), structure, new int[]{color1[0], color2[0], color3[0]},new int[]{color1[1], color2[1], color3[1]},new int[]{color1[2], color2[2], color3[2]})
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void PixelSplittingAndAssembling(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        PixelSplitting(ingredient, output, structure, r, g, b, consumer);
        PixelAssembling(ingredient, output, structure, r, g, b, consumer);
    }

    private void autoPixelSplittingAndAssembling(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        autoPixelSplitting(ingredient, output, structure, color1, color2, color3, consumer);
        autoPixelAssembling(ingredient, output, structure, color1, color2, color3, consumer);
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
        new ChemicalSeparatorRecipeBuilder(input, inputFluid, outputFluid, outputList)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void ChemicalCombining(Consumer<FinishedRecipe> consumer, ChanceIngredient output, FluidStack inputFluid, FluidStack outputFluid, CountedIngredient... inputs) {
        List<CountedIngredient> inputList = new java.util.ArrayList<>();
        Collections.addAll(inputList, inputs);
        new ChemicalCombinerRecipeBuilder(output, inputFluid, outputFluid, inputList)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }


    private void Fusing(Element element, Consumer<FinishedRecipe> consumer) {
        if (element.equals(Element.HYDROGEN)) {
            Fusing(element, false, element.getElement(), 0, element.getElement(), consumer);
            Fusing(element, true, element.getElement() * 8, 0, element.getElement() * 8, consumer);
        } else{
            Fusing(element, false, element.getElement(), element.getElement(), element.getElement(), consumer);
            Fusing(element, true, element.getElement() * 8, element.getElement() * 8, element.getElement() * 8, consumer);
        }
    }
    private void Fusing(Element element, boolean x512, int proton, int neutron, int electron, Consumer<FinishedRecipe> consumer) {
        new FusionRecipeBuilder(element, proton, neutron, electron, x512)
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

    private ItemPredicate toItemP(Item... items) {
        return new ItemPredicate(
                null,
                Set.of(items),
                MinMaxBounds.Ints.ANY,
                MinMaxBounds.Ints.ANY,
                EnchantmentPredicate.NONE,
                EnchantmentPredicate.NONE,
                null,
                NbtPredicate.ANY);
    }
    private ItemPredicate toItemP(TagKey<Item> tag) {
        return new ItemPredicate(
                tag,
                null,
                MinMaxBounds.Ints.ANY,
                MinMaxBounds.Ints.ANY,
                EnchantmentPredicate.NONE,
                EnchantmentPredicate.NONE,
                null,
                NbtPredicate.ANY);
    }

    //Immersive Engineering
    private ResourceLocation toRL(String string) {
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