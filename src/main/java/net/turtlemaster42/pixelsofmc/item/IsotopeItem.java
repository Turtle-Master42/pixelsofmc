package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class IsotopeItem extends ElementItem {
    Element e;
    private final int neutrons;

    public IsotopeItem(Element e, int addedNeutrons, Properties properties) {
        super(e, properties);
        this.e = e;
        this.neutrons = addedNeutrons;
    }

    @Override
    public double getElementalMass() {
        if (neutrons != 0) {
            return e.getIsotopes().getMass(getNeutronCount());
        }
        return e.getMass() + (neutrons * 1.01 * 1.66f * Math.pow(10, -27));
    }

    @Override
    public float getBitMass() {
        if (neutrons != 0) {
            return e.getIsotopes().getBitMass(getNeutronCount());
        }
        return e.getBitMass();
    }

    @Override
    public int getNeutronCount() {return e.getNeutrons() + neutrons;}

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (!Screen.hasShiftDown()) {return;}

        int dangerCount = e.getInfo().getDangerCount();

        if (Screen.hasControlDown()) {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element", e.getElement()).withStyle(ChatFormatting.GOLD));
            if (itemStack.is(POMtags.Items.ATOM512)) {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons.512", e.getElement(), e.getElement()*8).withStyle(ChatFormatting.BLUE));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons.512", getNeutronCount(), getNeutronCount()*8).withStyle(ChatFormatting.RED));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons.512", e.getElement(), e.getElement()*8).withStyle(ChatFormatting.YELLOW));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons", e.getElement()).withStyle(ChatFormatting.BLUE));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons", getNeutronCount()).withStyle(ChatFormatting.RED));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons", e.getElement()).withStyle(ChatFormatting.YELLOW));
            }
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state." + e.getState() + ".text"));
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
            if (dangerCount == 0 && !e.getIsotopes().isRadioactive(getNeutronCount()))
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none.text"));
            else {
                for (int d = 0; d < dangerCount; d++)
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger." + e.getInfo().getDangerName(d) + ".text"));
                if (e.getIsotopes().isRadioactive(getNeutronCount())) {
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.radioactive.text"));
                }
            }
        } else {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element",e.getElement()).withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state."+e.getState()));
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
            if (dangerCount == 0 && !e.getIsotopes().isRadioactive(getNeutronCount()))
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none"));
            else {
                for (int d = 0; d < dangerCount; d++)
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger." + e.getInfo().getDangerName(d)));
                if (e.getIsotopes().isRadioactive(getNeutronCount())) {
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.radioactive"));
                }
            }
        }
    }
}
