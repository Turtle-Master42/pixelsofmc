package net.turtlemaster42.pixelsofmc.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;

import java.awt.*;
import java.util.Locale;

public enum Element {
    HYDROGEN(1f, Type.CANISTER, new Info(15, 20, "FFFCFF", "DEDCE8", "79737F", Danger.FLAMMABLE)), //pale blue flame
    HELIUM(4f, Type.CANISTER, new Info(1, 5, "FFC977", "FFAA61", "794620")),
    LITHIUM(6.9f, new Info(450, 1600, "A5BD9C", "5C6557", "323831", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    BERYLLIUM(9f, new Info(1550, 2750, "B9D099", "7FA354", "43582D", Danger.TOXIC)), //white flame
    BORON(10.8f, Type.CUBE, true, false, false, new Info(2350, 4200, "4F4F62", "363647", "22222E")), //green flame
    CARBON(12f, Type.CUBE, true, false, false, new Info(3900, 3900, "54545D", "3B3B42", "222227")), //orange flame
    NITROGEN(14f, Type.CANISTER, new Info(60, 80, "AD95FF", "7264AC", "3A325A")),
    OXYGEN(16f, Type.CANISTER, new Info(55, 90, "7DB0F8", "4F719D", "263950")),
    FLUORINE(19f, Type.CUBE, new Info(50, 85, "DBCAE7", "8787A3", "3C3A53", Danger.CORROSIVE, Danger.TOXIC)), //canister
    NEON(20.2f, Type.CANISTER, new Info(25, 30, "FF8D73", "B75544", "6D2F25")),
    SODIUM(23f, true, false, false, new Info(370, 1150, "FEEF7C", "CBB24A", "70581F", Danger.CORROSIVE, Danger.FLAMMABLE)), //yellow flame
    MAGNESIUM(24.3f, new Info(900, 1350, "E7E4DF", "B3B0AB", "726E6A", Danger.FLAMMABLE)),
    ALUMINIUM(27f, true, false, false, new Info(950, 1200, "D4DCE2", "90A5A9", "43565E")), //silver flame
    SILICON(28.1f, Type.CUBE, true, false, false, new Info(1700, 3550, "9B90AC", "7D6D91", "463B55")),
    PHOSPHORUS(31f, Type.CUBE, new Info(320, 550, "E15549", "7D002D", "4B002A", Danger.FLAMMABLE, Danger.TOXIC)), //blue-green flame
    SULFUR(32.1f, Type.CUBE, true, false, false, new Info(400, 700, "D7AC45", "B77732", "723B26", Danger.FLAMMABLE)),
    CHLORINE(35.5f, Type.CANISTER, new Info(170, 240, "A4C764", "738B44", "394C1E", Danger.TOXIC)),
    ARGON(40f, Type.CANISTER, new Info(85, 90, "E0B9EE", "A482B2", "584362")),
    POTASSIUM(39.1f, true, false, false, new Info(350, 1050, "C380D6", "73439C", "372351", Danger.CORROSIVE, Danger.FLAMMABLE)), //purple flame
    CALCIUM(40.1f, true, false, false, new Info(1100, 1750, "E1DBD2", "CBBEA5", "746649", Danger.FLAMMABLE)), //orange flame
    SCANDIUM(45f, new Info(1800, 3100, "EFCE79", "B08934", "563D19")), //orange
    TITANIUM(47.9f, true, true, true, new Info(1950, 3550, "A69FAE", "7E748A", "43414E")), //silver flame
    VANADIUM(50.9f, new Info(2200, 3700, "F5796D", "AB3735", "541A24", Danger.TOXIC)), //yellow-green flame
    CHROMIUM(52f, new Info(2200, 2950, "81E4A5", "42B26A", "255F42")), //silver flame
    MANGANESE(54.9f, new Info(1500, 2350, "936B49", "5E4530", "33231A", Danger.TOXIC)), //yellow-green flame
    IRON(55.9f, Type.VANILLA, true, false, false, new Info(1800, 3150, "D8D8D8", "828282", "5E5E5E")), //orange flame
    COBALT(58.9f, true, false, false, new Info(1750, 3200, "87ABD3", "3D6AAD", "233A63")), //silver flame
    NICKEL(58.7f, true, false, false, new Info(1750, 3000, "E7A081", "A56246", "562E23")), //silver flame
    COPPER(63.6f, Type.VANILLA, true, true, false, new Info(1350,2850, "E77C56", "C15A36", "8A4129")), //blue-green flame
    ZINC(65.4f, true, false, false, new Info(700,1200, "94CAAF", "5D816C", "274430")), //white flame
    GALLIUM(69.7f, new Info(300, 2650, "9495AE", "595D7E", "302F42", Danger.CORROSIVE)),
    GERMANIUM(72.6f, new Info(1200, 3100, "E2B88F", "8C715A", "48352C")), //pale blue flame
    ARSENIC(74.9f, new Info(900, 900, "45D7CE", "32A0B7", "265472", Danger.TOXIC, Danger.CORROSIVE)), //dark blue flame
    SELENIUM(79f, new Info(500, 950, "DB7441", "BE4C2B", "762223")), //blue flame
    BROMINE(79.9f, Type.CANISTER, new Info(250, 350, "E890A2", "965A68", "4C2C38", Danger.TOXIC, Danger.CORROSIVE)),
    KRYPTON(83.8f, Type.CANISTER, new Info(110, 120, "7EB8C2", "52777D", "293F43")),
    RUBIDIUM(85.5f, new Info(310, 950, "FF8251", "B7451E", "5D1F14", Danger.FLAMMABLE, Danger.CORROSIVE)), //red flame
    STRONTIUM(87.6f, new Info(1050, 1650, "CCA078", "8E6842", "4A3524", Danger.FLAMMABLE)), //red flame
    YTTRIUM(88.9f, new Info(1800, 3200, "4CB179", "2C764A", "173E29", Danger.TOXIC)), //red flame
    ZIRCONIUM(91.2f, new Info(2100, 4650, "AA96E4", "6C5C98", "352D4A")), //red flame
    NIOBIUM(92.9f, true, false, false, new Info(2750, 5000, "748AD2", "42448C", "23264C")), //blue-green flame
    MOLYBDENUM(95.9f, Type.INGOT, true, false, false, new Info(2900, 4900, "5E627B", "3F455A", "272C37", Danger.TOXIC)), //yellow-green flame
    TECHNETIUM(98f, new Info(2450, 4550, "D891FF", "A352B9", "602E66", Danger.RADIOACTIVE)),
    RUTHENIUM(101.1f, new Info(2600, 4400, "FF688F", "C02B53", "641628")),
    RHODIUM(102.9f, new Info(2250, 4000, "D585BD", "975086", "4F2D4C")),
    PALLADIUM(106.4f, new Info(1850, 3250, "CAF190", "8EBE3D", "3E5E1E", Danger.TOXIC)),
    SILVER(107.9f, Type.INGOT, true, true, true, new Info(1250, 2450, "C6D4DB", "8DA4AE", "40515D")),
    CADMIUM(112.4f, new Info(600, 1050, "EBB872", "BA8929", "644A16", Danger.TOXIC)), //red flame
    INDIUM(114.8f, new Info(450, 2350, "7D6FD7", "4D4094", "2A2854")), //dark blue flame
    TIN(118.7f, true, false, false, new Info(500, 2900, "D1AD84", "87735A", "473A2D")), //light-blue flame
    ANTIMONY(121.8f, new Info(900, 1900, "9ADCB9", "4CB485", "28624F", Danger.TOXIC)), //green flame
    TELLURIUM(127.6f, new Info(700, 1250, "FFF6DE", "B79F93", "614B49", Danger.TOXIC)), //green flame
    IODINE(126.9f, Type.CUBE, new Info(400, 450, "F28AC5", "A4459B", "5F2C6C", Danger.TOXIC)),
    XENON(131.3f, Type.CANISTER, new Info(160, 165, "6C87CF", "3B4D77", "253148")),
    CAESIUM(132.9f, new Info(300, 950, "FFD17D", "DF9D28", "734717", Danger.FLAMMABLE, Danger.CORROSIVE)), //violet flame
    BARIUM(137.3f, new Info(1000, 2100, "8EB7DB", "4674AD", "273B5F", Danger.FLAMMABLE, Danger.CORROSIVE, Danger.TOXIC)), //green flame
    LANTHANUM(138.9f, new Info(1200, 3750, "79A979", "346135", "1D321B", Danger.FLAMMABLE)),
    CERIUM(140.1f, new Info(1050, 3700, "F794C7", "AD548B", "643155", Danger.FLAMMABLE)),
    PRASEODYMIUM(140.9f, new Info(1200, 3400, "B7E36B", "66A52B", "2F5618", Danger.FLAMMABLE)),
    NEODYMIUM(144.2f, new Info(1300, 3350, "96D5C4", "4E938A", "284B4D")),
    PROMETHIUM(145f, new Info(1300, 3300, "69F88C", "2DB852", "175F35", Danger.RADIOACTIVE)),
    SAMARIUM(150.4f, new Info(1350,2200, "DD9678", "986449", "4F3427", Danger.FLAMMABLE)),
    EUROPIUM(152f, new Info(1100, 1800, "3D9F7D", "246551", "133225", Danger.FLAMMABLE)),
    GADOLINIUM(157.3f, new Info(1600, 3300, "E7D058", "A7B010", "525E07", Danger.FLAMMABLE)),
    TERBIUM(158.9f, new Info(1650, 3400, "986D59", "694A38", "3D2C21")),
    DYSPROSIUM(162.5f, new Info(1700, 2850, "56565A", "363639", "1F1F20", Danger.FLAMMABLE)),
    HOLMIUM(164.9f, new Info(1750, 2900, "DA769E", "9E3F7C", "512147")),
    ERBIUM(167.3f, new Info(1800, 3150, "E1BDD8", "B96AA5", "693C69")),
    THULIUM(168.9f, new Info(1800, 2200, "81DAC2", "429482", "204844")),
    YTTERBIUM(173f, new Info(1100, 1450, "F49C55", "B95B1B", "5B270B", Danger.TOXIC, Danger.FLAMMABLE)),
    LUTETIUM(175f, new Info(1900, 3700, "FFA24B", "C85D00", "6C2900")),
    HAFNIUM(178.5f, new Info(2500, 4900, "556B9B", "2F446D", "1B2036", Danger.FLAMMABLE)), //white flame
    TANTALUM(180.9f, new Info(3300, 5750, "93C9E5", "658198", "353E4C")), //blue flame
    TUNGSTEN(183.8f, Type.INGOT, true, true, true, new Info(3700, 6200, "63858D", "3C5A63", "213336")), //green
    RHENIUM(186.2f, new Info(3450, 5900, "63B26D", "3C794B", "244130")),
    OSMIUM(190.2f, new Info(3300, 5300, "99C8DC", "518B9B", "2D434E")),
    IRIDIUM(192.2f, new Info(2700, 4400, "F96D61", "C61E15", "660D17")),
    PLATINUM(195.1f, true, false, false, new Info(2050, 4100, "93BED6", "5E7C8D", "323B47")),
    GOLD(197f, Type.VANILLA,true, false, false, new Info(1350, 3250, "FDF55F", "E9B115", "752802")),
    MERCURY(200.6f, new Info(230, 630, Danger.TOXIC)), //red flame
    THALLIUM(204.4f, new Info(600, 1750, "DEB665", "98762B", "493319", Danger.TOXIC)), //pure green flame
    LEAD(207.2f, true, false, false, new Info(600, 2000, "6F6B85", "49465D", "292733", Danger.TOXIC)), //light-blue flame
    BISMUTH(209f, new Info(550, 1850, "54EF5F", "951919", "430D7B", Danger.RADIOACTIVE)), //light blue flame
    POLONIUM(209.1f, new Info(550, 1250, "94DBDE", "529891", "2B484C", Danger.TOXIC, Danger.RADIOACTIVE)),
    ASTATINE(210f, Type.CUBE, new Info( 600, 650, "BAF28A", "56A445", "2C6C37", Danger.RADIOACTIVE)),
    RADON(222f, Type.CANISTER, new Info(200, 215, "FF7980", "D0484F", "671F22", Danger.RADIOACTIVE)),
    FRANCIUM(223f, new Info(300, 950, "AD6D4B", "803C25", "4B2416", Danger.RADIOACTIVE)),
    RADIUM(226f, new Info(950, 2000, "71CF74", "438046", "264A26", Danger.RADIOACTIVE)), //red flame
    ACTINIUM(227f, new Info(1500, 3500, "68C0FF", "427DA9", "244A65", Danger.RADIOACTIVE)),
    THORIUM(232f, new Info(2000, 5050, "C87F57", "7F2622", "43120F", Danger.RADIOACTIVE)),
    PROTACTINIUM(231f, new Info(1850, 4300, "70A54C", "4B6637", "2D4023", Danger.RADIOACTIVE)),
    URANIUM(238f, true, false, false, new Info(1400, 4400, "3AFF4F", "00AC3B", "007833", Danger.RADIOACTIVE)),
    NEPTUNIUM(237f, new Info(900, 4450, "71B898", "2B7A7C", "0B4347", Danger.RADIOACTIVE)),
    PLUTONIUM(244f, new Info(900, 3500, "75EFC7", "39AD6C", "256E3F", Danger.RADIOACTIVE)),
    AMERICIUM(243f, new Info(1450, 2900, "F6F770", "A8A445", "695D26", Danger.RADIOACTIVE)),
    CURIUM(247f, new Info(1600, 3350, "CE6349", "902E24", "581F18", Danger.RADIOACTIVE)),
    BERKELIUM(247f, new Info(1250, 2900, "E8D73B", "C9A700", "7D6400", Danger.RADIOACTIVE)),
    CALIFORNIUM(251f, new Info(1200, 1750, "C2E8EC", "89ADCB", "4C6486", Danger.RADIOACTIVE)),
    EINSTEINIUM(252f, new Info(1150, 1250, "6892E6", "536FE2", "30437A", Danger.RADIOACTIVE)),
    DEBUGIUM(1000f, new Info(10000, 1000000, Danger.RADIOACTIVE, Danger.TOXIC, Danger.FLAMMABLE, Danger.CORROSIVE)),
    ;

    private final Boolean dust;
    private final Info info;
    private final Boolean nugget;
    private final Boolean block;
    private final Type type;
    private final float bitMass;

    Element(float bitMass, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = Type.INGOT;
        this.bitMass = bitMass;
    }
    Element(float bitMass, Type type, Info info) {
        this.info = info;
        this.dust = false;
        this.nugget = false;
        this.block = false;
        this.type = type;
        this.bitMass = bitMass;
    }
    Element(float bitMass, boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = Type.INGOT;
        this.bitMass = bitMass;
    }
    Element(float bitMass, Type type, boolean dust, boolean nugget, boolean block, Info info) {
        this.info = info;
        this.dust = dust;
        this.nugget = nugget;
        this.block = block;
        this.type = type;
        this.bitMass = bitMass;
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
    public float getBitMass() {return this.bitMass;}

    public double getMass() {return this.getBitMass() * 1.66f * Math.pow(10, -27);}

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
