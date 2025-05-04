package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.tile.ChemicalMixerTile;

import java.util.function.Supplier;

public class PacketSyncTemperatureSwitchToServer {
    private final int state;
    private final BlockPos pos;

    public PacketSyncTemperatureSwitchToServer(BlockPos pos, int state) {
        this.pos = pos;
        this.state = state;
    }

    public PacketSyncTemperatureSwitchToServer(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.state = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(state);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = (ServerLevel) player.level();

            if (level.getBlockEntity(pos) instanceof ChemicalMixerTile tile) {
                tile.setTemperatureState(state);
            }
        });
        return true;
    }
}