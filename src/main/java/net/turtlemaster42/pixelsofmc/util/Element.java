package net.turtlemaster42.pixelsofmc.util;

import java.util.Locale;

public enum Element {
    UNOPTAINIUM,
    NONIUM;

    private final Boolean dust;
    private final Boolean nugget;
    private final Type type;
    Element() {
        this.dust = false;
        this.nugget = false;
        this.type = Type.INGOT;
    };

    Element(boolean dust) {
        this.dust = dust;
        this.nugget = false;
        this.type = Type.INGOT;
    }

    Element(Type type) {
        this.dust = false;
        this.nugget = false;
        this.type = type;
    }

    Element(Type type, boolean nugget) {
        this.dust = false;
        this.nugget = nugget;
        this.type = type;
    }

    Element(boolean dust, Type type) {
        this.dust = dust;
        this.nugget = false;
        this.type = type;
    }

    Element(Type type, boolean dust, boolean nugget) {
        this.dust = dust;
        this.nugget = nugget;
        this.type = type;
    }

    public String tagName() {
        return name().toLowerCase(Locale.US);
    }

    public String typeName() {
        return type.toString().toLowerCase(Locale.US);
    }

    public Boolean shouldAddDust() {
        return dust;
    }

    public Boolean shouldAddNugget() {
        return nugget;
    }

    public Type getType() {
        return type;
    }

    public boolean isMetal() {
        return getType()==Type.INGOT;
    }

    private enum Type
    {
        INGOT,
        CANISTER,
        COMPOUND,
        CUBE
    }
}
