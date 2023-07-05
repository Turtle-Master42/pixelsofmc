package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.AbstractFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Debugium extends ElementItem {
    public Debugium(Element e, Properties pProperties) {
        super(e, pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractFusionControllerBlock fusionController) {
            fusionController.forcePlaceMultiBlock(pContext.getLevel(), pContext.getClickedPos());
            return InteractionResult.CONSUME;
        } else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractDummyMachineBlock) {
            if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof AbstractDummyMachineBlockTile dummyTile) {
                if (pContext.getLevel().getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractFusionControllerBlock fusionController) {
                    fusionController.forcePlaceMultiBlock(pContext.getLevel(), dummyTile.getMainPos());
                    return InteractionResult.CONSUME;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        PixelsOfMc.LOGGER.info("Color white: {}", new Color(255, 255, 255).getRGB());
        PixelsOfMc.LOGGER.info("Color green: {}", new Color(0, 255, 125).getRGB());
        PixelsOfMc.LOGGER.info("Color blue: {}", new Color(0, 255, 255).getRGB());
        return super.use(pLevel, pPlayer, pUsedHand);
    }

//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color white: ( 1,000E+0  1,000E+0  1,000E+0)
//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color green: ( 0,000E+0  1,000E+0  4,902E-1)
//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color blue: ( 0,000E+0  1,000E+0  1,000E+0)
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color white: -1
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color green: -16711811
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color blue: -16711681

}