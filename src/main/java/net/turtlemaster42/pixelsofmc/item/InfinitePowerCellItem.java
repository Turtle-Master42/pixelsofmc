package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyItemProvider;
import net.turtlemaster42.pixelsofmc.network.InfinitePixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class InfinitePowerCellItem extends Item {
    public final InfiniteNumber maxPower;
    public final int color;
    public final ChatFormatting style;
    public InfinitePowerCellItem(Properties pProperties, InfiniteNumber maxPower, int color, ChatFormatting style) {
        super(pProperties.stacksTo(1));
        this.maxPower = maxPower;
        this.color = color;
        this.style = style;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new InfinitePixelEnergyItemProvider(stack, maxPower, maxPower.toInt());
    }

    public int getBarColor(@NotNull ItemStack pStack) {
        return this.color;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return Math.round(13.0F / 100f * new InfiniteNumber().getCrudePercentage(energy.getInfiniteCapacity(), energy.getInfiniteEnergy()));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) pPlayer.getItemInHand(pUsedHand).getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (pPlayer.isCrouching()) {
                energy.addEnergy(new InfiniteNumber().fromString("1000000000000000000000000000000000000"));
//                energy.receiveEnergy(maxPower / 100 * 5, false);
            } else {
                energy.extractEnergy(1000000000, false);
            }
//            PixelsOfMc.LOGGER.info(infiniteNumber.toString());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        InfinitePixelEnergyStorage energy = (InfinitePixelEnergyStorage) pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_shift", energy.getInfiniteEnergy(), energy.getInfiniteCapacity()).withStyle(style));
        } else {
            String energyString = energy.getInfiniteEnergy().toString();

            String compactEnergyName = "FE";

            String newEnergyString = energyString;

            if (energyString.length() <= 3) {
                compactEnergyName = "FE";
            } else if (energyString.length() <= 6) {
                compactEnergyName = "KFE";
                int split = energyString.length() - 3;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 9) {
                compactEnergyName = "MFE";
                int split = energyString.length() - 6;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 12) {
                compactEnergyName = "GFE";
                int split = energyString.length() - 9;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 15) {
                compactEnergyName = "TFE";
                int split = energyString.length() - 12;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 18) {
                compactEnergyName = "PFE";
                int split = energyString.length() - 15;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 21) {
                compactEnergyName = "EFE";
                int split = energyString.length() - 18;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 24) {
                compactEnergyName = "ZFE";
                int split = energyString.length() - 21;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 27) {
                compactEnergyName = "YFE";
                int split = energyString.length() - 24;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else if (energyString.length() <= 30) {
                compactEnergyName = "RFE";
                int split = energyString.length() - 27;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            } else {
                compactEnergyName = "QFE";
                int split = energyString.length() - 30;
                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
            }

//            } else if (energyString.length() <= 33) {
//                compactEnergyName = "QFE";
//                int split = energyString.length() - 30;
//                newEnergyString = energyString.substring(0, split) + "." + energyString.substring(split, split + 2);
//            }

            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_altern", newEnergyString, new InfiniteNumber().getCrudePercentage(energy.getInfiniteCapacity(), energy.getInfiniteEnergy()) + "%", compactEnergyName).withStyle(style));
        }
    }
}
