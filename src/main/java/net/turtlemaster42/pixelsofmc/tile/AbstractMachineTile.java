package net.turtlemaster42.pixelsofmc.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMachineTile<Tile extends BlockEntity> extends AbstractBaseMachineTile<Tile> implements IEnergyHandlingTile, IFluidHandlingTile {

    public final int capacity;
    public final int maxReceive;
    public final int energyConsumption;


    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        this(pType, pWorldPosition, pBlockState, 1024000, 512);
    }

    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int capacity, int energyConsumption) {
        this(pType, pWorldPosition, pBlockState, capacity, capacity, energyConsumption);
    }

    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int capacity, int maxReceive, int energyConsumption) {
        super(pType, pWorldPosition, pBlockState);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.energyConsumption = energyConsumption;

        if (capacity > 0) {
            this.energyStorage = createEnergyStorage();
        } else {
            this.energyStorage = null;
        }
    }

    public final PixelEnergyStorage energyStorage;

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

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                setChanged();
                if (maxExtract > 0 && !simulate) {
                    onEnergyChanged();
                }
                return super.extractEnergy(maxExtract, simulate);
            }
        };
    }



    protected LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    @Override
    public void onLoad() {
        super.onLoad();
        if (capacity > 0) lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        if (capacity > 0) lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        if (capacity > 0) {
            tag.putInt("powerCapacity", capacity);
            tag.putInt("Energy", energyStorage.getEnergyStored());
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        if (capacity > 0) {
            energyStorage.setEnergy(nbt.getInt("Energy"));
        }
    }


    // -- CRAFTING -- //

    public static boolean canInsertOutputFluid(FluidStack resultFluid, FluidTank tank) {
        return resultFluid.equals(tank.getFluid()) && resultFluid.getAmount() <= tank.getSpace() || tank.isEmpty() || resultFluid.isEmpty();
    }

    public static boolean canExtractInputFluid(FluidStack fluidInput, FluidTank tank) {
        return fluidInput.equals(tank.getFluid()) && fluidInput.getAmount() <= tank.getFluidAmount() || fluidInput.isEmpty();
    }

    public void addFluidOutput(FluidStack fluidOutput, FluidTank tank) {
        if (fluidOutput.isEmpty() || fluidOutput.getAmount() <= 0) {
            return;
        }
        if (fluidOutput.equals(tank.getFluid()) || tank.isEmpty()) {
            tank.fill(fluidOutput, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public void removeFluidInput(FluidStack fluidInput, FluidTank tank) {
        if (fluidInput.isEmpty() || fluidInput.getAmount() <= 0 || !fluidInput.equals(tank.getFluid())) {
            return;
        }
        tank.drain(fluidInput.getAmount(), IFluidHandler.FluidAction.EXECUTE);
    }





    // -- ENERGY -- //

    @Override
    public void setEnergyLevel(int energyLevel) {
        energyStorage.setEnergy(energyLevel);
    }

    @Override
    public PixelEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    protected void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}", energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    protected boolean hasPower(int energySlot, int speedSlot) {
        int speedAmount = itemHandler.getStackInSlot(speedSlot).getCount();
        return energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (energyUpgrade(energySlot, speedSlot) * speedAmount));
    }

    protected boolean hasPower(int energySlot) {
        return energyStorage.getEnergyStored() >= (energyConsumption - energyUpgrade(energySlot));
    }

    protected void consumePower(int energySlot, int speedSlot) {
        int speedAmount = itemHandler.getStackInSlot(speedSlot).getCount();
        energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (energyUpgrade(energySlot, speedSlot) * speedAmount));
    }

    protected void consumePower(int energySlot) {
        energyStorage.consumeEnergy(energyConsumption - energyUpgrade(energySlot));
    }

    public int energyUpgrade(int energySlot, int speedSlot) {
        return Math.round(energyConsumption / (1 + 0.125f * (this.itemHandler.getStackInSlot(energySlot).getCount() - this.itemHandler.getStackInSlot(speedSlot).getCount())));
    }

    public int energyUpgrade(int energySlot) {
        return Math.round(energyConsumption / (1 + 0.125f * this.itemHandler.getStackInSlot(energySlot).getCount()));
    }


    // -- FLUIDS -- //

    @Override
    public void setFluid(FluidStack fluid) {}

    @Override
    public FluidStack getFluid() {
        return null;
    }

    @Override
    public FluidTank getFluidTank() {return null;}
}
