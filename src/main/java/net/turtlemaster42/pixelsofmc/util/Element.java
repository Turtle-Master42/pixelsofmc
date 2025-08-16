package net.turtlemaster42.pixelsofmc.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.PixelItem;

import java.awt.*;
import java.util.Locale;

public enum Element {
    HYDROGEN(0, 1.01f, ItemType.CANISTER,
            new Isotopes(new int[]{1, 2}, new float[]{2.01f, 3.02f}, new boolean[]{false, true}),
            new Info(15, 20, "FFFCFF", "DEDCE8", "79737F", Danger.FLAMMABLE)), //pale blue flame
    HELIUM(2, 4f, ItemType.CANISTER,
            new Isotopes(1, 3.01f),
            new Info(1, 5, "FFC977", "FFAA61", "794620")),
    LITHIUM(4, 7f, true, false, false,
            new Isotopes(3, 6.02f),
            new Info(450, 1600, "A5BD9C", "5C6557", "323831", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    BERYLLIUM(5, 9.01f,
            new Isotopes(6, 10.01f, true),
            new Info(1550, 2750, "B9D099", "7FA354", "43582D", Danger.TOXIC)), //white flame
    BORON(6, 10.81f, ItemType.CUBE, true, false, false,
            new Isotopes(5, 10.01f),
            new Info(2350, 4200, "4F4F62", "363647", "22222E")), //green flame
    CARBON(6, 12.01f, ItemType.CUBE, true, false, false,
            new Isotopes(new int[]{7, 8}, new float[]{13f, 14f}, new boolean[]{false, true}),
            new Info(3900, 3900, "54545D", "3B3B42", "222227")), //orange flame
    NITROGEN(7, 14.01f, ItemType.CANISTER,
            new Isotopes(8, 15f),
            new Info(60, 80, "AD95FF", "7264AC", "3A325A")),
    OXYGEN(8, 16f, ItemType.CANISTER,
            new Isotopes(new int[]{9, 10}, new float[]{17f, 18f}, new boolean[]{false, true}),
            new Info(55, 90, "7DB0F8", "4F719D", "263950")),
    FLUORINE(10, 19f, ItemType.CUBE,
            new Info(50, 85, "DBCAE7", "8787A3", "3C3A53", Danger.CORROSIVE, Danger.TOXIC)), //canister
    NEON(10, 20.18f, ItemType.CANISTER,
            new Isotopes(new int[]{11, 12}, new float[]{20.99f, 21.99f}),
            new Info(25, 30, "FF8D73", "B75544", "6D2F25")),
    SODIUM(12, 22.99f, true, false, false,
            new Isotopes(11, 21.99f, true),
            new Info(370, 1150, "FEEF7C", "CBB24A", "70581F", Danger.CORROSIVE, Danger.FLAMMABLE)), //yellow flame
    MAGNESIUM(12, 24.31f, true, false, false,
            new Isotopes(new int[]{13, 14}, new float[]{24.99f, 25.98f}),
            new Info(900, 1350, "E7E4DF", "B3B0AB", "726E6A", Danger.FLAMMABLE)),
    ALUMINIUM(14, 26.98f, true, false, false,
            new Isotopes(13, 25.99f, true),
            new Info(950, 1200, "D4DCE2", "90A5A9", "43565E")), //silver flame
    SILICON(14, 28.09f, ItemType.CUBE, true, false, false,
            new Isotopes(new int[]{15, 16}, new float[]{28.98f, 29.97f}),
            new Info(1700, 3550, "9B90AC", "7D6D91", "463B55")),
    PHOSPHORUS(16, 30.97f, ItemType.CUBE,
            new Info(320, 550, "E15549", "7D002D", "4B002A", Danger.FLAMMABLE, Danger.TOXIC)), //blue-green flame
    SULFUR(16, 32.07f, ItemType.CUBE, true, false, false,
            new Isotopes(new int[]{17, 18}, new float[]{32.97f, 33.97f}),
            new Info(400, 700, "D7AC45", "B77732", "723B26", Danger.FLAMMABLE)),
    CHLORINE(18, 35.45f, ItemType.CANISTER,
            new Isotopes(new int[]{20}, new float[]{36.97f}, new boolean[]{false}),
            new Info(170, 240, "A4C764", "738B44", "394C1E", Danger.TOXIC)),
    ARGON(22, 39.9f, ItemType.CANISTER,
            new Isotopes(20, 36.96f),
            new Info(85, 90, "E0B9EE", "A482B2", "584362")),
    POTASSIUM(20, 39.09f, true, false, false,
            new Isotopes(22, 40.96f),
            new Info(350, 1050, "C380D6", "73439C", "372351", Danger.CORROSIVE, Danger.FLAMMABLE)), //purple flame
    CALCIUM(20, 40.08f, true, false, false,
            new Isotopes(24, 43.96f),
            new Info(1100, 1750, "E1DBD2", "CBBEA5", "746649", Danger.FLAMMABLE)), //orange flame
    SCANDIUM(24, 44.96f,
            new Info(1800, 3100, "EFCE79", "B08934", "563D19")), //orange
    TITANIUM(26, 47.87f, true, true, true,
            new Isotopes(new int[]{22, 24}, new float[]{43.96f, 45.95f}, new boolean[]{true, false}),
            new Info(1950, 3550, "A69FAE", "7E748A", "43414E")), //silver flame
    VANADIUM(28, 50.94f,
            new Isotopes(27, 49.95f, true),
            new Info(2200, 3700, "F5796D", "AB3735", "541A24", Danger.TOXIC)), //yellow-green flame
    CHROMIUM(28, 52f,
            new Isotopes(new int[]{29}, new float[]{52.94f}),
            new Info(2200, 2950, "81E4A5", "42B26A", "255F42")), //silver flame
    MANGANESE(30, 54.94f,
            new Isotopes(28, 52.94f, true),
            new Info(1500, 2350, "936B49", "5E4530", "33231A", Danger.TOXIC)), //yellow-green flame
    IRON(30, 55.84f, ItemType.VANILLA, true, false, false,
            new Isotopes(new int[]{29, 31}, new float[]{54.94f, 56.94f}, new boolean[]{true, false}),
            new Info(1800, 3150, "D8D8D8", "828282", "5E5E5E")), //orange flame
    COBALT(32, 58.93f, true, false, false,
            new Isotopes(33, 59.93f, true),
            new Info(1750, 3200, "87ABD3", "3D6AAD", "233A63")), //silver flame
    NICKEL(30, 58.69f, true, false, false,
            new Isotopes(new int[]{32, 34, 35}, new float[]{59.93f, 61.93f, 62.93f}, new boolean[]{false, false, true}),
            new Info(1750, 3000, "E7A081", "A56246", "562E23")), //silver flame
    COPPER(34, 63.55f, ItemType.VANILLA, true, true, false,
            new Isotopes(36, 64.93f, false),
            new Info(1350,2850, "E77C56", "C15A36", "8A4129")), //blue-green flame
    ZINC(34, 65.4f, true, false, false,
            new Isotopes(36, 65.93f),
            new Info(700,1200, "94CAAF", "5D816C", "274430")), //white flame
    GALLIUM(38, 69.72f,
            new Info(300, 2650, "9495AE", "595D7E", "302F42", Danger.CORROSIVE)),
    GERMANIUM(41, 72.63f,
            new Isotopes(40, 71.92f),
            new Info(1200, 3100, "E2B88F", "8C715A", "48352C")), //pale blue flame
    ARSENIC(42, 74.92f,
            new Info(900, 900, "45D7CE", "32A0B7", "265472", Danger.TOXIC, Danger.CORROSIVE)), //dark blue flame
    SELENIUM(44, 77.92f,
            new Info(500, 950, "DB7441", "BE4C2B", "762223")), //blue flame
    BROMINE(45, 79.9f, ItemType.CANISTER,
            new Isotopes(46, 80.91f),
            new Info(250, 350, "E890A2", "965A68", "4C2C38", Danger.TOXIC, Danger.CORROSIVE)),
    KRYPTON(48, 83.8f, ItemType.CANISTER,
            new Isotopes(new int[]{42, 46}, new float[]{77.92f, 81.91f}, new boolean[]{true, false}),
            new Info(110, 120, "7EB8C2", "52777D", "293F43")),
    RUBIDIUM(48, 85.47f,
            new Isotopes(50, 86.91f, true),
            new Info(310, 950, "FF8251", "B7451E", "5D1F14", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    STRONTIUM(50, 87.62f,
            new Isotopes(48, 85.91f, true),
            new Info(1050, 1650, "CCA078", "8E6842", "4A3524", Danger.FLAMMABLE)), //red flame
    YTTRIUM(50, 88.91f,
            new Info(1800, 3200, "4CB179", "2C764A", "173E29", Danger.TOXIC)), //red flame
    ZIRCONIUM(51, 91.22f,
            new Isotopes(new int[]{52, 53}, new float[]{91.91f, 92.91f}, new boolean[]{false, true}),
            new Info(2100, 4650, "AA96E4", "6C5C98", "352D4A")), //red flame
    NIOBIUM(52, 92.9f, true, false, false,
            new Isotopes(new int[]{51, 53}, new float[]{91.91f, 93.91f}, new boolean[]{true, true}),
            new Info(2750, 5000, "748AD2", "42448C", "23264C")), //blue-green flame
    MOLYBDENUM(54, 95.95f, ItemType.INGOT, true, false, false,
            new Isotopes(56, 97.91f),
            new Info(2900, 4900, "5E627B", "3F455A", "272C37", Danger.TOXIC)), //yellow-green flame
    TECHNETIUM(55, 96.91f,
            new Isotopes(55, 97.91f, true),
            new Info(2450, 4550, "D891FF", "A352B9", "602E66", Danger.RADIOACTIVE)),
    RUTHENIUM(57, 101.1f,
            new Isotopes(new int[]{55, 58}, new float[]{97.91f, 101.9f}),
            new Info(2600, 4400, "FF688F", "C02B53", "641628")),
    RHODIUM(58, 102.91f,
            new Info(2250, 4000, "D585BD", "975086", "4F2D4C")),
    PALLADIUM(60, 106.42f,
            new Isotopes(62, 107.9f),
            new Info(1850, 3250, "CAF190", "8EBE3D", "3E5E1E", Danger.TOXIC)),
    SILVER(61, 107.87f, ItemType.INGOT, true, true, true,
            new Info(1250, 2450, "C6D4DB", "8DA4AE", "40515D")),
    CADMIUM(64, 112.41f,
            new Isotopes(64, 111.9f),
            new Info(600, 1050, "EBB872", "BA8929", "644A16", Danger.TOXIC)), //red flame
    INDIUM(66, 114.82f,
            new Info(450, 2350, "7D6FD7", "4D4094", "2A2854")), //dark blue flame
    TIN(68,117.9f, true, false, false,
            new Info(500, 2900, "D1AD84", "87735A", "473A2D")), //light-blue flame
    ANTIMONY(70, 121.06f,
            new Info(900, 1900, "9ADCB9", "4CB485", "28624F", Danger.TOXIC)), //green flame
    TELLURIUM(72, 123.9f,
            new Info(700, 1250, "FFF6DE", "B79F93", "614B49", Danger.TOXIC)), //green flame
    IODINE(74, 126.9f, ItemType.CUBE,
//            new Isotopes(76, 128.9f, true),
            new Info(400, 450, "F28AC5", "A4459B", "5F2C6C", Danger.TOXIC)),
    XENON(76, 129.9f, ItemType.CANISTER,
            new Isotopes(75, 128.9f),
            new Info(160, 165, "6C87CF", "3B4D77", "253148")),
    CAESIUM(78, 132.9f,
            new Info(300, 950, "FFD17D", "DF9D28", "734717", Danger.FLAMMABLE, Danger.CORROSIVE)), //violet flame
    BARIUM(80, 136.0f,
            new Info(1000, 2100, "8EB7DB", "4674AD", "273B5F", Danger.FLAMMABLE, Danger.CORROSIVE, Danger.TOXIC)), //green flame
    LANTHANUM(82, 138.9f,
            new Isotopes(81, 137.91f, true),
            new Info(1200, 3750, "79A979", "346135", "1D321B", Danger.FLAMMABLE)),
    CERIUM(82, 140.12f,
            new Isotopes(84, 141.91f),
            new Info(1050, 3700, "F794C7", "AD548B", "643155", Danger.FLAMMABLE)),
    PRASEODYMIUM(82, 140.9f,
            new Info(1200, 3400, "B7E36B", "66A52B", "2F5618", Danger.FLAMMABLE)),
    NEODYMIUM(84, 144.24f,
            new Info(1300, 3350, "96D5C4", "4E938A", "284B4D")),
    PROMETHIUM(84, 144.91f,
            new Info(1300, 3300, "69F88C", "2DB852", "175F35", Danger.RADIOACTIVE)),
    SAMARIUM(88,  150.4f,
            new Isotopes(90, 151.92f),
            new Info(1350,2200, "DD9678", "986449", "4F3427", Danger.FLAMMABLE)),
    EUROPIUM(89, 151.96f,
            new Isotopes(92, 154.92f, true),
            new Info(1100, 1800, "3D9F7D", "246551", "133225", Danger.FLAMMABLE)),
    GADOLINIUM(93, 157.3f,
            new Info(1600, 3300, "E7D058", "A7B010", "525E07", Danger.FLAMMABLE)),
    TERBIUM(94, 158.92f,
            new Info(1650, 3400, "986D59", "694A38", "3D2C21")),
    DYSPROSIUM(96, 161.5f,
            new Info(1700, 2850, "56565A", "363639", "1F1F20", Danger.FLAMMABLE)),
    HOLMIUM(98, 164.93f,
            new Info(1750, 2900, "DA769E", "9E3F7C", "512147")),
    ERBIUM(99, 167.26f,
            new Isotopes(100, 167.93f),
            new Info(1800, 3150, "E1BDD8", "B96AA5", "693C69")),
    THULIUM(100, 168.93f,
            new Isotopes(102, 171.94f, true),
            new Info(1800, 2200, "81DAC2", "429482", "204844")),
    YTTERBIUM(103, 173.05f,
            new Isotopes(104, 173.94f),
            new Info(1100, 1450, "F49C55", "B95B1B", "5B270B", Danger.TOXIC, Danger.FLAMMABLE)),
    LUTETIUM(104, 174.97f,
            new Isotopes(105, 175.94f, true),
            new Info(1900, 3700, "FFA24B", "C85D00", "6C2900")),
    HAFNIUM(106, 178.49f,
            new Isotopes(104, 175.94f),
            new Info(2500, 4900, "556B9B", "2F446D", "1B2036", Danger.FLAMMABLE)), //white flame
    TANTALUM(108, 180.95f,
            new Info(3300, 5750, "93C9E5", "658198", "353E4C")), //blue flame
    TUNGSTEN(110, 183.84f, ItemType.INGOT, true, true, true,
            new Info(3700, 6200, "63858D", "3C5A63", "213336")), //green
    RHENIUM(111, 186.21f,
            new Isotopes(112, 186.96f),
            new Info(3450, 5900, "63B26D", "3C794B", "244130")),
    OSMIUM(114, 190.2f,
            new Info(3300, 5300, "99C8DC", "518B9B", "2D434E")),
    IRIDIUM(115, 192.22f,
            new Isotopes(116, 192.96f),
            new Info(2700, 4400, "F96D61", "C61E15", "660D17")),
    PLATINUM(117, 195.08f, true, false, false,
            new Isotopes(118, 195.96f),
            new Info(2050, 4100, "93BED6", "5E7C8D", "323B47")),
    GOLD(118, 196.96f, ItemType.VANILLA,true, false, false,
            new Info(1350, 3250, "FDF55F", "E9B115", "752802")),
    MERCURY(120,199.96f,
            new Info( 230, 630, Danger.TOXIC)), //red flame
    THALLIUM(122, 202.97f,
            new Info(600, 1750, "DEB665", "98762B", "493319", Danger.TOXIC)), //pure green flame
    LEAD(124, 205.97f, true, false, true,
            new Info(600, 2000, "6F6B85", "49465D", "292733", Danger.TOXIC)), //light-blue flame
    BISMUTH(126, 208.98f,
            new Info(550, 1850, "54EF5F", "951919", "430D7B", Danger.RADIOACTIVE)), //light blue flame
    POLONIUM(125, 209.1f,
            new Info(550, 1250, "94DBDE", "529891", "2B484C", Danger.TOXIC, Danger.RADIOACTIVE)),
    ASTATINE(125, 210f, ItemType.CUBE,
            new Info(600, 650, "BAF28A", "56A445", "2C6C37", Danger.RADIOACTIVE)),
    RADON(136, 222.1f, ItemType.CANISTER,
            new Isotopes(138, 224.02f, true),
            new Info(200, 215, "FF7980", "D0484F", "671F22", Danger.RADIOACTIVE)),
    FRANCIUM(136, 223.01f,
            new Info(300, 950, "AD6D4B", "803C25", "4B2416", Danger.RADIOACTIVE)),
    RADIUM(138, 226.02f,
            new Isotopes(140, 228.03f, true),
            new Info(950, 2000, "71CF74", "438046", "264A26", Danger.RADIOACTIVE)), //red flame
    ACTINIUM(138, 227.02f,
            new Info(1500, 3500, "68C0FF", "427DA9", "244A65", Danger.RADIOACTIVE)),
    THORIUM(142, 232.04f,
            new Isotopes(new int[]{140, 144}, new float[]{230.03f, 234.04f}, new boolean[]{true, true}),
            new Info(2000, 5050, "C87F57", "7F2622", "43120F", Danger.RADIOACTIVE)),
    PROTACTINIUM(140, 231.03f,
            new Info(1850, 4300, "70A54C", "4B6637", "2D4023", Danger.RADIOACTIVE)),
    URANIUM(146, 238.03f, true, false, false,
            new Isotopes(new int[]{141, 143}, new float[]{233.03f, 235.04f}, new boolean[]{true, true}),
            new Info(1400, 4400, "3AFF4F", "00AC3B", "007833", Danger.RADIOACTIVE)),
    NEPTUNIUM(144, 237.04f,
            new Info(900, 4450, "71B898", "2B7A7C", "0B4347", Danger.RADIOACTIVE)),
    PLUTONIUM(150, 244.06f,
            new Info(900, 3500, "75EFC7", "39AD6C", "256E3F", Danger.RADIOACTIVE)),
    AMERICIUM(148, 243.06f,
            new Info(1450, 2900, "F6F770", "A8A445", "695D26", Danger.RADIOACTIVE)),
    CURIUM(151, 247.07f,
            new Info(1600, 3350, "CE6349", "902E24", "581F18", Danger.RADIOACTIVE)),
    BERKELIUM(150, 247.07f,
            new Info(1250, 2900, "E8D73B", "C9A700", "7D6400", Danger.RADIOACTIVE)),
    CALIFORNIUM(153, 251.07f,
            new Info(1200, 1750, "C2E8EC", "89ADCB", "4C6486", Danger.RADIOACTIVE)),
    EINSTEINIUM(153, 252.08f,
            new Info(1150, 1250, "6892E6", "536FE2", "30437A", Danger.RADIOACTIVE)),
    DEBUGIUM(1000, 1000f,
            new Info(10_000, 1_000_000, Danger.RADIOACTIVE, Danger.TOXIC, Danger.FLAMMABLE, Danger.CORROSIVE)),
    ;

    private final boolean dust;
    private final Info info;
    private final boolean nugget;
    private final boolean block;
    private final ItemType type;
    private final float bitMass;
    private final int neutrons;
    private final Isotopes isotopes;


    Element(int neutrons, float bitMass, Isotopes isotopes, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = ItemType.INGOT;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = isotopes;
    }
    Element(int neutrons, float bitMass, ItemType type, Isotopes isotopes, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = type;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = isotopes;
    }
    Element(int neutrons, float bitMass, boolean dust, boolean nugget, boolean block, Isotopes isotopes, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = ItemType.INGOT;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = isotopes;
    }
    Element(int neutrons, float bitMass, ItemType type, boolean dust, boolean nugget, boolean block, Isotopes isotopes, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = type;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = isotopes;
    }

    Element(int neutrons, float bitMass, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = ItemType.INGOT;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = new Isotopes(new int[0], new float[0]);
    }
    Element(int neutrons, float bitMass, ItemType type, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = type;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = new Isotopes(new int[0], new float[0]);
    }
    Element(int neutrons, float bitMass, boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = ItemType.INGOT;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = new Isotopes(new int[0], new float[0]);
    }
    Element(int neutrons, float bitMass, ItemType type, boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = type;
        this.bitMass = bitMass;
        this.neutrons = neutrons;
        this.isotopes = new Isotopes(new int[0], new float[0]);
    }

    public String elementName() {return name().toLowerCase(Locale.US);}

    public String typeName() {return type.toString().toLowerCase(Locale.US);}

    public Boolean shouldAddDust() {return dust;}

    public Boolean shouldAddNugget() {return nugget;}

    public Boolean shouldAddBlock() {return block;}

    @SuppressWarnings("ClassEscapesDefinedScope")
    public Element.ItemType getType() {return type;}

    public Element.Isotopes getIsotopes() {return isotopes;}

    public String getState() {
        String state = "solid";
        if (info.getMeltingPoint() < 273) state = "liquid";
        if (info.getEvaporatingPoint() < 273) state = "gas";
        return state;
    }

    public boolean isMetal() {
        return getType() == ItemType.INGOT || getType() == ItemType.VANILLA;
    }

    public boolean isVanilla() {
        return getType() == ItemType.VANILLA;
    }

    public boolean isFireResistant() {return this.info.isFireProof();}

    public int getNeutrons() {return neutrons;}

    public Item item() {
        if (this.equals(Element.IRON)) {
            return Items.IRON_INGOT;
        } else if (this.equals(Element.GOLD)) {
            return Items.GOLD_INGOT;
        } else if (this.equals(Element.COPPER)) {
            return Items.COPPER_INGOT;
        }
        return POMitems.Elements.ELEMENTS.get(this).get();
    }

    public Item atom64() {return POMitems.Elements.ATOMX64.get(this).get();}

    public Item atom512() {return POMitems.Elements.ATOMX512.get(this).get();}

    public Item isotope64(int index) {
        return POMitems.Elements.ISOTOPEX64.get(this+"_"+(this.getIsotopes().getNeutrons()[index]+this.getElement())).get();
    }

    public Item isotope512(int index) {
        return POMitems.Elements.ISOTOPEX512.get(this+"_"+(this.getIsotopes().getNeutrons()[index]+this.getElement())).get();
    }

    public Block block() {return POMblocks.Elements.BLOCKS.get(this).get();}

    public Item blockItem() {
        if (this.equals(Element.IRON)) {
            return Items.IRON_BLOCK;
        } else if (this.equals(Element.GOLD)) {
            return Items.GOLD_BLOCK;
        } else if (this.equals(Element.COPPER)) {
            return Items.COPPER_BLOCK;
        } else if (this.shouldAddBlock())
            return POMblocks.Elements.BLOCKS.get(this).asItem();
        return Items.AIR;
    }

    public Item nugget() {
        if (this.equals(Element.IRON)) {
            return Items.IRON_NUGGET;
        } else if (this.equals(Element.GOLD)) {
            return Items.GOLD_NUGGET;
        } else if (this.shouldAddNugget())
            return POMitems.Elements.NUGGETS.get(this).get();
        return Items.AIR;
    }

    public Item dust() {
        if (this.shouldAddDust())
            return POMitems.Elements.DUSTS.get(this).get();
        return Items.AIR;
    }

    public ItemStack pixel() {
        return PixelItem.createForPixel(POMitems.PIXEL.get().getDefaultInstance(),
                new Color(this.hexToRGB(0)[0], this.hexToRGB(0)[1], this.hexToRGB(0)[2]).getRGB(),
                new Color(this.hexToRGB(1)[0], this.hexToRGB(1)[1], this.hexToRGB(1)[2]).getRGB(),
                new Color(this.hexToRGB(2)[0], this.hexToRGB(2)[1], this.hexToRGB(2)[2]).getRGB(),
                "element.pixelsofmc."+this.elementName());
    }

    public ItemStack pixelPile() {
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

    public Info getInfo() {return this.info;}

    public int getElement() {return this.ordinal()+1;}

    public float getBitMass() {return this.bitMass;}

    public double getMass() {return this.getBitMass() * 1.66f * Math.pow(10, -27);}

    public static class Isotopes {
        private final int[] isotopes;
        private final float[] bitMasses;
        private final boolean[] radioactive;

        public Isotopes(int[] isotopes, float[] bitMasses, boolean[] radioactive) {
            this.isotopes = isotopes;
            this.bitMasses = bitMasses;
            this.radioactive = radioactive;
        }
        public Isotopes(int[] isotopes, float[] bitMasses) {
            this.isotopes = isotopes;
            this.bitMasses = bitMasses;
            this.radioactive = new boolean[isotopes.length];
        }
        public Isotopes(int isotope, float bitMass, boolean radioactive) {
            this.isotopes = new int[]{isotope};
            this.bitMasses = new float[]{bitMass};
            this.radioactive = new boolean[]{radioactive};
        }
        public Isotopes(int isotope, float bitMass) {
            this.isotopes = new int[]{isotope};
            this.bitMasses = new float[]{bitMass};
            this.radioactive = new boolean[isotopes.length];
        }

        public int[] getNeutrons() {
            return isotopes;
        }

        public boolean[] getRadioactive() {
            return radioactive;
        }

        public float[] getBitMasses() {
            return bitMasses;
        }

        public float getBitMass(int neutrons) {
            for (int i = 0; i < isotopes.length; i++) {
                if (isotopes[i] == neutrons) {
                    return bitMasses[i];
                }
            }
            return 0;
        }

        public double getMass(int neutrons) {
            float bitMass = getBitMass(neutrons);
            if (bitMass != 0) {
                return bitMass * 1.66f * Math.pow(10, -27);
            }
            return 0;
        }

        public boolean isRadioactive(int neutrons) {
            for (int i = 0; i < isotopes.length; i++) {
                if (isotopes[i] == neutrons) {
                    return radioactive[i];
                }
            }
            return false;
        }
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
        public boolean isFireProof() {return meltingPoint >= 2000;}
        public int getDangerCount() {return danger.length;}
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

    private enum ItemType {
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

    public int[] hexToRGB(int index) {
        return Util.hexToRGB(this.info.color[index]);
    }

    // fusion factor of 325
    // fusion constant 8.9x10^9
    // r = 1.25x10^−15 * (atomic number)^(1/3)
    // mole to pixel constant = 2 * 10^22

    public static Element fromJson(JsonObject json) {
        return Element.values()[json.get("element").getAsInt() - 1];
    }

    public void toJson(JsonObject json) {
        json.addProperty("element", this.getElement());
    }

    public static Element fromNetwork(FriendlyByteBuf buffer) {
        return Element.values()[buffer.readInt() + 1];
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.getElement());
    }
}
