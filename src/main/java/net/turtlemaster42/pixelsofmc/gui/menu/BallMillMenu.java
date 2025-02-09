package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.block.tile.BallMillTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModTagRestrictedSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import org.jetbrains.annotations.NotNull;

public class BallMillMenu extends AbstractMachineMenu implements IEnergyMenu {
    public final BallMillTile blockEntity;

    public BallMillMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public BallMillMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenuType.BALL_MILL_MENU.get(), pContainerId);
        checkContainerSize(inv, 7);
        blockEntity = ((BallMillTile) entity);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new ModResultSlot(handler, 4, 129, 36));
            this.addSlot(new ModTagRestrictedSlot(handler, 3, 80, 36,  () -> POMtags.Items.MILLING_BALL));
            this.addSlot(new SlotItemHandler(handler, 0, 31, 14));
            this.addSlot(new SlotItemHandler(handler, 1, 31, 36));
            this.addSlot(new SlotItemHandler(handler, 2, 31, 58));
        });
    }

    public boolean isCrafting() {return data.get(0) > 0;}
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.data.get(1) - this.data.get(2);}

    public int getScaledProgressOne() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int speedUpgrade = this.data.get(2); // Speed upgrades
        int progressArrowSize = 84; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / (maxProgress - speedUpgrade) : 0;
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
                pPlayer, POMblocks.BALL_MILL.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}


