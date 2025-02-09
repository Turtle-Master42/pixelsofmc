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
    public static final IntegerProperty PLATING = IntegerProperty.create("plating", 0, 9);

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
        Item[] PLATE_ITEMS = new Item[]{
                POMitems.TITANIUM_PLATING.get(),
                POMitems.TITANIUM_DIBORIDE_PLATING.get(),
                POMitems.TITANIUM_GOLD_PLATING.get(),
                POMitems.NETHERITE_PLATING.get(),
                POMitems.OBSIDIAN_PLATING.get(),
                POMitems.CRYING_OBSIDIAN_PLATING.get(),
                POMitems.LEAD_PLATING.get(),
                POMitems.TUNGSTEN_PLATING.get(),
                POMitems.PYROLYTIC_CARBON_SHEET.get()
        };

        if (pState.getValue(PLATING) == 0) {
            int plate = 0;

            for (int i = 0; i < PLATE_ITEMS.length; i++) {
                if (mainHand == PLATE_ITEMS[i]) {
                    plate = i+1;
                    break;
                }
            }
            if (plate > 0) {
                if (!pPlayer.isCreative())
                    pPlayer.getMainHandItem().shrink(1);
                pLevel.playLocalSound(pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.4f, 1.2f, false);
                pLevel.setBlock(pPos, pState.setValue(PLATING, plate), 3);
                return InteractionResult.SUCCESS;
            }
        }
        if ((mainHand == POMitems.SCREWDRIVER.get() || offHand == POMitems.SCREWDRIVER.get()) && pState.getValue(PLATING) > 0) {
            int plating = pState.getValue(PLATING);
            Item plateItem = PLATE_ITEMS[plating-1];
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
        int plating = pState.getValue(PLATING);
        if (plating > 0) {
            Item[] PLATE_ITEMS = new Item[]{
                    POMitems.TITANIUM_PLATING.get(),
                    POMitems.TITANIUM_DIBORIDE_PLATING.get(),
                    POMitems.TITANIUM_GOLD_PLATING.get(),
                    POMitems.NETHERITE_PLATING.get(),
                    POMitems.OBSIDIAN_PLATING.get(),
                    POMitems.CRYING_OBSIDIAN_PLATING.get(),
                    POMitems.LEAD_PLATING.get(),
                    POMitems.TUNGSTEN_PLATING.get(),
                    POMitems.PYROLYTIC_CARBON_SHEET.get()
            };
            popResource(pLevel, pPos, new ItemStack(PLATE_ITEMS[plating-1]));
        }
    }
}
