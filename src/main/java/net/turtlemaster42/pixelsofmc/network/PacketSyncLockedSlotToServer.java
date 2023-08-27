package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;

import java.util.function.Supplier;

public class PacketSyncLockedSlotToServer {
    private final boolean locked;
    private final int slot;
    private final BlockPos pos;

    public PacketSyncLockedSlotToServer(BlockPos pos, boolean locked, int slot) {
        this.locked = locked;
        this.pos = pos;
        this.slot = slot;
    }

    public PacketSyncLockedSlotToServer(FriendlyByteBuf buf) {
        this.locked = buf.readBoolean();
        this.pos = buf.readBlockPos();
        this.slot = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(locked);
        buf.writeBlockPos(pos);
        buf.writeInt(slot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getBlockEntity(pos) instanceof SDSFusionControllerTile tile) {
                tile.setSlotLock(locked, slot);
            }
        });
        return true;
    }
}