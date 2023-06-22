package net.turtlemaster42.pixelsofmc.util.block;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.dummy.tile.AbstractDummyMachineBlockTile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BigMachineBlockUtil {

    public static void setMachineBlock(Level pLevel, Direction direction, int Xoffset, int Yoffset, int Zoffset, BlockState pState, BlockPos pPos) {
        setMachineBlock(pLevel, direction, Xoffset, Yoffset, Zoffset, pState, pPos, 2);
    }
    public static void setMachineBlock(Level pLevel, Direction direction, int Xoffset, int Yoffset, int Zoffset, BlockState pState, BlockPos mainPos, int flags) {
        int X = mainPos.getX();
        int Y = mainPos.getY();
        int Z = mainPos.getZ();
        if (direction == Direction.NORTH) {
            BlockPos newPos = new BlockPos(X + Xoffset,Y + Yoffset,Z + Zoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(getTileEntity(AbstractDummyMachineBlockTile.class, pLevel, newPos, true), mainPos);
        } else if (direction == Direction.EAST) {
            BlockPos newPos = new BlockPos(X - Zoffset,Y + Yoffset,Z + Xoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(getTileEntity(AbstractDummyMachineBlockTile.class, pLevel, newPos, true), mainPos);
        } else if (direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(X - Xoffset,Y + Yoffset,Z - Zoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(getTileEntity(AbstractDummyMachineBlockTile.class, pLevel, newPos, true), mainPos);
        } else if (direction == Direction.WEST) {
            BlockPos newPos = new BlockPos(X + Zoffset,Y + Yoffset,Z - Xoffset);
            pLevel.setBlock(newPos, pState, flags);
            setMainPos(getTileEntity(AbstractDummyMachineBlockTile.class, pLevel, newPos, true), mainPos);
        } else {
            PixelsOfMc.LOGGER.error("fail while trying to build big machine at {}, {}, {}",X,Y,Z);
        }
    }

    //use offset numbers based on the default (NORTH) direction
    public static BlockPos rotateBlockPosOnDirection(Direction direction, int Xoffset, int Yoffset, int Zoffset, BlockPos mainPos) {
        int X = mainPos.getX();
        int Y = mainPos.getY();
        int Z = mainPos.getZ();
        if (direction == Direction.NORTH) {
            return new BlockPos(X + Xoffset, Y + Yoffset, Z + Zoffset);
        } else if (direction == Direction.EAST) {
            return new BlockPos(X - Zoffset, Y + Yoffset, Z + Xoffset);
        } else if (direction == Direction.SOUTH) {
            return new BlockPos(X - Xoffset, Y + Yoffset, Z - Zoffset);
        } else if (direction == Direction.WEST) {
            return new BlockPos(X + Zoffset, Y + Yoffset, Z - Xoffset);
        } else {
            PixelsOfMc.LOGGER.error("fail while trying to chance position");
            return null;
        }
    }

//    public static BlockState rotateBlockStateOnDirection(Direction direction, Level level, BlockPos pos, Direction defaultDirection) {
//        BlockState state = level.getBlockState(pos);
//
//        if (direction == Direction.NORTH) {
//            if (state.rotate(level, pos, defaultDirection.));
//
//        } else if (direction == Direction.EAST) {
//
//        } else if (direction == Direction.SOUTH) {
//
//        } else if (direction == Direction.WEST) {
//
//        } else {
//            PixelsOfMc.LOGGER.error("fail while trying to chance position");
//            return null;
//        }
//    }


    public static Boolean BigMachinePlacement(BlockPlaceContext pContext, int Xoffset, int Yoffset, int Zoffset) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return level.getBlockState(BigMachineBlockUtil.rotateBlockPosOnDirection(pContext.getHorizontalDirection(), Xoffset*-1, Yoffset, Zoffset*-1, blockpos)).canBeReplaced(pContext);
    }


    public static void setMainPos(AbstractDummyMachineBlockTile tile, BlockPos mainPos) {
            tile.setMainPos(mainPos);
    }

    public static BlockPos getMainPos(Level pLevel, BlockPos pPos) {
        AbstractDummyMachineBlockTile tile = getTileEntity(AbstractDummyMachineBlockTile.class, pLevel, pPos);
        return tile.getMainPos();
    }

    //-- MEKANISM --
    /**
     * Checks if the chunk at the given position is loaded but does not validate the position is in bounds of the world.
     *
     * @param world world
     * @param pos   position
     *
     * @see #isBlockLoaded(BlockGetter, BlockPos)
     */
    @Contract("null, _ -> false")
    public static boolean isChunkLoaded(@Nullable LevelReader world, @NotNull BlockPos pos) {
        return isChunkLoaded(world, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
    }

    /**
     * Checks if the chunk at the given position is loaded.
     *
     * @param world    world
     * @param chunkPos Chunk position
     */
    @Contract("null, _ -> false")
    public static boolean isChunkLoaded(@Nullable LevelReader world, ChunkPos chunkPos) {
        return isChunkLoaded(world, chunkPos.x, chunkPos.z);
    }

    /**
     * Checks if the chunk at the given position is loaded.
     *
     * @param world  world
     * @param chunkX Chunk X coordinate
     * @param chunkZ Chunk Z coordinate
     */
    @Contract("null, _, _ -> false")
    public static boolean isChunkLoaded(@Nullable LevelReader world, int chunkX, int chunkZ) {
        if (world == null) {
            return false;
        } else if (world instanceof LevelAccessor accessor && accessor.getChunkSource() instanceof ServerChunkCache serverChunkCache) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> future = serverChunkCache.getChunkFuture(chunkX, chunkZ, ChunkStatus.FULL, false);
            return future.isDone() && future.getNow(ChunkHolder.UNLOADED_CHUNK).left().isPresent();
        }
        return world.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false) != null;
    }


    /**
     * Checks if a position is in bounds of the world, and is loaded
     *
     * @param world world
     * @param pos   position
     *
     * @return True if the position is loaded or the given world is of a superclass of IWorldReader that does not have a concept of being loaded.
     */
    @Contract("null, _ -> false")
    public static boolean isBlockLoaded(@Nullable BlockGetter world, @NotNull BlockPos pos) {
        if (world == null) {
            return false;
        } else if (world instanceof LevelReader reader) {
            if (reader instanceof Level level && !level.isInWorldBounds(pos)) {
                return false;
            }
            //TODO: If any cases come up where things are behaving oddly due to the change from reader.hasChunkAt(pos)
            // re-evaluate this and if the specific case is being handled properly
            return isChunkLoaded(reader, pos);
        }
        return true;
    }

    /**
     * Checks if a position is in bounds of the world
     *
     * @param world world
     * @param pos   position
     *
     * @return True if the position is in bounds of the world or the given world is of a superclass of IWorldReader that does not have a concept of bounds.
     */
    @Contract("null, _ -> false")
    public static boolean isBlockInBounds(@Nullable BlockGetter world, @NotNull BlockPos pos) {
        if (world == null) {
            return false;
        } else if (world instanceof LevelReader reader) {
            return !(reader instanceof Level level) || level.isInWorldBounds(pos);
        }
        return true;
    }

    /**
     * Gets the chunk in a given position or {@code null} if there is no world, the position is out of bounds or the chunk isn't loaded. Tries to retrieve it from our
     * cache and if it isn't found, tries to get it from the world and adds it to our cache.
     *
     * @param world    world
     * @param chunkMap cached chunk map
     * @param pos      position
     *
     * @return The chunk in a given position or {@code null} if there is no world, the position is out of bounds or the chunk isn't loaded
     */
    @Nullable
    @Contract("null, _, _ -> null")
    private static ChunkAccess getChunkForPos(@Nullable LevelAccessor world, @NotNull Long2ObjectMap<ChunkAccess> chunkMap, @NotNull BlockPos pos) {
        if (!isBlockInBounds(world, pos)) {
            //Allow the world to be nullable to remove warnings when we are calling things from a place that world could be null
            // Also short circuit to check if the position is out of bounds before bothering to look up the chunk
            return null;
        }
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        long combinedChunk = (((long) chunkX) << 32) | (chunkZ & 0xFFFFFFFFL);
        //We get the chunk rather than the world, so we can cache the chunk improving the overall
        // performance for retrieving a bunch of chunks in the general vicinity
        ChunkAccess chunk = chunkMap.get(combinedChunk);
        if (chunk == null) {
            //Get the chunk but don't force load it
            chunk = world.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
            if (chunk != null) {
                chunkMap.put(combinedChunk, chunk);
            }
        }
        return chunk;
    }

    /**
     * Gets a tile entity if the location is loaded by getting the chunk from the passed in cache of chunks rather than directly using the world. We then store our chunk
     * we found back in the cache to more quickly be able to look up chunks if we are doing lots of lookups at once (For example the transporter pathfinding)
     *
     * @param world    world
     * @param chunkMap cached chunk map
     * @param pos      position
     *
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    @Contract("null, _, _ -> null")
    public static BlockEntity getTileEntity(@Nullable LevelAccessor world, @NotNull Long2ObjectMap<ChunkAccess> chunkMap, @NotNull BlockPos pos) {
        //Get the tile entity using the chunk we found/had cached
        return getTileEntity(getChunkForPos(world, chunkMap, pos), pos);
    }

    /**
     * Gets a tile entity if the location is loaded by getting the chunk from the passed in cache of chunks rather than directly using the world. We then store our chunk
     * we found back in the cache to more quickly be able to look up chunks if we are doing lots of lookups at once (For example the transporter pathfinding)
     *
     * @param clazz    Class type of the TileEntity we expect to be in the position
     * @param world    world
     * @param chunkMap cached chunk map
     * @param pos      position
     *
     * @return tile entity if found, null if either not found, not loaded, or of the wrong type
     */
    @Nullable
    @Contract("_, null, _, _ -> null")
    public static <T extends BlockEntity> T getTileEntity(@NotNull Class<T> clazz, @Nullable LevelAccessor world, @NotNull Long2ObjectMap<ChunkAccess> chunkMap, @NotNull BlockPos pos) {
        return getTileEntity(clazz, world, chunkMap, pos, false);
    }

    /**
     * Gets a tile entity if the location is loaded by getting the chunk from the passed in cache of chunks rather than directly using the world. We then store our chunk
     * we found back in the cache to more quickly be able to look up chunks if we are doing lots of lookups at once (For example the transporter pathfinding)
     *
     * @param clazz        Class type of the TileEntity we expect to be in the position
     * @param world        world
     * @param chunkMap     cached chunk map
     * @param pos          position
     * @param logWrongType Whether an error should be logged if a tile of a different type is found at the position
     *
     * @return tile entity if found, null if either not found, not loaded, or of the wrong type
     */
    @Nullable
    @Contract("_, null, _, _, _ -> null")
    public static <T extends BlockEntity> T getTileEntity(@NotNull Class<T> clazz, @Nullable LevelAccessor world, @NotNull Long2ObjectMap<ChunkAccess> chunkMap, @NotNull BlockPos pos,
                                                          boolean logWrongType) {
        //Get the tile entity using the chunk we found/had cached
        return getTileEntity(clazz, getChunkForPos(world, chunkMap, pos), pos, logWrongType);
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param world world
     * @param pos   position
     *
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    @Contract("null, _ -> null")
    public static BlockEntity getTileEntity(@Nullable BlockGetter world, @NotNull BlockPos pos) {
        if (!isBlockLoaded(world, pos)) {
            //If the world is null, or it is a world reader and the block is not loaded, return null
            PixelsOfMc.LOGGER.error("Block at {} is not loaded", pos);
            return null;
        }
        return world.getBlockEntity(pos);
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param clazz Class type of the TileEntity we expect to be in the position
     * @param world world
     * @param pos   position
     *
     * @return tile entity if found, null if either not found, not loaded, or of the wrong type
     */
    @Nullable
    @Contract("_, null, _ -> null")
    public static <T extends BlockEntity> T getTileEntity(@NotNull Class<T> clazz, @Nullable BlockGetter world, @NotNull BlockPos pos) {
        return getTileEntity(clazz, world, pos, false);
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param clazz        Class type of the TileEntity we expect to be in the position
     * @param world        world
     * @param pos          position
     * @param logWrongType Whether an error should be logged if a tile of a different type is found at the position
     *
     * @return tile entity if found, null if either not found or not loaded, or of the wrong type
     */
    @Nullable
    @Contract("_, null, _, _ -> null")
    public static <T extends BlockEntity> T getTileEntity(@NotNull Class<T> clazz, @Nullable BlockGetter world, @NotNull BlockPos pos, boolean logWrongType) {
        BlockEntity tile = getTileEntity(world, pos);
        if (tile == null) {
            return null;
        }
        if (clazz.isInstance(tile)) {
            return clazz.cast(tile);
        } else if (logWrongType) {
            PixelsOfMc.LOGGER.warn("Unexpected TileEntity class at {}, expected {}, but found: {}", pos, clazz, tile.getClass());
        }
        return null;
    }
}
