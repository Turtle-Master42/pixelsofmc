package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.block.StarBlock;
import net.turtlemaster42.pixelsofmc.block.tile.StarTile;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.StarModel;

public class StarRenderer<T extends StarTile> implements BlockEntityRenderer<T> {
    private static final ResourceLocation SMALL = new ResourceLocation("pixelsofmc:textures/block/small_star.png");
    private static final ResourceLocation BIG = new ResourceLocation("pixelsofmc:textures/block/big_star.png");
    private static final ResourceLocation NEUTRON = new ResourceLocation("pixelsofmc:textures/block/neutron_star.png");
    private static final ResourceLocation HOLE = new ResourceLocation("pixelsofmc:textures/block/black_hole.png");
    private static StarModel MODEL = new StarModel();

    public StarRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.5F, 0.5F);
        int seed = pBlockEntity.getBlockPos().getX() + pBlockEntity.getBlockPos().getY() + pBlockEntity.getBlockPos().getZ();
        //renderToBuffer(PoseStack, VertexConsumer, light, colorOverlay?, red, green, blue, ?)
        if (pBlockEntity.getBlockState().getValue(StarBlock.INT_4) == 4) {
            MODEL.renderBlackHole(pBlockEntity, pPartialTick);
            MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(HOLE, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin((pBlockEntity.ticksExisted + pPartialTick) / 32) * 0.05f + 0.08f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff84f6, 0.9f, 0.02f, scale + 0.05f, 0.02f, seed + 11);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xc349fc, 0.9f, 0.03f, scale + 0.07f, 0.03f, seed + 11);
        } else if (pBlockEntity.getBlockState().getValue(StarBlock.INT_4) == 3) {
            MODEL.renderNeutronStar(pBlockEntity, pPartialTick);
            MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(NEUTRON, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin((pBlockEntity.ticksExisted + pPartialTick) / 32) * 0.02f + 0.08f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xccfbfc, 0.7f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0x75f5ff, 0.8f, scale + 0.03f, scale + 0.01f, scale + 0.03f, seed + 11);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0x00edff, 1f, 0.04f, scale + 0.11f, 0.04f, seed + 11);
        } else if (pBlockEntity.getBlockState().getValue(StarBlock.INT_4) == 2) {
            MODEL.renderBigStar(pBlockEntity, pPartialTick);
            MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(BIG, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin((pBlockEntity.ticksExisted + pPartialTick) / 32) * 0.02f + 0.15f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff0000, 0.8f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff8300, 0.8f, scale + 0.03f, scale + 0.03f, scale + 0.03f, seed + 11);
        } else {
            MODEL.renderStar(pBlockEntity, pPartialTick);
            MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(SMALL, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin((pBlockEntity.ticksExisted + pPartialTick) / 32) * 0.02f + 0.07f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff0000, 0.6f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xffa600, 0.8f, scale + 0.02f, scale + 0.02f, scale + 0.02f, seed + 11);
        }
        pPoseStack.translate(0.5f, 0.5f ,0.5f);
        pPoseStack.popPose();
    }
}
