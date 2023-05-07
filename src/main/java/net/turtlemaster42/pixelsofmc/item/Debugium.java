package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.turtlemaster42.pixelsofmc.block.AbstractFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.util.Element;

public class Debugium extends ElementItem {
    public Debugium(Element e, Properties pProperties) {
        super(e, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
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

}
