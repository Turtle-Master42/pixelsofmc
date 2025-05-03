package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChemicalMixerRecipeBuilder extends POMRecipeBuilder {
    private final List<FluidStack> inputFluids;
    private final List<FluidStack> outputFluids;
    private final int temperatureState;

    public ChemicalMixerRecipeBuilder(int temperatureState) {
        this.inputFluids = new ArrayList<>();
        this.outputFluids = new ArrayList<>();
        this.temperatureState = temperatureState;
    }

    public static ChemicalMixerRecipeBuilder build(int temperatureState) {
        return new ChemicalMixerRecipeBuilder(temperatureState);
    }

    public ChemicalMixerRecipeBuilder input(Fluid fluid, int amount) {
        inputFluids.add(new FluidStack(fluid, amount));
        return this;
    }

    public ChemicalMixerRecipeBuilder output(Fluid fluid, int amount) {
        outputFluids.add(new FluidStack(fluid, amount));
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        if (inputFluids.size() > 3) {
            throw new IndexOutOfBoundsException("Chemical Mixer recipe can't have more than 3 inputs, there where " + inputFluids.size() + " proved");
        }
        if (outputFluids.size() > 3) {
            throw new IndexOutOfBoundsException("Chemical Mixer recipe can't have more than 3 inputs, there where " + outputFluids.size() + " proved");
        }
        StringBuilder name = new StringBuilder();
        for (FluidStack output : outputFluids) {
            name.append("_");
            name.append(output.getFluid().getFluidType().toString().split(":")[1]);
        }
        name.deleteCharAt(0);
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("chemical_mixing/" + name));
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

    public static class Result extends POMRecipeResult {
        private final List<FluidStack> inputFluids;
        private final List<FluidStack> resultFluids;
        private final int temperatureState;

        public Result(ResourceLocation pId, List<FluidStack> pInFluid, List<FluidStack> pOutFluid, int temperatureState, Advancement.Builder pAdvancement) {
            super(ChemicalMixerRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.resultFluids = pOutFluid;
            this.inputFluids = pInFluid;
            this.temperatureState = temperatureState;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
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
