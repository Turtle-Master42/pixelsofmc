package net.turtlemaster42.pixelsofmc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class GasBucketItem extends BucketItem {
    private final java.util.function.Supplier<? extends Fluid> fluidSupplier;
    public GasBucketItem(Fluid fluid, Properties properties) {
        super(fluid, properties);
        this.fluidSupplier = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getDelegateOrThrow(fluid);
    }

    public GasBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
        this.fluidSupplier = supplier;
    }

    @Override
    public @NotNull Fluid getFluid() { return fluidSupplier.get(); }

    @Override
    public @NotNull ICapabilityProvider initCapabilities(@NotNull ItemStack itemStack, @Nullable CompoundTag nbt) {
        if (this.getClass() == GasBucketItem.class)
            return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(itemStack);
        else
            return super.initCapabilities(itemStack, nbt);
    }


    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack container) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

        for(int l = 0; l < 8; ++l) {
            level.addParticle(ParticleTypes.CLOUD, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
            if (getFluid().getFluidType().getTemperature() > 1000) {
                level.addParticle(ParticleTypes.FLAME, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
        return true;
    }
}
