package net.turtlemaster42.pixelsofmc.util.renderer;


/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */

public final class ClientTickHandler {

    private ClientTickHandler() {}
    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static float total() {
        return ticksInGame + partialTicks;
    }

    public static void renderTick(float renderTickTime) {
        partialTicks = renderTickTime;
    }


}
