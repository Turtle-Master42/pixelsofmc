package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.ITriFluidMenu;
import net.turtlemaster42.pixelsofmc.util.block.ITriFluidHandlingTile;

import java.util.function.Supplier;

public class PacketSyncTriFluidToClient {
    private final FluidStack fluid;
    private final BlockPos pos;

    public PacketSyncTriFluidToClient(FluidStack fluid, BlockPos pos) {
        this.fluid = fluid;
        this.pos = pos;
    }

    public PacketSyncTriFluidToClient(FriendlyByteBuf buf) {
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
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ITriFluidHandlingTile fluidHandlingTile) {
                fluidHandlingTile.setTriFluid(this.fluid);

                if(Minecraft.getInstance().player.containerMenu instanceof ITriFluidMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setTriFluid(fluid);
                }
            }
        });
        return true;
    }
}