package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.tile.PixelSplitterTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.*;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import org.jetbrains.annotations.NotNull;

public class PixelSplitterMenu extends AbstractMachineMenu implements IEnergyMenu {
    public final PixelSplitterTile blockEntity;

    public PixelSplitterMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public PixelSplitterMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenuType.PIXEL_SPLITTER_MENU.get(), pContainerId);
        blockEntity = ((PixelSplitterTile) entity);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new ModResultSlot(handler, 2, 116, 32));
            this.addSlot(new ModResultSlot(handler, 3, 116, 50));
            this.addSlot(new ModResultSlot(handler, 4, 134, 41));
            this.addSlot(new ModTagRestrictedSlot(handler, 1, 80, 18, () -> POMtags.Items.CIRCLE_SAW));
            this.addSlot(new SlotItemHandler(handler, 0, 35, 41));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgressOne() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 61; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
    }

    public int getScaledProgressTwo() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 18; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress(): 0;
    }

    public int getScaledEnergy() { //energy test
        int energy = this.data.get(5); //stored energy
        int maxEnergy = this.data.get(3);  // Max Energy
        int progressArrowSize = 44; // This is the height in pixels of your arrow

        return maxEnergy != 0 && energy != 0 ? (energy * progressArrowSize / maxEnergy) : 0;
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getMaxProgress() {
        return this.blockEntity.requiredProgress(5);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.PIXEL_SPLITTER.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
