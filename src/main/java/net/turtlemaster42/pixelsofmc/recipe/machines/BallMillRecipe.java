package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BallMillRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ball;
    private final List<CountedIngredient> recipeItems;
    public BallMillRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> ball,
                          List<CountedIngredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.ball = ball;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        List<Item> slotItems = new ArrayList<>();
        List<Integer> slotCounts = new ArrayList<>();

        // Iterate over the slots and makes a list of the total ingredients
        for (int slot = 0; slot < 3; slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty())
                continue;

            if (!slotItems.contains(stack.getItem())) {
                slotItems.add(stack.getItem());
                slotCounts.add(stack.getCount());
            } else {
                int index = slotItems.indexOf(stack.getItem());
                slotCounts.set(index, slotCounts.get(index) + stack.getCount());
            }
        }

        // if slotItems and recipeItems are not equal there are ingredients missing or to many
        if (slotItems.size() != recipeItems.size()) {
            return false;
        }

        // Iterates over the needed items
        for (CountedIngredient recipeItem : recipeItems) {
            Item item = recipeItem.asItem();
            // Checks if the item is present in the slots
            if (!slotItems.contains(item)) {
                return false;
            }
            int index = slotItems.indexOf(item);
            // Checks if there is enough items in the slots
            if (recipeItem.count() > slotCounts.get(index)) {
                return false;
            }
            // We win, the item is present and there is enough
        }
        return ball.get(0).test(container.getItem(3)) && ball.size() <= 1;





        //credits EnderIO
//        boolean[] matched = new boolean[3];
//
//        // Iterate over the slots
//        for (int slot = 0; slot < 3; slot++) {
//            // Iterate over the inputs
//            for (int inp = 0; inp < 3; inp++) {
//                // If this ingredient has been matched already, continue
//                if (matched[inp])
//                    continue;
//
//                if (inp < recipeItems.size()) {
//                    // If we expect an input, test we have a match for it.
//                    if (recipeItems.get(inp).test(container.getItem(slot))) {
//                        matched[inp] = true;
//                    }
//                } else if (container.getItem(slot).isEmpty()) {
//                    // If we don't expect an input, make sure we have a blank for it.
//                    matched[inp] = true;
//                }
//            }
//        }
//
//        // If we matched all our ingredients, we win!
//        for (int i = 0; i < 3; i++) {
//            if (!matched[i])
//                return false;
//        }
//
//        return ball.get(0).test(container.getItem(3)) && ball.size() <= 1;
    }


    public int getOutputCount() {
        return output.getCount();
    }

    public NonNullList<Ingredient> getBall() {
        return ball;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return output;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.copy();
    }

    public List<CountedIngredient> getInputs() {
        return recipeItems;
    }
    public ItemStack getInput(int input) {
        return recipeItems.get(input).getItems()[0];
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
            CountedIngredient out = CountedIngredient.fromJson(json.getAsJsonObject("output"));
            ItemStack output = new ItemStack(out.asItem(), out.count());

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

                ItemStack output = buf.readItem();

                return new BallMillRecipe(id, output, ball, inputs);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading alloy smelting recipe from packet.", ex);
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
                buf.writeItem(recipe.output);

                buf.writeInt(recipe.getOutputCount());

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
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}
