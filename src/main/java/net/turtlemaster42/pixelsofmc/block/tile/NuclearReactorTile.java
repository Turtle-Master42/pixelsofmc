package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.NuclearReactorBlock;
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.NuclearReactorMenu;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.item.FuelCellItem;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncSwitchToClient;
import net.turtlemaster42.pixelsofmc.util.Constants;
import net.turtlemaster42.pixelsofmc.util.block.IButtonTile;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IMultiFluidHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class NuclearReactorTile extends AbstractMachineTile<NuclearReactorTile> implements IMultiFluidHandlingTile, IDuoFluidHandlingTile, IEnergyHandlingTile, IButtonTile {

    protected final ContainerData data;
    private final int capacity = 8_192_000;
    private final int maxReceive = 512_000;
    private static final int energyConsumption = 100;
    private float efficiency_bonus = 1f;
    private int internalHeat = 0;
    private final int internalHeatCapacity = 10_000_000;
    private int rebootCooldown = 0;

    public boolean[] switches = new boolean[]{false, false, true, false};



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
                    case 2 -> NuclearReactorTile.this.internalHeat;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
            }

            public int getCount() {
                return 3;
            }
        };
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
        tag.putInt("internalHeat", internalHeat);
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        CompoundTag slotTag = new CompoundTag();
        tag.put("lockedSlot", slotTag);
        tag.putFloat("efficiencyBonus", efficiency_bonus);
        tag.putBoolean("redSwitch1", switches[0]);
        tag.putBoolean("redSwitch2", switches[1]);
        tag.putBoolean("redSwitch3", switches[2]);
        tag.putBoolean("bigRedSwitch", switches[3]);
        tag.putInt("rebootCooldown", rebootCooldown);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        energyStorage.setEnergy(nbt.getInt("Energy"));
        internalHeat = nbt.getInt("internalHeat");
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
        efficiency_bonus = nbt.getFloat("efficiencyBonus");
        switches[0] = nbt.getBoolean("redSwitch1");
        switches[1] = nbt.getBoolean("redSwitch2");
        switches[2] = nbt.getBoolean("redSwitch3");
        switches[3] = nbt.getBoolean("bigRedSwitch");
        rebootCooldown = nbt.getInt("rebootCooldown");
    }



    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, NuclearReactorTile e) {
        e.tick(level, blockPos, blockState);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, NuclearReactorTile e) {
        e.tick(level, blockPos, blockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        //cooldown and reboot
        if (rebootCooldown > 0) {
            rebootCooldown--;
            if (rebootCooldown == 0 && getSwitch(2)) {
                setSwitch(3, true);
            }
        }

        //cooling
        if (getSwitch(0)) {
            int maxDrain = internalHeat / Constants.FE_waterToSteam;
            FluidStack drained = fluidTank.drain(maxDrain, IFluidHandler.FluidAction.EXECUTE);
            duoFluidTank.fill(new FluidStack(POMfluids.STEAM_SOURCE.get(), drained.getAmount()), IFluidHandler.FluidAction.EXECUTE);
            internalHeat -= drained.getAmount() * Constants.FE_waterToSteam;
            setChanged(pLevel, pPos, pState);
        }

        //active
        if (getSwitch(3)) {
            handleFuelCell(0);
            handleFuelCell(1);
            handleFuelCell(2);
            handleFuelCell(3);
            if (internalHeat >= internalHeatCapacity && getSwitch(1)) {
                setSwitch(3, false);
                if (getSwitch(2))
                    rebootCooldown = 200; // 10s
            }
        }

        //debug water fill TODO: remove
        if (getSwitch(2)) {
            fluidTank.fill(new FluidStack(Fluids.WATER, 20_000), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public void handleFuelCell(int slot) {
        ItemStack slotStack = itemHandler.getStackInSlot(slot);
        if (slotStack.getItem() instanceof FuelCellItem fuelCellItem) {
            internalHeat += (int) (fuelCellItem.getEnergyPerTick() * efficiency_bonus);
            if (!fuelCellItem.hasRemainderStack())
                return;

            if (fuelCellItem.isDepleted(slotStack)) {
                itemHandler.setStackInSlot(slot, FuelCellItem.getRemainderStack(slotStack));
            } else {
                fuelCellItem.deplete(slotStack);
            }
        }
    }

    //---OTHER---//

    @Override
    public void setSwitch(int currentSwitch, boolean on) {
        this.switches[currentSwitch] = on;
        setChanged();
        calculateEfficiencyBonus();
        if (!this.level.isClientSide) {
            POMmessages.sendToClients(new PacketSyncSwitchToClient(this.worldPosition, on, currentSwitch));
            if (currentSwitch == 3) {
                level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(NuclearReactorBlock.ACTIVE, on? 3 : 2), 2);
            }
        }
    }

    private void calculateEfficiencyBonus() {
        efficiency_bonus = 1f;
        if (!itemHandler.getStackInSlot(0).isEmpty() && !itemHandler.getStackInSlot(1).isEmpty()) {
            efficiency_bonus += 0.125f;
        }
        if (!itemHandler.getStackInSlot(0).isEmpty() && !itemHandler.getStackInSlot(2).isEmpty()) {
            efficiency_bonus += 0.125f;
        }
        if (!itemHandler.getStackInSlot(2).isEmpty() && !itemHandler.getStackInSlot(3).isEmpty()) {
            efficiency_bonus += 0.125f;
        }
        if (!itemHandler.getStackInSlot(1).isEmpty() && !itemHandler.getStackInSlot(3).isEmpty()) {
            efficiency_bonus += 0.125f;
        }
    }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public float getEfficiencyBonus() {
        return efficiency_bonus;
    }

    public void handleFuelCellHolder(ItemStackHandler cellItemHandler, int slot, boolean isLocked) {
        if (isLocked) {
            itemHandler.setStackInSlot(slot, cellItemHandler.getStackInSlot(0));
        } else {
            cellItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(slot));
            itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
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

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{fluidTank, duoFluidTank};
    }

    @Override
    public FluidTank getFluidTank(String name) {
        return switch (name) {
            case "coolant_input" -> fluidTank;
            case "coolant_output" -> duoFluidTank;
            default -> null;
        };
    }

    @Override
    public String[] getFluidTankNames() {
        return new String[]{"coolant_input", "coolant_output"};
    }
}
