package net.turtlemaster42.pixelsofmc.block.entity;

import net.turtlemaster42.pixelsofmc.PixelsOfMcMod;
import net.turtlemaster42.pixelsofmc.init.PixelsOfMcModBlockEntities;
import net.turtlemaster42.pixelsofmc.init.PixelsOfMcModItems;
import net.turtlemaster42.pixelsofmc.network.PixelEnergyStorage;
import net.turtlemaster42.pixelsofmc.recipe.PixelSplitterRecipe;
import net.turtlemaster42.pixelsofmc.gui.menu.PixelSplitterGuiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;


import static net.minecraft.core.particles.ParticleTypes.*;

public class MachineBlockBlockEntity extends BlockEntity {
	protected final ContainerData data;


	public BlockPos mainPos = null;

	public MachineBlockBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(PixelsOfMcModBlockEntities.MACHINE_BLOCK.get(), pWorldPosition, pBlockState);
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


	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void invalidateCaps()  {
		super.invalidateCaps();
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PixelSplitterBlockEntity pBlockEntity) {

	}

}
