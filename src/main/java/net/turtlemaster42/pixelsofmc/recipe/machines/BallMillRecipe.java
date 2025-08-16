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
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BallMillRecipe extends BaseItemRecipe {
    private final ChanceIngredient output;
    private final Ingredient ball;
    private final List<CountedIngredient> inputs;
    public BallMillRecipe(ResourceLocation id, ChanceIngredient output, Ingredient ball, List<CountedIngredient> inputs) {
        super(id);
        this.output = output;
        this.ball = ball;
        this.inputs = inputs;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        return matchMultiInput(container, inputs, 0, 2) && ball.test(container.getItem(3));
    }

    public Ingredient getBall() {
        return ball;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return output.asItemStack();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.asItemStack().copy();
    }

    public List<CountedIngredient> getInputs() {
        return inputs;
    }
    public ItemStack getInput(int input) {
        return inputs.get(input).getItems()[0];
    }


    public ChanceIngredient getOutput() {
        return output;
    }
    public ItemStack getBaseOutput() {
        return output.asItemStack();
    }

    public int getOutputCount() {return output.count();}

    public float getOutputChance() {
        return output.chance();
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
        return new ItemStack(POMblocks.BALL_MILL.get());
    }

    public static class Type implements RecipeType<BallMillRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "ball_milling";
    }

    public static class Serializer implements POMRecipeSerializer<BallMillRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull BallMillRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //output
            ChanceIngredient output = JsonRecipeUtils.CHIFromJson(json, "output");
            //inputs
            List<CountedIngredient> inputs = JsonRecipeUtils.CIListFromJson(json, "inputs");
            Ingredient ball = JsonRecipeUtils.IFromJson(json, "ball");
            return new BallMillRecipe(id, output, ball, inputs);
        }

        public BallMillRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);
            Ingredient ball = Ingredient.fromNetwork(buf);
            ChanceIngredient output = ChanceIngredient.fromNetwork(buf);
            return new BallMillRecipe(id, output, ball, inputs);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull BallMillRecipe recipe) {
            buf.writeCollection(recipe.inputs, (buffer, ing) -> ing.toNetwork(buffer));
            recipe.ball.toNetwork(buf);
            recipe.output.toNetwork(buf);
        }
        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}
