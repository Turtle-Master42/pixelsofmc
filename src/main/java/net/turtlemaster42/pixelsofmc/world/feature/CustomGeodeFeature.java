package net.turtlemaster42.pixelsofmc.world.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;
import net.turtlemaster42.pixelsofmc.block.AcanthiteSpikeBlock;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class CustomGeodeFeature extends Feature<GeodeConfiguration> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public CustomGeodeFeature(Codec<GeodeConfiguration> config) {
        super(config);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param context A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<GeodeConfiguration> context) {
        GeodeConfiguration geodeconfiguration = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        int minGenOffset = geodeconfiguration.minGenOffset;
        int maxGenOffset = geodeconfiguration.maxGenOffset;
        List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
        int distributionSample = geodeconfiguration.distributionPoints.sample(randomsource);
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(worldgenlevel.getSeed()));
        NormalNoise normalnoise = NormalNoise.create(worldgenrandom, -4, 1.0D);
        List<BlockPos> crackPosList = Lists.newLinkedList();
        double maxOuterWallDistance = (double)distributionSample / (double)geodeconfiguration.outerWallDistance.getMaxValue();
        GeodeLayerSettings geodelayersettings = geodeconfiguration.geodeLayerSettings;
        GeodeBlockSettings geodeblocksettings = geodeconfiguration.geodeBlockSettings;
        GeodeCrackSettings geodecracksettings = geodeconfiguration.geodeCrackSettings;
        double filling = 1.0D / Math.sqrt(geodelayersettings.filling);
        double innerLayer = 1.0D / Math.sqrt(geodelayersettings.innerLayer + maxOuterWallDistance);
        double middleLayer = 1.0D / Math.sqrt(geodelayersettings.middleLayer + maxOuterWallDistance);
        double outerLayer = 1.0D / Math.sqrt(geodelayersettings.outerLayer + maxOuterWallDistance);
        double crackSize = 1.0D / Math.sqrt(geodecracksettings.baseCrackSize + randomsource.nextDouble() / 2.0D + (distributionSample > 3 ? maxOuterWallDistance : 0.0D));
        boolean generateCrack = (double)randomsource.nextFloat() < geodecracksettings.generateCrackChance;
        int l = 0;

        for(int i1 = 0; i1 < distributionSample; ++i1) {
            int j1 = geodeconfiguration.outerWallDistance.sample(randomsource);
            int k1 = geodeconfiguration.outerWallDistance.sample(randomsource);
            int l1 = geodeconfiguration.outerWallDistance.sample(randomsource);
            BlockPos blockpos1 = blockpos.offset(j1, k1, l1);
            BlockState blockstate = worldgenlevel.getBlockState(blockpos1);
            if (blockstate.isAir() || blockstate.is(BlockTags.GEODE_INVALID_BLOCKS)) {
                ++l;
                if (l > geodeconfiguration.invalidBlocksThreshold) {
                    return false;
                }
            }

            list.add(Pair.of(blockpos1, geodeconfiguration.pointOffset.sample(randomsource)));
        }

        if (generateCrack) {
            int i2 = randomsource.nextInt(4);
            int j2 = distributionSample * 2 + 1;
            if (i2 == 0) {
                crackPosList.add(blockpos.offset(j2, 7, 0));
                crackPosList.add(blockpos.offset(j2, 5, 0));
                crackPosList.add(blockpos.offset(j2, 1, 0));
            } else if (i2 == 1) {
                crackPosList.add(blockpos.offset(0, 7, j2));
                crackPosList.add(blockpos.offset(0, 5, j2));
                crackPosList.add(blockpos.offset(0, 1, j2));
            } else if (i2 == 2) {
                crackPosList.add(blockpos.offset(j2, 7, j2));
                crackPosList.add(blockpos.offset(j2, 5, j2));
                crackPosList.add(blockpos.offset(j2, 1, j2));
            } else {
                crackPosList.add(blockpos.offset(0, 7, 0));
                crackPosList.add(blockpos.offset(0, 5, 0));
                crackPosList.add(blockpos.offset(0, 1, 0));
            }
        }

        List<BlockPos> innerPlacementPosList = Lists.newArrayList();
        Predicate<BlockState> predicate = isReplaceable(geodeconfiguration.geodeBlockSettings.cannotReplace);

        for(BlockPos blockPosInFeature : BlockPos.betweenClosed(blockpos.offset(minGenOffset, minGenOffset, minGenOffset), blockpos.offset(maxGenOffset, maxGenOffset, maxGenOffset))) {
            double noiseValue = normalnoise.getValue(blockPosInFeature.getX(), blockPosInFeature.getY(), blockPosInFeature.getZ()) * geodeconfiguration.noiseMultiplier;
            double d6 = 0.0D;
            double d7 = 0.0D;

            for(Pair<BlockPos, Integer> pair : list) {
                d6 += Mth.invSqrt(blockPosInFeature.distSqr(pair.getFirst()) + (double)pair.getSecond().intValue()) + noiseValue;
            }

            for(BlockPos blockpos6 : crackPosList) {
                d7 += Mth.invSqrt(blockPosInFeature.distSqr(blockpos6) + (double)geodecracksettings.crackPointOffset) + noiseValue;
            }

            if (!(d6 < outerLayer)) {
                if (generateCrack && d7 >= crackSize && d6 < filling) {
                    this.safeSetBlock(worldgenlevel, blockPosInFeature, Blocks.AIR.defaultBlockState(), predicate);

                    for(Direction direction1 : DIRECTIONS) {
                        BlockPos blockpos2 = blockPosInFeature.relative(direction1);
                        FluidState fluidstate = worldgenlevel.getFluidState(blockpos2);
                        if (!fluidstate.isEmpty()) {
                            worldgenlevel.scheduleTick(blockpos2, fluidstate.getType(), 0);
                        }
                    }
                } else if (d6 >= filling) {
                    this.safeSetBlock(worldgenlevel, blockPosInFeature, geodeblocksettings.fillingProvider.getState(randomsource, blockPosInFeature), predicate);
                } else if (d6 >= innerLayer) {
                    boolean flag1 = (double)randomsource.nextFloat() < geodeconfiguration.useAlternateLayer0Chance;
                    if (flag1) {
                        this.safeSetBlock(worldgenlevel, blockPosInFeature, geodeblocksettings.alternateInnerLayerProvider.getState(randomsource, blockPosInFeature), predicate);
                    } else {
                        this.safeSetBlock(worldgenlevel, blockPosInFeature, geodeblocksettings.innerLayerProvider.getState(randomsource, blockPosInFeature), predicate);
                    }

                    if ((!geodeconfiguration.placementsRequireLayer0Alternate || flag1) && (double)randomsource.nextFloat() < geodeconfiguration.usePotentialPlacementsChance) {
                        innerPlacementPosList.add(blockPosInFeature.immutable());
                    }
                } else if (d6 >= middleLayer) {
                    this.safeSetBlock(worldgenlevel, blockPosInFeature, geodeblocksettings.middleLayerProvider.getState(randomsource, blockPosInFeature), predicate);
                } else if (d6 >= outerLayer) {
                    this.safeSetBlock(worldgenlevel, blockPosInFeature, geodeblocksettings.outerLayerProvider.getState(randomsource, blockPosInFeature), predicate);
                }
            }
        }

        List<BlockState> innerPlacementStates = geodeblocksettings.innerPlacements;

        for(BlockPos placementPos : innerPlacementPosList) {
            BlockState randomInnerState = Util.getRandom(innerPlacementStates, randomsource);


            Collection<Direction> RANDOMIZED_DIRECTIONS = Direction.allShuffled(context.random());

            for(Direction direction : RANDOMIZED_DIRECTIONS) {
                boolean modifiedRelativeState = false;
                if (randomInnerState.hasProperty(BlockStateProperties.FACING)) {
                    randomInnerState = randomInnerState.setValue(BlockStateProperties.FACING, direction);
                }

                BlockPos relativePlacementPos = placementPos.relative(direction);
                BlockState relativePlacementState = worldgenlevel.getBlockState(relativePlacementPos);
                BlockPos longRelativePlacementPos = placementPos.relative(direction, 2);
                BlockState longRelativePlacementState = worldgenlevel.getBlockState(longRelativePlacementPos);

                if (randomInnerState.hasProperty(AcanthiteSpikeBlock.SPIKE_TYPE)) {
                    if (longRelativePlacementState.getBlock().equals(randomInnerState.getBlock()) && longRelativePlacementState.getValue(AcanthiteSpikeBlock.SPIKE_TYPE) == 0 && worldgenlevel.getBlockState(placementPos.relative(direction, 3)).isFaceSturdy(worldgenlevel, placementPos.relative(direction, 3), direction.getOpposite())) {
                        randomInnerState = randomInnerState.setValue(AcanthiteSpikeBlock.SPIKE_TYPE, 1);
                        longRelativePlacementState = longRelativePlacementState.setValue(AcanthiteSpikeBlock.SPIKE_TYPE, 1).setValue(BlockStateProperties.FACING, direction.getOpposite());
                        modifiedRelativeState = true;
                    } else if (longRelativePlacementState.isFaceSturdy(worldgenlevel, longRelativePlacementPos, direction.getOpposite())) {
                        randomInnerState = randomInnerState.setValue(AcanthiteSpikeBlock.SPIKE_TYPE, 2);
                    } else {
                        randomInnerState = randomInnerState.setValue(AcanthiteSpikeBlock.SPIKE_TYPE, 0);
                    }
                }

                if (randomInnerState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    randomInnerState = randomInnerState.setValue(BlockStateProperties.WATERLOGGED, relativePlacementState.getFluidState().isSource());
                }

                if (BuddingAmethystBlock.canClusterGrowAtState(relativePlacementState)) {
                    this.safeSetBlock(worldgenlevel, relativePlacementPos, randomInnerState, predicate);
                    if (modifiedRelativeState) {
                        this.safeSetBlock(worldgenlevel, longRelativePlacementPos, longRelativePlacementState, predicate);
                    }

                    break;
                }
            }
        }

        return true;
    }
}