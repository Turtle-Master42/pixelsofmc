package net.turtlemaster42.pixelsofmc.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class PixelsOfMcModTabs {
	public static CreativeModeTab PIXELS_OF_MINECRAFT_TAB;

	public static void load() {
		PIXELS_OF_MINECRAFT_TAB = new CreativeModeTab("pixels_of_minecraft_tab") {
			public ItemStack makeIcon() {
				return new ItemStack(PixelsOfMcModItems.HEAT_RESISTANT_PLATING.get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
