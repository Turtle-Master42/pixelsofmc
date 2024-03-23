package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ElementItem extends Item {
    Element e;
    public ElementItem(Element e, Properties pProperties) {
        super(pProperties);
        this.e = e;
    }
    public ElementItem(Element e) {
        this(e, new Properties());
        this.e = e;
    }

    public Element getElement() {return e;}
    public double getElementalMass() {return e.getMass();}
    public int getProtonCount() {return e.getElement();}
    public int getNeutronCount() {
        if (e.equals(Element.HYDROGEN))
            return 0;
        return e.getElement();
    }
    public int getElectronCount() {return e.getElement();}

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {

            int dangerAmount = e.getInfo().getDangerAmount();

            if (Screen.hasControlDown()) {
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element", e.getElement()).withStyle(ChatFormatting.GOLD));
                if (pStack.is(POMtags.Items.ATOM512)) {
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons.512", e.getElement(), e.getElement()*8).withStyle(ChatFormatting.BLUE));
                    if (!e.equals(Element.HYDROGEN))
                        pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons.512", e.getElement(), e.getElement()*8).withStyle(ChatFormatting.RED));
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons.512", e.getElement(), e.getElement()*8).withStyle(ChatFormatting.YELLOW));
                } else {
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons", e.getElement()).withStyle(ChatFormatting.BLUE));
                    if (!e.equals(Element.HYDROGEN))
                        pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons", e.getElement()).withStyle(ChatFormatting.RED));
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons", e.getElement()).withStyle(ChatFormatting.YELLOW));
                }
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state." + e.getState() + ".text"));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
                if (dangerAmount == 0)
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none.text"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)+".text"));
            } else {
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element",e.getElement()).withStyle(ChatFormatting.GOLD));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state."+e.getState()));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
                if (dangerAmount == 0)
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)));


            }
        }
    }
}
