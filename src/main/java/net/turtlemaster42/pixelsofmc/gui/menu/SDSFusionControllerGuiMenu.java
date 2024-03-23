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
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IInfiniteEnergyMenu;
import net.turtlemaster42.pixelsofmc.gui.slots.ModResultSlot;
import net.turtlemaster42.pixelsofmc.gui.slots.ModTagRestrictedSlot;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMmenuType;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.network.PacketSyncLockedSlotToServer;
import net.turtlemaster42.pixelsofmc.network.PacketSyncSlotMaxToServer;
import net.turtlemaster42.pixelsofmc.network.PacketSyncSwitchToServer;
import org.jetbrains.annotations.NotNull;

import static net.turtlemaster42.pixelsofmc.init.POMmessages.sendToServer;

public class SDSFusionControllerGuiMenu extends AbstractContainerMenu implements IEnergyMenu, IDuoFluidMenu, IInfiniteEnergyMenu {
    public final SDSFusionControllerTile blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluid;
    private FluidStack duoFluid;

    public SDSFusionControllerGuiMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(7));
    }

    public SDSFusionControllerGuiMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(POMmenuType.SDS_CONTROLLER_MENU.get(), pContainerId);
        checkContainerSize(inv, 10);
        blockEntity = ((SDSFusionControllerTile) entity);
        this.level = inv.player.level;
        this.data = data;
        this.fluid = blockEntity.getFluid();
        this.duoFluid = blockEntity.getDuoFluid();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new ModResultSlot(handler, 9, 139, 35));
            this.addSlot(new ModTagRestrictedSlot(handler, 0, 35, 18, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 1, 53, 18, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 2, 71, 18, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 3, 35, 36, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 4, 53, 36, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 5, 71, 36, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 6, 35, 54, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 7, 53, 54, () -> POMtags.Items.ATOM));
            this.addSlot(new ModTagRestrictedSlot(handler, 8, 71, 54, () -> POMtags.Items.ATOM));

        });

        addDataSlots(data);
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

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 48; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledEnergy() { //energy test
        long energyPercent = this.blockEntity.getEnergyPercentage();
        int progressArrowSize = 44; // This is the height in pixels of your arrow

        return (int) (progressArrowSize / 100f * energyPercent);
    }

    public int getScaledFusionPower() {
        int fusionPower = this.blockEntity.getFusionPower(); // Current
        int maxFusionPower = this.blockEntity.getMaxFusionPower();  // Max
        int progressArrowSize = 36; // This is the height in pixels of your arrow

        return maxFusionPower != 0 && fusionPower != 0 ? (int) (fusionPower * 1f / maxFusionPower  * progressArrowSize) : 0;
    }

    public void setSlotLimit(int slotLimit) {
        if (slotLimit > 64)
            slotLimit = 64;
        this.blockEntity.setSlotLimit(slotLimit);
        sendToServer(new PacketSyncSlotMaxToServer(blockEntity.getBlockPos(), slotLimit));
    }

    public int getSlotLimit() {
        return this.blockEntity.getSlotLimit();
    }

    public void setSlotLock(boolean locked, int slot) {
        this.blockEntity.setSlotLock(locked, slot);
        sendToServer(new PacketSyncLockedSlotToServer(blockEntity.getBlockPos(), locked, slot));
    }

    public boolean getSlotLock(int slot) {
        return this.blockEntity.getSlotLock(slot);
    }

    public void setSwitch(boolean on, int currentSwitch) {
        this.blockEntity.setSwitches(on, currentSwitch);
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

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots and the player inventory slots and the hotbar.
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
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!customMoveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!customMoveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
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


    protected boolean customMoveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {
        boolean flag = false;
        int i = pStartIndex;
        if (pReverseDirection) {
            i = pEndIndex - 1;
        }

        if (pStack.isStackable()) {
            while(!pStack.isEmpty()) {
                if (pReverseDirection) {
                    if (i < pStartIndex) {
                        break;
                    }
                } else if (i >= pEndIndex) {
                    break;
                }

                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(pStack, itemstack)) {
                    int j = itemstack.getCount() + pStack.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), pStack.getMaxStackSize());
                    if (j <= maxSize) {
                        pStack.setCount(0);
                        itemstack.setCount(j);
                        slot.setChanged();
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        pStack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.setChanged();
                        flag = true;
                    }
                }

                if (pReverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!pStack.isEmpty()) {
            if (pReverseDirection) {
                i = pEndIndex - 1;
            } else {
                i = pStartIndex;
            }

            while(true) {
                if (pReverseDirection) {
                    if (i < pStartIndex) {
                        break;
                    }
                } else if (i >= pEndIndex) {
                    break;
                }

                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.getMaxStackSize() > 0 && slot1.mayPlace(pStack)) {
                    if (pStack.getCount() > slot1.getMaxStackSize()) {
                        slot1.set(pStack.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.set(pStack.split(pStack.getCount()));
                    }

                    slot1.setChanged();
                    flag = true;
                    break;
                }

                if (pReverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }




    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, POMblocks.SDS_CONTROLLER.get());
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

