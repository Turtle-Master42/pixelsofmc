package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.tile.PixelBombarderTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.EnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.StackLimitedSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.SpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenus;
import org.jetbrains.annotations.NotNull;

public class PixelBombarderMenu extends AbstractMachineMenu implements IEnergyMenu {
    public final PixelBombarderTile blockEntity;
    public final ItemStackHandler itemHandler;

    public PixelBombarderMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public PixelBombarderMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenus.PIXEL_BOMBARDER_MENU.get(), pContainerId);
        blockEntity = ((PixelBombarderTile) entity);
        itemHandler = blockEntity.getItemStackHandler();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new EnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new SlotItemHandler(handler, 0, 116, 15));
            this.addSlot(new StackLimitedSlot(handler, 1, 39, 37, 1));
            this.addSlot(new StackLimitedSlot(handler, 2, 57, 37, 1));
            this.addSlot(new StackLimitedSlot(handler, 3, 86, 37, 1));
            this.addSlot(new ResultSlot(handler, 4, 116, 58));
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
        int progressArrowSize = 24; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
    }

    public int getSourceColor() {
        return blockEntity.getSourceColor();
    }

    public int getSourceSize() {
        return blockEntity.getSourceSize();
    }

    public int getLaserColor() {
        return blockEntity.getLaserColor();
    }

    public int getLaserType() {
        return blockEntity.getLaserType();
    }

    public int getLaserSize() {
        return blockEntity.getLaserSize();
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.PIXEL_BOMBARDER.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}


