package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class SpongeItem extends Item {

    public SpongeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        HumanoidArm arm = Minecraft.getInstance().options.mainHand().get();
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        int amount = getFluid(stack).getAmount() < 250 ? 0 : getFluid(stack).getAmount()-250;
        setFluid(stack, new FluidStack(getFluid(stack).getFluid(), amount));

        if (!pLevel.isClientSide()) {
            if (pUsedHand.equals(InteractionHand.OFF_HAND) && arm != HumanoidArm.LEFT) {
                ((ServerLevel) pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, getFluid(stack).getRawFluid().defaultFluidState().createLegacyBlock()), pPlayer.getX() + (0.4f * Math.cos(Mth.DEG_TO_RAD * (pPlayer.yBodyRot +40))), pPlayer.getY() + 0.8f, pPlayer.getZ() + (0.4f * Math.sin(Mth.DEG_TO_RAD * (pPlayer.yBodyRot +40))), 8, 0, 0, 0, 0f);
            } else if (arm == HumanoidArm.LEFT && !pUsedHand.equals(InteractionHand.OFF_HAND)) {
                ((ServerLevel) pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, getFluid(stack).getRawFluid().defaultFluidState().createLegacyBlock()), pPlayer.getX() + (0.4f * Math.cos(Mth.DEG_TO_RAD * (pPlayer.yBodyRot +40))), pPlayer.getY() + 0.8f, pPlayer.getZ() + (0.4f * Math.sin(Mth.DEG_TO_RAD * (pPlayer.yBodyRot +40))), 8, 0, 0, 0, 0f);
            } else {
                ((ServerLevel) pLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, getFluid(stack).getRawFluid().defaultFluidState().createLegacyBlock()), pPlayer.getX() + (-0.4f * Math.cos(Mth.DEG_TO_RAD * (pPlayer.yBodyRot))), pPlayer.getY() + 0.8f, pPlayer.getZ() + (-0.4f * Math.sin(Mth.DEG_TO_RAD * (pPlayer.yBodyRot - 40))), 8, 0, 0, 0, 0f);
            }
        }

        if (getFluid(stack).getAmount() == 0)
            setFluid(stack, FluidStack.EMPTY);

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (!getFluid(stack).isEmpty()) {
            tooltip.add(Component.translatable(getFluid(stack).getTranslationKey()).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.literal("Amount: " + getFluid(stack).getAmount()).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.literal("Color: " + getBarColor(stack)).withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return !getFluid(stack).isEmpty();
    }

    public int getBarWidth(ItemStack stack) {
        return Math.min(Math.round(getFluid(stack).getAmount()/1000f*13), 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        Fluid fluid = getFluid(stack).getFluid();
        if (fluid == Fluids.LAVA) return -44273;    //(new Color(255, 83, 15)).getRGB()
        return IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1));
    }

    @Nonnull
    public FluidStack getFluid(ItemStack stack) {
        CompoundTag tagCompound = stack.getTag();
        if (tagCompound == null || !tagCompound.contains("Fluid"))
            return FluidStack.EMPTY;

        return FluidStack.loadFluidStackFromNBT(tagCompound.getCompound("Fluid"));
    }

    protected void setFluid(ItemStack stack, FluidStack fluid) {
        if (!stack.hasTag())
            stack.setTag(new CompoundTag());

        CompoundTag fluidTag = new CompoundTag();
        fluid.writeToNBT(fluidTag);
        stack.getTag().put("Fluid", fluidTag);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, 1000);
    }
}
