package net.turtlemaster42.pixelsofmc.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Predicate;

public enum Element {
    HYDROGEN(Type.CANISTER, new Info(14, 20, Danger.FLAMMABLE)), //pale blue flame
    HELIUM(Type.CANISTER, new Info(1, 4)),
    LITHIUM(new Info(454, 1603, Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    BERYLLIUM(new Info(1560, 2742, Danger.TOXIC)), //white flame
    BORON(Type.CUBE, true, false, false, new Info(2349, 4200)), //green flame
    CARBON(Type.CUBE, true, false, false, new Info(3915, 3915)), //orange flame
    NITROGEN(Type.CANISTER, new Info(63, 77)),
    OXYGEN(Type.CANISTER, new Info(54, 90)),
    FLUORINE(Type.CUBE, new Info(53, 85, Danger.CORROSIVE, Danger.TOXIC)), //canister
    NEON(Type.CANISTER, new Info(25, 27)),
    SODIUM(true, false, false, new Info(370, 1156, Danger.CORROSIVE, Danger.FLAMMABLE)), //yellow flame
    MAGNESIUM(new Info(923, 1363, Danger.FLAMMABLE)),
    ALUMINIUM(true, false, false, new Info(933, 1221)), //silver flame
    SILICON(Type.CUBE, true, false, false, new Info(1680, 3538)),
    PHOSPHORUS(Type.CUBE, new Info(317, 554, Danger.FLAMMABLE, Danger.TOXIC)), //blue-green flame
    SULFUR(Type.CUBE, true, false, false, new Info(388, 717, Danger.FLAMMABLE)),
    CHLORINE(Type.CANISTER, new Info(172, 238, Danger.TOXIC)),
    ARGON(Type.CANISTER, new Info(84, 87)),
    POTASSIUM(true, false, false, new Info(337, 1031, Danger.CORROSIVE, Danger.FLAMMABLE)), //purple flame
    CALCIUM(true, false, false, new Info(1115, 1757, Danger.FLAMMABLE)), //orange flame
    SCANDIUM(new Info(1814, 3109)), //orange
    TITANIUM(true, true, true, new Info(1941, 3560)), //silver flame
    VANADIUM(new Info(2183, 3680, Danger.TOXIC)), //yellow-green flame
    CHROMIUM(new Info(2180, 2944)), //silver flame
    MANGANESE(new Info(1519, 2334, Danger.TOXIC)), //yellow-green flame
    IRON(Type.VANILLA, true, false, false, new Info(1811, 3134)), //orange flame
    COBALT(true, false, false, new Info(1768, 3200)), //silver flame
    NICKEL(true, false, false, new Info(1728, 3003)), //silver flame
    COPPER(Type.VANILLA, true, true, false, new Info(1358,2835)), //blue-green flame
    ZINC(true, false, false, new Info(693,1180)), //white flame
    GALLIUM(new Info(303, 2673, Danger.CORROSIVE)),
    GERMANIUM(new Info(1211, 3106)), //pale blue flame
    ARSENIC(new Info(887, 887, Danger.TOXIC, Danger.CORROSIVE)), //dark blue flame
    SELENIUM(new Info(494, 958)), //blue flame
    BROMINE(Type.CANISTER, new Info(266, 332, Danger.TOXIC, Danger.CORROSIVE)),
    KRYPTON(Type.CANISTER, new Info(116, 120)),
    RUBIDIUM(new Info(312, 961, Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    STRONTIUM(new Info(1050, 1650, Danger.FLAMMABLE)), //red flame
    YTTRIUM(new Info(1799, 3203, Danger.TOXIC)), //red flame
    ZIRCONIUM(new Info(2125, 4650)), //red flame
    NIOBIUM(new Info(2750, 5017)), //blue-green flame
    MOLYBDENUM(Type.INGOT, true, false, false, new Info(2896, 4912, Danger.TOXIC)), //yellow-green flame
    TECHNETIUM(new Info(2430, 4538, Danger.RADIOACTIVE)),
    RUTHENIUM(new Info(2607, 4423)),
    RHODIUM(new Info(2237, 3968)),
    PALLADIUM(new Info(1828, 3236, Danger.TOXIC)),
    SILVER(Type.INGOT, true, true, true, new Info(1235, 2435)),
    CADMIUM(new Info(594, 1040, Danger.TOXIC)), //red flame
    INDIUM(new Info(430, 2345)), //dark blue flame
    TIN(true, false, false, new Info(506, 2875)), //light-blue flame
    ANTIMONY(new Info(904, 1908, Danger.TOXIC)), //green flame
    TELLURIUM(new Info(723, 1261, Danger.TOXIC)), //green flame
    IODINE(Type.CUBE, new Info(387, 457, Danger.TOXIC)),
    XENON(Type.CANISTER, new Info(161, 165)),
    CAESIUM(new Info(302, 944, Danger.FLAMMABLE, Danger.CORROSIVE)), //violet flame
    BARIUM(new Info(1000, 2118, Danger.FLAMMABLE, Danger.CORROSIVE, Danger.TOXIC)), //green flame
    LANTHANUM(new Info(1193, 3737, Danger.FLAMMABLE)),
    CERIUM(new Info(1068, 3716, Danger.FLAMMABLE)),
    PRASEODYMIUM(new Info(1208, 3403, Danger.FLAMMABLE)),
    NEODYMIUM(new Info(1297, 3347)),
    PROMETHIUM(new Info(1315, 3273, Danger.RADIOACTIVE)),
    SAMARIUM(new Info(1345,2173, Danger.FLAMMABLE)),
    EUROPIUM(new Info(1099, 1802, Danger.FLAMMABLE)),
    GADOLINIUM(new Info(1585, 3273, Danger.FLAMMABLE)),
    TERBIUM(new Info(1629, 3396)),
    DYSPROSIUM(new Info(1680, 2840, Danger.FLAMMABLE)),
    HOLMIUM(new Info(1734, 2873)),
    ERBIUM(new Info(1802, 3141)),
    THULIUM(new Info(1818, 2223)),
    YTTERBIUM(new Info(1097, 1469, Danger.TOXIC, Danger.FLAMMABLE)),
    LUTETIUM(new Info(1925, 3675)),
    HAFNIUM(new Info(2506, 4876, Danger.FLAMMABLE)), //white flame
    TANTALUM(new Info(3290, 5731)), //blue flame
    TUNGSTEN(Type.INGOT, true, true, false, new Info(3695, 6203)), //green
    RHENIUM(new Info(3459, 5903)),
    OSMIUM(new Info(3306, 5285)),
    IRIDIUM(new Info(2719, 4403)),
    PLATINUM(true, false, false, new Info(2041, 4098)),
    GOLD(Type.VANILLA,true, false, false, new Info(1337, 3243)),
    MERCURY(new Info(234, 630, Danger.TOXIC)), //red flame
    THALLIUM(new Info(577, 1746, Danger.TOXIC)), //pure green flame
    LEAD(true, false, false, new Info(601, 2022, Danger.TOXIC)), //light-blue flame
    BISMUTH(new Info(545, 1837, Danger.RADIOACTIVE)), //light blue flame
    POLONIUM(new Info(527, 1235, Danger.TOXIC, Danger.RADIOACTIVE)),
    ASTATINE(Type.CUBE, new Info( 0, 0, Danger.RADIOACTIVE)),
    RADON(Type.CANISTER, new Info(202, 212, Danger.RADIOACTIVE)),
    FRANCIUM(new Info(300, 950, Danger.RADIOACTIVE)),
    RADIUM(new Info(973, 2010, Danger.RADIOACTIVE)), //red flame
    ACTINIUM(new Info(1500, 3500, Danger.RADIOACTIVE)),
    THORIUM(new Info(2023, 5061, Danger.RADIOACTIVE)),
    PROTACTINIUM(new Info(1841, 4300, Danger.RADIOACTIVE)),
    URANIUM(true, false, false, new Info(1405, 4404, Danger.RADIOACTIVE)),
    NEPTUNIUM(new Info(912, 4447, Danger.RADIOACTIVE)),
    PLUTONIUM(new Info(913, 3505, Danger.RADIOACTIVE)),
    AMERICIUM(new Info(1449, 2880, Danger.RADIOACTIVE)),
    CURIUM(new Info(1613, 3363, Danger.RADIOACTIVE)),
    BERKELIUM(new Info(1251, 2900, Danger.RADIOACTIVE)),
    CALIFORNIUM(new Info(1173, 1743, Danger.RADIOACTIVE)),
    EINSTEINIUM(new Info(1133, 1269, Danger.RADIOACTIVE)),

    DEBUGIUM(new Info(10000, 1000000, Danger.RADIOACTIVE, Danger.TOXIC, Danger.FLAMMABLE, Danger.CORROSIVE)),
    ;

    private final Boolean dust;
    private final Info info;
    private final Boolean nugget;
    private final Boolean block;
    private final Type type;


    Element(Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = Type.INGOT;
    }
    Element(Type type, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = type;
    }
    Element(boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = Type.INGOT;
    }
    Element(Type type, boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
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
    public Boolean shouldAddBlock() {return block;}

    public Type getType() {
        return type;
    }
    public String getState() {
        String state = "solid";
        if (info.getMeltingPoint() < 273) state = "liquid";
        if (info.getEvaporatingPoint() < 273) state = "gas";
        return state;
    }
    public boolean isMetal() {
        return getType()==Type.INGOT;
    }
    public boolean isVanilla() {
        return getType()==Type.VANILLA;
    }
    public boolean isFireResistant() {return this.info.isFireProof();}

    public ItemLike item() {return POMitems.Metals.ELEMENTS.get(this);}
    public ItemLike atom64() {return POMitems.Metals.ATOMX64.get(this);}
    public ItemLike atom512() {return POMitems.Metals.ATOMX512.get(this);}
    public Block block() {return POMblocks.Metals.BLOCKS.get(this).get();}
    public ItemLike blockItem() {return POMblocks.Metals.BLOCKS.get(this).asItem();}
    public ItemLike nugget() {
        if (this.shouldAddNugget())
            return POMitems.Metals.NUGGETS.get(this);
        return Items.AIR;
    }
    public ItemLike dust() {
        if (this.shouldAddDust())
            return POMitems.Metals.DUSTS.get(this);
        return Items.AIR;
    }

    public TagKey<Item> itemTag() {
        if (this.isMetal())
            return POMtags.getTagsFor(this).metal;
        return POMtags.getTagsFor(this).other;
    }
    public TagKey<Item> nuggetTag() {
        return POMtags.getTagsFor(this).nugget;
    }
    public TagKey<Item> dustTag() {
        return POMtags.getTagsFor(this).dust;
    }

    public Info getInfo() {
        return this.info;
    }

    private enum Type {
        INGOT,
        CANISTER,
        CUBE,
        VANILLA
    }

    private enum Danger {
        RADIOACTIVE,
        TOXIC,
        CORROSIVE,
        FLAMMABLE,
        NONE
    }

    public static class Info {
        private int meltingPoint;
        private int evaporatingPoint;
        Danger[] danger;

        Info(int meltingPoint, int evaporatingPoint, Danger... danger) {
            this.meltingPoint = meltingPoint;
            this.evaporatingPoint = evaporatingPoint;
            this.danger = danger;
        }

        public int getMeltingPoint() {return meltingPoint;}
        public int getEvaporatingPoint() {return evaporatingPoint;}
        public boolean isFireProof() {return meltingPoint >= 1973;}
        public int getDangerAmount() {return danger.length;}
        public String getDangerName(int count) {return danger[count].name().toLowerCase();}

        public boolean isRadioActive() {
            for (Danger d : danger) {
                if (d.equals(Danger.RADIOACTIVE))
                    return true;
            }
            return false;
        }
        public boolean isToxic() {
            for (Danger d : danger) {
                if (d.equals(Danger.TOXIC))
                    return true;
            }
            return false;
        }
        public boolean isCorrosive() {
            for (Danger d : danger) {
                if (d.equals(Danger.CORROSIVE))
                    return true;
            }
            return false;
        }
        public boolean isFlammable() {
            for (Danger d : danger) {
                if (d.equals(Danger.FLAMMABLE))
                    return true;
            }
            return false;
        }
    }
}
