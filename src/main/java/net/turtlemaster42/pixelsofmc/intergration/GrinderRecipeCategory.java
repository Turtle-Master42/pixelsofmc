package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class GrinderRecipeCategory implements IRecipeCategory<GrinderRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "grinding");
    public final static ResourceLocation TEXTURE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/grinder_gui.png");
    public final static ResourceLocation CHANCE = new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/widgets.png");

    private final IDrawable background;
    private final IDrawable chanceOverlay;
    private final IDrawable smallChanceOverlay;
    private final IDrawable slot;
    private final IDrawable icon;

    public GrinderRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 30, 6, 115, 75);
        this.chanceOverlay = helper.drawableBuilder(CHANCE, 0, 0, 16 ,16).build();
        this.smallChanceOverlay = helper.drawableBuilder(CHANCE, 16, 0, 16 ,16).build();
        this.slot = helper.drawableBuilder(CHANCE, 32, 0, 16 ,16).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.GRINDER.get()));
    }

    @Override
    public RecipeType<GrinderRecipe> getRecipeType() {
        return new RecipeType<>(UID, GrinderRecipe.class);
    }

    @Override
    @Deprecated
    public Class<? extends GrinderRecipe> getRecipeClass() {
        return GrinderRecipe.class;
    }

    @Override
    @Deprecated
       public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("block.pixelsofmc.grinder");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull GrinderRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
            builder.addSlot(RecipeIngredientRole.INPUT, 13, 30).addIngredients(recipe.getInput());
        //outputs
        for (int p = 0; p < recipe.getOutputs().size(); p++ ) {
            IDrawable overlay = chanceOverlay;
            String display = "§6"+recipe.OutputChance(p)*100+"%";
            int x = 81 + 18*(p/4);
            int y = 9*((p-4*(p/4))*2+1)-6;

            if (recipe.OutputChance(p) < 0.5)
                overlay = smallChanceOverlay;
            if(p > 3)
                display=display+"\n§cThis item may not appear if the 4 official slots are full";

            if (recipe.OutputChance(p) < 1)
                builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(recipe.getResultItems(p))
                    .setBackground(overlay, 0, 0).addTooltipCallback(new JEItooltip(display));
            else if (p > 3)
                builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(recipe.getResultItems(p)).setBackground(slot, 0, 0);
            else
                builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(recipe.getResultItems(p));
        }
    }
}
