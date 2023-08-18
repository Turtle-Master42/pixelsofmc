package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AbstractMultiBlockTile extends BlockEntity {

    private BlockPos mainPos = worldPosition;
    public AbstractMultiBlockTile(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public void setMainPos(BlockPos pos) {
        if (level != null && !level.isClientSide) {
            this.mainPos = pos;
            setChanged();
        }
    }

    public BlockPos getMainPos() {
        return mainPos;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("mainPos", NbtUtils.writeBlockPos(mainPos));
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        mainPos = NbtUtils.readBlockPos(nbt.getCompound("mainPos"));
    }
}
