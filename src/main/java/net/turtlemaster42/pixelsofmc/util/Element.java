package net.turtlemaster42.pixelsofmc.util;

import java.util.Locale;

public enum Element {
    HYDROGEN(Type.CANISTER),
    HELIUM(Type.CANISTER),
    LITHIUM,
    BERYLLIUM,
    BORON(Type.CUBE, Property.FIRE, true, false),
    CARBON(Type.CUBE, true),
    NITROGEN(Type.CANISTER),
    OXYGEN(Type.CANISTER),
    FLUORINE(Type.CUBE),
    NEON(Type.CANISTER),
    SODIUM(true),
    MAGNESIUM,
    ALUMINIUM(true),
    SILICON(Type.CUBE, true),
    SULFUR(Type.CUBE, true),
    PHOSPHORUS(Type.CUBE),
    CHLORINE(Type.CANISTER),
    ARGON(Type.CANISTER),
    POTASSIUM(true),
    CALCIUM(true),
    SCANDIUM,
    TITANIUM(true, true),
    VANADIUM(Property.FIRE),
    CHROMIUM,
    MANGANESE,
    IRON(Type.VANILLA, true),
    COBALT(true),
    NICKEL(true),
    COPPER(Type.VANILLA, true, true),
    ZINC(true),
    GALLIUM,
    GERMANIUM,
    ARSENIC,

    BROMINE(Type.CANISTER),
    KRYPTON(Type.CANISTER),
    RUBIDIUM,
    STRONTIUM,
    YTTRIUM,
    ZIRCONIUM,
    NIOBIUM(Property.FIRE),
    MOLYBDENUM(Type.INGOT, Property.FIRE, true, false),
    TECHNETIUM(Property.FIRE),
    RUTHENIUM(Property.FIRE),
    RHODIUM,
    PALLADIUM,
    SILVER(Type.INGOT, false, true),
    CADMIUM,
    INDIUM,
    TIN(true),
    ANTIMONY,
    SELENIUM,
    TELLURIUM,
    IODINE(Type.CUBE),
    XENON(Type.CANISTER),
    CAESIUM,
    BARIUM,
    LANTHANUM,
    CERIUM,
    PRASEODYMIUM,
    NEODYMIUM,
    PROMETHIUM,
    SAMARIUM,
    EUROPIUM,
    GADOLINIUM,
    TERBIUM,
    DYSPROSIUM,
    HOLMIUM,
    ERBIUM,
    THULIUM,
    YTTERBIUM,
    LUTETIUM,
    HAFNIUM(Property.FIRE),
    TANTALUM(Property.FIRE),
    TUNGSTEN(Type.INGOT, Property.FIRE, true, false),
    RHENIUM(Property.FIRE),
    OSMIUM(Property.FIRE),
    IRIDIUM(Property.FIRE),
    PLATINUM(true),
    GOLD(Type.VANILLA,true, false),
    MERCURY,
    THALLIUM,
    LEAD(true),
    BISMUTH,
    POLONIUM,
    ASTATINE(Type.CUBE),
    RADON(Type.CANISTER),
    FRANCIUM,
    RADIUM,
    ACTINIUM,
    THORIUM,
    PROTACTINIUM,
    URANIUM(true),
    NEPTUNIUM,
    PLUTONIUM,
    AMERICIUM,
    CURIUM,
    BERKELIUM,
    CALIFORNIUM,
    EINSTEINIUM,
    ;

    private final Boolean dust;
    private final Boolean nugget;
    private final Type type;
    private final Property property;
    Element() {
        this.dust = false;
        this.nugget = false;
        this.type = Type.INGOT;
        this.property = Property.NONE;
    };
    Element(boolean dust) {
        this.dust = dust;
        this.nugget = false;
        this.type = Type.INGOT;
        this.property = Property.NONE;
    }
    Element(Type type) {
        this.dust = false;
        this.nugget = false;
        this.type = type;
        this.property = Property.NONE;
    }
    Element(Property property) {
        this.dust = false;
        this.nugget = false;
        this.type = Type.INGOT;
        this.property = property;
    }
    Element(Type type, Property property) {
        this.dust = false;
        this.nugget = false;
        this.type = type;
        this.property = property;
    }
    Element(Type type, boolean dust) {
        this.dust = dust;
        this.nugget = false;
        this.type = type;
        this.property = Property.NONE;
    }
    Element(boolean dust, boolean nugget) {
        this.dust = dust;
        this.nugget = nugget;
        this.type = Type.INGOT;
        this.property = Property.NONE;
    }
    Element(Type type, boolean dust, boolean nugget) {
        this.dust = dust;
        this.nugget = nugget;
        this.type = type;
        this.property = Property.NONE;
    }
    Element(Type type, Property property, boolean dust, boolean nugget) {
        this.dust = dust;
        this.nugget = nugget;
        this.type = type;
        this.property = property;
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
    public Property getProperty() {
        return property;
    }

    public boolean isMetal() {
        return getType()==Type.INGOT;
    }
    public boolean isVanilla() {
        return getType()==Type.VANILLA;
    }
    public boolean isFireResistant() {return getProperty()==Property.FIRE;}

    private enum Type {
        INGOT,
        CANISTER,
        CUBE,
        VANILLA
    }
    private enum Property {
        NONE,
        FIRE
    }
}
