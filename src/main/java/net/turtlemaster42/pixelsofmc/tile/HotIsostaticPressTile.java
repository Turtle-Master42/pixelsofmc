package net.turtlemaster42.pixelsofmc.tile;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.turtlemaster42.pixelsofmc.gui.menu.HotIsostaticPressMenu;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.recipe.machines.HotIsostaticPressRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;


public class HotIsostaticPressTile extends AbstractMachineTile<HotIsostaticPressTile> {

    protected final ContainerData data;
    private int heat;
    private final int maxHeat = 2500;
    private final int maxSoulHeat = 5000;

    private static int requiredHeat = -1;
    private static int requiredMaxHeat = -1;
    private int burnTime = 0;
    private int soulBurnTime = 0;
    private int maxBurnTime = 0;

    public HotIsostaticPressTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.HOT_ISOSTATIC_PRESS.get(), pWorldPosition, pBlockState, 1024000, 512);
        defineMaxProgress(144);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> HotIsostaticPressTile.this.progress;
                    case 1 -> HotIsostaticPressTile.this.maxProgress;
                    case 2 -> HotIsostaticPressTile.this.requiredProgress;
                    case 3 -> HotIsostaticPressTile.this.capacity;
                    case 4 -> HotIsostaticPressTile.this.maxReceive;
                    case 5 -> HotIsostaticPressTile.this.energyStorage.getEnergyStored();
                    case 6 -> HotIsostaticPressTile.this.heat;
                    case 7 -> HotIsostaticPressTile.this.maxHeat;
                    case 8 -> HotIsostaticPressTile.this.burnTime;
                    case 9 -> HotIsostaticPressTile.this.soulBurnTime;
                    case 10 -> HotIsostaticPressTile.this.maxBurnTime;
                    case 11 -> HotIsostaticPressTile.this.maxSoulHeat;
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> HotIsostaticPressTile.this.progress = value;
                    case 1 -> HotIsostaticPressTile.this.maxProgress = value;
                    case 2 -> HotIsostaticPressTile.this.requiredProgress = value;
                    case 6 -> HotIsostaticPressTile.this.heat = value;
                    case 8 -> HotIsostaticPressTile.this.burnTime = value;
                    case 9 -> HotIsostaticPressTile.this.soulBurnTime = value;
                    case 10 -> HotIsostaticPressTile.this.maxBurnTime = value;
                }
            }

            public int getCount() {
                return 11;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot == 1)
            return ForgeHooks.getBurnTime(stack, null) > 0 || stack.is(Items.ICE) || stack.is(Items.PACKED_ICE) || stack.is(Items.BLUE_ICE);
        return slot != 3 ;
    }

    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 3;
    }
    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==4) speedUpgradeCheck(4);
        else if (slot==1)
            if (itemHandler.getStackInSlot(1).is(Items.ICE)) {
                heat = heat - 50;
                itemHandler.extractItem(1, 1, false);
            }
            else if (itemHandler.getStackInSlot(1).is(Items.PACKED_ICE)) {
                heat = heat - 250;
                itemHandler.extractItem(1, 1, false);
            }
            else if (itemHandler.getStackInSlot(1).is(Items.BLUE_ICE)) {
                heat = heat - 1000;
                itemHandler.extractItem(1, 1, false);
            }
        if (heat < 0) heat=0;
    }

    public int getMaxTime() {return maxBurnTime;}
    public int getHeat() {return heat;}
    public int getRequiredHeat() {return requiredHeat;}
    public int getRequiredMaxHeat() {return requiredMaxHeat;}

    @Override
    public @NotNull Component getDisplayName() {return Component.translatable("block.pixelsofmc.hot_isostatic_press");}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new HotIsostaticPressMenu(pContainerId, pInventory, this, this.data);
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

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("heat", heat);
        tag.putInt("burnTime", burnTime);
        tag.putInt("soulBurnTime", soulBurnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        heat = nbt.getInt("heat");
        burnTime = nbt.getInt("burnTime");
        soulBurnTime = nbt.getInt("soulBurnTime");
        maxBurnTime = nbt.getInt("maxBurnTime");
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, HotIsostaticPressTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HotIsostaticPressTile pBlockEntity) {

        if(ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) > 0 && burnTime == 0 && soulBurnTime == 0) {
            int time = ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), null) / 20;
            maxBurnTime=time;
            if (itemHandler.getStackInSlot(1).is(POMtags.Items.SOUL_FUELS))
                soulBurnTime=time;
            else
                burnTime=time;

            if (!itemHandler.getStackInSlot(1).getCraftingRemainingItem().isEmpty() && itemHandler.getStackInSlot(1).getMaxStackSize() == 1)
                itemHandler.setStackInSlot(1, itemHandler.getStackInSlot(1).getCraftingRemainingItem());
            else
                itemHandler.extractItem(1, 1, false);
        }
        createHeat();
        createSoulHeat();

        if(hasRecipe(pBlockEntity) && hasPower(5, 4)) {
            pBlockEntity.progress++;
            consumePower(5, 4);
            if(pBlockEntity.progress > pBlockEntity.requiredProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(HotIsostaticPressTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<HotIsostaticPressRecipe> match = level.getRecipeManager()
                .getRecipeFor(HotIsostaticPressRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            requiredHeat = match.get().getHeat();
            requiredMaxHeat = match.get().getMaxHeat();
            return canInsertAmountIntoOutputSlot(inventory, match.get().getOutputCount())
                    && canInsertItemIntoOutputSlot(inventory, match.get().getBaseOutput())
                    && hasHeat(entity, match.get().getHeat())
                    && !hasHeat(entity, match.get().getMaxHeat()+1);
        }
        else return false;
    }

    private static boolean hasHeat(HotIsostaticPressTile entity, int heat) {
        return entity.getHeat() > heat;
    }

    private static void craftItem(HotIsostaticPressTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<HotIsostaticPressRecipe> match = level.getRecipeManager()
                .getRecipeFor(HotIsostaticPressRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {

            entity.removeInput(2, match.get().getInput().count());

            entity.addOutput(match.get().getBaseOutput(), 3);

            requiredHeat = -1;
            requiredMaxHeat = -1;
            setChanged(entity.level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    private void createHeat() {
        if (burnTime > 0) {
            int upgrade = this.itemHandler.getStackInSlot(6).getCount();
            this.burnTime = this.burnTime - 1 - upgrade;
            if (this.burnTime < 0) this.burnTime = 0;
            if (heat < maxHeat) {
                this.heat = this.heat + upgrade;
                if (this.heat > maxHeat) this.heat = maxHeat;
            }
        }
    }
    private void createSoulHeat() {
        if (soulBurnTime > 0) {
            int upgrade = this.itemHandler.getStackInSlot(6).getCount() + 1;
            this.soulBurnTime = this.soulBurnTime - upgrade;
            if (this.soulBurnTime < 0) this.soulBurnTime = 0;
            if (heat < maxSoulHeat) {
                this.heat = this.heat + upgrade;
                if (this.heat > maxSoulHeat) this.heat = maxSoulHeat;
            }
        }
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int count) {
        return inventory.getItem(3).getMaxStackSize() >= inventory.getItem(3).getCount() + count;
    }
}

