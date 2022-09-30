package net.turtlemaster42.pixelsofmc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.init.POMblockEntities;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class MachineEnergyBlockBlockEntity extends BlockEntity {
        protected final ContainerData data;

        private final int capacity = 40960;
        private final int maxReceive = 4096;



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
                    return super.receiveEnergy(maxReceive, simulate);
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    return super.extractEnergy(maxExtract, simulate);
                }
            };
        }

        private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

        public MachineEnergyBlockBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
            super(POMblockEntities.MACHINE_ENERGY_BLOCK.get(), pWorldPosition, pBlockState);
            this.data = new ContainerData() {

                @Override
                public int get(int index) {
                    switch (index) {
                        case 0: return MachineEnergyBlockBlockEntity.this.energyStorage.getEnergyStored();
                        default: return 0;
                    }
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
        if (cap == CapabilityEnergy.ENERGY) {
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
        public void load(CompoundTag nbt) {
            super.load(nbt);
            energyStorage.setEnergy(nbt.getInt("Energy"));
        }

        private BlockPos getMainPos() {
            int mainX = this.getTileData().getInt("mainX");
            int mainY = this.getTileData().getInt("mainY");
            int mainZ = this.getTileData().getInt("mainZ");
            return new BlockPos(mainX, mainY, mainZ);
        }


        //---ENERGY---//

        public void setEnergyLevel(int energyLevel) {
            this.energyStorage.setEnergy(energyLevel);
        }

        public IEnergyStorage getEnergyStorage() { return energyStorage; }

    }

