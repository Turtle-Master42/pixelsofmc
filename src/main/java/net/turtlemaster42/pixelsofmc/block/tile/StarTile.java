package net.turtlemaster42.pixelsofmc.block.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.block.StarBlock;
import net.turtlemaster42.pixelsofmc.init.POMtiles;

public class StarTile extends BlockEntity {
    public int ticksExisted;
    public StarTile(BlockPos pPos, BlockState pBlockState) {
        super(POMtiles.STAR.get(), pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, StarTile e) {
        e.tick();
    }

    public static <E extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, StarTile e) {
        e.tick();
    }

    public void tick() {
        ticksExisted++;
    }
}
