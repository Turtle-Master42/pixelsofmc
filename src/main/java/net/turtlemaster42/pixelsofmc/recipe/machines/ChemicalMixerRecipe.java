package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChemicalMixerRecipe implements Recipe<FluidContainer> {
    private final ResourceLocation id;
    private final List<FluidStack> fluidInputs;
    private final List<FluidStack> fluidOutputs;
    private final int temperatureState;

    public ChemicalMixerRecipe(ResourceLocation id, List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int temperatureState) {
        this.id = id;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.temperatureState = temperatureState;
    }

    @Override
    public boolean matches(@NotNull FluidContainer fluidContainer, Level level) {
        if (level.isClientSide()) {return false;}
        return matchMultiFluidInput(fluidContainer, getFluidInputs());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FluidContainer fluidContainer, @NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }


    public int getTemperatureState() {return this.temperatureState;}
    public List<FluidStack> getResultFluids() {return this.fluidOutputs;}
    public List<FluidStack> getFluidInputs() {return this.fluidInputs;}

    public FluidStack getResultFluid(int index) {
        if (this.fluidOutputs.size() > index) {
            return this.fluidOutputs.get(index);
        } else {
            return FluidStack.EMPTY;
        }
    }
    public FluidStack getInputFluid(int index) {
        if (this.fluidInputs.size() > index) {
            return this.fluidInputs.get(index);
        } else {
            return FluidStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {return true;}

    @Override
    public boolean isSpecial() {return true;}

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
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
        return new ItemStack(POMblocks.CHEMICAL_MIXER.get());
    }

    public static class Type implements RecipeType<ChemicalMixerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "chemical_mixing";
    }

    public static class Serializer implements RecipeSerializer<ChemicalMixerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Util.resourceLocation("chemical_mixing");

        public @NotNull ChemicalMixerRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            JsonArray jsonOutputs = json.getAsJsonArray("fluid_outputs");
            List<FluidStack> fluidOutputs = new ArrayList<>(jsonOutputs.size());
            for (int i = 0; i < jsonOutputs.size(); i++) {
                fluidOutputs.add(i, FluidJSONUtil.readFluid(jsonOutputs.get(i).getAsJsonObject()));
            }
            //input
            JsonArray jsonInputs = json.getAsJsonArray("fluid_inputs");
            List<FluidStack> fluidInputs = new ArrayList<>(jsonInputs.size());
            for (int i = 0; i < jsonInputs.size(); i++) {
                fluidInputs.add(i, FluidJSONUtil.readFluid(jsonInputs.get(i).getAsJsonObject()));
            }
            int temperature = json.get("temperature_state").getAsInt();

            return new ChemicalMixerRecipe(id, fluidInputs, fluidOutputs, temperature);
        }

        public ChemicalMixerRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                List<FluidStack> fluidInputs = buf.readList(FluidStack::readFromPacket);
                List<FluidStack> fluidOutputs = buf.readList(FluidStack::readFromPacket);
                int temperature = buf.readInt();

                return new ChemicalMixerRecipe(id, fluidInputs, fluidOutputs, temperature);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_mixing recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalMixerRecipe recipe) {
            try {
                buf.writeCollection(recipe.fluidInputs, (buffer, fluid) -> fluid.writeToPacket(buffer));
                buf.writeCollection(recipe.fluidOutputs, (buffer, fluid) -> fluid.writeToPacket(buffer));
                buf.writeInt(recipe.temperatureState);

            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading chemical_mixing recipe from packet.", ex);
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

    public static boolean matchMultiFluidInput(FluidContainer container, List<FluidStack> fluidStacks) {
        List<Fluid> tankFluids = new ArrayList<>();
        List<Integer> tankAmounts = new ArrayList<>();

        // Iterate over the tanks and makes a list of the total ingredients

        for (int i = 0; i < container.getContainerSize(); i++) {
            FluidStack stack = container.getFluid(i);
            if (stack.isEmpty())
                continue;

            if (!tankFluids.contains(stack.getFluid())) {
                tankFluids.add(stack.getFluid());
                tankAmounts.add(stack.getAmount());
            } else {
                int index = tankFluids.indexOf(stack.getFluid());
                tankAmounts.set(index, tankAmounts.get(index) + stack.getAmount());
            }
        }

        // if tankFluids and fluidStacks are not equal there are ingredients missing or to many
        if (tankFluids.size() != fluidStacks.size()) {
            return false;
        }

        // Iterates over the needed items
        for (FluidStack fluidStack : fluidStacks) {
            Fluid fluid = fluidStack.getFluid();
            // Checks if the fluid is present in the tanks
            if (!tankFluids.contains(fluid)) {
                return false;
            }
            int index = tankFluids.indexOf(fluid);
            // Checks if there is enough fluid in the tanks
            if (fluidStack.getAmount() > tankAmounts.get(index)) {
                return false;
            }
            // We win, the item is present and there is enough
        }
        return true;
    }
}

