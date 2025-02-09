package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IInfiniteEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModTagRestrictedSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.item.AtomItem;
import net.turtlemaster42.pixelsofmc.network.PacketSyncSwitchToServer;
import net.turtlemaster42.pixelsofmc.util.Element;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class SDSFusionControllerMenu extends AbstractMachineMenu implements IEnergyMenu, IDuoFluidMenu, IInfiniteEnergyMenu {
    public final SDSFusionControllerTile blockEntity;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public SDSFusionControllerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(7));
    }

    public SDSFusionControllerMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(5, inv, data, POMmenuType.SDS_CONTROLLER_MENU.get(), pContainerId);
        checkContainerSize(inv, 5);
        blockEntity = ((SDSFusionControllerTile) entity);
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModResultSlot(handler, 2, 95, 24));
            this.addSlot(new ModResultSlot(handler, 3, 95, 42));
            this.addSlot(new ModResultSlot(handler, 4, 95, 60));
            this.addSlot(new ModTagRestrictedSlot(handler, 0, 38, 29, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 1, 38, 55, () -> POMtags.Items.ATOM));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getReason() {
        return data.get(5);
    }
    public int getElement() {
        return data.get(6);
    }

    public AtomItem getFirstElement() {
        Item item = this.blockEntity.getItemStackHandler().getStackInSlot(0).getItem();
        if (item instanceof AtomItem atom)
            return atom;
        return (AtomItem) Element.HYDROGEN.item();
    }

    public AtomItem getSecondElement() {
        Item item = this.blockEntity.getItemStackHandler().getStackInSlot(1).getItem();
        if (item instanceof AtomItem atom)
            return atom;
        return (AtomItem) Element.HYDROGEN.item();
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 37; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledEnergy() { //energy test
        long energyPercent = this.blockEntity.getEnergyPercentage();
        int progressArrowSize = 44; // This is the height in pixels of your arrow

        return (int) (progressArrowSize / 100f * energyPercent);
    }

    public int getScaledFusionPower() {
        long fusionPower = this.blockEntity.getFusionPower(); // Current
        long maxFusionPower = this.blockEntity.getBaseMaxFusionPower();  // Max
        int progressArrowSize = 36; // This is the height in pixels of your arrow

        return maxFusionPower != 0 && fusionPower != 0 ? (int) (fusionPower * 1f / maxFusionPower  * progressArrowSize) : 0;
    }

//    public void setSlotLimit(int slotLimit) {
//        if (slotLimit > 64)
//            slotLimit = 64;
//        this.blockEntity.setSlotLimit(slotLimit);
//        sendToServer(new PacketSyncSlotMaxToServer(blockEntity.getBlockPos(), slotLimit));
//    }
//
//    public int getSlotLimit() {
//        return this.blockEntity.getSlotLimit();
//    }
//
//    public void setSlotLock(boolean locked, int slot) {
//        this.blockEntity.setSlotLock(locked, slot);
//        sendToServer(new PacketSyncLockedSlotToServer(blockEntity.getBlockPos(), locked, slot));
//    }
//
//    public boolean getSlotLock(int slot) {
//        return this.blockEntity.getSlotLock(slot);
//    }

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
                pPlayer, POMblocks.SDS_CONTROLLER.get());
    }
}

