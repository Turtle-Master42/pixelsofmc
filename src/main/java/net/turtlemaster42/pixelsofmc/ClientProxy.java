package net.turtlemaster42.pixelsofmc;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.gui.book.GuiBook1;
import net.turtlemaster42.pixelsofmc.init.POMitems;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public void openBookGUI(ItemStack itemStackIn) {
        Minecraft.getInstance().setScreen(new GuiBook1(itemStackIn));
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
        Minecraft.getInstance().setScreen(new GuiBook1(itemStackIn, page));
    }
}