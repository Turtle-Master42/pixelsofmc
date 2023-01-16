package net.turtlemaster42.pixelsofmc.block.tile;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
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
    private int maxHeat = 5000;
    private int burnTime = 0;
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
        super(POMtiles.HOT_ISOTOPIC_PRESS.get(), pWorldPosition, pBlockState);
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
                    case 9: return HotIsostaticPressTile.this.maxBurnTime;
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
                    case 9: HotIsostaticPressTile.this.maxBurnTime = value; break;
                }
            }

            public int getCount() {
                return 9;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return slot < 3;
    }

    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 3;
    }
    @Override
    protected int itemHandlerSize() {return 6;}

    public int GetMaxTime() {return maxBurnTime;}
    public int getHeat() {return heat;}

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.pixelsofmc.hot_isotopic_press");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new HotIsostaticPressGuiMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
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
        maxBurnTime = nbt.getInt("maxBurnTime");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HotIsostaticPressTile pBlockEntity) {

        if(ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) > 0 && burnTime == 0) {
            int time = ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) / 20;
            maxBurnTime=time;
            burnTime=time;
            itemHandler.extractItem(1, 1, false);
        }
        createHeat();

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

        return match.isPresent()
                && canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
                && hasHeat(entity, match.get().getHeat());
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


            setChanged(entity.level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}
    private void createHeat() {
        if (burnTime > 0 && heat < maxHeat) {
            this.burnTime--;
            this.heat++;
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
    public IEnergyStorage getEnergyStorage() { return energyStorage; }

}

