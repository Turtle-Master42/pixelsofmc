package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Element;

@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class POMtabs {
	public static CreativeModeTab PIXELS_OF_MINECRAFT_TAB;
	public static CreativeModeTab ATOM_TAB;

	@SubscribeEvent
	public static void register(CreativeModeTabEvent.Register event) {
		PIXELS_OF_MINECRAFT_TAB = event.registerCreativeModeTab(new ResourceLocation(PixelsOfMc.MOD_ID, "pixels_of_minecraft_tab"),
				builder -> builder.icon(() -> new ItemStack(POMitems.NETHERITE_PLATING.get())).title(Component.translatable("itemGroup.pixels_of_minecraft_tab")).build());

		ATOM_TAB = event.registerCreativeModeTab(new ResourceLocation(PixelsOfMc.MOD_ID, "atom_tab"),
				builder -> builder.icon(() -> new ItemStack(POMitems.Metals.ATOMX64.get(Element.TITANIUM).get())).title(Component.translatable("itemGroup.atom_tab")).build());
	}
}
