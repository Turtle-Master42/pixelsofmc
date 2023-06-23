package net.turtlemaster42.pixelsofmc.recipe;

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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HotIsostaticPressRecipeBuilder implements RecipeBuilder {
    private final CountedIngredient output;
    private final int heat;
    private final int maxHeat;
    private final CountedIngredient ingredient;
    private final CountedIngredient mold;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public HotIsostaticPressRecipeBuilder(CountedIngredient ingredient, CountedIngredient mold, CountedIngredient result, int heat, int maxHeat) {
        this.ingredient = ingredient;
        this.mold = mold;
        this.output = result;
        this.heat = heat;
        this.maxHeat = maxHeat;
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

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.output, this.ingredient, this.mold, this.heat,
                this.maxHeat, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/pressing/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CountedIngredient result;
        private final int heat;
        private final int maxHeat;
        private final CountedIngredient ingredient;
        private final CountedIngredient mold;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, CountedIngredient pResult, CountedIngredient ingredient, CountedIngredient mold, int heat, int maxHeat, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ingredient = ingredient;
            this.mold = mold;
            this.heat = heat;
            this.maxHeat = maxHeat;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            pJson.add("mold", mold.toJson());
            pJson.add("output", result.toJson());
            pJson.addProperty("heat", heat);
            pJson.addProperty("max_heat", maxHeat);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            String name = this.result.asItem().toString();
            String jsonString = this.result.ingredient().toJson().toString();

            if (jsonString.contains("{\"tag\":")) {
                String jsonName = jsonString
                        .replace("{\"tag\":\"", "")
                        .replace("\"}", "")
                        .replace(":", "-")
                        .replace("/", "-");
                name = "tag-"+jsonName;
            }


            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "pressing/" + name + "_from_pressing");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return HotIsostaticPressRecipe.Serializer.INSTANCE;
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
