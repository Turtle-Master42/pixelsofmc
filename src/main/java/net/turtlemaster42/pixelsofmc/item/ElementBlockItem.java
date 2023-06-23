package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ElementBlockItem extends BlockItem {
    Element e;
    public ElementBlockItem(Element e, Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        this.e = e;
    }
    public ElementBlockItem(Element e, Block pBlock) {
        this(e, pBlock, new Properties());
        this.e = e;
    }

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {

            int dangerAmount = e.getInfo().getDangerAmount();

            if (Screen.hasControlDown()) {
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state."+e.getState()+".text"));
                pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
                if (dangerAmount == 0)
                    pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none.text"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)+".text"));
            } else {
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
