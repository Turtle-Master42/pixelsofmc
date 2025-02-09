package net.turtlemaster42.pixelsofmc.item;

import net.turtlemaster42.pixelsofmc.util.Element;

public class AtomItem extends IsotopeItem {
    public AtomItem(Element e, Properties pProperties) {
        super(e, 0, pProperties);
    }
    public AtomItem(Element e) {
        super(e, 0, new Properties());
    }

    public AtomItem(Element e, int addNeutrons, Properties pProperties) {
        super(e, addNeutrons, pProperties);
    }
    public AtomItem(Element e, int addNeutrons) {
        super(e, addNeutrons, new Properties());
    }
}
