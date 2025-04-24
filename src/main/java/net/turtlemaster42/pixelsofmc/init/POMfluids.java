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

    public static final RegistryObject<FlowingFluid> NITRIC_ACID_SOURCE
            = FLUIDS.register("nitric_acid_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.NITRIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITRIC_ACID_FLOWING
            = FLUIDS.register("nitric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITRIC_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NITRIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITRIC_ACID_FLUID_TYPE, NITRIC_ACID_SOURCE, NITRIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITRIC_ACID_BLOCK).bucket(POMitems.NITRIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> PUREX_SOLUTION_SOURCE
            = FLUIDS.register("purex_solution_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.PUREX_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> PUREX_SOLUTION_FLOWING
            = FLUIDS.register("purex_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.PUREX_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties PUREX_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.PUREX_SOLUTION_FLUID_TYPE, PUREX_SOLUTION_SOURCE, PUREX_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.PUREX_SOLUTION_BLOCK).bucket(POMitems.PUREX_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE_SOLUTION_SOURCE
            = FLUIDS.register("nuclear_waste_solution_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.NUCLEAR_WASTE_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE_SOLUTION_FLOWING
            = FLUIDS.register("nuclear_waste_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NUCLEAR_WASTE_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NUCLEAR_WASTE_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NUCLEAR_WASTE_SOLUTION_FLUID_TYPE, NUCLEAR_WASTE_SOLUTION_SOURCE, NUCLEAR_WASTE_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NUCLEAR_WASTE_SOLUTION_BLOCK).bucket(POMitems.NUCLEAR_WASTE_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> URANIUM_SOLUTION_SOURCE
            = FLUIDS.register("uranium_solution_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.URANIUM_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> URANIUM_SOLUTION_FLOWING
            = FLUIDS.register("uranium_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.URANIUM_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties URANIUM_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.URANIUM_SOLUTION_FLUID_TYPE, URANIUM_SOLUTION_SOURCE, URANIUM_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.URANIUM_SOLUTION_BLOCK).bucket(POMitems.URANIUM_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> PLUTONIUM_SOLUTION_SOURCE
            = FLUIDS.register("plutonium_solution_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.PLUTONIUM_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> PLUTONIUM_SOLUTION_FLOWING
            = FLUIDS.register("plutonium_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.PLUTONIUM_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties PLUTONIUM_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.PLUTONIUM_SOLUTION_FLUID_TYPE, PLUTONIUM_SOLUTION_SOURCE, PLUTONIUM_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.PLUTONIUM_SOLUTION_BLOCK).bucket(POMitems.PLUTONIUM_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> RED_OIL_SOURCE
            = FLUIDS.register("red_oil_fluid", () -> new ForgeFlowingFluid.Source(POMfluids.RED_OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RED_OIL_FLOWING
            = FLUIDS.register("red_oil_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.RED_OIL_PROPERTIES));
    public static final ForgeFlowingFluid.Properties RED_OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.RED_OIL_FLUID_TYPE, RED_OIL_SOURCE, RED_OIL_FLOWING).slopeFindDistance(3).levelDecreasePerBlock(2)
            .block(POMblocks.RED_OIL_BLOCK).bucket(POMitems.RED_OIL_BUCKET);


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

    public static final RegistryObject<FlowingFluid> STEAM_SOURCE
            = FLUIDS.register("steam", () -> new ForgeFlowingFluid.Source(POMfluids.STEAM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> STEAM_FLOWING
            = FLUIDS.register("steam_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.STEAM_PROPERTIES));
    public static final ForgeFlowingFluid.Properties STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.STEAM_TYPE, STEAM_SOURCE, STEAM_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.STEAM_BLOCK).bucket(POMitems.STEAM_BUCKET);

    public static final RegistryObject<FlowingFluid> BLAZING_STEAM_SOURCE
            = FLUIDS.register("blazing_steam", () -> new ForgeFlowingFluid.Source(POMfluids.BLAZING_STEAM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BLAZING_STEAM_FLOWING
            = FLUIDS.register("blazing_steam_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BLAZING_STEAM_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BLAZING_STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BLAZING_STEAM_TYPE, BLAZING_STEAM_SOURCE, BLAZING_STEAM_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.BLAZING_STEAM_BLOCK).bucket(POMitems.BLAZING_STEAM_BUCKET);

    public static final RegistryObject<FlowingFluid> AIR_SOURCE
            = FLUIDS.register("air", () -> new ForgeFlowingFluid.Source(POMfluids.AIR_PROPERTIES));
    public static final RegistryObject<FlowingFluid> AIR_FLOWING
            = FLUIDS.register("air_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.AIR_PROPERTIES));
    public static final ForgeFlowingFluid.Properties AIR_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.AIR_TYPE, AIR_SOURCE, AIR_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(15);


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

    public static final RegistryObject<FlowingFluid> AMMONIA_GAS_SOURCE
            = FLUIDS.register("ammonia_gas", () -> new ForgeFlowingFluid.Source(POMfluids.AMMONIA_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> AMMONIA_GAS_FLOWING
            = FLUIDS.register("ammonia_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.AMMONIA_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties AMMONIA_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.AMMONIA_GAS_TYPE, AMMONIA_GAS_SOURCE, AMMONIA_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.AMMONIA_GAS_BLOCK).bucket(POMitems.AMMONIA_GAS_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
