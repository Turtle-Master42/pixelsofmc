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
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.ChemicalMixerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.ChemicalMixerMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.*;
import net.turtlemaster42.pixelsofmc.util.block.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ChemicalMixerTile extends AbstractMachineTile<ChemicalMixerTile> implements IDuoFluidHandlingTile, ITriFluidHandlingTile, IQuadFluidHandlingTile, IQuinFluidHandlingTile, IHexaFluidHandlingTile, IButtonTile {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedUpgrade = 0;
    private final int capacity = 512000;
    private final int maxReceive = 512000;
    private static final int energyConsumption = 128;
    public boolean[] switches = new boolean[]{false, false, false};
    public int temperatureState = 2;

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



    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive, 512000) {
            @Override
            public void onEnergyChanged() {
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
                setChanged();
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                onEnergyChanged();
                setChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }
            @Override
            public int extractEnergy(int maxReceive, boolean simulate) {
                onEnergyChanged();
                setChanged();
                return super.extractEnergy(maxReceive, simulate);
            }
        };
    }
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyTriFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyQuadFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyQuinFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyHexaFluidHandler = LazyOptional.empty();


    public ChemicalMixerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.CHEMICAL_MIXER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChemicalMixerTile.this.progress;
                    case 1 -> ChemicalMixerTile.this.maxProgress;
                    case 2 -> ChemicalMixerTile.this.speedUpgrade;
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
                    case 2 -> ChemicalMixerTile.this.speedUpgrade = value;
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
        if (slot==6)
            speedUpgradeCheck();
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

    //TODO: upgrade
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
            Direction localDir = this.getBlockState().getValue(ChemicalMixerBlock.FACING);
            return switch (localDir) {
                case EAST -> {
                    if (side == Direction.SOUTH)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.NORTH)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                case SOUTH -> {
                    if (side == Direction.WEST)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.EAST)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                case WEST -> {
                    if (side == Direction.NORTH)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.SOUTH)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
                default -> {
                    if (side == Direction.EAST)
                        yield lazyFluidHandler.cast();
                    else if (side == Direction.WEST)
                        yield lazyDuoFluidHandler.cast();
                    else
                        yield super.getCapability(cap, side);
                }
            };
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
        lazyTriFluidHandler = LazyOptional.of(() -> triFluidTank);
        lazyQuadFluidHandler = LazyOptional.of(() -> quadFluidTank);
        lazyQuinFluidHandler = LazyOptional.of(() -> quinFluidTank);
        lazyHexaFluidHandler = LazyOptional.of(() -> hexaFluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyDuoFluidHandler.invalidate();
        lazyTriFluidHandler.invalidate();
        lazyQuadFluidHandler.invalidate();
        lazyQuinFluidHandler.invalidate();
        lazyHexaFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putInt("temperatureState", temperatureState);

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
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
        temperatureState = nbt.getInt("temperatureState");

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

        if (switches[1]) {
            List<FluidStack> fluidStacks = new ArrayList<>();
            fluidStacks.add(fluidTank.drain(fluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));
            fluidStacks.add(duoFluidTank.drain(duoFluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));
            fluidStacks.add(triFluidTank.drain(triFluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));

            addFluidOutput(fluidStacks);
        }

        if (switches[2]) {
            List<FluidStack> fluidStacks = new ArrayList<>();
            fluidStacks.add(quadFluidTank.drain(quadFluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE));

            removeFluidInput(fluidStacks);
        }

        if(hasRecipe(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(6).getCount();
            pBlockEntity.progress++;
            //pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));

            if (pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
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
                stack = new FluidStack(tank.getFluid(), Math.min(handler.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE), 1000));
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
        return false;
//        Level level = entity.level;
//        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
//        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
//            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
//        }
//
//        Optional<ChemicalCombinerRecipe> match = level.getRecipeManager()
//                .getRecipeFor(ChemicalCombinerRecipe.Type.INSTANCE, inventory, level);
//
//        return match.isPresent()
//                && canExtractInputFluid(entity, match.get().getFluidInput())
//                && canInsertOutputFluid(entity, match.get().getResultFluid());
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

    private static boolean hasPower(ChemicalMixerTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(4).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
    }

    private static void craftItem(ChemicalMixerTile entity) {
//        Level level = entity.level;
//        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
//        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
//            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
//        }
//
//        Optional<ChemicalCombinerRecipe> match = level.getRecipeManager()
//                .getRecipeFor(ChemicalCombinerRecipe.Type.INSTANCE, inventory, level);
//
//        if(match.isPresent()) {
//            List<CountedIngredient> recipeItems = match.get().getInputs();
//
//            entity.fluidTank.drain(match.get().getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE);
//
//            entity.duoFluidTank.fill(match.get().getResultFluid(), IFluidHandler.FluidAction.EXECUTE);
//
//            setChanged(level, entity.worldPosition, entity.getBlockState());
//            entity.resetProgress();
//            entity.errorEnergyReset();
//        }
    }

    private void resetProgress() {this.progress = 0;}

    private void speedUpgradeCheck() {
        this.speedUpgrade = this.maxProgress - speedUpgrade();
    }

    private int energyUpgrade() {
        return Math.round(energyConsumption / (1 + 0.125f * (this.itemHandler.getStackInSlot(5).getCount() - this.itemHandler.getStackInSlot(5).getCount())));
    }

    private int speedUpgrade() {
        return Math.round(this.maxProgress / (1 + 0.125f * this.itemHandler.getStackInSlot(4).getCount()));
    }

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}",energyStorage.getEnergyStored() ,energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }
    @Override
    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

    public FluidTank getFluidTank() { return fluidTank; }

    public FluidTank getDuoFluidTank() { return duoFluidTank; }

    public FluidTank getTriFluidTank() { return triFluidTank; }

    public FluidTank getQuadFluidTank() { return quadFluidTank; }

    public FluidTank getQuinFluidTank() { return quinFluidTank; }

    public FluidTank getHexaFluidTank() { return hexaFluidTank; }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitch(boolean state, int currentSwitch) {
        this.switches[currentSwitch] = state;
        setChanged();
    }

    public int getTemperatureState() {
        return temperatureState;
    }

    public void setTemperatureState(int temperatureState) {
        this.temperatureState = temperatureState;
    }
}


