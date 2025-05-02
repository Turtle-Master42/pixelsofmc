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

public class ChemicalCombinerRecipeBuilder extends POMRecipeBuilder {
    private final List<CountedIngredient> ingredients;
    private final FluidStack inputFluid;
    private final ChanceIngredient output;
    private final FluidStack outputFluid;

    public ChemicalCombinerRecipeBuilder(ChanceIngredient result, FluidStack inputFluid, FluidStack outputFluid, List<CountedIngredient> ingredients) {
        this.ingredients = ingredients;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.output = result;
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.ingredients, this.inputFluid, this.outputFluid, this.output, this.advancement);
    }

    public static class Result extends POMResult {
        private final List<CountedIngredient> ingredients;
        private final FluidStack inputFluid;
        private final ChanceIngredient result;
        private final FluidStack resultFluid;

        public Result(ResourceLocation pId, List<CountedIngredient> pIngredients, FluidStack pInFluid, FluidStack pOutFluid, ChanceIngredient pResult, Advancement.Builder pAdvancement) {
            super(ChemicalCombinerRecipe.Serializer.INSTANCE, "chemical_combining", pId, pAdvancement);
            this.result = pResult;
            this.resultFluid = pOutFluid;
            this.ingredients = pIngredients;
            this.inputFluid = pInFluid;
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
    }
}
