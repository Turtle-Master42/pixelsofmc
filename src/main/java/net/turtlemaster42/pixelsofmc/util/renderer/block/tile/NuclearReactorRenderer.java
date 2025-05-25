package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.turtlemaster42.pixelsofmc.block.NuclearReactorBlock;
import net.turtlemaster42.pixelsofmc.block.StarBlock;
import net.turtlemaster42.pixelsofmc.block.tile.NuclearReactorTile;
import net.turtlemaster42.pixelsofmc.block.tile.StarTile;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.renderer.RenderHelper;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.StarModel;
import org.jetbrains.annotations.NotNull;

public class NuclearReactorRenderer<T extends NuclearReactorTile> implements BlockEntityRenderer<T> {

    public NuclearReactorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.5F, 0.5F);
        int seed = (pBlockEntity.getBlockPos().getX()*1_000_000) + (pBlockEntity.getBlockPos().getY()*1_000) + pBlockEntity.getBlockPos().getZ();
        //renderToBuffer(PoseStack, VertexConsumer, light, colorOverlay?, red, green, blue, ?)
        if (pBlockEntity.getBlockState().getValue(NuclearReactorBlock.ACTIVE) == 3) {
            if (pBlockEntity.getItemStackHandler().getStackInSlot(0).getItem() instanceof FuelCellItem fuelCell1) {
                pPoseStack.translate(0f, -1f, 1f);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell1.getColor(), 0.9f, 0.04f,  0.02f, 0.04f, seed + 1);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell1.getColor(), 0.9f, 0.05f,  0.03f, 0.05f, seed + 1);
                pPoseStack.translate(0f, 1f, -1f);
            }
            if (pBlockEntity.getItemStackHandler().getStackInSlot(1).getItem() instanceof FuelCellItem fuelCell2) {
                pPoseStack.translate(-1f, -1f, 0f);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell2.getColor(), 0.9f, 0.04f,  0.02f, 0.04f, seed + 2);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell2.getColor(), 0.9f, 0.05f,  0.03f, 0.05f, seed + 2);
                pPoseStack.translate(1f, 1f, 0f);
            }
            if (pBlockEntity.getItemStackHandler().getStackInSlot(2).getItem() instanceof FuelCellItem fuelCell3) {
                pPoseStack.translate(0f, -1f, -1f);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell3.getColor(), 0.9f, 0.04f,  0.02f, 0.04f, seed + 3);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell3.getColor(), 0.9f, 0.05f,  0.03f, 0.05f, seed + 3);
                pPoseStack.translate(0f, 1f, 1f);
            }
            if (pBlockEntity.getItemStackHandler().getStackInSlot(3).getItem() instanceof FuelCellItem fuelCell4) {
                pPoseStack.translate(1f, -1f, 0f);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell4.getColor(), 0.9f, 0.04f,  0.02f, 0.04f, seed + 4);
                RenderHelper.renderStar(pPoseStack, pBufferSource, fuelCell4.getColor(), 0.9f, 0.05f,  0.03f, 0.05f, seed + 4);
                pPoseStack.translate(-1f, 1f, 0f);
            }
        }
        pPoseStack.popPose();
    }
}
