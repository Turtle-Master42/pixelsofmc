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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class PowerCellItem extends Item  {
    public final int maxPower;
    public final int color;
    public final ChatFormatting style;
    public PowerCellItem(Properties pProperties, int maxPower, int color, ChatFormatting style) {
        super(pProperties.stacksTo(1));
        this.maxPower = maxPower;
        this.color = color;
        this.style = style;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new PixelEnergyItemProvider(stack, maxPower, maxPower);
    }

    public int getBarWidth(ItemStack pStack) {
        IEnergyStorage energy = pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return Math.round((float)energy.getEnergyStored() * 13.0F / (float)energy.getMaxEnergyStored());
    }

    public int getBarColor(ItemStack pStack) {
        return this.color;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        IEnergyStorage energy = pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return energy.getEnergyStored() < energy.getMaxEnergyStored() && energy.getEnergyStored() > 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            PixelEnergyStorage energy = (PixelEnergyStorage) pPlayer.getItemInHand(pUsedHand).getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (pPlayer.isCrouching()) {
                energy.receiveEnergy(maxPower / 100 * 5, false);
            } else {
                energy.receiveEnergy(100000, false);
            }
//            PixelsOfMc.LOGGER.info(infiniteNumber.toString());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }


    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, @NotNull List<net.minecraft.network.chat.Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        IEnergyStorage energy = pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_shift", energy.getEnergyStored(), energy.getMaxEnergyStored()).withStyle(style));
        } else {

            String energyString = String.valueOf(energy.getEnergyStored());

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
            }

            pTooltipComponents.add(Component.translatable("tooltip.pixelsofmc.stored_power_altern", newEnergyString, Math.round((float) 100 / energy.getMaxEnergyStored() * energy.getEnergyStored()) + "%", compactEnergyName).withStyle(style));
        }
    }
}
