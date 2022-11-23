//credits ImmersiveEngineering

package net.turtlemaster42.pixelsofmc.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DamageToolRecipe extends ShapelessRecipe {
    public DamageToolRecipe(ResourceLocation id, String group, ItemStack result, Ingredient tool, NonNullList<Ingredient> input) {
        super(id, group, result, addTo(tool, input));
    }

    private static NonNullList<Ingredient> addTo(Ingredient additional, NonNullList<Ingredient> old) {
        old.add(additional);
        return old;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remains = super.getRemainingItems(inv);
        for(int i = 0; i < remains.size(); i++) {
            ItemStack s = inv.getItem(i);
            ItemStack remain = remains.get(i);
            ItemStack tool = ItemStack.EMPTY;
            int toolDamageSlot = getIngredients().size()-1;
            if(remain.isEmpty()&&!s.isEmpty()&&getIngredients().get(toolDamageSlot).test(s))
                tool = s.copy();
            else if(!remain.isEmpty()&&getIngredients().get(toolDamageSlot).test(remain))
                tool = remain;
            if(!tool.isEmpty()&&tool.isDamageableItem()) {
                tool.setDamageValue(tool.getDamageValue()+1);
                if(tool.getDamageValue() > tool.getMaxDamage())
                    tool = ItemStack.EMPTY;
                remains.set(i, tool);
            }
        }
        return remains;
    }

    @Override
    public boolean matches(CraftingContainer matrix, Level world) {
        List<Ingredient> required = new LinkedList<>(getIngredients());

        for(int i = 0; i < matrix.getContainerSize(); i++) {
            ItemStack slot = matrix.getItem(i);
            if(!slot.isEmpty()) {
                boolean inRecipe = false;
                Iterator<Ingredient> iterator = required.iterator();
                while(iterator.hasNext()) {
                    Ingredient next = iterator.next();
                    if(next.test(slot)) {
                        inRecipe = true;
                        iterator.remove();
                        break;
                    }
                }
                if(!inRecipe)
                    return false;
            }
        }
        return required.isEmpty();
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return POMrecipes.DAMAGE_TOOL_SERIALIZER.get();
    }

    public Ingredient getTool()
    {
        return getIngredients().get(getIngredients().size()-1);
    }
}
