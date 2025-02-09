package net.turtlemaster42.pixelsofmc.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;

import java.util.function.Function;

public class POMparticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK = PARTICLE_TYPES.register("electric_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<ColoredBlockParticleOptions>> COLORED_BLOCK = register("colored_block", false, ColoredBlockParticleOptions.DESERIALIZER, ColoredBlockParticleOptions::codec);


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String pKey, boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final Function<ParticleType<T>, Codec<T>> pCodecFactory) {
        return PARTICLE_TYPES.register(pKey, () -> new ParticleType<T>(pOverrideLimiter, pDeserializer) {
            public Codec<T> codec() {
                return pCodecFactory.apply(this);
            }
        });
    }
}
