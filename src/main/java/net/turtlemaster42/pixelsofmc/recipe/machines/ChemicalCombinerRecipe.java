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

public class ChemicalCombinerRecipe extends BaseRecipe {
    private final ResourceLocation id;
    private final List<CountedIngredient> recipeItems;
    private final ChanceIngredient output;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;

    public ChemicalCombinerRecipe(ResourceLocation id, List<CountedIngredient> recipeItems, FluidStack fluidInput,
                                  ChanceIngredient output, FluidStack fluidOutput) {
        this.id = id;
        this.recipeItems = recipeItems;
        this.output = output;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
    }

    //credits EnderIO
    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide) return false;

        boolean[] matched = new boolean[3];

        // Iterate over the slots
        for (int slot = 0; slot < 3; slot++) {
            // Iterate over the inputs
            for (int inp = 0; inp < 3; inp++) {
                // If this ingredient has been matched already, continue
                if (matched[inp])
                    continue;

                if (inp < recipeItems.size()) {
                    // If we expect an input, test we have a match for it.
                    if (recipeItems.get(inp).test(container.getItem(slot))) {
                        matched[inp] = true;
                    }
                } else if (container.getItem(slot).isEmpty()) {
                    // If we don't expect an input, make sure we have a blank for it.
                    matched[inp] = true;
                }
            }
        }

        // If we matched all our ingredients, we win!
        for (int i = 0; i < 3; i++) {
            if (!matched[i])
                return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return getResultItem();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.getItems()[0];
    }

    public FluidStack getResultFluid() {return this.fluidOutput;}

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public List<CountedIngredient> getInputs() {
        return recipeItems;
    }

    public ChanceIngredient getOutput() {
        return output;
    }

    public int getOutputCount() {
        return output.count();
    }

    public float OutputChance(int index) {
        return output.chance();
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

    public static class Type implements RecipeType<ChemicalCombinerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "chemical_combining";
    }

    public static class Serializer implements RecipeSerializer<ChemicalCombinerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(PixelsOfMc.MOD_ID,"chemical_combining");

        public @NotNull ChemicalCombinerRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            FluidStack fluidOutput = FluidJSONUtil.readFluid(json.get("fluid_output").getAsJsonObject());

            ChanceIngredient output = ChanceIngredient.fromJson(json.getAsJsonObject("output"));

            //inputs
            FluidStack fluidInput = FluidJSONUtil.readFluid(json.get("fluid_input").getAsJsonObject());

            JsonArray jsonInputs = json.getAsJsonArray("inputs");
            List<CountedIngredient> inputs = new ArrayList<>(jsonInputs.size());
            for (int i = 0; i < jsonInputs.size(); i++) {
                inputs.add(i, CountedIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
            }

            return new ChemicalCombinerRecipe(id, inputs, fluidInput, output, fluidOutput);
        }

        public ChemicalCombinerRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                List<CountedIngredient> inputs = buf.readList(CountedIngredient::fromNetwork);

                ChanceIngredient output = ChanceIngredient.fromNetwork(buf);

                FluidStack fluidInput = buf.readFluidStack();
                FluidStack fluidOutput = buf.readFluidStack();

                return new ChemicalCombinerRecipe(id, inputs, fluidInput, output, fluidOutput);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_separating recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalCombinerRecipe recipe) {
            try {
                buf.writeCollection(recipe.recipeItems, (buffer, ing) -> ing.toNetwork(buffer));
                buf.writeInt(recipe.getIngredients().size());
                for (Ingredient ing : recipe.getIngredients()) {
                    ing.toNetwork(buf);
                }
                recipe.output.toNetwork(buf);

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

