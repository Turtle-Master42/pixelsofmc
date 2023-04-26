package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.network.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class POMmessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    private static int index = 0;

    public static void register() {

        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PixelsOfMc.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PacketSyncEnergyToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncEnergyToClient::new)
                .encoder(PacketSyncEnergyToClient::toBytes)
                .consumerMainThread(PacketSyncEnergyToClient::handle)
                .add();

        net.messageBuilder(PacketSyncItemStackToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncItemStackToClient::new)
                .encoder(PacketSyncItemStackToClient::toBytes)
                .consumerMainThread(PacketSyncItemStackToClient::handle)
                .add();

        net.messageBuilder(PacketSyncFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncFluidToClient::new)
                .encoder(PacketSyncFluidToClient::toBytes)
                .consumerMainThread(PacketSyncFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncDuoFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncDuoFluidToClient::new)
                .encoder(PacketSyncDuoFluidToClient::toBytes)
                .consumerMainThread(PacketSyncDuoFluidToClient::handle)
                .add();

        net.messageBuilder(PacketUpdateTile.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketUpdateTile::new)
                .encoder(PacketUpdateTile::toBytes)
                .consumerMainThread(PacketUpdateTile::handle)
                .add();

    }
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

//    // --- MEKANISM ---
//    public static <MSG> void sendToAllTracking(MSG message, BlockEntity tile) {
//        sendToAllTracking(message, tile.getLevel(), tile.getBlockPos());
//    }
//
//    public static <MSG> void sendToAllTracking(MSG message, Level world, BlockPos pos) {
//        if (world instanceof ServerLevel level) {
//            PixelsOfMc.LOGGER.info("sendToAllTracking is ServerLevel");
//            //If we have a ServerWorld just directly figure out the ChunkPos to not require looking up the chunk
//            // This provides a decent performance boost over using the packet distributor
//            level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach(p -> sendTo(message, p));
//        } else {
//            //Otherwise, fallback to entities tracking the chunk if some mod did something odd and our world is not a ServerWorld
//            net.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunk(pos.getX() >> 4, pos.getZ() >> 4)), message);
//        }
//    }
//
//    /**
//     * Send this message to the specified player.
//     *
//     * @param message - the message to send
//     * @param player  - the player to send it to
//     */
//    public static <MSG> void sendTo(MSG message, ServerPlayer player) {
//        //Validate it is not a fake player, even though none of our code should call this with a fake player
//        if (!(player instanceof FakePlayer)) {
//            net.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
//        }
//    }
}
