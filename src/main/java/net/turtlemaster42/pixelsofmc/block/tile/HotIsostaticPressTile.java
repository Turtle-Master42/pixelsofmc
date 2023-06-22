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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;


public class HotIsostaticPressTile extends AbstractMachineTile<HotIsostaticPressTile> {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 144;
    private int speedUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 4096;
    private static final int energyConsumption = 512;
    private int heat;
    private int maxHeat = 2500;
    private int maxSoulHeat = 5000;

    private static int requiredHeat = -1;
    private static int requiredMaxHeat = -1;
    private int burnTime = 0;
    private int soulBurnTime = 0;
    private int maxBurnTime = 0;

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

    public HotIsostaticPressTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.HOT_ISOSTATIC_PRESS.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return HotIsostaticPressTile.this.progress;
                    case 1: return HotIsostaticPressTile.this.maxProgress;
                    case 2: return HotIsostaticPressTile.this.speedUpgrade;
                    case 3: return HotIsostaticPressTile.this.capacity;
                    case 4: return HotIsostaticPressTile.this.maxReceive;
                    case 5: return HotIsostaticPressTile.this.energyStorage.getEnergyStored();
                    case 6: return HotIsostaticPressTile.this.heat;
                    case 7: return HotIsostaticPressTile.this.maxHeat;
                    case 8: return HotIsostaticPressTile.this.burnTime;
                    case 9: return HotIsostaticPressTile.this.soulBurnTime;
                    case 10: return HotIsostaticPressTile.this.maxBurnTime;
                    case 11: return HotIsostaticPressTile.this.maxSoulHeat;
                    default: return 0;
                }
            }
            public void set(int index, int value) {
                switch(index) {
                    case 0: HotIsostaticPressTile.this.progress = value; break;
                    case 1: HotIsostaticPressTile.this.maxProgress = value; break;
                    case 2: HotIsostaticPressTile.this.speedUpgrade = value; break;
                    case 6: HotIsostaticPressTile.this.heat = value; break;
                    case 7: HotIsostaticPressTile.this.maxHeat = value; break;
                    case 8: HotIsostaticPressTile.this.burnTime = value; break;
                    case 9: HotIsostaticPressTile.this.soulBurnTime = value; break;
                    case 10: HotIsostaticPressTile.this.maxBurnTime = value; break;
                    case 11: HotIsostaticPressTile.this.maxSoulHeat = value; break;
                }
            }

            public int getCount() {
                return 11;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot == 1)
            return ForgeHooks.getBurnTime(stack, null) > 0 || stack.is(Items.ICE) || stack.is(Items.PACKED_ICE) || stack.is(Items.BLUE_ICE);
        return slot != 3 ;
    }

    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 3;
    }
    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==4)
            speedUpgradeCheck();
        else if (slot==1)
            if (itemHandler.getStackInSlot(1).is(Items.ICE)) {
                heat = heat - 50;
                itemHandler.extractItem(1, 1, false);
            }
            else if (itemHandler.getStackInSlot(1).is(Items.PACKED_ICE)) {
                heat = heat - 250;
                itemHandler.extractItem(1, 1, false);
            }
            else if (itemHandler.getStackInSlot(1).is(Items.BLUE_ICE)) {
                heat = heat - 1000;
                itemHandler.extractItem(1, 1, false);
            }
        if (heat < 0) heat=0;
    }

    public int getMaxTime() {return maxBurnTime;}
    public int getHeat() {return heat;}
    public int getRequiredHeat() {return requiredHeat;}
    public int getRequiredMaxHeat() {return requiredMaxHeat;}

    @Override
    public Component getDisplayName() {return Component.translatable("block.pixelsofmc.hot_isostatic_press");}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new HotIsostaticPressGuiMenu(pContainerId, pInventory, this, this.data);
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
        tag.putInt("heat", heat);
        tag.putInt("burnTime", burnTime);
        tag.putInt("soulBurnTime", soulBurnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        heat = nbt.getInt("heat");
        burnTime = nbt.getInt("burnTime");
        soulBurnTime = nbt.getInt("soulBurnTime");
        maxBurnTime = nbt.getInt("maxBurnTime");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HotIsostaticPressTile pBlockEntity) {

        if(ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) > 0 && burnTime == 0 && soulBurnTime == 0) {
            int time = ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) / 20;
            maxBurnTime=time;
            if (itemHandler.getStackInSlot(1).is(POMtags.Items.SOUL_FUELS))
                soulBurnTime=time;
            else
                burnTime=time;

            if (!itemHandler.getStackInSlot(1).getCraftingRemainingItem().isEmpty() && itemHandler.getStackInSlot(1).getMaxStackSize() == 1)
                itemHandler.setStackInSlot(1, itemHandler.getStackInSlot(1).getCraftingRemainingItem());
            else
                itemHandler.extractItem(1, 1, false);
        }
        createHeat();
        createSoulHeat();

        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(4).getCount();
            pBlockEntity.speedUpgradeCheck();
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

    private static boolean hasRecipe(HotIsostaticPressTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<HotIsostaticPressRecipe> match = level.getRecipeManager()
                .getRecipeFor(HotIsostaticPressRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            requiredHeat = match.get().getHeat();
            requiredMaxHeat = match.get().getMaxHeat();
            return canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                    && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
                    && hasHeat(entity, match.get().getHeat())
                    && !hasHeat(entity, match.get().getMaxHeat()+1);
        }
        else return false;
    }

    private static boolean hasPower(HotIsostaticPressTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(4).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
    }
    private static boolean hasHeat(HotIsostaticPressTile entity, int heat) {
        return entity.getHeat() > heat;
    }


    private static void craftItem(HotIsostaticPressTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<HotIsostaticPressRecipe> match = level.getRecipeManager()
                .getRecipeFor(HotIsostaticPressRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {

            entity.itemHandler.extractItem(2, match.get().getInput().getCount(), false);

            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + (match.get().getOutputCount())));

            requiredHeat = -1;
            requiredMaxHeat = -1;
            setChanged(entity.level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}
    private void createHeat() {
        if (burnTime > 0) {
            int upgrade = this.itemHandler.getStackInSlot(6).getCount() + 1;
            this.burnTime = this.burnTime - upgrade;
            if (this.burnTime < 0) this.burnTime = 0;
            if (heat < maxHeat) {
                this.heat = this.heat + upgrade;
                if (this.heat > maxHeat) this.heat = maxHeat;
            }
        }
    }
    private void createSoulHeat() {
        if (soulBurnTime > 0) {
            int upgrade = this.itemHandler.getStackInSlot(6).getCount() + 1;
            this.soulBurnTime = this.soulBurnTime - upgrade;
            if (this.soulBurnTime < 0) this.soulBurnTime = 0;
            if (heat < maxSoulHeat) {
                this.heat = this.heat + upgrade;
                if (this.heat > maxSoulHeat) this.heat = maxSoulHeat;
            }
        }
    }

    private void speedUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(4).getItem() == POMitems.SPEED_UPGRADE.get()) {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(4).getCount();
        } else {
            this.speedUpgrade = 0;
        }
    }

    private int energyUpgrade() {
        int amount = this.itemHandler.getStackInSlot(5).getCount();
        return energyConsumption / 10 * amount;
    }

    private int speedUpgrade() {
        int amount = this.itemHandler.getStackInSlot(4).getCount();
        return maxProgress / 10 * amount;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(3).getMaxStackSize() >= inventory.getItem(3).getCount() + count;
    }


    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy " + energyStorage.getEnergyStored() + " is higher than max " + energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at " + this.getBlockPos() + " was outside limits, energy reverted to 0");
        }
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }
    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

}

