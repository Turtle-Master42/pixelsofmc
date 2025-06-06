package net.turtlemaster42.pixelsofmc.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BaseFluidRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FluidHeatingRecipe extends BaseFluidRecipe {
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;
    private final int requiredEnergy;

    public FluidHeatingRecipe(ResourceLocation id, FluidStack fluidInput, FluidStack fluidOutput, int requiredEnergy) {
        super(id);
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
        this.requiredEnergy = requiredEnergy;
    }

    @Override
    public boolean matches(@NotNull FluidContainer fluidContainer, Level level) {
        if (level.isClientSide()) {return false;}
        return matchFluidInput(fluidContainer, getFluidInput());
    }

    public FluidStack getResultFluid() {return this.fluidOutput;}
    public FluidStack getFluidInput() {return this.fluidInput;}
    public int getRequiredEnergy() {return requiredEnergy;}

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(POMblocks.INDUSTRIAL_HEAT_EXCHANGER.get());
    }

    public static class Type implements RecipeType<FluidHeatingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fluid_heating";
    }

    public static class Serializer implements POMRecipeSerializer<FluidHeatingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public @NotNull FluidHeatingRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            FluidStack fluidOutput = FluidJSONUtil.readFluid(json.getAsJsonObject("fluid_output"));
            //input
            FluidStack fluidInput = FluidJSONUtil.readFluid(json.getAsJsonObject("fluid_input"));
            int energy = GsonHelper.getAsInt(json, "energy");
            return new FluidHeatingRecipe(id, fluidInput, fluidOutput, energy);
        }

        public FluidHeatingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            FluidStack fluidInput = FluidStack.readFromPacket(buf);
            FluidStack fluidOutput = FluidStack.readFromPacket(buf);
            int energy = buf.readInt();
            return new FluidHeatingRecipe(id, fluidInput, fluidOutput, energy);
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull FluidHeatingRecipe recipe) {
            recipe.fluidInput.writeToPacket(buf);
            recipe.fluidOutput.writeToPacket(buf);
            buf.writeInt(recipe.requiredEnergy);
        }

        @Override
        public RecipeSerializer<?> setRegistryName() {return INSTANCE;}

        @Nullable
        public ResourceLocation getRegistryName() {return Util.resourceLocation(Type.ID);}
    }
}

