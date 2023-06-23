package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChemicalSeparatorRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final CountedIngredient recipeItem;
    private final List<ChanceIngredient> outputs;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;

    public ChemicalSeparatorRecipe(ResourceLocation id, CountedIngredient recipeItem, FluidStack fluidInput,
                                   List<ChanceIngredient> outputs, FluidStack fluidOutput) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.outputs = outputs;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return recipeItem.test(pContainer.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return getResultItem();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public FluidStack getResultFluid() {return this.fluidOutput;}

    public ItemStack getResultItems(int index) {
        return outputs.get(index).getItems()[0];
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public Ingredient getInput() {
        return recipeItem.ingredient();
    }
    public int getInputCount() {
        return recipeItem.count();
    }

    public List<ChanceIngredient> getOutputs() {
        return outputs;
    }

    public int getOutputsCount(int index) {
        return outputs.get(index).count();
    }

    public float OutputChance(int index) {
        return outputs.get(index).chance();
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
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "chemical_separating";
    }

    public static class Serializer implements RecipeSerializer<ChemicalSeparatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"chemical_separating");

        public @NotNull ChemicalSeparatorRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //outputs
            JsonArray jsonOutputs = json.getAsJsonArray("outputs");
            FluidStack fluidOutput = FluidJSONUtil.readFluid(json.get("fluid_output").getAsJsonObject());
            List<ChanceIngredient> outputs = new ArrayList<>(jsonOutputs.size());
            for (int i = 0; i < jsonOutputs.size(); i++) {
                outputs.add(i, ChanceIngredient.fromJson(jsonOutputs.get(i).getAsJsonObject()));
            }
            //input
            JsonObject jsonObject = json.getAsJsonObject("input");
            CountedIngredient input = CountedIngredient.fromJson(jsonObject.getAsJsonObject());
            FluidStack fluidInput = FluidJSONUtil.readFluid(json.get("fluid_input").getAsJsonObject());

            return new ChemicalSeparatorRecipe(id, input, fluidInput, outputs, fluidOutput);
        }

        public ChemicalSeparatorRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                CountedIngredient input = CountedIngredient.fromNetwork(buf);
                List<ChanceIngredient> outputs = buf.readList(ChanceIngredient::fromNetwork);
                FluidStack fluidInput = buf.readFluidStack();
                FluidStack fluidOutput = buf.readFluidStack();

                return new ChemicalSeparatorRecipe(id, input, fluidInput, outputs, fluidOutput);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_separating recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalSeparatorRecipe recipe) {
            try {
                recipe.recipeItem.toNetwork(buf);
                buf.writeCollection(recipe.outputs, (buffer, ing) -> ing.toNetwork(buffer));
                buf.writeFluidStack(recipe.fluidInput);
                buf.writeFluidStack(recipe.fluidOutput);

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_separating recipe from packet.", ex);
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

