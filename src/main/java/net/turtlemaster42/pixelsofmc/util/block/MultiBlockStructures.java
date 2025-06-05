package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.world.level.block.Blocks;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class MultiBlockStructures {
    //normal
    private static final GhostBlockState GLASS = new GhostBlockState(POMblocks.REINFORCED_GLASS.get());
    private static final GhostBlockState AIR = new GhostBlockState(Blocks.AIR);
    private static final GhostBlockState WATER = new GhostBlockState(Blocks.WATER);
    private static final GhostBlockState OTHER = new GhostBlockState(POMblocks.REINFORCED_CASING.get());
    private static final GhostBlockState CASING = new GhostBlockState(POMblocks.MACHINE_CASING.get());
    private static final GhostBlockState ARMORED_CASING = new GhostBlockState(POMblocks.ARMORED_MACHINE_CASING.get());
    private static final GhostBlockState HEAT_SINK = new GhostBlockState(POMblocks.HEAT_SINK.get());

    //decoration
    private static final GhostBlockState DECOR = new GhostBlockState(POMblocks.MACHINE_CASING_STAIRS.get());
    private static final GhostBlockState FISSION_DECOR = new GhostBlockState(POMblocks.FISSION_CASING_STAIRS.get());
    private static final GhostBlockState FUSION_DECOR = new GhostBlockState(POMblocks.ARMORED_MACHINE_CASING_STAIRS.get());

    //ports
    private static final GhostBlockState FLUID_PORT = new GhostBlockState(POMblocks.FLUID_PORT.get());

    //fission
    private static final GhostBlockState FISSION_CASING = new GhostBlockState(POMblocks.FISSION_CASING.get());
    private static final GhostBlockState FUEL_CELL_HOLDER = new GhostBlockState(POMblocks.FUEL_CELL_HOLDER.get());

    //fusion
    private static final GhostBlockState FUSION_CASING = new GhostBlockState(POMblocks.FUSION_CASING.get());

    private static final GhostBlockState SUPERCONDUCTIVE = new GhostBlockState(POMblocks.SUPERCONDUCTIVE_FUSION_CASING.get());
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
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CORNER}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, null, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, FUSION_CASING, FUSION_CORNER}
            }
    };
    //MDS
    public static final GhostBlockState[][][] MDS_STRUCTURE = {
            {
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, SUPERCONDUCTIVE, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {SUPERCONDUCTIVE, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, SUPERCONDUCTIVE, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {SUPERCONDUCTIVE, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {SUPERCONDUCTIVE, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING}
            },
            {
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, SUPERCONDUCTIVE, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {SUPERCONDUCTIVE, GLASS, GLASS, GLASS, GLASS, GLASS, SUPERCONDUCTIVE},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, FUSION_CASING, FUSION_CASING, SUPERCONDUCTIVE, FUSION_CASING, FUSION_CASING, FUSION_CORNER},
            }
    };
    //MNS
    public static final GhostBlockState[][][] MNS_STRUCTURE = {
            {
                    {null, null, FUSION_CORNER, FUSION_CASING, FUSION_CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, FUSION_CORNER, FUSION_CASING, FUSION_CORNER, null, null}
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
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING}
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
                    {null, null, FUSION_CORNER, FUSION_CASING, FUSION_CORNER, null, null},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {FUSION_CASING, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CASING},
                    {FUSION_CORNER, GLASS, GLASS, GLASS, GLASS, GLASS, FUSION_CORNER},
                    {null, OTHER, GLASS, GLASS, GLASS, OTHER, null},
                    {null, null, FUSION_CORNER, FUSION_CASING, FUSION_CORNER, null, null}
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

    //INDUSTRIAL COOLER
    public static final GhostBlockState[][][] INDUSTRIAL_HEAT_EXCHANGER = {
            {
                    {DECOR, CASING, DECOR},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {DECOR, CASING, DECOR}
            },
            {
                    {CASING, FLUID_PORT, CASING},
                    {GLASS, HEAT_SINK, GLASS},
                    {GLASS, HEAT_SINK, GLASS},
                    {GLASS, HEAT_SINK, GLASS},
                    {CASING, FLUID_PORT, CASING}
            },
            {
                    {DECOR, null, DECOR},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {HEAT_SINK, GLASS, HEAT_SINK},
                    {DECOR, CASING, DECOR}
            }
    };
    
}
