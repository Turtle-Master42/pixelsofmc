package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.tile.SDSFusionControllerTile;
import net.turtlemaster42.pixelsofmc.gui.menu.SDSFusionControllerMenu;

import java.util.function.Supplier;

public class PacketSyncFusionPowerToClient {
    private final long fusionPower;
    private final BlockPos pos;

    public PacketSyncFusionPowerToClient(long fusionPower, BlockPos pos) {
        this.fusionPower = fusionPower;
        this.pos = pos;
    }

    public PacketSyncFusionPowerToClient(FriendlyByteBuf buf) {
        this.fusionPower = buf.readLong();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(fusionPower);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof SDSFusionControllerTile blockEntity) {
                blockEntity.setFusionPower(fusionPower);

                if(Minecraft.getInstance().player.containerMenu instanceof SDSFusionControllerMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setFusionPower(fusionPower);
                }
            }
        });
        return true;
    }
}