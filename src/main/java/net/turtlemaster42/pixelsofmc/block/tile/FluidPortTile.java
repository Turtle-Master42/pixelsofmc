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
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncCurrentTankToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncMainPosToClient;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IMultiFluidHandlingTile;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;

public class FluidPortTile extends AbstractMultiBlockTile implements IFluidHandlingTile {

    private final int capacity = -1;
    private int fluidPlaceProgress = 0;
    private String currentTank = "null";


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
            if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(1)) {
                //credits Cyclic
                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition))
                    return this.fluid;
                BlockEntity mainTile = level.getBlockEntity(posTarget);
                if (mainTile instanceof IMultiFluidHandlingTile fluidLogicTile) {
                    FluidTank fluidTankFrom = fluidLogicTile.getFluidTank(currentTank);
                    if (fluidTankFrom != null) {
                        //ok go
                        FluidStack drain = fluidTankFrom.drain(maxDrain, action);
                        fluidTank.setFluid(fluidTankFrom.getFluidInTank(0));
                        onContentsChanged();
                        return drain;
                    }
                } else if (mainTile != null) {
                    IFluidHandler fluidHandlerFrom = mainTile.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).orElse(null);
                    if (fluidHandlerFrom != null) {
                        //ok go
                        FluidStack drain = fluidHandlerFrom.drain(maxDrain, action);
                        fluidTank.setFluid(fluidHandlerFrom.getFluidInTank(0));
                        onContentsChanged();
                        return drain;
                    }
                }
            }
            return this.fluid;
        }


        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!level.getBlockState(worldPosition).getValue(AbstractPort.MODE).equals(2)) {
                //credits Cyclic
                BlockPos posTarget = getMainPos();
                if (posTarget.equals(worldPosition))
                    return 0;
                BlockEntity mainTile = level.getBlockEntity(posTarget);
                if (mainTile instanceof IMultiFluidHandlingTile fluidLogicTile) {
                    FluidTank fluidTankFrom = fluidLogicTile.getFluidTank(currentTank);
                    if (fluidTankFrom != null) {
                        //ok go
                        int fill = fluidTankFrom.fill(resource, action);
                        fluidTank.setFluid(fluidTankFrom.getFluidInTank(0));
                        onContentsChanged();
                        return fill;
                    }
                } else if (mainTile != null) {
                    IFluidHandler fluidHandlerFrom = mainTile.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).orElse(null);
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

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public FluidPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.FLUID_PORT.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void onInvalidation() {
        POMmessages.sendToClients(new PacketSyncMainPosToClient(worldPosition, worldPosition));
        POMmessages.sendToClients(new PacketSyncCurrentTankToClient("null", worldPosition));
        currentTank = "null";
        setChanged();
    }

    @Override
    public void onValidation() {
        if (level != null && !level.isClientSide()) {
            POMmessages.sendToClients(new PacketSyncMainPosToClient(getMainPos(), worldPosition));
            if (level.getBlockEntity(getMainPos()) instanceof IMultiFluidHandlingTile fluidHandlingTile) {
                currentTank = fluidHandlingTile.getFluidTankNames()[0];
                POMmessages.sendToClients(new PacketSyncCurrentTankToClient(currentTank, worldPosition));
            }
            setChanged();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        fluidTank.writeToNBT(tag);
        tag.putString("currentTank", currentTank);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt);
        currentTank = nbt.getString("currentTank");
    }


    public static void idleTick(Level level, BlockPos blockPos, BlockState blockState, FluidPortTile e) {
        Direction direction = blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite();
        if (level.getBlockState(blockPos.relative(direction)).is(Blocks.COMPARATOR)) {
            level.scheduleTick(blockPos.relative(direction), Blocks.COMPARATOR, 0);
        } else if (level.getBlockState(blockPos.relative(direction, 2)).is(Blocks.COMPARATOR)) {
            level.scheduleTick(blockPos.relative(direction, 2), Blocks.COMPARATOR, 0);
        }
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FluidPortTile e) {
        idleTick(level, blockPos, blockState, e);

        if (blockState.getValue(AbstractPort.PUSHING) && e.isMainPosValid()) {
            Direction dir = blockState.getValue(AbstractPort.PUSH_DIRECTION) == Direction.UP || blockState.getValue(AbstractPort.PUSH_DIRECTION) == Direction.DOWN  ? blockState.getValue(AbstractPort.PUSH_DIRECTION) : blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite();
            BlockPos facingPos = blockPos.relative(dir);
            BlockState facingState = level.getBlockState(facingPos);
            if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                if (e.fluidPlaceProgress > 200 && !e.fluidTank.getFluid().getFluid().getFluidType().isLighterThanAir()) {
                    e.fluidPlaceProgress = 0;
                    level.setBlock(facingPos, e.fluidTank.getFluid().getRawFluid().defaultFluidState().createLegacyBlock(), 3);
                } else {
                    e.fluidTank.drain(5, IFluidHandler.FluidAction.SIMULATE); //TODO: Change to EXECUTE once done
                    e.fluidPlaceProgress++;
                }
            } else {
                BlockEntity facingTile = level.getBlockEntity(facingPos);
                if (facingTile != null) {
                    IFluidHandler fluidHandler = facingTile.getCapability(ForgeCapabilities.FLUID_HANDLER, blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite()).orElse(null);
                    if (fluidHandler != null) {
                        int amount =  Math.min(fluidHandler.getTankCapacity(0) - fluidHandler.getFluidInTank(0).getAmount(), 1000);
                        int fill = fluidHandler.fill(new FluidStack(e.fluidTank.getFluid(),
                                e.fluidTank.drain(amount < 0 ? 1000 : amount, IFluidHandler.FluidAction.SIMULATE).getAmount()),
                                IFluidHandler.FluidAction.EXECUTE
                        );
                        e.fluidTank.drain(fill, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
        }
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, FluidPortTile e) {
        if (blockState.getValue(AbstractPort.PUSHING)) {
            Direction dir = blockState.getValue(AbstractPort.PUSH_DIRECTION) == Direction.UP || blockState.getValue(AbstractPort.PUSH_DIRECTION) == Direction.DOWN  ? blockState.getValue(AbstractPort.PUSH_DIRECTION) : blockState.getValue(AbstractPort.PUSH_DIRECTION).getOpposite();
            BlockPos facingPos = blockPos.relative(dir);
            BlockState facingState = level.getBlockState(facingPos);
            if ((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR))) {
                Vector3f centerVec = new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                Vector3f posVec = rotatedVecPos(blockState.getValue(AbstractPort.PUSH_DIRECTION), centerVec, 0, 0, 0.6f);
                Vector3f speedVec = rotatedVecPos(blockState.getValue(AbstractPort.PUSH_DIRECTION), new Vector3f(0), 0, 0, 0.5f);
                Fluid fluid = e.fluidTank.getFluid().getRawFluid();
                level.addParticle(new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), fluid.defaultFluidState().createLegacyBlock()), posVec.x, posVec.y, posVec.z, speedVec.x, speedVec.y, speedVec.z);
                level.addParticle(new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), fluid.defaultFluidState().createLegacyBlock()), posVec.x, posVec.y, posVec.z, speedVec.x, speedVec.y, speedVec.z);
            }
        }
    }

    public void setCurrentTank(String currentTank) {
        this.currentTank = currentTank;
    }

    public String getCurrentTank() {
        return currentTank;
    }
}
