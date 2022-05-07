
package net.mcreator.pixelsofmc.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.mcreator.pixelsofmc.init.PixelsOfMcModTabs;

public class EmptyJarItem extends Item {
	public EmptyJarItem() {
		super(new Item.Properties().tab(PixelsOfMcModTabs.TAB_PIXELS_OF_MINECRAFT_TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}
}
