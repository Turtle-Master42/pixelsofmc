package net.turtlemaster42.pixelsofmc.intergration.jei.catagory;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.recipe.DecayRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DecayRecipeCategory extends BaseCategory<DecayRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("decay");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/decay.png");

    public DecayRecipeCategory(IGuiHelper helper) {
        super("decay", helper);
        this.background = helper.createDrawable(TEXTURE, 0, 0, 69, 30);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMitems.PLUTONIUM_FUEL_CELL.get()));
    }

    @Override
    public @NotNull RecipeType<DecayRecipe> getRecipeType() {
        return new RecipeType<>(UID, DecayRecipe.class);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull DecayRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //input
        addInputSlot(builder, 7, 7, new ItemStack(recipe.getFuelCell()));
        //output
        addOutputSlot(builder, 46, 7, recipe.getBaseOutput());
    }
}