package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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
import net.turtlemaster42.pixelsofmc.gui.menu.IndustrialHeatExchangerMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncQuadFluidToClient;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncTriFluidToClient;
import net.turtlemaster42.pixelsofmc.recipe.FluidCoolingRecipe;
import net.turtlemaster42.pixelsofmc.recipe.FluidHeatingRecipe;
import net.turtlemaster42.pixelsofmc.util.block.*;
import net.turtlemaster42.pixelsofmc.util.recipe.FluidContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class IndustrialHeatExchangerTile extends AbstractMachineTile<IndustrialHeatExchangerTile> implements IMultiFluidHandlingTile, IDuoFluidHandlingTile, ITriFluidHandlingTile, IQuadFluidHandlingTile, IButtonTile {

    protected final ContainerData data;
    private final boolean[] switches = {false};
    public int isCrafting = 0;

    //TODO: make input only insert and output only extract
    private final FluidTank fluidTank = new FluidTank(64000) { // hot stuff
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

    private final FluidTank duoFluidTank = new FluidTank(64000) { //coolant
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

    private final FluidTank triFluidTank = new FluidTank(32000) { // heated coolant
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

    private final FluidTank quadFluidTank = new FluidTank(32000) { // cooled stuff
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

    public IndustrialHeatExchangerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.INDUSTRIAL_HEAT_EXCHANGER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {return isCrafting;}

            @Override
            public void set(int pIndex, int pValue) {}

            @Override
            public int getCount() {return 1;}
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return slot == 0 && stack.is(POMtags.Items.HEAT_UPGRADE);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.industrial_heat_exchanger");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        POMmessages.sendToClients(new PacketSyncFluidToClient(this.getFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncDuoFluidToClient(this.getDuoFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncTriFluidToClient(this.getTriFluid(), worldPosition));
        POMmessages.sendToClients(new PacketSyncQuadFluidToClient(this.getQuadFluid(), worldPosition));
        return new IndustrialHeatExchangerMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
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
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialHeatExchangerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, IndustrialHeatExchangerTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, IndustrialHeatExchangerTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && getSwitch(0)) {
            if (!pLevel.isClientSide()) {
                isCrafting = 1;
                if (pState.getValue(SDSFusionControllerBlock.ACTIVE) != 3) {
                    pLevel.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 3), 2);
                }
            }
            craft(pBlockEntity);
        } else {
            if (!pLevel.isClientSide()) {
                isCrafting = 0;
                if (pState.getValue(SDSFusionControllerBlock.ACTIVE) == 3) {
                    pLevel.setBlock(pPos, pState.setValue(SDSFusionControllerBlock.ACTIVE, 2), 2);
                }
            }
            setChanged(pLevel, pPos, pState);
        }
    }


    private static boolean hasRecipe(IndustrialHeatExchangerTile entity) {
        Level level = entity.level;
        if (level == null) {return false;}
        FluidContainer fluidInventory = new FluidContainer(1);

        fluidInventory.setFluid(0, entity.duoFluidTank.getFluid());
        Optional<FluidHeatingRecipe> coolant_match = level.getRecipeManager().getRecipeFor(FluidHeatingRecipe.Type.INSTANCE, fluidInventory, level);

        fluidInventory.setFluid(0, entity.fluidTank.getFluid());
        Optional<FluidCoolingRecipe> heat_match = level.getRecipeManager().getRecipeFor(FluidCoolingRecipe.Type.INSTANCE, fluidInventory, level);

        return heat_match.isPresent() && coolant_match.isPresent()
                && canExtractInputFluid(coolant_match.get().getFluidInput(), entity.duoFluidTank)
                && canInsertOutputFluid(coolant_match.get().getResultFluid(), entity.triFluidTank)
                && canExtractInputFluid(heat_match.get().getFluidInput(), entity.fluidTank)
                && canInsertOutputFluid(heat_match.get().getResultFluid(), entity.quadFluidTank);
    }

    private static void craft(IndustrialHeatExchangerTile entity) {
        Level level = entity.level;
        FluidContainer fluidInventory = new FluidContainer(1);

        fluidInventory.setFluid(0, entity.fluidTank.getFluid());
        Optional<FluidCoolingRecipe> heat_match = level.getRecipeManager().getRecipeFor(FluidCoolingRecipe.Type.INSTANCE, fluidInventory, level);

        fluidInventory.setFluid(0, entity.duoFluidTank.getFluid());
        Optional<FluidHeatingRecipe> coolant_match = level.getRecipeManager().getRecipeFor(FluidHeatingRecipe.Type.INSTANCE, fluidInventory, level);

        if(coolant_match.isPresent() && heat_match.isPresent()) {

            int heatEnergy = heat_match.get().getReleasedEnergy();
            int coolEnergy = coolant_match.get().getRequiredEnergy();

            float factor = 1f/((float)coolEnergy/(float)heatEnergy);

            int maxHeat = Math.min(entity.fluidTank.getFluid().getAmount(), entity.quadFluidTank.getSpace()) * heatEnergy;
            int maxCool = Math.min(entity.duoFluidTank.getFluid().getAmount(), entity.triFluidTank.getSpace()) * coolEnergy;

            int heatTotal = Mth.floor((float)maxCool / (float)heatEnergy);
            int coolTotal = Mth.floor((float)maxHeat / (float)coolEnergy);

            int total = Math.min(Math.min(heatTotal, coolTotal), 250 * (entity.itemHandler.getStackInSlot(0).getCount() + 2));

            float heatAmount = total;
            float coolAmount = (int) (total * factor);

            for (int i = 0; i < 25; i++) {
                if (coolAmount - Mth.floor(coolAmount) != 0 && coolAmount - Mth.floor(coolAmount) > 0.0001) {
                    heatAmount -= 1;
                    coolAmount -= factor;
                } else {
                    break;
                }
            }
            if ((int)heatAmount == 0 || (int)coolAmount == 0) {return;}

            //heating
            entity.addFluidOutput(new FluidStack(heat_match.get().getResultFluid().getFluid(), (int)heatAmount), entity.quadFluidTank);
            entity.removeFluidInput(new FluidStack(heat_match.get().getFluidInput().getFluid(), (int)heatAmount), entity.fluidTank);
            //coolant
            entity.addFluidOutput(new FluidStack(coolant_match.get().getResultFluid().getFluid(), (int)coolAmount), entity.triFluidTank);
            entity.removeFluidInput(new FluidStack(coolant_match.get().getFluidInput().getFluid(), (int)coolAmount), entity.duoFluidTank);

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


