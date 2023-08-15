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
	private static final ParticleOptions DUST_COLOR = new DustParticleOptions(Vec3.fromRGB24(-1).toVector3f(), 2f);
	private static final ParticleOptions DUST = new DustParticleOptions(new Vector3f(0, 1, 1), 2f);
	private static final ParticleOptions PARTICLE = ParticleTypes.CLOUD;
	private static final Vector3f DUST_COLOR_green = Vec3.fromRGB24(-16711811).toVector3f();
	private static final Vector3f DUST_COLOR_blue = Vec3.fromRGB24(-16711681).toVector3f();

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

	public static void particleTick(Level pLevel, BlockPos pPos, BlockState pState, DummyMachineBlockTile pBlockEntity) {
		Random pRandom = new Random();
		Minecraft instance = Minecraft.getInstance();
		if (pRandom.nextInt(20) == 0) {
			Player pPlayer = instance.player;
			if (pPlayer.getMainHandItem().is(POMitems.DEBUGIUM_INGOT.get())) {
				pLevel.addParticle(DUST,
						pPos.getX() + pRandom.nextInt(0, 2),
						pPos.getY() + pRandom.nextInt(0, 2),
						pPos.getZ() + pRandom.nextInt(0, 2),
						0, 0, 0);
			}
		}
	}
}
