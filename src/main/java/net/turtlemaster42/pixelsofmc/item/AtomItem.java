package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.init.POMtabs;

import javax.annotation.Nullable;
import java.util.List;

public class AtomItem extends BaseItem {
    public AtomItem(Properties pProperties) {
        super(pProperties);
    }
    public AtomItem() {
        this(new Properties().tab(POMtabs.ATOM_TAB));
    }
}
