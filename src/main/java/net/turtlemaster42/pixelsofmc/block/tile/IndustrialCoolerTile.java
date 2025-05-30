package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.IndustrialCoolerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.IndustrialCoolerMenu;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.*;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.block.*;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class IndustrialCoolerTile extends AbstractMachineTile<IndustrialCoolerTile> implements IMultiFluidHandlingTile, IDuoFluidHandlingTile, ITriFluidHandlingTile, IQuadFluidHandlingTile, IButtonTile {

    protected final ContainerData data;
    private final boolean[] switches = {false};

    //TODO: make input only insert and output only extract
    private final FluidTank fluidTank = new FluidTank(128000) { // hot stuff
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

    private final FluidTank duoFluidTank = new FluidTank(128000) { //coolant
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

    private final FluidTank triFluidTank = new FluidTank(64000) { // heated coolant
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncTriFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    private final FluidTank quadFluidTank = new FluidTank(64000) { // cooled stuff
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncQuadFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    public void setFluid(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    public void setDuoFluid(FluidStack stack) {
        this.duoFluidTank.setFluid(stack);
    }

    public FluidStack getDuoFluid() {
        return this.duoFluidTank.getFluid();
    }

    public void setTriFluid(FluidStack stack) {
        this.triFluidTank.setFluid(stack);
    }

    public FluidStack getTriFluid() {
        return this.triFluidTank.getFluid();
    }

    public void setQuadFluid(FluidStack stack) {
        this.quadFluidTank.setFluid(stack);
    }

    public FluidStack getQuadFluid() {
        return this.quadFluidTank.getFluid();
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyTriFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyQuadFluidHandler = LazyOptional.empty();

    public IndustrialCoolerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.INDUSTRIAL_COOLER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {return 0;}

            @Override
            public void set(int pIndex, int pValue) {}

            @Override
            public int getCount() {return 0;}
        };
    }

    @Override
    protected int itemHandlerSize() {return 0;}

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.industrial_cooler");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncTriFluidToClient(this.getTriFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncQuadFluidToClient(this.getQuadFluid(), worldPosition));
        return new IndustrialCoolerMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == Direction.UP) {
                return lazyFluidHandler.cast();
            } else if (side == Direction.DOWN) {
                return lazyQuadFluidHandler.cast();
            }

            Direction localDir = this.getBlockState().getValue(IndustrialCoolerBlock.FACING);

            return switch (localDir) {
                case EAST -> {
                    if (side == Direction.EAST) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.WEST) {
                        yield lazyTriFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                case SOUTH -> {
                    if (side == Direction.SOUTH) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.NORTH) {
                        yield lazyTriFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                case WEST -> {
                    if (side == Direction.WEST) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.EAST) {
                        yield lazyTriFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                default -> {
                    if (side == Direction.NORTH) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.SOUTH) {
                        yield lazyTriFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
            };
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
        lazyTriFluidHandler = LazyOptional.of(() -> triFluidTank);
        lazyQuadFluidHandler = LazyOptional.of(() -> quadFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
        lazyTriFluidHandler.invalidate();
        lazyQuadFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("tank1", fluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank2", duoFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank3", triFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank4", quadFluidTank.writeToNBT(new CompoundTag()));
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        fluidTank.readFromNBT(nbt.getCompound("tank1"));
        duoFluidTank.readFromNBT(nbt.getCompound("tank2"));
        triFluidTank.readFromNBT(nbt.getCompound("tank3"));
        quadFluidTank.readFromNBT(nbt.getCompound("tank4"));
    }


    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialCoolerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialCoolerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, IndustrialCoolerTile pBlockEntity) {
//        if(hasRecipe(pBlockEntity)) {
//            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(6).getCount();
//            pBlockEntity.progress++;
//            //pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));
//
//            if (pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
//                craftItem(pBlockEntity);
//            }
//        } else {
            setChanged(pLevel, pPos, pState);
//        }
    }


    private static boolean hasRecipe(IndustrialCoolerTile entity) {
        Level level = entity.level;
        FluidContainer fluidInventory = new FluidContainer(3);
        FluidTank[] fluidTanks = {entity.fluidTank, entity.duoFluidTank, entity.triFluidTank};
        for (int i = 0; i < 3; i++) {
            fluidInventory.setFluid(i, fluidTanks[i].getFluid());
        }

        Optional<ChemicalMixerRecipe> match = level.getRecipeManager().getRecipeFor(ChemicalMixerRecipe.Type.INSTANCE, fluidInventory, level);

        return match.isPresent()
                && canExtractInputFluid(entity, match.get().getInputFluid(0))
                && canExtractInputFluid(entity, match.get().getInputFluid(1))
                && canInsertOutputFluid(entity, match.get().getResultFluid(0))
                && canInsertOutputFluid(entity, match.get().getResultFluid(1));
    }

    private static boolean canInsertOutputFluid(IndustrialCoolerTile entity, FluidStack resultFluid) {
        FluidTank[] outputTanks = {entity.triFluidTank, entity.quadFluidTank};
        for (FluidTank tank : outputTanks) {
            if (resultFluid.equals(tank.getFluid()) && resultFluid.getAmount() <= tank.getSpace() || tank.isEmpty() || resultFluid.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean canExtractInputFluid(IndustrialCoolerTile entity, FluidStack fluidInput) {
        FluidTank[] inputTanks = {entity.fluidTank, entity.duoFluidTank};
        for (FluidTank tank : inputTanks) {
            if (fluidInput.equals(tank.getFluid()) && fluidInput.getAmount() <= tank.getFluidAmount() || fluidInput.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void addFluidOutput(List<FluidStack> fluidStacks) {
        FluidTank[] outputTanks = {triFluidTank, quadFluidTank};
        // iterates over the fluidStacks
        for (FluidStack fluidOutput : fluidStacks) {
            if (fluidOutput.getRawFluid().isSame(POMfluids.AIR_SOURCE.get())) { //can't output air
                continue;
            }
            // iterates over the tanks
            for (FluidTank tank : outputTanks) {
                if (fluidOutput.isEmpty() || fluidOutput.getAmount() <= 0) {
                    break;
                }
                if (fluidOutput.equals(tank.getFluid()) || tank.isEmpty()) {
                    int fillAmount =  tank.fill(fluidOutput, IFluidHandler.FluidAction.EXECUTE);
                    // not everything was inserted
                    if (fillAmount < fluidOutput.getAmount()) {
                        fluidOutput.setAmount(fluidOutput.getAmount() - fillAmount);
                    } else { // everything was inserted
                        break;
                    }
                }
            }
        }
    }

    private void removeFluidInput(List<FluidStack> fluidStacks) {
        FluidTank[] inputTanks = {fluidTank, duoFluidTank};
        // iterates over the fluidStacks
        for (FluidStack fluidInput : fluidStacks) {
            // iterates over the tanks
            for (FluidTank tank : inputTanks) {
                if (fluidInput.isEmpty() || fluidInput.getAmount() <= 0) {
                    break;
                }
                if (fluidInput.equals(tank.getFluid())) {
                    FluidStack drainedStack = tank.drain(fluidInput.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                    // not everything was drained
                    if (drainedStack.getAmount() < fluidInput.getAmount()) {
                        PixelsOfMc.LOGGER.info("not everything was drained: {} < {}, {} left", drainedStack.getAmount(), fluidInput.getAmount(), fluidInput.getAmount() - drainedStack.getAmount());
                        fluidInput.setAmount(fluidInput.getAmount() - drainedStack.getAmount());
                    } else { // everything was inserted
                        PixelsOfMc.LOGGER.info("everything was inserted, {} == {}", drainedStack.getAmount(), fluidInput.getAmount());
                        break;
                    }
                }
            }
        }
    }

    private static void craftItem(IndustrialCoolerTile entity) {
        Level level = entity.level;
        FluidContainer fluidInventory = new FluidContainer(3);
        FluidTank[] fluidTanks = {entity.fluidTank, entity.duoFluidTank, entity.triFluidTank};
        for (int i = 0; i < 3; i++) {
            fluidInventory.setFluid(i, fluidTanks[i].getFluid());
        }

        Optional<ChemicalMixerRecipe> match = level.getRecipeManager().getRecipeFor(ChemicalMixerRecipe.Type.INSTANCE, fluidInventory, level);

        if(match.isPresent()) {
            entity.addFluidOutput(match.get().getResultFluids());
            entity.removeFluidInput(match.get().getFluidInputs());

            setChanged(level, entity.worldPosition, entity.getBlockState());
        }
    }

    public FluidTank getFluidTank() { return fluidTank; }

    public FluidTank getDuoFluidTank() { return duoFluidTank; }

    public FluidTank getTriFluidTank() { return triFluidTank; }

    public FluidTank getQuadFluidTank() { return quadFluidTank; }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitch(int currentSwitch, boolean on) {
        this.switches[currentSwitch] = on;
        setChanged();
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{fluidTank, duoFluidTank, triFluidTank, quadFluidTank};
    }

    @Override
    public FluidTank getFluidTank(String name) {
        return switch (name) {
            case "input" -> fluidTank;
            case "coolant_input" -> duoFluidTank;
            case "coolant_output" -> triFluidTank;
            case "output" -> quadFluidTank;
            default -> null;
        };
    }

    @Override
    public String[] getFluidTankNames() {
        return new String[]{"input", "coolant_input", "coolant_output", "output"};
    }
}


