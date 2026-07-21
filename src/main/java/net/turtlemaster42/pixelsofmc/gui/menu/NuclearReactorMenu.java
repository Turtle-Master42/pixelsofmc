package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.gui.renderer.IButtonMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IInfiniteEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.DisplaySlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenus;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncSwitchToServer;
import net.turtlemaster42.pixelsofmc.tile.NuclearReactorTile;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class NuclearReactorMenu extends AbstractMachineMenu implements IEnergyMenu, IDuoFluidMenu, IInfiniteEnergyMenu, IButtonMenu {
    public final NuclearReactorTile blockEntity;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public NuclearReactorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public NuclearReactorMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(4, inv, data, POMmenus.NUCLEAR_REACTOR_MENU.get(), pContainerId);
        this.blockEntity = (NuclearReactorTile) entity;
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(handler -> {
            this.addSlot(new DisplaySlot(handler, 0, 80, 15)); // up
            this.addSlot(new DisplaySlot(handler, 1, 100, 35)); // right
            this.addSlot(new DisplaySlot(handler, 2, 80, 55)); // down
            this.addSlot(new DisplaySlot(handler, 3, 60, 35)); // left
        });

    }

    public void setSwitch(int currentSwitch, boolean on) {
        this.blockEntity.setSwitch(currentSwitch, on);
        sendToServer(new PacketSyncSwitchToServer(blockEntity.getBlockPos(), on, currentSwitch));
    }

    public boolean getSwitch(int currentSwitch) {
        return this.blockEntity.getSwitch(currentSwitch);
    }

    public float getEfficiencyBonus() {
        return this.blockEntity.getEfficiencyBonus();
    }

    @Override
    public void setDuoFluid(FluidStack fluidStack) {
        this.duoFluid = fluidStack;
    }
    @Override
    public FluidStack getDuoFluid() {
        return duoFluid;
    }
    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
    public FluidStack getFluid() {
        return fluid;
    }
    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.NUCLEAR_REACTOR.get());
    }
}

