package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PixelSplitterRecipeBuilder implements RecipeBuilder {
    private final CountedIngredient output;
    private final CountedIngredient ingredient;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private String structure;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public PixelSplitterRecipeBuilder(CountedIngredient ingredient, CountedIngredient result, String structure, int[] r, int[] g, int[] b) {
        this.ingredient = ingredient;
        this.output = result;
        this.R = r;
        this.G = g;
        this.B = b;
        this.structure = structure;
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
        return output.asItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.output, this.structure, this.R, this.G, this.B, this.ingredient,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/splitting/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CountedIngredient result;
        private final int[] R;
        private final int[] G;
        private final int[] B;
        private String structure;
        private final CountedIngredient ingredient;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, CountedIngredient pResult, String structure, int[] r, int[] g, int[] b, CountedIngredient ingredient, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.R = r;
            this.G = g;
            this.B = b;
            this.structure =structure;
            this.ingredient = ingredient;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            pJson.add("output", result.toJson());
            pJson.addProperty("structure", structure);

            JsonArray jsonarray = new JsonArray();
            for (int i=0; i < R.length; i++) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("R", R[i]);
                jsonObject.addProperty("G", G[i]);
                jsonObject.addProperty("B", B[i]);
                jsonarray.add(jsonObject);
            }
            pJson.add("colors", jsonarray);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "splitting/"+this.ingredient.asItem().toString() + "_splitting");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return PixelSplitterRecipe.Serializer.INSTANCE;
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
