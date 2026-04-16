package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.recipe.machines.LaserSourceRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Consumer;

public class LaserSourceRecipeBuilder extends POMRecipeBuilder {
    private final CountedIngredient input;
    private final int inputColor;
    private int outputColor = 0;
    private int outputType = 0;
    private int sizeModifier = 0;
    private int sizeRequirement = 1;

    public LaserSourceRecipeBuilder(CountedIngredient input, int color) {
        this.input = input;
        this.inputColor = color;
    }

    public static LaserSourceRecipeBuilder build(Item item, Color color) {
        return new LaserSourceRecipeBuilder(CountedIngredient.of(1, item), color.getRGB());
    }
    public static LaserSourceRecipeBuilder build(TagKey<Item> tag, Color color) {
        return new LaserSourceRecipeBuilder(CountedIngredient.of(1, tag), color.getRGB());
    }
    public static LaserSourceRecipeBuilder build(Item item, int color) {
        return new LaserSourceRecipeBuilder(CountedIngredient.of(1, item), color);
    }
    public static LaserSourceRecipeBuilder build(TagKey<Item> tag, int color) {
        return new LaserSourceRecipeBuilder(CountedIngredient.of(1, tag), color);
    }

    public LaserSourceRecipeBuilder outputLaser(Color color) {
        this.outputColor = color.getRGB();
        return this;
    }

    public LaserSourceRecipeBuilder outputLaser(int color) {
        this.outputColor = color;
        return this;
    }

    public LaserSourceRecipeBuilder outputType(int type) {
        this.outputType = type;
        return this;
    }

    public LaserSourceRecipeBuilder sizeModifier(int modifier) {
        this.sizeModifier = modifier;
        return this;
    }

    public LaserSourceRecipeBuilder sizeRequirement(int requirement) {
        this.sizeRequirement = requirement;
        return this;
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("laser/" + input.asName()));
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.inputColor, this.input, sizeRequirement, this.outputColor, this.outputType, this.sizeModifier, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final int inputColor;
        private final CountedIngredient input;
        private final int outputColor;
        private final int outputType;
        private final int sizeRequirement;
        private final int sizeModifier;


        public Result(ResourceLocation pId, int pInputColor, CountedIngredient pIngredient, int pSizeRequirement, int pOutputColor, int pOutputType, int pSizeModifier, Advancement.Builder pAdvancement) {
            super(LaserSourceRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.inputColor = pInputColor;
            this.input = pIngredient;
            this.outputColor = pOutputColor;
            this.outputType = pOutputType;
            this.sizeRequirement = pSizeRequirement;
            this.sizeModifier = pSizeModifier;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("input", input.toJson());
            pJson.addProperty("input_color", inputColor);
            pJson.addProperty("output_color", outputColor);
            pJson.addProperty("output_type", outputType);
            pJson.addProperty("size_modifier", sizeModifier);
            pJson.addProperty("min_size", sizeRequirement);
        }
    }
}
