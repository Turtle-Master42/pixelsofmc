package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.server.level.ServerPlayer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.network.PacketSyncDuoFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncEnergyToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.turtlemaster42.pixelsofmc.network.PacketSyncFluidToClient;
import net.turtlemaster42.pixelsofmc.network.PacketSyncItemStackToClient;

public class POMmessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

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
                .consumer(PacketSyncEnergyToClient::handle)
                .add();

        net.messageBuilder(PacketSyncItemStackToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncItemStackToClient::new)
                .encoder(PacketSyncItemStackToClient::toBytes)
                .consumer(PacketSyncItemStackToClient::handle)
                .add();

        net.messageBuilder(PacketSyncFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncFluidToClient::new)
                .encoder(PacketSyncFluidToClient::toBytes)
                .consumer(PacketSyncFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncDuoFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncDuoFluidToClient::new)
                .encoder(PacketSyncDuoFluidToClient::toBytes)
                .consumer(PacketSyncDuoFluidToClient::handle)
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
}
