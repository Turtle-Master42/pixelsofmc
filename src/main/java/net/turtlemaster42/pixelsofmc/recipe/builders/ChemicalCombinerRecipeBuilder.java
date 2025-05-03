package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalCombinerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChemicalCombinerRecipeBuilder extends POMRecipeBuilder {
    private final List<CountedIngredient> ingredients;
    private FluidStack inputFluid;
    private final ChanceIngredient output;
    private final FluidStack outputFluid;

    public ChemicalCombinerRecipeBuilder(ChanceIngredient result, FluidStack outputFluid) {
        this.ingredients = new ArrayList<>();
        this.outputFluid = outputFluid;
        this.inputFluid = FluidStack.EMPTY;
        this.output = result;
    }

    public static ChemicalCombinerRecipeBuilder build(Item item) {
        return build(item, 1, 1f);
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, int count) {
        return build(item, count, 1f);
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, float chance) {
        return build(item, 1, chance);
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, int count, float chance) {
        return new ChemicalCombinerRecipeBuilder(ChanceIngredient.of(count, chance, item), FluidStack.EMPTY);
    }
    public static ChemicalCombinerRecipeBuilder build(Fluid fluid, int amount) {
        return new ChemicalCombinerRecipeBuilder(ChanceIngredient.EMPTY, new FluidStack(fluid, amount));
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, Fluid fluid, int amount) {
        return build(item, 1, 1f, fluid, amount);
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, int count, Fluid fluid, int amount) {
        return build(item, count, 1f, fluid, amount);
    }
    public static ChemicalCombinerRecipeBuilder build(Item item, float chance, Fluid fluid, int amount) {
        return build(item, 1, chance, fluid, amount);
    }

    public static ChemicalCombinerRecipeBuilder build(Item item, int count, float chance, Fluid fluid, int amount) {
        return new ChemicalCombinerRecipeBuilder(ChanceIngredient.of(count, chance, item), new FluidStack(fluid, amount));
    }

    public ChemicalCombinerRecipeBuilder input(Item item) {
        this.ingredients.add(CountedIngredient.of(item));
        return this;
    }
    public ChemicalCombinerRecipeBuilder input(TagKey<Item> tag) {
        this.ingredients.add(CountedIngredient.of(tag));
        return this;
    }
    public ChemicalCombinerRecipeBuilder input(Item item, int count) {
        this.ingredients.add(CountedIngredient.of(count, item));
        return this;
    }
    public ChemicalCombinerRecipeBuilder input(TagKey<Item> tag, int count) {
        this.ingredients.add(CountedIngredient.of(count, tag));
        return this;
    }

    public ChemicalCombinerRecipeBuilder input(Fluid fluid, int amount) {
        this.inputFluid = new FluidStack(fluid, amount);
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        if (ingredients.size() > 3) {
            throw new IndexOutOfBoundsException("Chemical Combiner recipe can't have more than 3 inputs, there where " + ingredients.size() + " proved");
        }
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL(
                "chemical_combining/" + (output.isEmpty() ? outputFluid.getFluid().getFluidType().toString().split(":")[1] : output.asName())
        ));
    }


    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.ingredients, this.inputFluid, this.outputFluid, this.output, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final List<CountedIngredient> ingredients;
        private final FluidStack inputFluid;
        private final ChanceIngredient result;
        private final FluidStack resultFluid;

        public Result(ResourceLocation pId, List<CountedIngredient> pIngredients, FluidStack pInFluid, FluidStack pOutFluid, ChanceIngredient pResult, Advancement.Builder pAdvancement) {
            super(ChemicalCombinerRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.result = pResult;
            this.resultFluid = pOutFluid;
            this.ingredients = pIngredients;
            this.inputFluid = pInFluid;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
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
