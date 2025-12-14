package net.turtlemaster42.pixelsofmc.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterMenu;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.CountedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

import static net.minecraft.core.particles.ParticleTypes.CRIT;


public class PixelSplitterTile extends AbstractMachineTile<PixelSplitterTile> {

    protected final ContainerData data;

    public PixelSplitterTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.PIXEL_SPLITTER.get(), pWorldPosition, pBlockState, 1024000, 256);
        defineMaxProgress(72);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> PixelSplitterTile.this.progress;
                    case 1 -> PixelSplitterTile.this.maxProgress;
                    case 2 -> PixelSplitterTile.this.requiredProgress;
                    case 3 -> PixelSplitterTile.this.capacity;
                    case 4 -> PixelSplitterTile.this.maxReceive;
                    case 5 -> PixelSplitterTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PixelSplitterTile.this.progress = value;
                    case 1 -> PixelSplitterTile.this.maxProgress = value;
                    case 2 -> PixelSplitterTile.this.requiredProgress = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot<2) return true;
        else if (slot==5) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot==6) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        return false;
    }
    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==5) speedUpgradeCheck(5);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.pixel_splitter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new PixelSplitterMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    //---RECIPE---//
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PixelSplitterTile pBlockEntity) {
        if(hasRecipe(pBlockEntity) && pBlockEntity.hasPower(6, 5)) {
            pBlockEntity.progress++;
            pBlockEntity.consumePower(6, 5);
            if(pBlockEntity.progress > pBlockEntity.requiredProgress) {
                   craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(PixelSplitterTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelSplitterRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelSplitterRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent()
                && canInsertIntoOutputSlot(entity, match.get())
                && hasToolsInToolSlot(entity);
    }

    private static boolean hasToolsInToolSlot(PixelSplitterTile entity) {
        return entity.itemHandler.getStackInSlot(1).getItem().isDamageable(entity.itemHandler.getStackInSlot(1)) && entity.itemHandler.getStackInSlot(1).is(POMtags.Items.CIRCLE_SAW);
    }

    private static void craftItem(PixelSplitterTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelSplitterRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelSplitterRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {

            List<CountedIngredient> outputs = match.get().getOutputs();
            boolean[] matched = new boolean[outputs.size()];
            // Iterate over the outputs -out-
            for (int out = 0;out < outputs.size(); out++) {
                ItemStack beginStack;

                //Checks if the output item is a pixelItem and gives it the necessary nbt if so
                if (match.get().getResultItems(out).getItem() instanceof PixelItem) {
                    ItemStack pixel = new ItemStack(match.get().getOutputs().get(out).asItem(), match.get().getOutputs().get(out).count());
                    PixelItem.createForPixel(pixel, match.get().getColor(0).getRGB(), match.get().getColor(1).getRGB(), match.get().getColor(2).getRGB(), match.get().getStructure());
                    beginStack = pixel;
                } else {
                    beginStack = new ItemStack(match.get().getOutputs().get(out).asItem(), match.get().getOutputs().get(out).count());
                }

                ItemStack newStack;
                newStack = beginStack;
                // Iterate over the slots -slot-
                for (int slot = 2; slot < 5; slot++) {
                    // if already matched continue output cycle
                    if (matched[out])
                        continue;
                    //if it can insert it will, otherwise continue slot cycle
                    if (canInsertItemIntoSlot(entity.itemHandler.getStackInSlot(slot), newStack, match.get())) {
                        //inserts items and sets newStack to that what could not be inserted
                        if (newStack==beginStack) {
                            newStack = entity.customInsertItemStack(slot, newStack, false);
                        }
                        else newStack=ItemStack.EMPTY;
                        // if newStack is empty, matched = true
                        if (newStack.isEmpty()) {
                            matched[out]=true;
                        }
                    }
                }
            }
            entity.removeInput(0);
            entity.itemHandler.getStackInSlot(1).hurt(1, RandomSource.create(), null); //saw

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private static boolean canInsertItemIntoSlot(ItemStack slotStack, ItemStack inputStack, PixelSplitterRecipe recipe) {
        if ((slotStack.getItem() == inputStack.getItem() && slotStack.getCount() + inputStack.getCount() <= slotStack.getMaxStackSize()) || slotStack.isEmpty()) {
            if (inputStack.getItem() instanceof PixelItem)
                if (slotStack.getTagElement("structure") != null)
                    return slotStack.getTagElement("structure").getString("text").equals(recipe.getStructure());
                else return true;
            else return true;
        }
        else return false;
    }

    private static boolean canInsertIntoOutputSlot (PixelSplitterTile entity, PixelSplitterRecipe recipe) {
        boolean[] matched = new boolean[recipe.getOutputs().size()];
        boolean[] matchNeeded = new boolean[recipe.getOutputs().size()];
        ItemStack[] newStackInSlot = new ItemStack[5];
        ItemStack newStack;

        // Makes sure that newStackInSlot[] is not null
        for (int i = 2; i < 5; i++)
            newStackInSlot[i] = ItemStack.EMPTY;

        // Iterate over the inputs -q-
        for (int q = 0; q < recipe.getOutputs().size(); q++) {
            matchNeeded[q] = true;
            newStack = recipe.getResultItems(q);
            // Iterate over the slots -p-
            for (int p = 2; p < 5; p++) {
                if (matched[q])
                    continue;
                ItemStack[] stackInSlot = new ItemStack[5];
                stackInSlot[p] = entity.itemHandler.getStackInSlot(p);
                if (stackInSlot[p].isEmpty())
                    stackInSlot[p] = newStackInSlot[p];
                if (canInsertItemIntoSlot(stackInSlot[p], newStack, recipe)) {
                    newStackInSlot[p] = newStack;
                    newStack = entity.insertItemStack(p, newStack, true);
                    if (newStack.isEmpty()) {
                        matched[q] = true;
                    }
                }
            }
        }

        for (int i = 0; i < recipe.getOutputs().size(); i++) {
            if (matched[i]!=matchNeeded[i])
                return false;
        }
        return true;
    }

    public ItemStack customInsertItemStack(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        ItemStack existing = this.itemHandler.getStackInSlot(slot);
        int limit =  Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize());
        boolean limitReached = existing.getCount() + stack.getCount() > limit;

        if (!limitReached && !simulate) {
            stack.setCount(existing.getCount() + stack.getCount());
            itemHandler.setStackInSlot(slot, stack);
        }
        if (limitReached) {

            if (!simulate) {
                stack.setCount(stack.getMaxStackSize());
                itemHandler.setStackInSlot(slot, stack);
            }
            stack.setCount(existing.getCount()+stack.getCount()-stack.getMaxStackSize());
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public void activeParticles(LevelAccessor world) {
        int x = this.getBlockPos().getX();
        int y = this.getBlockPos().getY();
        int z = this.getBlockPos().getZ();
        if (world instanceof ServerLevel serverlevel)
            serverlevel.sendParticles(CRIT, x, y, z, 10, 1, 1, 1, 0);
    }
}
