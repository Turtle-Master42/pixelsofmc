package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PixelSplitterRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public PixelSplitterRecipe(ResourceLocation id, ItemStack output,
                                   NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @java.lang.Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItems.get(0).test(pContainer.getItem(0));
    }

    @java.lang.Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @java.lang.Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @java.lang.Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @java.lang.Override
    public ResourceLocation getId() {
        return id;
    }

    @java.lang.Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @java.lang.Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PixelSplitterRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "pixel_splitting";
    }

    public static class Serializer implements RecipeSerializer<PixelSplitterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"pixel_splitting");

        public PixelSplitterRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new PixelSplitterRecipe(id, output, inputs);
        }

        public PixelSplitterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new PixelSplitterRecipe(id, output, inputs);
        }

        public void toNetwork(FriendlyByteBuf buf, PixelSplitterRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
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
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
