package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import org.jetbrains.annotations.NotNull;

public class AbstractMultiBlockTile extends BlockEntity {

    private BlockPos mainPos = worldPosition;
    public AbstractMultiBlockTile(BlockPos pPos, BlockState pBlockState) {
        super(POMtiles.MULTIBLOCK.get(), pPos, pBlockState);
    }

    public void setMainPos(BlockPos pos) {
        if (level != null && pos != this.worldPosition && !level.isClientSide) {
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
