package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.IUpdatableWidgets;
import net.turtlemaster42.pixelsofmc.util.block.IButtonTile;

import java.util.function.Supplier;

public class PacketSyncSwitchToClient {
    private final boolean on;
    private final int currentSwitch;
    private final BlockPos pos;

    public PacketSyncSwitchToClient(BlockPos pos, boolean locked, int slot) {
        this.on = locked;
        this.pos = pos;
        this.currentSwitch = slot;
    }

    public PacketSyncSwitchToClient(FriendlyByteBuf buf) {
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
            // HERE WE ARE ON THE CLIENT!
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IButtonTile tile) {
                tile.setSwitch(currentSwitch, on);
                if(Minecraft.getInstance().screen instanceof IUpdatableWidgets screen) {
                    if (screen.getBlockEntity().getBlockPos().equals(pos)) {
                        screen.forceUpdateWidgets();
                    }
                }
            }
        });
        return true;
    }
}