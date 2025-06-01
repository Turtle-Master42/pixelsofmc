package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.FluidCoolingRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FluidCoolingRecipeBuilder extends POMRecipeBuilder {
    private FluidStack inputFluid;
    private FluidStack outputFluid;
    private final int energy;

    public FluidCoolingRecipeBuilder(int energy) {
        this.inputFluid = FluidStack.EMPTY;
        this.outputFluid = FluidStack.EMPTY;
        this.energy = energy;
    }

    public static FluidCoolingRecipeBuilder build(int energy) {
        return new FluidCoolingRecipeBuilder(energy);
    }

    public FluidCoolingRecipeBuilder input(Fluid fluid, int amount) {
        this.inputFluid = new FluidStack(fluid, amount);
        return this;
    }

    public FluidCoolingRecipeBuilder output(Fluid fluid, int amount) {
        this.outputFluid = new FluidStack(fluid, amount);
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("fluid_cooling/" + outputFluid.getFluid().getFluidType().toString().split(":")[1]));
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.inputFluid, this.outputFluid, this.energy, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final FluidStack inputFluid;
        private final FluidStack resultFluid;
        private final int energy;

        public Result(ResourceLocation pId, FluidStack pInFluid, FluidStack pOutFluid, int pEnergy, Advancement.Builder pAdvancement) {
            super(FluidCoolingRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.resultFluid = pOutFluid;
            this.inputFluid = pInFluid;
            this.energy = pEnergy;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("fluid_input", FluidJSONUtil.toJson(inputFluid));
            pJson.add("fluid_output", FluidJSONUtil.toJson(resultFluid));
            pJson.addProperty("energy", energy);
        }
    }
}
