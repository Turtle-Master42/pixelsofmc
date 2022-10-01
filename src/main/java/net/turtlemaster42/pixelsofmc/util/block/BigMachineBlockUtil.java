package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class BigMachineBlockUtil {

    public static void setMachineBlock(Level pLevel, Direction direction, int Xoffset, int Yoffset, int Zoffset, BlockState pState, BlockPos mainPos) {
        int X = mainPos.getX();
        int Y = mainPos.getY();
        int Z = mainPos.getZ();
        if (direction == Direction.NORTH) {
            BlockPos newPos = new BlockPos(X + Xoffset,Y + Yoffset,Z + Zoffset);
            pLevel.setBlock(newPos, pState, 2);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.EAST) {
            BlockPos newPos = new BlockPos(X - Zoffset,Y + Yoffset,Z + Xoffset);
            pLevel.setBlock(newPos, pState, 2);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(X - Xoffset,Y + Yoffset,Z - Zoffset);
            pLevel.setBlock(newPos, pState, 2);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.WEST) {
            BlockPos newPos = new BlockPos(X + Zoffset,Y + Yoffset,Z - Xoffset);
            pLevel.setBlock(newPos, pState, 2);
            setMainPos(pLevel, newPos, mainPos);
        } else {
            PixelsOfMc.LOGGER.error("fail while trying to build big machine at {}, {}, {}",X,Y,Z);
        }
    }

    public static void setMachineBlock(Level pLevel, Direction direction, int Xoffset, int Yoffset, int Zoffset, BlockState pState, BlockPos mainPos, int flags) {
        int X = mainPos.getX();
        int Y = mainPos.getY();
        int Z = mainPos.getZ();
        if (direction == Direction.NORTH) {
            BlockPos newPos = new BlockPos(X + Xoffset,Y + Yoffset,Z + Zoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.EAST) {
            BlockPos newPos = new BlockPos(X - Zoffset,Y + Yoffset,Z + Xoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(X - Xoffset,Y + Yoffset,Z - Zoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(pLevel, newPos, mainPos);
        } else if (direction == Direction.WEST) {
            BlockPos newPos = new BlockPos(X + Zoffset,Y + Yoffset,Z - Xoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(pLevel, newPos, mainPos);
        } else {
            PixelsOfMc.LOGGER.error("fail while trying to build big machine at {}, {}, {}",X,Y,Z);
        }
    }

    public static void setMainPos(Level pLevel, BlockPos pPos, BlockPos mainPos) {
        BlockEntity pBlockentity = pLevel.getBlockEntity(pPos);

        assert pBlockentity != null;
        pBlockentity.getTileData().putInt("mainX", mainPos.getX());
        pBlockentity.getTileData().putInt("mainY", mainPos.getY());
        pBlockentity.getTileData().putInt("mainZ", mainPos.getZ());
    }
}
