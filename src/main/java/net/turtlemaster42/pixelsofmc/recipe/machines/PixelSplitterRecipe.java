package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import javax.annotation.Nullable;
import java.awt.*;

public class PixelSplitterRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final CountedIngredient output;
    private final int R;
    private final int G;
    private final int B;
    private final CountedIngredient recipeItem;

    public PixelSplitterRecipe(ResourceLocation id, CountedIngredient output, int R, int G, int B,
                               CountedIngredient recipeItem) {
        this.id = id;
        this.output = output;
        this.R = R;
        this.G = G;
        this.B = B;
        this.recipeItem = recipeItem;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItem.test(pContainer.getItem(0));
    }



    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output.getItems()[0];
    }

    @Override
    public ItemStack getResultItem() {
        return output.getItems()[0].copy();
    }

    public Color getColor() {
        return new Color(R,G,B);
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
            CountedIngredient output = CountedIngredient.fromJson(GsonHelper.getAsJsonObject(json,"output"));
            int r = GsonHelper.getAsInt(json, "R");
            int g = GsonHelper.getAsInt(json, "G");
            int b = GsonHelper.getAsInt(json, "B");

            CountedIngredient input = CountedIngredient.fromJson(GsonHelper.getAsJsonObject(json,"input"));

            return new PixelSplitterRecipe(id, output, r, g, b, input);
        }

        public PixelSplitterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            CountedIngredient input = buf.readList(CountedIngredient::fromNetwork).get(0);
            CountedIngredient output = buf.readList(CountedIngredient::fromNetwork).get(0);
            int r = buf.readInt();
            int g = buf.readInt();
            int b = buf.readInt();
            return new PixelSplitterRecipe(id, output, r, g, b, input);
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
