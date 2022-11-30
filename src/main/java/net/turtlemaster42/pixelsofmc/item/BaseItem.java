package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.Item;
import net.turtlemaster42.pixelsofmc.init.POMtabs;

public class BaseItem extends Item {
    public BaseItem(Properties pProperties) {
        super(pProperties);
    }
    public BaseItem()
    {
        this(new Properties().tab(POMtabs.PIXELS_OF_MINECRAFT_TAB));
    }
}
