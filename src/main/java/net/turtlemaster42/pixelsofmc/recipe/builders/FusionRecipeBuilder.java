package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

public class FusionRecipeBuilder extends POMRecipeBuilder {
    private final Element element;
    private final CountedIngredient output;
    private final boolean x512;
    private final int protonCount;
    private final int neutronCount;

    public FusionRecipeBuilder(Element element, boolean x512) {
        this.x512 = x512;
        this.element = element;
        if (x512) {
            this.output = CountedIngredient.of(element.atom512());
            this.protonCount = element.getElement() * 8;
            this.neutronCount = element.getNeutrons() * 8;
        } else {
            this.output = CountedIngredient.of(element.atom64());
            this.protonCount = element.getElement();
            this.neutronCount = element.getNeutrons();
        }
    }

    public FusionRecipeBuilder(Element element, CountedIngredient output, int neutronCount, boolean x512) {
        this.x512 = x512;
        this.element = element;
        this.output = output;
        if (x512) {
            this.protonCount = element.getElement() * 8;
            this.neutronCount = neutronCount * 8;
        } else {
            this.protonCount = element.getElement();
            this.neutronCount = neutronCount;
        }
    }

    @Override
    public @NotNull Item getResult() {
        return output.asItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.output, this.element, this.protonCount, this.neutronCount, this.x512, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final int protonCount;
        private final int neutronCount;
        private final Element element;
        private final boolean x512;
        private final CountedIngredient output;

        public Result(ResourceLocation pId, CountedIngredient output, Element element, int protonCount, int neutronCount, boolean x512, Advancement.Builder pAdvancement) {
            super(FusionRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.protonCount = protonCount;
            this.neutronCount = neutronCount;
            this.element = element;
            this.x512 =x512;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.add("output", output.toJson());
            element.toJson(pJson);
            pJson.addProperty("x512", x512);
            pJson.addProperty("proton", protonCount);
            pJson.addProperty("neutron", neutronCount);
        }
    }
}

