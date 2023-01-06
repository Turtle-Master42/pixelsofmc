package net.turtlemaster42.pixelsofmc.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.turtlemaster42.pixelsofmc.util.Element;

public class POMtabs {
	public static CreativeModeTab PIXELS_OF_MINECRAFT_TAB;
	public static CreativeModeTab ATOM_TAB;

	public static void load() {
		PIXELS_OF_MINECRAFT_TAB = new CreativeModeTab("pixels_of_minecraft_tab") {
			public ItemStack makeIcon() {
				return new ItemStack(POMitems.NETHERITE_PLATING.get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
		ATOM_TAB = new CreativeModeTab("atom_tab") {
			public ItemStack makeIcon() {
				return new ItemStack(POMitems.Metals.ATOMX64.get(Element.TITANIUM).get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
