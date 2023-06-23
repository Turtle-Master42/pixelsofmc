package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

public class PixelSplitterRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final CountedIngredient output;
    private final String structure;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private final CountedIngredient recipeItem;

    public PixelSplitterRecipe(ResourceLocation id, CountedIngredient output, int[] R, int[] G, int[] B,
                               CountedIngredient recipeItem, String structure) {
        this.id = id;
        this.output = output;
        this.R = R;
        this.G = G;
        this.B = B;
        this.recipeItem = recipeItem;
        this.structure = structure;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return recipeItem.test(pContainer.getItem(0));
    }



    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return output.getItems()[0];
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.getItems()[0];
    }

    public Color getColor(int index) {
        return new Color(R[index], G[index], B[index]);
    }

    public int getRGB(String rgb, int index) {
        if (Objects.equals(rgb, "R")) return R[index];
        else if (Objects.equals(rgb, "G")) return G[index];
        else if (Objects.equals(rgb, "B")) return B[index];
        return 0;
    }

    public String getStructure() {return structure;}

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
        return new ItemStack(POMblocks.PIXEL_SPLITTER.get());
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

        public @NotNull PixelSplitterRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            CountedIngredient output = CountedIngredient.fromJson(GsonHelper.getAsJsonObject(json,"output"));
            JsonArray Colors = json.getAsJsonArray("colors");
            int[] r = new int[5];
            int[] g = new int[5];
            int[] b = new int[5];
            for (int i=0; i < Colors.size(); i++) {
                r[i] = Colors.get(i).getAsJsonObject().get("R").getAsInt();
                g[i] = Colors.get(i).getAsJsonObject().get("G").getAsInt();
                b[i] = Colors.get(i).getAsJsonObject().get("B").getAsInt();
            }
            String structure = GsonHelper.getAsString(json, "structure");
            CountedIngredient input = CountedIngredient.fromJson(GsonHelper.getAsJsonObject(json,"input"));

            return new PixelSplitterRecipe(id, output, r, g, b, input, structure);
        }

        public PixelSplitterRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
            CountedIngredient input = buf.readList(CountedIngredient::fromNetwork).get(0);
            CountedIngredient output = buf.readList(CountedIngredient::fromNetwork).get(0);
            int[] r = new int[5];
            int[] g = new int[5];
            int[] b = new int[5];
            for (int i = 0; i < 3; i++) {
                r[i] = buf.readInt();
                g[i] = buf.readInt();
                b[i] = buf.readInt();
            }
            String structure = buf.readUtf();

            return new PixelSplitterRecipe(id, output, r, g, b, input, structure);
        }

        public void toNetwork(FriendlyByteBuf buf, PixelSplitterRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            for (int i=0; i < recipe.R.length; i++) {
                buf.writeInt(recipe.getRGB("R", i));
                buf.writeInt(recipe.getRGB("G", i));
                buf.writeInt(recipe.getRGB("B", i));
            }
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeUtf(recipe.structure);
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
