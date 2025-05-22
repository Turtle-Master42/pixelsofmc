package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.block.AbstractPort;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMparticles;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;

public class FluidPortTile extends AbstractMultiBlockTile implements IFluidHandlingTile, IDuoFluidHandlingTile {

    private final int capacity = -1;
    private int fluidPlaceProgress = 0;

    public final FluidTank fluidTank = new FluidTank(capacity) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return duoFluidTank.drain(maxDrain, action);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(2)) {
                //credits Cyclic
                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition))
                    return 0;
                BlockEntity tile = level.getBlockEntity(posTarget);
                if (tile != null) {
                    IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.DOWN).orElse(null);
                    if (fluidHandlerFrom != null) {
                        //ok go
                        int fill = fluidHandlerFrom.fill(resource, action);
                        fluidTank.setFluid(fluidHandlerFrom.getFluidInTank(0));
                        onContentsChanged();
                        return fill;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    public final FluidTank duoFluidTank = new FluidTank(capacity) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(1)) {
                //credits Cyclic
                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition))
                    return this.fluid;
                BlockEntity tile = level.getBlockEntity(posTarget);
                if (tile != null) {
                    IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).orElse(null);
                    if (fluidHandlerFrom != null) {
                        //ok go
                        FluidStack drain = fluidHandlerFrom.drain(maxDrain, action);
                        duoFluidTank.setFluid(fluidHandlerFrom.getFluidInTank(0));
                        onContentsChanged();
                        return drain;
                    }
                }
            }
            return this.fluid;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return fluidTank.fill(resource, action);
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
    public FluidTank getFluidTank() {
        return fluidTank;
    }

    @Override
    public void setDuoFluid(FluidStack fluid) {
        this.duoFluidTank.setFluid(fluid);
    }
    @Override
    public FluidStack getDuoFluid() {
        return this.duoFluidTank.getFluid();
    }

    @Override
    public FluidTank getDuoFluidTank() {
        return duoFluidTank;
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();

    public FluidPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.FLUID_PORT.get(), pWorldPosition, pBlockState);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
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
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FluidPortTile e) {
        if (blockState.getValue(AbstractPort.PUSHING) && e.isMainPosValid()) {
            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(AbstractPort.PUSH_DIRECTION), 0, 0, 1, blockPos);
            BlockState facingState = level.getBlockState(facingPos);
            if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                if (e.fluidPlaceProgress > 200 && !e.duoFluidTank.getFluid().getFluid().getFluidType().isLighterThanAir()) {
                    e.fluidPlaceProgress = 0;
                    level.setBlock(facingPos, e.duoFluidTank.getFluid().getRawFluid().defaultFluidState().createLegacyBlock(), 3);
                } else {
                    e.duoFluidTank.drain(5, IFluidHandler.FluidAction.SIMULATE);
                    e.fluidPlaceProgress++;
                }
            } else {
                BlockEntity facingTile = level.getBlockEntity(facingPos);
                if (facingTile != null) {
                    IFluidHandler fluid = facingTile.getCapability(ForgeCapabilities.FLUID_HANDLER, blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite()).orElse(null);
                    if (fluid != null) {
                        fluid.fill(new FluidStack(e.getDuoFluid().getFluid(), e.duoFluidTank.drain(Math.min(fluid.getTankCapacity(0) - fluid.getFluidInTank(0).getAmount(), 250), IFluidHandler.FluidAction.EXECUTE).getAmount()), IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
        }
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, FluidPortTile e) {
        if (blockState.getValue(AbstractPort.PUSHING)) {
            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(AbstractPort.PUSH_DIRECTION), 0, 0, 1, blockPos);
            BlockState facingState = level.getBlockState(facingPos);
            if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                Vector3f centerVec = new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                Vector3f posVec = rotatedVecPos(blockState.getValue(AbstractPort.PUSH_DIRECTION), centerVec, 0, 0, 0.6f);
                Vector3f speedVec = rotatedVecPos(blockState.getValue(AbstractPort.PUSH_DIRECTION), new Vector3f(0), 0, 0, 0.5f);
                Fluid fluid = e.duoFluidTank.getFluid().getRawFluid();
                    level.addParticle(new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), fluid.defaultFluidState().createLegacyBlock()), posVec.x, posVec.y, posVec.z, speedVec.x, speedVec.y, speedVec.z);
                    level.addParticle(new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), fluid.defaultFluidState().createLegacyBlock()), posVec.x, posVec.y, posVec.z, speedVec.x, speedVec.y, speedVec.z);
//                }
            }
        }
    }
}
