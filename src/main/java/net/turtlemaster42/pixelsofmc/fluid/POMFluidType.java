package net.turtlemaster42.pixelsofmc.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtlemaster42.pixelsofmc.PixelsOfMc;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.joml.Vector3f;

import java.awt.*;

public class POMFluidType {
    public static final ResourceLocation WATER_STILL_RL = Util.resourceLocation("minecraft", "block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = Util.resourceLocation("minecraft","block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = Util.resourceLocation("minecraft","block/water_overlay");

    public static final ResourceLocation HEAVY_LIQUID_STILL_RL = Util.resourceLocation("block/heavy_liquid_still");
    public static final ResourceLocation HEAVY_LIQUID_FLOWING_RL = Util.resourceLocation("block/heavy_liquid_flow");
    public static final ResourceLocation HEAVY_LIQUID_OVERLAY_RL = Util.resourceLocation("block/heavy_liquid_overlay");

    public static final ResourceLocation GAS_STILL_RL = Util.resourceLocation("block/gas_still");
    public static final ResourceLocation THIN_GAS_STILL_RL = Util.resourceLocation("block/thin_gas_still");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<FluidType> MERCURY_FLUID_TYPE = registerHeavyFluid("mercury", 64, 75, 141,
            FluidType.Properties.create().lightLevel(15).temperature(300).density(13500).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.035f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY_POWDER_SNOW));

    public static final RegistryObject<FluidType> LEAD_FLUID_TYPE = registerHeavyFluid("lead", 95, 84, 120,
            FluidType.Properties.create().lightLevel(15).temperature(300).density(10700).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.035f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY_POWDER_SNOW));


    public static final RegistryObject<FluidType> SULFURIC_ACID_FLUID_TYPE = registerFluid("sulfuric_acid", 161, 178, 48,
            FluidType.Properties.create().lightLevel(1).temperature(300).density(1830).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.008f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> NITRIC_ACID_FLUID_TYPE = registerFluid("nitric_acid", 239, 211, 26,
            FluidType.Properties.create().lightLevel(1).temperature(300).density(1510).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.008f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> PUREX_SOLUTION_FLUID_TYPE = registerThickFluid("purex_solution", 92, 170, 75,
            FluidType.Properties.create().lightLevel(8).temperature(300).density(1900).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.005f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> NUCLEAR_WASTE_FLUID_TYPE = registerThickFluid("nuclear_waste", 102, 104, 74,
            FluidType.Properties.create().lightLevel(15).temperature(500).density(15500).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.005f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));


    public static final RegistryObject<FluidType> NUCLEAR_WASTE_SOLUTION_FLUID_TYPE = registerThickFluid("nuclear_waste_solution", 116, 132, 67,
            FluidType.Properties.create().lightLevel(15).temperature(400).density(17400).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.005f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> URANIUM_SOLUTION_FLUID_TYPE = registerFluid("uranium_solution", 75, 201, 66,
            FluidType.Properties.create().lightLevel(12).temperature(350).density(11000).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.008f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> PLUTONIUM_SOLUTION_FLUID_TYPE = registerFluid("plutonium_solution", 66, 201, 147,
            FluidType.Properties.create().lightLevel(12).temperature(350).density(11500).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.008f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> RED_OIL_FLUID_TYPE = registerThickFluid("red_oil", 145, 13, 20,
            FluidType.Properties.create().lightLevel(5).temperature(300).density(1450).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.002f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));


    //-----------SUPERCOOLED-----------//
    public static final RegistryObject<FluidType> HYDROGEN_FLUID_TYPE = registerFluid("hydrogen", 225, 223, 235,
            FluidType.Properties.create().lightLevel(0).temperature(19).density(70).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> NITROGEN_FLUID_TYPE = registerFluid("nitrogen", 151, 130, 230,
            FluidType.Properties.create().lightLevel(1).temperature(70).density(800).viscosity(5).canDrown(false).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> OXYGEN_FLUID_TYPE = registerFluid("oxygen", 119, 171, 240,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(1150).viscosity(5).canDrown(false).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> CHLORINE_FLUID_TYPE = registerFluid("chlorine", 148, 176, 96,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(1550).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> BROMINE_FLUID_TYPE = registerFluid("bromine", 218, 146, 163,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(3100).viscosity(5).canDrown(true).canPushEntity(true)
                    .supportsBoating(true).motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    //-----------GAS-----------//

    public static final RegistryObject<FluidType> STEAM_TYPE = registerGas("steam", 240, 240, 245,
            FluidType.Properties.create().lightLevel(0).temperature(450).density(-2).viscosity(0).canDrown(false).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> BLAZING_STEAM_TYPE = registerGas("blazing_steam", 240, 240, 245,
            FluidType.Properties.create().lightLevel(0).temperature(1200).density(-6).viscosity(0).canDrown(false).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> AIR_TYPE = registerThinGas("air", 140, 140, 150,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(0).viscosity(0).canDrown(false).canPushEntity(false));

    public static final RegistryObject<FluidType> MERCURY_GAS_TYPE = registerGas("mercury_gas", 74, 85, 151,
            FluidType.Properties.create().lightLevel(2).temperature(700).density(-1).viscosity(0).canDrown(true).canPushEntity(false));

    public static final RegistryObject<FluidType> LEAD_GAS_TYPE = registerGas("lead_gas", 105, 86, 122,
            FluidType.Properties.create().lightLevel(2).temperature(1500).density(-1).viscosity(0).canDrown(true).canPushEntity(false));


    public static final RegistryObject<FluidType> HYDROGEN_GAS_TYPE = registerGas("hydrogen_gas", 225, 223, 235,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-13).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> NITROGEN_GAS_TYPE = registerGas("nitrogen_gas", 151, 130, 230,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(0).viscosity(0).canDrown(false).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> OXYGEN_GAS_TYPE = registerGas("oxygen_gas", 119, 171, 240,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(1).viscosity(0).canDrown(false).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> CHLORINE_GAS_TYPE = registerGas("chlorine_gas", 148, 176, 96,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(3).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> BROMINE_GAS_TYPE = registerGas("bromine_gas", 218, 146, 163,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(3).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> AMMONIA_GAS_TYPE = registerGas("ammonia_gas", 117, 56, 95,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-1).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));


    private static RegistryObject<FluidType> registerFluid(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    private static RegistryObject<FluidType> registerThickFluid(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(HEAVY_LIQUID_STILL_RL, HEAVY_LIQUID_FLOWING_RL, HEAVY_LIQUID_FLOWING_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), 0f, 1f, properties));
    }

    private static RegistryObject<FluidType> registerHeavyFluid(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new HeavyLiquidType(HEAVY_LIQUID_STILL_RL, HEAVY_LIQUID_FLOWING_RL, HEAVY_LIQUID_FLOWING_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    private static RegistryObject<FluidType> registerGas(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(GAS_STILL_RL, GAS_STILL_RL, WATER_OVERLAY_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    private static RegistryObject<FluidType> registerThinGas(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(THIN_GAS_STILL_RL, THIN_GAS_STILL_RL, WATER_OVERLAY_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
