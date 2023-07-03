//credits CustomizableElytra

package net.turtlemaster42.pixelsofmc.util.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.item.PixelItem;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorHandler {
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, color) ->
                ((PixelItem) stack.getItem()).getColor(stack, color), POMitems.PIXEL.get());
        event.getItemColors().register((stack, color) ->
                ((PixelItem) stack.getItem()).getColor(stack, color), POMitems.PIXEL_PILE.get());
    }
}
