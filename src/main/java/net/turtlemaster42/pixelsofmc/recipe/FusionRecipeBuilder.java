package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FusionRecipeBuilder implements RecipeBuilder {
    private final CountedIngredient output;
    private final int protonCount;
    private final int neutronCount;
    private final int electronCount;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public FusionRecipeBuilder(ItemLike result, int outputCount, int protonCount, int neutronCount, int electronCount) {
        this.output = CountedIngredient.of(outputCount, result);
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.electronCount = electronCount;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String pCriterionName, @NotNull CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.output, this.protonCount, this.neutronCount, this.electronCount,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/fusing/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CountedIngredient result;
        private final int protonCount;
        private final int neutronCount;
        private final int electronCount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, CountedIngredient pResult, int protonCount, int neutronCount, int electronCount, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.protonCount = protonCount;
            this.neutronCount = neutronCount;
            this.electronCount = electronCount;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("output", result.toJson());
            pJson.addProperty("proton", protonCount);
            pJson.addProperty("neutron", neutronCount);
            pJson.addProperty("electron", electronCount);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "fusing/"+result.asItem()+"_fusing");
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return FusionRecipe.Serializer.INSTANCE;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

