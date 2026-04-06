package net.turtlemaster42.pixelsofmc;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.turtlemaster42.pixelsofmc.events.EventListener;
import net.turtlemaster42.pixelsofmc.fluid.POMFluidType;
import net.turtlemaster42.pixelsofmc.init.*;
import net.turtlemaster42.pixelsofmc.item.BigBucket;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.LiquidBlock.LEVEL;

@Mod(PixelsOfMc.MOD_ID)
public class PixelsOfMc {
    public static final String MOD_ID = "pixelsofmc";
	public static final Logger LOGGER = LogUtils.getLogger();
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(Util.resourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public static POMCommonProxy PROXY = DistExecutor.safeRunForDist(() -> POMClientProxy::new, () -> POMCommonProxy::new);
	private static int messageID = 0;

	@SuppressWarnings("marked for removal")
	public PixelsOfMc() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		POMblocks.register(bus);
		POMitems.register(bus);
		POMfluids.register(bus);
		POMentities.register(bus);
		POMFluidType.register(bus);
		POMfeature.register(bus);
		POMmenus.register(bus);
		POMtabs.register(bus);
		POMrecipes.register(bus);
		POMparticles.register(bus);
		POMtags.register();
		POMtiles.register(bus);
		POMpotions.register(bus);

		bus.addListener(POMClientProxy::clientSetup);
		bus.addListener(POMClientProxy::registerTileRenderer);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(new EventListener());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			POMmessages.register();
			setupBlockBehavior();
			POMpotions.addMixes();
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

		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(
                        (level, currentPos, relativePos, currentState) -> level.getFluidState(relativePos).getFluidType() == POMFluidType.RED_OIL_FLUID_TYPE.get(),
						(level, currentPos, relativePos, currentState) -> {
							level.setBlockAndUpdate(currentPos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, relativePos, relativePos, Blocks.AIR.defaultBlockState()));
							level.levelEvent(1501, currentPos, 0);
							if (!level.isClientSide)
								level.explode(null, relativePos.getX(), relativePos.getY(), relativePos.getZ(), 1.5f, Level.ExplosionInteraction.TNT);
						})
		);

		FluidInteractionRegistry.addInteraction(POMFluidType.RED_OIL_FLUID_TYPE.get(),
				new FluidInteractionRegistry.InteractionInformation(
						(level, currentPos, relativePos, currentState) -> level.getBlockState(relativePos).getBlock() instanceof BaseFireBlock,
						(level, currentPos, relativePos, currentState) -> {
							level.setBlockAndUpdate(currentPos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, currentPos, currentPos, Blocks.AIR.defaultBlockState()));
							level.levelEvent(1501, currentPos, 0);
							if (!level.isClientSide)
								level.explode(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1.5f, Level.ExplosionInteraction.TNT);
						})
		);

	}

	public void setupBlockBehavior() {
		PixelsOfMc.LOGGER.info("Setting up Dispenser Behavior");
		DispenseItemBehavior bucketBehavior = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
			public @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
				BucketItem bucketitem = (BucketItem) stack.getItem();
				BlockPos relativePos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level world = source.getLevel();
				BlockEntity tile = source.getLevel().getBlockEntity(relativePos);
				if (tile != null) {
					IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, source.getBlockState().getValue(DispenserBlock.FACING)).orElse(null);
					if (fluidHandlerFrom != null) {
						if (fluidHandlerFrom.getTankCapacity(0) - fluidHandlerFrom.getFluidInTank(0).getAmount() >= 1000) {
							fluidHandlerFrom.fill(new FluidStack(bucketitem.getFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);
							return new ItemStack(Items.BUCKET);
						}
					}
					return stack;
				} else if (bucketitem.emptyContents(null, world, relativePos, null)) {
					bucketitem.checkExtraContent(null, world, stack, relativePos);
					return new ItemStack(Items.BUCKET);
				} else {
					return this.defaultBehavior.dispense(source, stack);
				}
			}
		};

		DispenseItemBehavior energyCell = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
			public @NotNull ItemStack execute(BlockSource source, @NotNull ItemStack stack) {
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				BlockEntity entity = source.getLevel().getBlockEntity(blockpos);
				if (entity != null) {
					IEnergyStorage energyHandlerFrom = entity.getCapability(ForgeCapabilities.ENERGY, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite()).orElse(null);
					IEnergyStorage powerCellHandler = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
					if (energyHandlerFrom != null && powerCellHandler != null) {
						if (energyHandlerFrom.canReceive()) {
							int received =  energyHandlerFrom.receiveEnergy(powerCellHandler.getEnergyStored(), false);
							powerCellHandler.extractEnergy(received, false);
						}
						return stack;
					}
				}
				//TODO:IMPLEMENT ON A LATER DAY
                // if (state.canBeReplaced()) {
                //source.getLevel().sendParticles(POMparticles.ELECTRIC_SPARK.get(), blockpos.getX(), blockpos.getY(), blockpos.getZ(), 50, 0.5f, 0.5f, 0.5f, 0.01);
                //return stack;
                //}
				return defaultBehavior.dispense(source, stack);
			}
		};

		DispenseItemBehavior titaniumBucket = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
			public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack inputStack) {
				if (inputStack.getItem() instanceof BigBucket bucket) {
					ItemStack stack = inputStack.copyWithCount(1);
					BlockPos relativePos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
					BlockState state = source.getLevel().getBlockState(relativePos);
					BlockEntity tile = source.getLevel().getBlockEntity(relativePos);
					Level level = source.getLevel();

					IFluidHandlerItem bucketCapability = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
					FluidStack bucketFluid = bucketCapability.getFluidInTank(0);

					if (bucketFluid.isEmpty()) {
						// fill bucket
						if (state.getBlock() instanceof LiquidBlock liquidBlock && state.getValue(LEVEL) == 0) {
							level.setBlock(relativePos, Blocks.AIR.defaultBlockState(), 11);
							bucketCapability.fill(new FluidStack(liquidBlock.getFluid().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE);
//								liquidBlock.getPickupSound(state).ifPresent((p_150709_) -> pPlayer.playSound(p_150709_, 1.0F, 1.0F));
							inputStack.shrink(1);
							if (inputStack.isEmpty()) {
								return stack;
							} else {
								if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
									this.defaultBehavior.dispense(source, stack);
								}
								return inputStack;
							}
						}

						if (tile != null) {
							IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, source.getBlockState().getValue(DispenserBlock.FACING)).orElse(null);
							if (fluidHandlerFrom != null) {
								int maxFill = bucketCapability.fill(fluidHandlerFrom.getFluidInTank(0), IFluidHandler.FluidAction.SIMULATE);
								bucketCapability.fill(fluidHandlerFrom.drain(maxFill, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
							}
							inputStack.shrink(1);
							if (inputStack.isEmpty()) {
								return stack;
							} else {
								if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
									this.defaultBehavior.dispense(source, stack);
								}
								return inputStack;
							}
						}
					} else {
						// empty bucket
						if (tile != null) {
							IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, source.getBlockState().getValue(DispenserBlock.FACING)).orElse(null);
							if (fluidHandlerFrom != null) {
								int maxFill = fluidHandlerFrom.fill(bucketCapability.getFluidInTank(0), IFluidHandler.FluidAction.SIMULATE);
								fluidHandlerFrom.fill(bucketCapability.drain(maxFill, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
							}
							return stack;
						}
						if (bucket.emptyContents(null, level, relativePos, null, stack)) {
							bucketCapability.drain(1000, IFluidHandler.FluidAction.EXECUTE);
							return stack;
						}
					}
				}
				return this.defaultBehavior.dispense(source, inputStack);
			}
		};

		DispenseItemBehavior reinforcedBucket = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();
			public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack inputStack) {
				if (inputStack.getItem() instanceof BigBucket bucket) {
					ItemStack stack = inputStack.copyWithCount(1);
					BlockPos relativePos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
					BlockState state = source.getLevel().getBlockState(relativePos);
					BlockEntity tile = source.getLevel().getBlockEntity(relativePos);
					Level level = source.getLevel();

					IFluidHandlerItem bucketCapability = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
					FluidStack fluid = bucketCapability.getFluidInTank(0);
					//pickup
					if (fluid.isEmpty() || fluid.getAmount() <= 3000) {
						if (state.getBlock() instanceof LiquidBlock liquidBlock) {
							if ((liquidBlock.getFluid().getSource().equals(fluid.getRawFluid()) || fluid.isEmpty()) && state.getValue(LEVEL) == 0) {
								level.setBlock(relativePos, Blocks.AIR.defaultBlockState(), 11);

								if (liquidBlock.getFluid().canConvertToSource(liquidBlock.getFluidState(state), level, relativePos)) {
									bucketCapability.fill(new FluidStack(liquidBlock.getFluid().getSource(), 4000), IFluidHandler.FluidAction.EXECUTE);
								} else {
									bucketCapability.fill(new FluidStack(liquidBlock.getFluid().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE);
								}
//								liquidBlock.getPickupSound(state).ifPresent((p_150709_) -> pPlayer.playSound(p_150709_, 1.0F, 1.0F));

								inputStack.shrink(1);
								if (inputStack.isEmpty()) {
									return stack;
								} else {
									if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
										this.defaultBehavior.dispense(source, stack);
									}
									return inputStack;
								}
                            }
						}

						if (tile != null) {
							IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, source.getBlockState().getValue(DispenserBlock.FACING)).orElse(null);
							if (fluidHandlerFrom != null) {
								int maxFill = bucketCapability.fill(fluidHandlerFrom.getFluidInTank(0), IFluidHandler.FluidAction.SIMULATE);
								bucketCapability.fill(fluidHandlerFrom.drain(maxFill, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
							}
							inputStack.shrink(1);
							if (inputStack.isEmpty()) {
								return stack;
							} else {
								if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
									this.defaultBehavior.dispense(source, stack);
								}
								return inputStack;
							}
						}
					}
					//empty
					if (!fluid.isEmpty()) {
						if (tile != null) {
							IFluidHandler fluidHandlerFrom = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, source.getBlockState().getValue(DispenserBlock.FACING)).orElse(null);
							if (fluidHandlerFrom != null) {
								int maxFill = fluidHandlerFrom.fill(bucketCapability.getFluidInTank(0), IFluidHandler.FluidAction.SIMULATE);
								fluidHandlerFrom.fill(bucketCapability.drain(maxFill, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
							}
							return stack;
						}
						if (bucket.emptyContents(null, level, relativePos, null, stack)) {
							bucketCapability.drain(1000, IFluidHandler.FluidAction.EXECUTE);
							return stack;
						}
					}
				}
				return this.defaultBehavior.dispense(source, inputStack);
			}
		};

        //BUCKETS
		DispenserBlock.registerBehavior(POMitems.LIQUID_HYDROGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_NITROGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_OXYGEN_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_CHLORINE_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_BROMINE_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.MERCURY_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.SULFURIC_ACID_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.NITRIC_ACID_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.RED_OIL_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.NUCLEAR_WASTE_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.NUCLEAR_WASTE_SOLUTION_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.PUREX_SOLUTION_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.URANIUM_SOLUTION_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.PLUTONIUM_SOLUTION_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.LIQUID_LEAD_BUCKET.get(), bucketBehavior);
		DispenserBlock.registerBehavior(POMitems.DIRTY_WATER_BUCKET.get(), bucketBehavior);

		DispenserBlock.registerBehavior(POMitems.POWER_CELL.get(), energyCell);
		DispenserBlock.registerBehavior(POMitems.OVERCHARGED_POWER_CELL.get(),  energyCell);
		DispenserBlock.registerBehavior(POMitems.SUPERCHARGED_POWER_CELL.get(), energyCell);
		DispenserBlock.registerBehavior(POMitems.TITANIUM_BUCKET.get(), titaniumBucket);
		DispenserBlock.registerBehavior(POMitems.REINFORCED_BUCKET.get(), reinforcedBucket);
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
