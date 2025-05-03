package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GrinderRecipeBuilder extends POMRecipeBuilder {
    private final Ingredient ingredient;
    private final List<ChanceIngredient> outputs;

    public GrinderRecipeBuilder(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.outputs = new ArrayList<>();
    }

    public static GrinderRecipeBuilder build(Item item) {
        return new GrinderRecipeBuilder(Ingredient.of(item));
    }
    public static GrinderRecipeBuilder build(TagKey<Item> tag) {
        return new GrinderRecipeBuilder(Ingredient.of(tag));
    }

    public GrinderRecipeBuilder output(Item item) {
        return output(item, 1, 1f);
    }
    public GrinderRecipeBuilder output(Item item, int count) {
        return output(item, count, 1f);
    }
    public GrinderRecipeBuilder output(Item item, float chance) {
        return output(item, 1, chance);
    }
    public GrinderRecipeBuilder output(Item item, int count, float chance) {
        outputs.add(ChanceIngredient.of(count, chance, item));
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("grinding/" + CountedIngredient.of(ingredient).asName()));
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
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            JsonArray jsonarray = new JsonArray();
            for (ChanceIngredient result : results) {
                jsonarray.add(result.toJson());
            }
            pJson.add("outputs", jsonarray);
        }
    }
}
