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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FusionRecipeBuilder implements RecipeBuilder {
    private final Element element;
    private final ItemStack output;
    private final boolean x512;
    private final int protonCount;
    private final int neutronCount;
    private final int electronCount;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public FusionRecipeBuilder(Element element, int protonCount, int neutronCount, int electronCount, boolean x512) {
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.electronCount = electronCount;
        this.x512 = x512;
        this.element = element;
        if (x512) {
            this.output = new ItemStack(element.atom512());
        } else {
            this.output = new ItemStack(element.atom64());
        }
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
        return output.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.element, this.protonCount, this.neutronCount, this.electronCount, this.x512,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/misc/fusing/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final int protonCount;
        private final int neutronCount;
        private final int electronCount;
        private final Element element;
        private final boolean x512;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Element element, int protonCount, int neutronCount, int electronCount, boolean x512, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.protonCount = protonCount;
            this.neutronCount = neutronCount;
            this.electronCount = electronCount;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.element = element;
            this.x512 =x512;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            element.toJson(pJson);
            pJson.addProperty("x512", x512);
            pJson.addProperty("proton", protonCount);
            pJson.addProperty("neutron", neutronCount);
            pJson.addProperty("electron", electronCount);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            ResourceLocation id = this.id;
            String name;
            if (x512) {
                name = element.atom512().asItem().toString();
            } else {
                name = element.atom64().asItem().toString();
            }

            return new ResourceLocation(PixelsOfMc.MOD_ID,
                    "fusing/"+name+"_fusing");
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

