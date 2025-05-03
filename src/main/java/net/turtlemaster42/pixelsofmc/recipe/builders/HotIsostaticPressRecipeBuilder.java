package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class HotIsostaticPressRecipeBuilder extends POMRecipeBuilder {
    private final CountedIngredient output;
    private int heat;
    private int maxHeat;
    private CountedIngredient ingredient;
    private CountedIngredient mold;

    public HotIsostaticPressRecipeBuilder(CountedIngredient output) {
        this.ingredient = CountedIngredient.EMPTY;
        this.output = output;
        this.mold = CountedIngredient.EMPTY;
        this.heat = 0;
        this.maxHeat = 5000;
    }

    public static HotIsostaticPressRecipeBuilder build(Item item) {
        return build(item, 1);
    }

    public static HotIsostaticPressRecipeBuilder build(TagKey<Item> tag) {
        return build(tag, 1);
    }

    public static HotIsostaticPressRecipeBuilder build(Item item, int count) {
        return new HotIsostaticPressRecipeBuilder(CountedIngredient.of(count, item));
    }

    public static HotIsostaticPressRecipeBuilder build(TagKey<Item> tag, int count) {
        return new HotIsostaticPressRecipeBuilder(CountedIngredient.of(count, tag));
    }

    public HotIsostaticPressRecipeBuilder heat(int min, int max) {
        this.heat = Math.max(min, 0);
        this.maxHeat = Math.min(max, 5000);
        return this;
    }

    public HotIsostaticPressRecipeBuilder ballMold() {
        return mold(POMitems.BALL_CAST.get());
    }

    public HotIsostaticPressRecipeBuilder plateMold() {
        return mold(POMitems.PLATE_CAST.get());
    }

    public HotIsostaticPressRecipeBuilder ingotMold() {
        return mold(POMitems.INGOT_CAST.get());
    }

    public HotIsostaticPressRecipeBuilder mold(Item item) {
        this.mold = CountedIngredient.of(item);
        return this;
    }

    public HotIsostaticPressRecipeBuilder mold(TagKey<Item> tag) {
        this.mold = CountedIngredient.of(tag);
        return this;
    }

    public HotIsostaticPressRecipeBuilder input(Item item) {
        this.ingredient = CountedIngredient.of(item);
        return this;
    }

    public HotIsostaticPressRecipeBuilder input(TagKey<Item> tag) {
        this.ingredient = CountedIngredient.of(tag);
        return this;
    }

    public HotIsostaticPressRecipeBuilder input(Item item, int count) {
        this.ingredient = CountedIngredient.of(count, item);
        return this;
    }

    public HotIsostaticPressRecipeBuilder input(TagKey<Item> tag, int count) {
        this.ingredient = CountedIngredient.of(count, tag);
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("pressing/" + output.asName()));
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.output, this.ingredient, this.mold, this.heat,
                this.maxHeat, this.advancement
        );
    }

    public static class Result extends POMRecipeResult {
        private final CountedIngredient result;
        private final int heat;
        private final int maxHeat;
        private final CountedIngredient ingredient;
        private final CountedIngredient mold;

        public Result(ResourceLocation pId, CountedIngredient pResult, CountedIngredient ingredient, CountedIngredient mold, int heat, int maxHeat,
                      Advancement.Builder pAdvancement) {
            super(HotIsostaticPressRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.result = pResult;
            this.ingredient = ingredient;
            this.mold = mold;
            this.heat = heat;
            this.maxHeat = maxHeat;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("input", ingredient.toJson());
            pJson.add("mold", mold.toJson());
            pJson.add("output", result.toJson());
            pJson.addProperty("heat", heat);
            pJson.addProperty("max_heat", maxHeat);
        }
    }
}
