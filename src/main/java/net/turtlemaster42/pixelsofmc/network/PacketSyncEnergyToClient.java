package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.block.entity.PixelSplitterBlockEntity;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterGuiMenu;

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
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof PixelSplitterBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof PixelSplitterGuiMenu menu &&
                        menu.blockEntity.getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}
