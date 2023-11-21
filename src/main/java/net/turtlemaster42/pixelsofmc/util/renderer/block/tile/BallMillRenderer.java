package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.block.BallMillBlock;
import net.turtlemaster42.pixelsofmc.block.HotIsostaticPressBlock;
import net.turtlemaster42.pixelsofmc.block.tile.BallMillTile;
import net.turtlemaster42.pixelsofmc.block.tile.HotIsostaticPressTile;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.ModelBallMill;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.ModelHotIsostaticPress;
import org.jetbrains.annotations.NotNull;

public class BallMillRenderer<T extends BallMillTile> implements BlockEntityRenderer<T> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("pixelsofmc:textures/block/ball_mill_box.png");
    private static final ModelBallMill MODEL = new ModelBallMill();

    public BallMillRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        Direction dir = pBlockEntity.getBlockState().getValue(BallMillBlock.FACING);
        if(dir == Direction.NORTH){
            pPoseStack.translate(0.0f, 0.0F, 1.0F);
        }else if(dir == Direction.EAST){
            pPoseStack.translate(0.0F, 0.0F, 0.0F);

        }else if(dir == Direction.SOUTH){
            pPoseStack.translate(1.0F, 0.0F, 0.0F);

        }else if(dir == Direction.WEST){
            pPoseStack.translate(1.0F, 0.0F, 1.0F);
        }
        pPoseStack.mulPose(dir.getOpposite().getRotation());
        pPoseStack.pushPose();
        pPoseStack.translate(0F, 0.0F, 0.0F);
        MODEL.renderMill(pBlockEntity, pPartialTick);
        MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), pPackedLight, pPackedOverlay, 1, 1F, 1, 1);
        pPoseStack.popPose();
        pPoseStack.popPose();
    }
}
