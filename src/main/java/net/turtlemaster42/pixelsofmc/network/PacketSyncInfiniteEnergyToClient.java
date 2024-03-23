package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.IInfiniteEnergyMenu;
import net.turtlemaster42.pixelsofmc.util.InfiniteNumber;
import net.turtlemaster42.pixelsofmc.util.block.IInfiniteEnergyHandlingTile;

import java.util.function.Supplier;

public class PacketSyncInfiniteEnergyToClient {
    private final InfiniteNumber energy;
    private final BlockPos pos;

    public PacketSyncInfiniteEnergyToClient(InfiniteNumber energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public PacketSyncInfiniteEnergyToClient(FriendlyByteBuf buf) {
        this.energy = new InfiniteNumber().fromString(buf.readUtf());
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(energy.toString());
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IInfiniteEnergyHandlingTile blockEntity) {
                blockEntity.setEnergyLevel(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof IInfiniteEnergyMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}