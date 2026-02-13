package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
}
