package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GrinderRecipeBuilder extends POMRecipeBuilder {
    private final Ingredient ingredient;
    private final List<ChanceIngredient> outputs;

    public GrinderRecipeBuilder(Ingredient ingredients, List<ChanceIngredient> result) {
        this.ingredient = ingredients;
        this.outputs = result;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.ingredient, this.outputs, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final Ingredient ingredient;
        private final List<ChanceIngredient> results;

        public Result(ResourceLocation pId, Ingredient pIngredient, List<ChanceIngredient> pResults, Advancement.Builder pAdvancement) {
            super(GrinderRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.results = pResults;
            this.ingredient = pIngredient;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            JsonArray jsonarray = new JsonArray();
            for (ChanceIngredient result : results) {
                jsonarray.add(result.toJson());
            }
            pJson.add("outputs", jsonarray);
        }
    }
}
