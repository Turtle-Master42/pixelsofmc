package net.turtlemaster42.pixelsofmc.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.IHexaFluidMenu;
import net.turtlemaster42.pixelsofmc.util.block.IHexaFluidHandlingTile;

import java.util.function.Supplier;

public class PacketSyncHexaFluidToClient {
    private final FluidStack fluid;
    private final BlockPos pos;

    public PacketSyncHexaFluidToClient(FluidStack fluid, BlockPos pos) {
        this.fluid = fluid;
        this.pos = pos;
    }

    public PacketSyncHexaFluidToClient(FriendlyByteBuf buf) {
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
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IHexaFluidHandlingTile fluidHandlingTile) {
                fluidHandlingTile.setHexaFluid(this.fluid);

                if(Minecraft.getInstance().player.containerMenu instanceof IHexaFluidMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setHexaFluid(fluid);
                }
            }
        });
        return true;
    }
}