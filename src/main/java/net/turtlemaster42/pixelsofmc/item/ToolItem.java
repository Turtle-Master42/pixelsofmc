package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.tile.dummy.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class ToolItem extends Item {
    public ToolItem(Properties pProperties) {
        super(pProperties);
    }

    //immersive engineering
    @Nonnull
    @Override
    public ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack) {
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
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();

        // SCREWDRIVER LOGIC
        if (pContext.getItemInHand().is(POMitems.SCREWDRIVER.get())) {
            Map<Block, Integer> blocks;
            if (level.getBlockState(pos).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                blocks = multiControllerBlock.validateMultiBlock(level, pos);
            } else if (level.getBlockState(pos).getBlock() instanceof AbstractDummyMachineBlock) {
                if (level.getBlockEntity(pos) instanceof AbstractDummyMachineBlockTile dummyTile) {
                    if (level.getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                        blocks = multiControllerBlock.validateMultiBlock(level, dummyTile.getMainPos());
                    } else {
                        return InteractionResult.PASS;
                    }
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return InteractionResult.PASS;
            }
            if (blocks.isEmpty() && pContext.getPlayer() != null && !level.isClientSide()) {
                pContext.getPlayer().sendSystemMessage(Component.translatable("message.pixelsofmc.block.multiblock.invalid"));
            }
            return InteractionResult.SUCCESS;

        // CLEANING CLOTH LOGIC
        } else if (pContext.getItemInHand().is(POMitems.CLEANING_CLOTH.get())) {
            BlockState modifiedState = level.getBlockState(pos).getBlock().getToolModifiedState(level.getBlockState(pos), pContext, ToolActions.AXE_WAX_OFF, false);
            Optional<BlockState> optionalBlockState = Optional.ofNullable(modifiedState);
            if (optionalBlockState.isPresent()) {
                level.setBlock(pos, optionalBlockState.get(), 11);
                level.levelEvent(pContext.getPlayer(), 3004, pos, 0); // wax_off particles (LevelRenderer)
                return InteractionResult.SUCCESS;
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
