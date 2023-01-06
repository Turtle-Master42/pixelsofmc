package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.block.HotIsostaticPressBlock;
import net.turtlemaster42.pixelsofmc.block.tile.HotIsostaticPressTile;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.ModelHotIsostaticPress;

public class RendererHotIsostaticPress<T extends HotIsostaticPressTile> implements BlockEntityRenderer<T> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("pixelsofmc:textures/block/hot_isostatic_press.png");
    private static ModelHotIsostaticPress MODEL = new ModelHotIsostaticPress();

    public RendererHotIsostaticPress(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        Direction dir = pBlockEntity.getBlockState().getValue(HotIsostaticPressBlock.FACING);
        if(dir == Direction.NORTH){
            pPoseStack.translate(0.0f, 0.0F, 0.0F);
        }else if(dir == Direction.EAST){
            pPoseStack.translate(1.0F, 0.0F, 0.0F);

        }else if(dir == Direction.SOUTH){
            pPoseStack.translate(1.0F, 0.0F, 1.0F);

        }else if(dir == Direction.WEST){
            pPoseStack.translate(0.0F, 0.0F, 1.0F);
        }
        pPoseStack.mulPose(dir.getOpposite().getRotation());
        pPoseStack.pushPose();
        pPoseStack.translate(0, -0.01F, 0.0F);
        MODEL.renderIndecator(pBlockEntity, pPartialTick);
        MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), pPackedLight, pPackedOverlay, 1, 1F, 1, 1);
        pPoseStack.popPose();
        pPoseStack.popPose();
    }
}
