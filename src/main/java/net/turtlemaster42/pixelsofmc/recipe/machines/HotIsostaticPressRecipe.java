package net.turtlemaster42.pixelsofmc.recipe.machines;

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

public class HotIsostaticPressRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final Ingredient output;
    private final CountedIngredient recipeItem;
    private final CountedIngredient mold;
    private final int heat;
    private final int maxHeat;
    public HotIsostaticPressRecipe(ResourceLocation id, Ingredient output, CountedIngredient recipeItem, CountedIngredient mold, int heat, int maxHeat) {
        this.id = id;
        this.output = output;
        this.recipeItem = recipeItem;
        this.mold = mold;
        this.heat = heat;
        this.maxHeat = maxHeat;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return recipeItem.test(container.getItem(2))&& mold.test(container.getItem(0));
    }


    public int getOutputCount() {
        return output.getItems()[0].getCount();
    }
    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return output.getItems()[0];
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.getItems()[0];
    }
    public int getHeat() {return heat;}
    public int getMaxHeat() {return maxHeat;}

    public ItemStack getInput() {
        return recipeItem.getItems()[0];
    }
    public ItemStack getMold() {
        return mold.getItems()[0];
    }

    public Ingredient getInputAsI() {
        return Ingredient.of(recipeItem.asItem());
    }
    public Ingredient getMoldAsI() {
        return mold.ingredient();
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
        return new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get());
    }

    public static class Type implements RecipeType<HotIsostaticPressRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "pressing";
    }

    public static class Serializer implements RecipeSerializer<HotIsostaticPressRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"pressing");

        public @NotNull HotIsostaticPressRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            CountedIngredient out = CountedIngredient.fromJson(json.getAsJsonObject("output"));
            Ingredient output = out.ingredient();
            //input
            CountedIngredient input = CountedIngredient.fromJson(json.getAsJsonObject("input"));
            CountedIngredient mold = CountedIngredient.fromJson(json.getAsJsonObject("mold"));
            //heat
            int heat = GsonHelper.getAsInt(json, "heat");
            int maxHeat = GsonHelper.getAsInt(json, "max_heat");

            return new HotIsostaticPressRecipe(id, output, input, mold, heat, maxHeat);
        }

        public HotIsostaticPressRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                CountedIngredient input = buf.readList(CountedIngredient::fromNetwork).get(0);
                CountedIngredient mold = buf.readList(CountedIngredient::fromNetwork).get(0);
                Ingredient output = buf.readList(Ingredient::fromNetwork).get(0);
                int heat = buf.readInt();
                int maxHeat = buf.readInt();

                return new HotIsostaticPressRecipe(id, output, input, mold, heat, maxHeat);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading pressing recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull HotIsostaticPressRecipe recipe) {
            try {
                buf.writeInt(recipe.getIngredients().size());
                for (Ingredient ing : recipe.getIngredients()) {
                    ing.toNetwork(buf);
                }
                buf.writeItem(recipe.output.getItems()[0]);
                buf.writeInt(recipe.getOutputCount());
                buf.writeInt(recipe.heat);
                buf.writeInt(recipe.maxHeat);

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading pressing recipe from packet.", ex);
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
