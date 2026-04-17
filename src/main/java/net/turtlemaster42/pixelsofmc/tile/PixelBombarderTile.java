package net.turtlemaster42.pixelsofmc.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelBombarderMenu;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
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
    private int sourceColor = Color.WHITE.getRGB();
    private int sourceSize = 2;
    private int laserColor = Color.WHITE.getRGB();
    private int laserType = 0;
    private int laserSize = 2;

    public PixelBombarderTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.PIXEL_BOMBARDER.get(), pWorldPosition, pBlockState, 1024000, 256);
        defineMaxProgress(600);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> PixelBombarderTile.this.progress;
                    case 1 -> PixelBombarderTile.this.maxProgress;
                    case 2 -> PixelBombarderTile.this.requiredProgress;
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
                    case 2 -> PixelBombarderTile.this.requiredProgress = value;
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
        if (slot==5) {speedUpgradeCheck(5);}
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
        calculateLaserType();
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
            pBlockEntity.progress += laserSize;
//            consumePower(6, 5);

            if(pBlockEntity.progress > pBlockEntity.requiredProgress) {
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

        return match.isPresent() &&
                canInsertItemIntoOutputSlot(inventory, match.get().getOutput()) &&
                match.get().getLaserColor() == entity.laserColor &&
                match.get().getLaserType() == entity.laserType;
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
        sourceSize = 2;
        Color lastColor = Color.WHITE;
        sourceColor = Color.WHITE.getRGB();
        for (int i = 1; i <= 2; i++) {
            ItemStack stack = this.itemHandler.getStackInSlot(i);
            // color
            if (stack.is(POMtags.Items.RED_LENS)) {
                lastColor = mergeColors(lastColor, Color.RED);
            } else if (stack.is(POMtags.Items.ORANGE_LENS)) {
                lastColor = mergeColors(lastColor, new Color(255, 130, 0));
            } else if (stack.is(POMtags.Items.YELLOW_LENS)) {
                lastColor = mergeColors(lastColor, Color.YELLOW);
            } else if (stack.is(POMtags.Items.LIME_LENS)) {
                lastColor = mergeColors(lastColor, Color.GREEN);
            } else if (stack.is(POMtags.Items.LIGHT_BLUE_LENS)) {
                lastColor = mergeColors(lastColor, Color.CYAN);
            } else if (stack.is(POMtags.Items.BLUE_LENS)) {
                lastColor = mergeColors(lastColor, Color.BLUE);
            } else if (stack.is(POMtags.Items.PURPLE_LENS)) {
                lastColor = mergeColors(lastColor, new Color(175, 50, 255));
            } else if (stack.is(POMtags.Items.MAGENTA_LENS)) {
                lastColor = mergeColors(lastColor, Color.MAGENTA);
            } else if (stack.is(POMtags.Items.WHITE_LENS)) {
                lastColor = mergeColors(lastColor, new Color(254, 254, 254));
            }

            //size
            if (stack.is(POMtags.Items.OPAQUE_LENS)) { //tinted glass
                sourceSize = -1;
            } else if (stack.is(POMtags.Items.DARK_LENS)) { //glass like black, brown or cyan
                sourceSize -= 1;
            } else if (stack.is(POMtags.Items.FOCUS_LENS)) {
                sourceSize += 1;
            }
        }
        sourceSize = Math.min(sourceSize, 3);
        sourceColor = lastColor.getRGB();


        if (itemHandler.getStackInSlot(3).isEmpty()) {
            laserColor = sourceColor;
            laserSize = sourceSize;
            laserType = 0;
        } else {
            SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inventory.setItem(i, itemHandler.getStackInSlot(i));
            }
            Optional<LaserSourceRecipe> match = level.getRecipeManager()
                    .getRecipeFor(LaserSourceRecipe.Type.INSTANCE, inventory, level);
            if (match.isPresent() && sourceSize >= match.get().getSizeRequirement()) {
                LaserSourceRecipe recipe = match.get();
                if (sourceColor == recipe.getInputColor()) {
                    laserColor = recipe.getOutputColor();
                    laserType = recipe.getOutputType();
                    laserSize = Math.min(sourceSize + match.get().getSizeModifier(), 3);
                } else {
                    laserColor = 0;
                }
            } else {
                laserSize = 0;
                laserType = 0;
                laserColor = 0;
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

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ChanceIngredient output) {
        return (inventory.getItem(4).getItem() == output.asItem() && inventory.getItem(4).getMaxStackSize() >= inventory.getItem(4).getCount() + output.count())
                || inventory.getItem(4).isEmpty();
    }

    public int getSourceColor() {
        return sourceColor;
    }

    public int getSourceSize() {
        return sourceSize;
    }

    public int getLaserColor() {
        return laserColor;
    }

    public int getLaserType() {
        return laserType;
    }

    public int getLaserSize() {
        return laserSize;
    }
}


