package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GrinderRecipeBuilder extends POMRecipeBuilder {
    private final Ingredient ingredient;
    private final List<ChanceIngredient> outputs;

    public GrinderRecipeBuilder(Ingredient ingredients, List<ChanceIngredient> result) {
        this.ingredient = ingredients;
        this.outputs = result;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.ingredient, this.outputs, this.advancement);
    }

    public static class Result extends POMResult {
        private final Ingredient ingredient;
        private final List<ChanceIngredient> results;

        public Result(ResourceLocation pId, Ingredient pIngredient, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement) {
            super(GrinderRecipe.Serializer.INSTANCE, "grinding", pId, pAdvancement);
            this.results = pResults;
            this.ingredient = pIngredient;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            JsonArray jsonarray = new JsonArray();
            for (ChanceIngredient result : results) {
                jsonarray.add(result.toJson());
            }
            pJson.add("outputs", jsonarray);
        }
    }
}
