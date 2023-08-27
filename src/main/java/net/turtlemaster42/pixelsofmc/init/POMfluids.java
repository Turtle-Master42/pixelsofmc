package net.turtlemaster42.pixelsofmc.init;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.fluid.POMFluidType;

public class POMfluids {
    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, PixelsOfMc.MOD_ID);


    public static final RegistryObject<FlowingFluid> MERCURY_SOURCE
            = FLUIDS.register("mercury_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.MERCURY_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERCURY_FLOWING
            = FLUIDS.register("mercury_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.MERCURY_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MERCURY_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.MERCURY_FLUID_TYPE, MERCURY_SOURCE, MERCURY_FLOWING).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(POMblocks.MERCURY_BLOCK).bucket(POMitems.MERCURY_BUCKET);

    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_SOURCE
            = FLUIDS.register("sulfuric_acid_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_FLOWING
            = FLUIDS.register("sulfuric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.SULFURIC_ACID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SULFURIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.SULFURIC_ACID_FLUID_TYPE, SULFURIC_ACID_SOURCE, SULFURIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.SULFURIC_ACID_BLOCK).bucket(POMitems.SULFURIC_ACID_BUCKET);

    //-----------SUPERCOOLED-----------//

    public static final RegistryObject<FlowingFluid> HYDROGEN_SOURCE
            = FLUIDS.register("hydrogen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_FLOWING
            = FLUIDS.register("hydrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HYDROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROGEN_FLUID_TYPE, HYDROGEN_SOURCE, HYDROGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.HYDROGEN_BLOCK).bucket(POMitems.LIQUID_HYDROGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> NITROGEN_SOURCE
            = FLUIDS.register("nitrogen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.NITROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITROGEN_FLOWING
            = FLUIDS.register("nitrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITROGEN_PROPERTIES));

    public static final ForgeFlowingFluid.Properties NITROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITROGEN_FLUID_TYPE, NITROGEN_SOURCE, NITROGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITROGEN_BLOCK).bucket(POMitems.LIQUID_NITROGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> OXYGEN_SOURCE
            = FLUIDS.register("oxygen_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_FLOWING
            = FLUIDS.register("oxygen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_PROPERTIES));

    public static final ForgeFlowingFluid.Properties OXYGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.OXYGEN_FLUID_TYPE, OXYGEN_SOURCE, OXYGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.OXYGEN_BLOCK).bucket(POMitems.LIQUID_OXYGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> CHLORINE_SOURCE
            = FLUIDS.register("chlorine_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.CHLORINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CHLORINE_FLOWING
            = FLUIDS.register("chlorine_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.CHLORINE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CHLORINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.CHLORINE_FLUID_TYPE, CHLORINE_SOURCE, CHLORINE_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.CHLORINE_BLOCK).bucket(POMitems.LIQUID_CHLORINE_BUCKET);

    public static final RegistryObject<FlowingFluid> BROMINE_SOURCE
            = FLUIDS.register("bromine_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.BROMINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BROMINE_FLOWING
            = FLUIDS.register("bromine_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BROMINE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties BROMINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BROMINE_FLUID_TYPE, BROMINE_SOURCE, BROMINE_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.BROMINE_BLOCK).bucket(POMitems.LIQUID_BROMINE_BUCKET);

    //-----------GAS-----------//

    public static final RegistryObject<FlowingFluid> HYDROGEN_GAS_SOURCE
            = FLUIDS.register("hydrogen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_GAS_FLOWING
            = FLUIDS.register("hydrogen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_GAS_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HYDROGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROGEN_GAS_TYPE, HYDROGEN_GAS_SOURCE, HYDROGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.HYDROGEN_GAS_BLOCK).bucket(POMitems.HYDROGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> NITROGEN_GAS_SOURCE
            = FLUIDS.register("nitrogen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.NITROGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITROGEN_GAS_FLOWING
            = FLUIDS.register("nitrogen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITROGEN_GAS_PROPERTIES));

    public static final ForgeFlowingFluid.Properties NITROGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITROGEN_GAS_TYPE, NITROGEN_GAS_SOURCE, NITROGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITROGEN_GAS_BLOCK).bucket(POMitems.NITROGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> OXYGEN_GAS_SOURCE
            = FLUIDS.register("oxygen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_GAS_FLOWING
            = FLUIDS.register("oxygen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_GAS_PROPERTIES));

    public static final ForgeFlowingFluid.Properties OXYGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.OXYGEN_GAS_TYPE, OXYGEN_GAS_SOURCE, OXYGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.OXYGEN_GAS_BLOCK).bucket(POMitems.OXYGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> CHLORINE_GAS_SOURCE
            = FLUIDS.register("chlorine_gas", () -> new ForgeFlowingFluid.Source(POMfluids.CHLORINE_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CHLORINE_GAS_FLOWING
            = FLUIDS.register("chlorine_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.CHLORINE_GAS_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CHLORINE_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.CHLORINE_GAS_TYPE, CHLORINE_GAS_SOURCE, CHLORINE_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.CHLORINE_GAS_BLOCK).bucket(POMitems.CHLORINE_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> BROMINE_GAS_SOURCE
            = FLUIDS.register("bromine_gas", () -> new ForgeFlowingFluid.Source(POMfluids.BROMINE_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BROMINE_GAS_FLOWING
            = FLUIDS.register("bromine_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BROMINE_GAS_PROPERTIES));

    public static final ForgeFlowingFluid.Properties BROMINE_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BROMINE_GAS_TYPE, BROMINE_GAS_SOURCE, BROMINE_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.BROMINE_GAS_BLOCK).bucket(POMitems.BROMINE_GAS_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
