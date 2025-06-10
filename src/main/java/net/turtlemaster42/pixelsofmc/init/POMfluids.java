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


    public static final RegistryObject<FlowingFluid> MERCURY
            = FLUIDS.register("mercury", () -> new ForgeFlowingFluid.Source(POMfluids.MERCURY_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERCURY_FLOWING
            = FLUIDS.register("mercury_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.MERCURY_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MERCURY_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.MERCURY_FLUID_TYPE, MERCURY, MERCURY_FLOWING).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(POMblocks.MERCURY_BLOCK).bucket(POMitems.MERCURY_BUCKET);

    public static final RegistryObject<FlowingFluid> LEAD
            = FLUIDS.register("lead", () -> new ForgeFlowingFluid.Source(POMfluids.LEAD_PROPERTIES));
    public static final RegistryObject<FlowingFluid> LEAD_FLOWING
            = FLUIDS.register("lead_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.LEAD_PROPERTIES));
    public static final ForgeFlowingFluid.Properties LEAD_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.LEAD_FLUID_TYPE, LEAD, LEAD_FLOWING).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(POMblocks.LIQUID_LEAD_BLOCK).bucket(POMitems.LIQUID_LEAD_BUCKET);


    public static final RegistryObject<FlowingFluid> SULFURIC_ACID
            = FLUIDS.register("sulfuric_acid", () -> new ForgeFlowingFluid.Source(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SULFURIC_ACID_FLOWING
            = FLUIDS.register("sulfuric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.SULFURIC_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SULFURIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.SULFURIC_ACID_FLUID_TYPE, SULFURIC_ACID, SULFURIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.SULFURIC_ACID_BLOCK).bucket(POMitems.SULFURIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> NITRIC_ACID
            = FLUIDS.register("nitric_acid", () -> new ForgeFlowingFluid.Source(POMfluids.NITRIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITRIC_ACID_FLOWING
            = FLUIDS.register("nitric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITRIC_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NITRIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITRIC_ACID_FLUID_TYPE, NITRIC_ACID, NITRIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITRIC_ACID_BLOCK).bucket(POMitems.NITRIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> HYDROFLUORIC_ACID
            = FLUIDS.register("hydrofluoric_acid", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROFLUORIC_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROFLUORIC_ACID_FLOWING
            = FLUIDS.register("hydrofluoric_acid_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROFLUORIC_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties HYDROFLUORIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROFLUORIC_ACID_FLUID_TYPE, HYDROFLUORIC_ACID, HYDROFLUORIC_ACID_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.HYDROFLUORIC_ACID_BLOCK).bucket(POMitems.HYDROFLUORIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> PUREX_SOLUTION
            = FLUIDS.register("purex_solution", () -> new ForgeFlowingFluid.Source(POMfluids.PUREX_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> PUREX_SOLUTION_FLOWING
            = FLUIDS.register("purex_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.PUREX_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties PUREX_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.PUREX_SOLUTION_FLUID_TYPE, PUREX_SOLUTION, PUREX_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.PUREX_SOLUTION_BLOCK).bucket(POMitems.PUREX_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE
            = FLUIDS.register("nuclear_waste", () -> new ForgeFlowingFluid.Source(POMfluids.NUCLEAR_WASTE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE_FLOWING
            = FLUIDS.register("nuclear_waste_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NUCLEAR_WASTE_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NUCLEAR_WASTE_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NUCLEAR_WASTE_FLUID_TYPE, NUCLEAR_WASTE, NUCLEAR_WASTE_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NUCLEAR_WASTE_BLOCK).bucket(POMitems.NUCLEAR_WASTE_BUCKET);

    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE_SOLUTION
            = FLUIDS.register("nuclear_waste_solution", () -> new ForgeFlowingFluid.Source(POMfluids.NUCLEAR_WASTE_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NUCLEAR_WASTE_SOLUTION_FLOWING
            = FLUIDS.register("nuclear_waste_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NUCLEAR_WASTE_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NUCLEAR_WASTE_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NUCLEAR_WASTE_SOLUTION_FLUID_TYPE, NUCLEAR_WASTE_SOLUTION, NUCLEAR_WASTE_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NUCLEAR_WASTE_SOLUTION_BLOCK).bucket(POMitems.NUCLEAR_WASTE_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> URANIUM_SOLUTION
            = FLUIDS.register("uranium_solution", () -> new ForgeFlowingFluid.Source(POMfluids.URANIUM_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> URANIUM_SOLUTION_FLOWING
            = FLUIDS.register("uranium_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.URANIUM_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties URANIUM_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.URANIUM_SOLUTION_FLUID_TYPE, URANIUM_SOLUTION, URANIUM_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.URANIUM_SOLUTION_BLOCK).bucket(POMitems.URANIUM_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> ENRICHED_URANIUM_SOLUTION
            = FLUIDS.register("enriched_uranium_solution", () -> new ForgeFlowingFluid.Source(POMfluids.ENRICHED_URANIUM_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> ENRICHED_URANIUM_SOLUTION_FLOWING
            = FLUIDS.register("enriched_uranium_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.ENRICHED_URANIUM_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties ENRICHED_URANIUM_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.ENRICHED_URANIUM_SOLUTION_FLUID_TYPE, ENRICHED_URANIUM_SOLUTION, ENRICHED_URANIUM_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.ENRICHED_URANIUM_SOLUTION_BLOCK).bucket(POMitems.ENRICHED_URANIUM_SOLUTION_BUCKET);


    public static final RegistryObject<FlowingFluid> PLUTONIUM_SOLUTION
            = FLUIDS.register("plutonium_solution", () -> new ForgeFlowingFluid.Source(POMfluids.PLUTONIUM_SOLUTION_PROPERTIES));
    public static final RegistryObject<FlowingFluid> PLUTONIUM_SOLUTION_FLOWING
            = FLUIDS.register("plutonium_solution_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.PLUTONIUM_SOLUTION_PROPERTIES));
    public static final ForgeFlowingFluid.Properties PLUTONIUM_SOLUTION_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.PLUTONIUM_SOLUTION_FLUID_TYPE, PLUTONIUM_SOLUTION, PLUTONIUM_SOLUTION_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.PLUTONIUM_SOLUTION_BLOCK).bucket(POMitems.PLUTONIUM_SOLUTION_BUCKET);

    public static final RegistryObject<FlowingFluid> RED_OIL
            = FLUIDS.register("red_oil", () -> new ForgeFlowingFluid.Source(POMfluids.RED_OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RED_OIL_FLOWING
            = FLUIDS.register("red_oil_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.RED_OIL_PROPERTIES));
    public static final ForgeFlowingFluid.Properties RED_OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.RED_OIL_FLUID_TYPE, RED_OIL, RED_OIL_FLOWING).slopeFindDistance(3).levelDecreasePerBlock(2)
            .block(POMblocks.RED_OIL_BLOCK).bucket(POMitems.RED_OIL_BUCKET);

    public static final RegistryObject<FlowingFluid> DIRTY_WATER
            = FLUIDS.register("dirty_water", () -> new ForgeFlowingFluid.Source(POMfluids.DIRTY_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> DIRTY_WATER_FLOWING
            = FLUIDS.register("dirty_water_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.DIRTY_WATER_PROPERTIES));
    public static final ForgeFlowingFluid.Properties DIRTY_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.DIRTY_WATER_FLUID_TYPE, DIRTY_WATER, DIRTY_WATER_FLOWING).slopeFindDistance(3).levelDecreasePerBlock(2)
            .block(POMblocks.DIRTY_WATER_BLOCK).bucket(POMitems.DIRTY_WATER_BUCKET);


    //-----------SUPERCOOLED-----------//
    public static final RegistryObject<FlowingFluid> HYDROGEN
            = FLUIDS.register("hydrogen", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_FLOWING
            = FLUIDS.register("hydrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_PROPERTIES));
    public static final ForgeFlowingFluid.Properties HYDROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROGEN_FLUID_TYPE, HYDROGEN, HYDROGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.HYDROGEN_BLOCK).bucket(POMitems.LIQUID_HYDROGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> NITROGEN
            = FLUIDS.register("nitrogen", () -> new ForgeFlowingFluid.Source(POMfluids.NITROGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITROGEN_FLOWING
            = FLUIDS.register("nitrogen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITROGEN_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NITROGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITROGEN_FLUID_TYPE, NITROGEN, NITROGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITROGEN_BLOCK).bucket(POMitems.LIQUID_NITROGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> OXYGEN
            = FLUIDS.register("oxygen", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_FLOWING
            = FLUIDS.register("oxygen_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_PROPERTIES));
    public static final ForgeFlowingFluid.Properties OXYGEN_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.OXYGEN_FLUID_TYPE, OXYGEN, OXYGEN_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.OXYGEN_BLOCK).bucket(POMitems.LIQUID_OXYGEN_BUCKET);

    public static final RegistryObject<FlowingFluid> CHLORINE
            = FLUIDS.register("chlorine", () -> new ForgeFlowingFluid.Source(POMfluids.CHLORINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CHLORINE_FLOWING
            = FLUIDS.register("chlorine_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.CHLORINE_PROPERTIES));
    public static final ForgeFlowingFluid.Properties CHLORINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.CHLORINE_FLUID_TYPE, CHLORINE, CHLORINE_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.CHLORINE_BLOCK).bucket(POMitems.LIQUID_CHLORINE_BUCKET);

    public static final RegistryObject<FlowingFluid> BROMINE
            = FLUIDS.register("bromine", () -> new ForgeFlowingFluid.Source(POMfluids.BROMINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BROMINE_FLOWING
            = FLUIDS.register("bromine_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BROMINE_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BROMINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BROMINE_FLUID_TYPE, BROMINE, BROMINE_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.BROMINE_BLOCK).bucket(POMitems.LIQUID_BROMINE_BUCKET);


    //-----------GAS-----------//
    public static final RegistryObject<FlowingFluid> STEAM
            = FLUIDS.register("steam", () -> new ForgeFlowingFluid.Source(POMfluids.STEAM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> STEAM_FLOWING
            = FLUIDS.register("steam_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.STEAM_PROPERTIES));
    public static final ForgeFlowingFluid.Properties STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.STEAM_TYPE, STEAM, STEAM_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.STEAM_BLOCK).bucket(POMitems.STEAM_BUCKET);

    public static final RegistryObject<FlowingFluid> BLAZING_STEAM
            = FLUIDS.register("blazing_steam", () -> new ForgeFlowingFluid.Source(POMfluids.BLAZING_STEAM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BLAZING_STEAM_FLOWING
            = FLUIDS.register("blazing_steam_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BLAZING_STEAM_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BLAZING_STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BLAZING_STEAM_TYPE, BLAZING_STEAM, BLAZING_STEAM_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.BLAZING_STEAM_BLOCK).bucket(POMitems.BLAZING_STEAM_BUCKET);

    public static final RegistryObject<FlowingFluid> AIR
            = FLUIDS.register("air", () -> new ForgeFlowingFluid.Source(POMfluids.AIR_PROPERTIES));
    public static final RegistryObject<FlowingFluid> AIR_FLOWING
            = FLUIDS.register("air_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.AIR_PROPERTIES));
    public static final ForgeFlowingFluid.Properties AIR_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.AIR_TYPE, AIR, AIR_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(15);

    public static final RegistryObject<FlowingFluid> MERCURY_GAS
            = FLUIDS.register("mercury_gas", () -> new ForgeFlowingFluid.Source(POMfluids.MERCURY_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERCURY_GAS_FLOWING
            = FLUIDS.register("mercury_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.MERCURY_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MERCURY_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.MERCURY_GAS_TYPE, MERCURY_GAS, MERCURY_GAS_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.MERCURY_GAS_BLOCK).bucket(POMitems.MERCURY_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> LEAD_GAS
            = FLUIDS.register("lead_gas", () -> new ForgeFlowingFluid.Source(POMfluids.LEAD_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> LEAD_GAS_FLOWING
            = FLUIDS.register("lead_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.LEAD_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties LEAD_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.LEAD_GAS_TYPE, LEAD_GAS, LEAD_GAS_FLOWING).slopeFindDistance(0).levelDecreasePerBlock(5)
            .block(POMblocks.LEAD_GAS_BLOCK).bucket(POMitems.LEAD_GAS_BUCKET);


    public static final RegistryObject<FlowingFluid> HYDROGEN_GAS
            = FLUIDS.register("hydrogen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.HYDROGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HYDROGEN_GAS_FLOWING
            = FLUIDS.register("hydrogen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.HYDROGEN_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties HYDROGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.HYDROGEN_GAS_TYPE, HYDROGEN_GAS, HYDROGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.HYDROGEN_GAS_BLOCK).bucket(POMitems.HYDROGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> NITROGEN_GAS
            = FLUIDS.register("nitrogen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.NITROGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> NITROGEN_GAS_FLOWING
            = FLUIDS.register("nitrogen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.NITROGEN_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties NITROGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.NITROGEN_GAS_TYPE, NITROGEN_GAS, NITROGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.NITROGEN_GAS_BLOCK).bucket(POMitems.NITROGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> OXYGEN_GAS
            = FLUIDS.register("oxygen_gas", () -> new ForgeFlowingFluid.Source(POMfluids.OXYGEN_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OXYGEN_GAS_FLOWING
            = FLUIDS.register("oxygen_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.OXYGEN_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties OXYGEN_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.OXYGEN_GAS_TYPE, OXYGEN_GAS, OXYGEN_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.OXYGEN_GAS_BLOCK).bucket(POMitems.OXYGEN_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> CHLORINE_GAS
            = FLUIDS.register("chlorine_gas", () -> new ForgeFlowingFluid.Source(POMfluids.CHLORINE_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CHLORINE_GAS_FLOWING
            = FLUIDS.register("chlorine_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.CHLORINE_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties CHLORINE_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.CHLORINE_GAS_TYPE, CHLORINE_GAS, CHLORINE_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.CHLORINE_GAS_BLOCK).bucket(POMitems.CHLORINE_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> BROMINE_GAS
            = FLUIDS.register("bromine_gas", () -> new ForgeFlowingFluid.Source(POMfluids.BROMINE_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BROMINE_GAS_FLOWING
            = FLUIDS.register("bromine_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.BROMINE_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BROMINE_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.BROMINE_GAS_TYPE, BROMINE_GAS, BROMINE_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.BROMINE_GAS_BLOCK).bucket(POMitems.BROMINE_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> AMMONIA_GAS
            = FLUIDS.register("ammonia_gas", () -> new ForgeFlowingFluid.Source(POMfluids.AMMONIA_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> AMMONIA_GAS_FLOWING
            = FLUIDS.register("ammonia_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.AMMONIA_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties AMMONIA_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.AMMONIA_GAS_TYPE, AMMONIA_GAS, AMMONIA_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.AMMONIA_GAS_BLOCK).bucket(POMitems.AMMONIA_GAS_BUCKET);

    public static final RegistryObject<FlowingFluid> URANIUM_HEXAFLUORIDE_GAS
            = FLUIDS.register("uranium_hexafluoride_gas", () -> new ForgeFlowingFluid.Source(POMfluids.URANIUM_HEXAFLUORIDE_GAS_PROPERTIES));
    public static final RegistryObject<FlowingFluid> URANIUM_HEXAFLUORIDE_GAS_FLOWING
            = FLUIDS.register("uranium_hexafluoride_gas_flowing", () -> new ForgeFlowingFluid.Flowing(POMfluids.URANIUM_HEXAFLUORIDE_GAS_PROPERTIES));
    public static final ForgeFlowingFluid.Properties URANIUM_HEXAFLUORIDE_GAS_PROPERTIES = new ForgeFlowingFluid.Properties(
            POMFluidType.URANIUM_HEXAFLUORIDE_GAS_TYPE, URANIUM_HEXAFLUORIDE_GAS, URANIUM_HEXAFLUORIDE_GAS_FLOWING).slopeFindDistance(4).levelDecreasePerBlock(1)
            .block(POMblocks.URANIUM_HEXAFLUORIDE_GAS_BLOCK).bucket(POMitems.URANIUM_HEXAFLUORIDE_GAS_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
