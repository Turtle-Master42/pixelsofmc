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
import net.turtlemaster42.pixelsofmc.block.tile.IndustrialCoolerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.*;
import net.turtlemaster42.pixelsofmc.gui.slots.ModHeatUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncSwitchToServer;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class IndustrialCoolerMenu extends AbstractMachineMenu implements IEnergyMenu, IFluidMenu, IDuoFluidMenu, ITriFluidMenu, IQuadFluidMenu {
    public final IndustrialCoolerTile blockEntity;
    public final ItemStackHandler itemHandler;
    private FluidStack fluid;
    private FluidStack duoFluid;
    private FluidStack triFluid;
    private FluidStack quadFluid;

    public IndustrialCoolerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public IndustrialCoolerMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(1, inv, data, POMmenuType.INDUSTRIAL_COOLER_MENU.get(), pContainerId);
        blockEntity = ((IndustrialCoolerTile) entity);
        itemHandler = blockEntity.getItemStackHandler();
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();
        this.triFluid = blockEntity.getTriFluid();
        this.quadFluid = blockEntity.getQuadFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> this.addSlot(new ModHeatUpgradeSlot(handler, 0, 161, 8)));
    }

    public boolean isCrafting() {return data.get(0) != 0;}

    public void setSwitch(boolean on, int currentSwitch) {
        this.blockEntity.setSwitch(currentSwitch, on);
        sendToServer(new PacketSyncSwitchToServer(blockEntity.getBlockPos(), on, currentSwitch));
    }

    public boolean getSwitch(int currentSwitch) {
        return this.blockEntity.getSwitch(currentSwitch);
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
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
    protected int playerInventoryHeightStart() {return 68;}

    @Override
    protected int playerHotBarHeightStart() {return 126;}

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, POMblocks.INDUSTRIAL_COOLER.get());
    }
}


