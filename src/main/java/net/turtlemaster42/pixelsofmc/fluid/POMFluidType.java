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
import org.joml.Vector3f;

import java.awt.*;

public class POMFluidType {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final ResourceLocation GAS_STILL_RL = new ResourceLocation(PixelsOfMc.MOD_ID, "block/gas_still");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<FluidType> MERCURY_FLUID_TYPE = registerFluid("mercury_fluid", 0, 21, 89,
            FluidType.Properties.create().lightLevel(2).temperature(300).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.002f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY_POWDER_SNOW));

    public static final RegistryObject<FluidType> SULFURIC_ACID_FLUID_TYPE = registerFluid("sulfuric_acid_fluid", 214, 171, 42,
            FluidType.Properties.create().lightLevel(1).temperature(300).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.008f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    //-----------SUPERCOOLED-----------//
    public static final RegistryObject<FluidType> HYDROGEN_FLUID_TYPE = registerFluid("hydrogen_fluid", 225, 223, 235,
            FluidType.Properties.create().lightLevel(1).temperature(19).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));
    public static final RegistryObject<FluidType> NITROGEN_FLUID_TYPE = registerFluid("nitrogen_fluid", 151, 130, 230,
            FluidType.Properties.create().lightLevel(1).temperature(70).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> OXYGEN_FLUID_TYPE = registerFluid("oxygen_fluid", 119, 171, 240,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(15).viscosity(5).canDrown(false).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> CHLORINE_FLUID_TYPE = registerFluid("chlorine_fluid", 148, 176, 96,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> BROMINE_FLUID_TYPE = registerFluid("bromine_fluid", 218, 146, 163,
            FluidType.Properties.create().lightLevel(1).temperature(80).density(15).viscosity(5).canDrown(true).canPushEntity(true)
                    .motionScale(0.01f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    //-----------GAS-----------//

    public static final RegistryObject<FluidType> HYDROGEN_GAS_TYPE = registerGas("hydrogen_gas", 225, 223, 235,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-15).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));
    public static final RegistryObject<FluidType> NITROGEN_GAS_TYPE = registerGas("nitrogen_gas", 151, 130, 230,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-15).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> OXYGEN_GAS_TYPE = registerGas("oxygen_gas", 119, 171, 240,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-15).viscosity(0).canDrown(false).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> CHLORINE_GAS_TYPE = registerGas("chlorine_gas", 148, 176, 96,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-15).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));

    public static final RegistryObject<FluidType> BROMINE_GAS_TYPE = registerGas("bromine_gas", 218, 146, 163,
            FluidType.Properties.create().lightLevel(0).temperature(300).density(-15).viscosity(0).canDrown(true).canPushEntity(false)
                    .sound(SoundAction.get("drink"),SoundEvents.BUCKET_EMPTY));


    private static RegistryObject<FluidType> registerFluid(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    private static RegistryObject<FluidType> registerGas(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(GAS_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    public static void registerFluid(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
