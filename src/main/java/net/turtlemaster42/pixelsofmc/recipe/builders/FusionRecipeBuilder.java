package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

public class FusionRecipeBuilder extends POMRecipeBuilder {
    private final Element element;
    private final ItemStack output;
    private final boolean x512;
    private final int protonCount;
    private final int neutronCount;

    public FusionRecipeBuilder(Element element, int protonCount, int neutronCount, boolean x512) {
        this.protonCount = protonCount;
        this.neutronCount = neutronCount;
        this.x512 = x512;
        this.element = element;
        if (x512) {
            this.output = new ItemStack(element.atom512());
        } else {
            this.output = new ItemStack(element.atom64());
        }
    }

    @Override
    public @NotNull Item getResult() {
        return output.getItem();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.element, this.protonCount, this.neutronCount, this.x512, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final int protonCount;
        private final int neutronCount;
        private final Element element;
        private final boolean x512;

        public Result(ResourceLocation pId, Element element, int protonCount, int neutronCount, boolean x512, Advancement.Builder pAdvancement) {
            super(FusionRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.protonCount = protonCount;
            this.neutronCount = neutronCount;
            this.element = element;
            this.x512 =x512;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            element.toJson(pJson);
            pJson.addProperty("x512", x512);
            pJson.addProperty("proton", protonCount);
            pJson.addProperty("neutron", neutronCount);
        }
    }
}

