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

public class BallMillGuiMenu extends AbstractContainerMenu implements IEnergyMenu {
    public final BallMillTile blockEntity;
    private final Level level;
    private final ContainerData data;

    public BallMillGuiMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public BallMillGuiMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(POMmenuType.BALL_MILL_MENU.get(), pContainerId);
        checkContainerSize(inv, 7);
        blockEntity = ((BallMillTile) entity);
        this.level = inv.player.level;
        this.data = data;


        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModSpeedUpgradeSlot(handler, 5, 161, 8));
            this.addSlot(new ModEnergyUpgradeSlot(handler, 6, 161, 26));
            this.addSlot(new ModResultSlot(handler, 4, 129, 36));
            this.addSlot(new ModTagRestrictedSlot(handler, 3, 80, 36,  () -> POMtags.Items.MILLING_BALL));
            this.addSlot(new SlotItemHandler(handler, 0, 31, 14));
            this.addSlot(new SlotItemHandler(handler, 1, 31, 36));
            this.addSlot(new SlotItemHandler(handler, 2, 31, 58));
        });

        addDataSlots(data);

    }

    public boolean isCrafting() {return data.get(0) > 0;}
    public int getProgress() {return this.data.get(0);}
    public int getMaxProgress() {return this.data.get(1) - this.data.get(2);}

    public int getScaledProgressOne() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int speedUpgrade = this.data.get(2); // Speed upgrades
        int progressArrowSize = 82; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / (maxProgress - speedUpgrade) : 0;
    }

    public int getScaledEnergy() { //energy test
        int energy = this.data.get(5); //stored energy
        int maxEnergy = this.data.get(3);  // Max Energy
        int progressArrowSize = 44; // This is the height in pixels of your arrow


        return maxEnergy != 0 && energy != 0 ? (energy * progressArrowSize / maxEnergy) : 0;
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
    private static final int TE_INVENTORY_SLOT_COUNT = 7;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
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
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.BALL_MILL.get());
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

    @Override
    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}


