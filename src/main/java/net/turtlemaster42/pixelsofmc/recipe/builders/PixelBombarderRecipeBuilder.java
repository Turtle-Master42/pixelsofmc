package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelBombarderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PixelBombarderRecipeBuilder extends POMRecipeBuilder {
    private final CountedIngredient input;
    private int color;
    private int type = 0;
    private final List<ChanceIngredient> output;

    public PixelBombarderRecipeBuilder(CountedIngredient input) {
        this.input = input;
        this.output = new ArrayList<>();
    }

    public static PixelBombarderRecipeBuilder build(Item item, int count) {
        return new PixelBombarderRecipeBuilder(CountedIngredient.of(count, item));
    }
    public static PixelBombarderRecipeBuilder build(TagKey<Item> tag, int count) {
        return new PixelBombarderRecipeBuilder(CountedIngredient.of(count, tag));
    }

    public static PixelBombarderRecipeBuilder build(Item item) {
        return new PixelBombarderRecipeBuilder(CountedIngredient.of(1, item));
    }
    public static PixelBombarderRecipeBuilder build(TagKey<Item> tag) {
        return new PixelBombarderRecipeBuilder(CountedIngredient.of(1, tag));
    }

    public PixelBombarderRecipeBuilder laser(Color color) {
        return laser(color, 0);
    }

    public PixelBombarderRecipeBuilder laser(int color) {
        return laser(color, 0);
    }

    public PixelBombarderRecipeBuilder laser(Color color, int type) {
        return laser(color.getRGB(), type);
    }

    public PixelBombarderRecipeBuilder laser(int color, int type) {
        this.color = color;
        this.type = type;
        return this;
    }

    public PixelBombarderRecipeBuilder output(Item item) {
        return output(item, 1, 1f);
    }
    public PixelBombarderRecipeBuilder output(Item item, int count) {
        return output(item, count, 1f);
    }
    public PixelBombarderRecipeBuilder output(Item item, float chance) {
        return output(item, 1, chance);
    }
    public PixelBombarderRecipeBuilder output(Item item, int count, float chance) {
        output.add(ChanceIngredient.of(count, chance, item));
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        if (output.size() > 1) {
            throw new IndexOutOfBoundsException("Pixel Bombarder recipe can't have more than 1 output, there where " + output.size() + " proved");
        }
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("bombarding/" + input.asName()));
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.input, this.output, this.type, this.color, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final CountedIngredient input;
        private final int color;
        private final int type;
        private final List<ChanceIngredient> outputs;

        public Result(ResourceLocation pId, CountedIngredient pIngredient, List<ChanceIngredient> pResults, int pType, int pColor, Advancement.Builder pAdvancement) {
            super(PixelBombarderRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.outputs = pResults;
            this.input = pIngredient;
            this.type = pType;
            this.color = pColor;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("input", input.toJson());
            pJson.addProperty("laser_type", type);
            pJson.addProperty("laser_color", color);
            pJson.add("output", outputs.get(0).toJson());
        }
    }
}
