package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.block.AbstractPort;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMparticles;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.List;

public class AbstractEnergyPortTile extends AbstractMultiBlockTile implements IEnergyHandlingTile {

    protected final ContainerData data;

    private final int capacity = -1;
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
                if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(2)) {
                    //credits Cyclic
                    BlockPos posTarget = getMainPos();
                    if (posTarget.equals(worldPosition))
                        return 0;
                    BlockEntity tile = level.getBlockEntity(posTarget);
                    if (tile != null) {
                        IEnergyStorage energyHandlerFrom = tile.getCapability(ForgeCapabilities.ENERGY, Direction.UP.getOpposite()).orElse(null);
                        if (energyHandlerFrom != null) {
                            //ok go
                            int receive = energyHandlerFrom.receiveEnergy(maxReceive, true);
                            energyHandlerFrom.receiveEnergy(maxReceive, simulate);
                            energyStorage.setEnergy(energyHandlerFrom.getEnergyStored());
                            onEnergyChanged();
                            return receive;
                        }
                    }
                }
                return 0;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(1)) {
                    //credits Cyclic
                    BlockPos posTarget = getMainPos();
                    if (posTarget.equals(worldPosition))
                        return 0;
                    BlockEntity tile = level.getBlockEntity(posTarget);
                    if (tile != null) {
                        IEnergyStorage energyHandlerFrom = tile.getCapability(ForgeCapabilities.ENERGY, Direction.UP.getOpposite()).orElse(null);
                        if (energyHandlerFrom != null) {
                            //ok go
                            int extract = energyHandlerFrom.extractEnergy(maxExtract, true);
                            energyHandlerFrom.extractEnergy(maxExtract, simulate);
                            energyStorage.setEnergy(energyHandlerFrom.getEnergyStored());
                            onEnergyChanged();
                            return extract;
                        }
                    }
                }
                return 0;
            }
        };
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public AbstractEnergyPortTile(BlockEntityType<?> blockEntityType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(blockEntityType, pWorldPosition, pBlockState);
        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                if (index == 0) {
                    return AbstractEnergyPortTile.this.energyStorage.getEnergyStored();
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

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractEnergyPortTile e) {
        if (blockState.getValue(AbstractPort.PUSHING) && e.isMainPosValid()) {
            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(AbstractPort.PUSH_DIRECTION), 0, 0, 1, blockPos);
            BlockState facingState = level.getBlockState(facingPos);
            e.energyStorage.extractEnergy(1, true);
            if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                if (e.energyStorage.getEnergyStored() <= 0) {
                    return;
                }
                if (Math.random() < 0.2) {
                    List<Entity> entities = level.getEntities((Entity) null, new AABB(facingPos), (entity) -> entity.isAlive() && entity instanceof LivingEntity);
                    if (!entities.isEmpty()) {
                        e.energyStorage.extractEnergy(12000, false);
                    }
                    for (Entity entity : entities) {
                        entity.hurt(level.damageSources().lightningBolt(), 4);
                    }
                }
                e.energyStorage.extractEnergy(50, false);
            } else {
                BlockEntity facingTile = level.getBlockEntity(facingPos);
                if (facingTile != null && e.energyStorage.getEnergyStored() < e.capacity) {
                    EnergyStorage energy = (EnergyStorage) facingTile.getCapability(ForgeCapabilities.ENERGY, blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite()).orElse(null);
                    if (energy != null) {
                        energy.receiveEnergy(e.energyStorage.extractEnergy(Math.min(100, e.energyStorage.getMaxEnergyStored() - e.energyStorage.getEnergyStored()), false), false);
                    }
                }
            }
        }
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, AbstractEnergyPortTile e) {
        if (blockState.getValue(AbstractPort.PUSHING)) {
            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(AbstractPort.PUSH_DIRECTION), 0, 0, 1, blockPos);
            BlockState facingState = level.getBlockState(facingPos);
            if (((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) && e.energyStorage.getEnergyStored() > 0) {
                Vector3f centerVec = new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                Vector3f posVec = rotatedVecPos(blockState.getValue(AbstractPort.PUSH_DIRECTION), centerVec, 0, 0, 0.6f);
                level.addParticle(POMparticles.ELECTRIC_SPARK.get(), posVec.x + (Math.random() - 0.5) / 2, posVec.y + (Math.random() - 0.5) / 2, posVec.z + (Math.random() - 0.5) / 2, 0, 0, 0);
            }
        }
    }

    @Override
    public void setEnergyLevel(int energy) {
        energyStorage.setEnergy(energy);
    }

    @Override
    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }
}
