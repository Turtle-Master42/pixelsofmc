package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CleaningClothItem extends ToolItem {
    public CleaningClothItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Player player = pContext.getPlayer();

        // CLEANING CLOTH LOGIC
        if (pContext.getItemInHand().is(POMitems.CLEANING_CLOTH.get())) {
            BlockState modifiedState = level.getBlockState(pos).getBlock().getToolModifiedState(level.getBlockState(pos), pContext, ToolActions.AXE_WAX_OFF, false);
            Optional<BlockState> optionalBlockState = Optional.ofNullable(modifiedState);
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            if (optionalBlockState.isPresent()) {
                level.setBlock(pos, optionalBlockState.get(), 11);
                level.levelEvent(player, 3004, pos, 0); // wax_off particles (LevelRenderer)
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
