package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BallMillRecipeBuilder extends POMRecipeBuilder {
    private final ChanceIngredient output;
    private final List<CountedIngredient> ingredients;
    private final Ingredient ball;

    public BallMillRecipeBuilder(List<CountedIngredient> ingredients, ChanceIngredient output, Ingredient ball) {
        this.ingredients = ingredients;
        this.output = output;
        this.ball = ball;
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id,
                this.output, this.ball, this.ingredients,
                this.advancement
        );
    }

    public static class Result extends POMRecipeResult {
        private final ChanceIngredient result;
        private final List<CountedIngredient> ingredients;
        private final Ingredient ball;

        public Result(ResourceLocation pId, ChanceIngredient pResult, Ingredient pBall, List<CountedIngredient> ingredients, Advancement.Builder pAdvancement) {
            super(BallMillRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.result = pResult;
            this.ball = pBall;
            this.ingredients = ingredients;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            JsonArray inputArray = new JsonArray();
            for (CountedIngredient ingredient : ingredients) {
                inputArray.add(ingredient.toJson());
            }
            pJson.add("inputs", inputArray);

            JsonArray ballArray = new JsonArray();
            ballArray.add(ball.toJson());
            pJson.add("ball", ballArray);
            pJson.add("output", result.toJson());
        }
    }
}
