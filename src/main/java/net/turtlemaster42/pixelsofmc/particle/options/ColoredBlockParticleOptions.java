package net.turtlemaster42.pixelsofmc.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredBlockParticleOptions implements ParticleOptions {
    public static final ParticleOptions.Deserializer<ColoredBlockParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<ColoredBlockParticleOptions>() {
        public ColoredBlockParticleOptions fromCommand(ParticleType<ColoredBlockParticleOptions> pParticleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            return new ColoredBlockParticleOptions(pParticleType, BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), stringReader, false).blockState());
        }

        public ColoredBlockParticleOptions fromNetwork(ParticleType<ColoredBlockParticleOptions> pParticleType, FriendlyByteBuf buff) {
            return new ColoredBlockParticleOptions(pParticleType, buff.readById(Block.BLOCK_STATE_REGISTRY));
        }
    };
    private final ParticleType<ColoredBlockParticleOptions> type;
    private final BlockState state;

    public static Codec<ColoredBlockParticleOptions> codec(ParticleType<ColoredBlockParticleOptions> pType) {
        return BlockState.CODEC.xmap((blockState) -> {
            return new ColoredBlockParticleOptions(pType, blockState);
        }, (particleOptions) -> {
            return particleOptions.getState();
        });
    }

   public ColoredBlockParticleOptions(ParticleType<ColoredBlockParticleOptions> pType, BlockState pState) {
        this.type = pType;
        this.state = pState;
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeId(Block.BLOCK_STATE_REGISTRY, this.state);
    }

    public String writeToString() {
        return BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()) + " " + BlockStateParser.serialize(this.state);
    }

    public ParticleType<ColoredBlockParticleOptions> getType() {
        return this.type;
    }

    public BlockState getState() {
        return this.state;
    }

    //FORGE: Add a source pos property, so we can provide models with additional model data
    private net.minecraft.core.BlockPos pos;
    public ColoredBlockParticleOptions setPos(net.minecraft.core.BlockPos pos) {
        this.pos = pos;
        return this;
    }

    public net.minecraft.core.BlockPos getPos() {
        return pos;
    }
}
