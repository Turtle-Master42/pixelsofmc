package net.turtlemaster42.pixelsofmc.item;

import net.turtlemaster42.pixelsofmc.util.Element;

public class AtomItem extends IsotopeItem {
    public AtomItem(Element e, Properties pProperties) {
        super(e, 0, pProperties);
    }

    public AtomItem(Element e, int extraNeutrons, Properties pProperties) {
        super(e, extraNeutrons, pProperties);
    }
    public AtomItem(Element e, int extraNeutrons) {
        super(e, extraNeutrons, new Properties());
    }
}
