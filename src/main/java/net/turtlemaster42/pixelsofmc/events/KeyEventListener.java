package net.turtlemaster42.pixelsofmc.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

import java.util.HashMap;

public class KeyEventListener {

    //private HashMap<String, InputConstants.Key> yourKeyMappings = new HashMap<>();

//    private boolean off = false;
//
//    @SubscribeEvent
//    public void keyEvent(InputEvent.Key event) {
//        Minecraft m = Minecraft.getInstance();
//        if (event.getKey() == InputConstants.KEY_F19) {
//            PixelsOfMc.LOGGER.info("Yeet ClientSide");
//            m.gameRenderer.close();
//            m.getWindow().setFramerateLimit(1);
//            m.getSoundManager().updateSourceVolume(SoundSource.MASTER, m.options.getSoundSourceVolume(SoundSource.MASTER) * 0);
//            m.noRender = true;
//            off = true;
//        }
//        if (event.getKey() == InputConstants.KEY_DELETE) {
//            PixelsOfMc.LOGGER.info("Off: {}", off);
//            PixelsOfMc.LOGGER.info(m.fpsString);
//            off = true;
//
//        }
//        if (event.getKey() == -1) {
//            off = false;
//        }
//        if (off) {
//            //if (event.getKey() == InputConstants.KEY_ESCAPE)
//            KeyMapping.releaseAll();
//        }
//    }
//
//    @SubscribeEvent
//    public void screenRenderPreEvent(ScreenEvent.Render.Pre event) {
//        if (off) event.setCanceled(true);
//    }
}
