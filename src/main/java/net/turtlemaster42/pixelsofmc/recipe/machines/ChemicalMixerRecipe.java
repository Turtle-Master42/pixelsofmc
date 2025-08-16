package net.turtlemaster42.pixelsofmc.recipe.machines;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.POMRecipeSerializer;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import net.turtlemaster42.pixelsofmc.util.recipe.JsonRecipeUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ChemicalMixerRecipe extends BaseFluidRecipe {
    private final List<FluidStack> fluidInputs;
    private final List<FluidStack> fluidOutputs;
    private final int temperatureState;

    public ChemicalMixerRecipe(ResourceLocation id, List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int temperatureState) {
        super(id);
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.temperatureState = temperatureState;
    }

    @Override
    public boolean matches(@NotNull FluidContainer fluidContainer, Level level) {
        if (level.isClientSide()) {return false;}
        return matchMultiFluidInput(fluidContainer, getFluidInputs());
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

    public static class Serializer implements POMRecipeSerializer<ChemicalMixerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull ChemicalMixerRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            //output
            List<FluidStack> fluidOutputs = JsonRecipeUtils.FListFromJson(json, "fluid_outputs");
            //input
            List<FluidStack> fluidInputs = JsonRecipeUtils.FListFromJson(json, "fluid_inputs");
            int temperature = JsonRecipeUtils.intFromJson(json, "temperature_state");
            return new ChemicalMixerRecipe(id, fluidInputs, fluidOutputs, temperature);
        }

        public ChemicalMixerRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            List<FluidStack> fluidInputs = buf.readList(FluidStack::readFromPacket);
            List<FluidStack> fluidOutputs = buf.readList(FluidStack::readFromPacket);
            int temperature = buf.readInt();
            return new ChemicalMixerRecipe(id, fluidInputs, fluidOutputs, temperature);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ChemicalMixerRecipe recipe) {
            buf.writeCollection(recipe.fluidInputs, (buffer, fluid) -> fluid.writeToPacket(buffer));
            buf.writeCollection(recipe.fluidOutputs, (buffer, fluid) -> fluid.writeToPacket(buffer));
            buf.writeInt(recipe.temperatureState);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

