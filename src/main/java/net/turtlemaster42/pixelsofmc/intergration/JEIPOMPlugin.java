package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPOMPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(PixelsOfMc.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BallMillRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new GrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HotIsostaticPressRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(POMblocks.BALL_MILL.get()), new RecipeType<>(BallMillRecipeCategory.UID, BallMillRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.GRINDER.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
    registration.addRecipeCatalyst(new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get()), new RecipeType<>(HotIsostaticPressRecipeCategory.UID, HotIsostaticPressRecipe.class));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<BallMillRecipe> milling = rm.getAllRecipesFor(BallMillRecipe.Type.INSTANCE);
        List<GrinderRecipe> grinding = rm.getAllRecipesFor(GrinderRecipe.Type.INSTANCE);
        List<HotIsostaticPressRecipe> pressing = rm.getAllRecipesFor(HotIsostaticPressRecipe.Type.INSTANCE);

        registration.addRecipes(new RecipeType<>(BallMillRecipeCategory.UID, BallMillRecipe.class), milling);
        registration.addRecipes(new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class), grinding);
        registration.addRecipes(new RecipeType<>(HotIsostaticPressRecipeCategory.UID, HotIsostaticPressRecipe.class), pressing);
    }
}