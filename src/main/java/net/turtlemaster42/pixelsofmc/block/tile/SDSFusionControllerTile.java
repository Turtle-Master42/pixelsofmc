package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
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
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.SDSFusionControllerMenu;
import net.turtlemaster42.pixelsofmc.init.POMfluids;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.item.AtomItem;
import net.turtlemaster42.pixelsofmc.network.*;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.Constants;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.block.IButtonTile;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInfiniteEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SDSFusionControllerTile extends AbstractMachineTile<SDSFusionControllerTile> implements IDuoFluidHandlingTile, IInfiniteEnergyHandlingTile, IButtonTile {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 8;
    private final InfiniteNumber capacity = new InfiniteNumber().fromLong(4096000000L);
    private final int maxReceive = 2048000000;
    private static final int energyConsumption = 12000;
    private long fusionPower = 0;
    private final long maxFusionPower = 5_000_000_000L;
    private final long maxOverchargePower = 1_250_000_000L;
    private int cantCraftReason = 0;
    private int cantCraftElement = 0;
    private int heatSinkAmount = 0;

    private float heatEnergyEfficiency = 1.5f;
    public int inputSlotLimit = 64;
    public boolean[] switches = new boolean[]{false, false, false};





    public final InfinitePixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public InfinitePixelEnergyStorage createEnergyStorage() {
        return new InfinitePixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
                POMmessages.sendToClients(new PacketSyncInfiniteEnergyToClient(this.infiniteEnergy, worldPosition));
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
        return new InfiniteNumber().getCrudePercentage(energyStorage.getInfiniteCapacity(), energyStorage.getInfiniteEnergy());
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public final FluidTank fluidTank = new FluidTank(500000) {
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

    public final FluidTank duoFluidTank = new FluidTank(500000) {
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



    public SDSFusionControllerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.SDS_CONTROLLER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> SDSFusionControllerTile.this.progress;
                    case 1 -> SDSFusionControllerTile.this.maxProgress;
//                    case 2 -> SDSFusionControllerTile.this.capacity;
                    case 3 -> SDSFusionControllerTile.this.maxReceive;
                    case 4 -> SDSFusionControllerTile.this.energyStorage.getEnergyStored();
                    case 5 -> SDSFusionControllerTile.this.cantCraftReason;
                    case 6 -> SDSFusionControllerTile.this.cantCraftElement;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SDSFusionControllerTile.this.progress = value;
                    case 1 -> SDSFusionControllerTile.this.maxProgress = value;
                }
            }

            public int getCount() {
                return 7;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return slot <= 1 && (stack.is(POMtags.Items.ATOM)) && getSlotLimits(slot) != 0;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot >= 2;
    }
    @Override
    protected int itemHandlerSize() {return 5;}

    @Override
    protected void contentsChanged(int slot) {
        this.cantCraftReason = 0;
        this.cantCraftElement = 0;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.sds_controller");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), getBlockPos()));
        POMmessages.sendToClients(new PacketSyncInfiniteEnergyToClient(this.energyStorage.getInfiniteEnergy(), worldPosition));
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        return new SDSFusionControllerMenu(pContainerId, pInventory, this, this.data);
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
        tag.putInt("progress", progress);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putString("InfiniteEnergy", energyStorage.getInfiniteEnergy().toString());
        tag.putLong("fusionPower", fusionPower);
        tag.putInt("slotLimit", inputSlotLimit);
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        CompoundTag slotTag = new CompoundTag();
        tag.put("lockedSlot", slotTag);
        tag.putBoolean("switch1", switches[0]);
        tag.putBoolean("switch2", switches[1]);
        tag.putBoolean("switch3", switches[2]);
        tag.putInt("heatSinks", heatSinkAmount);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        energyStorage.setEnergy(nbt.getInt("Energy"));
        energyStorage.setInfiniteEnergy(new InfiniteNumber().fromString(nbt.getString("InfiniteEnergy")));
        fusionPower = nbt.getLong("fusionPower");
        inputSlotLimit = nbt.getInt("slotLimit");
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
        switches[0] = nbt.getBoolean("switch1");
        switches[1] = nbt.getBoolean("switch2");
        switches[2] = nbt.getBoolean("switch3");
        heatSinkAmount = nbt.getInt("heatSinks");
    }



    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, SDSFusionControllerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, SDSFusionControllerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, SDSFusionControllerTile pBlockEntity) {
        if (getSwitch(0)) {
            if (getSwitch(2)) {
                addFusionPower(75_000);
                energyStorage.consumeEnergy((int) (300_000 * heatEnergyEfficiency));
            } else {
                addFusionPower(50_000);
                energyStorage.consumeEnergy((int) (200_000 * heatEnergyEfficiency));
            }
        }
        if (getSwitch(1)) {
            FluidStack drained = fluidTank.drain(1000 * heatSinkAmount, IFluidHandler.FluidAction.EXECUTE);
            duoFluidTank.fill(new FluidStack(POMfluids.STEAM_SOURCE.get(), drained.getAmount()), IFluidHandler.FluidAction.EXECUTE);
            addFusionPower(Math.round(drained.getAmount() * -0.3f));
        }


        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(energyConsumption);
            if (pBlockEntity.progress > 0 && pState.getValue(SDSFusionControllerBlock.ACTIVE) != 3) {
                level.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 3), 2);
            }
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
                if (!hasRecipe(pBlockEntity))
                    level.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 2), 2);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private boolean hasRecipe(SDSFusionControllerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FusionRecipe> match = level.getRecipeManager()
                .getRecipeFor(FusionRecipe.Type.INSTANCE, inventory, level);
        return match.isPresent()
                && canInsertIntoOutputSlot(entity, match.get())
                && canFuse(entity, match.get());
//                && isFusible(match.get().getResultItem());
    }

    private static boolean hasPower(SDSFusionControllerTile entity) {
        return entity.energyStorage.getInfiniteEnergy().isBiggerThenOrEquals(energyConsumption);
    }


    private static void craftItem(SDSFusionControllerTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FusionRecipe> match = level.getRecipeManager()
                .getRecipeFor(FusionRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            FusionRecipe recipe = match.get();

            if (entity.itemHandler.getStackInSlot(0).getItem() instanceof AtomItem atom1 && entity.itemHandler.getStackInSlot(1).getItem() instanceof AtomItem atom2) {
                double outputMass = 0;
                for (CountedIngredient output: recipe.getOutputs()) {
                    if (output.asItem() instanceof AtomItem atom)
                        outputMass += atom.getElementalMass();
                }
                double deltaMass = atom1.getElementalMass() + atom2.getElementalMass() - outputMass;
                double released_energy;
                if (recipe.x512()) { // ∆mc^2 * fusionEnergyReleaseConstant * (amount of fusions)
                    released_energy = deltaMass * Constants.cSquared * Constants.fusionEnergyReleaseConstant * 512;
                } else {
                    released_energy = deltaMass * Constants.cSquared * Constants.fusionEnergyReleaseConstant * 64;
                }
                double temperature = released_energy / Constants.plasmaHeatingPerJoule;

                PixelsOfMc.LOGGER.info("Energy J: {}, delta m: {}", released_energy, deltaMass);
                PixelsOfMc.LOGGER.info("Energy FE: {}", released_energy * Constants.J_FE_Constant);
                PixelsOfMc.LOGGER.info("Temperature K: {}", (long) temperature);

                entity.fusionPower += (long) temperature;

                entity.removeInput(0);
                entity.removeInput(1);
                entity.addMultiOutput(recipe.getOutputs(), 2, 4);
            }
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}

    private static boolean canInsertItemIntoSlot(ItemStack stack, ItemStack item) {
        return item.getItem()==stack.getItem() && stack.getCount() + item.getCount() <= stack.getMaxStackSize() || stack.isEmpty();
    }

    private static boolean canInsertIntoOutputSlot(SDSFusionControllerTile entity, FusionRecipe match) {
        boolean[] matched = new boolean[match.getOutputs().size()];
        boolean[] matchNeeded = new boolean[match.getOutputs().size()];
        ItemStack[] newStackInSlot = new ItemStack[entity.itemHandlerSize()];
        ItemStack newStack;

        // Makes sure that newStackInSlot[] is not null
        for (int i = 2; i < 5; i++)
            newStackInSlot[i] = ItemStack.EMPTY;

        // Iterate over the inputs -q-
        for (int q = 0; q < match.getOutputs().size(); q++) {
            matchNeeded[q] = true;
            newStack = match.getResultItems(q);
            // Iterate over the slots -p-
            for (int p = 2; p < 5; p++) {
                if (matched[q])
                    continue;
                ItemStack[] stackInSlot = new ItemStack[entity.itemHandlerSize()];
                stackInSlot[p] = entity.itemHandler.getStackInSlot(p);
                if (stackInSlot[p].isEmpty())
                    stackInSlot[p] = newStackInSlot[p];
                if (canInsertItemIntoSlot(stackInSlot[p], newStack)) {
                    newStackInSlot[p] = newStack;
                    newStack = entity.insertItemStack(p, newStack, true);
                    if (newStack.isEmpty()) {
                        matched[q] = true;
                    }
                }
            }
        }

        for (int i = 0; i < match.getOutputs().size(); i++) {
            if (matched[i]!=matchNeeded[i])
                return false;
        }
        return true;
    }

    private boolean canFuse(SDSFusionControllerTile entity, FusionRecipe match) {
        if ((entity.itemHandler.getStackInSlot(0).getItem() instanceof AtomItem atom1) && (entity.itemHandler.getStackInSlot(1).getItem() instanceof AtomItem atom2)) {
            // 1,446*10^-25 = 4π * 8.854 * 10^-12 * 1.3 * 10 ^-15
//            double energy = (atom1.getProtonCount() * atom2.getProtonCount() * Math.pow(1.602*Math.pow(10, -19), 2)) / (1.446 * Math.pow(10, -25) * (Math.pow(atom1.getElementalMass(), 0.333) + Math.pow(atom2.getElementalMass(), 0.333)));
            double energy = (atom1.getProtonCount() * atom2.getProtonCount()) / (5.636*Math.pow(10, 12)*(Math.pow(atom1.getBitMass(), 0.333) + Math.pow(atom2.getBitMass(), 0.333)));
            // calculate the temperature
            double temperature = (Math.PI * Math.abs(energy)) / (4 * 1.3806 * Math.pow(10, -23)) / Constants.fusionTemperatureDivider;
//            PixelsOfMc.LOGGER.info("{} <= {}", (long) temperature, fusionPower);

            if (temperature > maxOverchargePower) {
                this.cantCraftReason = 1;
            } else if (temperature > fusionPower) {
                this.cantCraftReason = 2;
            }
            return temperature <= fusionPower;
        }
        return false;
    }

    private boolean isFusible(ItemStack stack) {
        if (stack.getItem() instanceof AtomItem atomItem) {
            if (stack.is(POMtags.Items.SDS)) {
                this.cantCraftReason = 0;
                return true;
            }
            this.cantCraftReason = 1;
            this.cantCraftElement = atomItem.getElement().getElement();
            return false;
        }
        this.cantCraftReason = 2;
        return false;
    }

    //---OTHER---//

    public int getHeatSinkAmount() {
        return heatSinkAmount;
    }

    public void setHeatSinkAmount(int amount) {
        this.heatSinkAmount = amount;
    }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitch(boolean state, int currentSwitch) {
        this.switches[currentSwitch] = state;
        setChanged();
    }

    public long getFusionPower() {
        return fusionPower;
    }

    public void setFusionPower(long fusionPower) {
        this.fusionPower = fusionPower;
        setChanged();
    }

    public void addFusionPower(long fusionPower) {
        if (getSwitch(2)) {
            this.fusionPower = Math.max(0, Math.min(maxFusionPower + maxOverchargePower, this.fusionPower + fusionPower));
        } else {
            this.fusionPower = Math.max(0, Math.min(maxFusionPower, this.fusionPower + fusionPower));
        }
        setChanged();
    }

    public long getMaxFusionPower() {
        if (getSwitch(2)) {
            return maxFusionPower + maxOverchargePower;
        }
        return maxFusionPower;
    }

    public long getBaseMaxOverchargePower() {
        return maxOverchargePower;
    }

    public long getBaseMaxFusionPower() {
        return maxFusionPower;
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
    public void setEnergyLevel(InfiniteNumber energyLevel) {
        this.energyStorage.setInfiniteEnergy(energyLevel);
    }

    public InfinitePixelEnergyStorage getEnergyStorage() { return energyStorage; }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidTank getDuoFluidTank() {
        return duoFluidTank;
    }
}
