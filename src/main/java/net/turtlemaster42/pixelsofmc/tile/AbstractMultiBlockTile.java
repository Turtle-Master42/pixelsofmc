package net.turtlemaster42.pixelsofmc.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

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

    public void setClientMainPos(BlockPos pos) {
        if (level != null && level.isClientSide) {
            this.mainPos = pos;
        }
    }

    public BlockPos getMainPos() {
        return mainPos;
    }

    public boolean hasValidMainPos() {
        return !(mainPos.getX() == worldPosition.getX() && mainPos.getY() == worldPosition.getY() && mainPos.getZ() == worldPosition.getZ());
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

    public void onInvalidation() {}

    public void onValidation() {}

    public static Vector3f rotatedVecPos(Direction direction, Vector3f pos, float xOffset, float yOffset, float zOffset) {
        float X = pos.x;
        float Y = pos.y;
        float Z = pos.z;
        if (direction == Direction.NORTH) {
            return new Vector3f(X + xOffset, Y + yOffset, Z + zOffset);
        } else if (direction == Direction.EAST) {
            return new Vector3f(X - zOffset, Y + yOffset, Z + xOffset);
        } else if (direction == Direction.SOUTH) {
            return new Vector3f(X - xOffset, Y + yOffset, Z - zOffset);
        } else if (direction == Direction.WEST) {
            return new Vector3f(X + zOffset, Y + yOffset, Z - xOffset);
        } else if (direction == Direction.UP) {
            return new Vector3f(X + xOffset, Y + zOffset, Z - yOffset);
        } else if (direction == Direction.DOWN) {
            return new Vector3f(X + xOffset, Y - zOffset, Z + yOffset);
        } else {
            PixelsOfMc.LOGGER.error("fail while trying to chance position");
            return pos;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);
        return compound;
    }
}
