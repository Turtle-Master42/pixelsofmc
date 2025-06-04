package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.tile.FluidPortTile;

import java.util.function.Supplier;

public class PacketSyncCurrentTankToClient {
    private final String tank;
    private final BlockPos pos;

    public PacketSyncCurrentTankToClient(String tank, BlockPos pos) {
        this.tank = tank;
        this.pos = pos;
    }

    public PacketSyncCurrentTankToClient(FriendlyByteBuf buf) {
        this.tank = buf.readUtf();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(tank);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof FluidPortTile blockEntity) {
                blockEntity.setCurrentTank(tank);
            }
        });
        return true;
    }
}