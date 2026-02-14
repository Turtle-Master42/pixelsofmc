package net.turtlemaster42.pixelsofmc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.turtlemaster42.pixelsofmc.entity.client.RiverShellRenderer;
import net.turtlemaster42.pixelsofmc.gui.book.GuiBook1;
import net.turtlemaster42.pixelsofmc.init.*;
import net.turtlemaster42.pixelsofmc.item.BigBucket;
import net.turtlemaster42.pixelsofmc.item.PixelItem;
import net.turtlemaster42.pixelsofmc.particle.*;
import net.turtlemaster42.pixelsofmc.util.Util;
import net.turtlemaster42.pixelsofmc.util.renderer.block.tile.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = PixelsOfMc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class POMClientProxy extends POMCommonProxy {

    public void openBookGUI(ItemStack itemStackIn) {
        Minecraft.getInstance().setScreen(new GuiBook1(itemStackIn));
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
        Minecraft.getInstance().setScreen(new GuiBook1(itemStackIn, page));
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        registerRenderLayers();
        registerEntityRenderer();
        POMmenus.registerMenuScreens();


        ItemProperties.register(POMitems.POWER_CELL.get(), Util.resourceLocation("empty"), (stack, world, entity, seed) -> {
            IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            return energy.getEnergyStored() <= 0 ? 1 : 0;
        });
        ItemProperties.register(POMitems.OVERCHARGED_POWER_CELL.get(), Util.resourceLocation("empty"), (stack, world, entity, seed) -> {
            IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            return energy.getEnergyStored() <= 0 ? 1 : 0;
        });
        ItemProperties.register(POMitems.SUPERCHARGED_POWER_CELL.get(), Util.resourceLocation("empty"), (stack, world, entity, seed) -> {
            IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            return energy.getEnergyStored() <= 0 ? 1 : 0;
        });
        ItemProperties.register(POMitems.TITANIUM_BUCKET.get(), Util.resourceLocation("bucket_state"), (stack, world, entity, seed) -> {
            FluidStack fluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null).getFluidInTank(0);
            if (fluid.isEmpty()) return 0;
            else if (fluid.getFluid().isSame(Fluids.WATER)) return 3;
            else if (fluid.getFluid().isSame(Fluids.LAVA)) return 4;
            else if (fluid.getFluid().getFluidType().isLighterThanAir()) return 2;
            else return 1;
        });
        ItemProperties.register(POMitems.REINFORCED_BUCKET.get(), Util.resourceLocation("bucket_state"), (stack, world, entity, seed) -> {
            FluidStack fluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null).getFluidInTank(0);
            if (fluid.isEmpty()) return 0;
            else if (fluid.getFluid().isSame(Fluids.WATER)) return 3;
            else if (fluid.getFluid().isSame(Fluids.LAVA)) return 4;
            else if (fluid.getFluid().getFluidType().isLighterThanAir()) return 2;
            else return 1;
        });
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        PixelsOfMc.LOGGER.info("RegisterParticleProvider");

        // RegisterParticleProvidersEvent does not seem to allow the registration of a color-able particle with a set
        // texture that doesn't need to be specified and is instead grabbed from the /particles/ folder in the texture-pack.
        Minecraft.getInstance().particleEngine.register(POMparticles.FLUID_BUBBLE_POP.get(), FluidBubblePopParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(POMparticles.FLUID_BUBBLE.get(), FluidBubbleParticle.Provider::new);

        event.registerSpriteSet(POMparticles.ELECTRIC_SPARK.get(), ElectricSpark.Provider::new);
        event.registerSpriteSet(POMparticles.SPARKLE.get(), Sparkle.Provider::new);
        event.registerSpecial(POMparticles.COLORED_BLOCK.get(), new ColoredBlockParticle.Provider());
        event.registerSpriteSet(POMparticles.RED_CROSS.get(), CrossParticle.RedCrossProvider::new);
        event.registerSpriteSet(POMparticles.GREEN_CROSS.get(), CrossParticle.GreenCrossProvider::new);
    }

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((stack, index) -> ((PixelItem) stack.getItem()).getColor(stack, index), POMitems.PIXEL.get(), POMitems.PIXEL_PILE.get());
        event.register((stack, index) -> index == 1 ? ((BigBucket) stack.getItem()).getColor(stack) : -1, POMitems.TITANIUM_BUCKET.get(), POMitems.REINFORCED_BUCKET.get());
    }

    @SuppressWarnings("marked for removal")
    public static void registerRenderLayers() {
        // FLUID
        ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROGEN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROGEN_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.NITROGEN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.NITROGEN_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.OXYGEN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.OXYGEN_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.CHLORINE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.CHLORINE_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.BROMINE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.BROMINE_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.SULFURIC_ACID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.SULFURIC_ACID_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.NITRIC_ACID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.NITRIC_ACID_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.DIRTY_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.DIRTY_WATER_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROFLUORIC_ACID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.HYDROFLUORIC_ACID_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.URANIUM_HEXAFLUORIDE_GAS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(POMfluids.URANIUM_HEXAFLUORIDE_GAS_FLOWING.get(), RenderType.translucent());

        //BLOCK
        ItemBlockRenderTypes.setRenderLayer(POMblocks.ACANTHITE_SPIKE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(POMblocks.PIXEL_BOMBARDER.get(), RenderType.cutout());
    }

    public static void registerTileRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        //BlockEntityRenderers.register(POMtiles.HOT_ISOSTATIC_PRESS.get(), HotIsostaticPressRenderer::new);
        event.registerBlockEntityRenderer(POMtiles.PIXEL_SPLITTER.get(), PixelSplitterRenderer::new);
        event.registerBlockEntityRenderer(POMtiles.STAR.get(), StarRenderer::new);
        event.registerBlockEntityRenderer(POMtiles.BALL_MILL.get(), BallMillRenderer::new);
        event.registerBlockEntityRenderer(POMtiles.FUEL_CELL_HOLDER.get(), FuelCellHolderRenderer::new);
        event.registerBlockEntityRenderer(POMtiles.NUCLEAR_REACTOR.get(), NuclearReactorRenderer::new);
    }

    public static void registerEntityRenderer() {
        EntityRenderers.register(POMentities.RIVER_SHELL.get(), RiverShellRenderer::new);
    }

}