package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

public class HotIsostaticPressRecipe extends BaseItemRecipe {
    private final Ingredient output;
    private final CountedIngredient input;
    private final CountedIngredient mold;
    private final int heat;
    private final int maxHeat;
    public HotIsostaticPressRecipe(ResourceLocation id, Ingredient output, CountedIngredient input, CountedIngredient mold, int heat, int maxHeat) {
        super(id);
        this.output = output;
        this.input = input;
        this.mold = mold;
        this.heat = heat;
        this.maxHeat = maxHeat;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return input.test(container.getItem(2))&& mold.test(container.getItem(0));
    }


    public int getOutputCount() {
        return output.getItems()[0].getCount();
    }
    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return output.getItems()[0];
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.getItems()[0];
    }
    public int getHeat() {return heat;}
    public int getMaxHeat() {return maxHeat;}

    public CountedIngredient getInput() {
        return input;
    }

    public ItemStack getBaseOutput() {return output.getItems()[0];}
    public Ingredient getOutput() {return output;}
    public CountedIngredient getMold() {
        return mold;
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
        public static final Type INSTANCE = new Type();
        public static final String ID = "pressing";
    }

    public static class Serializer implements POMRecipeSerializer<HotIsostaticPressRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull HotIsostaticPressRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            CountedIngredient out = JsonRecipeUtils.CIFromJson(json, "output");
            Ingredient output = out.ingredient();
            //input
            CountedIngredient input = JsonRecipeUtils.CIFromJson(json, "input");
            CountedIngredient mold = JsonRecipeUtils.CIFromJson(json, "mold");
            //heat
            int heat = JsonRecipeUtils.intFromJson(json, "heat");
            int maxHeat = JsonRecipeUtils.intFromJson(json, "max_heat");

            return new HotIsostaticPressRecipe(id, output, input, mold, heat, maxHeat);
        }

        public HotIsostaticPressRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            CountedIngredient input = CountedIngredient.fromNetwork(buf);
            CountedIngredient mold = CountedIngredient.fromNetwork(buf);
            Ingredient output = Ingredient.fromNetwork(buf);
            int heat = buf.readInt();
            int maxHeat = buf.readInt();

            return new HotIsostaticPressRecipe(id, output, input, mold, heat, maxHeat);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull HotIsostaticPressRecipe recipe) {
            recipe.input.toNetwork(buf);
            recipe.mold.toNetwork(buf);
            recipe.output.toNetwork(buf);
            buf.writeInt(recipe.heat);
            buf.writeInt(recipe.maxHeat);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}
