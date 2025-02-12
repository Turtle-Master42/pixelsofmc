package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BallMillRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final ChanceIngredient output;
    private final NonNullList<Ingredient> ball;
    private final List<CountedIngredient> recipeItems;
    public BallMillRecipe(ResourceLocation id, ChanceIngredient output, NonNullList<Ingredient> ball,
                          List<CountedIngredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.ball = ball;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        return matchMultiInput(container, recipeItems, 0, 2) && ball.get(0).test(container.getItem(3)) && ball.size() <= 1;
    }

    public NonNullList<Ingredient> getBall() {
        return ball;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, RegistryAccess registryAccess) {
        return output.asItemStack();
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.asItemStack().copy();
    }

    public List<CountedIngredient> getInputs() {
        return recipeItems;
    }
    public ItemStack getInput(int input) {
        return recipeItems.get(input).getItems()[0];
    }


    public ChanceIngredient getOutput() {
        return output;
    }
    public ItemStack getBaseOutput() {
        return output.asItemStack();
    }

    public int getOutputCount() {return output.count();}

    public float getOutputChance() {
        return output.chance();
    }


    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.BALL_MILL.get());
    }

    public static class Type implements RecipeType<BallMillRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "ball_milling";
    }

    public static class Serializer implements RecipeSerializer<BallMillRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"ball_milling");

        public @NotNull BallMillRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            ChanceIngredient output = ChanceIngredient.fromJson(json.getAsJsonObject("output"));

            //inputs
            JsonArray jsonInputs = json.getAsJsonArray("inputs");
            List<CountedIngredient> inputs = new ArrayList<>(jsonInputs.size());
            for (int i = 0; i < jsonInputs.size(); i++) {
                inputs.add(i, CountedIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
            }

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ball");
            NonNullList<Ingredient> ball = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int j = 0; j < ball.size(); j++) {
                ball.set(j, Ingredient.fromJson(ingredients.get(j)));
            }


            return new BallMillRecipe(id, output, ball, inputs);
        }

        public BallMillRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);

                NonNullList<Ingredient> ball = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
                for (int i = 0; i < inputs.size(); i++) {
                    ball.set(i, Ingredient.fromNetwork(buf));
                }

                ChanceIngredient output = ChanceIngredient.fromNetwork(buf);

                return new BallMillRecipe(id, output, ball, inputs);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading ball mill recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull BallMillRecipe recipe) {
            try {
                buf.writeCollection(recipe.recipeItems, (buffer, ing) -> ing.toNetwork(buffer));
                buf.writeInt(recipe.getIngredients().size());
                for (Ingredient ing : recipe.getIngredients()) {
                    ing.toNetwork(buf);
                }
                recipe.output.toNetwork(buf);

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading ball mill recipe from packet.", ex);
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
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}
