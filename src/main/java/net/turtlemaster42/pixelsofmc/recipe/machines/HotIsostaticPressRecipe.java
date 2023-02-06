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
import net.minecraftforge.common.crafting.CraftingHelper;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import javax.annotation.Nullable;

public class HotIsostaticPressRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final CountedIngredient recipeItem;
    private final CountedIngredient mold;
    private final int heat;
    public HotIsostaticPressRecipe(ResourceLocation id, ItemStack output, CountedIngredient recipeItem, CountedIngredient mold, int heat) {
        this.id = id;
        this.output = output;
        this.recipeItem = recipeItem;
        this.mold = mold;
        this.heat = heat;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return recipeItem.test(container.getItem(2))&& mold.test(container.getItem(0));
    }


    public int getOutputCount() {
        return output.getCount();
    }
    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }
    public int getHeat() {return heat;}

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

    public ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get());
    }

    public static class Type implements RecipeType<HotIsostaticPressRecipe> {
        private Type() { }
        public static final HotIsostaticPressRecipe.Type INSTANCE = new HotIsostaticPressRecipe.Type();
        public static final String ID = "pressing";
    }

    public static class Serializer implements RecipeSerializer<HotIsostaticPressRecipe> {
        public static final HotIsostaticPressRecipe.Serializer INSTANCE = new HotIsostaticPressRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"pressing");

        public HotIsostaticPressRecipe fromJson(ResourceLocation id, JsonObject json) {
            //output
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), false);
            //input
            CountedIngredient input = CountedIngredient.fromJson(json.getAsJsonObject("input"));
            CountedIngredient mold = CountedIngredient.fromJson(json.getAsJsonObject("mold"));
            //heat
            int heat = GsonHelper.getAsInt(json, "heat");

            return new HotIsostaticPressRecipe(id, output, input, mold, heat);
        }

        public HotIsostaticPressRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            try {
                CountedIngredient input = buf.readList(CountedIngredient::fromNetwork).get(0);
                CountedIngredient mold = buf.readList(CountedIngredient::fromNetwork).get(0);
                ItemStack output = buf.readItem();
                int heat = buf.readInt();

                return new HotIsostaticPressRecipe(id, output, input, mold, heat);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading pressing recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(FriendlyByteBuf buf, HotIsostaticPressRecipe recipe) {
            try {
                buf.writeInt(recipe.getIngredients().size());
                for (Ingredient ing : recipe.getIngredients()) {
                    ing.toNetwork(buf);
                }
                buf.writeItem(recipe.output);

                buf.writeInt(recipe.getOutputCount());

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
            return HotIsostaticPressRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
