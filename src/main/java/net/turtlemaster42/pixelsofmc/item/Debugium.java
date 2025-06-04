package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.turtlemaster42.pixelsofmc.block.AbstractMultiControllerBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.AbstractDummyMachineBlock;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

public class Debugium extends ElementItem {
    public Debugium(Element e, Properties properties) {
        super(e, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AbstractMultiControllerBlock multiController) {
            multiController.forcePlaceMultiBlock(context.getLevel(), context.getClickedPos());
            return InteractionResult.SUCCESS;
        } else if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AbstractDummyMachineBlock) {
            if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof AbstractDummyMachineBlockTile dummyTile) {
                if (context.getLevel().getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractMultiControllerBlock multiController) {
                    multiController.forcePlaceMultiBlock(context.getLevel(), dummyTile.getMainPos());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        Level level = player.level();
        if (level.isClientSide() || !player.isCrouching()) {return super.onBlockStartBreak(itemstack, pos, player);}

        if (level.getBlockState(pos).getBlock() instanceof AbstractMultiControllerBlock multiController) {
            multiController.forceRemoveMultiBlock(level, pos);
        } else if (level.getBlockState(pos).getBlock() instanceof AbstractDummyMachineBlock) {
            if (level.getBlockEntity(pos) instanceof AbstractDummyMachineBlockTile dummyTile) {
                if (level.getBlockState(dummyTile.getMainPos()).getBlock() instanceof AbstractMultiControllerBlock multiController) {
                    multiController.forceRemoveMultiBlock(level, dummyTile.getMainPos());
                }
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }

//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color white: ( 1,000E+0  1,000E+0  1,000E+0)
//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color green: ( 0,000E+0  1,000E+0  4,902E-1)
//    [10:50:45] [Server thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color blue: ( 0,000E+0  1,000E+0  1,000E+0)
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color white: -1
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color green: -16711811
//    [10:57:02] [Render thread/INFO] [ne.tu.pi.PixelsOfMc/]: Color blue: -16711681

}