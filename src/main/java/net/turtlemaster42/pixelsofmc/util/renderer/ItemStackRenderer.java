package net.turtlemaster42.pixelsofmc.util.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.turtlemaster42.pixelsofmc.entity.client.RiverShellModel;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

public class ItemStackRenderer extends BlockEntityWithoutLevelRenderer {

    public static int ticksExisted = 0;
    private static final RiverShellModel RIVER_SHELL_MODEL = new RiverShellModel();
    private static final ResourceLocation RIVER_SHELL_TEXTURE = Util.resourceLocation("textures/entity/river_shell.png");

    public ItemStackRenderer() {
        super(null, null);
    }

    public static void incrementTick() {
        ticksExisted++;
    }

    @Override
    public void renderByItem(ItemStack itemStack, @NotNull ItemDisplayContext transformType, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (itemStack.getItem() == POMitems.RIVER_SHELL.get()) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-180));
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-180));
            if (itemStack.getOrCreateTag().getInt("Age") < 0) {
                RIVER_SHELL_MODEL.young = true;
                matrixStackIn.translate(0.6f, -1.83f, -0.5f);
            } else {
                RIVER_SHELL_MODEL.young = false;
                matrixStackIn.translate(0.6f, -1.8f, -0.5f);
            }
            RIVER_SHELL_MODEL.animateStack(itemStack);
            RIVER_SHELL_MODEL.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(RIVER_SHELL_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }

}