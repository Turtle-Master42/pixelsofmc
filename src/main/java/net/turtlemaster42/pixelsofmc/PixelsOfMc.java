package net.turtlemaster42.pixelsofmc;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.turtlemaster42.pixelsofmc.fluid.POMFluidType;
import net.turtlemaster42.pixelsofmc.gui.screen.*;
import net.turtlemaster42.pixelsofmc.init.*;
import net.turtlemaster42.pixelsofmc.util.Element;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.PixelSplitterTileRenderer;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.StarRenderer;
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
		POMFluidType.register(bus);
		POMmenuType.MENUS.register(bus);

		POMrecipes.register(bus);
		POMtags.register();
		
		POMtiles.TILES.register(bus);

		bus.addListener(this::clientSetup);
		bus.addListener(this::registerRenderers);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::addCreative);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			POMmessages.register();
		});
	}

    private void clientSetup(final FMLClientSetupEvent event) {
        //ItemBlockRenderTypes.setRenderLayer(POMblocks.PIXEL_SPLITTER.get(), RenderType.cutout());
		//ItemBlockRenderTypes.setRenderLayer(POMblocks.FUSION_CORE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_BLOCK.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_SOURCE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_FLOWING.get(), RenderType.translucent());


		MenuScreens.register(POMmenuType.PIXEL_SPLITTER_MENU.get(), PixelSplitterGuiScreen::new);
		MenuScreens.register(POMmenuType.BALL_MILL_MENU.get(), BallMillGuiScreen::new);
		MenuScreens.register(POMmenuType.GRINDER_MENU.get(), GrinderGuiScreen::new);
		MenuScreens.register(POMmenuType.HOT_ISOTOPIC_PRESS_MENU.get(), HotIsostaticPressScreen::new);
		MenuScreens.register(POMmenuType.CHEMICAL_SEPERATOR_MENU.get(), ChemicalSeperatorScreen::new);
		MenuScreens.register(POMmenuType.SDS_CONTROLLER_MENU.get(), SDSFusionControllerGuiScreen::new);
    }

	public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		//BlockEntityRenderers.register(POMtiles.HOT_ISOSTATIC_PRESS.get(), HotIsostaticPressRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.PIXEL_SPLITTER.get(), PixelSplitterTileRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.STAR.get(), StarRenderer::new);
	}

	private void addCreative(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.OP_BLOCKS && event.hasPermissions())
			event.accept(POMitems.DEBUGUIM_INGOT);

		if (event.getTab() == POMtabs.PIXELS_OF_MINECRAFT_TAB) {
			event.accept(POMitems.BOOK_1);

			if (event.hasPermissions()) {
				event.accept(POMitems.DEBUGUIM_INGOT);
				event.accept(POMitems.TEST_ITEM);
				event.accept(POMblocks.STAR);
			}

			event.accept(POMblocks.SIMPLE_CASING_1);
			event.accept(POMblocks.ADVANCED_CASING_1);
			event.accept(POMblocks.PERFECTED_CASING_1);
			event.accept(POMblocks.STRONG_CASING);
			event.accept(POMblocks.REINFORCED_CASING);

			event.accept(POMblocks.ENDSTONE_TITANIUM_ORE);
			event.accept(POMblocks.ACANTHITE);
			event.accept(POMblocks.SAND_MINERAL_DEPOSIT);

			for(Element m : Element.values()) {
				if (m.shouldAddBlock())
					event.accept(m.block());
			}
			event.accept(POMblocks.TITANIUM_DIBORIDE_BLOCK);

			event.accept(POMblocks.ALUMINIUM_SCRAP_BLOCK);
			event.accept(POMblocks.RAW_TITANIUM_BLOCK);
			event.accept(POMblocks.COPPER_SPOOL);
			event.accept(POMblocks.SILVER_SPOOL);
			event.accept(POMblocks.TUNGSTEN_SPOOL);

			event.accept(POMblocks.BALL_MILL);
			event.accept(POMblocks.GRINDER);
			event.accept(POMblocks.HOT_ISOSTATIC_PRESS);
			event.accept(POMblocks.CHEMICAL_SEPERATOR);
			event.accept(POMblocks.PIXEL_SPLITTER);
			event.accept(POMblocks.SDS_CONTROLLER);

			event.accept(POMitems.SPEED_UPGRADE);
			event.accept(POMitems.ENERGY_UPGRADE);
			event.accept(POMitems.HEAT_UPGRADE);

			event.accept(POMitems.CLEANING_CLOTH);
			event.accept(POMitems.SCREWDRIVER);
			event.accept(POMitems.WIRECUTTER);
			event.accept(POMitems.HAMMER);
			event.accept(POMitems.CLEANING_SPONGE);

			event.accept(POMitems.RUBBER_BALL);
			event.accept(POMitems.FIRE_PROOF_RUBBER_BALL);
			event.accept(POMitems.REPELLING_RUBBER_BALL);
			event.accept(POMitems.NETHERITE_BALL);
			event.accept(POMitems.TITANIUM_BALL);
			event.accept(POMitems.TITANIUM_DIBORIDE_BALL);

			event.accept(POMitems.TITANIUM_CIRCLE_SAW);
			event.accept(POMitems.TITANIUM_DIBORIDE_CIRCLE_SAW);

			event.accept(POMitems.INGOT_CAST);
			event.accept(POMitems.BALL_CAST);
			event.accept(POMitems.PLATE_CAST);

			event.accept(POMitems.SIMPLE_CIRCUIT_BOARD_1);
			event.accept(POMitems.ADVANCED_CIRCUIT_BOARD_1);
			event.accept(POMitems.PERFECTED_CIRCUIT_BOARD_1);

			event.accept(POMitems.MOVING_PARTS);
			event.accept(POMitems.POWER_CELL);
			event.accept(POMitems.ADVANCED_LASER);

			event.accept(POMitems.COPPER_WIRE);
			event.accept(POMitems.SILVER_WIRE);
			event.accept(POMitems.TUNGSTEN_WIRE);
			event.accept(POMitems.REDSTONE_LAYERED_COPPER_WIRE);
			event.accept(POMitems.REDSTONE_IMBUED_SILVER_WIRE);
			event.accept(POMitems.RED_TUNGSTEN_WIRE);
			event.accept(POMitems.POWER_ORB);
			event.accept(POMitems.MICRO_CHIP);
			event.accept(POMitems.REDSTONE_COUNTER);
			event.accept(POMitems.DRAGON_EYE);
			event.accept(POMitems.VOID_EYE);
			event.accept(POMitems.ENDER_SENSOR);
			event.accept(POMitems.DRAGON_SENSOR);
			event.accept(POMitems.VOID_SENSOR);
			event.accept(POMitems.DIAMOND_LENS);
			event.accept(POMitems.VIOLET_DIAMOND_LENS);
			event.accept(POMitems.RED_DIAMOND_LENS);

			event.accept(POMitems.PIXEL);
			event.accept(POMitems.PIXEL_PILE);

			event.accept(POMitems.DENSE_CARBON_CUBE);
			event.accept(POMitems.CARBONARO_CLUMP);
			event.accept(POMitems.BLACK_DIAMOND);
			event.accept(POMitems.VIOLET_DIAMOND);
			event.accept(POMitems.RED_DIAMOND);

			event.accept(POMitems.BIO_COMPOUND);
			event.accept(POMitems.BIO_PLASTIC);
			event.accept(POMitems.FIRE_PROOF_COMPOUND);
			event.accept(POMitems.FIRE_PROOF_PLASTIC);
			event.accept(POMitems.REPELLING_COMPOUND);
			event.accept(POMitems.REPELLING_PLASTIC);

			event.accept(POMitems.RUSTED_PLATING);
			event.accept(POMitems.NETHERITE_PLATING);
			event.accept(POMitems.TITANIUM_PLATING);
			event.accept(POMitems.TITANIUM_DIBORIDE_PLATING);
			event.accept(POMitems.OBSIDIAN_PLATING);
			event.accept(POMitems.CRYING_OBSIDIAN_PLATING);

			event.accept(POMitems.ALUMINIUM_SCRAP);
			event.accept(POMitems.SULFUR);
			event.accept(POMitems.RAW_TITANIUM);

			event.accept(POMitems.NETHERITE_NUGGET);
			event.accept(POMitems.Metals.NUGGETS.get(Element.TITANIUM).get());
			event.accept(POMitems.TITANIUM_DIBORIDE_NUGGET);
			for(Element m : Element.values()) {
				if (m.shouldAddNugget())
					event.accept(m.nugget());
			}

			event.accept(POMitems.Metals.ELEMENTS.get(Element.TITANIUM).get());
			event.accept(POMitems.TITANIUM_DIBORIDE_INGOT);

			for(Element m : Element.values()) {
				if (!m.isVanilla() && m != Element.DEBUGIUM)
					event.accept(m.item());
			}

			event.accept(POMitems.ANCIENT_DEBRIS_DUST);
			event.accept(POMitems.NETHERITE_DUST);
			event.accept(POMitems.TITANIUM_OXIDE_DUST);
			event.accept(POMitems.TITANIUM_DIBORIDE_DUST);

			for(Element m : Element.values()) {
				if (m.shouldAddDust())
					event.accept(m.dust());
			}

			event.accept(POMitems.MERCURY_SULFIDE_DUST);
			event.accept(POMitems.ACANTHITE_DUST);
			event.accept(POMitems.OBSIDIAN_DUST);
			event.accept(POMitems.CRYING_OBSIDIAN_DUST);
			event.accept(POMitems.MINERAL_GRIT);
			event.accept(POMitems.COAL_DUST);

			event.accept(POMitems.MERCURY_BUCKET);
		}

		if (event.getTab() == POMtabs.ATOM_TAB) {
			for(Element m : Element.values()) {
				if (m.equals(Element.DEBUGIUM)) return;
				event.accept(POMitems.Metals.ATOMX64.get(m).asItem());
				event.accept(POMitems.Metals.ATOMX512.get(m).asItem());
			}
		}
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
