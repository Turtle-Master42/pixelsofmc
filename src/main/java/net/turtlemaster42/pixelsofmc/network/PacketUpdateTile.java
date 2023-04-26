// --- MEKANISM ---
package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;

import java.util.function.Supplier;

public class PacketUpdateTile {

    private final BlockPos mainPos;
    private final BlockPos pos;

    public PacketUpdateTile(BlockPos mainPos, BlockPos pos) {
        this.mainPos = mainPos;
        this.pos = pos;
    }

    public PacketUpdateTile(FriendlyByteBuf buf) {
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
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IDummyMachineTile blockEntity) {
                PixelsOfMc.LOGGER.info("   YEEEEEEEESSSSSSSSSSSSS!!!");
                PixelsOfMc.LOGGER.info("Tile: {}", blockEntity);
            } else {
                PixelsOfMc.LOGGER.info("   NOOOOOOOOOOOO!!!");
                PixelsOfMc.LOGGER.info("Tile: {}", Minecraft.getInstance().level.getBlockEntity(pos));
                PixelsOfMc.LOGGER.info("BlockState below: {}", Minecraft.getInstance().level.getBlockState(pos.below()));
                PixelsOfMc.LOGGER.info("Tile below: {}", Minecraft.getInstance().level.getBlockEntity(pos.below()));
                PixelsOfMc.LOGGER.info("mainTile: {}", Minecraft.getInstance().level.getBlockEntity(mainPos));
                PixelsOfMc.LOGGER.info("mainBlockState: {}", Minecraft.getInstance().level.getBlockState(mainPos));
            }
        });
        return true;
    }
}
