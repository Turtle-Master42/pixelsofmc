package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.turtlemaster42.pixelsofmc.block.tile.PixelSplitterTile;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;

public class PixelSplitterTileRenderer implements BlockEntityRenderer<PixelSplitterTile> {
    public PixelSplitterTileRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PixelSplitterTile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
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
