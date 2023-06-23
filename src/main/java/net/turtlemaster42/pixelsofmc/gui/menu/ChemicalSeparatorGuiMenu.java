package net.turtlemaster42.pixelsofmc.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.turtlemaster42.pixelsofmc.block.tile.ChemicalSeparatorTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModEnergyUpgradeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModMaxStacksizeSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModSpeedUpgradeSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import org.jetbrains.annotations.NotNull;

public class ChemicalSeparatorGuiMenu extends AbstractContainerMenu implements IEnergyMenu, IFluidMenu, IDuoFluidMenu {
    public final ChemicalSeparatorTile blockEntity;
    public final ItemStackHandler itemHandler;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public ChemicalSeparatorGuiMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public ChemicalSeparatorGuiMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(POMmenuType.CHEMICAL_SEPARATOR_MENU.get(), pContainerId);
        checkContainerSize(inv, 9);
        blockEntity = ((ChemicalSeparatorTile) entity);
        itemHandler = blockEntity.getItemStackHandler();
        this.level = inv.player.level;
        this.data = data;
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 4, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 5, 161, 26));
            this.addSlot(new SlotItemHandler(handler, 6, 40, 5));
            this.addSlot(new ModMaxStacksizeSlot(handler, 7, 85, 5, 1));
            this.addSlot(new ModMaxStacksizeSlot(handler, 8, 85, 21, 1));
            this.addSlot(new ModResultSlot(handler, 1, 124, 29));
            this.addSlot(new ModResultSlot(handler, 2, 120, 47));
            this.addSlot(new ModResultSlot(handler, 3, 116, 65));
            this.addSlot(new SlotItemHandler(handler, 0, 48, 47));
        });
        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.data.get(1) - this.data.get(2);}

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int speedUpgrade = this.data.get(2); // Speed upgrades
        int progressArrowSize = 57; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / (maxProgress - speedUpgrade) : 0;
    }

    public int getScaledEnergy() { //energy test
        int energy = this.data.get(5); //stored energy
        int maxEnergy = this.data.get(3);  // Max Energy
        int progressArrowSize = 44; // This is the height in pixels of your arrow
        return maxEnergy != 0 && energy != 0 ? (energy * progressArrowSize / maxEnergy) : 0;
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


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 9;  // must be the number of slots you have!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.CHEMICAL_SEPARATOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}


