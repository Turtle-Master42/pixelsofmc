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

public class ChemicalSeparatorRecipeBuilder extends POMRecipeBuilder {
    private final CountedIngredient ingredient;
    private final FluidStack inputFluid;
    private final List<ChanceIngredient> outputs;
    private final FluidStack outputFluid;

    public ChemicalSeparatorRecipeBuilder(CountedIngredient ingredients, FluidStack inputFluid, FluidStack outputFluid, List<ChanceIngredient> result) {
        this.ingredient = ingredients;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.outputs = result;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.ingredient, this.inputFluid, this.outputFluid, this.outputs,
                this.advancement);
    }

    public static class Result extends POMResult {
        private final CountedIngredient ingredient;
        private final FluidStack inputFluid;
        private final List<ChanceIngredient> results;
        private final FluidStack resultFluid;

        public Result(ResourceLocation pId, CountedIngredient pIngredient, FluidStack pInFluid, FluidStack pOutFluid, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement) {
            super(ChemicalSeparatorRecipe.Serializer.INSTANCE, "chemical_separating", pId, pAdvancement);
            this.results = pResults;
            this.resultFluid = pOutFluid;
            this.ingredient = pIngredient;
            this.inputFluid = pInFluid;
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
    }
}
