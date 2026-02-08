package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.tile.ChemicalSeparatorTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModMaxStackSizeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import org.jetbrains.annotations.NotNull;

public class ChemicalSeparatorMenu extends AbstractMachineMenu implements IEnergyMenu, IFluidMenu, IDuoFluidMenu {
    public final ChemicalSeparatorTile blockEntity;
    public final ItemStackHandler itemHandler;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public ChemicalSeparatorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public ChemicalSeparatorMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(9, inv, data, POMmenuType.CHEMICAL_SEPARATOR_MENU.get(), pContainerId);
        blockEntity = ((ChemicalSeparatorTile) entity);
        itemHandler = blockEntity.getItemStackHandler();
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 4, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 5, 161, 26));
            this.addSlot(new SlotItemHandler(handler, 6, 40, 5));
            this.addSlot(new ModMaxStackSizeSlot(handler, 7, 85, 5, 1));
            this.addSlot(new ModMaxStackSizeSlot(handler, 8, 85, 21, 1));
            this.addSlot(new ModResultSlot(handler, 1, 125, 29));
            this.addSlot(new ModResultSlot(handler, 2, 123, 47));
            this.addSlot(new ModResultSlot(handler, 3, 121, 65));
            this.addSlot(new SlotItemHandler(handler, 0, 48, 47));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.blockEntity.requiredProgress(4);}

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 59; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / getMaxProgress() : 0;
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
                pPlayer, POMblocks.CHEMICAL_SEPARATOR.get());
    }
}


