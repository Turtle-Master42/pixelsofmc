package net.turtlemaster42.pixelsofmc.intergration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiExclusionArea;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.registry.EmiExclusionAreas;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.turtlemaster42.pixelsofmc.gui.screen.AbstractPOMscreen;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.intergration.emi.catagory.*;
import net.turtlemaster42.pixelsofmc.recipe.machines.*;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@EmiEntrypoint
public class EMIPOMplugin implements EmiPlugin {


    @Override
    public void register(EmiRegistry registry) {
        registry.addGenericExclusionArea(new ExclusionArea());

        addEmiCategory(registry, "pressing",
                POMblocks.HOT_ISOSTATIC_PRESS.get().asItem(),
                HotIsostaticPressRecipe.Type.INSTANCE,
                (recipe, category) -> new HotIsostaticPressRecipeEmi((HotIsostaticPressRecipe)recipe, category));
        addEmiCategory(registry, "milling",
                POMblocks.BALL_MILL.get().asItem(),
                BallMillRecipe.Type.INSTANCE,
                (recipe, category) -> new BallMillRecipeEmi((BallMillRecipe)recipe, category));
        addEmiCategory(registry, "grinding",
                POMblocks.GRINDER.get().asItem(),
                GrinderRecipe.Type.INSTANCE,
                (recipe, category) -> new GrinderRecipeEmi((GrinderRecipe)recipe, category));
        addEmiCategory(registry, "chemical_combining",
                POMblocks.CHEMICAL_COMBINER.get().asItem(),
                ChemicalCombinerRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalCombinerRecipeEmi((ChemicalCombinerRecipe)recipe, category));
        addEmiCategory(registry, "chemical_mixing",
                POMblocks.CHEMICAL_MIXER.get().asItem(),
                ChemicalMixerRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalMixerRecipeEmi((ChemicalMixerRecipe)recipe, category));
        addEmiCategory(registry, "chemical_separating",
                POMblocks.CHEMICAL_SEPARATOR.get().asItem(),
                ChemicalSeparatorRecipe.Type.INSTANCE,
                (recipe, category) -> new ChemicalSeperatorRecipeEmi((ChemicalSeparatorRecipe) recipe, category));
        addEmiCategory(registry, "fusing",
                POMblocks.SDS_CONTROLLER.get().asItem(),
                FusionRecipe.Type.INSTANCE,
                (recipe, category) -> new FusionRecipeEmi((FusionRecipe) recipe, category));

    }

    private <CONTAINER extends Container, RECIPE extends Recipe<CONTAINER>> void addEmiCategory(
            EmiRegistry registry, String id, ItemLike icon, RecipeType<RECIPE> type, BiFunction<Recipe<?>, EmiRecipeCategory, BaseEmiRecipe<RECIPE>> recipeFunction)
    {
        EmiRecipeCategory category = new EmiRecipeCategory(Util.resourceLocation(id), EmiStack.of(icon), EmiStack.of(icon));

        registry.addCategory(category);
        registry.addWorkstation(category, EmiStack.of(icon));

        RecipeManager manager = registry.getRecipeManager();
        for (RECIPE recipe : manager.getAllRecipesFor(type)) {
            registry.addRecipe(recipeFunction.apply(recipe, category));
        }
    }
}
