package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyItemProvider;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class InfinitePowerCellItem extends Item {
    public final InfiniteNumber maxPower;
    public final int color;
    public final ChatFormatting style;
    public InfinitePowerCellItem(Properties properties, InfiniteNumber maxPower, int color, ChatFormatting style) {
        super(properties.stacksTo(1));
        this.maxPower = maxPower;
        this.color = color;
        this.style = style;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new InfinitePixelEnergyItemProvider(itemStack, maxPower, maxPower.toInt());
    }

    public int getBarColor(@NotNull ItemStack itemStack) {
        return this.color;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) itemStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return Math.round(13.0F / 100f * new InfiniteNumber().getCrudePercentage(energy.getInfiniteCapacity(), energy.getInfiniteEnergy()));
    }

    //TODO: remove ones completed
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (!level.isClientSide()) {
            InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) player.getItemInHand(usedHand).getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (player.isCrouching()) {
                energy.addEnergy(new InfiniteNumber().fromString("1000000000000000000000000000000000000"));
//                energy.receiveEnergy(maxPower / 100 * 5, false);
            } else {
                energy.extractEnergy(1000000000, false);
            }
//            PixelsOfMc.LOGGER.info(infiniteNumber.toString());
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) itemStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_shift", Util.formatNumber(energy.getInfiniteEnergy()), Util.formatNumber(energy.getInfiniteCapacity())).withStyle(style));
        } else {
            String[] compactString = Util.compactMetricNumber(energy.getInfiniteEnergy());
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_altern", Util.formatNumber(compactString[0]), new InfiniteNumber().getCrudePercentage(energy.getInfiniteCapacity(), energy.getInfiniteEnergy()) + "%", "§7"+compactString[1]+"FE").withStyle(style));
        }
    }
}
