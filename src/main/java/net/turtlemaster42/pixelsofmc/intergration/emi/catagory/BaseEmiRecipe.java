package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseEmiRecipe<R extends Recipe<?>> implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiTexture texture;
    private final EmiRecipeCategory category;
    protected final R recipe;

    public BaseEmiRecipe(R recipe, EmiTexture texture, EmiRecipeCategory category) {
        this.id = recipe.getId();
        this.texture = texture;
        this.category =  category;
        this.recipe = recipe;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return category;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of();
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
    }

    @Override
    public int getDisplayWidth() {
        return texture.width;
    }

    @Override
    public int getDisplayHeight() {
        return texture.height;
    }

    protected void draw(WidgetHolder widgetHolder) {}

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(texture, 0, 0);
        addWidgets(new AdvancedWidgetHolder(widgetHolder));
        draw(widgetHolder);
    }

    public void addWidgets(AdvancedWidgetHolder widgetHolder) {
        widgetHolder.addTexture(texture, 0, 0);
    }


    public static List<EmiIngredient> parseInput(ItemStack input) {
        return List.of(EmiIngredient.of(Ingredient.of(input)));
    }

    public static List<EmiIngredient> parseInput(Ingredient input) {
        return List.of(EmiIngredient.of(input));
    }

    public static List<EmiIngredient> parseInput(CountedIngredient input) {
        return List.of(EmiIngredient.of(input.ingredient(), input.count()));
    }

    public static List<EmiIngredient> parseInput(ChanceIngredient input) {
        return List.of(EmiIngredient.of(input.ingredient(), input.count()).setChance(input.chance()));
    }

    public static List<EmiIngredient> parseInput(FluidStack input) {
        return List.of(EmiStack.of(input.getFluid(), input.getAmount()));
    }

    public static List<EmiIngredient> parseInput(List<Ingredient> input) {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (Ingredient ingredient : input) {
            list.add(EmiIngredient.of(ingredient));
        }
        return list;
    }

    public static List<EmiIngredient> parseStackInput(List<ItemStack> input) {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (ItemStack stack : input) {
            list.add(EmiIngredient.of(Ingredient.of(stack)));
        }
        return list;
    }

    public static List<EmiIngredient> parseCountedInput(List<CountedIngredient> input) {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (CountedIngredient ingredient : input) {
            list.add(EmiIngredient.of(ingredient.ingredient(), ingredient.count()));
        }
        return list;
    }

    public static List<EmiIngredient> parseChanceInput(List<ChanceIngredient> input) {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (ChanceIngredient ingredient : input) {
            list.add(EmiIngredient.of(ingredient.ingredient(), ingredient.count()).setChance(ingredient.chance()));
        }
        return list;
    }

    public static List<EmiIngredient> parseFluidInput(List<FluidStack> input) {
        List<EmiIngredient> list = new java.util.ArrayList<>();
        for (FluidStack ingredient : input) {
            list.add(EmiStack.of(ingredient.getFluid(), ingredient.getAmount()));
        }
        return list;
    }

    public static List<EmiStack> parseOutput(ItemStack output) {
        return List.of(EmiStack.of(output));
    }

    public static List<EmiStack> parseOutput(Ingredient output) {
        return List.of(EmiStack.of(output.getItems()[0]));
    }

    public static List<EmiStack> parseOutput(CountedIngredient output) {
        return List.of(EmiStack.of(output.asItemStack()));
    }

    public static List<EmiStack> parseOutput(ChanceIngredient output) {
        return List.of(EmiStack.of(output.asItemStack()).setChance(output.chance()));
    }

    public static List<EmiStack> parseOutput(FluidStack output) {
        return List.of(EmiStack.of(output.getFluid(), output.getAmount()));
    }

    public static List<EmiStack> parseOutput(List<Ingredient> output) {
        List<EmiStack> list = new java.util.ArrayList<>();
        for (Ingredient ingredient : output) {
            list.add(EmiStack.of(ingredient.getItems()[0]));
        }
        return list;
    }

    public static List<EmiStack> parseStackOutput(List<ItemStack> output) {
        List<EmiStack> list = new java.util.ArrayList<>();
        for (ItemStack stack : output) {
            list.add(EmiStack.of(stack));
        }
        return list;
    }

    public static List<EmiStack> parseCountOutput(List<CountedIngredient> output) {
        List<EmiStack> list = new java.util.ArrayList<>();
        for (CountedIngredient ingredient : output) {
            list.add(EmiStack.of(ingredient.asItemStack()));
        }
        return list;
    }

    public static List<EmiStack> parseChanceOutput(List<ChanceIngredient> output) {
        List<EmiStack> list = new java.util.ArrayList<>();
        for (ChanceIngredient ingredient : output) {
            list.add(EmiStack.of(ingredient.asItemStack()).setChance(ingredient.chance()));
        }
        return list;
    }

    public static List<EmiStack> parseFluidOutput(List<FluidStack> output) {
        List<EmiStack> list = new java.util.ArrayList<>();
        for (FluidStack ingredient : output) {
            list.add(EmiStack.of(ingredient.getFluid(), ingredient.getAmount()));
        }
        return list;
    }

    public static EmiStack parseStack(FluidStack stack) {
        return EmiStack.of(stack.getFluid(), stack.getAmount());
    }

    public static EmiStack parseStack(ItemStack stack) {
        return EmiStack.of(stack);
    }

    public static EmiStack parseStack(Ingredient stack) {
        return EmiStack.of(stack.getItems()[0]);
    }

    public static EmiStack parseStack(CountedIngredient stack) {
        return EmiStack.of(stack.asItemStack());
    }

    public static EmiStack parseStack(ChanceIngredient stack) {
        return EmiStack.of(stack.asItemStack());
    }
}
