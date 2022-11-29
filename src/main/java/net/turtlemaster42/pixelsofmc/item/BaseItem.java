package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.Item;

public class BaseItem extends Item {
    public BaseItem(Properties pProperties) {
        super(pProperties);
    }
    public BaseItem()
    {
        this(new Properties());
    }
}
