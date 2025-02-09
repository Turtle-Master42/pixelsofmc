package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyItemProvider;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class PowerCellItem extends Item  {
    public final int maxPower;
    public final int color;
    public final ChatFormatting style;
    public PowerCellItem(Properties properties, int maxPower, int color, ChatFormatting style) {
        super(properties.stacksTo(1));
        this.maxPower = maxPower;
        this.color = color;
        this.style = style;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new PixelEnergyItemProvider(itemStack, maxPower, maxPower);
    }

    public int getBarWidth(ItemStack itemStack) {
        IEnergyStorage energy = itemStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return Math.round((float)energy.getEnergyStored() * 13.0F / (float)energy.getMaxEnergyStored());
    }

    public int getBarColor(@NotNull ItemStack itemStack) {
        return this.color;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        IEnergyStorage energy = itemStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return energy.getEnergyStored() < energy.getMaxEnergyStored() && energy.getEnergyStored() > 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            PixelEnergyStorage energy = (PixelEnergyStorage) player.getItemInHand(usedHand).getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (player.isCrouching()) {
                energy.receiveEnergy(maxPower / 100 * 5, false);
            } else {
                energy.receiveEnergy(100000, false);
            }
        }
        return super.use(level, player, usedHand);
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, @NotNull List<net.minecraft.network.chat.Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        IEnergyStorage energy = itemStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_shift", Util.formatNumber(energy.getEnergyStored()), Util.formatNumber(energy.getMaxEnergyStored())).withStyle(style));
        } else {
            String[] compactString = Util.compactMetricNumber(energy.getEnergyStored());
            tooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_altern", compactString[0], Math.round((float) 100 / energy.getMaxEnergyStored() * energy.getEnergyStored()) + "%", "§7"+compactString[1]+"FE").withStyle(style));
        }
    }
}
