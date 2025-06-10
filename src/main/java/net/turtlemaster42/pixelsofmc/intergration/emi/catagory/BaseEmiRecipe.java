package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseEmiRecipe<R extends Recipe<?>> implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiTexture texture;
    private final EmiRecipeCategory category;
    protected final R recipe;

    public BaseEmiRecipe(R recipe, EmiTexture texture, EmiRecipeCategory category) {
        this.id = recipe.getId();
        this.texture = texture;
        this.category =  category;
        this.recipe = recipe;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return category;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of();
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
    }

    @Override
    public int getDisplayWidth() {
        return texture.width;
    }

    @Override
    public int getDisplayHeight() {
        return texture.height;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(texture, 0, 0);
        addWidgets(new AdvancedWidgetHolder(widgetHolder));
    }

    public void addWidgets(AdvancedWidgetHolder widgetHolder) {
        widgetHolder.addTexture(texture, 0, 0);
    }
}
