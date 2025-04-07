package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FuelCellItem extends Item {

    private final int energyPerTick;
    private final int color;

    public FuelCellItem(Properties pProperties, int energyPerTick, int color) { // 80_000 ticks active (1.11 hour)
        super(pProperties);
        this.energyPerTick = energyPerTick;
        this.color = color;
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round((getMaxTime() - getTime(stack)) * 13f / getMaxTime());
    }

    public int getBarColor(@NotNull ItemStack itemStack) {
        return this.color;
    }

    public int getEnergyPerTick(ItemStack stack) {
        if (isDepleted(stack)) {
            return (int) (energyPerTick * 0.04f);
        } else {
            return energyPerTick;
        }
    }

    public int getTime(ItemStack stack) {
        return stack.getOrCreateTag().getInt("time");
    }

    public void setTime(ItemStack stack, int time) {
        stack.getOrCreateTag().putInt("time", Math.min(time, getMaxTime()));
    }

    public int getMaxTime() {
        return 80000;
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
        tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.fuel_cell", (int) ((float) getTime(itemStack) / (float) getMaxTime() * 100) + "%").withStyle(ChatFormatting.GREEN));
    }

}
