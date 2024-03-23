package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.PixelItemStackHandler;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;
import net.turtlemaster42.pixelsofmc.util.block.IInventoryHandlingTile;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

import static java.lang.Math.random;

public abstract class AbstractMachineTile<Tile extends BlockEntity> extends BlockEntity implements MenuProvider, IInventoryHandlingTile, IEnergyHandlingTile, IFluidHandlingTile {

    Tile tile;
    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    protected final ItemStackHandler itemHandler = new PixelItemStackHandler(itemHandlerSize()) {
        @Override
        protected void onContentsChanged(int slot) {
            if (level != null && !level.isClientSide()) {
                POMmessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
            contentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return isInputValid(slot, stack);
        }

        @Override
        public boolean isValidOutput(int slot) {
            return isSlotValidOutput(slot);
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return getSlotLimits(slot);
        }
    };

    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }
    protected boolean isSlotValidOutput(int slot) {
        return true;
    }
    protected int itemHandlerSize() {return 1;}
    protected void contentsChanged(int slot) {}
    protected int getSlotLimits(int slot) {return 64;}

    @Override
    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    protected void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);
        return compound;
    }

    // -- CRAFTING -- //

    public ItemStack insertItemStack(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        ItemStack existing = this.itemHandler.getStackInSlot(slot);
        int limit =  Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize());
        boolean limitReached = existing.getCount() + stack.getCount() > limit;

        if (existing.getItem() != stack.getItem() && !existing.isEmpty()) {
            return stack;
        }

        if (!limitReached && !simulate)
            itemHandler.setStackInSlot(slot, new ItemStack(stack.getItem(), existing.getCount()+stack.getCount()));
        if (limitReached) {
            if (!simulate) {
                itemHandler.setStackInSlot(slot, new ItemStack(stack.getItem(), stack.getMaxStackSize()));
            }
            return new ItemStack(stack.getItem(), existing.getCount()+stack.getCount()-stack.getMaxStackSize());
        }
        return ItemStack.EMPTY;
    }

    public void addOutput(ItemStack stack, int slot) {
        insertItemStack(slot, stack, false);
    }

    public void addOutput(CountedIngredient ingredient, int slot) {
        insertItemStack(slot, ingredient.asItemStack(), false);
    }

    public void addOutput(ChanceIngredient ingredient, int slot) {
        insertItemStack(slot, ingredient.asItemStack(), false);
    }

    public void addChanceOutput(ChanceIngredient ingredient, int slot) {
        // rolls if the output should be outputted
        if (ingredient.chance() >= random()) {
            insertItemStack(slot, ingredient.asItemStack(), false);
        }
    }

    public void addMultiOutput(List<CountedIngredient> recipeOutputs, int min_slot, int max_slot) {
        // Iterate over the recipeOutputs
        for (CountedIngredient output : recipeOutputs) {
            // Iterate over the slots
            for (int slot = min_slot; slot <= max_slot; slot++) {
                if (output.isEmpty()) {
                    break;
                }
                // tries to insert output
                ItemStack stack = insertItemStack(slot, output.asItemStack(), false);
                // sets output to the not inserted stack and tries to add it to other slots
                output = CountedIngredient.of(stack);
            }
        }
    }

    public void addMultiChanceOutput(List<ChanceIngredient> recipeOutputs, int min_slot, int max_slot) {
        // Iterate over the recipeOutputs
        for (ChanceIngredient output : recipeOutputs) {
            // rolls if the output should be outputted
            if (output.chance() >= random()) {
                // Iterate over the slots
                for (int slot = min_slot; slot <= max_slot; slot++) {
                    if (output.isEmpty()) {
                        break;
                    }
                    // tries to insert output
                    ItemStack stack = insertItemStack(slot, output.asItemStack(), false);
                    // sets output to the not inserted stack and tries to add it to other slots
                    output = ChanceIngredient.of(stack);
                }
            }
        }
    }


    public void removeInput(int slot, int amount) {
        itemHandler.extractItem(slot, amount, false);
    }

    public void removeInput(int slot) {
        itemHandler.extractItem(slot, 1, false);
    }

    public void removeMultiInput(List<CountedIngredient> recipeItems, int min_slot, int max_slot) {
        // Iterate over the recipeItems
        for (CountedIngredient recipeItem : recipeItems) {
            // Iterate over the slots
            for (int slot = min_slot; slot <= max_slot; slot++) {
                if (Ingredient.of(recipeItem.asItemStack()).test(itemHandler.getStackInSlot(slot))) {
                    ItemStack slotStack = itemHandler.getStackInSlot(slot);
                    if (slotStack.getCount() < recipeItem.count()) {
                        itemHandler.extractItem(slot, slotStack.getCount(), false);
                        recipeItem = CountedIngredient.of(recipeItem.count() - slotStack.getCount(), recipeItem.asItem());
                    } else {
                        itemHandler.extractItem(slot, recipeItem.count(), false);
                        break;
                    }
                }
            }
        }
    }

    public void removeMultiChanceInput(List<ChanceIngredient> recipeItems, int min_slot, int max_slot) {
        // Iterate over the recipeItems
        for (ChanceIngredient recipeItem : recipeItems) {
            // rolls if the input should be removed
            if (recipeItem.chance() >= random()) {
                // Iterate over the slots
                for (int slot = min_slot; slot <= max_slot; slot++) {
                    if (Ingredient.of(recipeItem.asItemStack()).test(itemHandler.getStackInSlot(slot))) {
                        ItemStack slotStack = itemHandler.getStackInSlot(slot);
                        if (slotStack.getCount() < recipeItem.count()) {
                            itemHandler.extractItem(slot, slotStack.getCount(), false);
                            recipeItem = ChanceIngredient.of(recipeItem.count() - slotStack.getCount(), recipeItem.asItem());
                        } else {
                            itemHandler.extractItem(slot, recipeItem.count(), false);
                            break;
                        }
                    }
                }
            }
        }
    }


    // -- ENERGY -- //

    @Override
    public void setEnergyLevel(int energyLevel) {}

    @Override
    public PixelEnergyStorage getEnergyStorage() {
        return null;
    }

    // -- FLUIDS -- //

    @Override
    public void setFluid(FluidStack fluid) {}

    @Override
    public FluidStack getFluid() {
        return null;
    }
}
