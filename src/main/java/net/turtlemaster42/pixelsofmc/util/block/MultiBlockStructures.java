package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.turtlemaster42.pixelsofmc.block.AbstractPillarFusionCasing;
import net.turtlemaster42.pixelsofmc.block.FuelCellHolderBlock;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class MultiBlockStructures {
    //normal
    private static final GhostBlockState GLASS = new GhostBlockState(POMblocks.REINFORCED_GLASS.get());
    private static final GhostBlockState AIR = new GhostBlockState(Blocks.AIR);
    private static final GhostBlockState WATER = new GhostBlockState(Blocks.WATER);
    private static final GhostBlockState OTHER = new GhostBlockState(POMblocks.REINFORCED_CASING.get());
    private static final GhostBlockState CASING = new GhostBlockState(POMblocks.MULTIBLOCK_CASING.get());
    private static final GhostBlockState ARMORED_CASING = new GhostBlockState(POMblocks.ARMORED_MULTIBLOCK_CASING.get());

    //fission
    private static final GhostBlockState FISSION_CASING = new GhostBlockState(POMblocks.FISSION_CASING.get());
    private static final GhostBlockState FUEL_CELL_HOLDER = new GhostBlockState(POMblocks.FUEL_CELL_HOLDER.get()); //.addProperty(FuelCellHolderBlock.FACING, Direction.UP);

    //fusion
    private static final GhostBlockState FUSION_CASING = new GhostBlockState(POMblocks.FUSION_CASING.get());
    private static final GhostBlockState FUSION_CASING_UP = new GhostBlockState(POMblocks.FUSION_CASING.get()).addProperty(BlockStateProperties.AXIS, Direction.Axis.Y);
    private static final GhostBlockState FUSION_CASING_FORWARD = new GhostBlockState(POMblocks.FUSION_CASING.get()).addProperty(BlockStateProperties.AXIS, Direction.Axis.Z);
    private static final GhostBlockState FUSION_CASING_SIDE = new GhostBlockState(POMblocks.FUSION_CASING.get()).addProperty(BlockStateProperties.AXIS, Direction.Axis.X);

    private static final GhostBlockState SUPERCONDUCTIVE = new GhostBlockState(POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get());
    private static final GhostBlockState SUPERCONDUCTIVE_UP = new GhostBlockState(POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get()).addProperty(AbstractPillarFusionCasing.AXIS, Direction.Axis.Y);
    private static final GhostBlockState SUPERCONDUCTIVE_FORWARD = new GhostBlockState(POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get()).addProperty(AbstractPillarFusionCasing.AXIS, Direction.Axis.Z);
    private static final GhostBlockState SUPERCONDUCTIVE_SIDE = new GhostBlockState(POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get()).addProperty(AbstractPillarFusionCasing.AXIS, Direction.Axis.X);
    private static final GhostBlockState FUSION_CORNER = new GhostBlockState(POMblocks.FUSION_CORNER.get());


    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you

    //NUCLEAR REACTOR
    public static final GhostBlockState[][][] NUCLEAR_REACTOR = {
            {
                    {null, null, FISSION_CASING, null, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, null, FISSION_CASING, null, null}
            },
            {
                    {null, FISSION_CASING, FUEL_CELL_HOLDER, FISSION_CASING, null},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {null, FISSION_CASING, FISSION_CASING, FISSION_CASING, null}
            },
            {
                    {FISSION_CASING, FUEL_CELL_HOLDER, null, FUEL_CELL_HOLDER, FISSION_CASING},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {FISSION_CASING, FISSION_CASING, FISSION_CASING, FISSION_CASING, FISSION_CASING}
            },
            {
                    {null, FISSION_CASING, FUEL_CELL_HOLDER, FISSION_CASING, null},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {null, FISSION_CASING, FISSION_CASING, FISSION_CASING, null}
            },
            {
                    {null, null, FISSION_CASING, null, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {null, null, FISSION_CASING, null, null}
            },
    };

    //SDS
    public static final GhostBlockState[][][] SDS_STRUCTURE = {
            {
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, null, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER}
            }
    };
    //MDS
    public static final GhostBlockState[][][] MDS_STRUCTURE = {
            {
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, SUPERCONDUCTIVE_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {SUPERCONDUCTIVE_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, SUPERCONDUCTIVE_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {SUPERCONDUCTIVE_UP, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {SUPERCONDUCTIVE_UP, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_UP}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, SUPERCONDUCTIVE_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {SUPERCONDUCTIVE_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CASING_SIDE, SUPERCONDUCTIVE_SIDE, FUSION_CASING_SIDE, FUSION_CASING_SIDE, FUSION_CORNER},
            }
    };
    //MNS
    public static final GhostBlockState[][][] MNS_STRUCTURE = {
            {
                    {null, null, FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CORNER, null, null}
            },
            {
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {OTHER, AIR, AIR, AIR, AIR, AIR, OTHER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {OTHER, AIR, AIR, AIR, AIR, AIR, OTHER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null}
            },
            {
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER}
            },
            {
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_UP}
            },
            {
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER}
            },
            {
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {OTHER, AIR, AIR, AIR, AIR, AIR, OTHER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {OTHER, AIR, AIR, AIR, AIR, AIR, OTHER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null}
            },
            {
                    {null, null, FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {FUSION_CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING_FORWARD},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, FUSION_CORNER, FUSION_CASING_SIDE, FUSION_CORNER, null, null}
            }
    };
    //BH
    public static final GhostBlockState[][][] BH_STRUCTURE = {
            {
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null, null},
                    {null, null, FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING, null, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, null, FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING, null, null},
                    {null, null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
            },
            {
                    {null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null},
                    {null, FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING, null},
                    {FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING},
                    {null, FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING, null},
                    {null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null},
            },
            {
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, null, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
            },
            {
                    {null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null},
                    {null, FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING, null},
                    {FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, FUSION_CASING},
                    {FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING},
                    {null, FUSION_CASING, FUSION_CASING, AIR, AIR, AIR, FUSION_CASING, FUSION_CASING, null},
                    {null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null},
            },
            {
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null, null},
                    {null, null, FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING, null, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING, null},
                    {null, null, FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING, null, null},
                    {null, null, null, FUSION_CASING, FUSION_CASING, FUSION_CASING, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
            }
    };
    
}
