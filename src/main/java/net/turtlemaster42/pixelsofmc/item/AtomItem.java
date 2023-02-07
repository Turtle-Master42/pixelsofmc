package net.turtlemaster42.pixelsofmc.item;

import net.turtlemaster42.pixelsofmc.init.POMtabs;

public class AtomItem extends BaseItem {
    public AtomItem(Properties pProperties) {
        super(pProperties);
    }
    public AtomItem() {
        this(new Properties().tab(POMtabs.ATOM_TAB));
    }
}
