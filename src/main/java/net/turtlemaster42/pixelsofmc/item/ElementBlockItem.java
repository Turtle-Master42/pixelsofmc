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
    public ElementBlockItem(Element e, Block block, Properties properties) {
        super(block, properties);
        this.e = e;
    }
    public ElementBlockItem(Element e, Block pBlock) {
        this(e, pBlock, new Properties());
        this.e = e;
    }

    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (Screen.hasShiftDown()) {

            int dangerAmount = e.getInfo().getDangerAmount();

            if (Screen.hasControlDown()) {
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state"));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.state."+e.getState()+".text"));
                tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger"));
                if (dangerAmount == 0)
                    tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger.none.text"));
                else
                    for (int d = 0; d < dangerAmount; d++)
                        tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.danger."+e.getInfo().getDangerName(d)+".text"));
            } else {
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
