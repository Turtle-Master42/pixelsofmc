package net.turtlemaster42.pixelsofmc.block.dummy.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMmessages;
import net.turtlemaster42.pixelsofmc.network.PacketUpdateTile;
import net.turtlemaster42.pixelsofmc.util.block.IDummyMachineTile;
import org.jetbrains.annotations.NotNull;

public class AbstractDummyMachineBlockTile extends BlockEntity implements IDummyMachineTile {

    private BlockPos mainPos = BlockPos.ZERO;

    public AbstractDummyMachineBlockTile(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void setMainPos(BlockPos pos) {
        if (pos != this.worldPosition && level.getBlockEntity(pos) != null) {
            this.mainPos = pos;
            POMmessages.sendToClients(new PacketUpdateTile(pos, worldPosition));
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
}
