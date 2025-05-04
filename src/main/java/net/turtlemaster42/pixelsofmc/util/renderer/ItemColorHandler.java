//credits CustomizableElytra

package net.turtlemaster42.pixelsofmc.util.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.item.ReinforcedBucket;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorHandler {
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, index) -> ((PixelItem) stack.getItem()).getColor(stack, index), POMitems.PIXEL.get());
        event.getItemColors().register((stack, index) -> ((PixelItem) stack.getItem()).getColor(stack, index), POMitems.PIXEL_PILE.get());
        
        event.getItemColors().register(
                (stack, index) -> {
                    if (index == 1) {
                        return ((ReinforcedBucket) stack.getItem()).getColor(stack);
                    } else {
                        return -1;
                    }
                },
                POMitems.REINFORCED_BUCKET.get()
        );
    }
}
