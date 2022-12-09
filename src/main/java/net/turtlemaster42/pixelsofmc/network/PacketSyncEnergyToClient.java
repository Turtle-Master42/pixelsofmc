package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;

import java.util.function.Supplier;

public class PacketSyncEnergyToClient {
    private final int energy;
    private final BlockPos pos;

    public PacketSyncEnergyToClient(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public PacketSyncEnergyToClient(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IEnergyHandlingTile blockEntity) {
                blockEntity.setEnergyLevel(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof IEnergyMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}