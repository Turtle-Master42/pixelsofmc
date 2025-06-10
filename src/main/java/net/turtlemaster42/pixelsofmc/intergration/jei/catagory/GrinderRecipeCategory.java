package net.turtlemaster42.pixelsofmc.intergration.jei.catagory;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.intergration.jei.IngredientDrawable;
import net.turtlemaster42.pixelsofmc.intergration.jei.JEItooltip;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class GrinderRecipeCategory extends BaseCategory<GrinderRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("grinding");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/grinder.png");

    private final IDrawable slot;

    public GrinderRecipeCategory(IGuiHelper helper) {
        super(helper);
        this.background = helper.createDrawable(TEXTURE, 0, 0, 98, 84);
        this.slot = helper.drawableBuilder(CHANCE, 32, 0, 16 ,16).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.GRINDER.get()));
    }

    @Override
    public @NotNull RecipeType<GrinderRecipe> getRecipeType() {
        return new RecipeType<>(UID, GrinderRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.grinder");
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull GrinderRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        addInputSlot(builder, 7, 34, recipe.getInput());
        //outputs

        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            float chance = recipe.getOutputChance(p);
            IDrawable overlay = chanceOverlay;
            String display = "§6"+Math.round(chance*100)+"%";
            int x = 75 + 18*(p/4);
            int y = 7 + (18*p) - (72*(p/4));

            if (chance < 0.5)
                overlay = smallChanceOverlay;
            if(p > 3)
                display = display + "\n§cThis item may not appear if the 4 official slots are full";

            ChanceIngredient output = recipe.getOutputs().get(p);
            if (chance < 1)
                builder.addOutputSlot(x, y)
                        .addIngredients(output.ingredient())
                        .setOverlay(new IngredientDrawable(output), 0, 0)
                        .setBackground(chance < 0.5f ? smallChanceOverlay : chanceOverlay, 0, 0)
                        .addRichTooltipCallback(new JEItooltip(display))
                        .setBackground(overlay, 0, 0);
            else if (p > 3)
                builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addIngredients(output.ingredient()).setBackground(slot, 0, 0);
            else
                addOutputSlot(builder, x, y, output);
        }
    }
}
