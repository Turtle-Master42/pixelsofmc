// --- MEKANISM ---
package net.turtlemaster42.pixelsofmc.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
public interface IPOMpacket {

    boolean handle(Supplier<NetworkEvent.Context> contextSupplier);

    void encode(FriendlyByteBuf buffer);

    static <PACKET extends IPOMpacket> void handle(PACKET message, Supplier<NetworkEvent.Context> ctx) {
        if (message != null) {
            //Message should never be null unless something went horribly wrong decoding.
            // In which case we don't want to try enqueuing handling it, or set the packet as handled
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> message.handle(ctx));
            context.setPacketHandled(true);
        }
    }
}
