package net.turtlemaster42.pixelsofmc.recipe.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.datagen.POMrecipeProvider;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.recipe.DecayRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DecayRecipeBuilder extends POMRecipeBuilder {
    private final FuelCellItem fuelCell;

    public DecayRecipeBuilder(FuelCellItem cellItem) {
        this.fuelCell = cellItem;
    }

    public static DecayRecipeBuilder build(FuelCellItem cellItem) {
        return new DecayRecipeBuilder(cellItem);
    }

    public void finish(Consumer<FinishedRecipe> consumer, POMrecipeProvider provider) {
        this.unlockedBy("", ANY_CRITERION).save(consumer, provider.toRL("decaying/" + fuelCell));
    }

    @Override
    public @NotNull Item getResult() {
        return fuelCell.getRemainder();
    }

    @Override
    protected FinishedRecipe save(@NotNull ResourceLocation id) {
        return new Result(id, this.fuelCell, this.advancement);
    }

    public static class Result extends POMRecipeResult {
        private final FuelCellItem fuelCell;

        public Result(ResourceLocation pId, FuelCellItem fuelCell, Advancement.Builder pAdvancement) {
            super(DecayRecipe.Serializer.INSTANCE, pId, pAdvancement);
            this.fuelCell = fuelCell;

        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            pJson.addProperty("decay_item", BuiltInRegistries.ITEM.getKey(fuelCell).toString());
        }
    }
}

