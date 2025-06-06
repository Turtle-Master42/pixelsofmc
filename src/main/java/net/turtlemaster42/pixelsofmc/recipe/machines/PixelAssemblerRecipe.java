package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PixelAssemblerRecipe extends BaseItemRecipe {
    private final CountedIngredient output;
    private final String structure;
    private final List<CountedIngredient> inputs;
    private final int[] R;
    private final int[] G;
    private final int[] B;

    public PixelAssemblerRecipe(ResourceLocation id, CountedIngredient output, int[] R, int[] G, int[] B, List<CountedIngredient> inputs, String structure) {
        super(id);
        this.output = output;
        this.inputs = inputs;
        this.structure = structure;
        this.R = R;
        this.G = G;
        this.B = B;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;
        List<Item> slotItems = new ArrayList<>();
        List<Integer> slotCounts = new ArrayList<>();
        List<String> slotStructures = new ArrayList<>();

        // Iterate over the slots and makes a list of the total ingredients
        for (int slot = 0; slot < 3; slot++) {
            ItemStack stack = pContainer.getItem(slot);
            if (stack.isEmpty())
                continue;

            if (!slotItems.contains(stack.getItem())) {
                slotItems.add(stack.getItem());
                slotCounts.add(stack.getCount());
                if (stack.getItem() instanceof PixelItem pixelItem) {
                    slotStructures.add(pixelItem.getStructure(stack));
                } else {
                    slotStructures.add("");
                }
            } else {
                int index = slotItems.indexOf(stack.getItem());
                if (stack.getItem() instanceof PixelItem pixelItem) {
                    if (Objects.equals(pixelItem.getStructure(stack), slotStructures.get(index))) {
                        slotCounts.set(index, slotCounts.get(index) + stack.getCount());
                    } else {
                        slotItems.add(stack.getItem());
                        slotCounts.add(stack.getCount());
                        slotStructures.add(pixelItem.getStructure(stack));
                    }
                } else {
                    slotCounts.set(index, slotCounts.get(index) + stack.getCount());
                }
            }
        }

        // if slotItems and recipeItems are not equal there are ingredients missing or to many
        if (slotItems.size() != inputs.size()) {
            return false;
        }

        // Iterates over the needed items
        for (CountedIngredient recipeItem : inputs) {
            Item item = recipeItem.asItem();
            // Checks if the item is present in the slots
            if (!slotItems.contains(item)) {
                return false;
            }
            int index = slotItems.indexOf(item);
            // Checks if it is a pixel item
            if (item instanceof PixelItem pixelItem) {
                // Checks if the pixel structure is correct
                if (!Objects.equals(structure, slotStructures.get(index))) {
                    return false;
                }
            }
            // Checks if there is enough items in the slots
            if (recipeItem.count() > slotCounts.get(index)) {
                return false;
            }
            // We win, the item is present and there is enough
        }
        return true;
    }



    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {return output.asItemStack().copy();}


    public String getStructure() {return structure;}

    public List<CountedIngredient> getInputs() {return inputs;}
    public ItemStack getBaseOutput() {
        return output.asItemStack();
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

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.PIXEL_ASSEMBLER.get());
    }

    public static class Type implements RecipeType<PixelAssemblerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "pixel_assembling";
    }

    public static class Serializer implements POMRecipeSerializer<PixelAssemblerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull PixelAssemblerRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //inputs
            List<CountedIngredient> inputs = JsonRecipeUtils.CIListFromJson(json, "inputs");
            //structure
            String structure = JsonRecipeUtils.stringFromJson(json, "structure");
            //output
            CountedIngredient output = JsonRecipeUtils.CIFromJson(json, "output");
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
            return new PixelAssemblerRecipe(id, output, r, g, b, inputs, structure);
        }

        public PixelAssemblerRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            //output
            CountedIngredient output = CountedIngredient.fromNetwork(buf);
            //input
            List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);
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

            return new PixelAssemblerRecipe(id, output, r, g, b, inputs, structure);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PixelAssemblerRecipe recipe) {
            //output
            recipe.output.toNetwork(buf);
            //input
            buf.writeCollection(recipe.inputs, (buffer, ing) -> ing.toNetwork(buffer));
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
