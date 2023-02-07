package net.turtlemaster42.pixelsofmc;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.turtlemaster42.pixelsofmc.gui.screen.*;
import net.turtlemaster42.pixelsofmc.init.*;
import net.turtlemaster42.pixelsofmc.init.POMrecipes;
import net.turtlemaster42.pixelsofmc.util.renderer.FusionCoreRenderer;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.PixelSplitterTileRenderer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;

@Mod(PixelsOfMc.MOD_ID)
public class PixelsOfMc {
    public static final String MOD_ID = "pixelsofmc";
	public static final Logger LOGGER = LogManager.getLogger(PixelsOfMc.class);
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	private static int messageID = 0;

	public PixelsOfMc() {
		POMtabs.load();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		POMblocks.BLOCKS.register(bus);
		POMitems.register(bus);
		POMfluids.register(bus);
		POMmenuType.MENUS.register(bus);

		POMrecipes.register(bus);
		POMtags.register();
		
		POMtiles.TILES.register(bus);

		bus.addListener(this::clientSetup);
		bus.addListener(this::registerRenderers);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		POMmessages.register();
	}

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(POMblocks.PIXEL_SPLITTER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(POMblocks.FUSION_CORE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_BLOCK.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_FLUID.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(POMfluids.MERCURY_FLOWING.get(), RenderType.translucent());


		MenuScreens.register(POMmenuType.PIXEL_SPLITTER_MENU.get(), PixelSplitterGuiScreen::new);
		MenuScreens.register(POMmenuType.BALL_MILL_MENU.get(), BallMillGuiScreen::new);
		MenuScreens.register(POMmenuType.GRINDER_MENU.get(), GrinderGuiScreen::new);
		MenuScreens.register(POMmenuType.HOT_ISOTOPIC_PRESS_MENU.get(), HotIsostaticPressScreen::new);
		MenuScreens.register(POMmenuType.CHEMICAL_SEPERATOR_MENU.get(), ChemicalSeperatorScreen::new);
		MenuScreens.register(POMmenuType.SDS_CONTROLLER_MENU.get(), SDSFusionControllerGuiScreen::new);
    }

	public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		//BlockEntityRenderers.register(POMtiles.HOT_ISOSTATIC_PRESS.get(), RendererHotIsostaticPress::new);
		event.registerBlockEntityRenderer(POMtiles.PIXEL_SPLITTER.get(), PixelSplitterTileRenderer::new);
		event.registerBlockEntityRenderer(POMtiles.SDS_CONTROLLER.get(), FusionCoreRenderer::new);
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
