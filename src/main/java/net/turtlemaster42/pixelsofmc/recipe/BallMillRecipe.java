package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import javax.annotation.Nullable;

public class BallMillRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int count;
    private final float chance;

    public BallMillRecipe(ResourceLocation id, ItemStack output,
                               NonNullList<Ingredient> recipeItems, int count, float chance) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.count = count;
        this.chance = chance;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(1)) &&
                recipeItems.get(2).test(pContainer.getItem(2))) {
            return true;
        }

        return false;
    }

    public int getOutputCount() {
        return count;
    }

    public float getOutputChance() {
        return chance;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
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
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            int count = GsonHelper.getAsInt(json, "count");
            float chance = GsonHelper.getAsFloat(json, "chance");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new BallMillRecipe(id, output, inputs, count, chance);
        }

        public BallMillRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            int count = buf.readInt();
            float chance = buf.readFloat();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new BallMillRecipe(id, output, inputs, count, chance);
        }

        public void toNetwork(FriendlyByteBuf buf, BallMillRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            buf.writeInt(recipe.getOutputCount());
            buf.writeFloat(recipe.getOutputChance());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
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
