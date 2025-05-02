package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelAssemblerRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PixelAssemblerRecipeBuilder extends POMRecipeBuilder {
    private final List<CountedIngredient> inputs;
    private final CountedIngredient output;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private final String structure;

    public PixelAssemblerRecipeBuilder(List<CountedIngredient> ingredients, CountedIngredient result, String structure, int[] r, int[] g, int[] b) {
        this.inputs = ingredients;
        this.output = result;
        this.R = r;
        this.G = g;
        this.B = b;
        this.structure = structure;
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.output, this.structure, this.R, this.G, this.B, this.inputs, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final List<CountedIngredient> ingredients;
        private final String structure;
        private final int[] R;
        private final int[] G;
        private final int[] B;
        private final CountedIngredient result;

        public Result(ResourceLocation pId, CountedIngredient pResult, String structure, int[] r, int[] g, int[] b, List<CountedIngredient> ingredients, Advancement.Builder pAdvancement) {
            super(PixelAssemblerRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.result = pResult;
            this.structure =structure;
            this.R = r;
            this.G = g;
            this.B = b;
            this.ingredients = ingredients;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("output", result.toJson());
            JsonArray jsonArray1 = new JsonArray();
            for (CountedIngredient result : ingredients) {
                jsonArray1.add(result.toJson());
            }
            pJson.add("inputs", jsonArray1);
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
