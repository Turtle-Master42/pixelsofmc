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

public class ChemicalMixerRecipeBuilder extends POMRecipeBuilder {
    private final List<FluidStack> inputFluids;
    private final List<FluidStack> outputFluids;
    private final int temperatureState;

    public ChemicalMixerRecipeBuilder(List<FluidStack> inputFluids, List<FluidStack> outputFluids, int temperatureState) {
        this.inputFluids = inputFluids;
        this.outputFluids = outputFluids;
        this.temperatureState = temperatureState;
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.inputFluids, this.outputFluids, this.temperatureState,
                this.advancement);
    }

    public static class Result extends POMResult {
        private final List<FluidStack> inputFluids;
        private final List<FluidStack> resultFluids;
        private final int temperatureState;

        public Result(ResourceLocation pId, List<FluidStack> pInFluid, List<FluidStack> pOutFluid, int temperatureState, Advancement.Builder pAdvancement) {
            super(ChemicalMixerRecipe.Serializer.INSTANCE, "chemical_mixing", pId, pAdvancement);
            this.resultFluids = pOutFluid;
            this.inputFluids = pInFluid;
            this.temperatureState = temperatureState;
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
    }
}
