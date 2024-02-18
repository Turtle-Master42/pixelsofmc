package net.turtlemaster42.pixelsofmc;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.turtlemaster42.pixelsofmc.entity.client.RiverShellRenderer;
import net.turtlemaster42.pixelsofmc.events.EventListener;
import net.turtlemaster42.pixelsofmc.fluid.POMFluidType;
import net.turtlemaster42.pixelsofmc.gui.screen.*;
import net.turtlemaster42.pixelsofmc.init.*;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.BallMillRenderer;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.PixelSplitterTileRenderer;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.StarRenderer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(PixelsOfMc.MOD_ID)
public class PixelsOfMc {
    public static final String MOD_ID = "pixelsofmc";
	public static final Logger LOGGER = LogUtils.getLogger();
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	private static int messageID = 0;

	public PixelsOfMc() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		POMblocks.register(bus);
		POMitems.register(bus);
		POMfluids.register(bus);
		POMentities.register(bus);
		POMFluidType.registerFluid(bus);
		POMmenuType.MENUS.register(bus);

		POMrecipes.register(bus);
		POMtags.register();
		
		POMtiles.TILES.register(bus);

		bus.addListener(this::clientSetup);
		bus.addListener(this::registerRenderers);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(new EventListener());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			POMmessages.register();
			setupBlockBehavior();
		});

		FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.HYDROGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.PACKED_ICE.defaultBlockState() : Blocks.ICE.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.NITROGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.PACKED_ICE.defaultBlockState() : Blocks.ICE.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.OXYGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.PACKED_ICE.defaultBlockState() : Blocks.ICE.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.CHLORINE_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.PACKED_ICE.defaultBlockState() : Blocks.ICE.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.BROMINE_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.PACKED_ICE.defaultBlockState() : Blocks.ICE.defaultBlockState()
				));


		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.HYDROGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.BASALT.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.NITROGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.BASALT.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.OXYGEN_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.BASALT.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.CHLORINE_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.BASALT.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(POMFluidType.BROMINE_FLUID_TYPE.get(),
						fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.BASALT.defaultBlockState()
				));

	}

    private void clientSetup(final FMLClientSetupEvent event) {

		ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROGEN_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROGEN_FLOWING.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.NITROGEN_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.NITROGEN_FLOWING.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.OXYGEN_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.OXYGEN_FLOWING.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.CHLORINE_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.CHLORINE_FLOWING.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.BROMINE_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.BROMINE_FLOWING.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.SULFURIC_ACID_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.SULFURIC_ACID_FLOWING.get(), RenderType.translucent());

		MenuScreens.register(POMmenuType.PIXEL_SPLITTER_MENU.get(), PixelSplitterGuiScreen::new);
		MenuScreens.register(POMmenuType.PIXEL_ASSEMBLER_MENU.get(), PixelAssemblerGuiScreen::new);
		MenuScreens.register(POMmenuType.BALL_MILL_MENU.get(), BallMillGuiScreen::new);
		MenuScreens.register(POMmenuType.GRINDER_MENU.get(), GrinderGuiScreen::new);
		MenuScreens.register(POMmenuType.HOT_ISOTOPIC_PRESS_MENU.get(), HotIsostaticPressScreen::new);
		MenuScreens.register(POMmenuType.CHEMICAL_SEPARATOR_MENU.get(), ChemicalSeparatorScreen::new);
		MenuScreens.register(POMmenuType.CHEMICAL_COMBINER_MENU.get(), ChemicalCombinerScreen::new);
		MenuScreens.register(POMmenuType.SDS_CONTROLLER_MENU.get(), SDSFusionControllerGuiScreen::new);

		EntityRenderers.register(POMentities.RIVER_SHELL.get(), RiverShellRenderer::new);

		ItemProperties.register(POMitems.POWER_CELL.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "empty"), (stack, world, entity, seed) -> {
			IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
			return energy.getEnergyStored() <= 0 ? 1 : 0;
		});
		ItemProperties.register(POMitems.OVERCHARGED_POWER_CELL.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "empty"), (stack, world, entity, seed) -> {
			IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
			return energy.getEnergyStored() <= 0 ? 1 : 0;
		});
		ItemProperties.register(POMitems.SUPERCHARGED_POWER_CELL.get(), new ResourceLocation(PixelsOfMc.MOD_ID, "empty"), (stack, world, entity, seed) -> {
			IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
			return energy.getEnergyStored() <= 0 ? 1 : 0;
		});
    }

	public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		//BlockEntityRenderers.register(POMtiles.HOT_ISOSTATIC_PRESS.get(), HotIsostaticPressRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.PIXEL_SPLITTER.get(), PixelSplitterTileRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.STAR.get(), StarRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.BALL_MILL.get(), BallMillRenderer::new);
	}

	public void setupBlockBehavior() {
		//Credits The Undergarden
		DispenseItemBehavior bucketBehavior = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
			public @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
				BucketItem bucketitem = (BucketItem) stack.getItem();
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level world = source.getLevel();
				if (bucketitem.emptyContents(null, world, blockpos, null)) {
					bucketitem.checkExtraContent(null, world, stack, blockpos);
					return new ItemStack(Items.BUCKET);
				} else {
					return this.defaultBehavior.dispense(source, stack);
				}
			}
		};

		DispenseItemBehavior energyCell = new DefaultDispenseItemBehavior() {
			public @NotNull ItemStack execute(BlockSource source, @NotNull ItemStack stack) {
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				BlockState state = source.getLevel().getBlockState(blockpos);
				BlockEntity entity = source.getLevel().getBlockEntity(blockpos);
				if (entity != null) {
					IEnergyStorage EnergyHandlerFrom = entity.getCapability(ForgeCapabilities.ENERGY, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite()).orElse(null);
					if (EnergyHandlerFrom != null) {
						int maxReceive = 10000;

						if (EnergyHandlerFrom.canReceive()) {
							EnergyHandlerFrom.receiveEnergy(maxReceive, false);
							stack.shrink(1);
						}
						return stack;
					}
				}
				if (state.canBeReplaced()) {
					source.getLevel().sendParticles(ParticleTypes.SOUL, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 50, 0.5f, 0.5f, 0.5f, 0.01);
					stack.shrink(1);
					return stack;
				}
				return stack;
			}
		};

		DispenseItemBehavior energyCell2 = new DefaultDispenseItemBehavior() {
			public @NotNull ItemStack execute(BlockSource source, @NotNull ItemStack stack) {
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				BlockState state = source.getLevel().getBlockState(blockpos);
				BlockEntity entity = source.getLevel().getBlockEntity(blockpos);
				if (entity != null) {
					IEnergyStorage EnergyHandlerFrom = entity.getCapability(ForgeCapabilities.ENERGY, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite()).orElse(null);
					if (EnergyHandlerFrom != null) {
						int maxReceive = 90000;

						if (EnergyHandlerFrom.canReceive()) {
							EnergyHandlerFrom.receiveEnergy(maxReceive, false);
							stack.shrink(1);
						}
						return stack;
					}
				}
				if (state.canBeReplaced()) {
					source.getLevel().sendParticles(ParticleTypes.SOUL, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 150, 1.5f, 1.5f, 1.5f, 0.01);
					stack.shrink(1);
					return stack;
				}
				return stack;
			}
		};

		DispenseItemBehavior energyCell3 = new DefaultDispenseItemBehavior() {
			public @NotNull ItemStack execute(BlockSource source, @NotNull ItemStack stack) {
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				BlockState state = source.getLevel().getBlockState(blockpos);
				BlockEntity entity = source.getLevel().getBlockEntity(blockpos);
				if (entity != null) {
					IEnergyStorage EnergyHandlerFrom = entity.getCapability(ForgeCapabilities.ENERGY, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite()).orElse(null);
					if (EnergyHandlerFrom != null) {
						int maxReceive = 810000;

						if (EnergyHandlerFrom.canReceive()) {
							EnergyHandlerFrom.receiveEnergy(maxReceive, false);
							stack.shrink(1);
						}
						return stack;
					}
				}
				if (state.canBeReplaced()) {
					source.getLevel().sendParticles(ParticleTypes.SOUL, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 450, 2.5f, 2.5f, 2.5f, 0.01);
					stack.shrink(1);
					return stack;
				}
				return stack;
			}
		};

		DispenserBlock.registerBehavior(POMitems.LIQUID_HYDROGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_NITROGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_OXYGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_CHLORINE_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_BROMINE_BUCKET.get(), bucketBehavior);

		DispenserBlock.registerBehavior(POMitems.MERCURY_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.SULFURIC_ACID_BUCKET.get(), bucketBehavior);

		DispenserBlock.registerBehavior(POMitems.POWER_CELL.get(), energyCell);
		DispenserBlock.registerBehavior(POMitems.OVERCHARGED_POWER_CELL.get(),  energyCell2);
		DispenserBlock.registerBehavior(POMitems.SUPERCHARGED_POWER_CELL.get(), energyCell3);
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
