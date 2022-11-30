package net.turtlemaster42.pixelsofmc.util;

import java.util.Locale;

public enum Element {
    HYDROGEN(Type.CANISTER),
    HELIUM(Type.CANISTER),
    LITHIUM,
    BERYLLIUM,
    BORON(true, Type.CUBE),
    CARBON(true, Type.CUBE),
    NITROGEN(Type.CANISTER),
    OXYGEN(Type.CANISTER),
    FLUORINE(Type.CUBE),
    NEON(Type.CANISTER),
    SODIUM(true),
    MAGNESIUM,
    ALUMINIUM(true),
    SILICON(true, Type.CUBE),
    SULFUR(true, Type.CUBE),
    PHOSPHORUS(Type.CUBE),
    CHLORINE(Type.CANISTER),
    ARGON(Type.CANISTER),
    POTASSIUM(true),
    CALCIUM(true),
    SCANDIUM,
    TITANIUM(true, true),
    VANADIUM,
    CHROMIUM,
    MANGANESE,
    IRON(true, Type.VANILLA),
    COBALT,
    NICKEL,
    COPPER(Type.VANILLA, true, true),
    ZINC,
    GALLIUM,
    GERMANIUM,
    ARSENIC,

    BROMINE(Type.CANISTER),
    KRYPTON(Type.CANISTER),
    RUBIDIUM,
    STRONTIUM,
    YTTRIUM,
    ZIRCONIUM,
    NIOBIUM,
    MOLYBDENUM,
    TECHNETIUM,
    RUTHENIUM,
    RHODIUM,
    PALLADIUM,
    SILVER(Type.INGOT, true),
    CADMIUM,
    INDIUM,
    TIN,
    ANTIMONY,

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
    HAFNIUM,
    TANTALUM,
    TUNGSTEN,
    RHENIUM,
    OSMIUM,
    IRIDIUM,
    PLATINUM,
    GOLD(true, Type.VANILLA),
    MERCURY,
    THALLIUM,
    LEAD,
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
    Element(boolean dust, boolean nugget) {
        this.dust = dust;
        this.nugget = nugget;
        this.type = Type.INGOT;
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
    public boolean isVanilla() {
        return getType()==Type.VANILLA;
    }

    private enum Type
    {
        INGOT,
        CANISTER,
        CUBE,
        VANILLA
    }
}
