package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PixelSplitterRecipeBuilder extends POMRecipeBuilder {
    private final List<CountedIngredient> outputs;
    private final CountedIngredient ingredient;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private final String structure;

    public PixelSplitterRecipeBuilder(CountedIngredient ingredient, List<CountedIngredient> result, String structure, int[] r, int[] g, int[] b) {
        this.ingredient = ingredient;
        this.outputs = result;
        this.R = r;
        this.G = g;
        this.B = b;
        this.structure = structure;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.outputs, this.structure, this.R, this.G, this.B, this.ingredient, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final List<CountedIngredient> results;
        private final int[] R;
        private final int[] G;
        private final int[] B;
        private final String structure;
        private final CountedIngredient ingredient;

        public Result(ResourceLocation pId, List<CountedIngredient> pResult, String structure, int[] r, int[] g, int[] b, CountedIngredient ingredient, Advancement.Builder pAdvancement) {
            super(PixelSplitterRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.results = pResult;
            this.R = r;
            this.G = g;
            this.B = b;
            this.structure =structure;
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            JsonArray jsonArray1 = new JsonArray();
            for (CountedIngredient result : results) {
                jsonArray1.add(result.toJson());
            }
            pJson.add("outputs", jsonArray1);
            pJson.addProperty("structure", structure);

            JsonArray jsonArray2 = new JsonArray();
            for (int i=0; i < R.length; i++) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("R", R[i]);
                jsonObject.addProperty("G", G[i]);
                jsonObject.addProperty("B", B[i]);
                jsonArray2.add(jsonObject);
            }
            pJson.add("colors", jsonArray2);
        }
    }
}
