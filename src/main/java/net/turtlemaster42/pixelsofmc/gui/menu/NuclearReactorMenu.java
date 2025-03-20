package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.NuclearReactorTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IInfiniteEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModDisplaySlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.network.PacketSyncSwitchToServer;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class NuclearReactorMenu extends AbstractMachineMenu implements IEnergyMenu, IDuoFluidMenu, IInfiniteEnergyMenu {
    public final NuclearReactorTile blockEntity;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public NuclearReactorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public NuclearReactorMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(4, inv, data, POMmenuType.NUCLEAR_REACTOR_MENU.get(), pContainerId);
        checkContainerSize(inv, 4);
        this.blockEntity = (NuclearReactorTile) entity;
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(handler -> {
            this.addSlot(new ModDisplaySlot(handler, 0, 51, 21));
            this.addSlot(new ModDisplaySlot(handler, 1, 81, 21));
            this.addSlot(new ModDisplaySlot(handler, 2, 51, 51));
            this.addSlot(new ModDisplaySlot(handler, 3, 81, 51));
        });

    }

    public int getScaledEnergy() { //energy test
        long energyPercent = this.blockEntity.getEnergyPercentage();
        int progressArrowSize = 44; // This is the height in pixels of your arrow

        return (int) (progressArrowSize / 100f * energyPercent);
    }

    public void setSwitch(boolean on, int currentSwitch) {
        this.blockEntity.setSwitch(on, currentSwitch);
        sendToServer(new PacketSyncSwitchToServer(blockEntity.getBlockPos(), on, currentSwitch));
    }

    public boolean getSwitch(int currentSwitch) {
        return this.blockEntity.getSwitch(currentSwitch);
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

