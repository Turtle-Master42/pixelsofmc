package net.turtlemaster42.pixelsofmc.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.entity.RiverShellEntity;
import org.jetbrains.annotations.NotNull;

public class RiverShellRenderer extends MobRenderer<RiverShellEntity, RiverShellModel> {
    public RiverShellRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RiverShellModel(), 0.4f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RiverShellEntity pEntity) {
        return new ResourceLocation("pixelsofmc:textures/entity/river_shell.png");
    }
}
