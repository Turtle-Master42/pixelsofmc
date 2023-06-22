package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.recipe.PixelDecompactingRecipe;
import net.turtlemaster42.pixelsofmc.util.Element;

import java.util.ArrayList;
import java.util.List;

public record PixelDecompactingExtension(PixelDecompactingRecipe recipe) implements ICraftingCategoryExtension {
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        List<ItemStack> inputs = new ArrayList<>();
        List<ItemStack> outputs = new ArrayList<>();

        for (Element m : Element.values()) {
            inputs.add(m.pixelPile());
            ItemStack out = m.pixel();
            out.setCount(8);
            outputs.add(out);
        }

        List<IRecipeSlotBuilder> gridSlots = craftingGridHelper.createAndSetInputs(builder, List.of(inputs), 0, 0);
        IRecipeSlotBuilder outSlot = craftingGridHelper.createAndSetOutputs(builder, outputs);

        builder.createFocusLink(new IRecipeSlotBuilder[] {
                outSlot
        });
    }

    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }
}
