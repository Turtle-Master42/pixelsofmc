package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.block.tile.ChemicalMixerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModMaxStacksizeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.network.PacketSyncSwitchToServer;
import net.turtlemaster42.pixelsofmc.network.PacketSyncTemperatureSwitchToServer;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class ChemicalMixerMenu extends AbstractMachineMenu implements IEnergyMenu, IFluidMenu, IDuoFluidMenu, ITriFluidMenu, IQuadFluidMenu, IQuinFluidMenu, IHexaFluidMenu {
    public final ChemicalMixerTile blockEntity;
    public final ItemStackHandler itemHandler;
    private FluidStack fluid;
    private FluidStack duoFluid;
    private FluidStack triFluid;
    private FluidStack quadFluid;
    private FluidStack quinFluid;
    private FluidStack hexaFluid;

    public ChemicalMixerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public ChemicalMixerMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(8, inv, data, POMmenuType.CHEMICAL_MIXER_MENU.get(), pContainerId);
        checkContainerSize(inv, 8);
        blockEntity = ((ChemicalMixerTile) entity);
        itemHandler = blockEntity.getItemStackHandler();
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();
        this.triFluid = blockEntity.getTriFluid();
        this.quinFluid = blockEntity.getQuinFluid();
        this.hexaFluid = blockEntity.getHexaFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 6, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 7, 161, 26));
            this.addSlot(new ModMaxStacksizeSlot(handler, 0, 32, 5, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 1, 32, 23, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 2, 32, 41, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 3, 136, 5, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 4, 136, 23, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 5, 136, 41, 1));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.data.get(1) - this.data.get(2);}

    public void setSwitch(boolean on, int currentSwitch) {
        this.blockEntity.setSwitch(on, currentSwitch);
        sendToServer(new PacketSyncSwitchToServer(blockEntity.getBlockPos(), on, currentSwitch));
    }

    public boolean getSwitch(int currentSwitch) {
        return this.blockEntity.getSwitch(currentSwitch);
    }

    public int getState() {
        return this.blockEntity.getTemperatureState();
    }

    public void setState(int state) {
        this.blockEntity.setTemperatureState(state);
        sendToServer(new PacketSyncTemperatureSwitchToServer(blockEntity.getBlockPos(), state));
    }


    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int speedUpgrade = this.data.get(2); // Speed upgrades
        int progressArrowSize = 16; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / (maxProgress - speedUpgrade) : 0;
    }

    public int getScaledEnergy() { //energy test
        int energy = this.data.get(5); //stored energy
        int maxEnergy = this.data.get(3);  // Max Energy
        int progressArrowSize = 44; // This is the height in pixels of your arrow
        return maxEnergy != 0 && energy != 0 ? (energy * progressArrowSize / maxEnergy) : 0;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public void setHexaFluid(FluidStack fluidStack) {
        this.hexaFluid = fluidStack;
    }
    public FluidStack getHexaFluid() {
        return hexaFluid;
    }
    public void setQuinFluid(FluidStack fluidStack) {
        this.quinFluid = fluidStack;
    }
    public FluidStack getQuinFluid() {
        return quinFluid;
    }
    public void setQuadFluid(FluidStack fluidStack) {
        this.quadFluid = fluidStack;
    }
    public FluidStack getQuadFluid() {
        return quadFluid;
    }
    public void setTriFluid(FluidStack fluidStack) {
        this.triFluid = fluidStack;
    }
    public FluidStack getTriFluid() {
        return triFluid;
    }
    public void setDuoFluid(FluidStack fluidStack) {
        this.duoFluid = fluidStack;
    }
    public FluidStack getDuoFluid() {
        return duoFluid;
    }
    public FluidStack getFluid() {
        return fluid;
    }
    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, POMblocks.CHEMICAL_MIXER.get());
    }
}


