package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.versions.forge.ForgeVersion;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Element;

import java.util.EnumMap;
import java.util.Map;

public class POMtags {

    public static void register() {Items.init();}
    private static final Map<Element, MetalTags> elements = new EnumMap<>(Element.class);

    public static class Items {
        private static void init() {}





        public static final TagKey<Item> CIRCLE_SAW = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "circle_saw"));
        public static final TagKey<Item> MILLING_BALL = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "milling_ball"));
        public static final TagKey<Item> BALL_1 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_1"));
        public static final TagKey<Item> BALL_2 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_2"));
        public static final TagKey<Item> BALL_3 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_3"));
        public static final TagKey<Item> BALL_4 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_4"));
        public static final TagKey<Item> BALL_5 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_5"));
        public static final TagKey<Item> BALL_6 = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "ball_6"));

        public static final TagKey<Item> INGOT_TITANIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "ingots/titanium"));


        public static final TagKey<Item> NUGGET_COPPER = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/copper"));
        public static final TagKey<Item> NUGGET_SILVER = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/silver"));
        public static final TagKey<Item> NUGGET_STEEL = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/steel"));
        public static final TagKey<Item> NUGGET_TITANIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/titanium"));
        public static final TagKey<Item> NUGGET_NETHERITE = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/netherite"));


        public static final TagKey<Item> DUST_TITANIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/titanium"));
        public static final TagKey<Item> DUST_COAL = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/coal"));
        public static final TagKey<Item> DUST_ALUMINIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/aluminium"));
        public static final TagKey<Item> DUST_ANCIENT_DEBRIS = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/ancient_debris"));
        public static final TagKey<Item> DUST_BORON = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/boron"));
        public static final TagKey<Item> DUST_CALCIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/calcium"));
        public static final TagKey<Item> DUST_GOLD = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/gold"));
        public static final TagKey<Item> DUST_IRON = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/iron"));
        public static final TagKey<Item> DUST_NETHERITE = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/netherite"));
        public static final TagKey<Item> DUST_POTASSIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/potassium"));
        public static final TagKey<Item> DUST_SILICON = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/silicon"));
        public static final TagKey<Item> DUST_SODIUM = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/sodium"));
        public static final TagKey<Item> DUST_SULFUR = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/sulfur"));
    }


    //

    static
    {
        for(Element m : Element.values())
            elements.put(m, new MetalTags(m));
    }
    public static class MetalTags {
        public final TagKey<Item> metal;
        public final TagKey<Item> dust;

        private MetalTags(Element m)
        {
            String name = m.tagName();
            if (m.isMetal())
                metal = createItemWrapper(getIngot(name));
            else metal = null;
            dust = createItemWrapper(getDust(name));
        }
    }

    public static MetalTags getTagsFor(Element element)
    {
        return elements.get(element);
    }
    public static ResourceLocation getIngot(String type) {
        return forgeLoc("ingots/"+type);
    }
    public static ResourceLocation getDust(String type) {
        return forgeLoc("dusts/"+type);
    }
    protected static ResourceLocation forgeLoc(String path) {return new ResourceLocation("forge", path);}
    protected static TagKey<Item> createItemWrapper(ResourceLocation name) {
        return TagKey.create(Registry.ITEM_REGISTRY, name);
    }
}
