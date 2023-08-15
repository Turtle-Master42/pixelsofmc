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

public class GrinderRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final List<ChanceIngredient> outputs;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public GrinderRecipeBuilder(Ingredient ingredients, List<ChanceIngredient> result) {
        this.ingredient = ingredients;
        this.outputs = result;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String pCriterionName, @NotNull CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    public List<ChanceIngredient> getResults() {
        return outputs;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.ingredient, this.outputs,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/grinding/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final List<ChanceIngredient> results;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Ingredient pIngredient, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.results = pResults;
            this.ingredient = pIngredient;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
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

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            String name = this.ingredient.getItems()[0].getItem().toString();
            String jsonString = this.ingredient.toJson().toString();

            if (this.ingredient.toJson().toString().contains("{\"tag\":")) {
                String jsonName = jsonString
                        .replace("{\"tag\":\"", "")
                        .replace("\"}", "")
                        .replace(":", "-")
                        .replace("/", "-");
                name = "tag-"+jsonName;
            }

            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "grinding/" + name + "_grinding");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return GrinderRecipe.Serializer.INSTANCE;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
