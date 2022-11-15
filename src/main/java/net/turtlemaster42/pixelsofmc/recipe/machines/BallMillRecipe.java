package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BallMillRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final List<CountedIngredient> recipeItems;
    private final float chance;

    public BallMillRecipe(ResourceLocation id, ItemStack output,
                          List<CountedIngredient> recipeItems, float chance) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.chance = chance;
    }

//enderio
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        boolean[] matched = new boolean[3];

        // Iterate over the slots
        for (int i = 0; i < 3; i++) {
            // Iterate over the inputs
            for (int j = 0; j < 3; j++) {
                // If this ingredient has been matched already, continue
                if (matched[j])
                    continue;

                if (j < recipeItems.size()) {
                    // If we expect an input, test we have a match for it.
                    if (recipeItems.get(j).test(container.getItem(i))) {
                        matched[j] = true;
                    }
                } else if (container.getItem(i) == ItemStack.EMPTY) {
                    // If we don't expect an input, make sure we have a blank for it.
                    matched[j] = true;
                }
            }
        }

        // If we matched all our ingredients, we win!
        for (int i = 0; i < 3; i++) {
            if (!matched[i])
                return false;
        }

        return true;
    }


    public int getOutputCount() {
        return output.getCount();
    }

    public float getDubbleChance() {
        return chance;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public List<CountedIngredient> getInputs() {
        return recipeItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BallMillRecipe> {
        private Type() { }
        public static final BallMillRecipe.Type INSTANCE = new BallMillRecipe.Type();
        public static final String ID = "ball_milling";
    }

    public static class Serializer implements RecipeSerializer<BallMillRecipe> {
        public static final BallMillRecipe.Serializer INSTANCE = new BallMillRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"ball_milling");

        public BallMillRecipe fromJson(ResourceLocation id, JsonObject json) {
            //output
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), false);
            //chance
            float chance = GsonHelper.getAsFloat(json, "dubble_chance");

            //inputs
            JsonArray jsonInputs = json.getAsJsonArray("inputs");
            List<CountedIngredient> inputs = new ArrayList<>(jsonInputs.size());
            for (int i = 0; i < jsonInputs.size(); i++) {
                inputs.add(i, CountedIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
            }
            return new BallMillRecipe(id, output, inputs, chance);
        }

        public BallMillRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            try {
                List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);

                float chance = buf.readFloat();
                ItemStack output = buf.readItem();

                return new BallMillRecipe(id, output, inputs, chance);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading alloy smelting recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(FriendlyByteBuf buf, BallMillRecipe recipe) {
            try {
                buf.writeCollection(recipe.recipeItems, (buffer, ing) -> ing.toNetwork(buffer));
                buf.writeItem(recipe.output);


                buf.writeInt(recipe.getOutputCount());
                buf.writeFloat(recipe.getDubbleChance());

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
            return BallMillRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}
