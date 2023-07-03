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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class PixelSplitterRecipeBuilder implements RecipeBuilder {
    private final List<CountedIngredient> outputs;
    private final CountedIngredient ingredient;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private final String structure;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public PixelSplitterRecipeBuilder(CountedIngredient ingredient, List<CountedIngredient> result, String structure, int[] r, int[] g, int[] b) {
        this.ingredient = ingredient;
        this.outputs = result;
        this.R = r;
        this.G = g;
        this.B = b;
        this.structure = structure;
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

    public List<CountedIngredient> getResults() {
        return outputs;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.outputs, this.structure, this.R, this.G, this.B, this.ingredient,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/splitting/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<CountedIngredient> results;
        private final int[] R;
        private final int[] G;
        private final int[] B;
        private final String structure;
        private final CountedIngredient ingredient;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, List<CountedIngredient> pResult, String structure, int[] r, int[] g, int[] b, CountedIngredient ingredient, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.results = pResult;
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
            JsonArray jsonArray1 = new JsonArray();
            for (CountedIngredient result : results) {
                jsonArray1.add(result.toJson());
            }
            pJson.add("outputs", jsonArray1);
            pJson.addProperty("structure", structure);

            JsonArray jsonArray2 = new JsonArray();
            for (int i=0; i < R.length; i++) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("R", R[i]);
                jsonObject.addProperty("G", G[i]);
                jsonObject.addProperty("B", B[i]);
                jsonArray2.add(jsonObject);
            }
            pJson.add("colors", jsonArray2);
        }

        @Override
        public @NotNull ResourceLocation getId() {
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
                    "splitting/"+name + "_splitting");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
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
