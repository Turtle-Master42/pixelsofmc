package net.turtlemaster42.pixelsofmc.tile;

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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.turtlemaster42.pixelsofmc.block.ChemicalMixerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalMixerMenu;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.*;
import net.turtlemaster42.pixelsofmc.recipe.machines.ChemicalMixerRecipe;
import net.turtlemaster42.pixelsofmc.util.block.*;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChemicalMixerTile extends AbstractMachineTile<ChemicalMixerTile> implements IDuoFluidHandlingTile, ITriFluidHandlingTile, IQuadFluidHandlingTile, IQuinFluidHandlingTile, IHexaFluidHandlingTile, IButtonTile {

    protected final ContainerData data;
    public boolean[] switches = new boolean[]{false, false, false};
    public int temperatureState = 2;

    //TODO: make input only insert and output only extract
    private final FluidTank fluidTank = new FluidTank(16000) {
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

    private final FluidTank duoFluidTank = new FluidTank(16000) {
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

    private final FluidTank triFluidTank = new FluidTank(16000) {
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

    private final FluidTank quadFluidTank = new FluidTank(16000) {
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

    private final FluidTank quinFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncQuinFluidToClient(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    private final FluidTank hexaFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncHexaFluidToClient(this.fluid, worldPosition));
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

    public void setQuinFluid(FluidStack stack) {
        this.quinFluidTank.setFluid(stack);
    }

    public FluidStack getQuinFluid() {
        return this.quinFluidTank.getFluid();
    }

    public void setHexaFluid(FluidStack stack) {
        this.hexaFluidTank.setFluid(stack);
    }

    public FluidStack getHexaFluid() {
        return this.hexaFluidTank.getFluid();
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyTriFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyQuadFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyQuinFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyHexaFluidHandler = LazyOptional.empty();


    public ChemicalMixerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.CHEMICAL_MIXER.get(), pWorldPosition, pBlockState, 512000, 128);
        defineMaxProgress(16);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChemicalMixerTile.this.progress;
                    case 1 -> ChemicalMixerTile.this.maxProgress;
                    case 2 -> ChemicalMixerTile.this.requiredProgress;
                    case 3 -> ChemicalMixerTile.this.capacity;
                    case 4 -> ChemicalMixerTile.this.maxReceive;
                    case 5 -> ChemicalMixerTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ChemicalMixerTile.this.progress = value;
                    case 1 -> ChemicalMixerTile.this.maxProgress = value;
                    case 2 -> ChemicalMixerTile.this.requiredProgress = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 6) return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
        else if (slot==6) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==7) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        return false;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return false;
    }
    @Override
    protected int itemHandlerSize() {return 8;}
    protected void contentsChanged(int slot) {
        if (slot==6) speedUpgradeCheck(6);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.chemical_mixer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncTriFluidToClient(this.getTriFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncQuadFluidToClient(this.getQuadFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncQuinFluidToClient(this.getQuinFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncHexaFluidToClient(this.getHexaFluid(), worldPosition));
        return new ChemicalMixerMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == Direction.UP) {
                return lazyFluidHandler.cast();
            } else if (side == Direction.DOWN) {
                return lazyQuadFluidHandler.cast();
            }

            Direction localDir = this.getBlockState().getValue(ChemicalMixerBlock.FACING);

            return switch (localDir) {
                case EAST -> {
                    if (side == Direction.EAST) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.WEST) {
                        yield lazyTriFluidHandler.cast();
                    } else if (side == Direction.SOUTH) {
                        yield lazyQuinFluidHandler.cast();
                    } else if (side == Direction.NORTH) {
                        yield lazyHexaFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                case SOUTH -> {
                    if (side == Direction.SOUTH) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.NORTH) {
                        yield lazyTriFluidHandler.cast();
                    } else if (side == Direction.WEST) {
                        yield lazyQuinFluidHandler.cast();
                    } else if (side == Direction.EAST) {
                        yield lazyHexaFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                case WEST -> {
                    if (side == Direction.WEST) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.EAST) {
                        yield lazyTriFluidHandler.cast();
                    } else if (side == Direction.NORTH) {
                        yield lazyQuinFluidHandler.cast();
                    } else if (side == Direction.SOUTH) {
                        yield lazyHexaFluidHandler.cast();
                    } else {
                        yield lazyFluidHandler.cast();
                    }
                }
                default -> {
                    if (side == Direction.NORTH) {
                        yield lazyDuoFluidHandler.cast();
                    } else if (side == Direction.SOUTH) {
                        yield lazyTriFluidHandler.cast();
                    } else if (side == Direction.EAST) {
                        yield lazyQuinFluidHandler.cast();
                    } else if (side == Direction.WEST) {
                        yield lazyHexaFluidHandler.cast();
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
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        lazyDuoFluidHandler = LazyOptional.of(() -> duoFluidTank);
        lazyTriFluidHandler = LazyOptional.of(() -> triFluidTank);
        lazyQuadFluidHandler = LazyOptional.of(() -> quadFluidTank);
        lazyQuinFluidHandler = LazyOptional.of(() -> quinFluidTank);
        lazyHexaFluidHandler = LazyOptional.of(() -> hexaFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
        lazyTriFluidHandler.invalidate();
        lazyQuadFluidHandler.invalidate();
        lazyQuinFluidHandler.invalidate();
        lazyHexaFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("temperatureState", temperatureState);
        tag.putBoolean("switch1", switches[0]);
        tag.putBoolean("switch2", switches[1]);
        tag.putBoolean("switch3", switches[2]);
        tag.put("tank1", fluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank2", duoFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank3", triFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank4", quadFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank5", quinFluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank6", hexaFluidTank.writeToNBT(new CompoundTag()));
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        temperatureState = nbt.getInt("temperatureState");
        switches[0] = nbt.getBoolean("switch1");
        switches[1] = nbt.getBoolean("switch2");
        switches[2] = nbt.getBoolean("switch3");
        fluidTank.readFromNBT(nbt.getCompound("tank1"));
        duoFluidTank.readFromNBT(nbt.getCompound("tank2"));
        triFluidTank.readFromNBT(nbt.getCompound("tank3"));
        quadFluidTank.readFromNBT(nbt.getCompound("tank4"));
        quinFluidTank.readFromNBT(nbt.getCompound("tank5"));
        hexaFluidTank.readFromNBT(nbt.getCompound("tank6"));
    }


    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalMixerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, ChemicalMixerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, ChemicalMixerTile pBlockEntity) {
        transferFluidToItem(pBlockEntity, quadFluidTank, 3);
        transferFluidToItem(pBlockEntity, quinFluidTank, 4);
        transferFluidToItem(pBlockEntity, hexaFluidTank, 5);
        if (hasFluidItemInSourceSlots(pBlockEntity, 0)) {
            transferFluidToTank(pBlockEntity, fluidTank, 0);
        }
        if (hasFluidItemInSourceSlots(pBlockEntity, 1)) {
            transferFluidToTank(pBlockEntity, duoFluidTank, 1);
        }
        if (hasFluidItemInSourceSlots(pBlockEntity, 2)) {
            transferFluidToTank(pBlockEntity, triFluidTank, 2);
        }

        if (switches[0]) { // fill machine input with air
            FluidStack air = new FluidStack(POMfluids.AIR.get(), 200);
            fluidTank.fill(air, IFluidHandler.FluidAction.EXECUTE);
            duoFluidTank.fill(air, IFluidHandler.FluidAction.EXECUTE);
            triFluidTank.fill(air, IFluidHandler.FluidAction.EXECUTE);
        }

        if (switches[1]) { // move input fluid to output
            List<FluidStack> fluidStacks = new ArrayList<>();
            fluidStacks.add(fluidTank.drain(fluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));
            fluidStacks.add(duoFluidTank.drain(duoFluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));
            fluidStacks.add(triFluidTank.drain(triFluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));
            addFluidOutput(fluidStacks);
        }

        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;

            if (pBlockEntity.progress > pBlockEntity.requiredProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void transferFluidToTank(ChemicalMixerTile pBlockEntity, FluidTank tank, int slot) {
        pBlockEntity.itemHandler.getStackInSlot(slot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(tank.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(tank.isFluidValid(stack)) {
                if (tank.getFluid().isFluidEqual(stack) || tank.getFluid().isEmpty()) {
                    stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                    fillTankWithFluid(pBlockEntity, tank, stack, handler.getContainer(), slot);
                }
            }
        });
    }

    private void transferFluidToItem(ChemicalMixerTile pBlockEntity, FluidTank tank, int slot) {
        pBlockEntity.itemHandler.getStackInSlot(slot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int fillAmount = Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.SIMULATE), 1000);
            FluidStack stack = new FluidStack(tank.getFluid(), fillAmount);
            if(handler.isFluidValid(0, stack)) {
                stack = new FluidStack(tank.getFluid(), Math.min(handler.fill(stack, IFluidHandler.FluidAction.EXECUTE), 1000));
                drainTankWithFluid(pBlockEntity, tank, stack, handler.getContainer(), slot);
            }
        });
    }

    private void fillTankWithFluid(ChemicalMixerTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item, int slot) {
        fluidTank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pBlockEntity.itemHandler.extractItem(slot, 1, false);
        pBlockEntity.itemHandler.setStackInSlot(slot, item);
    }

    private void drainTankWithFluid(ChemicalMixerTile pBlockEntity, FluidTank fluidTank, FluidStack stack, ItemStack item, int slot) {
        fluidTank.drain(stack, IFluidHandler.FluidAction.EXECUTE);

        pBlockEntity.itemHandler.extractItem(slot, 1, false);
        pBlockEntity.itemHandler.insertItem(slot, item, false);
    }

    private boolean hasFluidItemInSourceSlots(ChemicalMixerTile pBlockEntity, int slot) {
        return pBlockEntity.itemHandler.getStackInSlot(slot).getCount() > 0;
    }

    private static boolean hasRecipe(ChemicalMixerTile entity) {
        Level level = entity.level;
        FluidContainer fluidInventory = new FluidContainer(3);
        FluidTank[] fluidTanks = {entity.fluidTank, entity.duoFluidTank, entity.triFluidTank};
        for (int i = 0; i < 3; i++) {
            fluidInventory.setFluid(i, fluidTanks[i].getFluid());
        }

        Optional<ChemicalMixerRecipe> match = level.getRecipeManager().getRecipeFor(ChemicalMixerRecipe.Type.INSTANCE, fluidInventory, level);

        return match.isPresent()
                && correctTemperature(entity, match.get().getTemperatureState())
                && canExtractInputFluid(entity, match.get().getInputFluid(0))
                && canExtractInputFluid(entity, match.get().getInputFluid(1))
                && canExtractInputFluid(entity, match.get().getInputFluid(2))
                && canInsertOutputFluid(entity, match.get().getResultFluid(0))
                && canInsertOutputFluid(entity, match.get().getResultFluid(1))
                && canInsertOutputFluid(entity, match.get().getResultFluid(2));
    }

    private static boolean correctTemperature(ChemicalMixerTile entity, int temperatureState) {
        return entity.temperatureState == temperatureState;
    }

    private static boolean canInsertOutputFluid(ChemicalMixerTile entity, FluidStack resultFluid) {
        FluidTank[] outputTanks = {entity.quadFluidTank, entity.quinFluidTank, entity.hexaFluidTank};
        for (FluidTank tank : outputTanks) {
            if (resultFluid.equals(tank.getFluid()) && resultFluid.getAmount() <= tank.getSpace() || tank.isEmpty() || resultFluid.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean canExtractInputFluid(ChemicalMixerTile entity, FluidStack fluidInput) {
        FluidTank[] inputTanks = {entity.fluidTank, entity.duoFluidTank, entity.triFluidTank};
        for (FluidTank tank : inputTanks) {
            if (fluidInput.equals(tank.getFluid()) && fluidInput.getAmount() <= tank.getFluidAmount() || fluidInput.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void addFluidOutput(List<FluidStack> fluidStacks) {
        FluidTank[] outputTanks = {quadFluidTank, quinFluidTank, hexaFluidTank};
        // iterates over the fluidStacks
        for (FluidStack fluidOutput : fluidStacks) {
            if (fluidOutput.getRawFluid().isSame(POMfluids.AIR.get())) { //can't output air
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
        FluidTank[] inputTanks = {fluidTank, duoFluidTank, triFluidTank};
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
                        fluidInput.setAmount(fluidInput.getAmount() - drainedStack.getAmount());
                    } else { // everything was inserted
                        break;
                    }
                }
            }
        }
    }

    private static void craftItem(ChemicalMixerTile entity) {
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
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    public FluidTank getFluidTank() { return fluidTank; }

    public FluidTank getDuoFluidTank() { return duoFluidTank; }

    public FluidTank getTriFluidTank() { return triFluidTank; }

    public FluidTank getQuadFluidTank() { return quadFluidTank; }

    public FluidTank getQuinFluidTank() { return quinFluidTank; }

    public FluidTank getHexaFluidTank() { return hexaFluidTank; }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitch(int currentSwitch, boolean on) {
        this.switches[currentSwitch] = on;
        setChanged();
    }

    public int getTemperatureState() {
        return temperatureState;
    }

    public void setTemperatureState(int temperatureState) {
        this.temperatureState = temperatureState;
    }
}


