package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.fluid.POMFluidType;
import net.turtlemaster42.pixelsofmc.util.Element;

public class POMfluids {
    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, PixelsOfMc.MOD_ID);


    public static final RegistryObject<FlowingFluid> MERCURY_SOURCE
            = FLUIDS.register("mercury_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.MERCURY_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERCURY_FLOWING
            = FLUIDS.register("mercury_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.MERCURY_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MERCURY_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.MERCURY_FLUID_TYPE, MERCURY_SOURCE, MERCURY_FLOWING).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(POMfluids.MERCURY_BLOCK).bucket(POMitems.MERCURY_BUCKET);

    public static final RegistryObject<LiquidBlock> MERCURY_BLOCK = POMblocks.BLOCKS.register("mercury",
            () -> new LiquidBlock(POMfluids.MERCURY_SOURCE, BlockBehaviour.Properties.of(Material.WATER)));


    public static final RegistryObject<FlowingFluid> HYDROGEN_SOURCE
            = FLUIDS.register("hydrogen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_FLOWING
            = FLUIDS.register("hydrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HYDROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROGEN_FLUID_TYPE, HYDROGEN_SOURCE, HYDROGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMfluids.HYDROGEN_BLOCK).bucket(POMitems.Metals.ELEMENTS.get(Element.HYDROGEN));
    public static final RegistryObject<LiquidBlock> HYDROGEN_BLOCK = POMblocks.BLOCKS.register("hydrogen",
            () -> new LiquidBlock(POMfluids.HYDROGEN_SOURCE, BlockBehaviour.Properties.of(Material.WATER)));


    public static final RegistryObject<FlowingFluid> OXYGEN_SOURCE
            = FLUIDS.register("oxygen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_FLOWING
            = FLUIDS.register("oxygen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_PROPERTIES));

    public static final ForgeFlowingFluid.Properties OXYGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.OXYGEN_FLUID_TYPE, OXYGEN_SOURCE, OXYGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMfluids.OXYGEN_BLOCK).bucket(POMitems.Metals.ELEMENTS.get(Element.OXYGEN));

    public static final RegistryObject<LiquidBlock> OXYGEN_BLOCK = POMblocks.BLOCKS.register("oxygen",
            () -> new LiquidBlock(POMfluids.OXYGEN_SOURCE, BlockBehaviour.Properties.of(Material.WATER)));


    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_SOURCE
            = FLUIDS.register("sulfuric_acid_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_FLOWING
            = FLUIDS.register("sulfuric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.SULFURIC_ACID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SULFURIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.SULFURIC_ACID_FLUID_TYPE, SULFURIC_ACID_SOURCE, SULFURIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMfluids.SULFURIC_ACID_BLOCK).bucket(POMitems.Metals.ELEMENTS.get(Element.SULFUR));

    public static final RegistryObject<LiquidBlock> SULFURIC_ACID_BLOCK = POMblocks.BLOCKS.register("sulfuric_acid",
            () -> new LiquidBlock(POMfluids.SULFURIC_ACID_SOURCE, BlockBehaviour.Properties.of(Material.WATER)));



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
