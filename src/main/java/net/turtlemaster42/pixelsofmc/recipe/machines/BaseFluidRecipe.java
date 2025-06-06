package net.turtlemaster42.pixelsofmc.recipe.machines;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFluidRecipe implements Recipe<FluidContainer> {
    private final ResourceLocation id;

    public BaseFluidRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FluidContainer fluidContainer, @NotNull RegistryAccess registryAccess) {return ItemStack.EMPTY;}

    public static boolean matchFluidInput(FluidContainer container, FluidStack fluidStack) {
        if (container.getContainerSize() != 1) {
            throw new IllegalArgumentException("Fluid recipe can't have more than 1 tank, there where " + container.getContainerSize() + " proved");
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
