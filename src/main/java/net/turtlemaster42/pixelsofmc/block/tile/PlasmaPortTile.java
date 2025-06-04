package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import org.jetbrains.annotations.NotNull;

public class PlasmaPortTile extends AbstractMultiBlockTile {

//    protected final ContainerData data;

    public PlasmaPortTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(POMtiles.ENERGY_PORT.get(), pWorldPosition, pBlockState);
//        this.data = new ContainerData() {
//
//            @Override
//            public int get(int index) {
//                if (index == 0) {
//                    return FusionPlasmaPortTile.this.energyStorage.getEnergyStored();
//                }
//                return 0;
//            }
//
//            @Override
//            public void set(int index, int pValue) {
//
//            }
//
//            @Override
//            public int getCount() {
//                return 1;
//            }
//        };
    }



    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
//        tag.putInt("powerCapacity", capacity);
//        tag.putInt("Energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
//        energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, PlasmaPortTile e) {
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, PlasmaPortTile e) {
//        if (blockState.getValue(FusionPlasmaPortBlock.PUSHING)) {
//            BlockPos facingPos = BigMachineBlockUtil.rotateBlockPosOnDirection(blockState.getValue(FusionPlasmaPortBlock.PUSH_DIRECTION), 0, 0, 1, blockPos);
//            BlockState facingState = level.getBlockState(facingPos);
//            if (((facingState.getBlock().equals(Blocks.AIR) || facingState.getBlock().equals(Blocks.CAVE_AIR)))) {
//                Vector3f centerVec = new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
//                Vector3f posVec = rotatedVecPos(blockState.getValue(FusionPlasmaPortBlock.PUSH_DIRECTION), centerVec, 0, 0, 0.6f);
//                level.addParticle(POMparticles.ELECTRIC_SPARK.get(), posVec.x + (Math.random() - 0.5) / 2, posVec.y + (Math.random() - 0.5) / 2, posVec.z + (Math.random() - 0.5) / 2, 0, 0, 0);
//            }
//        }
    }
}
