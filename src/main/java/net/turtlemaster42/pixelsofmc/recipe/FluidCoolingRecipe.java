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
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidJSONUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FluidCoolingRecipe implements Recipe<FluidContainer> {
    private final ResourceLocation id;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;
    private final int releasedEnergy;

    public FluidCoolingRecipe(ResourceLocation id, FluidStack fluidInput, FluidStack fluidOutput, int requiredEnergy) {
        this.id = id;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
        this.releasedEnergy = requiredEnergy;
    }

    @Override
    public boolean matches(@NotNull FluidContainer fluidContainer, Level level) {
        if (level.isClientSide()) {return false;}
        return matchFluidInput(fluidContainer, getFluidInput());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FluidContainer fluidContainer, @NotNull RegistryAccess registryAccess) {return ItemStack.EMPTY;}

    public FluidStack getResultFluid() {return this.fluidOutput;}
    public FluidStack getFluidInput() {return this.fluidInput;}
    public int getReleasedEnergy() {return releasedEnergy;}

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
        return new ItemStack(POMblocks.INDUSTRIAL_COOLER.get());
    }

    public static class Type implements RecipeType<FluidCoolingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fluid_cooling";
    }

    public static class Serializer implements RecipeSerializer<FluidCoolingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Util.resourceLocation("fluid_cooling");

        public @NotNull FluidCoolingRecipe fromJson(@NotNull ResourceLocation id, JsonObject json) {
            //output
            FluidStack fluidOutput = FluidJSONUtil.readFluid(json.getAsJsonObject("fluid_output"));
            //input
            FluidStack fluidInput = FluidJSONUtil.readFluid(json.getAsJsonObject("fluid_input"));
            int energy = GsonHelper.getAsInt(json, "energy");
            return new FluidCoolingRecipe(id, fluidInput, fluidOutput, energy);
        }

        public FluidCoolingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            try {
                FluidStack fluidInput = FluidStack.readFromPacket(buf);
                FluidStack fluidOutput = FluidStack.readFromPacket(buf);
                int energy = buf.readInt();
                return new FluidCoolingRecipe(id, fluidInput, fluidOutput, energy);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading fluid_heating recipe from packet.", ex);
                throw ex;
            }
        }

        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull FluidCoolingRecipe recipe) {
            try {
                recipe.fluidInput.writeToPacket(buf);
                recipe.fluidOutput.writeToPacket(buf);
                buf.writeInt(recipe.releasedEnergy);
            } catch (Exception ex) {
                PixelsOfMc.LOGGER.error("Error reading fluid_heating recipe from packet.", ex);
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

    public static boolean matchFluidInput(FluidContainer container, FluidStack fluidStack) {
        if (container.getContainerSize() != 1) {
            throw new IllegalArgumentException("Fluid Heating recipe can't have more than 1 tank, there where " + container.getContainerSize() + " proved");
        }
        FluidStack fluidTank = container.getFluid(0);
        if (fluidTank.isEmpty())
            return false;

        Fluid fluid = fluidStack.getFluid();
        // Checks if the fluid is present in the tank
        if (!fluidTank.getFluid().equals(fluid)) {
            return false;
        }
        // Checks if there is enough fluid in the tank
        return fluidStack.getAmount() <= fluidTank.getAmount();
        // We win, the fluid is present and there is enough
    }
}

