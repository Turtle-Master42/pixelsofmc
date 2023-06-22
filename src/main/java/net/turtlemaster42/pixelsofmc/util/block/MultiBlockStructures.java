package net.turtlemaster42.pixelsofmc.util.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMblocks;

public class MultiBlockStructures {
    private static final BlockState CASING = POMblocks.REINFORCED_CASING.get().defaultBlockState();
    private static final BlockState GLASS = POMblocks.REINFORCED_GLASS.get().defaultBlockState();
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();

    //BlockState[y][z][x]
    // ^ facing you
    // < left
    // > right
    // \/ facing away from you
    
    //SDS
    public static final BlockState[][][] SDS_STRUCTURE = {
            {
                    {CASING, CASING, CASING, CASING, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, CASING, CASING, CASING, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, null, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, CASING, CASING, CASING, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, CASING},
                    {CASING, CASING, CASING, CASING, CASING}
            }
    };
    //MDS
    public static final BlockState[][][] MDS_STRUCTURE = {
            {
                    {CASING, CASING, CASING, CASING, CASING, CASING, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, CASING, CASING, CASING, CASING, CASING, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, CASING, CASING, CASING, CASING, CASING, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, CASING, CASING, CASING, CASING, CASING, CASING}
            }
    };
    //MNS
    public static final BlockState[][][] MNS_STRUCTURE = {
            {
                    {null, null, CASING, CASING, CASING, null, null},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {null, null, CASING, CASING, CASING, null, null}
            },
            {
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {CASING, AIR, AIR, AIR, AIR, AIR, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, AIR, AIR, AIR, AIR, AIR, CASING},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, null, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING}
            },
            {
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {CASING, AIR, AIR, AIR, AIR, AIR, CASING},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {GLASS, AIR, AIR, AIR, AIR, AIR, GLASS},
                    {CASING, AIR, AIR, AIR, AIR, AIR, CASING},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null}
            },
            {
                    {null, null, CASING, CASING, CASING, null, null},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {CASING, GLASS, GLASS, GLASS, GLASS, GLASS, CASING},
                    {null, CASING, GLASS, GLASS, GLASS, CASING, null},
                    {null, null, CASING, CASING, CASING, null, null}
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
