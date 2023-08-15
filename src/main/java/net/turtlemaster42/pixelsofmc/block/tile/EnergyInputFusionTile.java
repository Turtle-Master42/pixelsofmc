package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class EnergyInputFusionTile extends AbstractMultiBlockTile{

    protected final ContainerData data;

    private final int capacity = 512000;
    private final int maxReceive = 512000;



    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive, maxReceive) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                //credits Cyclic
                if (getMainPos() == worldPosition)
                    return 0;
                BlockPos posTarget = getMainPos();
                BlockEntity tile = level.getBlockEntity(posTarget);
                if (tile != null) {
                    IEnergyStorage EnergyHandlerFrom = tile.getCapability(ForgeCapabilities.ENERGY, Direction.UP.getOpposite()).orElse(null);
                    if (EnergyHandlerFrom != null) {
                        //ok go
                        int receive = EnergyHandlerFrom.receiveEnergy(maxReceive, true);
                        EnergyHandlerFrom.receiveEnergy(maxReceive, simulate);
                        energyStorage.setEnergy(EnergyHandlerFrom.getEnergyStored());
                        return receive;
                    }
                }
                return 0;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return super.extractEnergy(maxExtract, simulate);
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public EnergyInputFusionTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                if (index == 0) {
                    return EnergyInputFusionTile.this.energyStorage.getEnergyStored();
                }
                return 0;
            }

            @Override
            public void set(int index, int pValue) {

            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }
}
