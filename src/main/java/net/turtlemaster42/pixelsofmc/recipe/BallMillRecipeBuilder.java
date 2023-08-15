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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BallMillRecipeBuilder implements RecipeBuilder {
    private final CountedIngredient output;
    private final List<CountedIngredient> ingredients;
    private final Ingredient ball;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public BallMillRecipeBuilder(List<CountedIngredient> ingredients, ItemLike result, int outputCount, Ingredient ball) {
        this.ingredients = ingredients;
        this.output = CountedIngredient.of(outputCount, result);
        this.ball = ball;
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
        return output.asItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.output, this.ball, this.ingredients,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/milling/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CountedIngredient result;
        private final List<CountedIngredient> ingredients;
        private final Ingredient ball;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, CountedIngredient pResult, Ingredient pBall, List<CountedIngredient> ingredients, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ball = pBall;
            this.ingredients = ingredients;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            for (CountedIngredient ingredient : ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            pJson.add("inputs", jsonarray);


            JsonArray jsonarray2 = new JsonArray();
            jsonarray2.add(ball.toJson());
            pJson.add("ball", jsonarray2);
            pJson.add("output", result.toJson());
        }

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            String ingredient1 = "";
            String ingredient2 = "";
            String ingredient3 = "";
            String output = this.result.asItem().toString();

            if (!this.ingredients.get(0).ingredient().isEmpty()) {
                ingredient1 = this.ingredients.get(0).asItem() + "_";
                String jsonString = this.ingredients.get(0).ingredient().toJson().toString();

                if (jsonString.contains("{\"tag\":")) {
                    String jsonName = jsonString
                            .replace("{\"tag\":\"", "")
                            .replace("\"}", "")
                            .replace(":", "-")
                            .replace("/", "-");
                    ingredient1 = "tag-" + jsonName +"_";
                }
            }
            if (this.ingredients.size() > 1 && !this.ingredients.get(1).ingredient().isEmpty()) {
                ingredient2 = this.ingredients.get(1).asItem()+"_";
                String jsonString = this.ingredients.get(1).ingredient().toJson().toString();

                if (jsonString.contains("{\"tag\":")) {
                    String jsonName = jsonString
                            .replace("{\"tag\":\"", "")
                            .replace("\"}", "")
                            .replace(":", "-")
                            .replace("/", "-");
                    ingredient2 = "tag-" + jsonName +"_";
                }
            }
            if (this.ingredients.size() > 2 &&!this.ingredients.get(2).ingredient().isEmpty()) {
                ingredient3 = this.ingredients.get(2).asItem()+"_";
                String jsonString = this.ingredients.get(2).ingredient().toJson().toString();

                if (jsonString.contains("{\"tag\":")) {
                    String jsonName = jsonString
                            .replace("{\"tag\":\"", "")
                            .replace("\"}", "")
                            .replace(":", "-")
                            .replace("/", "-");
                    ingredient3 = "tag-" + jsonName +"_";
                }
            }

            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "milling/"+ingredient1+ingredient2+ingredient3+"to_"+output+"_milling");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return BallMillRecipe.Serializer.INSTANCE;
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
