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
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ChemicalSeparatorRecipeBuilder implements RecipeBuilder {
    private final CountedIngredient ingredient;
    private final FluidStack inputFluid;
    private final List<ChanceIngredient> outputs;
    private final FluidStack outputFluid;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ChemicalSeparatorRecipeBuilder(CountedIngredient ingredients, FluidStack inputFluid, FluidStack outputFluid, List<ChanceIngredient> result) {
        this.ingredient = ingredients;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
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

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.ingredient, this.inputFluid, this.outputFluid, this.outputs,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/chemical_separating/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CountedIngredient ingredient;

        private final FluidStack inputFluid;
        private final List<ChanceIngredient> results;

        private final FluidStack resultFluid;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, CountedIngredient pIngredient, FluidStack pInFluid, FluidStack pOutFluid, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.results = pResults;
            this.resultFluid = pOutFluid;
            this.ingredient = pIngredient;
            this.inputFluid = pInFluid;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            pJson.add("fluid_input", FluidJSONUtil.toJson(inputFluid));
            JsonArray jsonarray = new JsonArray();
            for (ChanceIngredient result : results) {
                jsonarray.add(result.toJson());
            }
            pJson.add("outputs", jsonarray);
            pJson.add("fluid_output", FluidJSONUtil.toJson(resultFluid));
        }

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            String name = this.ingredient.getItems()[0].getItem().toString();
            String jsonString = this.ingredient.ingredient().toJson().toString();

            if (jsonString.contains("{\"tag\":")) {
                String jsonName = jsonString
                        .replace("{\"tag\":\"", "")
                        .replace("\"}", "")
                        .replace(":", "-")
                        .replace("/", "-");
                name = "tag-"+jsonName;
            }

            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "chemical_separating/" + name + "_separating");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ChemicalSeparatorRecipe.Serializer.INSTANCE;
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
