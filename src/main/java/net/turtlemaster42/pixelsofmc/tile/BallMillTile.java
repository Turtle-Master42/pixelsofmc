package net.turtlemaster42.pixelsofmc.tile;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.turtlemaster42.pixelsofmc.block.BallMillBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.BallMillMenu;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;


public class BallMillTile extends AbstractMachineTile<BallMillTile> {

    protected final ContainerData data;
    public int rotation = 0;

    public BallMillTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.BALL_MILL.get(), pWorldPosition, pBlockState);
        defineMaxProgress(96);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> BallMillTile.this.progress;
                    case 1 -> BallMillTile.this.maxProgress;
                    case 2 -> BallMillTile.this.requiredProgress;
                    case 3 -> BallMillTile.this.capacity;
                    case 4 -> BallMillTile.this.maxReceive;
                    case 5 -> BallMillTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BallMillTile.this.progress = value;
                    case 1 -> BallMillTile.this.maxProgress = value;
                    case 2 -> BallMillTile.this.requiredProgress = value;
                }
            }

            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot == 3) return stack.is(POMtags.Items.MILLING_BALL);
        else if (slot==5) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==6) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        else return slot <= 3;
    }

    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 4;
    }
    @Override
    protected int itemHandlerSize() {return 7;}

    protected void contentsChanged(int slot) {
        if (slot==5) speedUpgradeCheck(5);
    }

    @Override
    protected LazyOptional<? extends IItemHandler>[] createSidedInventory() {
        return SidedInvWrapper.create(this, Direction.UP, Direction.NORTH, Direction.DOWN);
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return switch (side) {
            case UP -> new int[]{0, 1, 2};
            case DOWN -> new int[]{4};
            default -> new int[]{3};
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.ball_mill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new BallMillMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (lazyPartialItemHandlers != null && side != null) {
                return switch (side) {
                    case UP -> lazyPartialItemHandlers[0].cast();
                    case DOWN -> lazyPartialItemHandlers[2].cast();
                    default -> lazyPartialItemHandlers[1].cast();
                };
            }
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BallMillTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, BallMillTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BallMillTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasPower(6, 5)) {
            progress++;
            consumePower(6, 5);
            if (pBlockEntity.progress > 0 && !pState.getValue(BallMillBlock.ACTIVE)) {
                pLevel.setBlock(pPos, pState.setValue(BallMillBlock.ACTIVE, true), 2);
            }
            if(pBlockEntity.progress > pBlockEntity.requiredProgress) {
                craftItem(pBlockEntity);
                pLevel.setBlock(pPos, pState.setValue(BallMillBlock.ACTIVE, false), 2);
            }
        } else {
            if (pState.getValue(BallMillBlock.ACTIVE) && !pLevel.isClientSide()) {
                pLevel.setBlock(pPos, pState.setValue(BallMillBlock.ACTIVE, false), 2);
            }
            resetProgress();
            setChanged(pLevel, pPos, pState);
        }
        if (pState.getValue(BallMillBlock.ACTIVE)) {
            rotation++;
        }
    }

    private static boolean hasRecipe(BallMillTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BallMillRecipe> match = level.getRecipeManager()
                .getRecipeFor(BallMillRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent()
                && canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().asItemStack());
    }


    private static void craftItem(BallMillTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BallMillRecipe> match = level.getRecipeManager()
                .getRecipeFor(BallMillRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            List<CountedIngredient> recipeItems = match.get().getInputs();

            entity.removeMultiInput(recipeItems, 0, 2);
            entity.addOverflowChanceOutput(match.get().getOutput(), 4);

            if (entity.itemHandler.getStackInSlot(3).isDamageableItem()) {
                entity.itemHandler.getStackInSlot(3).hurt(1, RandomSource.create(), null); //ball
            } else {
                entity.itemHandler.getStackInSlot(3).shrink(1);
            }

            setChanged(entity.level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(4).getItem() == output.getItem() || inventory.getItem(4).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + count;
    }
}

