package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.tile.dummy.AbstractDummyMachineBlockTile;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ScrewDriverItem extends ToolItem {
    public ScrewDriverItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Player player = pContext.getPlayer();

        if (pContext.getItemInHand().is(POMitems.SCREWDRIVER.get())) {
            Map<Block, Integer> blocks;
            if (level.getBlockState(pos).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                blocks = multiControllerBlock.validateMultiBlock(level, pos, true);
            } else if (level.getBlockState(pos).getBlock() instanceof AbstractDummyMachineBlock) {
                if (level.getBlockEntity(pos) instanceof AbstractDummyMachineBlockTile dummyTile) {
                    if (level.getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractMultiControllerBlock multiControllerBlock) {
                        blocks = multiControllerBlock.validateMultiBlock(level, dummyTile.getMainPos(), true);
                    } else {
                        return InteractionResult.PASS;
                    }
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return InteractionResult.PASS;
            }
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                player.getCooldowns().addCooldown(this, 8);
                if (blocks.isEmpty() && !level.isClientSide()) {
                    player.sendSystemMessage(Component.translatable("message.pixelsofmc.block.multiblock.invalid"));
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
