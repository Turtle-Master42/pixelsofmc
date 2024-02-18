package net.turtlemaster42.pixelsofmc.block.dummy.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.turtlemaster42.pixelsofmc.init.POMitems;
import net.turtlemaster42.pixelsofmc.init.POMtiles;
import org.joml.Vector3f;

import java.util.Random;

public class DummyMachineBlockTile extends AbstractDummyMachineBlockTile {
	protected final ContainerData data;

	public DummyMachineBlockTile(BlockPos pWorldPosition, BlockState pBlockState) {
		super(POMtiles.MACHINE_BLOCK.get(), pWorldPosition, pBlockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return 0;
			}
			@Override
			public void set(int pIndex, int pValue) {
			}
			@Override
			public int getCount() {
				return 0;
			}
		};
	}
}
