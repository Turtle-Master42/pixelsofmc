package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.turtlemaster42.pixelsofmc.block.AbstractFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

public class ToolItem extends Item {
    public ToolItem(Properties pProperties) {
        super(pProperties);
    }

    //immersive engineering
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack container = itemStack.copy();
        if(container.hurt(1, RandomSource.create(), null))
            return ItemStack.EMPTY;
        else
            return container;
    }
    //immersive engineering
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getItemInHand().getItem().equals(POMitems.SCREWDRIVER.get())) {
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractFusionControllerBlock fusionController) {
                fusionController.validateMultiBlock(pContext.getLevel(), pContext.getClickedPos());
                return InteractionResult.CONSUME;
            } else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractDummyMachineBlock) {
                if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof AbstractDummyMachineBlockTile dummyTile) {
                    if (pContext.getLevel().getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractFusionControllerBlock fusionController) {
                        fusionController.validateMultiBlock(pContext.getLevel(), dummyTile.getMainPos());
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (stack.is(POMitems.CLEANING_CLOTH.get()))
            return toolAction.equals(ToolActions.AXE_WAX_OFF);
        return false;
    }
}
