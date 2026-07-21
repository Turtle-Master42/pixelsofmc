package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.tile.HotIsostaticPressTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.EnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.HeatUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.SpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenus;
import org.jetbrains.annotations.NotNull;

public class HotIsostaticPressMenu extends AbstractMachineMenu implements IEnergyMenu {
    public final HotIsostaticPressTile blockEntity;

    public HotIsostaticPressMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(12));
    }

    public HotIsostaticPressMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenus.HOT_ISOTOPIC_PRESS_MENU.get(), pContainerId);
        blockEntity = ((HotIsostaticPressTile) entity);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SpeedUpgradeSlot(handler, 4, 161, 8));
            this.addSlot(new EnergyUpgradeSlot(handler, 5, 161, 26));
            this.addSlot(new HeatUpgradeSlot(handler, 6, 161, 44));
            this.addSlot(new ResultSlot(handler, 3, 110, 34));
            this.addSlot(new SlotItemHandler(handler, 2, 51, 9));
            this.addSlot(new SlotItemHandler(handler, 1, 51, 59));
            this.addSlot(new SlotItemHandler(handler, 0, 51, 34));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public boolean isHeating() {
        return data.get(8) > 0 || data.get(9) > 0;
    }
    public int getHeat() {return data.get(6);}
    public int getRequiredHeat() {return blockEntity.getRequiredHeat();}
    public int getRequiredMaxHeat() {return blockEntity.getRequiredMaxHeat();}
    public int getTime() {return data.get(8);}
    public int getSoulTime() {return data.get(9);}
    public int getMaxTime() {return blockEntity.getMaxTime();}
    public int getEnergy() {return blockEntity.getEnergyStorage().getEnergyStored();}
    public int getMaxEnergy() {return blockEntity.getEnergyStorage().getMaxEnergyStored();}
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.blockEntity.requiredProgress(4);}

    public int getScaledProgressOne() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 39; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
    }
    public int getScaledProgressTwo() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 12; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
    }

    public int getScaledHeat() {
        int heat = this.data.get(6);
        int maxHeat = this.data.get(7);
        int progressArrowSize = 28;
        return maxHeat != 0 && heat <= 2500 ? ((maxHeat-heat) * progressArrowSize / maxHeat) :0;
    }

    public int getScaledSoulHeat() {
        int heat = this.data.get(6); //stored
        int maxSoulHeat = 2500;//this.data.get(11);  // Max
        int progressArrowSize = 27; // This is the height in pixels of your arrow
        return heat > 2500 ? (heat - 2500) * progressArrowSize / maxSoulHeat : 0;
    }

    public int getScaledBurnTime() {
        int time = this.data.get(8) + this.data.get(9);
        int maxTime = blockEntity.getMaxTime();
        int progressArrowSize = 16;
        return time * progressArrowSize / maxTime;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.HOT_ISOSTATIC_PRESS.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}


