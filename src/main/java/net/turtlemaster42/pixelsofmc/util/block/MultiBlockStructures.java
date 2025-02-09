package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.block.AbstractPillarFusionCasing;
import net.turtlemaster42.pixelsofmc.block.FuelCellHolderBlock;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class MultiBlockStructures {
    //normal
    private static final BlockState GLASS = POMblocks.REINFORCED_GLASS.get().defaultBlockState();
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState WATER = Blocks.WATER.defaultBlockState();
    private static final BlockState OTHER = POMblocks.REINFORCED_CASING.get().defaultBlockState();
    private static final BlockState CASING = POMblocks.MULTIBLOCK_CASING.get().defaultBlockState();
    private static final BlockState ARMORED_CASING = POMblocks.ARMORED_MULTIBLOCK_CASING.get().defaultBlockState();

    //fission
    private static final BlockState FISSION_CASING = POMblocks.FISSION_CASING.get().defaultBlockState();
    private static final BlockState FUEL_CELL_HOLDER = POMblocks.FUEL_CELL_HOLDER.get().defaultBlockState().setValue(FuelCellHolderBlock.FACING, Direction.UP);

    //fusion
    private static final BlockState FUSION_CASING = POMblocks.FUSION_CASING.get().defaultBlockState();
    private static final BlockState FUSION_CASING_UP = FUSION_CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Y);
    private static final BlockState FUSION_CASING_FORWARD = FUSION_CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Z);
    private static final BlockState FUSION_CASING_SIDE = FUSION_CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.X);

    private static final BlockState SUPERCONDUCTIVE = POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get().defaultBlockState();
    private static final BlockState SUPERCONDUCTIVE_UP = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Y);
    private static final BlockState SUPERCONDUCTIVE_FORWARD = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Z);
    private static final BlockState SUPERCONDUCTIVE_SIDE = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.X);
    private static final BlockState FUSION_CORNER = POMblocks.FUSION_CORNER.get().defaultBlockState();


    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you

    //NUCLEAR REACTOR
    public static final BlockState[][][] NUCLEAR_REACTOR = {
            {
                    {null, null, FISSION_CASING, null, null},
                    {null, FISSION_CASING, FISSION_CASING, FISSION_CASING, null},
                    {FISSION_CASING, FISSION_CASING, FISSION_CASING, FISSION_CASING, FISSION_CASING},
                    {null, FISSION_CASING, FISSION_CASING, FISSION_CASING, null},
                    {null, null, FISSION_CASING, null, null}
            },
            {
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null}
            },
            {
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null}
            },
            {
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {GLASS, WATER, WATER, WATER, GLASS},
                    {FISSION_CASING, WATER, WATER, WATER, FISSION_CASING},
                    {null, FISSION_CASING, GLASS, FISSION_CASING, null}
            },
            {
                    {null, null, FISSION_CASING, null, null},
                    {null, FISSION_CASING, FUEL_CELL_HOLDER, FISSION_CASING, null},
                    {FISSION_CASING, FUEL_CELL_HOLDER, null, FUEL_CELL_HOLDER, FISSION_CASING},
                    {null, FISSION_CASING, FUEL_CELL_HOLDER, FISSION_CASING, null},
                    {null, null, FISSION_CASING, null, null}
            },
    };

    //SDS
    public static final BlockState[][][] SDS_STRUCTURE = {
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
    public static final BlockState[][][] MDS_STRUCTURE = {
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
    public static final BlockState[][][] MNS_STRUCTURE = {
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
    public static final BlockState[][][] BH_STRUCTURE = {
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
