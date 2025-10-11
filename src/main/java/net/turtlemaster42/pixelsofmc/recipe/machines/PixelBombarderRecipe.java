package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PixelBombarderRecipe extends BaseItemRecipe {
    private final CountedIngredient input;
    private final int color;
    private final ChanceIngredient output;

    public PixelBombarderRecipe(ResourceLocation id, CountedIngredient input, ChanceIngredient output, int color) {
        super(id);
        this.input = input;
        this.output = output;
        this.color = color;
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
        return output.asItemStack();
    }


    public CountedIngredient getInput() {
        return input;
    }

    public ChanceIngredient getOutput() {
        return output;
    }

    public int getColor() {return color;}

    public float getOutputChance() {
        return output.chance();
    }


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {return Type.INSTANCE;}

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.PIXEL_BOMBARDER.get());
    }

    public static class Type implements RecipeType<PixelBombarderRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "bombarding";
    }

    public static class Serializer implements POMRecipeSerializer<PixelBombarderRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull PixelBombarderRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //outputs
            ChanceIngredient output = JsonRecipeUtils.CHIFromJson(json, "output");
            //input
            CountedIngredient input = JsonRecipeUtils.CIFromJson(json, "input");
            int color = JsonRecipeUtils.intFromJson(json, "color");
            return new PixelBombarderRecipe(id, input, output, color);
        }

        public PixelBombarderRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            CountedIngredient input = CountedIngredient.fromNetwork(buf);
            int color = buf.readInt();
            ChanceIngredient output = ChanceIngredient.fromNetwork(buf);
            return new PixelBombarderRecipe(id, input, output, color);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PixelBombarderRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeInt(recipe.color);
            recipe.output.toNetwork(buf);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

