
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pixelsofmc.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

import net.mcreator.pixelsofmc.recipe.Override;

public class PixelsOfMcModTabs {
	public static CreativeModeTab TAB_PIXELS_OF_MINECRAFT_TAB;

	public static void load() {
		TAB_PIXELS_OF_MINECRAFT_TAB = new CreativeModeTab("tabpixels_of_minecraft_tab") {
			@Override
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
