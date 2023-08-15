package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.block.AbstractPillarFusionCasing;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class MultiBlockStructures {
    private static final BlockState CASING = POMblocks.FUSION_CASING.get().defaultBlockState();
    private static final BlockState CASING_UP = CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Y);
    private static final BlockState CASING_FORWARD = CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Z);
    private static final BlockState CASING_SIDE = CASING.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.X);

    private static final BlockState SUPERCONDUCTIVE = POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get().defaultBlockState();
    private static final BlockState SUPERCONDUCTIVE_UP = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Y);
    private static final BlockState SUPERCONDUCTIVE_FORWARD = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.Z);
    private static final BlockState SUPERCONDUCTIVE_SIDE = SUPERCONDUCTIVE.setValue(AbstractPillarFusionCasing.AXIS, Direction.Axis.X);
    private static final BlockState CORNER = POMblocks.FUSION_CORNER.get().defaultBlockState();
    private static final BlockState GLASS = POMblocks.REINFORCED_GLASS.get().defaultBlockState();
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState OTHER = POMblocks.REINFORCED_CASING.get().defaultBlockState();

    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you
    
    //SDS
    public static final BlockState[][][] SDS_STRUCTURE = {
            {
                    {CORNER, CASING_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, CASING_SIDE, CASING_SIDE, CASING_SIDE, CORNER}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, null, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CORNER, CASING_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, CASING_SIDE, CASING_SIDE, CASING_SIDE, CORNER}
            }
    };
    //MDS
    public static final BlockState[][][] MDS_STRUCTURE = {
            {
                    {CORNER, CASING_SIDE, CASING_SIDE, SUPERCONDUCTIVE_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {SUPERCONDUCTIVE_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, CASING_SIDE, CASING_SIDE, SUPERCONDUCTIVE_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP}
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
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CORNER, CASING_SIDE, CASING_SIDE, SUPERCONDUCTIVE_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {SUPERCONDUCTIVE_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, CASING_SIDE, CASING_SIDE, SUPERCONDUCTIVE_SIDE, CASING_SIDE, CASING_SIDE, CORNER},
            }
    };
    //MNS
    public static final BlockState[][][] MNS_STRUCTURE = {
            {
                    {null, null, CORNER, CASING_SIDE, CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, CORNER, CASING_SIDE, CORNER, null, null}
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
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER}
            },
            {
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING_UP, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_UP}
            },
            {
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER}
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
                    {null, null, CORNER, CASING_SIDE, CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {CASING_FORWARD, GLASS, GLASS, GLASS, GLASS, GLASS, CASING_FORWARD},
                    {CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, CORNER, CASING_SIDE, CORNER, null, null}
            }
    };
    //BH
    public static final BlockState[][][] BH_STRUCTURE = {
            {
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, CASING, CASING, CASING, null, null, null},
                    {null, null, CASING, GLASS, GLASS, GLASS, CASING, null, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, null, CASING, GLASS, GLASS, GLASS, CASING, null, null},
                    {null, null, null, CASING, CASING, CASING, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
            },
            {
                    {null, null, CASING, CASING, CASING, CASING, CASING, null, null},
                    {null, CASING, CASING, AIR, AIR, AIR, CASING, CASING, null},
                    {CASING, CASING, AIR, AIR, AIR, AIR, AIR, CASING, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, CASING, AIR, AIR, AIR, AIR, AIR, CASING, CASING},
                    {null, CASING, CASING, AIR, AIR, AIR, CASING, CASING, null},
                    {null, null, CASING, CASING, CASING, CASING, CASING, null, null},
            },
            {
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, null, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
            },
            {
                    {null, null, CASING, CASING, CASING, CASING, CASING, null, null},
                    {null, CASING, CASING, AIR, AIR, AIR, CASING, CASING, null},
                    {CASING, CASING, AIR, AIR, AIR, AIR, AIR, CASING, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, AIR, AIR, AIR, AIR, AIR, AIR, AIR, CASING},
                    {CASING, CASING, AIR, AIR, AIR, AIR, AIR, CASING, CASING},
                    {null, CASING, CASING, AIR, AIR, AIR, CASING, CASING, null},
                    {null, null, CASING, CASING, CASING, CASING, CASING, null, null},
            },
            {
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, CASING, CASING, CASING, null, null, null},
                    {null, null, CASING, GLASS, GLASS, GLASS, CASING, null, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING, null},
                    {null, null, CASING, GLASS, GLASS, GLASS, CASING, null, null},
                    {null, null, null, CASING, CASING, CASING, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
            }
    };
    
}
