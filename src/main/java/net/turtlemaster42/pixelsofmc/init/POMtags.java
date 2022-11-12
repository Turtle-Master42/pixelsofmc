package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.versions.forge.ForgeVersion;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class POMtags {

    public static void register() {Items.init();}

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
    }
}
