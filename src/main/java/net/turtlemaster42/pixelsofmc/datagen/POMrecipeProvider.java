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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.turtlemaster42.pixelsofmc.recipe.builders.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.*;
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
                .requires(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .requires(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Items.SOUL_SAND), toItemP(Items.BLAZE_POWDER), toItemP(POMitems.COAL_DUST.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.WHITE_DYE, 2)
                .requires(POMitems.TITANIUM_OXIDE_DUST.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_OXIDE_DUST.get())))
                .save(fConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.MANA_AMALGAMATION.get())
                .requires(Items.ENDER_PEARL)
                .requires(Items.BLAZE_POWDER)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(POMitems.OBSIDIAN_DUST.get())
                .requires(Element.SILVER.dustTag())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Items.ENDER_PEARL), toItemP(POMitems.OBSIDIAN_DUST.get()), toItemP(Items.BLAZE_POWDER)))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.MANA_SPHERE.get())
                .define('A', POMitems.MANA_AMALGAMATION.get())
                .define('B', Tags.Items.GEMS_DIAMOND)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.MANA_AMALGAMATION.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.GLEAMING_MANA_SPHERE.get())
                .define('A', Tags.Items.DUSTS_GLOWSTONE)
                .define('B', POMitems.MANA_SPHERE.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.MANA_SPHERE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.CRIMSON_MANA_SPHERE.get())
                .define('A', Tags.Items.DUSTS_REDSTONE)
                .define('B', POMitems.MANA_SPHERE.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.MANA_SPHERE.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.DENSE_CARBON_CUBE.get())
                .define('C', Element.CARBON.item())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.dust())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.CARBONARO_CLUMP.get())
                .define('C', POMitems.DENSE_CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.dust())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.BLACK_DIAMOND.get())
                .define('C', POMitems.CARBONARO_CLUMP.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Element.CARBON.dust())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND)
                .define('C', POMitems.BLACK_DIAMOND.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BLACK_DIAMOND.get())))
                .save(fConsumer, toRL(Items.DIAMOND.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.COPPER_SHEET.get())
                .define('C', POMtags.getTagsFor(Element.COPPER).nugget)
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Items.COPPER_INGOT), toItemP(POMtags.getTagsFor(Element.COPPER).nugget)))
                .save(fConsumer);


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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.OBSIDIAN_PLATING.get())
                .requires(Items.LAVA_BUCKET)
                .requires(POMitems.OBSIDIAN_DUST.get())
                .requires(POMitems.OBSIDIAN_DUST.get())
                .requires(POMitems.OBSIDIAN_DUST.get())
                .requires(POMitems.OBSIDIAN_DUST.get())
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
                .define('B', POMitems.SILICON_SHEET.get())
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', POMtags.getTagsFor(Element.SILVER).nugget)
                .define('E', POMitems.REDSTONE_CORE.get())
                .pattern("AEA")
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
                .pattern("AAA")
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
                .save(fConsumer, toRL(POMitems.POWER_CELL.get() + "_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.OVERCHARGED_POWER_CELL.get())
                .requires(POMitems.EMPTY_CELL.get())
                .requires(POMitems.OVERCHARGED_POWER_ORB.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.OVERCHARGED_POWER_ORB.get()), toItemP(POMitems.EMPTY_CELL.get())))
                .save(fConsumer, toRL(POMitems.OVERCHARGED_POWER_CELL.get() + "_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.SUPERCHARGED_POWER_CELL.get())
                .requires(POMitems.EMPTY_CELL.get())
                .requires(POMitems.SUPERCHARGED_POWER_ORB.get())
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SUPERCHARGED_POWER_ORB.get()), toItemP(POMitems.EMPTY_CELL.get())))
                .save(fConsumer, toRL(POMitems.SUPERCHARGED_POWER_CELL.get() + "_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.URANIUM_FUEL_CELL.get())
                .requires(POMitems.URANIUM_FUEL_PELLET.get(), 4)
                .requires(POMitems.EMPTY_FUEL_CELL.get())
                .requires(POMitems.URANIUM_FUEL_PELLET.get(), 4)
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.EMPTY_FUEL_CELL.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.PLUTONIUM_FUEL_CELL.get())
                .requires(POMitems.PLUTONIUM_FUEL_PELLET.get(), 4)
                .requires(POMitems.EMPTY_FUEL_CELL.get())
                .requires(POMitems.PLUTONIUM_FUEL_PELLET.get(), 4)
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.EMPTY_FUEL_CELL.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.ENRICHED_URANIUM_FUEL_CELL.get())
                .requires(POMitems.EMPTY_FUEL_CELL.get())
                .requires(POMitems.URANIUM_FUEL_CORE.get())
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.EMPTY_FUEL_CELL.get())))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POMitems.ENRICHED_PLUTONIUM_FUEL_CELL.get())
                .requires(POMitems.EMPTY_FUEL_CELL.get())
                .requires(POMitems.PLUTONIUM_FUEL_CORE.get())
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.EMPTY_FUEL_CELL.get())))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_COUNTER.get())
                .define('A', Element.TITANIUM.nuggetTag())
                .define('B', Tags.Items.INGOTS_GOLD)
                .define('C', Items.COMPARATOR)
                .define('D', Items.DROPPER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM, toItemP(Element.TITANIUM.nuggetTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_TIMER.get())
                .define('A', Items.PISTON)
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('C', Items.COMPARATOR)
                .define('D', Items.HOPPER)
                .define('E', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .pattern("ECE")
                .pattern("ABA")
                .pattern("EDE")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(Tags.Items.STORAGE_BLOCKS_REDSTONE), toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.REDSTONE_DETECTOR.get())
                .define('A', POMitems.ROYAL_TUNGSTEN_INGOT.get())
                .define('B', Items.REDSTONE_TORCH)
                .define('C', Items.CALIBRATED_SCULK_SENSOR)
                .define('D', Items.COMPARATOR)
                .define('E', Items.DAYLIGHT_DETECTOR)
                .define('F', Items.OBSERVER)
                .pattern("ABA")
                .pattern("ECF")
                .pattern("ADA")
                .unlockedBy("", inventoryTrigger(
                        toItemP(Items.CALIBRATED_SCULK_SENSOR)))
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
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
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


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.TITANIUM_UPGRADE_TEMPLATE.get(), 1)
                .define('A', Element.TITANIUM.itemTag())
                .define('B', Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .define('C', Tags.Items.GEMS_DIAMOND)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TITANIUM.itemTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.TITANIUM_UPGRADE_TEMPLATE.get(), 2)
                .define('A', Element.TITANIUM.itemTag())
                .define('B', POMitems.TITANIUM_UPGRADE_TEMPLATE.get())
                .define('C', Tags.Items.GEMS_DIAMOND)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TITANIUM.itemTag())))
                .save(fConsumer, toRL(getItemName(POMitems.TITANIUM_UPGRADE_TEMPLATE.get()) + "_altern"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get(), 1)
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', POMitems.TITANIUM_UPGRADE_TEMPLATE.get())
                .define('C', POMitems.VIOLET_DIAMOND.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TITANIUM.itemTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get(), 2)
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get())
                .define('C', POMitems.VIOLET_DIAMOND.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TITANIUM.itemTag())))
                .save(fConsumer, toRL(getItemName(POMitems.TITANIUM_UPGRADE_TEMPLATE.get()) + "_altern"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SPEED_UPGRADE_1.get())
                .define('A', Element.TITANIUM.itemTag())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.TITANIUM.dustTag())
                .define('D', POMitems.TITANIUM_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM, toItemP(Element.TITANIUM.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENERGY_UPGRADE_1.get())
                .define('A', Element.TITANIUM.itemTag())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.GOLD.dustTag())
                .define('D', POMitems.TITANIUM_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM, toItemP(Element.GOLD.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.HEAT_UPGRADE_1.get())
                .define('A', Element.TITANIUM.itemTag())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Element.COPPER.dustTag())
                .define('D', POMitems.TITANIUM_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM, toItemP(Element.COPPER.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SPEED_UPGRADE_2.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Element.SILVER.itemTag())
                .define('C', POMitems.TITANIUM_GOLD_DUST.get())
                .define('D', POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(POMitems.TITANIUM_GOLD_DUST.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.ENERGY_UPGRADE_2.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Element.SILVER.itemTag())
                .define('C', Element.TUNGSTEN.dustTag())
                .define('D', POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
                .unlockedBy("has_items", inventoryTrigger(
                        HAS_TITANIUM_DIBORIDE, toItemP(Element.TUNGSTEN.dustTag())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.HEAT_UPGRADE_2.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Element.SILVER.itemTag())
                .define('C', Element.COBALT.dustTag())
                .define('D', POMitems.TITANIUM_DIBORIDE_UPGRADE_TEMPLATE.get())
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" D ")
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
                .define('C', POMblocks.TITANIUM_GOLD_BLOCK.get())
                .define('D', POMblocks.STRONG_CASING.get())
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
                .define('E', POMitems.RED_SILVER_WIRE.get())
                .define('F', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .define('G', POMblocks.STRENGTHENED_CASING.get())
                .pattern("ABA")
                .pattern("CGE")
                .pattern("FDF")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.CHEMICAL_COMBINER.get())
                .define('A', POMitems.EMPTY_CELL.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('C', POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('D', POMblocks.PERFECTED_CASING_2.get())
                .define('E', POMitems.RED_SILVER_WIRE.get())
                .define('F', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .define('G', POMblocks.STRENGTHENED_CASING.get())
                .pattern("CBE")
                .pattern("AGA")
                .pattern("FDF")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.CHEMICAL_MIXER.get())
                .define('A', POMitems.EMPTY_CELL.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('C', POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('D', POMblocks.PERFECTED_CASING_2.get())
                .define('E', POMitems.RED_SILVER_WIRE.get())
                .define('F', POMblocks.STRENGTHENED_CASING.get())
                .pattern("ABA")
                .pattern("AFA")
                .pattern("CDE")
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
                .pattern("AGA")
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
                .pattern("AGA")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.BIO_PLASTIC.get(), POMitems.MICRO_CHIP.get(), POMitems.COPPER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('A', POMitems.FIRE_PROOF_PLASTIC.get())
                .define('B', Tags.Items.DUSTS_REDSTONE)
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
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', POMitems.RED_SILVER_WIRE.get())
                .define('D', POMitems.IMPROVED_MICRO_CHIP.get())
                .define('E', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('F', POMitems.REDSTONE_TIMER.get())
                .define('G', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .pattern("EDE")
                .pattern("AGA")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.IMPROVED_MICRO_CHIP.get(), POMitems.SILVER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.PERFECTED_CIRCUIT_BOARD_2.get())
                .define('A', POMitems.FIRE_PROOF_PLASTIC.get())
                .define('B', POMitems.VIOLET_DIAMOND_LENS.get())
                .define('C', POMitems.RED_SILVER_WIRE.get())
                .define('D', POMitems.IMPROVED_MICRO_CHIP.get())
                .define('E', POMitems.SILVER_WIRE.get())
                .define('F', POMitems.DRAGON_SENSOR.get())
                .define('G', POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .pattern("EDE")
                .pattern("AGA")
                .pattern("FCB")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.IMPROVED_MICRO_CHIP.get(), POMitems.SILVER_WIRE.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMitems.COPPER_HEAT_SINK.get())
                .define('A', POMitems.COPPER_SHEET.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(toItemP(POMitems.COPPER_SHEET.get())))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.ADVANCED_CASING_1.get())
                .define('A', POMblocks.SIMPLE_CASING_1.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.INGOTS_GOLD)
                .define('E', POMitems.POWER_ORB.get())
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
                .define('E', POMitems.OVERCHARGED_POWER_ORB.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BEB")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_GOLD))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.PERFECTED_CASING_2.get())
                .define('A', POMblocks.ADVANCED_CASING_2.get())
                .define('B', POMitems.TITANIUM_DIBORIDE_PLATING.get())
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.HEAT_SINK.get())
                .define('A', POMitems.FUSION_PLATING.get())
                .define('B', POMitems.COPPER_HEAT_SINK.get())
                .define('C', POMitems.REDSTONE_CORE.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(HAS_TITANIUM_DIBORIDE))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.FUEL_CELL_HOLDER.get())
                .define('A', POMitems.LEAD_PLATING.get())
                .define('B', POMitems.COPPER_HEAT_SINK.get())
                .define('C', POMitems.SIMPLE_CIRCUIT_BOARD_2.get())
                .define('D', POMblocks.FISSION_CASING.get())
                .pattern("ACA")
                .pattern("B B")
                .pattern("ADA")
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.LEAD_PLATING.get())))
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
                .define('D', POMblocks.RED_SILVER_SPOOL.get())
                .define('E', POMitems.REFINED_REDSTONE.get())
                .pattern("ABA")
                .pattern("EDE")
                .pattern("ACA")
                .unlockedBy("has_items", inventoryTrigger(HAS_FUSION_PLATING))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get())
                .define('A', POMitems.FUSION_PLATING.get())
                .define('B', POMitems.ADVANCED_CIRCUIT_BOARD_2.get())
                .define('C', POMitems.REDSTONE_CORE.get())
                .define('D', POMblocks.SUPERCONDUCTIVE_SPOOL.get())
                .pattern("ABA")
                .pattern("ADA")
                .pattern("ACA")
                .unlockedBy("has_items", inventoryTrigger(HAS_FUSION_PLATING))
                .save(fConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CUT_COPPER)
                .define('C', POMitems.COPPER_SHEET.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.COPPER_SHEET.get())))
                .save(fConsumer, toRL(Items.CUT_COPPER.toString()));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.TITANIUM_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.NETHERITE_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.NETHERITE_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.NETHERITE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_GOLD_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.TITANIUM_GOLD_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_GOLD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TITANIUM_DIBORIDE_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.TITANIUM_DIBORIDE_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TITANIUM_DIBORIDE_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.LEAD_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.LEAD_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.LEAD_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.TUNGSTEN_PLATING_BLOCK.get(), 4)
                .define('A', POMitems.TUNGSTEN_PLATING.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.TUNGSTEN_PLATING.get())))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.PYROLYTIC_CARBON_SHEET_BLOCK.get(), 4)
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

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, POMblocks.MULTIBLOCK_CASING.get(), 4)
                .define('A', Element.TITANIUM.itemTag())
                .define('B', POMitems.TITANIUM_PLATING.get())
                .define('C', POMblocks.SIMPLE_CASING_1.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("has_items", inventoryTrigger(toItemP(POMitems.TITANIUM_PLATING.get().asItem())))
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_TORCH, 6)
                .define('A', POMitems.SOUL_COAL.get())
                .define('B', Items.STICK)
                .pattern("A")
                .pattern("B")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SOUL_COAL.get())))
                .save(fConsumer, toRL(Items.SOUL_TORCH.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_CAMPFIRE)
                .define('A', POMitems.SOUL_COAL.get())
                .define('B', Items.STICK)
                .define('C', ItemTags.LOGS_THAT_BURN)
                .pattern(" B ")
                .pattern("BAB")
                .pattern("CCC")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SOUL_COAL.get())))
                .save(fConsumer, toRL(Items.SOUL_CAMPFIRE.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.TNT)
                .define('A', Tags.Items.SAND)
                .define('B', POMitems.RED_OIL_BUCKET.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_items", inventoryTrigger(
                        toItemP(POMitems.SOUL_COAL.get())))
                .save(fConsumer, toRL(Items.SOUL_CAMPFIRE.toString()));



        // --GRINDING--
        GrinderRecipeBuilder.build(Items.NETHERITE_SCRAP)
                .output(POMitems.ANCIENT_DEBRIS_DUST.get())
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMitems.TITANIUM_GOLD_INGOT.get())
                .output(POMitems.TITANIUM_GOLD_DUST.get())
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.NETHERITE_INGOT)
                .output(POMitems.NETHERITE_DUST.get())
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .output(POMitems.TITANIUM_DIBORIDE_DUST.get())
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.ANCIENT_DEBRIS)
                .output(POMitems.ANCIENT_DEBRIS_DUST.get())
                .output(POMitems.ANCIENT_DEBRIS_DUST.get(), .75f)
                .output(POMitems.ANCIENT_DEBRIS_DUST.get(), .25f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(Items.FLINT)
                .output(POMitems.QUARTZ_DUST.get(), 0.7f)
                .output(Element.CALCIUM.dust(),0.25f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.STONE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.COBBLESTONE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.GRAVEL)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.2f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.SAND)
                .output(POMitems.QUARTZ_DUST.get())
                .output(POMitems.QUARTZ_DUST.get(), 0.2f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.SANDSTONE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.RED_SAND)
                .output(POMitems.QUARTZ_DUST.get())
                .output(POMitems.QUARTZ_DUST.get(), 0.2f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.GOLD.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.RED_SANDSTONE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.GOLD.dust(),0.01f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(Items.DEEPSLATE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .output(Element.LITHIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.COBBLED_DEEPSLATE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.8f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.ALUMINIUM.dust(),0.01f)
                .output(Element.LITHIUM.dust(),0.01f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(Items.GRANITE)
                .output(POMitems.QUARTZ_DUST.get(), 3)
                .output(POMitems.QUARTZ_DUST.get(), 0.2f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.TUNGSTEN.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.DIORITE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.4f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.CALCIUM.dust(),0.02f)
                .output(Element.MAGNESIUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.ANDESITE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.QUARTZ_DUST.get(), 0.4f)
                .output(Element.ALUMINIUM.dust(),0.15f)
                .output(Element.IRON.dust(),0.05f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(Items.CALCITE)
                .output(Element.CALCIUM.dust())
                .output(Element.CALCIUM.dust(),0.6f)
                .output(Element.CARBON.dust(),0.1f)
                .output(Element.MAGNESIUM.dust(),0.04f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.TUFF)
                .output(Element.SULFUR.dust(),0.1f)
                .output(Element.CARBON.dust(),0.1f)
                .output(Element.IRON.dust(),0.05f)
                .output(Element.LEAD.dust(),0.02f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.DRIPSTONE_BLOCK)
                .output(Element.CALCIUM.dust(),0.6f)
                .output(POMitems.QUARTZ_DUST.get(), 0.4f)
                .output(Element.COPPER.dust(),0.05f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.POINTED_DRIPSTONE)
                .output(Element.CALCIUM.dust(),0.4f)
                .output(POMitems.QUARTZ_DUST.get(), 0.08f)
                .output(Element.COPPER.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.NETHERRACK)
                .output(POMitems.MERCURY_SULFIDE_DUST.get(), 2)
                .output(POMitems.MERCURY_SULFIDE_DUST.get(),0.1f)
                .output(POMitems.QUARTZ_DUST.get(), 0.4f)
                .output(Element.SULFUR.dust(),0.05f)
                .output(Element.GOLD.dust(),0.05f)
                .output(Element.COBALT.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.MAGMA_BLOCK)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(POMitems.MERCURY_SULFIDE_DUST.get(), 0.1f)
                .output(Element.IRON.dust(), 0.05f)
                .output(Element.GOLD.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.BLACKSTONE)
                .output(Element.BORON.dust(), 0.4f)
                .output(Element.IRON.dust(), 0.4f)
                .output(POMitems.QUARTZ_DUST.get(), 0.1f)
                .output(Element.NICKEL.dust(),0.05f)
                .output(Element.COBALT.dust(),0.05f)
                .output(Element.PLATINUM.dust(),0.01f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.BASALT)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(Element.IRON.dust(), 0.1f)
                .output(Element.MAGNESIUM.dust(), 0.05f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .output(POMitems.QUARTZ_DUST.get())
                .output(POMitems.QUARTZ_DUST.get(),0.4f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.OBSIDIAN)
                .output(POMitems.OBSIDIAN_DUST.get(), 2)
                .output(POMitems.OBSIDIAN_DUST.get(),0.8f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.CRYING_OBSIDIAN)
                .output(POMitems.OBSIDIAN_DUST.get())
                .output(POMitems.CRYING_OBSIDIAN_DUST.get(),0.7f)
                .output(POMitems.CRYING_OBSIDIAN_DUST.get(),0.2f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.END_STONE)
                .output(POMitems.QUARTZ_DUST.get(), 2)
                .output(Element.POTASSIUM.dust(), 0.1f)
                .output(Element.TITANIUM.dust(), 0.01f)
                .output(POMitems.ACANTHITE_DUST.get(),0.01f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(Tags.Items.RAW_MATERIALS_IRON)
                .output(Element.IRON.dust())
                .output(Element.IRON.dust(), 0.5f)
                .output(POMitems.QUARTZ_DUST.get(), 0.1f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Tags.Items.RAW_MATERIALS_COPPER)
                .output(Element.COPPER.dust())
                .output(Element.COPPER.dust(), 0.5f)
                .output(POMitems.QUARTZ_DUST.get(), 0.1f)
                .output(Element.SULFUR.dust(), 0.05f)
                .output(POMitems.COAL_DUST.get(), 0.05f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Tags.Items.RAW_MATERIALS_GOLD)
                .output(Element.GOLD.dust())
                .output(Element.GOLD.dust(), 0.5f)
                .output(POMitems.QUARTZ_DUST.get(), 0.1f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMitems.RAW_TITANIUM.get())
                .output(Element.TITANIUM.dust())
                .output(Element.TITANIUM.dust(), 0.5f)
                .output(POMitems.QUARTZ_DUST.get(), 0.1f)
                .output(Element.IRON.dust(), 0.05f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(POMblocks.ACANTHITE.get().asItem())
                .output(POMitems.ACANTHITE_DUST.get(), 12)
                .output(POMitems.ACANTHITE_DUST.get(), 6, 0.5f)
                .output(Element.SILVER.dust(), 0.02f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMblocks.ACANTHITE_ORE.get().asItem())
                .output(POMitems.ACANTHITE_DUST.get(), 10)
                .output(POMitems.ACANTHITE_DUST.get(), 4, 0.5f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMblocks.LESSER_ACANTHITE_ORE.get().asItem())
                .output(POMitems.ACANTHITE_DUST.get(), 5)
                .output(POMitems.ACANTHITE_DUST.get(), 2, 0.5f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMblocks.ACANTHITE_SPIKE.get().asItem())
                .output(POMitems.ACANTHITE_DUST.get(), 5)
                .output(POMitems.ACANTHITE_DUST.get(), 2, 0.5f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Tags.Items.ORES_IRON)
                .output(Element.IRON.dust(), 4)
                .output(Element.IRON.dust(), 0.5f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Tags.Items.ORES_COPPER)
                .output(Element.COPPER.dust(), 20)
                .output(Element.COPPER.dust(), 5, 0.5f)
                .output(Element.SULFUR.dust(), 0.15f)
                .output(POMitems.COAL_DUST.get(), 0.15f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Tags.Items.ORES_GOLD)
                .output(Element.GOLD.dust(), 4)
                .output(Element.GOLD.dust(), 0.5f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(POMtags.Items.ORES_TITANIUM)
                .output(Element.TITANIUM.dust(), 4)
                .output(Element.TITANIUM.dust(), 0.5f)
                .output(Element.IRON.dust(), 0.2f)
                .finish(fConsumer, this);

        GrinderRecipeBuilder.build(ItemTags.WOOL)
                .output(Items.STRING, 2)
                .output(Items.STRING, 0.33f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(ItemTags.WOOL_CARPETS)
                .output(Items.STRING, 2)
                .output(Items.STRING, 0.33f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.COBWEB)
                .output(Items.STRING, 2)
                .output(Items.STRING, 0.33f)
                .output(Items.STRING, 0.33f)
                .finish(fConsumer, this);
        GrinderRecipeBuilder.build(Items.GLOWSTONE)
                .output(Items.GLOWSTONE_DUST, 4)
                .finish(fConsumer, this);

        // --MILLING--
        BallMillRecipeBuilder.build(Items.AMETHYST_SHARD, 4).ball(POMtags.Items.BALL_4)
                .input(Items.AMETHYST_BLOCK)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.AMETHYST_SHARD, 4, 3.2f).ball(POMtags.Items.BALL_3)
                .input(Items.AMETHYST_CLUSTER)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.AMETHYST_SHARD, 2, 1.6f).ball(POMtags.Items.BALL_3)
                .input(Items.LARGE_AMETHYST_BUD)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.AMETHYST_SHARD, .8f).ball(POMtags.Items.BALL_3)
                .input(Items.MEDIUM_AMETHYST_BUD)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.AMETHYST_SHARD, .2f).ball(POMtags.Items.BALL_3)
                .input(Items.SMALL_AMETHYST_BUD)
                .finish(fConsumer, this);

        BallMillRecipeBuilder.build(Items.DISC_FRAGMENT_5, 3, 2.3f).ball(POMtags.Items.BALL_3)
                .input(Items.MUSIC_DISC_5)
                .finish(fConsumer, this);

        BallMillRecipeBuilder.build(Items.GRAVEL).ball(POMtags.Items.BALL_5)
                .input(Items.COBBLESTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND).ball(POMtags.Items.BALL_5)
                .input(Items.GRAVEL)
                .finish(fConsumer, this);

        BallMillRecipeBuilder.build(Items.MELON_SLICE, 8.5f).ball(POMtags.Items.BALL_1)
                .input(Items.MELON)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MELON_SEEDS, 1.5f).ball(POMtags.Items.BALL_3)
                .input(Items.MELON_SLICE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PUMPKIN_SEEDS, 6).ball(POMtags.Items.BALL_3)
                .input(Items.PUMPKIN)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PUMPKIN_SEEDS, 1).ball(POMtags.Items.BALL_3)
                .input(Items.CARVED_PUMPKIN)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.HONEYCOMB).ball(POMtags.Items.BALL_1)
                .input(Items.HONEYCOMB_BLOCK)
                .finish(fConsumer, this);

        BallMillRecipeBuilder.build(Items.SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.CHISELED_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.CUT_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND, 2).ball(POMtags.Items.BALL_3)
                .input(Items.SANDSTONE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND, 2).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_SANDSTONE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND).ball(POMtags.Items.BALL_3)
                .input(Items.SANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_SANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND).ball(POMtags.Items.BALL_3)
                .input(Items.CUT_STANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.SAND).ball(POMtags.Items.BALL_3)
                .input(Items.SANDSTONE_WALL)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.RED_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_RED_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.CHISELED_RED_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 3).ball(POMtags.Items.BALL_3)
                .input(Items.CUT_RED_SANDSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 2).ball(POMtags.Items.BALL_3)
                .input(Items.RED_SANDSTONE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND, 2).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_RED_SANDSTONE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND).ball(POMtags.Items.BALL_3)
                .input(Items.RED_SANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND).ball(POMtags.Items.BALL_3)
                .input(Items.SMOOTH_RED_SANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND).ball(POMtags.Items.BALL_3)
                .input(Items.CUT_RED_SANDSTONE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_SAND).ball(POMtags.Items.BALL_3)
                .input(Items.RED_SANDSTONE_WALL)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD, 3).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD, 2).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_WALL)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,7).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_BRICKS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,4).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_BRICK_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,3).ball(POMtags.Items.BALL_4)
                .input(Items.PRISMARINE_BRICK_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,6).ball(POMtags.Items.BALL_4)
                .input(Items.DARK_PRISMARINE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,4).ball(POMtags.Items.BALL_4)
                .input(Items.DARK_PRISMARINE_STAIRS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_SHARD,2).ball(POMtags.Items.BALL_4)
                .input(Items.DARK_PRISMARINE_SLAB)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PRISMARINE_CRYSTALS,4.33f).ball(POMtags.Items.BALL_3)
                .input(Items.SEA_LANTERN)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BRICK,1.5f).ball(POMtags.Items.BALL_3)
                .input(Items.FLOWER_POT)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.GLOWSTONE_DUST,4).ball(POMtags.Items.BALL_3)
                .input(Items.GLOWSTONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.ROYAL_TUNGSTEN_DUST.get(),2).ball(POMtags.Items.BALL_4)
                .input(POMitems.ROYAL_TUNGSTEN_AMALGAMATION.get())
                .finish(fConsumer, this);

        // --DUST MIXING--
        BallMillRecipeBuilder.build(POMitems.ANCIENT_DEBRIS_DUST.get(),3).ball(POMtags.Items.BALL_5)
                .input(POMitems.MERCURY_SULFIDE_DUST.get())
                .input(POMitems.TITANIUM_DIBORIDE_DUST.get())
                .input(POMitems.TITANIUM_OXIDE_DUST.get())
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.NETHERITE_DUST.get(),2).ball(POMtags.Items.BALL_4)
                .input(Element.GOLD.dustTag(), 2)
                .input(POMitems.ANCIENT_DEBRIS_DUST.get(), 2)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_DUST.get(),2).ball(POMtags.Items.BALL_4)
                .input(Element.TITANIUM.dustTag())
                .input(Element.BORON.dustTag(), 2)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.TITANIUM_GOLD_DUST.get(),4).ball(POMtags.Items.BALL_4)
                .input(Element.TITANIUM.dustTag(), 3)
                .input(Element.GOLD.dustTag())
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.SUPERCONDUCTIVE_DUST.get(), 2).ball(POMtags.Items.BALL_4)
                .input(Element.TITANIUM.dustTag())
                .input(Element.NIOBIUM.dustTag())
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.RED_SILVER_DUST.get(),2).ball(POMtags.Items.BALL_3)
                .input(Element.SILVER.dustTag())
                .input(Items.REDSTONE)
                .finish(fConsumer, this);

        //flowers
        BallMillRecipeBuilder.build(Items.YELLOW_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.DANDELION)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.POPPY)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIGHT_BLUE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_ORCHID)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MAGENTA_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.ALLIUM)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIGHT_GRAY_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.AZURE_BLUET)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.RED_TULIP)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.ORANGE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.ORANGE_TULIP)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.WHITE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_TULIP)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PINK_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.PINK_TULIP)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIGHT_GRAY_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.OXEYE_DAISY)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BLUE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.CORNFLOWER)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.WHITE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.LILY_OF_THE_VALLEY)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BLACK_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.WITHER_ROSE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PINK_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.SPORE_BLOSSOM)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PINK_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.PINK_PETALS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.ORANGE_DYE,2).ball(POMtags.Items.BALL_2)
                .input(Items.TORCHFLOWER)
                .finish(fConsumer, this);

        BallMillRecipeBuilder.build(Items.YELLOW_DYE,4).ball(POMtags.Items.BALL_2)
                .input(Items.SUNFLOWER)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MAGENTA_DYE,4).ball(POMtags.Items.BALL_2)
                .input(Items.LILAC)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PINK_DYE,4).ball(POMtags.Items.BALL_2)
                .input(Items.PEONY)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.RED_DYE,4).ball(POMtags.Items.BALL_2)
                .input(Items.ROSE_BUSH)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.CYAN_DYE,4).ball(POMtags.Items.BALL_2)
                .input(Items.PITCHER_PLANT)
                .finish(fConsumer, this);

        //crushing
        BallMillRecipeBuilder.build(Items.SUGAR,2.5f).ball(POMtags.Items.BALL_3)
                .input(Items.SUGAR_CANE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BLAZE_POWDER,4.5f).ball(POMtags.Items.BALL_3)
                .input(Items.BLAZE_ROD)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BONE_MEAL, 2, 2.5f).ball(POMtags.Items.BALL_3)
                .input(Items.BONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.COBBLESTONE).ball(POMtags.Items.BALL_3)
                .input(Items.STONE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.COBBLED_DEEPSLATE).ball(POMtags.Items.BALL_3)
                .input(Items.DEEPSLATE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(POMitems.COAL_DUST.get()).ball(POMtags.Items.BALL_3)
                .input(ItemTags.COALS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.WHITE_DYE, 4).ball(POMtags.Items.BALL_4)
                .input(POMitems.TITANIUM_OXIDE_DUST.get())
                .finish(fConsumer, this);


        //dye source
        BallMillRecipeBuilder.build(Items.RED_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.BEETROOT)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BLACK_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.INK_SAC)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BROWN_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.COCOA_BEANS)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.WHITE_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.BONE_MEAL)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.BLUE_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.LAPIS_LAZULI)
                .finish(fConsumer, this);


        //dye mixing
        BallMillRecipeBuilder.build(Items.LIGHT_GRAY_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_DYE, 2)
                .input(Items.BLACK_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIGHT_GRAY_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_DYE)
                .input(Items.GRAY_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.GRAY_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_DYE)
                .input(Items.BLACK_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.ORANGE_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.YELLOW_DYE)
                .input(Items.RED_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIME_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_DYE)
                .input(Items.GREEN_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.CYAN_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_DYE)
                .input(Items.GREEN_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.LIGHT_BLUE_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_DYE)
                .input(Items.WHITE_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PURPLE_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_DYE)
                .input(Items.RED_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MAGENTA_DYE, 4).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_DYE)
                .input(Items.RED_DYE)
                .input(Items.WHITE_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MAGENTA_DYE, 3).ball(POMtags.Items.BALL_2)
                .input(Items.BLUE_DYE)
                .input(Items.RED_DYE)
                .input(Items.PINK_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.MAGENTA_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.PURPLE_DYE)
                .input(Items.PINK_DYE)
                .finish(fConsumer, this);
        BallMillRecipeBuilder.build(Items.PINK_DYE, 2).ball(POMtags.Items.BALL_2)
                .input(Items.WHITE_DYE)
                .input(Items.RED_DYE)
                .finish(fConsumer, this);

        // --PIXEL SPLITTER--
        PixelSplittingAndAssembling(POMblocks.TITANIUM_GOLD_BLOCK.get().asItem(), List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get())), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);
        PixelSplittingAndAssembling(POMitems.TITANIUM_GOLD_NUGGET.get(), List.of(toCI(POMitems.PIXEL.get(), 9)), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);
        PixelSplittingAndAssembling(POMitems.TITANIUM_GOLD_INGOT.get(), List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get())), "structure.pixelsofmc.titanium_gold", toAInt(232, 197, 152), toAInt(229, 153, 95), toAInt(166, 81, 53), fConsumer);

        //pixel assembler
//        PixelAssembling(POMitems.VOID_EYE.get(), List.of(toCI(POMitems.DRAGON_EYE.get(), 9)), "", fConsumer);
//        autoPixelAssembling(Items.AMETHYST_SHARD, List.of(toCI(POMitems.PIXEL.get(), 9), toCI(Items.LAPIS_LAZULI, 9)), "element.pixelsofmc.helium", Element.HELIUM.hexToRGB(0), Element.HELIUM.hexToRGB(1), Element.HELIUM.hexToRGB(2),  fConsumer);

        // --PIXEL ASSEMBLER--
        HotIsostaticPressRecipeBuilder.build(POMitems.RUBBER_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(POMitems.BIO_COMPOUND.get(), 4).heat(0, 80)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.FIRE_PROOF_RUBBER_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(POMitems.FIRE_PROOF_COMPOUND.get(), 4).heat(0, 120)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.REPELLING_RUBBER_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(POMitems.REPELLING_COMPOUND.get(), 4).heat(0, 80)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(Element.TITANIUM.dust()).heat(1941, 3560)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.NETHERITE_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(Items.NETHERITE_INGOT).heat(2500, 4000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_BALL.get()).mold(POMitems.BALL_CAST.get())
                .input(POMitems.TITANIUM_DIBORIDE_DUST.get()).heat(3000, 5000)
                .finish(fConsumer, this);

        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_INGOT.get()).mold(POMitems.INGOT_CAST.get())
                .input(POMitems.TITANIUM_DIBORIDE_DUST.get()).heat(3000, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(POMitems.TITANIUM_DIBORIDE_DUST.get()).heat(3000, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_GOLD_INGOT.get()).mold(POMitems.INGOT_CAST.get())
                .input(POMitems.TITANIUM_GOLD_DUST.get()).heat(2300, 3500)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_GOLD_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(POMitems.TITANIUM_GOLD_DUST.get()).heat(2300, 3500)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TITANIUM_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(Element.TITANIUM.dust()).heat(1941, 3560)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.NETHERITE_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(Items.NETHERITE_INGOT).heat(2400, 4000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.OBSIDIAN_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(POMitems.OBSIDIAN_DUST.get(), 2).heat(2200, 3400)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.CRYING_OBSIDIAN_PLATING.get()).mold(POMitems.PLATE_CAST.get())
                .input(POMitems.CRYING_OBSIDIAN_DUST.get(), 2).heat(2500, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(Items.NETHERITE_SCRAP).mold(POMitems.PLATE_CAST.get())
                .input(POMitems.ANCIENT_DEBRIS_DUST.get()).heat(2400, 4000)
                .finish(fConsumer, this);

        HotIsostaticPressRecipeBuilder.build(POMitems.INGOT_CAST.get()).mold(Tags.Items.INGOTS)
                .input(POMitems.CRYING_OBSIDIAN_DUST.get(), 4).heat(2500, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.BALL_CAST.get()).mold(POMtags.Items.MILLING_BALL)
                .input(POMitems.CRYING_OBSIDIAN_DUST.get(), 4).heat(2500, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.PLATE_CAST.get()).mold(POMitems.TITANIUM_PLATING.get())
                .input(POMitems.CRYING_OBSIDIAN_DUST.get(), 4).heat(2500, 5000)
                .finish(fConsumer, this);


        HotIsostaticPressRecipeBuilder.build(POMitems.VIOLET_DIAMOND.get()).ballMold()
                .input(Items.DIAMOND, 4).heat(3500, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.URANIUM_FUEL_PELLET.get()).ballMold()
                .input(POMitems.YELLOWCAKE_URANIUM.get(), 9).heat(3500, 5000)
                .finish(fConsumer, this);

        HotIsostaticPressRecipeBuilder.build(Items.NETHERITE_INGOT).ingotMold()
                .input(POMitems.NETHERITE_DUST.get()).heat(2500, 4000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.SUPERCONDUCTIVE_INGOT.get()).ingotMold()
                .input(POMitems.SUPERCONDUCTIVE_DUST.get()).heat(2300, 3500)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.RED_SILVER_INGOT.get()).ingotMold()
                .input(POMitems.RED_SILVER_DUST.get()).heat(1500, 2500)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.ROYAL_TUNGSTEN_INGOT.get()).ingotMold()
                .input(POMitems.ROYAL_TUNGSTEN_DUST.get()).heat(2600, 2700)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.PYROLYTIC_CARBON_SHEET.get()).plateMold()
                .input(POMitems.PYROLYTIC_CARBON.get(), 4).heat(4000, 5000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.LEAD_PLATING.get()).plateMold()
                .input(Element.LEAD.dustTag()).heat(700, 2000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.TUNGSTEN_PLATING.get()).plateMold()
                .input(Element.TUNGSTEN.dustTag()).heat(3700, 5000)
                .finish(fConsumer, this);

        HotIsostaticPressRecipeBuilder.build(POMitems.CLEANING_SPONGE.get()).ingotMold()
                .input(Items.SPONGE).heat(200, 400)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.SILICON_SHEET.get()).plateMold()
                .input(Items.QUARTZ).heat(600, 1000)
                .finish(fConsumer, this);
        HotIsostaticPressRecipeBuilder.build(POMitems.SILICON_SHEET.get()).plateMold()
                .input(Element.SILICON.dustTag()).heat(600, 1000)
                .finish(fConsumer, this);

        // --CHEMICAL COMBINING--
        ChemicalSeparatorRecipeBuilder.build(POMitems.ACANTHITE_DUST.get(), 2, Fluids.WATER, 200)
                .output(POMfluids.SULFURIC_ACID_SOURCE.get(), 50)
                .output(Element.SILVER.dust())
                .output(Element.SILVER.dust(),0.5f)
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(POMitems.TITANIUM_DIBORIDE_DUST.get())
                .output(Element.TITANIUM.dust())
                .output(Element.BORON.dust(), 2)
                .finish(fConsumer, this);

        ChemicalSeparatorRecipeBuilder.build(Element.TITANIUM.dustTag(), Fluids.WATER, 150)
                .output(POMfluids.HYDROGEN_GAS_SOURCE.get(), 100)
                .output(POMitems.TITANIUM_OXIDE_DUST.get())
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(POMitems.MERCURY_SULFIDE_DUST.get())
                .output(POMfluids.MERCURY_SOURCE.get(), 100)
                .output(Element.SULFUR.dust())
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(Items.REDSTONE, 8, Fluids.WATER, 500)
                .output(POMfluids.HYDROGEN_GAS_SOURCE.get(), 500)
                .output(POMitems.YELLOWCAKE_URANIUM.get())
                .output(POMitems.REFINED_REDSTONE.get(), 8)
                .finish(fConsumer, this);

        ChemicalSeparatorRecipeBuilder.build(POMitems.DEPLETED_URANIUM_FUEL_CELL.get(), POMfluids.NITRIC_ACID_SOURCE.get(), 500)
                .output(POMfluids.NUCLEAR_WASTE_SOURCE.get(), 1000)
                .output(POMitems.EMPTY_FUEL_CELL.get())
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(POMitems.DEPLETED_ENRICHED_URANIUM_FUEL_CELL.get(), POMfluids.NITRIC_ACID_SOURCE.get(), 500)
                .output(POMfluids.NUCLEAR_WASTE_SOURCE.get(), 3000)
                .output(POMitems.EMPTY_FUEL_CELL.get())
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(POMitems.DEPLETED_PLUTONIUM_FUEL_CELL.get(), POMfluids.NITRIC_ACID_SOURCE.get(), 500)
                .output(POMfluids.NUCLEAR_WASTE_SOURCE.get(), 2000)
                .output(POMitems.EMPTY_FUEL_CELL.get())
                .finish(fConsumer, this);
        ChemicalSeparatorRecipeBuilder.build(POMitems.DEPLETED_ENRICHED_PLUTONIUM_FUEL_CELL.get(), POMfluids.NITRIC_ACID_SOURCE.get(), 500)
                .output(POMfluids.NUCLEAR_WASTE_SOURCE.get(), 6000)
                .output(POMitems.EMPTY_FUEL_CELL.get())
                .finish(fConsumer, this);

        // --CHEMICAL COMBINING--
        ChemicalCombinerRecipeBuilder.build(POMitems.MERCURY_SULFIDE_DUST.get())
                .input(POMfluids.MERCURY_SOURCE.get(), 100)
                .input(Element.SULFUR.dustTag())
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMitems.PYROLYTIC_CARBON.get())
                .input(POMfluids.HYDROGEN_GAS_SOURCE.get(), 250)
                .input(POMtags.Items.DUST_COAL)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMitems.ROYAL_TUNGSTEN_AMALGAMATION.get())
                .input(Element.TUNGSTEN.dustTag(), 2)
                .input(POMitems.REFINED_REDSTONE.get(), 2)
                .input(Items.AMETHYST_SHARD)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMfluids.PUREX_SOLUTION_SOURCE.get(), 200)
                .input(POMfluids.SULFURIC_ACID_SOURCE.get(), 100)
                .input(Items.BONE_MEAL, 2)
                .input(Items.WARPED_FUNGUS)
                .input(POMtags.Items.DUST_COAL)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMfluids.PUREX_SOLUTION_SOURCE.get(), 200)
                .input(POMfluids.SULFURIC_ACID_SOURCE.get(), 100)
                .input(Items.BONE_MEAL, 2)
                .input(Items.WARPED_WART_BLOCK, 2)
                .input(POMtags.Items.DUST_COAL)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMfluids.PUREX_SOLUTION_SOURCE.get(), 200)
                .input(POMfluids.SULFURIC_ACID_SOURCE.get(), 100)
                .input(Items.BONE_MEAL, 2)
                .input(Items.WARPED_ROOTS, 2)
                .input(POMtags.Items.DUST_COAL)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMfluids.PUREX_SOLUTION_SOURCE.get(), 200)
                .input(POMfluids.SULFURIC_ACID_SOURCE.get(), 100)
                .input(Items.BONE_MEAL, 2)
                .input(Items.NETHER_SPROUTS, 4)
                .input(POMtags.Items.DUST_COAL)
                .finish(fConsumer, this);

        ChemicalCombinerRecipeBuilder.build(POMitems.PLUTONIUM_FUEL_PELLET.get())
                .input(POMfluids.PLUTONIUM_SOLUTION_SOURCE.get(), 125)
                .finish(fConsumer, this);
        ChemicalCombinerRecipeBuilder.build(POMitems.URANIUM_FUEL_PELLET.get())
                .input(POMfluids.URANIUM_SOLUTION_SOURCE.get(), 125)
                .finish(fConsumer, this);

        // --CHEMICAL MIXING--
        ChemicalMixerRecipeBuilder.build(2)
                .input(POMfluids.HYDROGEN_GAS_SOURCE.get(), 100)
                .input(POMfluids.OXYGEN_GAS_SOURCE.get(), 50)
                .output(Fluids.WATER, 100)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(4)
                .input(Fluids.WATER, 100)
                .output(POMfluids.HYDROGEN_GAS_SOURCE.get(), 100)
                .output(POMfluids.OXYGEN_GAS_SOURCE.get(), 50)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(0)
                .input(POMfluids.STEAM_SOURCE.get(), 100)
                .output(Fluids.WATER, 100)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(0)
                .input(POMfluids.BLAZING_STEAM_SOURCE.get(), 20)
                .output(POMfluids.STEAM_SOURCE.get(), 20)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(0)
                .input(POMfluids.AIR_SOURCE.get(), 10)
                .output(POMfluids.NITROGEN_GAS_SOURCE.get(), 8)
                .output(POMfluids.OXYGEN_GAS_SOURCE.get(), 2)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(3)
                .input(POMfluids.HYDROGEN_GAS_SOURCE.get(), 300)
                .input(POMfluids.NITROGEN_GAS_SOURCE.get(), 100)
                .output(POMfluids.AMMONIA_GAS_SOURCE.get(), 200)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(3)
                .input(POMfluids.HYDROGEN_GAS_SOURCE.get(), 150)
                .input(POMfluids.AIR_SOURCE.get(), 100)
                .output(POMfluids.AMMONIA_GAS_SOURCE.get(), 100)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(2)
                .input(POMfluids.AMMONIA_GAS_SOURCE.get(), 100)
                .input(POMfluids.OXYGEN_GAS_SOURCE.get(), 200)
                .output(POMfluids.NITRIC_ACID_SOURCE.get(), 100)
                .output(Fluids.WATER, 100)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(2)
                .input(POMfluids.AMMONIA_GAS_SOURCE.get(), 10)
                .input(POMfluids.AIR_SOURCE.get(), 25)
                .output(POMfluids.NITRIC_ACID_SOURCE.get(), 10)
                .output(Fluids.WATER, 10)
                .finish(fConsumer, this);

        ChemicalMixerRecipeBuilder.build(4)
                .input(POMfluids.NUCLEAR_WASTE_SOURCE.get(), 10)
                .input(POMfluids.PUREX_SOLUTION_SOURCE.get(), 5)
                .output(POMfluids.NUCLEAR_WASTE_SOLUTION_SOURCE.get(), 10)
                .finish(fConsumer, this);
        ChemicalMixerRecipeBuilder.build(5)
                .input(POMfluids.NUCLEAR_WASTE_SOLUTION_SOURCE.get(), 9)
                .input(POMfluids.NITRIC_ACID_SOURCE.get(), 5)
                .input(Fluids.WATER, 10)
                .output(POMfluids.URANIUM_SOLUTION_SOURCE.get(), 3)
                .output(POMfluids.PLUTONIUM_SOLUTION_SOURCE.get(), 3)
                .output(POMfluids.RED_OIL_SOURCE.get(), 5)
                .finish(fConsumer, this);

        //ez crafting
        SimpleSurroundRecipe(Element.TITANIUM.nugget(), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.VIOLET_DIAMOND.get(), POMitems.VIOLET_DIAMOND_LENS.get(), fConsumer);
//        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.RED_DIAMOND.get(), POMitems.RED_DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(Element.COPPER.nugget(), Items.STICK, POMitems.COPPER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.SILVER.nugget(), Items.STICK, POMitems.SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TUNGSTEN.nugget(), Items.STICK, POMitems.TUNGSTEN_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.RED_SILVER_NUGGET.get(), Items.STICK, POMitems.RED_SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.ROYAL_TUNGSTEN_NUGGET.get(), Items.STICK, POMitems.ROYAL_TUNGSTEN_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.SUPERCONDUCTIVE_NUGGET.get(), Items.STICK, POMitems.SUPERCONDUCTIVE_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(Element.TITANIUM.item(), Items.NETHERITE_INGOT, POMblocks.SIMPLE_CASING_1.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.SIMPLE_CASING_1.get(), POMblocks.SIMPLE_CASING_2.get(), fConsumer);

        SimpleFullCrossRecipe(Items.OBSIDIAN, POMitems.OBSIDIAN_PLATING.get(), Items.NETHERITE_BLOCK, POMblocks.STRONG_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRONG_CASING.get(), POMblocks.STRENGTHENED_CASING.get(), fConsumer);
//        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRENGTHENED_CASING.get(), POMblocks.REINFORCED_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, Items.NETHER_STAR, POMitems.POWER_ORB.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.MANA_SPHERE.get(), POMitems.CRUDE_POWER_CORE.get(), POMitems.POWER_ORB.get(), fConsumer, "_crude");
        SimpleFullCrossRecipe(toI(Element.TITANIUM.itemTag()), toI(Items.REDSTONE), toI(Items.REDSTONE_BLOCK), POMitems.REDSTONE_CORE.get(), fConsumer, "");
        SimpleFullCrossRecipe(POMitems.TITANIUM_GOLD_PLATING.get(), Items.AMETHYST_BLOCK, POMitems.REDSTONE_CORE.get(), POMitems.CRUDE_POWER_CORE.get(), fConsumer);
        SimpleFullCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_PLATING.get(), POMitems.TITANIUM_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMitems.TITANIUM_GOLD_PLATING.get(), POMitems.TITANIUM_GOLD_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get(), fConsumer);

        SimpleFullCrossRecipe(POMitems.TITANIUM_PLATING.get(), POMitems.COPPER_WIRE.get(), POMitems.ADVANCED_CIRCUIT_BOARD_1.get(), POMblocks.ENERGY_PORT.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_PLATING.get(), Items.HOPPER, POMitems.ADVANCED_CIRCUIT_BOARD_1.get(), POMblocks.ITEM_PORT.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_PLATING.get(), Items.BUCKET, POMitems.ADVANCED_CIRCUIT_BOARD_1.get(), POMblocks.FLUID_PORT.get(), fConsumer);

        SimpleFullCrossRecipe(POMitems.FUSION_PLATING.get(), POMitems.SILVER_WIRE.get(), POMitems.ADVANCED_CIRCUIT_BOARD_2.get(), POMblocks.FUSION_ENERGY_PORT.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.FUSION_PLATING.get(), Items.HOPPER, POMitems.ADVANCED_CIRCUIT_BOARD_2.get(), POMblocks.FUSION_ITEM_PORT.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.FUSION_PLATING.get(), Items.BUCKET, POMitems.ADVANCED_CIRCUIT_BOARD_2.get(), POMblocks.FUSION_FLUID_PORT.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.FUSION_PLATING.get(), POMitems.RED_SILVER_WIRE.get(), POMitems.ADVANCED_CIRCUIT_BOARD_2.get(), POMblocks.FUSION_PLASMA_PORT.get(), fConsumer);

        SimpleCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_COMPOUND.get(), POMitems.RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, POMitems.NETHERITE_BALL.get(), fConsumer);
        SimpleCrossRecipe(Element.TITANIUM.nugget(), Element.TITANIUM.item(), POMitems.TITANIUM_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMitems.TITANIUM_GOLD_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.LEAD_PLATING.get(), POMblocks.MULTIBLOCK_CASING.get(), POMblocks.FISSION_CASING.get(), fConsumer);
        SimpleCrossRecipe(POMitems.LEAD_PLATING.get(), POMblocks.ENERGY_PORT.get(), POMblocks.FISSION_ENERGY_PORT.get(), fConsumer);
        SimpleCrossRecipe(POMitems.LEAD_PLATING.get(), POMblocks.FLUID_PORT.get(), POMblocks.FISSION_FLUID_PORT.get(), fConsumer);
        SimpleCrossRecipe(POMitems.FUSION_PLATING.get(), POMblocks.MULTIBLOCK_CASING.get(), POMblocks.ARMORED_MULTIBLOCK_CASING.get(), fConsumer);

        //compacting
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_GOLD_NUGGET.get(), POMitems.TITANIUM_GOLD_INGOT.get(), POMblocks.TITANIUM_GOLD_BLOCK.get(), fConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), fConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCONDUCTIVE_NUGGET.get(), POMitems.SUPERCONDUCTIVE_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.ROYAL_TUNGSTEN_NUGGET.get(), POMitems.ROYAL_TUNGSTEN_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(Element.COPPER.nugget(), Items.COPPER_INGOT, fConsumer);
        SimpleCompactingRecipe(POMitems.ALUMINIUM_SCRAP.get(), POMblocks.ALUMINIUM_SCRAP_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.COPPER_WIRE.get(), POMblocks.COPPER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SILVER_WIRE.get(), POMblocks.SILVER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.TUNGSTEN_WIRE.get(), POMblocks.TUNGSTEN_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.REDSTONE_LAYERED_COPPER_WIRE.get(), POMblocks.REDSTONE_LAYERED_COPPER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.RED_SILVER_WIRE.get(), POMblocks.RED_SILVER_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.RED_SILVER_NUGGET.get(), POMitems.RED_SILVER_INGOT.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.ROYAL_TUNGSTEN_WIRE.get(), POMblocks.ROYAL_TUNGSTEN_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCONDUCTIVE_WIRE.get(), POMblocks.SUPERCONDUCTIVE_SPOOL.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.POWER_CELL.get(), POMblocks.POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.OVERCHARGED_POWER_CELL.get(), POMblocks.OVERCHARGED_POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.SUPERCHARGED_POWER_CELL.get(), POMblocks.SUPERCHARGED_POWER_CELL_ARRAY.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.VIOLET_DIAMOND.get(), POMblocks.VIOLET_DIAMOND_BLOCK.get(), fConsumer);


        //Furnace
        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), Element.TITANIUM.item(), 0.5f, fConsumer, toItemP(POMitems.RAW_TITANIUM.get()), "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), Element.TITANIUM.item(), 0.5f, fConsumer, toItemP(POMblocks.ENDSTONE_TITANIUM_ORE.get().asItem()), "_from_ore");
        SimpleSmeltingRecipe(POMitems.RUSTED_PLATING.get(), Items.NETHERITE_SCRAP, 0.5f, fConsumer, toItemP(POMitems.RUSTED_PLATING.get()), "");
        SimpleSmeltingRecipe(POMitems.ANCIENT_DEBRIS_DUST.get(), Items.NETHERITE_SCRAP, 0.5f, fConsumer, toItemP(Items.NETHERITE_SCRAP), "");
        SimpleSmeltingRecipe(Element.ALUMINIUM.dust(), POMitems.ALUMINIUM_SCRAP.get(), 0.8f, fConsumer, toItemP(Element.ALUMINIUM.dustTag()), "");

        SimpleSmeltingRecipe(POMitems.TITANIUM_GOLD_DUST.get(), POMitems.TITANIUM_GOLD_INGOT.get(), 0.5f, fConsumer, toItemP(POMitems.TITANIUM_GOLD_DUST.get()), "");
        SimpleSmeltingRecipe(POMitems.RED_SILVER_DUST.get(), POMitems.RED_SILVER_INGOT.get(), 0.5f, fConsumer, toItemP(POMitems.RED_SILVER_DUST.get()), "");
        SimpleSmeltingRecipe(POMitems.SUPERCONDUCTIVE_DUST.get(), POMitems.SUPERCONDUCTIVE_INGOT.get(), 0.5f, fConsumer, toItemP(POMitems.SUPERCONDUCTIVE_DUST.get()), "");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.BIO_COMPOUND.get()),  "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.FIRE_PROOF_COMPOUND.get()), "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 200 , fConsumer, toItemP(POMitems.REPELLING_COMPOUND.get()), "");

        //Smithing
        SmithingTransformRecipeBuilder.smithing(toI(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), toI(Items.STICK), toI(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, POMitems.HAMMER.get())
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
        for(Element e : Element.values()) {
            if (e.equals(Element.DEBUGIUM)) continue;
            SimpleAtomCompacting(e.atom64(), e.atom512(), fConsumer);
            Fusing(e, fConsumer);
            autoPixelSplittingAndAssembling(e.item(), List.of(toCI(POMitems.PIXEL_PILE.get(), 10), toCI(POMitems.PIXEL.get())), "element.pixelsofmc." + e.elementName(), e.hexToRGB(0), e.hexToRGB(1), e.hexToRGB(2), fConsumer);

            if (e.isMetal() && e.shouldAddDust()) {
                HotIsostaticPressRecipeBuilder.build(e.itemTag())
                        .input(e.dustTag()).mold(POMitems.INGOT_CAST.get())
                        .heat(e.getInfo().getMeltingPoint(), e.getInfo().getEvaporatingPoint())
                        .finish(fConsumer, this);
                SimpleSmeltingRecipe(e.dustTag(), e.item(), 1f, fConsumer, toItemP(e.dustTag()), "_from_dust");
            }
            if (e.shouldAddDust()) {
                GrinderRecipeBuilder.build(e.itemTag())
                        .output(e.dust())
                        .finish(fConsumer, this);
            }
            if (e.shouldAddNugget()) {
                autoPixelSplittingAndAssembling(e.nugget(),  List.of(toCI(POMitems.PIXEL_PILE.get()), toCI(POMitems.PIXEL.get())), "element.pixelsofmc."+e.elementName(), e.hexToRGB(0), e.hexToRGB(1), e.hexToRGB(2), fConsumer);
                if (!e.isVanilla())
                    SimpleCompactingRecipe(toI(e.nugget()), e.item(), fConsumer);
            }
            if (e.shouldAddBlock()) {
                autoPixelSplittingAndAssembling(e.blockItem(), List.of(toCI(POMitems.PIXEL_PILE.get(), 64), toCI(POMitems.PIXEL_PILE.get(), 27), toCI(POMitems.PIXEL.get())), "element.pixelsofmc."+e.elementName(), e.hexToRGB(0), e.hexToRGB(1), e.hexToRGB(2), fConsumer);
                if (!e.isVanilla())
                    SimpleCompactingRecipe(toI(e.item()), e.block(), fConsumer);
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
    private void SimpleSmeltingRecipe(TagKey<Item> input, ItemLike output, float xp, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, consumer, trigger, extra);
    }
    private void SimpleSmeltingRecipe(ItemLike input, ItemLike output, float xp, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleSmeltingRecipe(Ingredient.of(input), output, xp, consumer, trigger, extra);
    }
    private void SimpleSmeltingRecipe(Ingredient input, ItemLike output, float xp, Consumer<FinishedRecipe> consumer, ItemPredicate trigger, String extra) {
        SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, output, xp, 200)
                .unlockedBy("", inventoryTrigger(trigger))
                .save(consumer, toRL("smelting/"+output.asItem()+extra));
        SimpleCookingRecipeBuilder.blasting(input, RecipeCategory.MISC, output, xp, 100)
                .unlockedBy("", inventoryTrigger(trigger))
                .save(consumer, toRL("smelting/"+output.asItem()+extra+"_from_blasting"));
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
        SimpleFullCrossRecipe(toI(corners), toI(sides), toI(middle), output, consumer, "");
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer, String extra) {
        SimpleFullCrossRecipe(toI(corners), toI(sides), toI(middle), output, consumer, extra);
    }
    private void SimpleFullCrossRecipe(Ingredient corners, Ingredient sides, Ingredient middle , ItemLike output, Consumer<FinishedRecipe> consumer, String extra) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', corners)
                .define('B', sides)
                .define('C', middle)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(toItemP(middle.getItems()[0].getItem()), toItemP(corners.getItems()[0].getItem()), toItemP(sides.getItems()[0].getItem())))
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

    private void PixelSplitting(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), output, structure, r,g,b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("splitting/" + CountedIngredient.of(ingredient).asName()));
    }
    private void autoPixelSplitting(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        new PixelSplitterRecipeBuilder(CountedIngredient.of(ingredient), output, structure, new int[]{color1[0], color2[0], color3[0]},new int[]{color1[1], color2[1], color3[1]},new int[]{color1[2], color2[2], color3[2]})
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("splitting/" + CountedIngredient.of(ingredient).asName()));
    }

    private void PixelAssembling(ItemLike output, List<CountedIngredient> inputs, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        new PixelAssemblerRecipeBuilder(inputs, toCI(output), structure, r, g, b)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("assembling/" + CountedIngredient.of(output).asName()));
    }

    private void autoPixelAssembling(ItemLike output, List<CountedIngredient> inputs, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        new PixelAssemblerRecipeBuilder(inputs, toCI(output), structure, new int[]{color1[0], color2[0], color3[0]},new int[]{color1[1], color2[1], color3[1]},new int[]{color1[2], color2[2], color3[2]})
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("assembling/" + CountedIngredient.of(output).asName()));
    }

    private void PixelSplittingAndAssembling(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] r, int[] g, int[] b, Consumer<FinishedRecipe> consumer) {
        PixelSplitting(ingredient, output, structure, r, g, b, consumer);
        PixelAssembling(ingredient, output, structure, r, g, b, consumer);
    }

    private void autoPixelSplittingAndAssembling(ItemLike ingredient, List<CountedIngredient> output, String structure, int[] color1, int[] color2, int[] color3, Consumer<FinishedRecipe> consumer) {
        autoPixelSplitting(ingredient, output, structure, color1, color2, color3, consumer);
        autoPixelAssembling(ingredient, output, structure, color1, color2, color3, consumer);
    }

    private void Fusing(Element element, Consumer<FinishedRecipe> consumer) {
        if (element.equals(Element.HYDROGEN)) {
            Fusing(element, false, element.getElement(), 0, consumer);
            Fusing(element, true, element.getElement() * 8, 0, consumer);
        } else {
            Fusing(element, false, element.getElement(), element.getElement(), consumer);
            Fusing(element, true, element.getElement() * 8, element.getElement() * 8, consumer);
        }
    }
    private void Fusing(Element element, boolean x512, int proton, int neutron, Consumer<FinishedRecipe> consumer) {
        new FusionRecipeBuilder(element, proton, neutron, x512)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("fusing/" + (x512 ? element.atom512().asItem().toString() : element.atom64().asItem().toString())));
    }


    //toIngredient
    private Ingredient toI(ItemLike input) {
        return Ingredient.of(input);
    }
    private Ingredient toI(TagKey<Item> input) {
        return Ingredient.of(input);
    }
    //toCountIngredient
    private CountedIngredient toCI(ItemLike input) {
        return CountedIngredient.of(1, input);
    }
    private CountedIngredient toCI(ItemLike input, int count) {
        return CountedIngredient.of(count, input);
    }


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
    public ResourceLocation toRL(String string) {
        if(PATH_COUNT.containsKey(string))
        {
            int count = PATH_COUNT.get(string)+1;
            PATH_COUNT.put(string, count);
            return new ResourceLocation(PixelsOfMc.MOD_ID, string+"_"+count);
        }
        PATH_COUNT.put(string, 1);
        return new ResourceLocation(PixelsOfMc.MOD_ID, string);
    }
}