package net.turtlemaster42.pixelsofmc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.GrinderGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMblockEntities;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.GrinderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.turtlemaster42.pixelsofmc.block.GrinderBlock.FACING;


public class GrinderBlockEntity extends AbstractMachineEntity {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;
    private int speedUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 4096;
    private static final int energyConsumption = 256;

    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
        }
    };

    @Override
    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    private void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public GrinderBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMblockEntities.GRINDER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return GrinderBlockEntity.this.progress;
                    case 1: return GrinderBlockEntity.this.maxProgress;
                    case 2: return GrinderBlockEntity.this.speedUpgrade;
                    case 3: return GrinderBlockEntity.this.capacity;
                    case 4: return GrinderBlockEntity.this.maxReceive;
                    case 5: return GrinderBlockEntity.this.energyStorage.getEnergyStored();
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: GrinderBlockEntity.this.progress = value; break;
                    case 1: GrinderBlockEntity.this.maxProgress = value; break;
                    case 2: GrinderBlockEntity.this.speedUpgrade = value; break;
                }
            }

            public int getCount() {
                return 6;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.pixelsofmc.grinder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new GrinderGuiMenu(pContainerId, pInventory, this, this.data);
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
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);

        return compound;
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, GrinderBlockEntity e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, GrinderBlockEntity e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, GrinderBlockEntity pBlockEntity) {
        getEnergyFromEnergyMachineBlock(pState.getValue(FACING).getOpposite());

        if(hasRecipe(pBlockEntity) && hasPower(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(5).getCount();
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

    private static boolean hasRecipe(GrinderBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GrinderRecipe> match = level.getRecipeManager()
                .getRecipeFor(GrinderRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent()
                && canInsertAmountIntoOutputSlots(inventory, match);
    }

    private static boolean hasPower(GrinderBlockEntity entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(5).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
    }


    private static void craftItem(GrinderBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GrinderRecipe> match = level.getRecipeManager()
                .getRecipeFor(GrinderRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {

            entity.itemHandler.extractItem(0, 1, false);

            entity.itemHandler.setStackInSlot(1, new ItemStack(match.get().getResultItems(0).getItem(),
                    entity.itemHandler.getStackInSlot(1).getCount() + (match.get().getOutputsCount(0))));
            entity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItems(1).getItem(),
                    entity.itemHandler.getStackInSlot(2).getCount() + (match.get().getOutputsCount(1))));
            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItems(2).getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + (match.get().getOutputsCount(2))));
            entity.itemHandler.setStackInSlot(4, new ItemStack(match.get().getResultItems(3).getItem(),
                    entity.itemHandler.getStackInSlot(4).getCount() + (match.get().getOutputsCount(3))));


            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}

    private void speedUpgradeCheck() {
        if (this.itemHandler.getStackInSlot(5).getItem() == POMitems.SPEED_UPGRADE.get()) {
            this.speedUpgrade = this.maxProgress / 10 * this.itemHandler.getStackInSlot(5).getCount();
        } else {
            this.speedUpgrade = 0;
        }
    }

    private int energyUpgrade() {
        int amount = this.itemHandler.getStackInSlot(6).getCount();
        return energyConsumption / 10 * amount;
    }

    private int speedUpgrade() {
        int amount = this.itemHandler.getStackInSlot(5).getCount();
        return maxProgress / 10 * amount;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, List<CountedIngredient> outputs) {
        return inventory.getItem(1).getItem() == outputs.get(0).getItems()[0].getItem()
                && inventory.getItem(2).getItem() == outputs.get(1).getItems()[1].getItem()
                && inventory.getItem(3).getItem() == outputs.get(2).getItems()[2].getItem()
                && inventory.getItem(4).getItem() == outputs.get(3).getItems()[3].getItem()

                || inventory.getItem(1).isEmpty()
                && inventory.getItem(2).isEmpty()
                && inventory.getItem(3).isEmpty()
                && inventory.getItem(4).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlots(SimpleContainer inventory, Optional<GrinderRecipe> match) {
        return inventory.getItem(1).getMaxStackSize() >= inventory.getItem(1).getCount() + match.get().getOutputsCount(0) &&
                inventory.getItem(2).getMaxStackSize() >= inventory.getItem(2).getCount() + match.get().getOutputsCount(1) &&
                inventory.getItem(3).getMaxStackSize() >= inventory.getItem(3).getCount() + match.get().getOutputsCount(2) &&
                inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + match.get().getOutputsCount(3);
    }

    //cyclic
    public void getEnergyFromEnergyMachineBlock(Direction extractSide) {
        if (extractSide == null) {
            return;
        }
        BlockPos posTarget = this.worldPosition.relative(extractSide);
        BlockEntity tile = level.getBlockEntity(posTarget);
        if (tile != null) {
            IEnergyStorage EnergyHandlerFrom = tile.getCapability(CapabilityEnergy.ENERGY, extractSide.getOpposite()).orElse(null);
            if (EnergyHandlerFrom != null) {
                //ok go
                int extractSim = EnergyHandlerFrom.extractEnergy(maxReceive, true);
                if (extractSim > 0 && energyStorage.receiveEnergy(extractSim, true) > 0) {
                    //actually extract energy for real, whatever it accepted
                    EnergyHandlerFrom.extractEnergy(energyStorage.receiveEnergy(extractSim, false), false);
                    POMmessages.sendToClients(new PacketSyncEnergyToClient(energyStorage.getEnergyStored(), worldPosition));
                }
            }
        }
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


