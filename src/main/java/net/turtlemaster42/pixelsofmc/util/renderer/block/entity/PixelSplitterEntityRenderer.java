package net.turtlemaster42.pixelsofmc.util.renderer.block.entity;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.turtlemaster42.pixelsofmc.block.entity.PixelSplitterBlockEntity;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;

import java.util.Random;

public class PixelSplitterEntityRenderer implements BlockEntityRenderer<PixelSplitterBlockEntity> {
    public PixelSplitterEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PixelSplitterBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.5 ,0.5);

        int seed = pBlockEntity.getBlockPos()
                .getX() + pBlockEntity.getBlockPos()
                .getY() + pBlockEntity.getBlockPos()
                .getZ();

        if (pBlockEntity.getBlockState().getValue(BlockStateProperties.LIT)) {
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFF0000, 0.25f, 0.25f, 0.25f, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFF0000, 0.25f, 0.25f, 0.25f, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFA600, 0.35f, 0.35f, 0.35f, seed + 11);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFA600, 0.35f, 0.35f, 0.35f, seed + 11);
        }
        pPoseStack.popPose();
    }
}
