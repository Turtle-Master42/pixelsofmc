package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GrinderRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final Ingredient recipeItem;
    private final List<ChanceIngredient> outputs;

    public GrinderRecipe(ResourceLocation id, Ingredient recipeItem,
                          List<ChanceIngredient> outputs) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItem.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return getResultItem();
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getResultItems(int index) {
        return outputs.get(index).getItems()[0];
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getInput() {
        return recipeItem;
    }

    public List<ChanceIngredient> getOutputs() {
        return outputs;
    }

    public int getOutputsCount(int index) {
        return outputs.get(index).count();
    }

    public float OutputChance(int index) {
        return outputs.get(index).chance();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<GrinderRecipe> {
        private Type() { }
        public static final GrinderRecipe.Type INSTANCE = new GrinderRecipe.Type();
        public static final String ID = "grinding";
    }

    public static class Serializer implements RecipeSerializer<GrinderRecipe> {
        public static final GrinderRecipe.Serializer INSTANCE = new GrinderRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"grinding");

        public GrinderRecipe fromJson(ResourceLocation id, JsonObject json) {
            //outputs
            JsonArray jsonOutputs = json.getAsJsonArray("outputs");
            List<ChanceIngredient> outputs = new ArrayList<>(jsonOutputs.size());
            for (int i = 0; i < jsonOutputs.size(); i++) {
                outputs.add(i, ChanceIngredient.fromJson(jsonOutputs.get(i).getAsJsonObject()));
            }
            //input
            Ingredient input = Ingredient.fromJson(json.get("input"));

            return new GrinderRecipe(id, input, outputs);
        }

        public GrinderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            try {
                Ingredient input = Ingredient.fromNetwork(buf);
                List<ChanceIngredient> outputs = buf.readList(ChanceIngredient::fromNetwork);

                return new GrinderRecipe(id, input, outputs);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading alloy smelting recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(FriendlyByteBuf buf, GrinderRecipe recipe) {
            try {
                recipe.recipeItem.toNetwork(buf);
                buf.writeCollection(recipe.outputs, (buffer, ing) -> ing.toNetwork(buffer));

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading alloy smelting recipe from packet.", ex);
                throw ex;
            }
        }

        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        public ResourceLocation getRegistryName() {
            return ID;
        }

        public Class<RecipeSerializer<?>> getRegistryType() {
            return GrinderRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}

