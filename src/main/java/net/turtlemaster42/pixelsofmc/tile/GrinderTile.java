package net.turtlemaster42.pixelsofmc.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.turtlemaster42.pixelsofmc.gui.menu.GrinderMenu;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class GrinderTile extends AbstractMachineTile<GrinderTile> {

    protected final ContainerData data;

    public GrinderTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.GRINDER.get(), pWorldPosition, pBlockState, 1024000, 256);
        defineMaxProgress(96);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> GrinderTile.this.progress;
                    case 1 -> GrinderTile.this.maxProgress;
                    case 2 -> GrinderTile.this.requiredProgress;
                    case 3 -> GrinderTile.this.capacity;
                    case 4 -> GrinderTile.this.maxReceive;
                    case 5 -> GrinderTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GrinderTile.this.progress = value;
                    case 1 -> GrinderTile.this.maxProgress = value;
                    case 2 -> GrinderTile.this.requiredProgress = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot==0) return true;
        else if (slot==5) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==6) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        return false;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot > 0 && slot < 5;
    }
    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==5) speedUpgradeCheck(5);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.grinder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new GrinderMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, GrinderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, GrinderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, GrinderTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasPower(6, 5)) {
            pBlockEntity.progress++;
            consumePower(6, 5);
            if(pBlockEntity.progress > pBlockEntity.requiredProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(GrinderTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GrinderRecipe> match = level.getRecipeManager()
                .getRecipeFor(GrinderRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertIntoOutputSlot(entity, match.get());
    }

    private static void craftItem(GrinderTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GrinderRecipe> match = level.getRecipeManager()
                .getRecipeFor(GrinderRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent() && !level.isClientSide) {
            List<ChanceIngredient> outputs = match.get().getOutputs();

            entity.removeInput(0, 1);
            entity.addMultiChanceOutput(outputs, 1, 4);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private static boolean canInsertItemIntoSlot(ItemStack slotStack, ItemStack inputStack) {
        return (inputStack.getItem()==slotStack.getItem() && slotStack.getCount() + inputStack.getCount() <= slotStack.getMaxStackSize()) || slotStack.isEmpty();
    }

    private static boolean canInsertIntoOutputSlot(GrinderTile entity, GrinderRecipe match) {
        boolean[] matched = new boolean[match.getOutputs().size()];
        boolean[] matchNeeded = new boolean[match.getOutputs().size()];
        ItemStack[] newStackInSlot = new ItemStack[5];
        ItemStack newStack;

        // Makes sure that newStackInSlot[] is not null
        for (int i = 1; i < 5; i++)
            newStackInSlot[i] = ItemStack.EMPTY;

        // Iterate over the inputs -q-
        for (int q = 0; q < match.getOutputs().size(); q++) {
            matchNeeded[q] = true;
            newStack = match.getResultItems(q);
            // Iterate over the slots -p-
            for (int p = 1; p < 5; p++) {
                if (matched[q])
                    continue;
                ItemStack[] stackInSlot = new ItemStack[5];
                stackInSlot[p] = entity.itemHandler.getStackInSlot(p);
                if (stackInSlot[p].isEmpty())
                    stackInSlot[p] = newStackInSlot[p];
                if (canInsertItemIntoSlot(stackInSlot[p], newStack)) {
                    newStackInSlot[p] = newStack;
                    newStack = entity.insertItemStack(p, newStack, true);
                    if (newStack.isEmpty()) {
                        matched[q] = true;
                    }
                }
            }
        }

        for (int i = 0; i < match.getOutputs().size(); i++) {
            if (matched[i]!=matchNeeded[i])
                return false;
        }
        return true;
    }
}


