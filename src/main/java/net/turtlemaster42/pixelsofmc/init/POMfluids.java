package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;

public class POMfluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, PixelsOfMc.MOD_ID);


    public static final RegistryObject<FlowingFluid> MERCURY_FLUID
            = FLUIDS.register("mercury_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.MERCURY_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MERCURY_FLOWING
            = FLUIDS.register("mercury_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.MERCURY_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MERCURY_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MERCURY_FLUID.get(), () -> MERCURY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(15).luminosity(2).viscosity(5).sound(SoundEvents.HONEY_DRINK).overlay(WATER_OVERLAY_RL)
            .color(0xbf001559)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> POMfluids.MERCURY_BLOCK.get()).bucket(() -> POMitems.MERCURY_BUCKET.get());

    public static final RegistryObject<LiquidBlock> MERCURY_BLOCK = POMblocks.BLOCKS.register("mercury",
            () -> new LiquidBlock(() -> POMfluids.MERCURY_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
