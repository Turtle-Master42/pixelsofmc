package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.block.tile.PixelAssemblerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModMaxStackSizeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import org.jetbrains.annotations.NotNull;

public class PixelAssemblerMenu extends AbstractMachineMenu implements IEnergyMenu, IFluidMenu {
    public final PixelAssemblerTile blockEntity;
    private FluidStack fluid;

    public PixelAssemblerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public PixelAssemblerMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(7, inv, data, POMmenuType.PIXEL_ASSEMBLER_MENU.get(), pContainerId);
        blockEntity = ((PixelAssemblerTile) entity);
        this.fluid = blockEntity.getFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new ModResultSlot(handler, 3, 128, 38));
            this.addSlot(new SlotItemHandler(handler, 0, 29, 38));
            this.addSlot(new SlotItemHandler(handler, 1, 47, 29));
            this.addSlot(new SlotItemHandler(handler, 2, 47, 47));
            this.addSlot(new ModMaxStackSizeSlot(handler, 4, 146, 62, 1));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgressOne() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int speedUpgrade = this.data.get(2); // Speed upgrades
        int progressArrowSize = 61; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / (maxProgress - speedUpgrade) : 0;
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
        return this.data.get(1);
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
                pPlayer, POMblocks.PIXEL_ASSEMBLER.get());
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
