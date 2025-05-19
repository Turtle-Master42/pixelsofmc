package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.versions.forge.ForgeVersion;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.Util;

import java.util.EnumMap;
import java.util.Map;

public class POMtags {

    public static void register() {
        Items.init();
        Blocks.init();
    }
    private static final Map<Element, ElementTags> elements = new EnumMap<>(Element.class);

    public static class Items {
        private static void init() {}

        // items
        public static final TagKey<Item> ATOM = ItemTags.create(Util.resourceLocation("atom"));
        public static final TagKey<Item> ATOM64 = ItemTags.create(Util.resourceLocation("atom64"));
        public static final TagKey<Item> ATOM512 = ItemTags.create(Util.resourceLocation("atom512"));
        public static final TagKey<Item> SDS = ItemTags.create(Util.resourceLocation("sds"));
        public static final TagKey<Item> MDS = ItemTags.create(Util.resourceLocation("mds"));
        public static final TagKey<Item> MNS = ItemTags.create(Util.resourceLocation("mns"));

        public static final TagKey<Item> CIRCLE_SAW = ItemTags.create(Util.resourceLocation("circle_saw"));
        public static final TagKey<Item> MILLING_BALL = ItemTags.create(Util.resourceLocation("milling_ball"));
        public static final TagKey<Item> BALL_1 = ItemTags.create(Util.resourceLocation("ball_1"));
        public static final TagKey<Item> BALL_2 = ItemTags.create(Util.resourceLocation("ball_2"));
        public static final TagKey<Item> BALL_3 = ItemTags.create(Util.resourceLocation("ball_3"));
        public static final TagKey<Item> BALL_4 = ItemTags.create(Util.resourceLocation("ball_4"));
        public static final TagKey<Item> BALL_5 = ItemTags.create(Util.resourceLocation("ball_5"));
        public static final TagKey<Item> BALL_6 = ItemTags.create(Util.resourceLocation("ball_6"));
        public static final TagKey<Item> SPEED_UPGRADE = ItemTags.create(Util.resourceLocation("speed_upgrade"));
        public static final TagKey<Item> ENERGY_UPGRADE = ItemTags.create(Util.resourceLocation("energy_upgrade"));
        public static final TagKey<Item> HEAT_UPGRADE = ItemTags.create(Util.resourceLocation("heat_upgrade"));
        public static final TagKey<Item> SOUL_FUELS = ItemTags.create(Util.resourceLocation("soul_fuels"));
        public static final TagKey<Item> NUGGET_NETHERITE = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "nuggets/netherite"));

        // forge items
        public static final TagKey<Item> DUST_NETHERITE = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "dusts/netherite"));
        public static final TagKey<Item> DUST_ANCIENT_DEBRIS = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "dusts/ancient_debris"));
        public static final TagKey<Item> DUST_COAL = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "dusts/coal"));
        public static final TagKey<Item> DUST_QUARTZ = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "dusts/quartz"));

        public static final TagKey<Item> ORES_TITANIUM = ItemTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "ores/titanium"));

    }

    public static class Blocks {
        private static void init() {}

        // blocks
        public static final TagKey<Block> MACHINE_BLOCKS = BlockTags.create(Util.resourceLocation("machine_block"));
        public static final TagKey<Block> MULTIBLOCK_CASINGS = BlockTags.create(Util.resourceLocation("multiblock/casing"));
        public static final TagKey<Block> FISSION_CASINGS = BlockTags.create(Util.resourceLocation("multiblock/fission"));
        public static final TagKey<Block> FUSION_CASINGS = BlockTags.create(Util.resourceLocation("multiblock/fusion"));

        // forge blocks
        public static final TagKey<Block> ORES_TITANIUM = BlockTags.create(Util.resourceLocation(ForgeVersion.MOD_ID, "ores/titanium"));

    }
    //Immersive Engineering

    static
    {
        for(Element m : Element.values())
            elements.put(m, new ElementTags(m));
    }
    public static class ElementTags {
        public final TagKey<Item> metal;
        public final TagKey<Item> dust;
        public final TagKey<Item> nugget;
        public final TagKey<Item> other;

        private ElementTags(Element m)
        {
            String name = m.elementName();
            metal = createItemWrapper(getIngot(name));
            nugget = createItemWrapper(getNugget(name));
            dust = createItemWrapper(getDust(name));
            other = createItemWrapper(forgeLoc(name+"_"+m.typeName().toLowerCase()));
        }
    }

    public static ElementTags getTagsFor(Element element)
    {
        return elements.get(element);
    }
    public static ResourceLocation getIngot(String type) {
        return forgeLoc("ingots/"+type);
    }
    public static ResourceLocation getDust(String type) {
        return forgeLoc("dusts/"+type);
    }
    public static ResourceLocation getNugget(String type) {
        return forgeLoc("nuggets/"+type);
    }
    protected static ResourceLocation forgeLoc(String path) {return Util.resourceLocation("forge", path);}
    protected static TagKey<Item> createItemWrapper(ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}
