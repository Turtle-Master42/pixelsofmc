package net.turtlemaster42.pixelsofmc.intergration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.screen.AbstractPOMscreen;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.recipe.PixelCompactingRecipe;
import net.turtlemaster42.pixelsofmc.recipe.PixelDecompactingRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.*;
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
        registration.addRecipeCategories(new FusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PixelSplitterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PixelAssemblerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChemicalSeraratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChemicalCombinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(POMblocks.BALL_MILL.get()), new RecipeType<>(BallMillRecipeCategory.UID, BallMillRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.GRINDER.get()), new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.HOT_ISOSTATIC_PRESS.get()), new RecipeType<>(HotIsostaticPressRecipeCategory.UID, HotIsostaticPressRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.SDS_CONTROLLER.get()), new RecipeType<>(FusionRecipeCategory.UID, FusionRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.MDS_CONTROLLER.get()), new RecipeType<>(FusionRecipeCategory.UID, FusionRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.MNS_CONTROLLER.get()), new RecipeType<>(FusionRecipeCategory.UID, FusionRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.BH_CONTROLLER.get()), new RecipeType<>(FusionRecipeCategory.UID, FusionRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.PIXEL_SPLITTER.get()), new RecipeType<>(PixelSplitterRecipeCategory.UID, PixelSplitterRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.PIXEL_ASSEMBLER.get()), new RecipeType<>(PixelAssemblerRecipeCategory.UID, PixelAssemblerRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.CHEMICAL_SEPARATOR.get()), new RecipeType<>(ChemicalSeraratorRecipeCategory.UID, ChemicalSeparatorRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(POMblocks.CHEMICAL_COMBINER.get()), new RecipeType<>(ChemicalCombinerRecipeCategory.UID, ChemicalCombinerRecipe.class));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<BallMillRecipe> milling = rm.getAllRecipesFor(BallMillRecipe.Type.INSTANCE);
        List<GrinderRecipe> grinding = rm.getAllRecipesFor(GrinderRecipe.Type.INSTANCE);
        List<HotIsostaticPressRecipe> pressing = rm.getAllRecipesFor(HotIsostaticPressRecipe.Type.INSTANCE);
        List<FusionRecipe> fusing = rm.getAllRecipesFor(FusionRecipe.Type.INSTANCE);
        List<PixelSplitterRecipe> splitting = rm.getAllRecipesFor(PixelSplitterRecipe.Type.INSTANCE);
        List<PixelAssemblerRecipe> assembling = rm.getAllRecipesFor(PixelAssemblerRecipe.Type.INSTANCE);
        List<ChemicalSeparatorRecipe> separating = rm.getAllRecipesFor(ChemicalSeparatorRecipe.Type.INSTANCE);
        List<ChemicalCombinerRecipe> combining = rm.getAllRecipesFor(ChemicalCombinerRecipe.Type.INSTANCE);

        registration.addRecipes(new RecipeType<>(BallMillRecipeCategory.UID, BallMillRecipe.class), milling);
        registration.addRecipes(new RecipeType<>(GrinderRecipeCategory.UID, GrinderRecipe.class), grinding);
        registration.addRecipes(new RecipeType<>(HotIsostaticPressRecipeCategory.UID, HotIsostaticPressRecipe.class), pressing);
        registration.addRecipes(new RecipeType<>(FusionRecipeCategory.UID, FusionRecipe.class), fusing);
        registration.addRecipes(new RecipeType<>(PixelSplitterRecipeCategory.UID, PixelSplitterRecipe.class), splitting);
        registration.addRecipes(new RecipeType<>(PixelAssemblerRecipeCategory.UID, PixelAssemblerRecipe.class), assembling);
        registration.addRecipes(new RecipeType<>(ChemicalSeraratorRecipeCategory.UID, ChemicalSeparatorRecipe.class), separating);
        registration.addRecipes(new RecipeType<>(ChemicalCombinerRecipeCategory.UID, ChemicalCombinerRecipe.class), combining);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(POMitems.PIXEL.get(), POMitems.PIXEL_PILE.get());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(AbstractPOMscreen.class, new GuiSizeExtension());
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(PixelDecompactingRecipe.class, PixelDecompactingExtension::new);
        registration.getCraftingCategory().addCategoryExtension(PixelCompactingRecipe.class, PixelCompactingExtension::new);
    }
}