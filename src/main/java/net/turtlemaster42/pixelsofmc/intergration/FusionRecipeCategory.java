package net.turtlemaster42.pixelsofmc.intergration;

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
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class FusionRecipeCategory extends BaseCategory<FusionRecipe> {
    public final static ResourceLocation UID = Util.resourceLocation("fusing");
    public final static ResourceLocation TEXTURE = Util.resourceLocation("textures/gui/jei/fusing.png");

    public FusionRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 158, 66);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(POMblocks.SDS_CONTROLLER.get()));
    }

    @Override
    public @NotNull RecipeType<FusionRecipe> getRecipeType() {
        return new RecipeType<>(UID, FusionRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.pixelsofmc.sds_controller");
    }

    @Override
    public void draw(FusionRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        String proton = String.valueOf(recipe.getProtonCount());
        String neutron = String.valueOf(recipe.getNeutronCount());

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int stringWidth1 = font.width(proton);
        int stringWidth2 = font.width(neutron);

        guiGraphics.drawString(font, proton, background.getWidth() - stringWidth1 - 125, 10, 0xFF5555FF, true);
        guiGraphics.drawString(font, neutron, background.getWidth() - stringWidth2 - 125, 30, 0xFFFF5555, true);

        guiGraphics.drawString(font, Component.translatable("tooltip.pixelsofmc.fusion.required"), 43, 3, 0xFF2CBAA8, true);
        if (recipe.getBaseOutput().is(POMtags.Items.MNS)) {
            guiGraphics.drawString(font, Component.translatable("tooltip.pixelsofmc.fusion.mns"), 43, 13, 0xFFFFAA00, true);
        } else if (recipe.getBaseOutput().is(POMtags.Items.MDS)) {
            guiGraphics.drawString(font, Component.translatable("tooltip.pixelsofmc.fusion.mds"), 43, 13, 0xFFFFAA00, true);
        } else {
            guiGraphics.drawString(font, Component.translatable("tooltip.pixelsofmc.fusion.sds"), 43, 13, 0xFFFFAA00, true);
        }
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull FusionRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //output
        addOutputSlot(builder, 82, 25, recipe.getBaseOutput());
    }
}