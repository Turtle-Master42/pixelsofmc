package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

public class AbstractPort extends AbstractMultiBlock {
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 2);
    public static final DirectionProperty PUSH_DIRECTION = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
    public static final BooleanProperty PUSHING = BooleanProperty.create("pushing");

    public AbstractPort(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MODE, PUSHING, PUSH_DIRECTION);
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(MODE, 0)
                .setValue(PUSH_DIRECTION, pContext.getNearestLookingDirection().equals(Direction.UP) || pContext.getNearestLookingDirection().equals(Direction.DOWN) ? pContext.getNearestLookingDirection().getOpposite() : pContext.getNearestLookingDirection())
                .setValue(PUSHING, false);
    }

    @Deprecated
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        Item mainHand = pPlayer.getMainHandItem().getItem();
        Item offHand = pPlayer.getOffhandItem().getItem();
        if (mainHand == POMitems.SCREWDRIVER.get() || offHand == POMitems.SCREWDRIVER.get()) {
            pLevel.setBlock(pPos, pState.cycle(MODE).setValue(PUSHING, false), 3);
            pLevel.playLocalSound(pPos, SoundEvents.NETHERITE_BLOCK_FALL, SoundSource.BLOCKS, 0.6f, 0.7f, false);
            pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block." + pState.getBlock().asItem() + "." + pState.getValue(MODE)), true);
            return InteractionResult.SUCCESS;
        }
        if (mainHand == POMitems.HAMMER.get() || offHand == POMitems.HAMMER.get()) {
            Direction hitDirection =  pHit.getDirection().equals(Direction.UP) || pHit.getDirection().equals(Direction.DOWN) ? pHit.getDirection() : pHit.getDirection().getOpposite();
            if (pState.getValue(PUSH_DIRECTION).equals(hitDirection)) {
                pLevel.setBlock(pPos, pState.cycle(PUSHING).setValue(MODE, 2), 3);
                if (!pState.getValue(PUSHING))
                    pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block." + pState.getBlock().asItem() + ".force_output"), true);
                else
                    pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block." + pState.getBlock().asItem() + ".1"), true);

            } else {
                pLevel.setBlock(pPos, pState.setValue(PUSH_DIRECTION, hitDirection).setValue(PUSHING, true).setValue(MODE, 2), 3);
                pPlayer.displayClientMessage(Component.translatable("message.pixelsofmc.block." + pState.getBlock().asItem() + ".force_output"), true);
            }
            pLevel.playLocalSound(pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.6f, 0.7f, false);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
