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
import net.turtlemaster42.pixelsofmc.block.SDSFusionControllerBlock;
import net.turtlemaster42.pixelsofmc.gui.menu.IndustrialTurbineMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.recipe.FluidCoolingRecipe;
import net.turtlemaster42.pixelsofmc.util.Constants;
import net.turtlemaster42.pixelsofmc.util.block.*;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class IndustrialTurbineTile extends AbstractMachineTile<IndustrialTurbineTile> implements IMultiFluidHandlingTile, IDuoFluidHandlingTile, IButtonTile {

    protected final ContainerData data;
    private final boolean[] switches = {false};
    public int isRunning = 0;

    //TODO: make input only insert and output only extract
    private final FluidTank fluidTank = new FluidTank(64000) { // input
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

    private final FluidTank duoFluidTank = new FluidTank(64000) { // output
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

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyDuoFluidHandler = LazyOptional.empty();

    public IndustrialTurbineTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.INDUSTRIAL_TURBINE.get(), pWorldPosition, pBlockState, 8_000_000, 0);
        defineMaxProgress(0);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> IndustrialTurbineTile.this.isRunning;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) {
                    IndustrialTurbineTile.this.isRunning = pValue;
                }
            }

            @Override
            public int getCount() {return 4;}
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return slot == 0 && stack.is(POMtags.Items.ENERGY_UPGRADE);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.industrial_turbine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energyStorage.getEnergyStored(), worldPosition));
        return new IndustrialTurbineMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
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
        tag.put("tank1", fluidTank.writeToNBT(new CompoundTag()));
        tag.put("tank2", duoFluidTank.writeToNBT(new CompoundTag()));
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt.getCompound("tank1"));
        duoFluidTank.readFromNBT(nbt.getCompound("tank2"));
    }


    //---RECIPE---//
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialTurbineTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialTurbineTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, IndustrialTurbineTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && getSwitch(0)) {
            if (!pLevel.isClientSide()) {
                isRunning = 1;
                if (pState.getValue(SDSFusionControllerBlock.ACTIVE) != 3) {
                    pLevel.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 3), 2);
                }
            }
            craft(pBlockEntity);
        } else {
            if (!pLevel.isClientSide()) {
                isRunning = 0;
                if (pState.getValue(SDSFusionControllerBlock.ACTIVE) == 3) {
                    pLevel.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 2), 2);
                }
            }
            setChanged(pLevel, pPos, pState);
        }
    }


    private static boolean hasRecipe(IndustrialTurbineTile entity) {
        Level level = entity.level;
        if (level == null) {return false;}
        FluidContainer fluidInventory = new FluidContainer(1);

        fluidInventory.setFluid(0, entity.fluidTank.getFluid());
        Optional<FluidCoolingRecipe> match = level.getRecipeManager().getRecipeFor(FluidCoolingRecipe.Type.INSTANCE, fluidInventory, level);

        return match.isPresent()
                && canExtractInputFluid(new FluidStack(match.get().getFluidInput().getFluid(), 1000), entity.fluidTank)
                && canInsertOutputFluid(new FluidStack(match.get().getResultFluid().getFluid(), 1000), entity.duoFluidTank);
    }

    private static void craft(IndustrialTurbineTile entity) {
        Level level = entity.level;
        FluidContainer fluidInventory = new FluidContainer(1);

        fluidInventory.setFluid(0, entity.fluidTank.getFluid());
        Optional<FluidCoolingRecipe> match = level.getRecipeManager().getRecipeFor(FluidCoolingRecipe.Type.INSTANCE, fluidInventory, level);

        if(match.isPresent()) {

            entity.addFluidOutput(new FluidStack(match.get().getResultFluid().getFluid(), 1000), entity.duoFluidTank);
            entity.removeFluidInput(new FluidStack(match.get().getFluidInput().getFluid(), 1000), entity.fluidTank);
            entity.energyStorage.receiveEnergy((int) Constants.FE_turbineSteam, false);

            setChanged(level, entity.worldPosition, entity.getBlockState());
        }
    }

    public FluidTank getFluidTank() { return fluidTank; }
    public FluidTank getDuoFluidTank() { return duoFluidTank; }

    public boolean getSwitch(int currentSwitch) {
        return switches[currentSwitch];
    }

    public void setSwitch(int currentSwitch, boolean on) {
        this.switches[currentSwitch] = on;
        setChanged();
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return new FluidTank[]{fluidTank, duoFluidTank};
    }

    @Override
    public FluidTank getFluidTank(String name) {
        return switch (name) {
            case "input" -> fluidTank;
            case "output" -> duoFluidTank;
            default -> null;
        };
    }

    @Override
    public String[] getFluidTankNames() {
        return new String[]{"input", "output"};
    }
}


