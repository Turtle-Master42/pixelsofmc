package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.NuclearReactorMenu;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.block.IButtonTile;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class NuclearReactorTile extends AbstractMachineTile<NuclearReactorTile> implements IDuoFluidHandlingTile, IEnergyHandlingTile, IButtonTile {

    protected final ContainerData data;
    private final int capacity = 8_192_000;
    private final int maxReceive = 512_000;
    private static final int energyConsumption = 100;

    public boolean[] switches = new boolean[]{false, false, false, false, false, false, false};



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
                onEnergyChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                onEnergyChanged();
                return super.extractEnergy(maxExtract, simulate);
            }
        };
    }

    public long getEnergyPercentage() {
        return energyStorage.getEnergyStored() / capacity;
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public final FluidTank fluidTank = new FluidTank(250000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    public final FluidTank duoFluidTank = new FluidTank(250000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    @Override
    public void setFluid(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    @Override
    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    @Override
    public void setDuoFluid(FluidStack fluid) {
        this.duoFluidTank.setFluid(fluid);
    }

    @Override
    public FluidStack getDuoFluid() {
        return this.duoFluidTank.getFluid();
    }
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();



    public NuclearReactorTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.NUCLEAR_REACTOR.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> NuclearReactorTile.this.maxReceive;
                    case 1 -> NuclearReactorTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
            }

            public int getCount() {
                return 2;
            }
        };
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return true;
    }

    @Override
    protected int getSlotLimits(int slot) {
        return 1;
    }

    @Override
    protected int itemHandlerSize() {return 4;}

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.nuclear_reactor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        return new NuclearReactorMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && side == Direction.DOWN) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == Direction.UP)
                return lazyDuoFluidHandler.cast();
            else return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        CompoundTag slotTag = new CompoundTag();
        tag.put("lockedSlot", slotTag);
        tag.putBoolean("redSwitch1", switches[0]);
        tag.putBoolean("redSwitch2", switches[1]);
        tag.putBoolean("redSwitch3", switches[2]);
        tag.putBoolean("greenSwitch1", switches[3]);
        tag.putBoolean("greenSwitch2", switches[4]);
        tag.putBoolean("greenSwitch3", switches[5]);
        tag.putBoolean("greenSwitch4", switches[6]);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        energyStorage.setEnergy(nbt.getInt("Energy"));
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
        switches[0] = nbt.getBoolean("redSwitch1");
        switches[1] = nbt.getBoolean("redSwitch2");
        switches[2] = nbt.getBoolean("redSwitch3");
        switches[3] = nbt.getBoolean("greenSwitch1");
        switches[4] = nbt.getBoolean("greenSwitch2");
        switches[5] = nbt.getBoolean("greenSwitch3");
        switches[6] = nbt.getBoolean("greenSwitch4");
    }



    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, NuclearReactorTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, NuclearReactorTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, NuclearReactorTile pBlockEntity) {
        if (getSwitch(1)) {
            FluidStack drained = fluidTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            duoFluidTank.fill(new FluidStack(POMfluids.STEAM_SOURCE.get(), drained.getAmount()), IFluidHandler.FluidAction.EXECUTE);
            setChanged(pLevel, pPos, pState);
        }
    }

    //---OTHER---//

    @Override
    public void setSwitch(boolean on, int currentSwitch) {
        this.switches[currentSwitch] = on;
        setChanged();
    }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void handleFuelCellHolder(ItemStackHandler itemHandler, int slot, boolean isLocked) {
        if (isLocked) {
            this.itemHandler.setStackInSlot(slot, itemHandler.getStackInSlot(0));
        } else {
//            setSwitch(false, 3 + slot); TODO: needs to be send to the client
            this.itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
    }

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}", energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidTank getDuoFluidTank() {
        return duoFluidTank;
    }
}
