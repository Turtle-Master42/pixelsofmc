package net.turtlemaster42.pixelsofmc.intergration.emi.catagory;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.intergration.emi.AdvancedWidgetHolder;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.List;

public class FusionRecipeEmi extends BaseEmiRecipe<FusionRecipe> {
    public static final EmiTexture TEXTURE = new EmiTexture(Util.resourceLocation("textures/gui/jei/fusing.png"), 0, 0, 158, 66);

    public FusionRecipeEmi(FusionRecipe recipe, EmiRecipeCategory category) {
        super(recipe, TEXTURE, category);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return parseCountOutput(recipe.getOutputs());
    }

    protected void draw(WidgetHolder widgets) {
        String proton = String.valueOf(recipe.getProtonCount());
        String neutron = String.valueOf(recipe.getNeutronCount());

        Font font = Minecraft.getInstance().font;
        widgets.addText(Component.literal(proton), getDisplayWidth() - font.width(proton) - 125, 10, 0xFF5555FF, true);
        widgets.addText(Component.literal(neutron), getDisplayWidth() - font.width(neutron) - 125, 30, 0xFFFF5555, true);

        widgets.addText(Component.translatable("tooltip.pixelsofmc.fusion.required"), 43, 3, 0xFF2CBAA8, true);
        if (recipe.getBaseOutput().is(POMtags.Items.MNS)) {
            widgets.addText(Component.translatable("tooltip.pixelsofmc.fusion.mns"), 43, 13, 0xFFFFAA00, true);
        } else if (recipe.getBaseOutput().is(POMtags.Items.MDS)) {
            widgets.addText(Component.translatable("tooltip.pixelsofmc.fusion.mds"), 43, 13, 0xFFFFAA00, true);
        } else {
            widgets.addText(Component.translatable("tooltip.pixelsofmc.fusion.sds"), 43, 13, 0xFFFFAA00, true);
        }
    }

    @Override
    public void addWidgets(AdvancedWidgetHolder widgets) {
        //output
        widgets.addSlot(recipe.getBaseOutput(), 82, 25).recipeContext(this);
    }
}
