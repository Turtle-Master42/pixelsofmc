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
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ChemicalCombinerRecipe extends BaseItemRecipe {
    private final List<CountedIngredient> inputs;
    private final ChanceIngredient output;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;

    public ChemicalCombinerRecipe(ResourceLocation id, List<CountedIngredient> inputs, FluidStack fluidInput, ChanceIngredient output, FluidStack fluidOutput) {
        super(id);
        this.inputs = inputs;
        this.output = output;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;
        return matchMultiInput(container, inputs, 0, 2);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.getItems()[0];
    }

    public FluidStack getResultFluid() {return this.fluidOutput;}

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

    public FluidStack getFluidInput() {return this.fluidInput;}


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.CHEMICAL_COMBINER.get());
    }

    public static class Type implements RecipeType<ChemicalCombinerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "chemical_combining";
    }

    public static class Serializer implements POMRecipeSerializer<ChemicalCombinerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull ChemicalCombinerRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            FluidStack fluidOutput = JsonRecipeUtils.FFromJson(json, "fluid_output");
            ChanceIngredient output = JsonRecipeUtils.CHIFromJson(json, "output");
            //inputs
            FluidStack fluidInput = JsonRecipeUtils.FFromJson(json, "fluid_input");
            List<CountedIngredient> inputs = JsonRecipeUtils.CIListFromJson(json, "inputs");
            return new ChemicalCombinerRecipe(id, inputs, fluidInput, output, fluidOutput);
        }

        public ChemicalCombinerRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);
            ChanceIngredient output = ChanceIngredient.fromNetwork(buf);
            FluidStack fluidInput = buf.readFluidStack();
            FluidStack fluidOutput = buf.readFluidStack();

            return new ChemicalCombinerRecipe(id, inputs, fluidInput, output, fluidOutput);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalCombinerRecipe recipe) {
            buf.writeCollection(recipe.inputs, (buffer, ing) -> ing.toNetwork(buffer));
            recipe.output.toNetwork(buf);
            buf.writeFluidStack(recipe.fluidInput);
            buf.writeFluidStack(recipe.fluidOutput);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

