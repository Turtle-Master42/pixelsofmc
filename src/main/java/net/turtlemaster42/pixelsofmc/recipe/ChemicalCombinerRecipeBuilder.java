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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ChemicalCombinerRecipeBuilder implements RecipeBuilder {
    private final List<CountedIngredient> ingredients;
    private final FluidStack inputFluid;
    private final ChanceIngredient output;
    private final FluidStack outputFluid;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ChemicalCombinerRecipeBuilder(ChanceIngredient result, FluidStack inputFluid, FluidStack outputFluid, List<CountedIngredient> ingredients) {
        this.ingredients = ingredients;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.output = result;
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

    public ChanceIngredient getFullResult() {
        return output;
    }
    public FluidStack getInputFluid() {
        return inputFluid;
    }
    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.ingredients, this.inputFluid, this.outputFluid, this.output,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/chemical_combining/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<CountedIngredient> ingredients;

        private final FluidStack inputFluid;
        private final ChanceIngredient result;

        private final FluidStack resultFluid;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, List<CountedIngredient> pIngredients, FluidStack pInFluid, FluidStack pOutFluid, ChanceIngredient pResult, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.resultFluid = pOutFluid;
            this.ingredients = pIngredients;
            this.inputFluid = pInFluid;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("output", result.toJson());
            pJson.add("fluid_output", FluidJSONUtil.toJson(resultFluid));
            JsonArray jsonarray = new JsonArray();
            for (CountedIngredient ingredient : ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            pJson.add("inputs", jsonarray);
            pJson.add("fluid_input", FluidJSONUtil.toJson(inputFluid));
        }

        @Override
        public @NotNull ResourceLocation getId() {
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
                    "chemical_combining/"+ingredient1+ingredient2+ingredient3+"to_"+output+"_combining");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ChemicalCombinerRecipe.Serializer.INSTANCE;
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
