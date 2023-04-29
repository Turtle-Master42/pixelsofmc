// --- MEKANISM ---
package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;

import java.util.function.Supplier;

public class PacketSyncMainPosToClient {

    private final BlockPos mainPos;
    private final BlockPos pos;

    public PacketSyncMainPosToClient(BlockPos mainPos, BlockPos pos) {
        this.mainPos = mainPos;
        this.pos = pos;
    }

    public PacketSyncMainPosToClient(FriendlyByteBuf buf) {
        this.mainPos = buf.readBlockPos();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(mainPos);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IDummyMachineTile dummyMachineTile) {
                //set the mainPos on the client
                dummyMachineTile.setClientMainPos(mainPos);
            }
        });
        return true;
    }
}
