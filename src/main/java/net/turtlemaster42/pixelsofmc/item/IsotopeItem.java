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
    public IsotopeItem(Element e, int neutrons) {
        this(e, neutrons, new Properties());
    }

    @Override
    public double getElementalMass() {
        return e.getMass() + (neutrons * 1.01 * 1.66f * Math.pow(10, -27));
    }

    @Override
    public float getBitMass() {return e.getBitMass() + neutrons * 1.01f;}

    public int getProtonCount() {return e.getElement();}
    public int getNeutronCount() {
        if (e.equals(Element.HYDROGEN))
            return neutrons;
        return e.getElement() + neutrons;
    }
    public int getElectronCount() {return e.getElement();}

    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (Screen.hasShiftDown()) {

            int dangerAmount = e.getInfo().getDangerAmount();

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
                if (dangerAmount == 0)
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none.text"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)+".text"));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element",e.getElement()).withStyle(ChatFormatting.GOLD));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state."+e.getState()));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
                if (dangerAmount == 0)
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)));


            }
        }
    }
}
