package net.turtlemaster42.pixelsofmc.util.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;

public class FusionCoreRenderer<T extends SDSFusionControllerTile> implements BlockEntityRenderer<T> {

    private static final ResourceLocation SMALL = new ResourceLocation("pixelsofmc:textures/block/small_star.png");
    private static final ResourceLocation BIG = new ResourceLocation("pixelsofmc:textures/block/big_star.png");
    private static final ResourceLocation NEUTRON = new ResourceLocation("pixelsofmc:textures/block/neutron_star.png");
    private static final ResourceLocation BLACK_HOLE = new ResourceLocation("pixelsofmc:textures/block/black_hole.png");
    private static FusionCoreModel STAR_MODEL = new FusionCoreModel();

    public FusionCoreRenderer(Context rendererDispatcherIn) {
    }

    private ResourceLocation getStarTexture(T tile) {
        boolean starLevel = tile.getBlockState().getValue(SDSFusionControllerBlock.ACTIVE);
        if (starLevel) {
            return BIG;
        } else  {
            return SMALL;
        }
    }

    @Override
    public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        Direction dir = tileEntityIn.getBlockState().getValue(SDSFusionControllerBlock.FACING);
        if(dir == Direction.NORTH){
            matrixStackIn.translate(0.0f, 0.0F, 0.0F);
        }else if(dir == Direction.EAST){
            matrixStackIn.translate(1.0F, 0.0F, 0.0F);

        }else if(dir == Direction.SOUTH){
            matrixStackIn.translate(1.0F, 0.0F, 1.0F);

        }else if(dir == Direction.WEST){
            matrixStackIn.translate(0.0F, 0.0F, 1.0F);
        }
        matrixStackIn.mulPose(dir.getOpposite().getRotation());
        matrixStackIn.pushPose();
        STAR_MODEL.renderStar(tileEntityIn, partialTicks);
        STAR_MODEL.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(getStarTexture(tileEntityIn))), combinedLightIn, combinedOverlayIn, 1, 1F, 1, 1);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }
}
