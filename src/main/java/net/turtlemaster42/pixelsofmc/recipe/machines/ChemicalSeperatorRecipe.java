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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChemicalSeperatorRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final CountedIngredient recipeItem;
    private final List<ChanceIngredient> outputs;
    private final FluidStack fluidInput;

    private final FluidStack fluidOutput;

    public ChemicalSeperatorRecipe(ResourceLocation id, CountedIngredient recipeItem, FluidStack fluidInput,
                                   List<ChanceIngredient> outputs, FluidStack fluidOutput) {
        this.id = id;
        this.recipeItem = recipeItem;
        this.outputs = outputs;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItem.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return getResultItem();
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public FluidStack getResultFluid() {return this.fluidOutput;}

    public ItemStack getResultItems(int index) {
        return outputs.get(index).getItems()[0];
    }

    @Override
    public ResourceLocation getId() {
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
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.CHEMICAL_SEPERATOR.get());
    }

    public static class Type implements RecipeType<ChemicalSeperatorRecipe> {
        private Type() { }
        public static final ChemicalSeperatorRecipe.Type INSTANCE = new ChemicalSeperatorRecipe.Type();
        public static final String ID = "chemical_separating";
    }

    public static class Serializer implements RecipeSerializer<ChemicalSeperatorRecipe> {
        public static final ChemicalSeperatorRecipe.Serializer INSTANCE = new ChemicalSeperatorRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"chemical_separating");

        public ChemicalSeperatorRecipe fromJson(ResourceLocation id, JsonObject json) {
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

            return new ChemicalSeperatorRecipe(id, input, fluidInput, outputs, fluidOutput);
        }

        public ChemicalSeperatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            try {
                CountedIngredient input = CountedIngredient.fromNetwork(buf);
                List<ChanceIngredient> outputs = buf.readList(ChanceIngredient::fromNetwork);
                FluidStack fluidInput = buf.readFluidStack();
                FluidStack fluidOutput = buf.readFluidStack();

                return new ChemicalSeperatorRecipe(id, input, fluidInput, outputs, fluidOutput);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_separating recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(FriendlyByteBuf buf, ChemicalSeperatorRecipe recipe) {
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
            return ChemicalSeperatorRecipe.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}

