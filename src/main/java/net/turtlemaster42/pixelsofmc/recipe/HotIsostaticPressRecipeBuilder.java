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
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HotIsostaticPressRecipeBuilder implements RecipeBuilder {
    private final Item output;
    private final int outputCount;
    private final int heat;
    private final CountedIngredient ingredient;
    private final CountedIngredient mold;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public HotIsostaticPressRecipeBuilder(CountedIngredient ingredient, CountedIngredient mold, ItemLike result, int outputCount, int heat) {
        this.ingredient = ingredient;
        this.mold = mold;
        this.outputCount = outputCount;
        this.output = result.asItem();
        this.heat = heat;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return output;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.output, this.outputCount, this.ingredient, this.mold, this.heat,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/pressing/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int resultCount;
        private final int heat;
        private final CountedIngredient ingredient;
        private final CountedIngredient mold;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, int pResultCount, CountedIngredient ingredient, CountedIngredient mold, int heat, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.resultCount = pResultCount;
            this.ingredient = ingredient;
            this.mold = mold;
            this.heat = heat;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            pJson.add("mold", mold.toJson());
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", this.result.getRegistryName().toString());
            jsonobject.addProperty("count", this.resultCount);
            pJson.add("output", jsonobject);
            pJson.addProperty("heat", heat);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "pressing/"+this.result.getRegistryName().getPath()+"_from_pressing");
        }

        @Override
        public RecipeSerializer<?> getType() {
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
