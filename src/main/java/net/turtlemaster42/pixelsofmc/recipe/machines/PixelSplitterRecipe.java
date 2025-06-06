package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Objects;


public class PixelSplitterRecipe extends BaseItemRecipe {
    private final List<CountedIngredient> outputs;
    private final String structure;
    private final int[] R;
    private final int[] G;
    private final int[] B;
    private final CountedIngredient input;

    public PixelSplitterRecipe(ResourceLocation id, List<CountedIngredient> output, int[] R, int[] G, int[] B, CountedIngredient input, String structure) {
        super(id);
        this.outputs = output;
        this.R = R;
        this.G = G;
        this.B = B;
        this.input = input;
        this.structure = structure;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return input.test(pContainer.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public ItemStack getResultItems(int index) {
        return outputs.get(index).getItems()[0];
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

    public List<CountedIngredient> getOutputs() {
        return outputs;
    }
    public CountedIngredient getInput() {return input;}

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

    public static class Serializer implements POMRecipeSerializer<PixelSplitterRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull PixelSplitterRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //input
            CountedIngredient input = JsonRecipeUtils.CIFromJson(json, "input");
            //structure
            String structure = GsonHelper.getAsString(json, "structure");
            //outputs
            List<CountedIngredient> outputs = JsonRecipeUtils.CIListFromJson(json, "outputs");
            //colors
            JsonArray Colors = json.getAsJsonArray("colors");
            int[] r = new int[5];
            int[] g = new int[5];
            int[] b = new int[5];
            for (int i=0; i < Colors.size(); i++) {
                r[i] = Colors.get(i).getAsJsonObject().get("R").getAsInt();
                g[i] = Colors.get(i).getAsJsonObject().get("G").getAsInt();
                b[i] = Colors.get(i).getAsJsonObject().get("B").getAsInt();
            }

            return new PixelSplitterRecipe(id, outputs, r, g, b, input, structure);
        }

        public PixelSplitterRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            //input
            CountedIngredient input = CountedIngredient.fromNetwork(buf);
            //output
            List<CountedIngredient> outputs = buf.readList(CountedIngredient::fromNetwork);
            //colors
            int[] r = new int[5];
            int[] g = new int[5];
            int[] b = new int[5];
            for (int i = 0; i < 3; i++) {
                r[i] = buf.readInt();
                g[i] = buf.readInt();
                b[i] = buf.readInt();
            }
            //structure
            String structure = buf.readUtf();

            return new PixelSplitterRecipe(id, outputs, r, g, b, input, structure);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, PixelSplitterRecipe recipe) {
            //input
            recipe.input.toNetwork(buf);
            //output
            buf.writeCollection(recipe.outputs, (buffer, ing) -> ing.toNetwork(buffer));
            //colors
            for (int i=0; i < recipe.R.length; i++) {
                buf.writeInt(recipe.getRGB("R", i));
                buf.writeInt(recipe.getRGB("G", i));
                buf.writeInt(recipe.getRGB("B", i));
            }
            //structure
            buf.writeUtf(recipe.structure);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}
