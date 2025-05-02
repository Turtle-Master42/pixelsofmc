package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FuelCellItem extends Item {

    private final int energyPerTick;
    private final int color;
    private final Item remainder;
    private final int maxTime;

    public FuelCellItem(Properties pProperties, Item remainder, int energyPerTick, int color) { // 80_000 ticks active (1.11 hour)
        super(pProperties);
        this.energyPerTick = energyPerTick;
        this.color = color;
        this.remainder = remainder;
        this.maxTime = 80000;
    }

    public FuelCellItem(Properties pProperties, int energyPerTick) {
        super(pProperties);
        this.energyPerTick = energyPerTick;
        this.color = 0;
        this.remainder = null;
        this.maxTime = -1;
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round((getMaxTime() - getTime(stack)) * 13f / getMaxTime());
    }

    public int getBarColor(@NotNull ItemStack itemStack) {
        return this.color;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    public int getTime(ItemStack stack) {
        return stack.getOrCreateTag().getInt("time");
    }

    public void setTime(ItemStack stack, int time) {
        stack.getOrCreateTag().putInt("time", Math.min(time, getMaxTime()));
    }

    public int getMaxTime() {
        return maxTime;
    }

    public Item getRemainder() {
        return remainder;
    }

    public void deplete(ItemStack stack) {
        setTime(stack, getTime(stack) + 1);
    }

    public boolean hasRemainderStack() {
        return remainder != null;
    }

    public static ItemStack getRemainderStack(ItemStack stack) {
        if (stack.getItem() instanceof FuelCellItem fuelCell) {
            if (fuelCell.hasRemainderStack()) {
                return new ItemStack(fuelCell.getRemainder(), stack.getCount(), stack.getTag());
            }
        }
        return stack;
    }

    public boolean isDepleted(ItemStack stack) {
        return getTime(stack) >= getMaxTime();
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getTime(stack) <= getMaxTime() && getTime(stack) > 0;
    }

    //TODO: remove once completed
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            setTime(player.getItemInHand(usedHand), getTime(player.getItemInHand(usedHand)) + 800);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (hasRemainderStack()) {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.fuel_cell", (int) ((float) getTime(itemStack) / (float) getMaxTime() * 100) + "%").withStyle(ChatFormatting.GREEN));
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.literal("  " + (getMaxTime() - getTime(itemStack))).withStyle(ChatFormatting.RED));
            }
        }
    }

}
