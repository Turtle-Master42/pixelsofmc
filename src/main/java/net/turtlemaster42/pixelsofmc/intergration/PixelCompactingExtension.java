package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.recipe.PixelCompactingRecipe;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record PixelCompactingExtension(PixelCompactingRecipe recipe) implements ICraftingCategoryExtension {
    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ICraftingGridHelper craftingGridHelper, @NotNull IFocusGroup focuses) {
        List<ItemStack> inputs = new ArrayList<>();
        List<ItemStack> outputs = new ArrayList<>();

//        craftingGridHelper.createAndSetInputs(builder, inputs, 0, 0);
//        craftingGridHelper.createAndSetOutputs(builder, outputs);

        for (Element m : Element.values()) {
            inputs.add(m.pixel());
            outputs.add(m.pixelPile());
        }

        List<IRecipeSlotBuilder> gridSlots = craftingGridHelper.createAndSetInputs(builder, Arrays.asList(inputs, inputs, inputs, inputs, inputs, inputs, inputs, inputs), 0, 0);
        IRecipeSlotBuilder outSlot = craftingGridHelper.createAndSetOutputs(builder, outputs);

        builder.createFocusLink(new IRecipeSlotBuilder[] {
                gridSlots.get(0), gridSlots.get(1), gridSlots.get(2), gridSlots.get(3), gridSlots.get(4), gridSlots.get(5), gridSlots.get(6), gridSlots.get(7), outSlot
        });
    }


    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }
}
