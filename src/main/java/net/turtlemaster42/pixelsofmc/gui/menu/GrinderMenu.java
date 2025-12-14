package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.tile.GrinderTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import org.jetbrains.annotations.NotNull;

public class GrinderMenu extends AbstractMachineMenu implements IEnergyMenu {
    public final GrinderTile blockEntity;
    public final ItemStackHandler itemHandler;

    public GrinderMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public GrinderMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenuType.GRINDER_MENU.get(), pContainerId);
        blockEntity = ((GrinderTile) entity);
        itemHandler = blockEntity.getItemStackHandler();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new ModResultSlot(handler, 1, 111, 9));
            this.addSlot(new ModResultSlot(handler, 2, 111, 27));
            this.addSlot(new ModResultSlot(handler, 3, 111, 45));
            this.addSlot(new ModResultSlot(handler, 4, 111, 63));
            this.addSlot(new SlotItemHandler(handler, 0, 43, 36));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.blockEntity.requiredProgress(5);}

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 51; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
    }

    public int getScaledEnergy() { //energy test
        int energy = this.data.get(5); //stored energy
        int maxEnergy = this.data.get(3);  // Max Energy
        int progressArrowSize = 44; // This is the height in pixels of your arrow
        return maxEnergy != 0 && energy != 0 ? (energy * progressArrowSize / maxEnergy) : 0;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.GRINDER.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}


