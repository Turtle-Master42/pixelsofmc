package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.GrinderGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.machines.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class GrinderTile extends AbstractMachineTile<GrinderTile> {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;
    private int speedUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 1024000;
    private static final int energyConsumption = 256;

    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
                setChanged();
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                onEnergyChanged();
                setChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    public GrinderTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.GRINDER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> GrinderTile.this.progress;
                    case 1 -> GrinderTile.this.maxProgress;
                    case 2 -> GrinderTile.this.speedUpgrade;
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
                    case 2 -> GrinderTile.this.speedUpgrade = value;
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
        if (slot==5)
            speedUpgradeCheck();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.grinder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new GrinderGuiMenu(pContainerId, pInventory, this, this.data);
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

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, GrinderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, GrinderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, GrinderTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(5).getCount();
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));

            if(pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
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

    private static boolean hasPower(GrinderTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(5).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
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

            entity.removeInput(2, match.get().getInput().getItems()[0].getCount());
            entity.addMultiChanceOutput(outputs, 1, 4);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}

    private void speedUpgradeCheck() {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(5).getCount();
    }

    private int energyUpgrade() {
        int amount = this.itemHandler.getStackInSlot(6).getCount();
        return energyConsumption / 10 * amount;
    }

    private int speedUpgrade() {
        int amount = this.itemHandler.getStackInSlot(5).getCount();
        return maxProgress / 10 * amount;
    }


    private static boolean canInsertItemIntoSlot(ItemStack slotStack, ItemStack inputStack) {
        return (inputStack.getItem()==slotStack.getItem() && slotStack.getCount() + inputStack.getCount() <= slotStack.getMaxStackSize()) || slotStack.isEmpty();
    }

    private static boolean canInsertIntoOutputSlot (GrinderTile entity, GrinderRecipe match) {
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

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}",energyStorage.getEnergyStored() ,energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

}


