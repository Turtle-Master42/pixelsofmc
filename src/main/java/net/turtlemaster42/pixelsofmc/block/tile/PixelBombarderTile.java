package net.turtlemaster42.pixelsofmc.block.tile;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelBombarderMenu;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.packets.PacketSyncEnergyToClient;
import net.turtlemaster42.pixelsofmc.recipe.machines.LaserSourceRecipe;
import net.turtlemaster42.pixelsofmc.recipe.machines.PixelBombarderRecipe;
import net.turtlemaster42.pixelsofmc.util.recipe.ChanceIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Optional;

public class PixelBombarderTile extends AbstractMachineTile<PixelBombarderTile> {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 300;
    private int speedUpgrade = 0;
    private final int capacity = 1024000;
    private final int maxReceive = 1024000;
    private static final int energyConsumption = 256;
    private int smallLaserColor = Color.WHITE.getRGB();
    private int bigLaserColor = Color.WHITE.getRGB();
    private int laserType = 0;
    private int laserSize = 2;

    public final PixelEnergyStorage energyStorage = createEnergyStorage();

    @NotNull
    public PixelEnergyStorage createEnergyStorage() {
        return new PixelEnergyStorage(capacity, maxReceive) {
            @Override
            public void onEnergyChanged() {
                POMmessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
                setChanged();
            }
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                onEnergyChanged();
                setChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }
        };
    }
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    public PixelBombarderTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.PIXEL_BOMBARDER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> PixelBombarderTile.this.progress;
                    case 1 -> PixelBombarderTile.this.maxProgress;
                    case 2 -> PixelBombarderTile.this.speedUpgrade;
                    case 3 -> PixelBombarderTile.this.capacity;
                    case 4 -> PixelBombarderTile.this.maxReceive;
                    case 5 -> PixelBombarderTile.this.energyStorage.getEnergyStored();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PixelBombarderTile.this.progress = value;
                    case 1 -> PixelBombarderTile.this.maxProgress = value;
                    case 2 -> PixelBombarderTile.this.speedUpgrade = value;
                }
            }
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    protected boolean isInputValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 4) return true;
        else if (slot == 5) return stack.is(POMtags.Items.SPEED_UPGRADE);
        else if (slot == 6) return stack.is(POMtags.Items.ENERGY_UPGRADE);
        return false;
    }
    @Override
    protected boolean isSlotValidOutput(int slot) {
        return slot == 4;
    }
    @Override
    protected int itemHandlerSize() {return 7;}
    protected void contentsChanged(int slot) {
        if (slot==5) {
            speedUpgradeCheck();
        }
        else if (slot == 1 || slot == 2 || slot == 3) {
            calculateLaserType();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.pixelsofmc.pixel_bombarder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new PixelBombarderMenu(pContainerId, pInventory, this, this.data);
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
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("speedUpgrade", speedUpgrade);
        tag.putInt("powerCapacity", capacity);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        speedUpgrade = nbt.getInt("speedUpgrade");
        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    //---RECIPE---//

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, PixelBombarderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, PixelBombarderTile e) {
        e.tick(level, blockPos, blockState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, PixelBombarderTile pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            int speedAmount = pBlockEntity.itemHandler.getStackInSlot(5).getCount();
            pBlockEntity.progress++;
//            pBlockEntity.energyStorage.consumeEnergy(energyConsumption + (speedAmount * energyConsumption) - (pBlockEntity.energyUpgrade() * speedAmount));

            if(pBlockEntity.progress > pBlockEntity.maxProgress - pBlockEntity.speedUpgrade) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(PixelBombarderTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelBombarderRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelBombarderRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertItemIntoOutputSlot(inventory, match.get().getOutput()) && match.get().getColor() == entity.bigLaserColor;
    }

    private static boolean hasPower(PixelBombarderTile entity) {
        int speedAmount = entity.itemHandler.getStackInSlot(5).getCount();
        return entity.energyStorage.getEnergyStored() >= (energyConsumption + (speedAmount * energyConsumption) - (entity.energyUpgrade() * speedAmount));
    }


    private static void craftItem(PixelBombarderTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PixelBombarderRecipe> match = level.getRecipeManager()
                .getRecipeFor(PixelBombarderRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent() && !level.isClientSide) {
            ChanceIngredient output = match.get().getOutput();

            entity.removeInput(0, match.get().getInput().count());
            entity.addChanceOutput(output, 4);

            setChanged(level, entity.worldPosition, entity.getBlockState());
            entity.resetProgress();
            entity.errorEnergyReset();
        }
    }

    public void calculateLaserType() {
        laserSize = 2;
        Color lastColor = Color.WHITE;
        smallLaserColor = Color.WHITE.getRGB();
        for (int i = 1; i < 3; i++) {
            ItemStack stack = this.itemHandler.getStackInSlot(i);
            // color
            if (stack.is(Tags.Items.GLASS_RED)) {
                lastColor = mergeColors(lastColor, Color.RED);
            } else if (stack.is(Tags.Items.GLASS_ORANGE) || stack.is(Tags.Items.GLASS_BROWN)) {
                lastColor = mergeColors(lastColor, new Color(255, 150, 0));
            } else if (stack.is(Tags.Items.GLASS_YELLOW)) {
                lastColor = mergeColors(lastColor, Color.YELLOW);
            } else if (stack.is(Tags.Items.GLASS_LIME) || stack.is(Tags.Items.GLASS_GREEN)) {
                lastColor = mergeColors(lastColor, Color.GREEN);
            } else if (stack.is(Tags.Items.GLASS_LIGHT_BLUE) || stack.is(Tags.Items.GLASS_CYAN)) {
                lastColor = mergeColors(lastColor, Color.CYAN);
            } else if (stack.is(Tags.Items.GLASS_BLUE)) {
                lastColor = mergeColors(lastColor, Color.BLUE);
            } else if (stack.is(Tags.Items.GLASS_PURPLE)) {
                lastColor = mergeColors(lastColor, new Color(175, 0, 255));
            } else if (stack.is(Tags.Items.GLASS_MAGENTA)) {
                lastColor = mergeColors(lastColor, Color.MAGENTA);
            }

            //size
            if (stack.is(POMtags.Items.OPAQUE_GLASS)) { //tinted glass
                laserSize -= 2;
            } else if (stack.is(POMtags.Items.DARK_GLASS)) { //glass like black, brown or cyan
                laserSize -= 1;
            }
        }
        smallLaserColor = lastColor.getRGB();


        if (itemHandler.getStackInSlot(3).isEmpty()) {
            bigLaserColor = smallLaserColor;
            laserType = 0;
        } else {
            SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inventory.setItem(i, itemHandler.getStackInSlot(i));
            }
            Optional<LaserSourceRecipe> match = level.getRecipeManager()
                    .getRecipeFor(LaserSourceRecipe.Type.INSTANCE, inventory, level);
            if (match.isPresent() && laserSize > 0) {
                LaserSourceRecipe recipe = match.get();
                if (smallLaserColor == recipe.getInputColor()) {
                    bigLaserColor = recipe.getOutputColor();
                    laserType = recipe.getOutputType();
                } else {
                    bigLaserColor = 0;
                }
            }
        }
    }

    public static Color mergeColors(Color color1, Color color2) {
        if (color1.equals(Color.WHITE)) return color2;
        if (color2.equals(Color.WHITE)) return color1;
        if (color1.equals(color2)) return color1;

        int r = (color1.getRed() + color2.getRed()) / 2;
        int g = (color1.getGreen() + color2.getGreen()) / 2;
        int b = (color1.getBlue() + color2.getBlue()) / 2;

        int max = Math.max(r, Math.max(g, b));
        float f = 255f / max; //make the color more vibrant
        return new Color((int) (r * f), (int) (g * f), (int) (b * f));
    }

    private void resetProgress() {this.progress = 0;}

    private void speedUpgradeCheck() {
        this.speedUpgrade = this.maxProgress - speedUpgrade();
    }

    private int energyUpgrade() {
        return Math.round(energyConsumption / (1 + 0.125f * (this.itemHandler.getStackInSlot(6).getCount() - this.itemHandler.getStackInSlot(5).getCount())));
    }

    private int speedUpgrade() {
        return Math.round(this.maxProgress / (1 + 0.125f * this.itemHandler.getStackInSlot(5).getCount()));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ChanceIngredient output) {
        return (inventory.getItem(4).getItem() == output.asItem() && inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + output.count())
                || inventory.getItem(4).isEmpty();
    }

    public int getSmallLaserColor() {
        return smallLaserColor;
    }

    public int getBigLaserColor() {
        return bigLaserColor;
    }

    public int getLaserType() {
        return laserType;
    }

    public int getLaserSize() {
        return laserSize;
    }

    //---ENERGY---//


    private void errorEnergyReset() {
        if (energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored() || energyStorage.getEnergyStored() < 0) {
            PixelsOfMc.LOGGER.error("Energy {} is higher than max {}",energyStorage.getEnergyStored() ,energyStorage.getMaxEnergyStored());
            energyStorage.setEnergy(0);
            PixelsOfMc.LOGGER.error("Stored energy of block at {} was outside limits, energy reverted to 0", this.getBlockPos());
        }
    }

    @Override
    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public PixelEnergyStorage getEnergyStorage() { return energyStorage; }

}


