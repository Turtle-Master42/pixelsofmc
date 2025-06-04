package net.turtlemaster42.pixelsofmc.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FluidBubbleParticleOptions implements ParticleOptions {
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_FLUID =
            new DynamicCommandExceptionType((fluid) -> Component.translatable("argument.fluid.id.invalid", fluid));

    public static final Deserializer<FluidBubbleParticleOptions> DESERIALIZER = new Deserializer<>() {
        public @NotNull FluidBubbleParticleOptions fromCommand(@NotNull ParticleType<FluidBubbleParticleOptions> pParticleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            return new FluidBubbleParticleOptions(pParticleType, parseForFluid(BuiltInRegistries.FLUID.asLookup(), stringReader));
        }

        public @NotNull FluidBubbleParticleOptions fromNetwork(@NotNull ParticleType<FluidBubbleParticleOptions> pParticleType, FriendlyByteBuf buff) {
            return new FluidBubbleParticleOptions(pParticleType, Objects.requireNonNull(buff.readById(Fluid.FLUID_STATE_REGISTRY)));
        }
    };
    private final ParticleType<FluidBubbleParticleOptions> type;
    private final Fluid fluid;

    public static Codec<FluidBubbleParticleOptions> codec(ParticleType<FluidBubbleParticleOptions> pType) {
        return FluidState.CODEC.xmap(
                (fluidState) -> new FluidBubbleParticleOptions(pType, fluidState), FluidBubbleParticleOptions::getFluidState
        );
    }

   public FluidBubbleParticleOptions(ParticleType<FluidBubbleParticleOptions> pType, FluidState fluidState) {
        this.type = pType;
        this.fluid = fluidState.getType();
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeId(Fluid.FLUID_STATE_REGISTRY, this.fluid.defaultFluidState());
    }

    public @NotNull String writeToString() {
        return BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()) + " " + this.fluid;
    }

    public @NotNull ParticleType<FluidBubbleParticleOptions> getType() {
        return this.type;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    public FluidState getFluidState() {
        return this.fluid.defaultFluidState();
    }


    //FORGE: Add a source pos property, so we can provide models with additional model data
    private net.minecraft.core.BlockPos pos;
    public FluidBubbleParticleOptions setPos(net.minecraft.core.BlockPos pos) {
        this.pos = pos;
        return this;
    }

    public net.minecraft.core.BlockPos getPos() {
        return pos;
    }

    public static FluidState parseForFluid(HolderLookup<Fluid> pLookup, StringReader pReader) throws CommandSyntaxException {
        int i = pReader.getCursor();

        try {
            ResourceLocation id = ResourceLocation.read(pReader);
            return pLookup.get(ResourceKey.create(Registries.FLUID, id)).orElseThrow(() -> {
                pReader.setCursor(i);
                return ERROR_UNKNOWN_FLUID.createWithContext(pReader, id.toString());
            }).value().defaultFluidState();
        } catch (CommandSyntaxException commandsyntaxexception) {
            pReader.setCursor(i);
            throw commandsyntaxexception;
        }
    }
}
