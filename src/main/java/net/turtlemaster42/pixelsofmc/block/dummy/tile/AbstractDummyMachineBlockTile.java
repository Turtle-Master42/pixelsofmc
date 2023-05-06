package net.turtlemaster42.pixelsofmc.block.dummy.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketSyncMainPosToClient;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;
import org.jetbrains.annotations.NotNull;

public class AbstractDummyMachineBlockTile extends BlockEntity implements IDummyMachineTile {

    private BlockPos mainPos = BlockPos.ZERO;
    private boolean hasClientMainPos = false;

    public AbstractDummyMachineBlockTile(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void setMainPos(BlockPos pos) {
        if (pos != this.worldPosition && !level.isClientSide) {
            this.mainPos = pos;
            setChanged();
        }
    }

    public void setClientMainPos(BlockPos pos) {
        this.mainPos = pos;
    }

    @Override
    public BlockPos getMainPos() {
        if (mainPos == null) {
            mainPos = BlockPos.ZERO;
        }
        return mainPos;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("mainPos", NbtUtils.writeBlockPos(mainPos));
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        mainPos = NbtUtils.readBlockPos(nbt.getCompound("mainPos"));
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AbstractDummyMachineBlockTile e) {
        e.tick(pLevel, pPos, pState, e);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, AbstractDummyMachineBlockTile pBlockEntity) {
        //first we check if hasClientMainPos is false, we do this so it only tries to send info to the client if
        //it hasn't received it yet. then we get the connection of the client, this is only true if the client is
        //in the actual game, so not still in the "joining game" screen, otherwise the packet doesn't go through.
        //when the packet is send and will be received, we set hasClientMainPos to true so it won't send packets until
        //it needs to.
        //we need to resend the packets every time someone logs in, luckily, hasClientMainPos will automatically revert
        //to its default state of "false" because the information is never fully stored.
        if (!pBlockEntity.hasClientMainPos) {
            if (Minecraft.getInstance().getConnection() != null) {
                POMmessages.sendToClients(new PacketSyncMainPosToClient(pBlockEntity.getMainPos(), pPos));
                pBlockEntity.hasClientMainPos = true;
            }
        }
    }
}
