package net.turtlemaster42.pixelsofmc.util.renderer.block.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.turtlemaster42.pixelsofmc.block.FuelCellHolderBlock;
import net.turtlemaster42.pixelsofmc.block.tile.FuelCellHolderTile;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.ModelFuelCell;
import net.turtlemaster42.pixelsofmc.util.renderer.block.models.ModelFuelCellHolder;
import org.jetbrains.annotations.NotNull;

public class FuelCellHolderRenderer implements BlockEntityRenderer<FuelCellHolderTile> {

    private static final ResourceLocation TEXTURE_HOLDER = Util.resourceLocation("textures/block/fuel_cell_casing.png");
    private static final ModelFuelCellHolder MODEL_HOLDER = new ModelFuelCellHolder();
    private static final ModelFuelCell MODEL_FUEL = new ModelFuelCell();


    public FuelCellHolderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FuelCellHolderTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getItemStackHandler().getStackInSlot(0).isEmpty()) {return;}
        pPoseStack.pushPose();

        Direction dir = pBlockEntity.getBlockState().getValue(FuelCellHolderBlock.FACING);
        if(dir == Direction.NORTH) {pPoseStack.translate(1.0f, 0.0F, 0.0F);
        }else if(dir == Direction.EAST) {pPoseStack.translate(1.0F, 0.0F, 1.0F);
        }else if(dir == Direction.SOUTH) {pPoseStack.translate(0.0F, 0.0F, 1.0F);
        } else if(dir == Direction.DOWN) {pPoseStack.translate(0.0F, 1.0F, 1.0F);}

        if (dir == Direction.UP || dir == Direction.DOWN) {pPoseStack.mulPose(dir.getOpposite().getRotation());}
        else {pPoseStack.mulPose(dir.getRotation());}

        ResourceLocation fuel = Util.resourceLocation("textures/item/uranium_fuel_model.png");
        if (pBlockEntity.getItemStackHandler().getStackInSlot(0).getItem() instanceof FuelCellItem fuelCell) {
            fuel = fuelCell.getTexture();
        }


        MODEL_FUEL.renderFuelCell(pBlockEntity, pPartialTick, pBlockEntity.getBlockState().getValue(FuelCellHolderBlock.CELL_TYPE) == 1);
        MODEL_FUEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutoutNoCull(fuel)), 255, pPackedOverlay, 1, 1F, 1, 1);
        MODEL_HOLDER.renderFuelCell(pBlockEntity, pPartialTick, pBlockEntity.getBlockState().getValue(FuelCellHolderBlock.CELL_TYPE) == 1);
        MODEL_HOLDER.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_HOLDER)), 200, pPackedOverlay, 1, 1F, 1, 1);
        pPoseStack.popPose();
    }
}
