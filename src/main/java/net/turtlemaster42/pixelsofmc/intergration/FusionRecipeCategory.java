package net.turtlemaster42.pixelsofmc.intergration;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class FusionRecipeCategory implements IRecipeCategory<FusionRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(PixelsOfMc.MOD_ID, "fusing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(PixelsOfMc.MOD_ID, "textures/gui/jei/fusing.png");

    private final IDrawable background;
    private final IDrawable icon;

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
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(FusionRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack poseStack, double mouseX, double mouseY) {
        String proton = String.valueOf(recipe.getProtonCount());
        String neutron = String.valueOf(recipe.getNeutronCount());
        String electron = String.valueOf(recipe.getElectronCount());

        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth1 = fontRenderer.width(proton);
        int stringWidth2 = fontRenderer.width(neutron);
        int stringWidth3 = fontRenderer.width(electron);

        fontRenderer.drawShadow(poseStack, proton, background.getWidth() - stringWidth1 - 125, 10, 0xFF5555FF);
        fontRenderer.drawShadow(poseStack, neutron, background.getWidth() - stringWidth2 - 125, 30, 0xFFFF5555);
        fontRenderer.drawShadow(poseStack, electron, background.getWidth() - stringWidth3 - 125, 50, 0xFFFFFF55);

        fontRenderer.drawShadow(poseStack, Component.translatable("tooltip.pixelsofmc.fusion.required"), 43, 3, 0xFF2CBAA8);
        if (recipe.getResultItem().is(POMtags.Items.MNS)) {
            fontRenderer.drawShadow(poseStack, Component.translatable("tooltip.pixelsofmc.fusion.mns"), 43, 13, 0xFFFFAA00);
        } else if (recipe.getResultItem().is(POMtags.Items.MDS)) {
            fontRenderer.drawShadow(poseStack, Component.translatable("tooltip.pixelsofmc.fusion.mds"), 43, 13, 0xFFFFAA00);
        } else {
            fontRenderer.drawShadow(poseStack, Component.translatable("tooltip.pixelsofmc.fusion.sds"), 43, 13, 0xFFFFAA00);
        }
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull FusionRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 82, 25).addIngredients(Ingredient.of(recipe.getResultItem()));
    }
}