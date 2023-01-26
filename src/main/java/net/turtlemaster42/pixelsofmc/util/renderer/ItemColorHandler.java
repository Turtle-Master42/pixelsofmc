//credits CustomizableElytra

package net.turtlemaster42.pixelsofmc.util.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.item.Pixel;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorHandler {
    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, color) ->
                ((Pixel) stack.getItem()).getColor(stack, color), POMitems.PIXEL.get());
        event.getItemColors().register((stack, color) ->
                ((Pixel) stack.getItem()).getColor(stack, color), POMitems.PIXEL_PILE.get());
    }
}
