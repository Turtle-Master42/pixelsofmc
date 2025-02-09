package net.turtlemaster42.pixelsofmc.util.renderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemRenderProperties implements IClientItemExtensions {

    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ItemstackRenderer();
    }
}