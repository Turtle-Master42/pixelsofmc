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
import net.minecraftforge.fml.ModList;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.recipe.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Mods;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.apache.commons.codec.language.bm.Lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final HashMap<String, Integer> PATH_COUNT = new HashMap<>();
    public POMrecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> fConsumer) {


        ShapelessRecipeBuilder.shapeless(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.SUGAR_CANE)
                .requires(Items.HONEYCOMB)
                .requires(Items.WARPED_ROOTS, 2)
                .requires(Items.KELP, 2)
                .requires(Tags.Items.SLIMEBALLS)
                .requires(Tags.Items.EGGS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.FIRE_PROOF_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CRIMSON_FUNGUS)
                .requires(Items.WEEPING_VINES)
                .requires(Items.CRIMSON_ROOTS)
                .requires(Items.NETHER_WART, 2)
                .requires(Items.MAGMA_CREAM, 2)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.REPELLING_COMPOUND.get(), 2)
                .requires(POMitems.BIO_COMPOUND.get(), 2)
                .requires(Items.CHORUS_FLOWER)
                .requires(Items.CHORUS_FRUIT, 2)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(Tags.Items.ENDER_PEARLS)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapedRecipeBuilder.shaped(POMitems.DENSE_CARBON_CUBE.get())
                .define('C', elementItem(Element.CARBON))
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.CARBONARO_CLUMP.get())
                .define('C', POMitems.DENSE_CARBON_CUBE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.BLACK_DIAMOND.get())
                .define('C', POMitems.CARBONARO_CLUMP.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(Items.DIAMOND)
                .define('C', POMitems.BLACK_DIAMOND.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.VIOLET_DIAMOND.get())
                .define('C', Items.DIAMOND)
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.RED_DIAMOND.get())
                .define('C', POMitems.VIOLET_DIAMOND.get())
                .pattern("CC")
                .pattern("CC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapedRecipeBuilder.shaped(POMitems.HAMMER.get())
                .define('A', Items.NETHERITE_INGOT)
                .define('B', Tags.Items.RODS_WOODEN)
                .pattern("A")
                .pattern("B")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.SCREWDRIVER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern("B")
                .pattern("B")
                .pattern("A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.WIRECUTTER.get())
                .define('A', POMitems.BIO_PLASTIC.get())
                .define('B', POMitems.NETHERITE_NUGGET.get())
                .pattern(" B ")
                .pattern("A A")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.CLEANING_CLOTH.get())
                .requires(ItemTags.CARPETS)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.STRING)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_PLATING.get())
                .requires(ingotTag(Element.TITANIUM))
                .requires(ingotTag(Element.TITANIUM))
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapelessRecipeBuilder.shapeless(POMitems.NETHERITE_PLATING.get())
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.NETHERITE_INGOT)
                .requires(POMitems.HAMMER.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);



        ShapedRecipeBuilder.shaped(POMitems.REDSTONE_LAYERED_COPPER_WIRE.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.COPPER_WIRE.get())
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DRAGON_EYE.get())
                .define('B', Items.ENDER_EYE)
                .define('C', Items.DRAGON_BREATH)
                .define('D', Tags.Items.NETHER_STARS)
                .pattern(" C ")
                .pattern("BDB")
                .pattern(" C ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.MICRO_CHIP.get())
                .define('A', POMitems.NETHERITE_NUGGET.get())
                .define('B', Items.REDSTONE)
                .define('C', POMitems.FIRE_PROOF_COMPOUND.get())
                .define('D', Items.IRON_NUGGET)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DDD")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.POWER_CELL.get())
                .define('A', Tags.Items.GLASS)
                .define('B', POMitems.NETHERITE_PLATING.get())
                .define('C', POMitems.POWER_ORB.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ENDER_SENSOR.get())
                .define('A', nuggetItem(Element.TITANIUM))
                .define('B', nuggetTag(Element.COPPER))
                .define('C', Items.ENDER_EYE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.DRAGON_SENSOR.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_NUGGET.get())
                .define('B', nuggetItem(Element.SILVER))
                .define('C', POMitems.DRAGON_EYE.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.MOVING_PARTS.get())
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


        ShapedRecipeBuilder.shaped(POMitems.SPEED_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', dustTag(Element.TITANIUM))
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.ENERGY_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', dustTag(Element.GOLD))
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMitems.HEAT_UPGRADE.get())
                .define('A', POMitems.TITANIUM_DIBORIDE_INGOT.get())
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('C', dustTag(Element.COBALT))
                .pattern(" B ")
                .pattern("ACA")
                .pattern(" B ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);


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
                .save(fConsumer);
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
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.HOT_ISOSTATIC_PRESS.get())
                .define('A', Items.PISTON)
                .define('B', Items.REDSTONE)
                .define('C', POMitems.TITANIUM_PLATING.get())
                .define('D', POMblocks.SIMPLE_CASING_1.get())
                .define('E', POMblocks.PERFECTED_CASING_1.get())
                .pattern("CDC")
                .pattern("ADA")
                .pattern("BEB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);




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
                .save(fConsumer);

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
                .save(fConsumer);
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
                .save(fConsumer);
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
                .save(fConsumer);


        ShapedRecipeBuilder.shaped(POMblocks.ADVANCED_CASING_1.get())
                .define('A', POMblocks.SIMPLE_CASING_1.get())
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.REDSTONE)
                .define('D', Items.GOLD_INGOT)
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);
        ShapedRecipeBuilder.shaped(POMblocks.PERFECTED_CASING_1.get())
                .define('A', POMblocks.ADVANCED_CASING_1.get())
                .define('B', POMitems.TITANIUM_PLATING.get())
                .define('C', Items.DIAMOND_BLOCK)
                .define('D', POMitems.ADVANCED_CIRCUIT_BOARD_1.get())
                .pattern("BCB")
                .pattern("DAD")
                .pattern("BCB")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(fConsumer);

        //auto
        for(Element m : Element.values()) {
            SimpleAtomCompacting(atom64Item(m), atom512Item(m), fConsumer);

            if (m.isMetal() && m.shouldAddDust())
                if (m.isFireResistant())
                    Pressing(toI(dustTag(m)), 1, toI(POMitems.INGOT_CAST.get()), toI(elementItem(m)), 1, 2500, fConsumer);
                else
                    Pressing(toI(dustTag(m)), 1, toI(POMitems.INGOT_CAST.get()), toI(elementItem(m)), 1, 1000, fConsumer);
            if (m.isMetal() && m.shouldAddDust() && m!=Element.ALUMINIUM)
                SimpleSmeltingRecipe(dustTag(m), elementItem(m), 1f, 200, fConsumer, "");
            if (m.shouldAddDust() && m!=Element.ALUMINIUM && !m.isVanilla())
                Grinder(toI(elementItem(m)), fConsumer, toCHI(dustTag(m), 1, 1)) ;
        }



        //grinding
        Grinder(toI(Items.FLINT), fConsumer, toCHI(dustTag(Element.CALCIUM), 1, 0.7f));
        Grinder(toI(Items.NETHERITE_SCRAP), fConsumer, toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 1f));
        Grinder(toI(Items.ANCIENT_DEBRIS), fConsumer, toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 1f), toCHI(POMtags.Items.DUST_ANCIENT_DEBRIS, 1, 0.33f));
        Grinder(toI(Items.TUFF), fConsumer, toCHI(dustTag(Element.SULFUR), 1, 0.3f), toCHI(dustTag(Element.LEAD), 1, 0.05f), toCHI(POMitems.MINERAL_GRIT.get(), 1, 0.2f), toCHI(dustTag(Element.CARBON), 1, 0.2f));
        Grinder(toI(Items.NETHERRACK), fConsumer, toCHI(POMitems.MERCURY_SULFIDE_DUST.get(), 2, 0.9f), toCHI(POMitems.MERCURY_SULFIDE_DUST.get(), 1, 0.2f), toCHI(dustTag(Element.GOLD), 1, 0.2f), toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.COBALT), 1, 0.05f));
        Grinder(toI(Items.END_STONE), fConsumer, toCHI(dustTag(Element.POTASSIUM), 2, 0.6f), toCHI(dustTag(Element.TITANIUM), 1, 0.75f), toCHI(dustTag(Element.SILICON), 1, 0.65f));
        Grinder(toI(Items.BLACKSTONE), fConsumer, toCHI(dustTag(Element.BORON), 1, 0.8f), toCHI(dustTag(Element.NICKEL), 1, 0.1f), toCHI(dustTag(Element.PLATINUM), 1, 0.01f), toCHI(dustTag(Element.COBALT), 1, 0.08f));
        Grinder(toI(Items.ANDESITE), fConsumer, toCHI(dustTag(Element.ALUMINIUM), 1, 0.2f));
        Grinder(toI(Items.BASALT), fConsumer, toCHI(dustTag(Element.IRON), 2, 0.7f), toCHI(dustTag(Element.GOLD), 1, 0.1f));
        Grinder(toI(Items.DIORITE), fConsumer, toCHI(dustTag(Element.SODIUM), 1, 0.3f));
        Grinder(toI(Items.GRANITE), fConsumer, toCHI(dustTag(Element.TUNGSTEN), 1, 0.015f), toCHI(dustTag(Element.MOLYBDENUM), 1, 0.015f));

        Grinder(toI(POMitems.ALUMINIUM_SCRAP.get()), fConsumer, toCHI(dustItem(Element.ALUMINIUM), 1, 1f));

        Grinder(toI(Items.STONE), fConsumer, toCHI(dustTag(Element.SILICON), 2, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f) ,toCHI(dustTag(Element.SILICON), 1, 0.55f) ,toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f), toCHI(dustTag(Element.SILICON), 1, 0.55f));
        Grinder(toI(Items.GOLD_BLOCK), fConsumer, toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f), toCHI(dustTag(Element.GOLD), 1, 1f));


        //milling
        BallMill(toI(POMitems.MERCURY_SULFIDE_DUST.get()), 1, toI(POMitems.TITANIUM_DIBORIDE_DUST.get()), 1, toI(POMitems.TITANIUM_OXIDE_DUST.get()), 3, POMitems.ANCIENT_DEBRIS_DUST.get(), 3, POMtags.Items.BALL_5, fConsumer);
        BallMill(toI(dustTag(Element.GOLD)), 2, toI(POMitems.ANCIENT_DEBRIS_DUST.get()), 2, POMitems.NETHERITE_DUST.get(), 1, POMtags.Items.BALL_4, fConsumer);
        BallMill(toI(dustTag(Element.BORON)), 2, toI(dustTag(Element.TITANIUM)), 1, POMitems.TITANIUM_DIBORIDE_DUST.get(), 2, POMtags.Items.BALL_4, fConsumer);

        //pixel splitter
        PixelSplitting(Items.GOLD_NUGGET, POMitems.PIXEL.get(), 6, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);
        PixelSplitting(Items.GOLD_INGOT, POMitems.PIXEL.get(), 56, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);
        PixelSplitting(Items.GOLD_BLOCK, POMitems.PIXEL_PILE.get(), 64, toAInt(253, 220, 178), toAInt(254, 150, 100), toAInt(95, 19, 17), fConsumer);

        PixelSplitting(Items.IRON_NUGGET, POMitems.PIXEL.get(), 6, toAInt(182, 147, 94), toAInt(182, 147, 94), toAInt(182, 147, 94), fConsumer);
        PixelSplitting(Items.IRON_INGOT, POMitems.PIXEL.get(), 56, toAInt(182, 147, 94), toAInt(182, 147, 94), toAInt(182, 147, 94), fConsumer);
        PixelSplitting(Items.IRON_BLOCK, POMitems.PIXEL_PILE.get(), 64, toAInt(182, 147, 94), toAInt(182, 147, 94), toAInt(182, 147, 94), fConsumer);



        //pressing
        Pressing(POMitems.BIO_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.RUBBER_BALL.get(), 1, 0, fConsumer);
        Pressing(POMitems.FIRE_PROOF_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), 1, 0, fConsumer);
        Pressing(POMitems.REPELLING_COMPOUND.get(), 4, POMitems.BALL_CAST.get(), POMitems.REPELLING_RUBBER_BALL.get(), 1, 0, fConsumer);
        Pressing(elementItem(Element.TITANIUM), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_BALL.get(), 1, 1000, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.BALL_CAST.get(), POMitems.NETHERITE_BALL.get(), 1, 2500, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.BALL_CAST.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), 1, 3000, fConsumer);

        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.INGOT_CAST.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), 1, 3000, fConsumer);
        Pressing(POMitems.TITANIUM_DIBORIDE_DUST.get(), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), 1, 3000, fConsumer);
        Pressing(elementItem(Element.TITANIUM), 1, POMitems.PLATE_CAST.get(), POMitems.TITANIUM_PLATING.get(), 1, 1000, fConsumer);
        Pressing(Items.NETHERITE_INGOT, 1, POMitems.PLATE_CAST.get(), POMitems.NETHERITE_PLATING.get(), 1, 2500, fConsumer);

        Pressing(toI(Items.CLAY_BALL), 4, toI(Tags.Items.INGOTS), toI(POMitems.INGOT_CAST.get()), 1, 250, fConsumer);
        Pressing(toI(Items.CLAY_BALL), 4, toI(POMtags.Items.MILLING_BALL), toI(POMitems.BALL_CAST.get()), 1, 250, fConsumer);
        Pressing(toI(Items.CLAY_BALL), 4, toI(POMitems.TITANIUM_PLATING.get()), toI(POMitems.PLATE_CAST.get()), 1, 250, fConsumer);

        Pressing(toI(dustTag(Element.GOLD)), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.GOLD_INGOT), 1, 1000, fConsumer);
        Pressing(toI(dustTag(Element.IRON)), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.IRON_INGOT), 1, 1000, fConsumer);
        Pressing(toI(dustTag(Element.COPPER)), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.COPPER_INGOT), 1, 1000, fConsumer);
        Pressing(toI(POMitems.NETHERITE_DUST.get()), 1, toI(POMitems.INGOT_CAST.get()), toI(Items.NETHERITE_INGOT), 1, 2500, fConsumer);

        //ez crafting
        SimpleSurroundRecipe(nuggetItem(Element.TITANIUM), Items.DIAMOND, POMitems.DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), Items.DIAMOND, POMitems.VIOLET_DIAMOND_LENS.get(), fConsumer);
        SimpleSurroundRecipe(nuggetItem(Element.COPPER), Items.STICK, POMitems.COPPER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(nuggetItem(Element.SILVER), Items.STICK, POMitems.SILVER_WIRE.get(), fConsumer);
        SimpleSurroundRecipe(elementItem(Element.TITANIUM), Items.NETHERITE_BLOCK, POMblocks.SIMPLE_CASING_1.get(), fConsumer);

        SimpleFullCrossRecipe(Items.OBSIDIAN, POMitems.NETHERITE_PLATING.get(), Items.NETHERITE_BLOCK, POMblocks.STRONG_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMblocks.STRONG_CASING.get(), POMblocks.REINFORCED_CASING.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.BIO_COMPOUND.get(), Items.REDSTONE, Items.NETHER_STAR, POMitems.POWER_ORB.get(), fConsumer);
        SimpleFullCrossRecipe(nuggetItem(Element.TITANIUM), Items.GOLD_INGOT, Items.REDSTONE_BLOCK, POMitems.REDSTONE_COUNTER.get(), fConsumer);
        SimpleFullCrossRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMitems.TITANIUM_PLATING.get(), POMitems.TITANIUM_CIRCLE_SAW.get(), fConsumer);
        SimpleFullCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_PLATING.get(), POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW.get(), fConsumer);

        SimpleCrossRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_COMPOUND.get(), POMitems.RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_COMPOUND.get(), POMitems.FIRE_PROOF_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_RUBBER_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, POMitems.NETHERITE_BALL.get(), fConsumer);
        SimpleCrossRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMitems.TITANIUM_BALL.get(), fConsumer);
        SimpleCrossRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMitems.TITANIUM_DIBORIDE_BALL.get(), fConsumer);

        //compacting
        SimpleMetalCompactingRecipe(nuggetItem(Element.TITANIUM), elementItem(Element.TITANIUM), POMblocks.TITANIUM_BLOCK.get(), fConsumer);
        SimpleMetalCompactingRecipe(POMitems.TITANIUM_DIBORIDE_NUGGET.get(), POMitems.TITANIUM_DIBORIDE_INGOT.get(), POMblocks.TITANIUM_DIBORIDE_BLOCK.get(), fConsumer);

        SimpleCompactingRecipe(POMitems.RAW_TITANIUM.get(), POMblocks.RAW_TITANIUM_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, fConsumer);
        SimpleCompactingRecipe(nuggetItem(Element.COPPER), Items.COPPER_INGOT, fConsumer);
        SimpleCompactingRecipe(nuggetItem(Element.SILVER), elementItem(Element.SILVER), fConsumer);
        SimpleCompactingRecipe(POMitems.ALUMINIUM_SCRAP.get(), POMblocks.ALUMINIUM_SCRAP_BLOCK.get(), fConsumer);
        SimpleCompactingRecipe(POMitems.COPPER_WIRE.get(), POMblocks.COPPER_SPOOL.get(), fConsumer);

        //Furnace
        SimpleSmeltingRecipe(POMitems.RAW_TITANIUM.get(), elementItem(Element.TITANIUM), 0.5f, 200 , fConsumer, "");
        SimpleSmeltingRecipe(POMblocks.ENDSTONE_TITANIUM_ORE.get(), elementItem(Element.TITANIUM), 0.5f, 200 , fConsumer, "_from_ore");
        SimpleSmeltingRecipe(POMitems.RUSTED_PLATING.get(), Items.NETHERITE_INGOT, 0.5f, 200, fConsumer, "");
        SimpleSmeltingRecipe(dustItem(Element.ALUMINIUM), POMitems.ALUMINIUM_SCRAP.get(), 0.8f, 200 , fConsumer, "");

        SimpleFurnaceRecipe(POMitems.BIO_COMPOUND.get(), POMitems.BIO_PLASTIC.get(), 0.1f, 200 , fConsumer, "");
        SimpleFurnaceRecipe(POMitems.FIRE_PROOF_PLASTIC.get(), POMitems.FIRE_PROOF_PLASTIC.get(), 0.1f, 200 , fConsumer, "");
        SimpleFurnaceRecipe(POMitems.REPELLING_COMPOUND.get(), POMitems.REPELLING_PLASTIC.get(), 0.1f, 200 , fConsumer, "");
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
    private void SimpleSurroundRecipe(ItemLike around, ItemLike middle, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(output)
                .define('A', around)
                .define('B', middle)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void SimpleFullCrossRecipe(ItemLike corners, ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer) {
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
    private void SimpleCrossRecipe(ItemLike sides, ItemLike middle , ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(output)
                .define('A', sides)
                .define('B', middle)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void SimpleAtomCompacting(ItemLike atomx64, ItemLike atomx512, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(atomx512, 1)
                .requires(atomx64, 8)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer, toRL("compacting/atoms/"+atomx64.asItem()+"_to_"+atomx512.asItem()));
        ShapelessRecipeBuilder.shapeless(atomx64, 8)
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

    private void Pressing(ItemLike ingredient, int inCount, ItemLike mold, ItemLike output, int outCount, int heat, Consumer<FinishedRecipe> consumer) {
        new HotIsostaticPressRecipeBuilder(CountedIngredient.of(inCount, ingredient), CountedIngredient.of(1, mold), output, outCount, heat)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }
    private void Pressing(Ingredient ingredient, int inCount, Ingredient mold, Ingredient output, int outCount, int heat, Consumer<FinishedRecipe> consumer) {
        new HotIsostaticPressRecipeBuilder(CountedIngredient.of(inCount, ingredient), CountedIngredient.of(1, mold), output.getItems()[0].getItem(), outCount, heat)
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(consumer);
    }

    private void Grinder(Ingredient input, ItemLike output, int outputCount, int outputChance, Consumer<FinishedRecipe> consumer) {
        new GrinderRecipeBuilder(input, List.of(ChanceIngredient.of(outputCount,outputChance,output)))
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
    private ItemLike atom64Item(Element element) {
        return POMitems.Metals.ATOMX64.get(element);
    }
    private ItemLike atom512Item(Element element) {
        return POMitems.Metals.ATOMX512.get(element);
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
    private int[] toAInt(int ...num) {
        return num;
    }
}