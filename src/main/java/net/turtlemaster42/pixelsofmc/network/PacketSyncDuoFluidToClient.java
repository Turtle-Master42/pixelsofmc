package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.IDuoFluidMenu;
import net.turtlemaster42.pixelsofmc.util.block.IDuoFluidHandlingTile;

import java.util.function.Supplier;

public class PacketSyncDuoFluidToClient {
    private final FluidStack fluid;
    private final BlockPos pos;

    public PacketSyncDuoFluidToClient(FluidStack fluid, BlockPos pos) {
        this.fluid = fluid;
        this.pos = pos;
    }

    public PacketSyncDuoFluidToClient(FriendlyByteBuf buf) {
        this.fluid = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluid);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IDuoFluidHandlingTile blockEntity) {
                blockEntity.setDuoFluid(this.fluid);

                if(Minecraft.getInstance().player.containerMenu instanceof IDuoFluidMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setDuoFluid(fluid);
                }
            }
        });
        return true;
    }
}