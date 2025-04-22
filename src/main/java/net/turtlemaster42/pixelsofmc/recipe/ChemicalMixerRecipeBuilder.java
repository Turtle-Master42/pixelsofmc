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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ChemicalMixerRecipeBuilder implements RecipeBuilder {
    private final List<FluidStack> inputFluids;
    private final List<FluidStack> outputFluids;
    private final int temperatureState;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ChemicalMixerRecipeBuilder(List<FluidStack> inputFluids, List<FluidStack> outputFluids, int temperatureState) {
        this.inputFluids = inputFluids;
        this.outputFluids = outputFluids;
        this.temperatureState = temperatureState;
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
        return Items.AIR;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.inputFluids, this.outputFluids, this.temperatureState,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/chemical_mixing/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        private final List<FluidStack> inputFluids;
        private final List<FluidStack> resultFluids;
        private final int temperatureState;

        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, List<FluidStack> pInFluid, List<FluidStack> pOutFluid, int temperatureState, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.resultFluids = pOutFluid;
            this.inputFluids = pInFluid;
            this.temperatureState = temperatureState;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray inputJson = new JsonArray();
            for (FluidStack fluidStack : inputFluids) {
                inputJson.add(FluidJSONUtil.toJson(fluidStack));
            }
            pJson.add("fluid_inputs", inputJson);

            JsonArray outputJson = new JsonArray();
            for (FluidStack fluidStack : resultFluids) {
                outputJson.add(FluidJSONUtil.toJson(fluidStack));
            }
            pJson.add("fluid_outputs", outputJson);
            pJson.addProperty("temperature_state", temperatureState);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            String fluid1 = "";
            String fluid2 = "";
            String fluid3 = "";

            if (!this.inputFluids.get(0).isEmpty()) {
                fluid1 = this.inputFluids.get(0).getFluid().getFluidType().toString().split(":")[1];
            }

            if (this.inputFluids.size() > 1 && !this.inputFluids.get(1).isEmpty()) {
                fluid2 = "_" + this.inputFluids.get(1).getFluid().getFluidType().toString().split(":")[1];
            }

            if (this.inputFluids.size() > 2 && !this.inputFluids.get(2).isEmpty()) {
                fluid3 = "_" + this.inputFluids.get(2).getFluid().getFluidType().toString().split(":")[1];
            }

            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "chemical_mixing/"+fluid1+fluid2+fluid3);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ChemicalMixerRecipe.Serializer.INSTANCE;
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
