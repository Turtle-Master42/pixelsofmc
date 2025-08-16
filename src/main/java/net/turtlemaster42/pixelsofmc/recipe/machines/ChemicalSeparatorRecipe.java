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

public class ChemicalSeparatorRecipe extends BaseItemRecipe {
    private final CountedIngredient input;
    private final List<ChanceIngredient> outputs;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;

    public ChemicalSeparatorRecipe(ResourceLocation id, CountedIngredient input, FluidStack fluidInput, List<ChanceIngredient> outputs, FluidStack fluidOutput) {
        super(id);
        this.input = input;
        this.outputs = outputs;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
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

    public FluidStack getResultFluid() {return this.fluidOutput;}

    public ItemStack getResultItems(int index) {
        return outputs.get(index).getItems()[0];
    }

    public CountedIngredient getInput() {
        return input;
    }
    public int getInputCount() {
        return input.count();
    }

    public List<ChanceIngredient> getOutputs() {
        return outputs;
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
        return new ItemStack(POMblocks.CHEMICAL_SEPARATOR.get());
    }

    public static class Type implements RecipeType<ChemicalSeparatorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "chemical_separating";
    }

    public static class Serializer implements POMRecipeSerializer<ChemicalSeparatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull ChemicalSeparatorRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //outputs
            FluidStack fluidOutput = JsonRecipeUtils.FFromJson(json, "fluid_output");
            List<ChanceIngredient> outputs = JsonRecipeUtils.CHIListFromJson(json, "outputs");
            //input
            CountedIngredient input = JsonRecipeUtils.CIFromJson(json, "input");
            FluidStack fluidInput = JsonRecipeUtils.FFromJson(json, "fluid_input");
            return new ChemicalSeparatorRecipe(id, input, fluidInput, outputs, fluidOutput);
        }

        public ChemicalSeparatorRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            CountedIngredient input = CountedIngredient.fromNetwork(buf);
            List<ChanceIngredient> outputs = buf.readList(ChanceIngredient::fromNetwork);
            FluidStack fluidInput = buf.readFluidStack();
            FluidStack fluidOutput = buf.readFluidStack();

            return new ChemicalSeparatorRecipe(id, input, fluidInput, outputs, fluidOutput);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalSeparatorRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeCollection(recipe.outputs, (buffer, ing) -> ing.toNetwork(buffer));
            buf.writeFluidStack(recipe.fluidInput);
            buf.writeFluidStack(recipe.fluidOutput);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

