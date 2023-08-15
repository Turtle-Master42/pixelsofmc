package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import org.jetbrains.annotations.NotNull;

public class AbstractFusionCasing extends AbstractMultiBlock {
    public static final IntegerProperty PLATING = IntegerProperty.create("plating", 0, 6);
    public AbstractFusionCasing(Properties pProperties) {
        super(pProperties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PLATING);
    }

    @Deprecated
    @Override
    public @NotNull InteractionResult use(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        Item mainHand = pPlayer.getMainHandItem().getItem();
        Item offHand = pPlayer.getOffhandItem().getItem();
        if (pState.getValue(PLATING) == 0) {
            int plate = 0;

            if (mainHand == POMitems.TITANIUM_PLATING.get()) {
                plate = 1;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (mainHand == POMitems.TITANIUM_DIBORIDE_PLATING.get()) {
                plate = 2;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (mainHand == POMitems.TITANIUM_GOLD_PLATING.get()) {
                plate = 3;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (mainHand == POMitems.NETHERITE_PLATING.get()) {
                plate = 4;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (mainHand == POMitems.OBSIDIAN_PLATING.get()) {
                plate = 5;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (mainHand == POMitems.CRYING_OBSIDIAN_PLATING.get()) {
                plate = 6;
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
            } else if (offHand == POMitems.TITANIUM_PLATING.get()) {
                plate = 1;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            } else if (offHand == POMitems.TITANIUM_DIBORIDE_PLATING.get()) {
                plate = 2;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            } else if (offHand == POMitems.TITANIUM_GOLD_PLATING.get()) {
                plate = 3;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            } else if (offHand == POMitems.NETHERITE_PLATING.get()) {
                plate = 4;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            } else if (offHand == POMitems.OBSIDIAN_PLATING.get()) {
                plate = 5;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            } else if (offHand == POMitems.CRYING_OBSIDIAN_PLATING.get()) {
                plate = 6;
                if (!pPlayer.isCreative())
                    pPlayer.getOffhandItem().shrink(1);
            }

            if (plate > 0) {
                pLevel.playLocalSound(pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.4f, 1.2f, false);
                pLevel.setBlock(pPos, pState.setValue(PLATING, plate), 3);
                return InteractionResult.SUCCESS;
            }
        }
        if ((mainHand == POMitems.SCREWDRIVER.get() || offHand == POMitems.SCREWDRIVER.get()) && pState.getValue(PLATING) != 0) {
            int plating = pState.getValue(PLATING);
            Item plateItem = Items.AIR;
            if (plating == 1) {
                plateItem = POMitems.TITANIUM_PLATING.get();
            } else if (plating == 2) {
                plateItem = POMitems.TITANIUM_DIBORIDE_PLATING.get();
            } else if (plating == 3) {
                plateItem = POMitems.TITANIUM_GOLD_PLATING.get();
            } else if (plating == 4) {
                plateItem = POMitems.NETHERITE_PLATING.get();
            } else if (plating == 5) {
                plateItem = POMitems.OBSIDIAN_PLATING.get();
            } else if (plating == 6) {
                plateItem = POMitems.CRYING_OBSIDIAN_PLATING.get();
            }
            popResourceFromFace(pLevel, pPos, pHit.getDirection(), new ItemStack(plateItem));
            pLevel.playLocalSound(pPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.3f, 1.8f, false);
            pLevel.setBlock(pPos, pState.setValue(PLATING, 0), 3);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void destroy(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        onDestroy((Level) pLevel, pPos, pState);
        super.destroy(pLevel, pPos, pState);
    }

    public void onDestroy(@NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pState) {
        if (pState.getValue(PLATING) == 1) {
            popResource(pLevel, pPos, new ItemStack(POMitems.TITANIUM_PLATING.get()));
        } else if (pState.getValue(PLATING) == 2) {
            popResource(pLevel, pPos, new ItemStack(POMitems.TITANIUM_DIBORIDE_PLATING.get()));
        } else if (pState.getValue(PLATING) == 3) {
            popResource(pLevel, pPos, new ItemStack(POMitems.TITANIUM_GOLD_PLATING.get()));
        } else if (pState.getValue(PLATING) == 4) {
            popResource(pLevel, pPos, new ItemStack(POMitems.NETHERITE_PLATING.get()));
        } else if (pState.getValue(PLATING) == 5) {
            popResource(pLevel, pPos, new ItemStack(POMitems.OBSIDIAN_PLATING.get()));
        } else if (pState.getValue(PLATING) == 6) {
            popResource(pLevel, pPos, new ItemStack(POMitems.CRYING_OBSIDIAN_PLATING.get()));
        }
    }
}
