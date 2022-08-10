package net.turtlemaster42.pixelsofmc;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.turtlemaster42.pixelsofmc.PixelsOfMc.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void openBookGUI(ItemStack itemStack) {
    }

    public void openBookGUI(ItemStack itemStack, String page) {
    }
}
