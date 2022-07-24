package net.turtlemaster42.pixelsofmc.datagen;

import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class POMrecipeProvider extends RecipeProvider implements IConditionBuilder {
    public POMrecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapedRecipeBuilder.shaped(POMblocks.RAW_TITANIUM_BLOCK.get())
                .define('E', POMitems.RAW_TITANIUM.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(POMitems.TITANIUM_INGOT.get())
                .requires(POMblocks.TITANIUM_BLOCK.get())
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(POMblocks.TITANIUM_BLOCK.get())
                .define('C', POMitems.TITANIUM_INGOT.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("", inventoryTrigger(ItemPredicate.ANY))
                .save(pFinishedRecipeConsumer);

    }
}