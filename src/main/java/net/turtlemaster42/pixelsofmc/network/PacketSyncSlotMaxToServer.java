package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;

import java.util.function.Supplier;

public class PacketSyncSlotMaxToServer {
    private final int max;
    private final BlockPos pos;

    public PacketSyncSlotMaxToServer(BlockPos pos, int max) {
        this.max = max;
        this.pos = pos;
    }

    public PacketSyncSlotMaxToServer(FriendlyByteBuf buf) {
        this.max = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(max);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getBlockEntity(pos) instanceof SDSFusionControllerTile tile) {
                tile.setSlotLimit(max);
            }
        });
        return true;
    }
}