package net.turtlemaster42.pixelsofmc.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.PixelItem;

import java.awt.*;
import java.util.Locale;

public enum Element {
    HYDROGEN(Type.CANISTER, new Info(14, 20, "F3F1FE", "CCCAD5", "76757C", Danger.FLAMMABLE)), //pale blue flame
    HELIUM(Type.CANISTER, new Info(1, 4, "FFB36A", "A86636", "5B3112")),
    LITHIUM(new Info(454, 1603, "A7C09E", "5A6355", "2F352E", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    BERYLLIUM(new Info(1560, 2742, "B4C79D", "779355", "3B482A", Danger.TOXIC)), //white flame
    BORON(Type.CUBE, true, false, false, new Info(2349, 4200, "3F3C4C", "262430", "1B1A1F")), //green flame
    CARBON(Type.CUBE, true, false, false, new Info(3915, 3915, "525253", "363637", "1F1F20")), //orange flame
    NITROGEN(Type.CANISTER, new Info(63, 77, "B097FF", "7162AF", "362E57")),
    OXYGEN(Type.CANISTER, new Info(54, 90, "77ABF0", "4B6F9D", "22354D")),
    FLUORINE(Type.CUBE, new Info(53, 85, "F0DCFF", "979EBB", "575B6B", Danger.CORROSIVE, Danger.TOXIC)), //canister
    NEON(Type.CANISTER, new Info(25, 27, "FF826A", "B35342", "59261E")),
    SODIUM(true, false, false, new Info(370, 1156, "F7E878", "C8AF49", "705D1F", Danger.CORROSIVE, Danger.FLAMMABLE)), //yellow flame
    MAGNESIUM(new Info(923, 1363, Danger.FLAMMABLE)),
    ALUMINIUM(true, false, false, new Info(933, 1221, "D1DAE0", "889EA3", "424D51")), //silver flame
    SILICON(Type.CUBE, true, false, false, new Info(1680, 3538, "9DA7B8", "71687F", "3C3A44")),
    PHOSPHORUS(Type.CUBE, new Info(317, 554, "A8403A", "7E2D29", "4A1715", Danger.FLAMMABLE, Danger.TOXIC)), //blue-green flame
    SULFUR(Type.CUBE, true, false, false, new Info(388, 717, "DCB202", "9E6612", "5A2C08", Danger.FLAMMABLE)),
    CHLORINE(Type.CANISTER, new Info(172, 238, "94B060", "5F723C", "2C3619", Danger.TOXIC)),
    ARGON(Type.CANISTER, new Info(84, 87, "C395D8", "7F608D", "3D2D45")),
    POTASSIUM(true, false, false, new Info(337, 1031, "C08CCF", "723F9E", "391F4D", Danger.CORROSIVE, Danger.FLAMMABLE)), //purple flame
    CALCIUM(true, false, false, new Info(1115, 1757, "DCD6CC", "C4B69C", "695B3F", Danger.FLAMMABLE)), //orange flame
    SCANDIUM(new Info(1814, 3109, "D0BA8B", "A08241", "5E4C26")), //orange
    TITANIUM(true, true, true, new Info(1941, 3560, "A69FAE", "7E748A", "43414E")), //silver flame
    VANADIUM(new Info(2183, 3680, "DC817E", "AB3735", "65201F", Danger.TOXIC)), //yellow-green flame
    CHROMIUM(new Info(2180, 2944, "95D2B7", "45A666", "225133")), //silver flame
    MANGANESE(new Info(1519, 2334, "8A6547", "5C422D", "312318", Danger.TOXIC)), //yellow-green flame
    IRON(Type.VANILLA, true, false, false, new Info(1811, 3134, "D8D8D8", "828282", "5E5E5E")), //orange flame
    COBALT(true, false, false, new Info(1768, 3200, "88A9DC", "3466B4", "1A3258")), //silver flame
    NICKEL(true, false, false, new Info(1728, 3003, "DCA185", "A55E42", "522E1F")), //silver flame
    COPPER(Type.VANILLA, true, true, false, new Info(1358,2835, "E77C56", "C15A36", "8A4129")), //blue-green flame
    ZINC(true, false, false, new Info(693,1180, "97BBA6", "627A6C", "2F3C35")), //white flame
    GALLIUM(new Info(303, 2673, "9A9AB0", "595C79", "2A2C3A", Danger.CORROSIVE)),
    GERMANIUM(new Info(1211, 3106, "D3AC85", "8A7056", "453729")), //pale blue flame
    ARSENIC(new Info(887, 887, "82BFB3", "547D74", "273E39", Danger.TOXIC, Danger.CORROSIVE)), //dark blue flame
    SELENIUM(new Info(494, 958, "F85F5A", "EF3636", "751E1F")), //blue flame
    BROMINE(Type.CANISTER, new Info(266, 332, "DA92A3", "8E5E69", "452B32", Danger.TOXIC, Danger.CORROSIVE)),
    KRYPTON(Type.CANISTER, new Info(116, 120, "72B0BB", "497279", "21373B")),
    RUBIDIUM(new Info(312, 961, "FA7F51", "B54A25", "5A2311", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    STRONTIUM(new Info(1050, 1650, "B59B83", "866443", "423222", Danger.FLAMMABLE)), //red flame
    YTTRIUM(new Info(1799, 3203, "599E78", "31704B", "1A3B29", Danger.TOXIC)), //red flame
    ZIRCONIUM(new Info(2125, 4650, "A797D8", "6C5D95", "352E49")), //red flame
    NIOBIUM(new Info(2750, 5017, "697EC3", "3A3C8D", "1B1E45")), //blue-green flame
    MOLYBDENUM(Type.INGOT, true, false, false, new Info(2896, 4912, "585D76", "3A4056", "232733", Danger.TOXIC)), //yellow-green flame
    TECHNETIUM(new Info(2430, 4538, "CFA3ED", "9D55B3", "4D2A59", Danger.RADIOACTIVE)),
    RUTHENIUM(new Info(2607, 4423, "E7829C", "BC2D53", "5D1629")),
    RHODIUM(new Info(2237, 3968, "C696B1", "935076", "49283A")),
    PALLADIUM(new Info(1828, 3236, "BBDA9E", "89B047", "425623", Danger.TOXIC)),
    SILVER(Type.INGOT, true, true, true, new Info(1235, 2435, "B4C1C7", "869CA6", "40505C")),
    CADMIUM(new Info(594, 1040, "F0BE70", "C08B22", "604411", Danger.TOXIC)), //red flame
    INDIUM(new Info(430, 2345, "8175CE", "493D94", "29224F")), //dark blue flame
    TIN(true, false, false, new Info(506, 2875, "CEAE84", "877155", "433728a")), //light-blue flame
    ANTIMONY(new Info(904, 1908, "8ED6B5", "3EAA78", "1E543C", Danger.TOXIC)), //green flame
    TELLURIUM(new Info(723, 1261, "E7EBF1", "AEB4BB", "55595F", Danger.TOXIC)), //green flame
    IODINE(Type.CUBE, new Info(387, 457, "FF85FD", "B854A3", "5C2651", Danger.TOXIC)),
    XENON(Type.CANISTER, new Info(161, 165, "6C87CF", "3B4D77", "253148")),
    CAESIUM(new Info(302, 944, "FFCA7D", "D89B2F", "734F17", Danger.FLAMMABLE, Danger.CORROSIVE)), //violet flame
    BARIUM(new Info(1000, 2118, "8BAFDE", "3C6EAE", "1E3656", Danger.FLAMMABLE, Danger.CORROSIVE, Danger.TOXIC)), //green flame
    LANTHANUM(new Info(1193, 3737, "5C885C", "346135", "1B321B", Danger.FLAMMABLE)),
    CERIUM(new Info(1068, 3716, "D688AD", "A3407C", "50203C", Danger.FLAMMABLE)),
    PRASEODYMIUM(new Info(1208, 3403, "B0CF78", "66A331", "335017", Danger.FLAMMABLE)),
    NEODYMIUM(new Info(1297, 3347, "95C7BD", "479288", "234843")),
    PROMETHIUM(new Info(1315, 3273, "7ADC94", "2FB051", "175628", Danger.RADIOACTIVE)),
    SAMARIUM(new Info(1345,2173, "CB9D89", "93634B", "483025", Danger.FLAMMABLE)),
    EUROPIUM(new Info(1099, 1802, "507F6E", "2D5144", "15251F", Danger.FLAMMABLE)),
    GADOLINIUM(new Info(1585, 3273, "DED062", "A6A31B", "52500D", Danger.FLAMMABLE)),
    TERBIUM(new Info(1629, 3396, "996953", "664532", "37261B")),
    DYSPROSIUM(new Info(1680, 2840, "4F4F54", "323235", "1A1A1B", Danger.FLAMMABLE)),
    HOLMIUM(new Info(1734, 2873, "CA89A4", "9B407B", "4D203C")),
    ERBIUM(new Info(1802, 3141, "E5C5DD", "BA6CA5", "643858")),
    THULIUM(new Info(1818, 2223)),
    YTTERBIUM(new Info(1097, 1469, Danger.TOXIC, Danger.FLAMMABLE)),
    LUTETIUM(new Info(1925, 3675)),
    HAFNIUM(new Info(2506, 4876, Danger.FLAMMABLE)), //white flame
    TANTALUM(new Info(3290, 5731)), //blue flame
    TUNGSTEN(Type.INGOT, true, true, true, new Info(3695, 6203)), //green
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


    public String elementName() {
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

    public ItemLike item() {
        return POMitems.Metals.ELEMENTS.get(this).get();
    }
    public ItemLike atom64() {return POMitems.Metals.ATOMX64.get(this).get();}
    public ItemLike atom512() {return POMitems.Metals.ATOMX512.get(this).get();}
    public Block block() {return POMblocks.Metals.BLOCKS.get(this).get();}
    public ItemLike blockItem() {
        if (this.shouldAddBlock())
            return POMblocks.Metals.BLOCKS.get(this).asItem();
        return Items.AIR;
    }
    public ItemLike nugget() {
        if (this.shouldAddNugget())
            return POMitems.Metals.NUGGETS.get(this).get();
        return Items.AIR;
    }
    public ItemLike dust() {
        if (this.shouldAddDust())
            return POMitems.Metals.DUSTS.get(this).get();
        return Items.AIR;
    }
    public ItemStack pixel() {
//        int dangerAmount = this.info.getDangerAmount();
//        String[] extra = new String[dangerAmount+1];
//        extra[0] = "tooltip.pixelsofmc.danger";
//        if (dangerAmount != 0)
//            for (int i = 1; i < dangerAmount; i++) {
//                extra[i] = "tooltip.pixelsofmc.danger."+this.info.getDangerName(i);
//            }
//        else extra[1] = "tooltip.pixelsofmc.danger.none";

        return PixelItem.createForPixel(POMitems.PIXEL.get().getDefaultInstance(),
                new Color(this.hexToRGB(0)[0], this.hexToRGB(0)[1], this.hexToRGB(0)[2]).getRGB(),
                new Color(this.hexToRGB(1)[0], this.hexToRGB(1)[1], this.hexToRGB(1)[2]).getRGB(),
                new Color(this.hexToRGB(2)[0], this.hexToRGB(2)[1], this.hexToRGB(2)[2]).getRGB(),
                "element.pixelsofmc."+this.elementName());
    }
    public ItemStack pixelPile() {
//        int dangerAmount = this.info.getDangerAmount();
//        String[] extra = new String[dangerAmount+1];
//        extra[0] = "tooltip.pixelsofmc.danger";
//        if (dangerAmount != 0)
//            for (int i = 1; i < dangerAmount; i++) {
//                extra[i] = "tooltip.pixelsofmc.danger."+this.info.getDangerName(i);
//            }
//        else extra[1] = "tooltip.pixelsofmc.danger.none";

        return PixelItem.createForPixel(POMitems.PIXEL_PILE.get().getDefaultInstance(),
                new Color(this.hexToRGB(0)[0], this.hexToRGB(0)[1], this.hexToRGB(0)[2]).getRGB(),
                new Color(this.hexToRGB(1)[0], this.hexToRGB(1)[1], this.hexToRGB(1)[2]).getRGB(),
                new Color(this.hexToRGB(2)[0], this.hexToRGB(2)[1], this.hexToRGB(2)[2]).getRGB(),
                "element.pixelsofmc."+this.elementName());
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
    public int getElement() {return this.ordinal()+1;}

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
        private final int meltingPoint;
        private final int evaporatingPoint;
        private final String[] color;
        final Danger[] danger;


        Info(int meltingPoint, int evaporatingPoint, String color1, String color2, String color3, Danger... danger) {
            this.meltingPoint = meltingPoint;
            this.evaporatingPoint = evaporatingPoint;
            this.color = new String[]{color1, color2, color3};
            this.danger = danger;
        }

        Info(int meltingPoint, int evaporatingPoint, Danger... danger) {
            this.meltingPoint = meltingPoint;
            this.evaporatingPoint = evaporatingPoint;
            this.color = new String[]{"000000", "000000", "000000"};
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
    public int[] hexToRGB(int index) {
        return Util.hexToRGB(this.info.color[index]);
    }
}
