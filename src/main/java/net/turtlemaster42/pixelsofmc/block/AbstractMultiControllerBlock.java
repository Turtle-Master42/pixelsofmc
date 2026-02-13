package net.turtlemaster42.pixelsofmc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.init.POMblocks;
import net.turtlemaster42.pixelsofmc.init.POMparticles;
import net.turtlemaster42.pixelsofmc.init.POMtags;
import net.turtlemaster42.pixelsofmc.particle.options.ColoredBlockParticleOptions;
import net.turtlemaster42.pixelsofmc.tile.AbstractMultiBlockTile;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.block.BigMachineBlockUtil;
import net.turtlemaster42.pixelsofmc.util.block.GhostBlockState;
import net.turtlemaster42.pixelsofmc.util.block.IMultiControllerBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMultiControllerBlock extends BaseEntityBlock implements IMultiControllerBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty ACTIVE = IntegerProperty.create("state", 1, 3);
    private final TagKey<Block> glassReplaceable;

    protected AbstractMultiControllerBlock(TagKey<Block> glassReplaceable, Properties pProperties) {
        super(pProperties);
        this.glassReplaceable = glassReplaceable;
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ACTIVE);
    }

    @Override
    @javax.annotation.Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, 1);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide())
            invalidateMultiBlock(pLevel, pPos);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return null;
    }

    // --- Multi Block --- //

    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you
    protected final GhostBlockState[][][] MULTIBLOCK_STRUCTURE = getMultiblockStructure();

    public GhostBlockState[][][] getMultiblockStructure() {
        return new GhostBlockState[][][]{{{}}};
    }
    public int getHeight() {return 0;}
    public int getWidth() {return 0;}
    public int getLength() {return 0;}

    public Direction getControllerDirection(BlockState state) {
        return state.getValue(FACING);
    }

    public Map<Block, Integer> validateMultiBlock(Level level, BlockPos controllerPos, boolean feedback) {
        Map<Block, Integer> blocks = new HashMap<>();
        if (level.isClientSide()) return blocks;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = getControllerDirection(controllerState);
        int correctBlocks =0;
        int totalBlocks = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    GhostBlockState multiBlockState = MULTIBLOCK_STRUCTURE[y][z][x];
                    if (multiBlockState == null) continue;
                    totalBlocks++;

                    BlockPos blockPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
                    BlockState blockState = level.getBlockState(blockPos);
                    Block block = blockState.getBlock();

                    //BLOCK MAP
                    if (blocks.containsKey(block)) {
                        blocks.replace(block, blocks.get(block) + 1);
                    } else {
                        blocks.put(block, 1);
                    }

                    boolean isCorrect = false;

                    //GLASS
                    if (multiBlockState.is(POMblocks.REINFORCED_GLASS.get()) && blockState.is(glassReplaceable)) {
                        isCorrect = true;
                    } else if (multiBlockState.presentIn(blockState)) {
                        isCorrect = true;
                    }

                    //CASING
                    else if (multiBlockState.is(POMblocks.FISSION_CASING.get()) && blockState.is(POMblocks.ARMORED_MACHINE_CASING.get())) {
                        isCorrect = true;
                    } else if (multiBlockState.is(POMblocks.MACHINE_CASING.get()) && (blockState.is(POMblocks.FISSION_CASING.get()) || blockState.is(POMblocks.ARMORED_MACHINE_CASING.get()))) {
                        isCorrect = true;
                    }

                    //DECOR
                    else if (multiBlockState.is(POMblocks.ARMORED_MACHINE_CASING_STAIRS.get()) && blockState.is(POMtags.Blocks.FUSION_DECOR)) {
                        isCorrect = true;
                    } else if (multiBlockState.is(POMblocks.FISSION_CASING_STAIRS.get()) && blockState.is(POMtags.Blocks.FISSION_DECOR)) {
                        isCorrect = true;
                    } else if (multiBlockState.is(POMblocks.MACHINE_CASING_STAIRS.get()) && blockState.is(POMtags.Blocks.CASINGS_DECOR)) {
                        isCorrect = true;
                    }

                    // IS CORRECT
                    if (isCorrect) {
                        correctBlocks++;
                        if (feedback && !blockState.is(Blocks.AIR)) {
                            Util.spawnServerParticlesOnBlockFaces((ServerLevel) level, blockPos, POMparticles.GREEN_CROSS.get(), UniformInt.of(1, 1));
                        }
                        // TILE VALIDATION
                        if (block instanceof AbstractMultiBlock) {
                            if (level.getBlockEntity(blockPos) instanceof AbstractMultiBlockTile multiBlockTile) {
                                multiBlockTile.setMainPos(controllerPos);
                                multiBlockTile.onValidation();
                            }
                        }

                    } else if (feedback) {
                        Util.spawnServerParticlesOnBlockFaces((ServerLevel) level, blockPos, POMparticles.RED_CROSS.get(), UniformInt.of(2, 4));
                        Util.spawnServerParticlesOnBlockFaces((ServerLevel) level, blockPos, new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), blockState), UniformInt.of(9, 12));
                    }

                }

            }

        }
        if (correctBlocks == totalBlocks) {
            if (controllerState.getValue(ACTIVE) != 3)
                level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 2), 2);
        }  else {
            blocks.clear();
            invalidateMultiBlock(level, controllerPos);
        }
        return blocks;
    }

    public void invalidateMultiBlock(Level level, BlockPos controllerPos) {
        if (level.isClientSide()) return;

        BlockState controllerState = level.getBlockState(controllerPos);
        Direction direction = getControllerDirection(controllerState);

        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;

                    BlockPos rotatedOffsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);

                    if (level.getBlockState(rotatedOffsetPos).getBlock() instanceof AbstractMultiBlock) {
                        if (level.getBlockEntity(rotatedOffsetPos) instanceof AbstractMultiBlockTile multiBlockTile) {
                            multiBlockTile.onInvalidation();
                            multiBlockTile.setMainPos(rotatedOffsetPos);
                        }
                    }
                }
            }
        }
        level.setBlock(controllerPos, controllerState.setValue(ACTIVE, 1), 2);
    }

    public void forcePlaceMultiBlock(Level level, BlockPos controllerPos) {
        Direction direction = getControllerDirection(level.getBlockState(controllerPos));
        if (level.isClientSide()) {return;}
        int totalBlocks = 0;
        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;
                    BlockPos offsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
                    BlockState offsetState = MULTIBLOCK_STRUCTURE[y][z][x].toBlockState();
                    Util.spawnServerParticlesOnBlockFaces((ServerLevel) level, offsetPos, new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), offsetState), UniformInt.of(1, 3));

                    level.setBlock(offsetPos, offsetState, 2);
                }
            }
        }
        PixelsOfMc.LOGGER.info("placed {} blocks", totalBlocks);
    }

    public void forceRemoveMultiBlock(Level level, BlockPos controllerPos) {
        if (level.isClientSide()) return;
        Direction direction = getControllerDirection(level.getBlockState(controllerPos));
        int totalBlocks = 0;
        for (int y = 0; y < getHeight(); y++) {
            for (int z = 0; z < getLength(); z++) {
                for (int x = 0; x < getWidth(); x++) {
                    if (MULTIBLOCK_STRUCTURE[y][z][x] == null) continue;
                    totalBlocks++;
                    BlockPos offsetPos = rotatedOffsetBlock(direction, x, y, z, controllerPos);
                    BlockState offsetState = MULTIBLOCK_STRUCTURE[y][z][x].toBlockState();
                    Util.spawnServerParticlesOnBlockFaces((ServerLevel) level, offsetPos, new ColoredBlockParticleOptions(POMparticles.COLORED_BLOCK.get(), offsetState), UniformInt.of(3, 7));
                    level.removeBlock(rotatedOffsetBlock(direction, x, y, z, controllerPos), false);
                }
            }
        }
        PixelsOfMc.LOGGER.info("removed {} blocks", totalBlocks);
    }

    public BlockPos rotatedOffsetBlock(Direction direction, int x, int y, int z, BlockPos startPos) {
        return offsetMultiBlock(BigMachineBlockUtil.rotateBlockPosOnDirection(direction, x, y, z, startPos), direction);
    }

    public BlockPos rotatedOffsetBlock(Direction direction, BlockPos offset, BlockPos startPos) {
        return rotatedOffsetBlock(direction, offset.getX(), offset.getY(), offset.getZ(), startPos);
    }

    public BlockPos offsetMultiBlock(BlockPos pos, Direction direction) {
        return BigMachineBlockUtil.rotateBlockPosOnDirection(direction, 0, 0, 0, pos);
    }
    public BlockPos offsetMultiBlock(int x, int y, int z, Direction direction) {
        return offsetMultiBlock(new BlockPos(x,y,z), direction);
    }
}
