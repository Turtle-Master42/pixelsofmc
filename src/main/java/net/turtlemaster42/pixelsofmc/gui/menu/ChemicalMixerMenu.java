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
import net.turtlemaster42.pixelsofmc.tile.ChemicalMixerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import net.turtlemaster42.pixelsofmc.gui.slots.EnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.StackLimitedSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.SpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenus;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncSwitchToServer;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncTemperatureSwitchToServer;
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
        super(8, inv, data, POMmenus.CHEMICAL_MIXER_MENU.get(), pContainerId);
        blockEntity = ((ChemicalMixerTile) entity);
        itemHandler = blockEntity.getItemStackHandler();
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();
        this.triFluid = blockEntity.getTriFluid();
        this.quinFluid = blockEntity.getQuinFluid();
        this.hexaFluid = blockEntity.getHexaFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SpeedUpgradeSlot(handler, 6, 161, 8));
            this.addSlot(new EnergyUpgradeSlot(handler, 7, 161, 26));
            this.addSlot(new StackLimitedSlot(handler, 0, 32, 5, 1));
            this.addSlot(new StackLimitedSlot(handler, 1, 32, 23, 1));
            this.addSlot(new StackLimitedSlot(handler, 2, 32, 41, 1));
            this.addSlot(new StackLimitedSlot(handler, 3, 136, 5, 1));
            this.addSlot(new StackLimitedSlot(handler, 4, 136, 23, 1));
            this.addSlot(new StackLimitedSlot(handler, 5, 136, 41, 1));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.blockEntity.requiredProgress(6);}

    public void setSwitch(boolean on, int currentSwitch) {
        this.blockEntity.setSwitch(currentSwitch, on);
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
        int progressArrowSize = 16; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
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


