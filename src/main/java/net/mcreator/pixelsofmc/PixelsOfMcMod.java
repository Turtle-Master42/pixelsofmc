/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and it won't get overwritten.
 *    If you change your modid or package, you need to apply these changes to this file MANUALLY.
 *
 *    Settings in @Mod annotation WON'T be changed in case of the base mod element
 *    files lock too, so you need to set them manually here in such case.
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package net.mcreator.pixelsofmc;

import net.mcreator.pixelsofmc.client.gui.PixelSplitterGuiScreen;
import net.mcreator.pixelsofmc.init.*;
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

import net.mcreator.pixelsofmc.recipe.ModRecipe;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;

@Mod(PixelsOfMcMod.MOD_ID)
public class PixelsOfMcMod {
    public static final String MOD_ID = "pixelsofmc";
	public static final Logger LOGGER = LogManager.getLogger(PixelsOfMcMod.class);
	public static final String MODID = "pixelsofmc";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public PixelsOfMcMod() {
		PixelsOfMcModTabs.load();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		PixelsOfMcModBlocks.REGISTRY.register(bus);
		PixelsOfMcModItems.REGISTRY.register(bus);
		PixelsOfMcModMenuType.MENUS.register(bus);

		ModRecipe.register(bus);
		
		PixelsOfMcModBlockEntities.REGISTRY.register(bus);
		PixelsOfMcModFeatures.REGISTRY.register(bus);

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
