package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.turtlemaster42.pixelsofmc.block.StarBlock;
import net.turtlemaster42.pixelsofmc.tile.StarTile;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.BigStarModel;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.BlackHoleModel;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.NeutronStarModel;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.StarModel;
import org.jetbrains.annotations.NotNull;

public class StarRenderer<T extends StarTile> implements BlockEntityRenderer<T> {
    private static final ResourceLocation SMALL = Util.resourceLocation("textures/block/small_star.png");
    private static final ResourceLocation BIG = Util.resourceLocation("textures/block/big_star.png");
    private static final ResourceLocation NEUTRON = Util.resourceLocation("textures/block/neutron_star.png");
    private static final ResourceLocation HOLE = Util.resourceLocation("textures/block/black_hole.png");
    private static final StarModel STAR_MODEL = new StarModel();
    private static final BigStarModel BIG_STAR_MODEL = new BigStarModel();
    private static final NeutronStarModel NEUTRON_STAR_MODEL = new NeutronStarModel();
    private static final BlackHoleModel BLACK_HOLE_MODEL = new BlackHoleModel();

    public StarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.5F, 0.5F);
        int seed = pBlockEntity.getBlockPos().getX() + pBlockEntity.getBlockPos().getY() + pBlockEntity.getBlockPos().getZ();
        float time = (pBlockEntity.ticksExisted + pPartialTick);
        //renderToBuffer(PoseStack, VertexConsumer, light, colorOverlay?, red, green, blue, ?)
        if (pBlockEntity.getBlockState().getValue(StarBlock.STAR_STAGE) == 4) {
            BLACK_HOLE_MODEL.renderBlackHole(pBlockEntity, pPartialTick);
            BLACK_HOLE_MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderHelper.starShine(HOLE)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin(time / 32) * 0.05f + 0.08f;


            //out
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*0.25f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xF83300, 1f, 0.13f, 0.01f, 0.13f, seed + 18);
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xF83300, 1f, 0.12f, 0.01f, 0.12f, seed + 19);
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xF83300, 1f, 0.11f, 0.01f, 0.11f, seed + 20);
            //middle
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFAE00, 1f, 0.10f, 0.01f, 0.10f, seed + 14);
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFAE00, 1f, 0.09f, 0.01f, 0.09f, seed + 15);
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFAE00, 1f, 0.08f, 0.01f, 0.08f, seed + 16);
            //in
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xF9FF75, 1f, 0.07f, 0.01f, 0.07f, seed + 12);
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xF9FF75, 1f, 0.06f, 0.01f, 0.06f, seed + 13);
            //horizon
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*1.2f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xFFFFF1, 1f, 0.05f, 0.01f, 0.05f, seed + 17);
            //beam
            pPoseStack.rotateAround(Axis.YP.rotationDegrees(time*10f), 0.0f, 0.0f, 0.0f);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff84f6, 0.8f, 0.01f, scale + 0.02f, 0.01f, seed + 10);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xc349fc, 0.8f, 0.0125f, scale + 0.03f, 0.0125f, seed + 11);

        } else if (pBlockEntity.getBlockState().getValue(StarBlock.STAR_STAGE) == 3) {
            NEUTRON_STAR_MODEL.renderNeutronStar(pBlockEntity, pPartialTick);
            NEUTRON_STAR_MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(NEUTRON, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin(time / 32) * 0.02f + 0.08f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xccfbfc, 0.7f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0x75f5ff, 0.8f, scale + 0.03f, scale + 0.01f, scale + 0.03f, seed + 11);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0x00edff, 1f, 0.04f, scale + 0.11f, 0.04f, seed + 11);
        } else if (pBlockEntity.getBlockState().getValue(StarBlock.STAR_STAGE) == 2) {
            BIG_STAR_MODEL.renderBigStar(pBlockEntity, pPartialTick);
            BIG_STAR_MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(BIG, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin(time / 32) * 0.02f + 0.15f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff0000, 0.8f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff8300, 0.8f, scale + 0.03f, scale + 0.03f, scale + 0.03f, seed + 11);
        } else {
            STAR_MODEL.renderStar(pBlockEntity, pPartialTick);
            STAR_MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(SMALL, false)), 255, pPackedOverlay, 1f, 1f, 1f, 1f);
            float scale = Mth.sin(time / 32) * 0.02f + 0.07f;
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xff0000, 0.6f, scale, scale, scale, seed);
            RenderHelper.renderStar(pPoseStack, pBufferSource, 0xffa600, 0.8f, scale + 0.02f, scale + 0.02f, scale + 0.02f, seed + 11);
        }
        pPoseStack.translate(0.5f, 0.5f ,0.5f);
        pPoseStack.popPose();
    }
}
