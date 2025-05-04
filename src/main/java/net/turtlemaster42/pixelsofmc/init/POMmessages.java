package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.server.level.ServerPlayer;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.turtlemaster42.pixelsofmc.network.packets.*;

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

        // --CLIENT--
        net.messageBuilder(PacketSyncEnergyToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncEnergyToClient::new)
                .encoder(PacketSyncEnergyToClient::toBytes)
                .consumerMainThread(PacketSyncEnergyToClient::handle)
                .add();

        net.messageBuilder(PacketSyncInfiniteEnergyToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncInfiniteEnergyToClient::new)
                .encoder(PacketSyncInfiniteEnergyToClient::toBytes)
                .consumerMainThread(PacketSyncInfiniteEnergyToClient::handle)
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

        net.messageBuilder(PacketSyncTriFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncTriFluidToClient::new)
                .encoder(PacketSyncTriFluidToClient::toBytes)
                .consumerMainThread(PacketSyncTriFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncQuadFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncQuadFluidToClient::new)
                .encoder(PacketSyncQuadFluidToClient::toBytes)
                .consumerMainThread(PacketSyncQuadFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncQuinFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncQuinFluidToClient::new)
                .encoder(PacketSyncQuinFluidToClient::toBytes)
                .consumerMainThread(PacketSyncQuinFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncHexaFluidToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncHexaFluidToClient::new)
                .encoder(PacketSyncHexaFluidToClient::toBytes)
                .consumerMainThread(PacketSyncHexaFluidToClient::handle)
                .add();

        net.messageBuilder(PacketSyncMainPosToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncMainPosToClient::new)
                .encoder(PacketSyncMainPosToClient::toBytes)
                .consumerMainThread(PacketSyncMainPosToClient::handle)
                .add();

        net.messageBuilder(PacketSyncSwitchToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncSwitchToClient::new)
                .encoder(PacketSyncSwitchToClient::toBytes)
                .consumerMainThread(PacketSyncSwitchToClient::handle)
                .add();


        // --SERVER--
        net.messageBuilder(PacketSyncSlotMaxToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSyncSlotMaxToServer::new)
                .encoder(PacketSyncSlotMaxToServer::toBytes)
                .consumerMainThread(PacketSyncSlotMaxToServer::handle)
                .add();

        net.messageBuilder(PacketSyncLockedSlotToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSyncLockedSlotToServer::new)
                .encoder(PacketSyncLockedSlotToServer::toBytes)
                .consumerMainThread(PacketSyncLockedSlotToServer::handle)
                .add();

        net.messageBuilder(PacketSyncSwitchToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSyncSwitchToServer::new)
                .encoder(PacketSyncSwitchToServer::toBytes)
                .consumerMainThread(PacketSyncSwitchToServer::handle)
                .add();

        net.messageBuilder(PacketSyncTemperatureSwitchToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSyncTemperatureSwitchToServer::new)
                .encoder(PacketSyncTemperatureSwitchToServer::toBytes)
                .consumerMainThread(PacketSyncTemperatureSwitchToServer::handle)
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
