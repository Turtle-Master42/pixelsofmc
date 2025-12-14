package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.menu.AbstractMachineMenu;
import net.turtlemaster42.pixelsofmc.gui.renderer.IEnergyMenu;
import net.turtlemaster42.pixelsofmc.tile.AbstractMachineTile;
import net.turtlemaster42.pixelsofmc.util.block.IEnergyHandlingTile;

import java.util.function.Supplier;

public class PacketSyncProgressToClient {
    private final int progress;
    private final BlockPos pos;

    public PacketSyncProgressToClient(int progress, BlockPos pos) {
        this.progress = progress;
        this.pos = pos;
    }

    public PacketSyncProgressToClient(FriendlyByteBuf buf) {
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof AbstractMachineTile<?> blockEntity) {
                blockEntity.setProgress(progress);

                if(Minecraft.getInstance().player.containerMenu instanceof AbstractMachineMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setProgress(progress);
                }
            }
        });
        return true;
    }
}