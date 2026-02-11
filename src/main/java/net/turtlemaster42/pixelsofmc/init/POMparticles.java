package net.turtlemaster42.pixelsofmc.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;
import net.turtlemaster42.pixelsofmc.particle.options.FluidBubbleParticleOptions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class POMparticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK = PARTICLE_TYPES.register("electric_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARKLE = PARTICLE_TYPES.register("sparkle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RED_CROSS = PARTICLE_TYPES.register("red_cross", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GREEN_CROSS = PARTICLE_TYPES.register("green_cross", () -> new SimpleParticleType(true));


    public static final RegistryObject<ParticleType<ColoredBlockParticleOptions>> COLORED_BLOCK = register("colored_block", false, ColoredBlockParticleOptions.DESERIALIZER, ColoredBlockParticleOptions::codec);
    public static final RegistryObject<ParticleType<FluidBubbleParticleOptions>> FLUID_BUBBLE_POP = register("fluid_bubble_pop", false, FluidBubbleParticleOptions.DESERIALIZER, FluidBubbleParticleOptions::codec);
    public static final RegistryObject<ParticleType<FluidBubbleParticleOptions>> FLUID_BUBBLE = register("fluid_bubble", false, FluidBubbleParticleOptions.DESERIALIZER, FluidBubbleParticleOptions::codec);


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String pKey, boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final Function<ParticleType<T>, Codec<T>> pCodecFactory) {
        return PARTICLE_TYPES.register(pKey, () -> new ParticleType<>(pOverrideLimiter, pDeserializer) {
            public @NotNull Codec<T> codec() {
                return pCodecFactory.apply(this);
            }
        });
    }
}
