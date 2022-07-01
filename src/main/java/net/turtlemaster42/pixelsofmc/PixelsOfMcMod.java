package net.turtlemaster42.pixelsofmc;

import net.minecraftforge.fml.DistExecutor;
import net.turtlemaster42.pixelsofmc.gui.screen.PixelSplitterGuiScreen;
import net.turtlemaster42.pixelsofmc.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import net.turtlemaster42.pixelsofmc.init.PixelsOfMcModRecipe;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;

@Mod(PixelsOfMcMod.MOD_ID)
public class PixelsOfMcMod {
    public static final String MOD_ID = "pixelsofmc";
	public static final Logger LOGGER = LogManager.getLogger(PixelsOfMcMod.class);
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	private static int messageID = 0;

	public PixelsOfMcMod() {
		PixelsOfMcModTabs.load();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		PixelsOfMcModBlocks.REGISTRY.register(bus);
		PixelsOfMcModItems.REGISTRY.register(bus);
		PixelsOfMcModMenuType.MENUS.register(bus);

		PixelsOfMcModRecipe.register(bus);
		
		PixelsOfMcModBlockEntities.REGISTRY.register(bus);

		bus.addListener(this::clientSetup);

	}
	
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(PixelsOfMcModBlocks.PIXEL_SPLITTER.get(), RenderType.cutout());

		MenuScreens.register(PixelsOfMcModMenuType.PIXEL_SPLITTER_MENU.get(), PixelSplitterGuiScreen::new);

    }

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
