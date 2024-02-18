package net.turtlemaster42.pixelsofmc.block.tile;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.BallMillBlock;
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.BallMillGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.machines.BallMillRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;


public class BallMillTile extends AbstractMachineTile<BallMillTile> {

    protected final ContainerData data;
    public int progress = 0;
    public int maxProgress = 120;
    private int speedUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 1024000;
    private static final int energyConsumption = 512;
    public int rotation = 0;

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
                setChanged();
                if (maxReceive > 0 && !simulate) {
                    onEnergyChanged();
                }
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public BallMillTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.BALL_MILL.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> BallMillTile.this.progress;
                    case 1 -> BallMillTile.this.maxProgress;
                    case 2 -> BallMillTile.this.speedUpgrade;
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
                    case 2 -> BallMillTile.this.speedUpgrade = value;
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
        if (slot==5)
            speedUpgradeCheck();
    }


    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.ball_mill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new BallMillGuiMenu(pContainerId, pInventory, this, this.data);
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

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BallMillTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, BallMillTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BallMillTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(5).getCount();
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));
            if (pBlockEntity.progress > 0 && !pState.getValue(BallMillBlock.ACTIVE)) {
                level.setBlock(pPos, pState.setValue(BallMillBlock.ACTIVE, true), 2);
            }
            if(pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
                craftItem(pBlockEntity);
                level.setBlock(pPos, pState.setValue(BallMillBlock.ACTIVE, false), 2);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
        if (pState.getValue(BallMillBlock.ACTIVE)) {
            pBlockEntity.rotation++;
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
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static boolean hasPower(BallMillTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(5).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
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

            // Iterate over the recipeItems
            for (CountedIngredient recipeItem : recipeItems) {
                // Iterate over the slots
                for (int slot = 0; slot < 3; slot++) {
                    if (Ingredient.of(recipeItem.asItemStack()).test(inventory.getItem(slot))) {
                        ItemStack slotStack = entity.itemHandler.getStackInSlot(slot);
                        if (slotStack.getCount() < recipeItem.count()) {
                            entity.itemHandler.extractItem(slot, slotStack.getCount(), false);
                            recipeItem = CountedIngredient.of(recipeItem.count() - slotStack.getCount(), recipeItem.asItem());
                        } else {
                            entity.itemHandler.extractItem(slot, recipeItem.count(), false);
                            break;
                        }
                    }
                }
            }

            if (entity.itemHandler.getStackInSlot(3).isDamageableItem()) {
                entity.itemHandler.getStackInSlot(3).hurt(1, RandomSource.create(), null); //ball
            } else {
                entity.itemHandler.getStackInSlot(3).shrink(1);
            }

            entity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(4).getCount() + (match.get().getOutputCount())));


            setChanged(entity.level, entity.worldPosition, entity.getBlockState());
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

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(4).getItem() == output.getItem() || inventory.getItem(4).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + count;
    }


    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy " + energyStorage.getEnergyStored() + " is higher than max " + energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at " + this.getBlockPos() + " was outside limits, energy reverted to 0");
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    @Override
    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

}

