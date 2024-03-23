package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.turtlemaster42.pixelsofmc.block.PixelSplitterBlock;
import net.turtlemaster42.pixelsofmc.block.tile.PixelSplitterTile;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;
import org.jetbrains.annotations.NotNull;

public class PixelSplitterTileRenderer implements BlockEntityRenderer<PixelSplitterTile> {
    public PixelSplitterTileRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PixelSplitterTile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack saw = pBlockEntity.getItemStackHandler().getStackInSlot(1);
        ItemStack input = pBlockEntity.getItemStackHandler().getStackInSlot(0);
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1.3125f, 0.5f);
        pPoseStack.scale(0.75f, 0.75f, 0.75f);

        switch (pBlockEntity.getBlockState().getValue(PixelSplitterBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(270));
            case EAST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
        }
        itemRenderer.renderStatic(saw, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

        pPoseStack.translate(0f, -0.5625f, 0f);
        pPoseStack.scale(0.6f, 0.6f, 0.6f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
        itemRenderer.renderStatic(input, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
