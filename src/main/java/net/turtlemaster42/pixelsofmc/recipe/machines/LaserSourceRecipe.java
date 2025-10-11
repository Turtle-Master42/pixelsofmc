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
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LaserSourceRecipe extends BaseItemRecipe {
    private final int inputColor;
    private final CountedIngredient source;
    private final int outputColor;
    private final int outputType;

    public LaserSourceRecipe(ResourceLocation id, int intputColor, CountedIngredient source, int outputColor, int outputType) {
        super(id);
        this.inputColor = intputColor;
        this.source = source;
        this.outputColor = outputColor;
        this.outputType = outputType;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, @NotNull Level pLevel) {
        return source.test(pContainer.getItem(3));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public CountedIngredient getSource() {return source;}

    public int getInputColor() {return inputColor;}

    public int getOutputColor() {return outputColor;}

    public int getOutputType() {return outputType;}

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {return Type.INSTANCE;}

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.PIXEL_BOMBARDER.get());
    }

    public static class Type implements RecipeType<LaserSourceRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "laser_source";
    }

    public static class Serializer implements POMRecipeSerializer<LaserSourceRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull LaserSourceRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            int inputColor = JsonRecipeUtils.intFromJson(json, "input_color");
            CountedIngredient source = JsonRecipeUtils.CIFromJson(json, "input");
            int outputColor = JsonRecipeUtils.intFromJson(json, "output_color");
            int outputType = JsonRecipeUtils.intFromJson(json, "output_type");
            return new LaserSourceRecipe(id, inputColor, source, outputColor, outputType);
        }

        public LaserSourceRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            int inputColor = buf.readInt();
            CountedIngredient source = CountedIngredient.fromNetwork(buf);
            int outputColor = buf.readInt();
            int outputType = buf.readInt();
            return new LaserSourceRecipe(id, inputColor, source, outputColor, outputType);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull LaserSourceRecipe recipe) {
            buf.writeInt(recipe.inputColor);
            recipe.source.toNetwork(buf);
            buf.writeInt(recipe.outputColor);
            buf.writeInt(recipe.outputType);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

