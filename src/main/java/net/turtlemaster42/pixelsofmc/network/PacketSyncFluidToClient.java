package net.turtlemaster42.pixelsofmc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.turtlemaster42.pixelsofmc.gui.renderer.IFluidMenu;
import net.turtlemaster42.pixelsofmc.util.block.IFluidHandlingTile;

import java.util.function.Supplier;

public class PacketSyncFluidToClient {
    private final FluidStack fluid;
    private final BlockPos pos;

    public PacketSyncFluidToClient(FluidStack fluid, BlockPos pos) {
        this.fluid = fluid;
        this.pos = pos;
    }

    public PacketSyncFluidToClient(FriendlyByteBuf buf) {
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
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IFluidHandlingTile blockEntity) {
                blockEntity.setFluid(this.fluid);

                if(Minecraft.getInstance().player.containerMenu instanceof IFluidMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(fluid);
                }
            }
        });
        return true;
    }
}