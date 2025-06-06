package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class GrinderRecipe extends BaseItemRecipe {
    private final Ingredient input;
    private final List<ChanceIngredient> outputs;

    public GrinderRecipe(ResourceLocation id, Ingredient input, List<ChanceIngredient> outputs) {
        super(id);
        this.input = input;
        this.outputs = outputs;
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
        if (!outputs.isEmpty() && outputs.get(index).getItems().length > 0)
            return outputs.get(index).getItems()[0];
        return ItemStack.EMPTY;
    }


    public Ingredient getInput() {
        return input;
    }

    public List<ChanceIngredient> getOutputs() {
        return outputs;
    }

    public float getOutputChance(int index) {
        return outputs.get(index).chance();
    }


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {return Type.INSTANCE;}

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.GRINDER.get());
    }

    public static class Type implements RecipeType<GrinderRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "grinding";
    }

    public static class Serializer implements POMRecipeSerializer<GrinderRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull GrinderRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //outputs
            List<ChanceIngredient> outputs = JsonRecipeUtils.CHIListFromJson(json, "outputs");
            //input
            Ingredient input = JsonRecipeUtils.IFromJson(json, "intput");
            return new GrinderRecipe(id, input, outputs);
        }

        public GrinderRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            List<ChanceIngredient> outputs = buf.readList(ChanceIngredient::fromNetwork);
            return new GrinderRecipe(id, input, outputs);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull GrinderRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeCollection(recipe.outputs, (buffer, ing) -> ing.toNetwork(buffer));
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

