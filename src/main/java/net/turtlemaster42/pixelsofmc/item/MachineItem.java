package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyItemProvider;

import javax.annotation.Nullable;
import java.awt.*;

public class MachineItem extends BlockItem {
    public MachineItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new PixelEnergyItemProvider(stack, 0, 0);
    }

    public int getBarWidth(ItemStack pStack) {
        IEnergyStorage energy = pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return Math.round((float)energy.getEnergyStored() * 13.0F / (float)energy.getMaxEnergyStored());
    }

    public int getBarColor(ItemStack pStack) {
        return new Color(0, 205 ,255).getRGB();
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        IEnergyStorage energy = pStack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        return energy.getEnergyStored() != energy.getMaxEnergyStored();
    }
}
