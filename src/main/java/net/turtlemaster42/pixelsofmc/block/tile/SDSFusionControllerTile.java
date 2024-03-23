package net.turtlemaster42.pixelsofmc.block.tile;

import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.MP4Util;
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
import net.turtlemaster42.pixelsofmc.gui.menu.SDSFusionControllerGuiMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.item.AtomItem;
import net.turtlemaster42.pixelsofmc.network.*;
import net.turtlemaster42.pixelsofmc.recipe.machines.FusionRecipe;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInfiniteEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class SDSFusionControllerTile extends AbstractMachineTile<SDSFusionControllerTile> implements IDuoFluidHandlingTile, IInfiniteEnergyHandlingTile {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 10;
    private final InfiniteNumber capacity = new InfiniteNumber().fromLong(4096000000L);
    private final int maxReceive = 2048000000;
    private static final int energyConsumption = 12000;
    private int fusionPower = 0;
    private final int maxFusionPower = 100000000;
    private int cantCraftReason = 0;
    private int cantCraftElement = 0;

    public int inputSlotLimit = 64;
    public boolean[] slotLock = new boolean[]{false, false, false, false, false, false, false, false, false};
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

    private final FluidTank fluidTank = new FluidTank(50000) {
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

    private final FluidTank duoFluidTank = new FluidTank(50000) {
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
        return slot < 9 && (stack.is(POMtags.Items.ATOM)) && getSlotLimits(slot) != 0;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 9;
    }
    @Override
    protected int itemHandlerSize() {return 10;}

    @Override
    protected int getSlotLimits(int slot) {
        if (slot < 9)
            if (slot == 0 && slotLock[0])
                return 0;
            else if (slot == 1 && slotLock[1])
                return 0;
            else if (slot == 2 && slotLock[2])
                return 0;
            else if (slot == 3 && slotLock[3])
                return 0;
            else if (slot == 4 && slotLock[4])
                return 0;
            else if (slot == 5 && slotLock[5])
                return 0;
            else if (slot == 6 && slotLock[6])
                return 0;
            else if (slot == 7 && slotLock[7])
                return 0;
            else if (slot == 8 && slotLock[8])
                return 0;
            else
                return inputSlotLimit;
        else
            return 64;
    }

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
        return new SDSFusionControllerGuiMenu(pContainerId, pInventory, this, this.data);
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
        tag.putInt("fusionPower", fusionPower);
        tag.putInt("slotLimit", inputSlotLimit);
        tag = fluidTank.writeToNBT(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTag = duoFluidTank.writeToNBT(fluidTag);
        tag.put("outFluid", fluidTag);
        CompoundTag slotTag = new CompoundTag();
        slotTag.putBoolean("0", slotLock[0]);
        slotTag.putBoolean("1", slotLock[1]);
        slotTag.putBoolean("2", slotLock[2]);
        slotTag.putBoolean("3", slotLock[3]);
        slotTag.putBoolean("4", slotLock[4]);
        slotTag.putBoolean("5", slotLock[5]);
        slotTag.putBoolean("6", slotLock[6]);
        slotTag.putBoolean("7", slotLock[7]);
        slotTag.putBoolean("8", slotLock[8]);
        tag.put("lockedSlot", slotTag);
        tag.putBoolean("switch1", switches[0]);
        tag.putBoolean("switch2", switches[1]);
        tag.putBoolean("switch3", switches[2]);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        energyStorage.setEnergy(nbt.getInt("Energy"));
        energyStorage.setInfiniteEnergy(new InfiniteNumber().fromString(nbt.getString("InfiniteEnergy")));
        fusionPower = nbt.getInt("fusionPower");
        inputSlotLimit = nbt.getInt("slotLimit");
        fluidTank.readFromNBT(nbt);
        duoFluidTank.readFromNBT(nbt.getCompound("outFluid"));
        slotLock[0] = nbt.getCompound("lockedSlot").getBoolean("0");
        slotLock[1] = nbt.getCompound("lockedSlot").getBoolean("1");
        slotLock[2] = nbt.getCompound("lockedSlot").getBoolean("2");
        slotLock[3] = nbt.getCompound("lockedSlot").getBoolean("3");
        slotLock[4] = nbt.getCompound("lockedSlot").getBoolean("4");
        slotLock[5] = nbt.getCompound("lockedSlot").getBoolean("5");
        slotLock[6] = nbt.getCompound("lockedSlot").getBoolean("6");
        slotLock[7] = nbt.getCompound("lockedSlot").getBoolean("7");
        slotLock[8] = nbt.getCompound("lockedSlot").getBoolean("8");
        switches[0] = nbt.getBoolean("switch1");
        switches[1] = nbt.getBoolean("switch2");
        switches[2] = nbt.getBoolean("switch3");
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
            if (getSwitch(2))
               addFusionPower(1000000);
            else
                addFusionPower(100000);
        }
        if (getSwitch(1)) {
            if (getSwitch(2))
                addFusionPower(-500000);
            else
                addFusionPower(-50000);
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
                && canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
                && isFusible(match.get().getResultItem());
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
            double inputMass = 0;
            double outputMass = recipe.getElement().getMass();

            if (recipe.x512()) {
                outputMass = outputMass * 8;
            }


            // Iterate over the slots -q-
            for (int q = 0; q < 9; q++) {
                if (entity.itemHandler.getStackInSlot(q).getItem() instanceof AtomItem atom) {
                    if (entity.itemHandler.getStackInSlot(q).is(POMtags.Items.ATOM512))
                        inputMass += atom.getElementalMass() * 8;
                    else
                        inputMass += atom.getElementalMass();
                    entity.removeInput(q);
                }
            }

            PixelsOfMc.LOGGER.info("Input Mass: {}", inputMass);
            PixelsOfMc.LOGGER.info("Output Mass: {} {}", recipe.getElement().elementName(), outputMass);
            double deltaMass = inputMass - outputMass;
            double energy = deltaMass * 2.998 * Math.pow(10, 16);
            PixelsOfMc.LOGGER.info("Energy J: {}, delta m: {}", energy, deltaMass);
            PixelsOfMc.LOGGER.info("Energy FE: {}", energy * 0.4);

            entity.addOutput(recipe.getResultItem(), 9);

            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void resetProgress() {this.progress = 0;}


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(9).getItem() == output.getItem() || inventory.getItem(9).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(9).getMaxStackSize() >= inventory.getItem(9).getCount() + count;
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

    public void setSlotLimit(int slotLimit) {
        this.inputSlotLimit = slotLimit;
        setChanged();
    }

    public int getSlotLimit() {
        return this.inputSlotLimit;
    }

    public void setSlotLock(boolean locked, int slot) {
        this.slotLock[slot] = locked;
        setChanged();
    }

    public boolean getSlotLock(int slot) {
        return this.slotLock[slot];
    }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitches(boolean on, int currentSwitch) {
        this.switches[currentSwitch] = on;
        setChanged();
    }

    public int getFusionPower() {
        return fusionPower;
    }

    public void setFusionPower(int fusionPower) {
        this.fusionPower = fusionPower;
        setChanged();
    }

    public void addFusionPower(int fusionPower) {
        this.fusionPower = Math.max(0, Math.min(maxFusionPower, this.fusionPower + fusionPower));
        setChanged();
    }

    public int getMaxFusionPower() {
        return maxFusionPower;
    }

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy " + energyStorage.getEnergyStored() + " is higher than max " + energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at " + this.getBlockPos() + " was outside limits, energy reverted to 0");
        }
    }

    @Override
    public void setEnergyLevel(InfiniteNumber energyLevel) {
        this.energyStorage.setInfiniteEnergy(energyLevel);
    }

    public InfinitePixelEnergyStorage getEnergyStorage() { return energyStorage; }

}
