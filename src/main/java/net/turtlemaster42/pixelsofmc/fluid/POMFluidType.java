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

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PixelsOfMc.MOD_ID);

    public static final RegistryObject<FluidType> MERCURY_FLUID_TYPE = register("mercury_fluid", 0, 21, 89,
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).canDrown(true).canPushEntity(true).motionScale(0.2f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_FILL_POWDER_SNOW));

    public static final RegistryObject<FluidType> HYDROGEN_FLUID_TYPE = register("hydrogen_fluid", 255, 255, 255,
            FluidType.Properties.create().lightLevel(1).density(15).viscosity(5).density(0).canDrown(true).canPushEntity(true).motionScale(1f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_FILL_POWDER_SNOW));

    public static final RegistryObject<FluidType> OXYGEN_FLUID_TYPE = register("oxygen_fluid", 122, 248, 255,
            FluidType.Properties.create().lightLevel(1).density(15).viscosity(5).density(0).canPushEntity(true).motionScale(1f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_FILL_POWDER_SNOW));

    public static final RegistryObject<FluidType> SULFURIC_ACID_FLUID_TYPE = register("sulfuric_acid_fluid", 214, 171, 42,
            FluidType.Properties.create().lightLevel(1).density(15).viscosity(5).density(0).canDrown(true).canPushEntity(true).motionScale(0.8f).sound(SoundAction.get("drink"),SoundEvents.BUCKET_FILL_POWDER_SNOW));



    private static RegistryObject<FluidType> register(String name, int R, int G, int B, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL,
                new Color(R, G, B).getRGB(), new Vector3f((float)R / 255f, (float)G / 255f, (float)B / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
