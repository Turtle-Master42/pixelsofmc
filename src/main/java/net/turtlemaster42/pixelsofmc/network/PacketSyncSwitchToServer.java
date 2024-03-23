package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;

import java.util.function.Supplier;

public class PacketSyncSwitchToServer {
    private final boolean on;
    private final int currentSwitch;
    private final BlockPos pos;

    public PacketSyncSwitchToServer(BlockPos pos, boolean locked, int slot) {
        this.on = locked;
        this.pos = pos;
        this.currentSwitch = slot;
    }

    public PacketSyncSwitchToServer(FriendlyByteBuf buf) {
        this.on = buf.readBoolean();
        this.pos = buf.readBlockPos();
        this.currentSwitch = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(on);
        buf.writeBlockPos(pos);
        buf.writeInt(currentSwitch);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getBlockEntity(pos) instanceof SDSFusionControllerTile tile) {
                tile.setSwitches(on, currentSwitch);
            }
        });
        return true;
    }
}