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
        public static final TagKey<Item> SPEED_UPGRADE = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "speed_upgrade"));
        public static final TagKey<Item> ENERGY_UPGRADE = ItemTags.create(new ResourceLocation(PixelsOfMc.MOD_ID, "energy_upgrade"));

        public static final TagKey<Item> NUGGET_NETHERITE = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "nuggets/netherite"));


        public static final TagKey<Item> DUST_NETHERITE = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/netherite"));
        public static final TagKey<Item> DUST_ANCIENT_DEBRIS = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/ancient_debris"));
        public static final TagKey<Item> DUST_COAL = ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, "dusts/coal"));
    }


    //Immersive Engineering

    static
    {
        for(Element m : Element.values())
            elements.put(m, new MetalTags(m));
    }
    public static class MetalTags {
        public final TagKey<Item> metal;
        public final TagKey<Item> dust;
        public final TagKey<Item> nugget;
        public final TagKey<Item> other;

        private MetalTags(Element m)
        {
            String name = m.tagName();
            if (m.isMetal())
                metal = createItemWrapper(getIngot(name));
            else metal = null;
            nugget = createItemWrapper(getNugget(name));
            dust = createItemWrapper(getDust(name));
            other = createItemWrapper(forgeLoc(name+"_"+m.typeName().toLowerCase()));
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
    public static ResourceLocation getNugget(String type) {
        return forgeLoc("nuggets/"+type);
    }
    protected static ResourceLocation forgeLoc(String path) {return new ResourceLocation("forge", path);}
    protected static TagKey<Item> createItemWrapper(ResourceLocation name) {
        return TagKey.create(Registry.ITEM_REGISTRY, name);
    }
}
