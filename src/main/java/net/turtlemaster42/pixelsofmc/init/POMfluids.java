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
import net.turtlemaster42.pixelsofmc.util.Element;

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
            .density(15).luminosity(2).viscosity(5).sound(SoundEvents.BUCKET_FILL_POWDER_SNOW).overlay(WATER_OVERLAY_RL)
            .color(0xbf001559)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> POMfluids.MERCURY_BLOCK.get()).bucket(() -> POMitems.MERCURY_BUCKET.get());
    public static final RegistryObject<LiquidBlock> MERCURY_BLOCK = POMblocks.BLOCKS.register("mercury",
            () -> new LiquidBlock(() -> POMfluids.MERCURY_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));


    public static final RegistryObject<FlowingFluid> HYDROGEN_FLUID
            = FLUIDS.register("hydrogen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_FLOWING
            = FLUIDS.register("hydrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_PROPERTIES));
    public static final ForgeFlowingFluid.Properties HYDROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> HYDROGEN_FLUID.get(), () -> HYDROGEN_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(1).luminosity(2).viscosity(5).gaseous().sound(SoundEvents.BUCKET_FILL_POWDER_SNOW).overlay(WATER_OVERLAY_RL)
            .color(0xbfffffff)).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(() -> POMfluids.HYDROGEN_BLOCK.get()).bucket(() -> POMitems.Metals.ELEMENTS.get(Element.HYDROGEN).get());
    public static final RegistryObject<LiquidBlock> HYDROGEN_BLOCK = POMblocks.BLOCKS.register("hydrogen",
            () -> new LiquidBlock(() -> POMfluids.HYDROGEN_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));

    public static final RegistryObject<FlowingFluid> OXYGEN_FLUID
            = FLUIDS.register("oxygen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_FLOWING
            = FLUIDS.register("oxygen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_PROPERTIES));
    public static final ForgeFlowingFluid.Properties OXYGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> OXYGEN_FLUID.get(), () -> OXYGEN_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(1).luminosity(2).viscosity(5).gaseous().sound(SoundEvents.BUCKET_FILL_POWDER_SNOW).overlay(WATER_OVERLAY_RL)
            .color(0xbf7af8ff)).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(() -> POMfluids.OXYGEN_BLOCK.get()).bucket(() -> POMitems.Metals.ELEMENTS.get(Element.OXYGEN).get());
    public static final RegistryObject<LiquidBlock> OXYGEN_BLOCK = POMblocks.BLOCKS.register("oxygen",
            () -> new LiquidBlock(() -> POMfluids.OXYGEN_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));


    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_FLUID
            = FLUIDS.register("sulfuric_acid_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_FLOWING
            = FLUIDS.register("sulfuric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SULFURIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> SULFURIC_ACID_FLUID.get(), () -> SULFURIC_ACID_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(1).luminosity(2).viscosity(5).sound(SoundEvents.BUCKET_FILL_POWDER_SNOW).overlay(WATER_OVERLAY_RL)
            .color(0xbfd6ab2a)).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(() -> POMfluids.SULFURIC_ACID_BLOCK.get()).bucket(() -> POMitems.Metals.ELEMENTS.get(Element.SULFUR).get());
    public static final RegistryObject<LiquidBlock> SULFURIC_ACID_BLOCK = POMblocks.BLOCKS.register("sulfuric_acid",
            () -> new LiquidBlock(() -> POMfluids.SULFURIC_ACID_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
