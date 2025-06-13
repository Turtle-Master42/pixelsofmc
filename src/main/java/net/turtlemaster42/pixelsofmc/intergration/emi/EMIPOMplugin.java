package net.turtlemaster42.pixelsofmc.intergration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.intergration.emi.catagory.*;
import net.turtlemaster42.pixelsofmc.recipe.DecayRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.*;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.function.BiFunction;

@EmiEntrypoint
public class EMIPOMplugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        //automatic exclusion areas
        registry.addGenericExclusionArea(new ExclusionArea());

        //nbt stuffs
        registry.setDefaultComparison(POMitems.PIXEL.get(), Comparison.compareNbt());
        registry.setDefaultComparison(POMitems.PIXEL_PILE.get(), Comparison.compareNbt());

        addEmiCategory(registry, "pressing", 0, 0,
                POMblocks.HOT_ISOSTATIC_PRESS.get(),
                HotIsostaticPressRecipe.Type.INSTANCE,
                (recipe, category) -> new HotIsostaticPressRecipeEmi((HotIsostaticPressRecipe)recipe, category));
        addEmiCategory(registry, "milling", 16, 0,
                POMblocks.BALL_MILL.get(),
                BallMillRecipe.Type.INSTANCE,
                (recipe, category) -> new BallMillRecipeEmi((BallMillRecipe)recipe, category));
        addEmiCategory(registry, "grinding", 32, 0,
                POMblocks.GRINDER.get(),
                GrinderRecipe.Type.INSTANCE,
                (recipe, category) -> new GrinderRecipeEmi((GrinderRecipe)recipe, category));
        addEmiCategory(registry, "chemical_combining", 48, 0,
                POMblocks.CHEMICAL_COMBINER.get(),
                ChemicalCombinerRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalCombinerRecipeEmi((ChemicalCombinerRecipe)recipe, category));
        addEmiCategory(registry, "chemical_mixing", 64, 0,
                POMblocks.CHEMICAL_MIXER.get(),
                ChemicalMixerRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalMixerRecipeEmi((ChemicalMixerRecipe)recipe, category));
        addEmiCategory(registry, "chemical_separating", 80, 0,
                POMblocks.CHEMICAL_SEPARATOR.get(),
                ChemicalSeparatorRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalSeperatorRecipeEmi((ChemicalSeparatorRecipe) recipe, category));
        addEmiCategory(registry, "fusing", 96, 0,
                POMblocks.SDS_CONTROLLER.get(),
                FusionRecipe.Type.INSTANCE,
                (recipe, category) -> new FusionRecipeEmi((FusionRecipe) recipe, category));
        addEmiCategory(registry, "decay", 112, 0,
                POMitems.PLUTONIUM_FUEL_CELL.get(),
                DecayRecipe.Type.INSTANCE,
                (recipe, category) -> new DecayRecipeEmi((DecayRecipe) recipe, category));
        addEmiCategory(registry, "pixel_assembling", 144, 0,
                POMblocks.PIXEL_ASSEMBLER.get(),
                PixelAssemblerRecipe.Type.INSTANCE,
                (recipe, category) -> new PixelAssemblerRecipeEmi((PixelAssemblerRecipe) recipe, category));
        addEmiCategory(registry, "pixel_splitting", 128, 0,
                POMblocks.PIXEL_SPLITTER.get(),
                PixelSplitterRecipe.Type.INSTANCE,
                (recipe, category) -> new PixelSplitterRecipeEmi((PixelSplitterRecipe) recipe, category));
    }



    private <CONTAINER extends Container, RECIPE extends Recipe<CONTAINER>> void addEmiCategory(
            EmiRegistry registry, String id, int textureX, int textureY, ItemLike icon, RecipeType<RECIPE> type, BiFunction<Recipe<?>, EmiRecipeCategory, BaseEmiRecipe<RECIPE>> recipeFunction)
    {
        EmiRecipeCategory category = new EmiRecipeCategory(
                Util.resourceLocation(id),
                EmiStack.of(icon),
                new EmiTexture(Util.resourceLocation("textures/gui/emi/widgets.png"), textureX, textureY, 16, 16)
        );
        registry.addCategory(category);
        registry.addWorkstation(category, EmiStack.of(icon));

        RecipeManager manager = registry.getRecipeManager();
        for (RECIPE recipe : manager.getAllRecipesFor(type)) {
            registry.addRecipe(recipeFunction.apply(recipe, category));
        }
    }
}
