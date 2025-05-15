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
    final Element e;
    public ElementItem(Element e, Properties properties) {
        super(properties);
        this.e = e;
    }

    public Element getElement() {return e;}
    public double getElementalMass() {return e.getMass();}
    public float getBitMass() {return e.getBitMass();}
    public int getProtonCount() {return e.getElement();}
    public int getNeutronCount() {return e.getNeutrons();}
    public int getElectronCount() {return e.getElement();}

    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (!Screen.hasShiftDown()) {return;}

        int dangerAmount = e.getInfo().getDangerCount();

        if (Screen.hasControlDown()) {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.element", e.getElement()).withStyle(ChatFormatting.GOLD));
            if (itemStack.is(POMtags.Items.ATOM512)) {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons.512", getProtonCount(), getProtonCount()*8).withStyle(ChatFormatting.BLUE));
                if (!e.equals(Element.HYDROGEN))
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons.512", getNeutronCount(), getNeutronCount()*8).withStyle(ChatFormatting.RED));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons.512", getElectronCount(), getElectronCount()*8).withStyle(ChatFormatting.YELLOW));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.protons", getProtonCount()).withStyle(ChatFormatting.BLUE));
                if (!e.equals(Element.HYDROGEN))
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.neutrons", getNeutronCount()).withStyle(ChatFormatting.RED));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.electrons", getElectronCount()).withStyle(ChatFormatting.YELLOW));
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
