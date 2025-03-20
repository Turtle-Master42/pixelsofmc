package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;

public class ToolItem extends Item {
    public ToolItem(Properties pProperties) {
        super(pProperties);
    }

    //immersive engineering
    @Nonnull
    @Override
    public ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack)
    {
        ItemStack container = stack.copy();
        if(container.hurt(1, RandomSource.create(), null))
            return ItemStack.EMPTY;
        else
            return container;
    }

    //immersive engineering
    @Override
    public boolean hasCraftingRemainingItem(@Nonnull ItemStack stack)
    {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getItemInHand().getItem().equals(POMitems.SCREWDRIVER.get())) {
            Map<Block, Integer> blocks;
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                blocks = multiControllerBlock.validateMultiBlock(pContext.getLevel(), pContext.getClickedPos());
            } else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof AbstractDummyMachineBlock) {
                if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof AbstractDummyMachineBlockTile dummyTile) {
                    if (pContext.getLevel().getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                        blocks = multiControllerBlock.validateMultiBlock(pContext.getLevel(), dummyTile.getMainPos());
                    } else {
                        return InteractionResult.PASS;
                    }
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return InteractionResult.PASS;
            }
            if (blocks.isEmpty() && pContext.getPlayer() != null && !pContext.getLevel().isClientSide()) {
                pContext.getPlayer().sendSystemMessage(Component.translatable("message.pixelsofmc.block.multiblock.invalid"));
            }
            return InteractionResult.SUCCESS;
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
