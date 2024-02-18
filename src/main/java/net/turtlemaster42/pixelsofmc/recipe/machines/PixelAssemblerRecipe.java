package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PixelAssemblerRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final CountedIngredient output;
    private final String structure;
    private final List<CountedIngredient> recipeItems;
    private final int[] R;
    private final int[] G;
    private final int[] B;

    public PixelAssemblerRecipe(ResourceLocation id, CountedIngredient output, int[] R, int[] G, int[] B,
                                List<CountedIngredient> recipeItems, String structure) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
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
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return getResultItem();
    }

    @Override
    public @NotNull ItemStack getResultItem() {return output.asItemStack().copy();}


    public String getStructure() {return structure;}

    public List<CountedIngredient> getInputs() {return recipeItems;}

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
        return new ItemStack(POMblocks.PIXEL_ASSEMBLER.get());
    }

    public static class Type implements RecipeType<PixelAssemblerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "pixel_assembling";
    }

    public static class Serializer implements RecipeSerializer<PixelAssemblerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"pixel_assembling");

        public @NotNull PixelAssemblerRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //outputs
            JsonArray jsonInputs = json.getAsJsonArray("inputs");
            List<CountedIngredient> inputs = new ArrayList<>(jsonInputs.size());
            for (int i = 0; i < jsonInputs.size(); i++) {
                inputs.add(i, CountedIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
            }
            //structure
            String structure = GsonHelper.getAsString(json, "structure");
            //input
            CountedIngredient output = CountedIngredient.fromJson(GsonHelper.getAsJsonObject(json,"output"));
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

        public PixelAssemblerRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
            try {
                //input
                CountedIngredient output = buf.readList(CountedIngredient::fromNetwork).get(0);
                //output
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
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading alloy smelting recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, PixelAssemblerRecipe recipe) {
            try {
                //input
                buf.writeCollection(recipe.recipeItems, (buffer, ing) -> ing.toNetwork(buffer));
                buf.writeInt(recipe.getIngredients().size());
                for (Ingredient ing : recipe.getIngredients()) {
                    ing.toNetwork(buf);
                }
                //output
                recipe.output.toNetwork(buf);
                //colors
                for (int i=0; i < recipe.R.length; i++) {
                    buf.writeInt(recipe.getRGB("R", i));
                    buf.writeInt(recipe.getRGB("G", i));
                    buf.writeInt(recipe.getRGB("B", i));
                }
                //structure
                buf.writeUtf(recipe.structure);
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
