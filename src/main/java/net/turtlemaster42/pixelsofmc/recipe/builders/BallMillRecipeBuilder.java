package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BallMillRecipeBuilder extends POMRecipeBuilder {
    private final ChanceIngredient output;
    private final List<CountedIngredient> ingredients;
    private Ingredient ball;

    public BallMillRecipeBuilder(ChanceIngredient output) {
        this.output = output;
        this.ingredients = new ArrayList<>();
    }

    public static BallMillRecipeBuilder build(Item item) {
        return build(item, 1);
    }
    public static BallMillRecipeBuilder build(Item item, int count) {
        return new BallMillRecipeBuilder(ChanceIngredient.of(count, 1f, item));
    }

    public BallMillRecipeBuilder ball(Item item) {
        this.ball = Ingredient.of(item);
        return this;
    }
    public BallMillRecipeBuilder ball(TagKey<Item> tag) {
        this.ball = Ingredient.of(tag);
        return this;
    }

    public BallMillRecipeBuilder input(Item item) {
        this.ingredients.add(CountedIngredient.of(item));
        return this;
    }
    public BallMillRecipeBuilder input(TagKey<Item> tag) {
        this.ingredients.add(CountedIngredient.of(tag));
        return this;
    }
    public BallMillRecipeBuilder input(Item item, int count) {
        this.ingredients.add(CountedIngredient.of(count, item));
        return this;
    }
    public BallMillRecipeBuilder input(TagKey<Item> tag, int count) {
        this.ingredients.add(CountedIngredient.of(count, tag));
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        if (ingredients.size() > 3) {
            throw new IndexOutOfBoundsException("Ball Mill recipe can't have more than 3 inputs, there where " + ingredients.size() + " proved");
        }
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("milling/" + output.asName()));
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
            pJson.add("ball", ball.toJson());
            pJson.add("output", result.toJson());
        }
    }
}
