package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalSeparatorRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChemicalSeparatorRecipeBuilder extends POMRecipeBuilder {
    private final CountedIngredient ingredient;
    private final FluidStack inputFluid;
    private final List<ChanceIngredient> outputs;
    private FluidStack outputFluid;

    public ChemicalSeparatorRecipeBuilder(CountedIngredient ingredient, FluidStack inputFluid) {
        this.outputs = new ArrayList<>();
        this.outputFluid = FluidStack.EMPTY;
        this.inputFluid = inputFluid;
        this.ingredient = ingredient;
    }

    public static ChemicalSeparatorRecipeBuilder build(Item item, Fluid fluid, int amount) {
        return build(item, 1, fluid, amount);
    }
    public static ChemicalSeparatorRecipeBuilder build(Item item) {
        return build(item, 1);
    }
    public static ChemicalSeparatorRecipeBuilder build(Item item, int count) {
        return new ChemicalSeparatorRecipeBuilder(CountedIngredient.of(count, item), FluidStack.EMPTY);
    }
    public static ChemicalSeparatorRecipeBuilder build(Fluid fluid, int amount) {
        return new ChemicalSeparatorRecipeBuilder(CountedIngredient.EMPTY, new FluidStack(fluid, amount));
    }
    public static ChemicalSeparatorRecipeBuilder build(Item item, int count, Fluid fluid, int amount) {
        return new ChemicalSeparatorRecipeBuilder(CountedIngredient.of(count, item), new FluidStack(fluid, amount));
    }
    public static ChemicalSeparatorRecipeBuilder build(TagKey<Item> tag) {
        return build(tag, 1);
    }
    public static ChemicalSeparatorRecipeBuilder build(TagKey<Item> tag, int count) {
        return new ChemicalSeparatorRecipeBuilder(CountedIngredient.of(count, tag), FluidStack.EMPTY);
    }
    public static ChemicalSeparatorRecipeBuilder build(TagKey<Item> tag, Fluid fluid, int amount) {
        return build(tag, 1, fluid, amount);
    }
    public static ChemicalSeparatorRecipeBuilder build(TagKey<Item> tag, int count, Fluid fluid, int amount) {
        return new ChemicalSeparatorRecipeBuilder(CountedIngredient.of(count, tag), new FluidStack(fluid, amount));
    }

    public ChemicalSeparatorRecipeBuilder output(Item item) {
        this.outputs.add(ChanceIngredient.of(1, 1f, item));
        return this;
    }
    public ChemicalSeparatorRecipeBuilder output(Item item, int count) {
        this.outputs.add(ChanceIngredient.of(count, 1f, item));
        return this;
    }
    public ChemicalSeparatorRecipeBuilder output(Item item, float chance) {
        this.outputs.add(ChanceIngredient.of(1, chance, item));
        return this;
    }
    public ChemicalSeparatorRecipeBuilder output(Fluid fluid, int amount) {
        this.outputFluid = new FluidStack(fluid, amount);
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        if (outputs.size() > 3) {
            throw new IndexOutOfBoundsException("Chemical Seperator recipe can't have more than 3 inputs, there where " + outputs.size() + " proved");
        }
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL(
                "chemical_separating/" + (ingredient.isEmpty() ? inputFluid.getFluid().getFluidType().toString().split(":")[1] : ingredient.asName())
        ));
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

    public static class Result extends POMRecipeResult {
        private final CountedIngredient ingredient;
        private final FluidStack inputFluid;
        private final List<ChanceIngredient> results;
        private final FluidStack resultFluid;

        public Result(ResourceLocation pId, CountedIngredient pIngredient, FluidStack pInFluid, FluidStack pOutFluid, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement) {
            super(ChemicalSeparatorRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.results = pResults;
            this.resultFluid = pOutFluid;
            this.ingredient = pIngredient;
            this.inputFluid = pInFluid;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
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
