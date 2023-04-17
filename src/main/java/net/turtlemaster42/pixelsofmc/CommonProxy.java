package net.turtlemaster42.pixelsofmc;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static net.turtlemaster42.pixelsofmc.PixelsOfMc.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void openBookGUI(ItemStack itemStack) {
    }

    public void openBookGUI(ItemStack itemStack, String page) {
    }

    public void clientInit() {
    }

    public Object getISTERProperties() {
        return null;
    }
}
